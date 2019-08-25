package com.kardox.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kardox.domain.Product;
import com.kardox.domain.Stock;
import com.kardox.form.PaginatorForm;

/**
 * Interfaz del servicio de producto.
 * 
 * @author lstubbia
 *
 */
public interface ProductService {

	/**
	 * Permite recuperar la informacion de un producto
	 * por su identificador. En caso de que con el identificador
	 * no se encuentre la informacion del producto, se devuelve
	 * null.
	 * 
	 * @param prodId
	 * @return product
	 */
	Optional<Product> getById(Long prodId);

	/**
	 * Permite persistir un producto. Creacion/Actualizacion.
	 * 
	 * @param product
	 * @return product
	 */
	Product persistProduct(Product product);

	/**
	 * recupera la informacion del stock de un producto
	 * 
	 * @param stockId
	 * @return Stock
	 */
	Optional<Stock> getByStockId(Long stockId);
	
	/**
	 * Recupera los productos filtrando por pagina o nombre
	 * 
	 * @param paginator
	 * @return
	 */
	Page<Stock> getProducts(PaginatorForm paginator);
}