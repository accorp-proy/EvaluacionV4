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
@Table(name = "TABLERO_INVENTARIO_CABECERA_ET")
public class TableroInventarioCabeceraEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_tablero_inventario_cabecera_et", sequenceName = "seq_tablero_inventario_cabecera_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_tablero_inventario_cabecera_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_tablero_inventario_cabecera")
	private Long idTableroInventarioCabecera;

	@Column(name = "evaluacion_01", length = 100)
	private String evaluacion01;

	@Column(name = "evaluacion_02", length = 100)
	private String evaluacion02;

	@Column(name = "evaluacion_03", length = 100)
	private String evaluacion03;

	@Column(name = "evaluacion_04", length = 100)
	private String evaluacion04;

	@Column(name = "evaluacion_05", length = 100)
	private String evaluacion05;

	@Column(name = "evaluacion_06", length = 100)
	private String evaluacion06;

	@Column(name = "evaluacion_07", length = 100)
	private String evaluacion07;

	@Column(name = "evaluacion_08", length = 100)
	private String evaluacion08;

	@Column(name = "numero_tienda")
	private Long numeroTienda;

	public TableroInventarioCabeceraEt() {
		this.numeroTienda = 0L;
		this.evaluacion01 = "0";
		this.evaluacion02 = "0";
		this.evaluacion03 = "0";
		this.evaluacion04 = "0";
		this.evaluacion05 = "0";
		this.evaluacion06 = "0";
		this.evaluacion07 = "0";
	}

	public Long getIdTableroInventarioCabecera() {
		return idTableroInventarioCabecera;
	}

	public void setIdTableroInventarioCabecera(Long idTableroInventarioCabecera) {
		this.idTableroInventarioCabecera = idTableroInventarioCabecera;
	}

	public String getEvaluacion01() {
		return evaluacion01;
	}

	public void setEvaluacion01(String evaluacion01) {
		this.evaluacion01 = evaluacion01;
	}

	public String getEvaluacion02() {
		return evaluacion02;
	}

	public void setEvaluacion02(String evaluacion02) {
		this.evaluacion02 = evaluacion02;
	}

	public String getEvaluacion03() {
		return evaluacion03;
	}

	public void setEvaluacion03(String evaluacion03) {
		this.evaluacion03 = evaluacion03;
	}

	public String getEvaluacion04() {
		return evaluacion04;
	}

	public void setEvaluacion04(String evaluacion04) {
		this.evaluacion04 = evaluacion04;
	}

	public String getEvaluacion05() {
		return evaluacion05;
	}

	public void setEvaluacion05(String evaluacion05) {
		this.evaluacion05 = evaluacion05;
	}

	public String getEvaluacion06() {
		return evaluacion06;
	}

	public void setEvaluacion06(String evaluacion06) {
		this.evaluacion06 = evaluacion06;
	}

	public String getEvaluacion07() {
		return evaluacion07;
	}

	public void setEvaluacion07(String evaluacion07) {
		this.evaluacion07 = evaluacion07;
	}

	public String getEvaluacion08() {
		return evaluacion08;
	}

	public void setEvaluacion08(String evaluacion08) {
		this.evaluacion08 = evaluacion08;
	}

	public Long getNumeroTienda() {
		return numeroTienda;
	}

	public void setNumeroTienda(Long numeroTienda) {
		this.numeroTienda = numeroTienda;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof TableroInventarioCabeceraEt) {

			TableroInventarioCabeceraEt other = (TableroInventarioCabeceraEt) obj;
			if (this.idTableroInventarioCabecera == null)
				return this == other;

			return this.idTableroInventarioCabecera.equals(other.idTableroInventarioCabecera);
		}
		return false;
	}

}
