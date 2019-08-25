package com.kardox.form;

import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseForm<T> {
	
	private Long id;

	/**
	 * Este metodo permite la conversion (marshaling) desde una entidad 
	 * a un formulario. Se basa en el nombre de cada campo para realizar
	 * el mapping de fields. Idem {@link com.kardox.form.BaseForm.toDomain}
	 * 
	 * @param entity
	 */
	public void fromDomain(T entity) {
		BeanUtils.copyProperties(entity, this);
	}

	/**
	 * Este metodo permite realizar unmarshal de un formulario y 
	 * lo convierte en una entidad. Idem {@link com.kardox.form.BaseForm.fromDomain}
	 *  
	 * @param clzz
	 * @return
	 */
	public T toDomain(Class<T> clzz) {
		T generic;
		try {
			generic = clzz.newInstance();
			BeanUtils.copyProperties(this, generic);
			return generic;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
