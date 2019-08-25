package com.kardox.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product extends AbstractEntity {
	
	@Column(name = "CODE", nullable = false)
	@NotNull
	private String code;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "AMOUNT")
	private Double amount;
	
	/**
	 * Constructor por defecto
	 */
	public Product() {
		
	}
	
	/**
	 * Constructor por defecto
	 */
	public Product(String code, String name, Double amount) {
		this.code = code;
		this.name = name;
		this.amount = amount;
	}
}
