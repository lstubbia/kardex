package com.kardox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kardox.domain.Movement;

/**
 * Repositorio de movimientos que permite resolver el acceso a datos (DB)
 * 
 * @author lstubbia
 *
 */
public interface MovementRepository extends JpaRepository<Movement, Long> {

	/**
	 * Recupera todos los movimientos por producto
	 * 
	 * @return page
	 */
	Page<Movement> findByProductIdOrderByCreatedDateDesc(Long id, Pageable page);
}
