package com.kardex.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.kardox.domain.Movement;
import com.kardox.domain.MovementType;
import com.kardox.domain.Stock;
import com.kardox.exception.InvalidNegativeStockException;
import com.kardox.form.PaginatorForm;
import com.kardox.repository.MovementRepository;
import com.kardox.repository.StockRepository;
import com.kardox.service.MovementService;
import com.kardox.service.MovementServiceImpl;

@SpringBootTest(classes = MovementService.class)
public class MovementServiceTest {

	@Mock
	private MovementRepository movementRepository;
	
	@Mock
	private StockRepository stockRepository;
	
	@InjectMocks
    private MovementService movementService = new MovementServiceImpl();
	
    @DisplayName("Test find all movements")
    @Test
    void testFindMovements() {
    	Page<Movement> mockedPage = Mocks.getMovementPage();
    	when(movementRepository.findByProductIdOrderByCreatedDateDesc(any(Long.class), any(PageRequest.class))).thenReturn(mockedPage);
    	
    	PaginatorForm pg = new PaginatorForm();
    	Page<Movement> movements = movementService.getMovements(1L, pg);
    	
    	assertNotNull(movements);
    	assertEquals(movements.getTotalPages(), 1);
    	assertEquals(movements.getNumberOfElements(), 10);
    }
    
    @DisplayName("Test reset stock")
    @Test
    void testResetStock() {
    	Optional<Stock> stk = Mocks.getMockedOptionalStock();
    	stk.get().setQuantity(100L);
    	
    	Stock s = Mocks.getMockedStock();
    	s.setQuantity(100L);
    	
    	Movement m = Mocks.getMockedMovement(Mocks.getMockedProduct(), 1L, MovementType.RESET, 85L);
    	
    	when(stockRepository.findById(1L)).thenReturn(stk);
    	when(stockRepository.findByProductId(1L)).thenReturn(stk);
    	when(stockRepository.save(s)).thenReturn(s);
    	when(movementRepository.save(m)).thenReturn(m);
		
    	Movement mv = new Movement();
    	mv.setProduct(Mocks.getMockedProduct());
    	mv.setQuantity(85L);
    	mv.setType(MovementType.RESET);
		try {
			mv = movementService.registerMovement(mv, 1L);
		} catch (InvalidNegativeStockException e) {
			e.printStackTrace();
		}
    	assertNotNull(mv);
    	assertEquals(mv.getQuantity(), new Long(-15));
    }
    
    @DisplayName("Test reset stock fails")
    @Test
    void testResetStockFail() {
    	Optional<Stock> stk = Mocks.getMockedOptionalStock();
    	Stock s = Mocks.getMockedStock();
    	
    	Movement m = Mocks.getMockedMovement(Mocks.getMockedProduct(), 1L, MovementType.RESET, -1L);
    	
    	when(stockRepository.findById(1L)).thenReturn(stk);
    	when(stockRepository.findByProductId(1L)).thenReturn(stk);
    	when(stockRepository.save(s)).thenReturn(s);
    	when(movementRepository.save(m)).thenReturn(m);
		
    	Movement mv = new Movement();
    	mv.setProduct(Mocks.getMockedProduct());
    	mv.setQuantity(-1L);
    	mv.setType(MovementType.RESET);
		try {
			mv = movementService.registerMovement(mv, 1L);
		} catch (InvalidNegativeStockException e) {
			e.printStackTrace();
		}
    	assertThatExceptionOfType(InvalidNegativeStockException.class);
    }
    
    @DisplayName("Test sell")
    @Test
    void testSell() {
    	Optional<Stock> stk = Mocks.getMockedOptionalStock();
    	stk.get().setQuantity(100L);
    	
    	Stock s = Mocks.getMockedStock();
    	s.setQuantity(100L);
    	
    	Movement m = Mocks.getMockedMovement(Mocks.getMockedProduct(), 1L, MovementType.OUT, 20L);
    	
    	when(stockRepository.findById(1L)).thenReturn(stk);
    	when(stockRepository.findByProductId(1L)).thenReturn(stk);
    	when(stockRepository.save(s)).thenReturn(s);
    	when(movementRepository.save(m)).thenReturn(m);
		
    	Movement mv = new Movement();
    	mv.setProduct(Mocks.getMockedProduct());
    	mv.setQuantity(-20L);
    	mv.setType(MovementType.OUT);
		try {
			mv = movementService.registerMovement(mv, 1L);
		} catch (InvalidNegativeStockException e) {
			e.printStackTrace();
		}
    	assertNotNull(mv);
    	assertEquals(mv.getQuantity(), new Long(-20));
    }
    
    @DisplayName("Test sell fails")
    @Test
    void testSellFail() {
    	Optional<Stock> stk = Mocks.getMockedOptionalStock();
    	Stock s = Mocks.getMockedStock();
    	
    	Movement m = Mocks.getMockedMovement(Mocks.getMockedProduct(), 1L, MovementType.OUT, 10L);
    	
    	when(stockRepository.findById(1L)).thenReturn(stk);
    	when(stockRepository.findByProductId(1L)).thenReturn(stk);
    	when(stockRepository.save(s)).thenReturn(s);
    	when(movementRepository.save(m)).thenReturn(m);
		
    	Movement mv = new Movement();
    	mv.setProduct(Mocks.getMockedProduct());
    	mv.setQuantity(-10L);
    	mv.setType(MovementType.OUT);
		try {
			mv = movementService.registerMovement(mv, 1L);
		} catch (InvalidNegativeStockException e) {
			e.printStackTrace();
		}
    	assertThatExceptionOfType(InvalidNegativeStockException.class);
    }
    
    @DisplayName("Test in")
    @Test
    void testInStock() {
    	Optional<Stock> stk = Mocks.getMockedOptionalStock();
    	stk.get().setQuantity(100L);
    	
    	Stock s = Mocks.getMockedStock();
    	s.setQuantity(100L);
    	
    	Movement m = Mocks.getMockedMovement(Mocks.getMockedProduct(), 1L, MovementType.IN, 20L);
    	
    	when(stockRepository.findById(1L)).thenReturn(stk);
    	when(stockRepository.findByProductId(1L)).thenReturn(stk);
    	when(stockRepository.save(s)).thenReturn(s);
    	when(movementRepository.save(m)).thenReturn(m);
		
    	Movement mv = new Movement();
    	mv.setProduct(Mocks.getMockedProduct());
    	mv.setQuantity(20L);
    	mv.setType(MovementType.IN);
		try {
			mv = movementService.registerMovement(mv, 1L);
		} catch (InvalidNegativeStockException e) {
			e.printStackTrace();
		}
    	assertNotNull(mv);
    	assertEquals(mv.getQuantity(), new Long(20));
    }
}
