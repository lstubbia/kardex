package com.kardox.exception;

/**
 * Esta exception deberia ser utilizada en la capa de servicio por alguna
 * regla de negocio que esta fallando o si se necesita hacer un wrapper
 * de una exception generica.
 * 
 * @author lstubbia
 *
 */
public class ServiceLayerException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor que permite hacer un wrapper de una exception generica.
	 * @param e
	 */
	public ServiceLayerException(Exception e) {
		super(e);
	}
}
