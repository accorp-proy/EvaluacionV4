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
@Table(name = "PLAN_ACCION_INV_MES_ET")

@NamedStoredProcedureQuery(name = "getGenerarOrgPlnInvMes", procedureName = "fun_generar_org_pln_inv_mes", resultClasses = PlanAccionInvMesEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "anio"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idPlanAccionInvAnio"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class PlanAccionInvMesEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_plan_accion_inv_mes_et", sequenceName = "seq_plan_accion_inv_mes_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_plan_accion_inv_mes_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_plan_accion_inv_mes")
	private Long idPlanAccionInvMes;

	@Column(name = "id_plan_accion_inv_anio")
	private Long idPlanAccionInvAnio;

	@Column(name = "mes_numero")
	private Long mesNumero;

	@Column(name = "mes_letra", length = 20)
	private String mesLetra;

	@Column(name = "anio")
	private Long anio;

	public PlanAccionInvMesEt() {
		this.anio = 0L;
		this.mesLetra = "";
		this.mesNumero = 0L;
		this.idPlanAccionInvAnio = 0L;
	}

	public Long getMesNumero() {
		return mesNumero;
	}

	public void setMesNumero(Long mesNumero) {
		this.mesNumero = mesNumero;
	}

	public String getMesLetra() {
		return mesLetra;
	}

	public void setMesLetra(String mesLetra) {
		this.mesLetra = mesLetra;
	}

	public Long getAnio() {
		return anio;
	}

	public void setAnio(Long anio) {
		this.anio = anio;
	}

	public Long getIdPlanAccionInvMes() {
		return idPlanAccionInvMes;
	}

	public void setIdPlanAccionInvMes(Long idPlanAccionInvMes) {
		this.idPlanAccionInvMes = idPlanAccionInvMes;
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

		if (obj instanceof PlanAccionInvMesEt) {

			PlanAccionInvMesEt other = (PlanAccionInvMesEt) obj;
			if (this.idPlanAccionInvMes == null)
				return this == other;

			return this.idPlanAccionInvMes.equals(other.idPlanAccionInvMes);
		}
		return false;
	}

}
