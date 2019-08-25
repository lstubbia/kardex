package com.kardox.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kardox.domain.Stock;

/**
 * Repositorio de Stock que permite resolver el acceso a datos (DB)
 * 
 * @author lstubbia
 *
 */
public interface StockRepository extends JpaRepository<Stock, Long> {

	/**
	 * Recupera stock por id de producto.
	 * 
	 * @param id
	 * @return
	 */
	Optional<Stock> findByProductId(Long id);
	
	/**
	 * Recupera todos los productos paginados y filtrados por nombre
	 * 
	 * @param name
	 * @param page
	 * @return page
	 */
	Page<Stock> findByProductNameOrderByCreatedDateDesc(String name, Pageable page);
}
