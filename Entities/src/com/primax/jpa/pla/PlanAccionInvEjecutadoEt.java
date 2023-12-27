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
@Table(name = "PLAN_ACCION_INV_EJECUTADO_ET")

@NamedStoredProcedureQuery(name = "getGenerarOrgPlnInvEje", procedureName = "fun_generar_org_pln_inv_eje", resultClasses = PlanAccionInvEjecutadoEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "anio"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idPlanAccionInvAnio"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "mes"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idZona"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class PlanAccionInvEjecutadoEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_plan_accion_inv_ejecutado_et", sequenceName = "seq_plan_accion_inv_ejecutado_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_plan_accion_inv_ejecutado_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_plan_accion_inv_ejecutado")
	private Long idPlanAccionInvEjecutado;

	@Column(name = "id_plan_accion_anio_inv")
	private Long idPlanAccionAnioInv;

	@Column(name = "id_planificacion_inventario")
	private Long idPlanificacionInventario;

	@Column(name = "id_tipo_inventario")
	private Long idTipoInventario;

	@Column(name = "descripcion", length = 300)
	private String descripcion;

	@Column(name = "id_zona")
	private Long idZona;

	@Column(name = "anio")
	private Long anio;

	@Column(name = "mes")
	private Long mes;

	@Column(name = "id_agencia")
	private Long idAgencia;

	public PlanAccionInvEjecutadoEt() {
		this.descripcion = "";
		this.idTipoInventario = 0L;
		this.idPlanAccionAnioInv = 0L;
		this.idPlanificacionInventario = 0L;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public Long getIdPlanAccionInvEjecutado() {
		return idPlanAccionInvEjecutado;
	}

	public void setIdPlanAccionInvEjecutado(Long idPlanAccionInvEjecutado) {
		this.idPlanAccionInvEjecutado = idPlanAccionInvEjecutado;
	}

	public Long getIdPlanAccionAnioInv() {
		return idPlanAccionAnioInv;
	}

	public void setIdPlanAccionAnioInv(Long idPlanAccionAnioInv) {
		this.idPlanAccionAnioInv = idPlanAccionAnioInv;
	}

	public Long getIdPlanificacionInventario() {
		return idPlanificacionInventario;
	}

	public void setIdPlanificacionInventario(Long idPlanificacionInventario) {
		this.idPlanificacionInventario = idPlanificacionInventario;
	}

	public Long getIdTipoInventario() {
		return idTipoInventario;
	}

	public void setIdTipoInventario(Long idTipoInventario) {
		this.idTipoInventario = idTipoInventario;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof PlanAccionInvEjecutadoEt) {

			PlanAccionInvEjecutadoEt other = (PlanAccionInvEjecutadoEt) obj;
			if (this.idPlanAccionInvEjecutado == null)
				return this == other;

			return this.idPlanAccionInvEjecutado.equals(other.idPlanAccionInvEjecutado);
		}
		return false;
	}

}
