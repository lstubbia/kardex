package com.kardox.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kardox.domain.Product;
import com.kardox.domain.Stock;
import com.kardox.form.PaginatorForm;
import com.kardox.repository.ProductRepository;
import com.kardox.repository.StockRepository;

/**
 * Servicio que maneja los productos y sirve de intermediario entre controller-repository
 * Incluye reglas de negocio y se asegura la consistencia de datos.
 * 
 * @author lstubbia
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private StockRepository stockRepository;
	
	@Override
	public Page<Stock> getProducts(PaginatorForm paginator) {
		PageRequest pageRequest = PageRequest.of(paginator.getSelectedPage(), paginator.getSelectedRowsToShow());
		if(StringUtils.isEmpty(paginator.getQuery())) {
			return stockRepository.findAll(pageRequest);
		}
		return stockRepository.findByProductNameOrderByCreatedDateDesc(paginator.getQuery(), pageRequest);
	}

	@Override
	public Optional<Product> getById(Long prodId) {
		return productRepository.findById(prodId);
	}

	@Override
	@Transactional
	public Product persistProduct(Product product) {
		product = productRepository.save(product);
		Stock stk = new Stock();
		stk.setProduct(product);
		stk.setQuantity(0L);
		stockRepository.save(stk);
		return product;
	}

	@Override
	public Optional<Stock> getByStockId(Long stockId) {
		return stockRepository.findById(stockId);
	}
}
