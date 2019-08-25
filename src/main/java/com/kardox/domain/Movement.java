package com.kardox.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * Representa los movimientos de cada producto.
 * 
 * @author lstubbia
 *
 */
@Entity
@Table(name = "movement")
@Getter
@Setter
public class Movement extends AbstractEntity {

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	@NotNull
	private Product product;

	@Column(name = "QUANTITY", nullable = false)
	@NotNull
	private Long quantity;

	@Column(name = "TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private MovementType type;

}