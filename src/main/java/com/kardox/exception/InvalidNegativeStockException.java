package com.kardox.exception;

import lombok.Getter;

/**
 * Esta exception deberia ser lanzada cuando cualquier operación sobre
 * la tabla de Stock deje inconsistente la cantidad (Valores negativos).
 * 
 * @author lstubbia
 *
 */
@Getter
public class InvalidNegativeStockException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String message;

	public InvalidNegativeStockException(String message) {
		this.message = message;
	}
}
