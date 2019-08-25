package com.kardox.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.kardox.domain.Movement;
import com.kardox.domain.MovementType;
import com.kardox.domain.Product;
import com.kardox.domain.Stock;
import com.kardox.exception.InvalidNegativeStockException;
import com.kardox.form.PaginatorForm;
import com.kardox.form.ProductForm;
import com.kardox.form.StockForm;
import com.kardox.service.MovementService;
import com.kardox.service.ProductService;
import com.kardox.validator.ProductValidator;

@Controller
@SessionAttributes(types = ProductForm.class)
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private MovementService movementService;
	
	@Autowired
	private ProductValidator productValidator;

	@GetMapping("/product")
	public String getProducts(Model model, PaginatorForm paginator,
			@RequestParam(name = "page", defaultValue = "0") int page, 
			@RequestParam(name = "error", defaultValue = "") String error) {
		if (paginator == null) {
			paginator = new PaginatorForm();			
		}
		paginator.setSelectedPage(page);
		Page<Stock> products = productService.getProducts(paginator);
		paginator.update(products);
		model.addAttribute("paginator", paginator);
		model.addAttribute("selections", products.getContent());
		model.addAttribute("errorException", error);
		return "product";
	}

	@RequestMapping("/product/new")
	public String createProduct(Model model) {
		ProductForm productForm = new ProductForm();
		model.addAttribute(productForm);
		return "productUIForm";
	}

	@RequestMapping(value = "/product/{prodId}", method = RequestMethod.GET)
	public String getProduct(@PathVariable("prodId") Long prodId, Model model) {
		ProductForm productForm = new ProductForm();
		if (null != prodId) {
			Optional<Product> prod = productService.getById(prodId);
			if (prod.isPresent()) {
				productForm.fromDomain(prod.get());
			}
		}
		model.addAttribute(productForm);
		return "productUIForm";
	}

	@RequestMapping(value = { "/product/{prodId}", "/product/new" }, method = RequestMethod.POST)
	public String saveProduct(ProductForm product, BindingResult result, Model model,
			@ModelAttribute("action") String action, SessionStatus status) {
		productValidator.validate(product, result);
		if (result.hasErrors()) {
			return "productUIForm";
		} else {
			productService.persistProduct(product.toDomain(Product.class));
			status.setComplete();
			return "redirect:/product";
		}
	}

	@RequestMapping(value = "/product", method = RequestMethod.POST)
	public String findOfficePage(PaginatorForm paginator, BindingResult result, Model model) {
		if (StringUtils.isEmpty(paginator.getQuery())) {
			paginator.setQuery("");
		} else {
			paginator.setSelectedPage(1);
		}
		Page<Stock> products = productService.getProducts(paginator);
		if (products != null && products.getContent().size() == 1) {
			return "redirect:/product/" + products.getContent().get(0).getId();
		} else {
			paginator.update(products);
			model.addAttribute("paginator", paginator);
			model.addAttribute("selections", products.getContent());
			return "product";
		}
	}
	
	@RequestMapping(value = "/registerStock", method = RequestMethod.POST)
	public String saleStock(StockForm stk, Model model) {
		try {
			if(stk.getNewQuantity() <= 0) {
				String err = "La cantidad especificada debe ser mayor a 0";
				return "redirect:/product?error="+err;
			}
			Movement mv = new Movement();
			if(MovementType.OUT.equals(stk.getType())) {
				mv.setQuantity(stk.getNewQuantity() * -1);
			} else {
				mv.setQuantity(stk.getNewQuantity());
			}
			mv.setType(stk.getType());
			movementService.registerMovement(mv, stk.getId());
		} catch (InvalidNegativeStockException e) {
			return "redirect:/product?error="+e.getMessage();
		}
		return "redirect:/product";
	}
	
	@RequestMapping(value = "/stock/{stockId}", method = RequestMethod.GET)
	@ResponseBody
	public StockForm getStock(@PathVariable("stockId") Long stockId, StockForm stk) {
		Optional<Stock> stock = productService.getByStockId(stockId);
		StockForm form = new StockForm();
		if(stock.isPresent()) {
			form.fromDomain(stock.get());
		}
		return form;
	}
}