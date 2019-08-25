package com.kardox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kardox.domain.Product;

/**
 * Repositorio de productos que permite resolver el acceso a datos (DB)
 * 
 * @author lstubbia
 *
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

	/**
	 * Recupera todos los productos paginados
	 * 
	 * @return page
	 */
	
	Page<Product> findAll(Pageable page);

}
