package com.kardex.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import com.kardox.domain.Product;
import com.kardox.domain.Stock;
import com.kardox.form.PaginatorForm;
import com.kardox.repository.ProductRepository;
import com.kardox.repository.StockRepository;
import com.kardox.service.ProductService;
import com.kardox.service.ProductServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = ProductService.class)
public class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private StockRepository stockRepository;
	
	@InjectMocks
    private ProductService productService = new ProductServiceImpl();

    @DisplayName("Test get product by ID")
    @Test
    void testGetProduct() {
    	Long prodId = 1L;
    	Optional<Product> mockedProd = Mocks.getMockedOptionalProduct();
    	when(productRepository.findById(prodId)).thenReturn(mockedProd);
    	Optional<Product> product = productService.getById(prodId);
    	assertEquals(mockedProd.get(), product.get());
    	assertEquals(mockedProd.get().getId(), product.get().getId());
    }
    
    @DisplayName("Test get Stock by ID")
    @Test
    void testGetStock() {
    	Long stockId = 1L;
    	Optional<Stock> mockedStock = Mocks.getMockedOptionalStock();
    	when(stockRepository.findById(stockId)).thenReturn(mockedStock);
    	
    	Optional<Stock> stock = productService.getByStockId(stockId);
    	
    	assertEquals(mockedStock.get(), stock.get());
    	assertEquals(mockedStock.get().getId(), stock.get().getId());
    }
    
    @DisplayName("Test save product")
    @Test
    void testPersistProduct() {
    	Product prod = new Product();
    	Product mockedProd = Mocks.getMockedProduct();
    	Stock mockedStock = Mocks.getMockedStock();
    	when(productRepository.save(prod)).thenReturn(mockedProd);
    	when(stockRepository.save(new Stock())).thenReturn(mockedStock);
    	
    	Product savedProduct = productService.persistProduct(prod);
    	
    	assertNotNull(savedProduct);
    	assertEquals(savedProduct.getId(), mockedProd.getId());
    	assertEquals(savedProduct.getName(), mockedProd.getName());
    }
    
    @DisplayName("Test find all products")
    @Test
    void testFindProduct() {
    	Page<Stock> mockedPage = Mocks.getStockPage();
    	when(stockRepository.findAll(any(PageRequest.class))).thenReturn(mockedPage);
    	
    	PaginatorForm pg = new PaginatorForm();
    	Page<Stock> products = productService.getProducts(pg);
    	
    	assertNotNull(products);
    	assertEquals(products.getTotalPages(), 1);
    	assertEquals(products.getNumberOfElements(), 10);
    }
}