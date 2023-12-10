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

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "REPORTE_TIPO_EVALUACION_CONSOLIDADO_ET")
@Audited

@NamedStoredProcedureQuery(name = "getGenerarReporteTipoEvaluacionCons", procedureName = "fun_generar_rpt_tipo_evaluacion_consolidado", resultClasses = ReporteTipoEvaluacionConsolidadoEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaDesde"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaHasta"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idZona"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idEstacion"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class ReporteTipoEvaluacionConsolidadoEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_reporte_tipo_evaluacion_consolidado_et", sequenceName = "seq_reporte_tipo_evaluacion_consolidado_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_reporte_tipo_evaluacion_consolidado_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_reporte_tipo_evaluacion_consolidado")
	private Long idReporteTipoEvaluacioConsolidado;

	@Column(name = "zona", length = 50)
	private String zona;

	@Column(name = "id_agencia")
	private Long idAgencia;

	@Column(name = "id_zona")
	private Long idZona;

	@Column(name = "promedio")
	private Double promedio;

	@Column(name = "cantidad")
	private Long cantidad;

	@Column(name = "total")
	private Double total;

	@Column(name = "codigo_agencia", length = 20)
	private String codigoAgencia;

	@Column(name = "nombre_agencia", length = 100)
	private String nombreAgencia;

	@Column(name = "mes_01")
	private Double mes01;

	@Column(name = "mes_02")
	private Double mes02;

	@Column(name = "mes_03")
	private Double mes03;

	@Column(name = "mes_04")
	private Double mes04;

	@Column(name = "mes_05")
	private Double mes05;

	@Column(name = "mes_06")
	private Double mes06;

	@Column(name = "mes_07")
	private Double mes07;

	@Column(name = "mes_08")
	private Double mes08;

	@Column(name = "mes_09")
	private Double mes09;

	@Column(name = "mes_10")
	private Double mes10;

	@Column(name = "mes_11")
	private Double mes11;

	@Column(name = "mes_12")
	private Double mes12;

	@Column(name = "mes_01_a")
	private Double mes01A;

	@Column(name = "mes_02_a")
	private Double mes02A;

	@Column(name = "mes_03_a")
	private Double mes03A;

	@Column(name = "mes_04_a")
	private Double mes04A;

	@Column(name = "mes_05_a")
	private Double mes05A;

	@Column(name = "mes_06_a")
	private Double mes06A;

	@Column(name = "mes_07_a")
	private Double mes07A;

	@Column(name = "mes_08_a")
	private Double mes08A;

	@Column(name = "mes_09_a")
	private Double mes09A;

	@Column(name = "mes_10_a")
	private Double mes10A;

	@Column(name = "mes_11_a")
	private Double mes11A;

	@Column(name = "mes_12_a")
	private Double mes12A;

	@Column(name = "mes_01_t")
	private Long mes01T;

	@Column(name = "mes_02_t")
	private Long mes02T;

	@Column(name = "mes_03_t")
	private Long mes03T;

	@Column(name = "mes_04_t")
	private Long mes04T;

	@Column(name = "mes_05_t")
	private Long mes05T;

	@Column(name = "mes_06_t")
	private Long mes06T;

	@Column(name = "mes_07_t")
	private Long mes07T;

	@Column(name = "mes_08_t")
	private Long mes08T;

	@Column(name = "mes_09_t")
	private Long mes09T;

	@Column(name = "mes_10_t")
	private Long mes10T;

	@Column(name = "mes_11_t")
	private Long mes11T;

	@Column(name = "mes_12_t")
	private Long mes12T;

	@Column(name = "mes_01_c")
	private String mes01C;

	@Column(name = "mes_02_c")
	private String mes02C;

	@Column(name = "mes_03_c")
	private String mes03C;

	@Column(name = "mes_04_c")
	private String mes04C;

	@Column(name = "mes_05_c")
	private String mes05C;

	@Column(name = "mes_06_c")
	private String mes06C;

	@Column(name = "mes_07_c")
	private String mes07C;

	@Column(name = "mes_08_c")
	private String mes08C;

	@Column(name = "mes_09_c")
	private String mes09C;

	@Column(name = "mes_10_c")
	private String mes10C;

	@Column(name = "mes_11_c")
	private String mes11C;

	@Column(name = "mes_12_c")
	private String mes12C;

	public ReporteTipoEvaluacionConsolidadoEt() {
		this.mes01 = 0.0D;
		this.mes02 = 0.0D;
		this.mes03 = 0.0D;
		this.mes04 = 0.0D;
		this.mes05 = 0.0D;
		this.mes06 = 0.0D;
		this.mes07 = 0.0D;
		this.mes08 = 0.0D;
		this.mes09 = 0.0D;
		this.mes10 = 0.0D;
		this.mes11 = 0.0D;
		this.mes12 = 0.0D;
		this.idAgencia = 0L;
		this.promedio = 0.0D;
	}

	public Long getIdReporteTipoEvaluacioConsolidado() {
		return idReporteTipoEvaluacioConsolidado;
	}

	public void setIdReporteTipoEvaluacioConsolidado(Long idReporteTipoEvaluacioConsolidado) {
		this.idReporteTipoEvaluacioConsolidado = idReporteTipoEvaluacioConsolidado;
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

	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	public Double getPromedio() {
		return promedio;
	}

	public void setPromedio(Double promedio) {
		this.promedio = promedio;
	}

	public String getCodigoAgencia() {
		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}

	public String getNombreAgencia() {
		return nombreAgencia;
	}

	public void setNombreAgencia(String nombreAgencia) {
		this.nombreAgencia = nombreAgencia;
	}

	public Double getMes01() {
		return mes01;
	}

	public void setMes01(Double mes01) {
		this.mes01 = mes01;
	}

	public Double getMes02() {
		return mes02;
	}

	public void setMes02(Double mes02) {
		this.mes02 = mes02;
	}

	public Double getMes03() {
		return mes03;
	}

	public void setMes03(Double mes03) {
		this.mes03 = mes03;
	}

	public Double getMes04() {
		return mes04;
	}

	public void setMes04(Double mes04) {
		this.mes04 = mes04;
	}

	public Double getMes05() {
		return mes05;
	}

	public void setMes05(Double mes05) {
		this.mes05 = mes05;
	}

	public Double getMes06() {
		return mes06;
	}

	public void setMes06(Double mes06) {
		this.mes06 = mes06;
	}

	public Double getMes07() {
		return mes07;
	}

	public void setMes07(Double mes07) {
		this.mes07 = mes07;
	}

	public Double getMes08() {
		return mes08;
	}

	public void setMes08(Double mes08) {
		this.mes08 = mes08;
	}

	public Double getMes09() {
		return mes09;
	}

	public void setMes09(Double mes09) {
		this.mes09 = mes09;
	}

	public Double getMes10() {
		return mes10;
	}

	public void setMes10(Double mes10) {
		this.mes10 = mes10;
	}

	public Double getMes11() {
		return mes11;
	}

	public void setMes11(Double mes11) {
		this.mes11 = mes11;
	}

	public Double getMes12() {
		return mes12;
	}

	public void setMes12(Double mes12) {
		this.mes12 = mes12;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getMes01C() {
		return mes01C;
	}

	public void setMes01C(String mes01c) {
		mes01C = mes01c;
	}

	public String getMes02C() {
		return mes02C;
	}

	public void setMes02C(String mes02c) {
		mes02C = mes02c;
	}

	public String getMes03C() {
		return mes03C;
	}

	public void setMes03C(String mes03c) {
		mes03C = mes03c;
	}

	public String getMes04C() {
		return mes04C;
	}

	public void setMes04C(String mes04c) {
		mes04C = mes04c;
	}

	public String getMes05C() {
		return mes05C;
	}

	public void setMes05C(String mes05c) {
		mes05C = mes05c;
	}

	public String getMes06C() {
		return mes06C;
	}

	public void setMes06C(String mes06c) {
		mes06C = mes06c;
	}

	public String getMes07C() {
		return mes07C;
	}

	public void setMes07C(String mes07c) {
		mes07C = mes07c;
	}

	public String getMes08C() {
		return mes08C;
	}

	public void setMes08C(String mes08c) {
		mes08C = mes08c;
	}

	public String getMes09C() {
		return mes09C;
	}

	public void setMes09C(String mes09c) {
		mes09C = mes09c;
	}

	public String getMes10C() {
		return mes10C;
	}

	public void setMes10C(String mes10c) {
		mes10C = mes10c;
	}

	public String getMes11C() {
		return mes11C;
	}

	public void setMes11C(String mes11c) {
		mes11C = mes11c;
	}

	public String getMes12C() {
		return mes12C;
	}

	public void setMes12C(String mes12c) {
		mes12C = mes12c;
	}

	public Long getMes01T() {
		return mes01T;
	}

	public void setMes01T(Long mes01t) {
		mes01T = mes01t;
	}

	public Long getMes02T() {
		return mes02T;
	}

	public void setMes02T(Long mes02t) {
		mes02T = mes02t;
	}

	public Long getMes03T() {
		return mes03T;
	}

	public void setMes03T(Long mes03t) {
		mes03T = mes03t;
	}

	public Long getMes04T() {
		return mes04T;
	}

	public void setMes04T(Long mes04t) {
		mes04T = mes04t;
	}

	public Long getMes05T() {
		return mes05T;
	}

	public void setMes05T(Long mes05t) {
		mes05T = mes05t;
	}

	public Long getMes06T() {
		return mes06T;
	}

	public void setMes06T(Long mes06t) {
		mes06T = mes06t;
	}

	public Long getMes07T() {
		return mes07T;
	}

	public void setMes07T(Long mes07t) {
		mes07T = mes07t;
	}

	public Long getMes08T() {
		return mes08T;
	}

	public void setMes08T(Long mes08t) {
		mes08T = mes08t;
	}

	public Long getMes09T() {
		return mes09T;
	}

	public void setMes09T(Long mes09t) {
		mes09T = mes09t;
	}

	public Long getMes10T() {
		return mes10T;
	}

	public void setMes10T(Long mes10t) {
		mes10T = mes10t;
	}

	public Long getMes11T() {
		return mes11T;
	}

	public void setMes11T(Long mes11t) {
		mes11T = mes11t;
	}

	public Long getMes12T() {
		return mes12T;
	}

	public void setMes12T(Long mes12t) {
		mes12T = mes12t;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ReporteTipoEvaluacionConsolidadoEt) {

			ReporteTipoEvaluacionConsolidadoEt other = (ReporteTipoEvaluacionConsolidadoEt) obj;
			if (this.idReporteTipoEvaluacioConsolidado == null)
				return this == other;

			return this.idReporteTipoEvaluacioConsolidado.equals(other.idReporteTipoEvaluacioConsolidado);
		}
		return false;
	}

}
