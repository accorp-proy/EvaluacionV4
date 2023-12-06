package com.primax.jpa.pla;

import java.io.InputStream;
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
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CHECK_LIST_EJECUCION_ADJUNTO_ET")
@Audited
public class CheckListEjecucionAdjuntoEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_check_list_ejecucion_adjunto_et", sequenceName = "seq_check_list_ejecucion_adjunto_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_check_list_ejecucion_adjunto_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_check_list_ejecucion_adjunto")
	private Long idCheckListEjecucionAdjunto;

	@ManyToOne
	@JoinColumn(name = "id_check_list_ejecucion")
	private CheckListEjecucionEt checkListEjecucion;

	@Column(name = "nombre_adjunto")
	private String nombreAdjunto;

	@Column(name = "detalle")
	private String detalle;

	@ManyToOne
	@JoinColumn(name = "id_parametro_categoria")
	private ParametrosGeneralesEt parametroCategoria;

	@Transient
	private InputStream file;

	@Column(name = "imagen_path", length = 500)
	private String imagenPath;

	public String getNombreAdjunto() {
		return nombreAdjunto;
	}

	public void setNombreAdjunto(String nombreAdjunto) {
		this.nombreAdjunto = nombreAdjunto;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public InputStream getFile() {
		return file;
	}

	public void setFile(InputStream file) {
		this.file = file;
	}

	public ParametrosGeneralesEt getParametroCategoria() {
		return parametroCategoria;
	}

	public void setParametroCategoria(ParametrosGeneralesEt parametroCategoria) {
		this.parametroCategoria = parametroCategoria;
	}

	public Long getIdCheckListEjecucionAdjunto() {
		return idCheckListEjecucionAdjunto;
	}

	public void setIdCheckListEjecucionAdjunto(Long idCheckListEjecucionAdjunto) {
		this.idCheckListEjecucionAdjunto = idCheckListEjecucionAdjunto;
	}

	public CheckListEjecucionEt getCheckListEjecucion() {
		return checkListEjecucion;
	}

	public void setCheckListEjecucion(CheckListEjecucionEt checkListEjecucion) {
		this.checkListEjecucion = checkListEjecucion;
	}

	public String getImagenPath() {
		return imagenPath;
	}

	public void setImagenPath(String imagenPath) {
		this.imagenPath = imagenPath;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CheckListEjecucionAdjuntoEt) {
			CheckListEjecucionAdjuntoEt other = (CheckListEjecucionAdjuntoEt) obj;

			if (this.idCheckListEjecucionAdjunto == null)
				return this == other;

			if (this.idCheckListEjecucionAdjunto.equals(other.idCheckListEjecucionAdjunto))
				return true;
		}
		return false;

	}

}
