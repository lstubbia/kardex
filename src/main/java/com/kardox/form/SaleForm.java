package com.kardox.form;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SaleForm {
	private List<SaleItem> items;
}