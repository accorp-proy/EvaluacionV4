package com.primax.jpa.pla;

import java.io.Serializable;
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

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.enums.EstadoPlanAccionInvEnum;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "PLAN_ACCION_INVENTARIO_TIPO_ET")
@Audited
public class PlanAccionInventarioTipoEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_plan_accion_inventario_tipo_et", sequenceName = "seq_plan_accion_inventario_tipo_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_plan_accion_inventario_tipo_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_plan_accion_inventario_tipo")
	private Long idPlanAccionInventarioTipo;

	@ManyToOne
	@JoinColumn(name = "id_plan_accion_inventario")
	private PlanAccionInventarioEt planAccionInventario;

	@ManyToOne
	@JoinColumn(name = "id_planificacion_inventario_tipo")
	private PlanificacionInventarioTipoEt planificacionInventarioTipo;

	@ManyToOne
	@JoinColumn(name = "id_tipo_inventario")
	private TipoInventarioEt tipoInventario;

	@Column(name = "ejecutado")
	private boolean ejecutado;

	@Column(name = "en_ejecucion")
	private boolean enEjecucion;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_plan_accion")
	private EstadoPlanAccionInvEnum estadoPlanAccionInv;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "planAccionInventarioTipo", fetch = FetchType.LAZY)
	@OrderBy("idPlanAccionInvCategoria asc")
	@Where(clause = "estado = 'ACT'")
	private List<PlanAccionInvCategoriaEt> planAccionInvCategoria;

	public PlanAccionInventarioTipoEt() {
		this.ejecutado = false;
		this.enEjecucion = false;
	}

	public TipoInventarioEt getTipoInventario() {
		return tipoInventario;
	}

	public void setTipoInventario(TipoInventarioEt tipoInventario) {
		this.tipoInventario = tipoInventario;
	}

	public boolean isEjecutado() {
		return ejecutado;
	}

	public void setEjecutado(boolean ejecutado) {
		this.ejecutado = ejecutado;
	}

	public Long getIdPlanAccionInventarioTipo() {
		return idPlanAccionInventarioTipo;
	}

	public void setIdPlanAccionInventarioTipo(Long idPlanAccionInventarioTipo) {
		this.idPlanAccionInventarioTipo = idPlanAccionInventarioTipo;
	}

	public PlanAccionInventarioEt getPlanAccionInventario() {
		return planAccionInventario;
	}

	public void setPlanAccionInventario(PlanAccionInventarioEt planAccionInventario) {
		this.planAccionInventario = planAccionInventario;
	}

	public EstadoPlanAccionInvEnum getEstadoPlanAccionInv() {
		return estadoPlanAccionInv;
	}

	public void setEstadoPlanAccionInv(EstadoPlanAccionInvEnum estadoPlanAccionInv) {
		this.estadoPlanAccionInv = estadoPlanAccionInv;
	}

	public List<PlanAccionInvCategoriaEt> getPlanAccionInvCategoria() {
		return planAccionInvCategoria;
	}

	public void setPlanAccionInvCategoria(List<PlanAccionInvCategoriaEt> planAccionInvCategoria) {
		this.planAccionInvCategoria = planAccionInvCategoria;
	}

	public boolean isEnEjecucion() {
		return enEjecucion;
	}

	public void setEnEjecucion(boolean enEjecucion) {
		this.enEjecucion = enEjecucion;
	}

	public PlanificacionInventarioTipoEt getPlanificacionInventarioTipo() {
		return planificacionInventarioTipo;
	}

	public void setPlanificacionInventarioTipo(PlanificacionInventarioTipoEt planificacionInventarioTipo) {
		this.planificacionInventarioTipo = planificacionInventarioTipo;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PlanAccionInventarioTipoEt) {
			PlanAccionInventarioTipoEt other = (PlanAccionInventarioTipoEt) obj;

			if (this.idPlanAccionInventarioTipo == null)
				return this == other;

			if (this.idPlanAccionInventarioTipo.equals(other.idPlanAccionInventarioTipo))
				return true;
		}
		return false;

	}

}
