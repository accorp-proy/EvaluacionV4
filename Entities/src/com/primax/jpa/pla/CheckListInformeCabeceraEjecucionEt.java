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
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CHECK_LIST_INFORME_CABECERA_EJECUCION_ET")
@Audited
public class CheckListInformeCabeceraEjecucionEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -4838501690442140136L;

	@Id
	@SequenceGenerator(name = "sec_check_list_informe_cabecera_ejecucion_et", sequenceName = "seq_check_list_informe_cabecera_ejecucion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_informe_cabecera_ejecucion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_informe_cabecera_ejecucion")
	private Long idCheckListInfCabEjecucion;

	@ManyToOne
	@JoinColumn(name = "id_check_list_ejecucion")
	private CheckListEjecucionEt checkListEjecucion;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "titulo", length = 100)
	private String titulo;

	@Column(name = "contenido", length = 200)
	private String contenido;

	public CheckListInformeCabeceraEjecucionEt() {
		this.orden = 1L;
		this.titulo = "";
		this.contenido = "";

	}

	public Long getIdCheckListInfCabEjecucion() {
		return idCheckListInfCabEjecucion;
	}

	public void setIdCheckListInfCabEjecucion(Long idCheckListInfCabEjecucion) {
		this.idCheckListInfCabEjecucion = idCheckListInfCabEjecucion;
	}

	public CheckListEjecucionEt getCheckListEjecucion() {
		return checkListEjecucion;
	}

	public void setCheckListEjecucion(CheckListEjecucionEt checkListEjecucion) {
		this.checkListEjecucion = checkListEjecucion;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof CheckListInformeCabeceraEjecucionEt) {

			CheckListInformeCabeceraEjecucionEt other = (CheckListInformeCabeceraEjecucionEt) obj;
			if (this.idCheckListInfCabEjecucion == null)
				return this == other;

			return this.idCheckListInfCabEjecucion.equals(other.idCheckListInfCabEjecucion);
		}
		return false;
	}

}
