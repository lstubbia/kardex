package com.kardox.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.kardox.form.ProductForm;

/**
 * Componente utilizado para validar el formulario de Producto.
 * En caso de no cumplir con las validaciones, los errores son 
 * incluidos dentro de una lista de errores (BindingResult) para
 * ser representados por thymeleaf mediante el tag th:errores. 
 * 
 * @author lstubbia
 *
 */
@Component
public class ProductValidator implements Validator {

	private static final Logger logger = LoggerFactory.getLogger(ProductValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(ProductForm.class);
	}

	/**
	 * Metodo utilizado para validar los campos necesarios de productForm.
	 * Recibe el objeto a validar y la lista de errores donde se pueden
	 * incluir las validaciones que fallen.
	 * 
	 * @param productForm
	 * @param errors
	 */
	@Override
	public void validate(Object productForm, Errors errors) {
		logger.debug("Validando {}", productForm);
		ProductForm form = (ProductForm) productForm;

		if (StringUtils.isEmpty(form.getName())) {
			errors.rejectValue("name", "productForm.required.name", "El nombre del producto es requerido");
		}
		if (StringUtils.isEmpty(form.getCode())) {
			errors.rejectValue("code", "productForm.required.code", "El código del producto es requerido");
		}
		if (form.getAmount() == null || form.getAmount() <= 0) {
			errors.rejectValue("amount", "productForm.required.amount", "El importe del producto es incorrecto");
		}
	}
}