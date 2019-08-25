package com.kardox.form;

import com.kardox.domain.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductForm extends BaseForm<Product>{

	private String code;
	private String name;
	private Double amount;
}