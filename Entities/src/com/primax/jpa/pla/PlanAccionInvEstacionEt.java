package com.primax.jpa.pla;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "PLAN_ACCION_INV_ESTACION_ET")
public class PlanAccionInvEstacionEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_plan_accion_inv_estacion_et", sequenceName = "seq_plan_accion_inv_estacion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_plan_accion_inv_estacion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_plan_accion_inv_estacion")
	private Long idPlanAccionInvEstacion;

	@Column(name = "id_plan_accion_inv_anio")
	private Long idPlanAccionInvAnio;

	@Column(name = "agencia", length = 50)
	private String agencia;

	@Column(name = "id_zona")
	private Long idZona;

	@Column(name = "id_agencia")
	private Long idAgencia;

	@Column(name = "anio")
	private Long anio;

	@Column(name = "mes")
	private Long mes;

	public PlanAccionInvEstacionEt() {
		this.agencia = "";
		this.idAgencia = 0L;
		this.idPlanAccionInvAnio = 0L;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	public Long getAnio() {
		return anio;
	}

	public void setAnio(Long anio) {
		this.anio = anio;
	}

	public Long getMes() {
		return mes;
	}

	public void setMes(Long mes) {
		this.mes = mes;
	}

	public Long getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Long idAgencia) {
		this.idAgencia = idAgencia;
	}

	public Long getIdPlanAccionInvEstacion() {
		return idPlanAccionInvEstacion;
	}

	public void setIdPlanAccionInvEstacion(Long idPlanAccionInvEstacion) {
		this.idPlanAccionInvEstacion = idPlanAccionInvEstacion;
	}

	public Long getIdPlanAccionInvAnio() {
		return idPlanAccionInvAnio;
	}

	public void setIdPlanAccionInvAnio(Long idPlanAccionInvAnio) {
		this.idPlanAccionInvAnio = idPlanAccionInvAnio;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof PlanAccionInvEstacionEt) {

			PlanAccionInvEstacionEt other = (PlanAccionInvEstacionEt) obj;
			if (this.idPlanAccionInvEstacion == null)
				return this == other;

			return this.idPlanAccionInvEstacion.equals(other.idPlanAccionInvEstacion);
		}
		return false;
	}

}
