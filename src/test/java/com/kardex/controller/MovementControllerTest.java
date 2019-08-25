package com.kardex.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.kardex.service.Mocks;
import com.kardox.controller.MovementController;
import com.kardox.form.PaginatorForm;
import com.kardox.service.MovementService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class MovementControllerTest {

    private static MockMvc mockMvc;
	
    private static MovementController mvController;
    
    private static MovementService mvService;
	
	@BeforeAll
	public static void setup() {
		mvController = new MovementController();
		mvService = mock(MovementService.class);
		mockMvc = MockMvcBuilders.standaloneSetup(mvController).build();
	}
	
	@Test
	public void testGetMovements() throws Exception {
		when(mvService.getMovements(any(Long.class), any(PaginatorForm.class))).thenReturn(Mocks.getMovementPage());
		
		mockMvc.perform(get("/product/{prodId}/movement", 1L))
		.andExpect(status().isOk())
		.andExpect(view().name("movement"));
	}
}