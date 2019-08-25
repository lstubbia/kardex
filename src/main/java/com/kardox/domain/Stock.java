package com.kardox.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stock")
@Getter
@Setter
public class Stock extends AbstractEntity {

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	@NotNull
	private Product product;

	@Column(name = "QUANTITY", nullable = false)
	@NotNull
	private Long quantity;
	
	/**
	 * Constructor por defecto
	 */
	public Stock() {
		
	}
	
	public Stock(Product product, Long quantity) {
		this.product = product;
		this.quantity = quantity;
	}
}