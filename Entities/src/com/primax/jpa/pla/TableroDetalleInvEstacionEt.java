package com.primax.jpa.pla;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "TABLERO_DETALLE_INV_ESTACION_ET")
@Audited

@NamedStoredProcedureQuery(name = "getGenerarDetalleInvEstacion", procedureName = "fun_generar_tab_detalle_inv_estacion", resultClasses = TableroDetalleInvEstacionEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaDesde"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaHasta"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idTipoEstacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idZona"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idAgencia"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class TableroDetalleInvEstacionEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_tablero_detalle_inv_estacion_et", sequenceName = "seq_tablero_detalle_inv_estacion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_tablero_detalle_inv_estacion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_tablero_detalle_inv_estacion")
	private Long idTableroDetalleInvEstacion;

	@Column(name = "fecha_ejecucion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEjecucion;

	@Column(name = "id_planificacion_inventario")
	private Long idPlanificacionInventario;

	@Column(name = "orden")
	private Long orden;

	@Column(name = "id_zona")
	private Long idZona;

	@Column(name = "zona", length = 50)
	private String zona;

	@Column(name = "id_agencia")
	private Long idAgencia;

	@Column(name = "codigo_agencia", length = 20)
	private String codigoAgencia;

	@Column(name = "agencia", length = 100)
	private String agencia;

	@Column(name = "id_tipo_inventario")
	private Long idTipoInventario;

	@Column(name = "tipo_inventario", length = 50)
	private String tipoInventario;

	@Column(name = "mes", length = 20)
	private String mes;

	public TableroDetalleInvEstacionEt() {
		this.orden = 0L;
	}

	public Long getIdTableroDetalleInvEstacion() {
		return idTableroDetalleInvEstacion;
	}

	public void setIdTableroDetalleInvEstacion(Long idTableroDetalleInvEstacion) {
		this.idTableroDetalleInvEstacion = idTableroDetalleInvEstacion;
	}

	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public Long getIdPlanificacionInventario() {
		return idPlanificacionInventario;
	}

	public void setIdPlanificacionInventario(Long idPlanificacionInventario) {
		this.idPlanificacionInventario = idPlanificacionInventario;
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

	public Long getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Long idAgencia) {
		this.idAgencia = idAgencia;
	}

	public String getCodigoAgencia() {
		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public Long getIdTipoInventario() {
		return idTipoInventario;
	}

	public void setIdTipoInventario(Long idTipoInventario) {
		this.idTipoInventario = idTipoInventario;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getTipoInventario() {
		return tipoInventario;
	}

	public void setTipoInventario(String tipoInventario) {
		this.tipoInventario = tipoInventario;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof TableroDetalleInvEstacionEt) {

			TableroDetalleInvEstacionEt other = (TableroDetalleInvEstacionEt) obj;
			if (this.idTableroDetalleInvEstacion == null)
				return this == other;

			return this.idTableroDetalleInvEstacion.equals(other.idTableroDetalleInvEstacion);
		}
		return false;
	}

}
