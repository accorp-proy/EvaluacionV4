package com.primax.jpa.pla;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.enums.EstadoPlanAccionInvEnum;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "PLAN_ACCION_INVENTARIO_ET")
@Audited
public class PlanAccionInventarioEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_plan_accion_inventario_et", sequenceName = "seq_plan_accion_inventario_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_plan_accion_inventario_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_plan_accion_inventario")
	private Long idPlanAccionInventario;

	@Column(name = "fecha_ejecucion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEjecucion;

	@Column(name = "fecha_inicio")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicio;

	@Column(name = "fecha_fin")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFin;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_plan_accion")
	private EstadoPlanAccionInvEnum estadoPlanAccionInv;

	@Column(name = "plan_accion")
	private boolean planAccion;

	@ManyToOne
	@JoinColumn(name = "id_planificacion_inventario")
	private PlanificacionInventarioEt planificacionInventario;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "planAccionInventario", fetch = FetchType.LAZY)
	@OrderBy("idPlanAccionInventarioTipo asc")
	@Where(clause = "estado = 'ACT'")
	private List<PlanAccionInventarioTipoEt> planAccionInventarioTipo;

	public PlanAccionInventarioEt() {
		this.planAccion = false;
		this.fechaEjecucion = null;
	}

	public Long getIdPlanAccionInventario() {
		return idPlanAccionInventario;
	}

	public void setIdPlanAccionInventario(Long idPlanAccionInventario) {
		this.idPlanAccionInventario = idPlanAccionInventario;
	}

	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public List<PlanAccionInventarioTipoEt> getPlanAccionInventarioTipo() {
		return planAccionInventarioTipo;
	}

	public void setPlanAccionInventarioTipo(List<PlanAccionInventarioTipoEt> planAccionInventarioTipo) {
		this.planAccionInventarioTipo = planAccionInventarioTipo;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public EstadoPlanAccionInvEnum getEstadoPlanAccionInv() {
		return estadoPlanAccionInv;
	}

	public void setEstadoPlanAccionInv(EstadoPlanAccionInvEnum estadoPlanAccionInv) {
		this.estadoPlanAccionInv = estadoPlanAccionInv;
	}

	public PlanificacionInventarioEt getPlanificacionInventario() {
		return planificacionInventario;
	}

	public void setPlanificacionInventario(PlanificacionInventarioEt planificacionInventario) {
		this.planificacionInventario = planificacionInventario;
	}

	public boolean isPlanAccion() {
		return planAccion;
	}

	public void setPlanAccion(boolean planAccion) {
		this.planAccion = planAccion;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PlanAccionInventarioEt) {
			PlanAccionInventarioEt other = (PlanAccionInventarioEt) obj;

			if (this.idPlanAccionInventario == null)
				return this == other;

			if (this.idPlanAccionInventario.equals(other.idPlanAccionInventario))
				return true;
		}
		return false;

	}

}
