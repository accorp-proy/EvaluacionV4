package com.primax.jpa.enums;

public enum EstadoPlanAccionInvEnum {

	ESTADO_PLAN_A("Todos"),
	PENDIENTE("PENDIENTE"),
	INGRESADO("INGRESADO");

	private String estado;

	EstadoPlanAccionInvEnum(String estadoDesc) {
		this.estado = estadoDesc;
	}

	public String getDescripcion() {
		return this.estado;
	}

}
