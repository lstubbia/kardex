package com.kardox.controller;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handler Exception.
 * Este manejador de exceptions permite capturar todas las exceptiones que puedan lanzarse desde
 * cualquier parte del codigo y permite representarla de una manera mas amigable al usuario.
 * Permite usar el mismo layout de presentacion, pero con el stack trace de la exception.
 * 
 * @author lstubbia
 *
 */
@ControllerAdvice
public class ExceptionController {

	private static Logger logger = LoggerFactory.getLogger(ExceptionController.class);

	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNoSuchElementException(NoSuchElementException e, final Model model) {
		logger.error("Exception durante la ejecucion de Spring-boot: NOT_FOUND", e);
		model.addAttribute("errorMessage", e.getMessage());
		model.addAttribute("exception", e);
		return e.getMessage();
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String exception(final Throwable throwable, final Model model) {
		logger.error("Exception durante la ejecucion de Spring-boot: INTERNAL_SERVER_ERROR", throwable);
		String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
		model.addAttribute("errorMessage", errorMessage);
		model.addAttribute("exception", throwable);
		return "exception";
	}

	@ExceptionHandler(RuntimeException.class)
	public ModelAndView handleUnknownResourceException(RuntimeException ex) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", ex);
		mav.setViewName("exception");
		return mav;
	}
}
