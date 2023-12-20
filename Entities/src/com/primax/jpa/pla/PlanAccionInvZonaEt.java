package com.primax.jpa.pla;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "PLAN_ACCION_INV_ZONA_ET")

@NamedStoredProcedureQuery(name = "getGenerarOrgPlnInvZona", procedureName = "fun_generar_org_pln_inv_zona", resultClasses = PlanAccionInvZonaEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "anio"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idPlanAccionInvAnio"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "mes"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class PlanAccionInvZonaEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_plan_accion_inv_zona_et", sequenceName = "seq_plan_accion_inv_zona_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_plan_accion_inv_zona_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_plan_accion_inv_zona")
	private Long idPlanAccionInvZona;

	@Column(name = "id_plan_accion_inv_anio")
	private Long idPlanAccionInvAnio;

	@Column(name = "id_zona")
	private Long idZona;

	@Column(name = "zona", length = 20)
	private String zona;

	@Column(name = "anio")
	private Long anio;

	@Column(name = "mes")
	private Long mes;

	public PlanAccionInvZonaEt() {
		this.mes = 0L;
		this.zona = "";
		this.anio = 0L;
		this.idZona = 0L;
		this.idPlanAccionInvAnio = 0L;
	}

	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
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

	public Long getIdPlanAccionInvZona() {
		return idPlanAccionInvZona;
	}

	public void setIdPlanAccionInvZona(Long idPlanAccionInvZona) {
		this.idPlanAccionInvZona = idPlanAccionInvZona;
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

		if (obj instanceof PlanAccionInvZonaEt) {

			PlanAccionInvZonaEt other = (PlanAccionInvZonaEt) obj;
			if (this.idPlanAccionInvZona == null)
				return this == other;

			return this.idPlanAccionInvZona.equals(other.idPlanAccionInvZona);
		}
		return false;
	}

}
