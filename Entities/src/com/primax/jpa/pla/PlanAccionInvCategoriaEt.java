package com.primax.jpa.pla;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import com.primax.jpa.param.CategoriaInventarioEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "PLAN_ACCION_INV_CATEGORIA_ET")
@Audited
public class PlanAccionInvCategoriaEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_plan_accion_inv_categoria_et", sequenceName = "seq_plan_accion_inv_categoria_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_plan_accion_inv_categoria_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_plan_accion_inv_categoria")
	private Long idPlanAccionInvCategoria;

	@ManyToOne
	@JoinColumn(name = "id_plan_accion_inventario_tipo")
	private PlanAccionInventarioTipoEt planAccionInventarioTipo;

	@ManyToOne
	@JoinColumn(name = "id_categoria_inventario")
	private CategoriaInventarioEt categoriaInventario;

	@Column(name = "valor_variacion")
	private Double valorVariacion;

	@Column(name = "valor_revision")
	private Double valorRevision;

	@Column(name = "comentario_plan_accion", length = 500)
	private String comentarioPlanAccion;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "planAccionInvCategoria", fetch = FetchType.LAZY)
	@OrderBy("idPlanAccionInvCategoriaAdjunto")
	@Where(clause = "estado = 'ACT'")
	private List<PlanAccionInvCategoriaAdjuntoEt> planAccionInvCategoriaAdjunto;

	public PlanAccionInvCategoriaEt() {
		this.valorRevision = 0.0D;
		this.valorVariacion = 0.0D;
		this.comentarioPlanAccion = "";
	}

	public Long getIdPlanAccionInvCategoria() {
		return idPlanAccionInvCategoria;
	}

	public void setIdPlanAccionInvCategoria(Long idPlanAccionInvCategoria) {
		this.idPlanAccionInvCategoria = idPlanAccionInvCategoria;
	}

	public PlanAccionInventarioTipoEt getPlanAccionInventarioTipo() {
		return planAccionInventarioTipo;
	}

	public void setPlanAccionInventarioTipo(PlanAccionInventarioTipoEt planAccionInventarioTipo) {
		this.planAccionInventarioTipo = planAccionInventarioTipo;
	}

	public CategoriaInventarioEt getCategoriaInventario() {
		return categoriaInventario;
	}

	public void setCategoriaInventario(CategoriaInventarioEt categoriaInventario) {
		this.categoriaInventario = categoriaInventario;
	}

	public Double getValorVariacion() {
		return valorVariacion;
	}

	public void setValorVariacion(Double valorVariacion) {
		this.valorVariacion = valorVariacion;
	}

	public Double getValorRevision() {
		return valorRevision;
	}

	public void setValorRevision(Double valorRevision) {
		this.valorRevision = valorRevision;
	}

	public String getComentarioPlanAccion() {
		return comentarioPlanAccion;
	}

	public void setComentarioPlanAccion(String comentarioPlanAccion) {
		this.comentarioPlanAccion = comentarioPlanAccion;
	}

	public List<PlanAccionInvCategoriaAdjuntoEt> getPlanAccionInvCategoriaAdjunto() {
		return planAccionInvCategoriaAdjunto;
	}

	public void setPlanAccionInvCategoriaAdjunto(List<PlanAccionInvCategoriaAdjuntoEt> planAccionInvCategoriaAdjunto) {
		this.planAccionInvCategoriaAdjunto = planAccionInvCategoriaAdjunto;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PlanAccionInvCategoriaEt) {
			PlanAccionInvCategoriaEt other = (PlanAccionInvCategoriaEt) obj;

			if (this.idPlanAccionInvCategoria == null)
				return this == other;

			if (this.idPlanAccionInvCategoria.equals(other.idPlanAccionInvCategoria))
				return true;
		}
		return false;

	}

}
