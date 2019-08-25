package com.kardex.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.kardox.domain.Movement;
import com.kardox.domain.MovementType;
import com.kardox.domain.Product;
import com.kardox.domain.Stock;
import com.kardox.form.StockForm;

/**
 * Mocks de objetos de domino.
 * 
 * @author lstubbia
 *
 */
public class Mocks {

	public static Optional<Product> getMockedOptionalProduct() {
		return Optional.of(getMockedProduct());
	}

	public static Optional<Stock> getMockedOptionalStock() {
		return Optional.of(getMockedStock());
	}

	public static Product getMockedProduct() {
		Product prod = new Product();
		prod.setId(1L);
		prod.setAmount(100.00);
		prod.setCode("0001");
		prod.setName("CAMISETA");
		prod.setCreatedDate(new Date());
		return prod;
	}

	public static Stock getMockedProductStock(Long id, String code, String name) {
		Product prod = new Product(code, name, 1.0);
		prod.setId(id);
		prod.setCreatedDate(new Date());

		Stock stk = new Stock();
		stk.setId(id);
		stk.setProduct(prod);
		stk.setQuantity(1L);
		return stk;
	}

	public static Movement getMockedMovement(Product prod, Long id, MovementType type, Long quantity) {
		Movement mv = new Movement();
		mv.setProduct(prod);
		mv.setId(id);
		mv.setType(type);
		mv.setCreatedDate(new Date());
		mv.setQuantity(quantity);
		return mv;
	}

	public static Stock getMockedStock() {
		Stock stk = new Stock();
		stk.setProduct(getMockedProduct());
		stk.setId(1L);
		stk.setQuantity(0L);
		return stk;
	}

	public static Page<Stock> getStockPage() {
		List<Stock> expected = new ArrayList<Stock>(10);
		expected.add(getMockedProductStock(1L, "0001", "TEST1"));
		expected.add(getMockedProductStock(2L, "0002", "TEST2"));
		expected.add(getMockedProductStock(3L, "0003", "TEST3"));
		expected.add(getMockedProductStock(4L, "0004", "TEST4"));
		expected.add(getMockedProductStock(5L, "0005", "TEST5"));
		expected.add(getMockedProductStock(6L, "0006", "TEST6"));
		expected.add(getMockedProductStock(7L, "0007", "TEST7"));
		expected.add(getMockedProductStock(8L, "0008", "TEST8"));
		expected.add(getMockedProductStock(9L, "0009", "TEST9"));
		expected.add(getMockedProductStock(10L, "0010", "TEST10"));
		return new PageImpl<Stock>(expected);
	}

	public static Page<Movement> getMovementPage() {
		List<Movement> expected = new ArrayList<Movement>(10);
		Product prod = getMockedProduct();
		expected.add(getMockedMovement(prod, 1L, MovementType.IN, 20L));
		expected.add(getMockedMovement(prod, 2L, MovementType.OUT, 10L));
		expected.add(getMockedMovement(prod, 3L, MovementType.RESET, 10L));
		expected.add(getMockedMovement(prod, 4L, MovementType.IN, 20L));
		expected.add(getMockedMovement(prod, 5L, MovementType.OUT, 10L));
		expected.add(getMockedMovement(prod, 6L, MovementType.RESET, 10L));
		expected.add(getMockedMovement(prod, 7L, MovementType.IN, 20L));
		expected.add(getMockedMovement(prod, 8L, MovementType.OUT, 10L));
		expected.add(getMockedMovement(prod, 9L, MovementType.RESET, 20L));
		expected.add(getMockedMovement(prod, 10L, MovementType.RESET, 10L));
		return new PageImpl<Movement>(expected);
	}

	public static StockForm getMockedStockForm(Long quantity, Long newQuantity) {
		StockForm form = new StockForm();
		form.setId(1L);
		form.setNewQuantity(newQuantity);
		form.setQuantity(quantity);
		form.setProduct(getMockedProduct());
		return form;
	}
}
