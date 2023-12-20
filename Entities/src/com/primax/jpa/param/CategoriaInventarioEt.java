package com.primax.jpa.param;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OrderBy;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CATEGORIA_INVENTARIO_ET")
@Audited

@NamedStoredProcedureQuery(name = "getReporteOrgPlnInvAnio", procedureName = "fun_limpiar_rpt_org_pla_inv", resultClasses = FrecuenciaVisitaEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class CategoriaInventarioEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_categoria_inventario_et", sequenceName = "seq_categoria_inventario_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_categoria_inventario_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_categoria_inventario")
	private Long idCategoriaInventario;

	@Column(name = "orden")
	private Long orden;

	@ManyToOne
	@JoinColumn(name = "id_tipo_inventario")
	private TipoInventarioEt tipoInventario;

	@Column(name = "descripcion", length = 100)
	@OrderBy("order by descripcion")
	private String descripcion;

	@Column(name = "codigo", length = 10)
	private String codigo;

	public CategoriaInventarioEt() {
		this.orden = 0L;
	}

	public Long getIdCategoriaInventario() {
		return idCategoriaInventario;
	}

	public void setIdCategoriaInventario(Long idCategoriaInventario) {
		this.idCategoriaInventario = idCategoriaInventario;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public TipoInventarioEt getTipoInventario() {
		return tipoInventario;
	}

	public void setTipoInventario(TipoInventarioEt tipoInventario) {
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
		if (obj instanceof CategoriaInventarioEt) {
			CategoriaInventarioEt other = (CategoriaInventarioEt) obj;

			if (this.idCategoriaInventario == null)
				return this == other;

			if (this.idCategoriaInventario.equals(other.idCategoriaInventario))
				return true;
		}
		return false;

	}

}
