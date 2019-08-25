package com.kardox.form;

import com.kardox.domain.MovementType;
import com.kardox.domain.Product;
import com.kardox.domain.Stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockForm extends BaseForm<Stock>{

	private Product product;
	private Long quantity;
	private Long newQuantity;
	private MovementType type;
}
