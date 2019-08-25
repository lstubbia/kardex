package com.kardox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kardox.domain.Product;
import com.kardox.service.ProductService;

/**
 * Aplicacion Kardox.
 * Implementación simplificada de un sistema de inventario
 * que permite manejar productos y el stock de cada uno.
 * 
 * @author lstubbia
 *
 */
@SpringBootApplication
public class KardexApplication implements CommandLineRunner {
	
	@Autowired
	private ProductService productService;
	
	public static void main(String[] args) {
		SpringApplication.run(KardexApplication.class, args);
	}

	/**
	 * Inicializa los valores de la base con productos y su correspondiente stock
	 * para agilizar las pruebas y validaciones. 
	 */
	@Override
	public void run(String... args) throws Exception {
		productService.persistProduct(new Product("0001","CAMISETA RIVER", 2345.0));
		productService.persistProduct(new Product("0002","HULK", 345.0));
		productService.persistProduct(new Product("0003","THOR", 145.0));
		productService.persistProduct(new Product("0004","CAMISETA SPIDER MAN", 1545.0));
		productService.persistProduct(new Product("0005","CAMISETA ARGENTINA", 2345.0));
		productService.persistProduct(new Product("0006","MATE STAR WAR", 699.0));
		productService.persistProduct(new Product("0007","CAMISETA STAR WAR", 1845.0));
		productService.persistProduct(new Product("0008","DARTH VADER MINIATURA", 3345.0));
		productService.persistProduct(new Product("0009","CHEWBACCA", 2345.0));
		productService.persistProduct(new Product("0010","YODA", 2345.0));
	}
}