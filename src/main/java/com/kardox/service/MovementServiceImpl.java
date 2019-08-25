package com.kardox.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kardox.domain.Movement;
import com.kardox.domain.MovementType;
import com.kardox.domain.Stock;
import com.kardox.exception.InvalidNegativeStockException;
import com.kardox.form.PaginatorForm;
import com.kardox.repository.MovementRepository;
import com.kardox.repository.StockRepository;

/**
 * Servicio que maneja los movimientos de productos, indicando salida y entrada
 * 
 * @author lstubbia
 *
 */
@Service
public class MovementServiceImpl implements MovementService {

	private static Logger LOG = LoggerFactory.getLogger(MovementServiceImpl.class);

	@Autowired
	private MovementRepository movementRepository;

	@Autowired
	private StockRepository stockRepository;

	private final Lock lock = new ReentrantLock();

	@Override
	public Page<Movement> getMovements(Long id, PaginatorForm paginator) {
		PageRequest pageRequest;
		if (paginator == null) {
			pageRequest = PageRequest.of(0, 5);
		} else {
			pageRequest = PageRequest.of(paginator.getSelectedPage(), paginator.getSelectedRowsToShow());
		}
		return movementRepository.findByProductIdOrderByCreatedDateDesc(id, pageRequest);
	}

	@Override
	public Movement registerMovement(Movement mv, Long stockId) throws InvalidNegativeStockException {
		boolean done = false;
		Optional<Stock> stock = stockRepository.findById(stockId);
		mv.setProduct(stock.get().getProduct());
		while(!done) {
			try {
				done = persistMovement(mv);
				LOG.info("registerMovement: status persist " + done);
			} catch (InterruptedException e) {
				LOG.error("Interrumped lock: " + e);
				// Validar los intentos que deberian ser validos para un retry.
			}
		}
		return mv;
	}
	
	/**
	 * Este metodo fue pensado para trabajar con una venta que soporte
	 * varios productos como items de la venta.
	 * No se terminó de implementar.
	 * 
	 * Tiene sentido aplicar algun service-executor?
	 * 
	 * @param sale
	 * @return
	 */
	public List<Error> registerMultipleMovement(List<Movement> movements) {
		final List<Error> errors = new ArrayList<>();
		movements.forEach(i -> {
			boolean done = false;
			Movement m = new Movement();
			m.setQuantity(i.getQuantity()*-1);
			m.setType(MovementType.OUT);
			while(!done) {
				try {
					done = persistMovement(m);
				} catch (Exception e) {
					errors.add(new Error(e.getMessage()));
					break;
				}
			}
		});
		return errors;
	}
	
	/**
	 * Este metodo permite centralizar las operaciones que se intenten aplicar sobre el stock de cada
	 * producto y los movimientos que cada operación genera.
	 * Utiliza la interfaz {@link java.util.concurrent.locks.Lock} para serializar el accesos a recursos
	 * compartidos, en este caso, serializa la actualización de las tablas de stock y movements.
	 * En comparación con la utilizacion de modificador {synchronized}, lock permite manejar retries
	 * y cambios de estado en los threads.
	 * 
	 * @param movement
	 * @return boolean (Lock service was locked or not)
	 * @throws InvalidNegativeStockException
	 * @throws InterruptedException
	 */
	private boolean persistMovement(Movement movement) throws InvalidNegativeStockException, InterruptedException {
		// trying to get a lock
		boolean attempt = lock.tryLock(100, TimeUnit.MILLISECONDS);
		LOG.info("persistMovement: attempt " + attempt);
		if (attempt) {
			LOG.info("persistMovement: locking");
			try {
				LOG.info("persistMovement: stockRepository.findByProductId : " + movement.getProduct().getId());
				Optional<Stock> stock = stockRepository.findByProductId(movement.getProduct().getId());
				LOG.info("persistMovement: stock.isPresent() : " + stock.isPresent());
				if (stock.isPresent()) {
					Stock stk = stock.get();
					movement.setProduct(stk.getProduct());
					if(movement.getType().equals(MovementType.RESET)) {
						movement.setQuantity(movement.getQuantity() - stk.getQuantity());
					}
					Long diff = Long.sum(stk.getQuantity(), movement.getQuantity());
					if (diff < 0) {
						StringBuilder st = new StringBuilder();
						st.append("Stock negativo invalido!").append("\n");
						st.append("Producto: ").append(movement.getProduct().getName()).append("\n");
						st.append("Stock actual: ").append(stk.getQuantity()).append("\n");
						st.append("operación: ").append(movement.getType()).append(" Cant: ").append(movement.getQuantity());
						
						LOG.warn("persistMovement: " + st.toString() + " stk: " + stk + " movement: " + movement);
						throw new InvalidNegativeStockException(st.toString());
					}
					stk.setQuantity(diff);
					stockRepository.save(stk);
					movementRepository.save(movement);
					attempt = true;
				}
			} finally {
				lock.unlock();
				LOG.info("persistMovement: unlocking");
			}
		}
		return attempt;
	}
}