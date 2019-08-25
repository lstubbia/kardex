package com.kardox.service;

import org.springframework.data.domain.Page;

import com.kardox.domain.Movement;
import com.kardox.exception.InvalidNegativeStockException;
import com.kardox.form.PaginatorForm;

/**
 * Esta interfaz busca exponer los metodos que pueden aplicarse sobre
 * movimientos para registrar entradas, ventas y reset de stock de
 * productos. Oculpa la actualización de los valores de la tabla de stock
 * para tener mas control y poder serializar el acceso a escritura. 
 * 
 * @author lstubbia
 *
 */
public interface MovementService {
	/**
	 * Recupera todos los movimientos por producto
	 * 
	 * @return page
	 */
	Page<Movement> getMovements(Long id, PaginatorForm paginator);

	/**
	 * Registrar Ingreso/venta/actualizacion de stock de un producto.
	 * Devuelve un error si el stock no puede ser actualizado.
	 * 
	 * @param sell
	 * @return errors
	 * @throws InvalidNegativeStockException 
	 */
	Movement registerMovement(Movement mv, Long stockId) throws InvalidNegativeStockException;
}
