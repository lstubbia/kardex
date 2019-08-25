package com.kardex.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.kardox.controller.HomeController;

public class HomeControllerTest {

    private static MockMvc mockMvc;
	
	private static HomeController homeController;
	
	@BeforeAll
	public static void setup() {
		homeController = new HomeController();
		mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
	}
	
	@Test
	public void testHome() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("home"));
	}
}
