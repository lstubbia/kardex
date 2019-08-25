package com.kardox.domain;

/**
 * Este enum permite identificar movimientos de
 * entrada y salida, para detallar el stock final
 * 
 * @author lstubbia
 *
 */
public enum MovementType {

	IN("INGRESO"), 
	OUT("SALIDA"),
	RESET("RESET");

	private final String displayName;

	MovementType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}