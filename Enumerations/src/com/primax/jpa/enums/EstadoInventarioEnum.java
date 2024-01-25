package com.primax.jpa.enums;

public enum EstadoInventarioEnum {

	ESTADO_CHECK("Todos"),
	AGENDADA("AGENDADA"),
	EN_EJECUCION("EN EJECUCION"),
	EJECUTADO("EJECUTADO"),
	NO_EJECUTADO("NO EJECUTADO"),
	INCONCLUSO("INCONCLUSO");

	private String estado;

	EstadoInventarioEnum(String estadoDesc) {
		this.estado = estadoDesc;
	}

	public String getDescripcion() {
		return this.estado;
	}

}
