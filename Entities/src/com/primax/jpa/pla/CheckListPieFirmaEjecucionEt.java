package com.primax.jpa.pla;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.param.TipoCargoEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CHECK_LIST_PIE_FIRMA_EJECUCION_ET")
@Audited
public class CheckListPieFirmaEjecucionEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_check_list_pie_firma_ejecucion_et", sequenceName = "seq_check_list_pie_firma_ejecucion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_pie_firma_ejecucion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_pie_firma_ejecucion")
	private Long idCheckListPieFirmaEjecucion;

	@ManyToOne
	@JoinColumn(name = "id_check_list_ejecucion")
	private CheckListEjecucionEt checkListEjecucion;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "responsable", length = 100)
	private String responsable;

	@ManyToOne
	@JoinColumn(name = "id_tipo_cargo")
	private TipoCargoEt tipoCargo;

	public CheckListPieFirmaEjecucionEt() {
		this.orden = 1L;
		this.responsable = "";
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public TipoCargoEt getTipoCargo() {
		return tipoCargo;
	}

	public void setTipoCargo(TipoCargoEt tipoCargo) {
		this.tipoCargo = tipoCargo;
	}

	public Long getIdCheckListPieFirmaEjecucion() {
		return idCheckListPieFirmaEjecucion;
	}

	public void setIdCheckListPieFirmaEjecucion(Long idCheckListPieFirmaEjecucion) {
		this.idCheckListPieFirmaEjecucion = idCheckListPieFirmaEjecucion;
	}

	public CheckListEjecucionEt getCheckListEjecucion() {
		return checkListEjecucion;
	}

	public void setCheckListEjecucion(CheckListEjecucionEt checkListEjecucion) {
		this.checkListEjecucion = checkListEjecucion;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof CheckListPieFirmaEjecucionEt) {

			CheckListPieFirmaEjecucionEt other = (CheckListPieFirmaEjecucionEt) obj;
			if (this.idCheckListPieFirmaEjecucion == null)
				return this == other;

			return this.idCheckListPieFirmaEjecucion.equals(other.idCheckListPieFirmaEjecucion);
		}
		return false;
	}

}
