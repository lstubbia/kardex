package com.kardox.form;

import com.kardox.domain.Product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SaleItem {

	private Product product;
	private Long quantity;
}