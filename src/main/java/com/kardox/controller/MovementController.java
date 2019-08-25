package com.kardox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kardox.domain.Movement;
import com.kardox.form.PaginatorForm;
import com.kardox.service.MovementService;

@Controller
public class MovementController {

	@Autowired
	private MovementService movementService;

	@RequestMapping(value = "/product/{prodId}/movement", method = RequestMethod.GET)
	public String getMovements(@PathVariable("prodId") Long prodId, Model model) {
		Page<Movement> movements = movementService.getMovements(prodId, null);
		model.addAttribute("paginator", new PaginatorForm(movements));
		String name = CollectionUtils.isEmpty(movements.getContent()) ? "Sin Movimientos"
				: movements.getContent().get(0).getProduct().getName();
		model.addAttribute("productName", name);
		model.addAttribute("selections", movements.getContent());
		return "movement";
	}

	@RequestMapping(value = "/product/{prodId}/movement", method = RequestMethod.POST)
	public String movePage(PaginatorForm paginator, @PathVariable("prodId") Long prodId, BindingResult result,
			Model model) {
		if (StringUtils.isEmpty(paginator.getQuery())) {
			paginator.setQuery("");
		} else {
			paginator.setSelectedPage(1);
		}
		Page<Movement> movements = movementService.getMovements(prodId, paginator);
		paginator.update(movements);
		model.addAttribute("paginator", paginator);
		String name = CollectionUtils.isEmpty(movements.getContent()) ? "Sin Movimientos"
				: movements.getContent().get(0).getProduct().getName();
		model.addAttribute("productName", name);
		model.addAttribute("selections", movements.getContent());
		return "movement";
	}
}