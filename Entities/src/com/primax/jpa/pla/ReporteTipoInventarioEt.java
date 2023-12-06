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
@Table(name = "REPORTE_TIPO_INVENTARIO_ET")
@Audited

@NamedStoredProcedureQuery(name = "getGenerarReporteTipoInventario", procedureName = "fun_generar_rpt_tipo_inventario", resultClasses = ReporteTipoInventarioEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaDesde"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaHasta"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idZona"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idTipoInventario"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idAgencia"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class ReporteTipoInventarioEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_reporte_tipo_inventario_et", sequenceName = "seq_reporte_tipo_inventario_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_reporte_tipo_inventario_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_reporte_tipo_inventario")
	private Long idReporteTipoInventario;

	@Column(name = "fecha_ejecucion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEjecucion;

	@Column(name = "id_planificacion_inventario")
	private Long idPlanificacionInventario;

	@Column(name = "zona", length = 50)
	private String zona;

	@Column(name = "nombre_agencia", length = 100)
	private String nombreAgencia;

	@Column(name = "tipo_inventario", length = 50)
	private String tipoInventario;

	public ReporteTipoInventarioEt() {
		this.idPlanificacionInventario = 0L;
	}

	public Long getIdReporteTipoInventario() {
		return idReporteTipoInventario;
	}

	public void setIdReporteTipoInventario(Long idReporteTipoInventario) {
		this.idReporteTipoInventario = idReporteTipoInventario;
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

	public String getNombreAgencia() {
		return nombreAgencia;
	}

	public void setNombreAgencia(String nombreAgencia) {
		this.nombreAgencia = nombreAgencia;
	}

	public String getTipoInventario() {
		return tipoInventario;
	}

	public void setTipoInventario(String tipoInventario) {
		this.tipoInventario = tipoInventario;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ReporteTipoInventarioEt) {

			ReporteTipoInventarioEt other = (ReporteTipoInventarioEt) obj;
			if (this.idReporteTipoInventario == null)
				return this == other;

			return this.idReporteTipoInventario.equals(other.idReporteTipoInventario);
		}
		return false;
	}

}
