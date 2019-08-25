package com.kardex.controller;

import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.kardex.service.Mocks;
import com.kardox.controller.ProductController;
import com.kardox.form.PaginatorForm;
import com.kardox.service.ProductService;
import static org.mockito.Matchers.any;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ProductControllerTest {
	
    private static MockMvc mockMvc;
	
    private static ProductController prodController;
    
    private static ProductService prodService;
	
	@BeforeAll
	public static void setup() {
		prodController = new ProductController();
		prodService = mock(ProductService.class);
		mockMvc = MockMvcBuilders.standaloneSetup(prodController).build();
	}
	
	//@Test
	public void testProductList() throws Exception {
		when(prodService.getProducts(any(PaginatorForm.class))).thenReturn(Mocks.getStockPage());
		mockMvc.perform(get("/product").param("page", "0"))
		.andExpect(status().isOk())
		.andExpect(view().name("product"));
	}

}