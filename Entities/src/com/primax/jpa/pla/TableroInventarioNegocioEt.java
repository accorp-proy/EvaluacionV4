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
@Table(name = "TABLERO_INVENTARIO_NEGOCIO_ET")
public class TableroInventarioNegocioEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_tablero_inventario_negocio_et", sequenceName = "seq_tablero_inventario_negocio_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_tablero_inventario_negocio_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_tablero_inventario_negocio")
	private Long idTableroInventarioNegocio;

	@Column(name = "cantidad_tienda")
	private Long cantidadTienda;

	@Column(name = "cantidad_pista")
	private Long cantidadPista;

	@Column(name = "cantidad_pista_tienda")
	private Long cantidadPistaTienda;

	public TableroInventarioNegocioEt() {
		this.cantidadPista = 0L;
		this.cantidadTienda = 0L;
		this.cantidadPistaTienda = 0L;
	}

	public Long getIdTableroInventarioNegocio() {
		return idTableroInventarioNegocio;
	}

	public void setIdTableroInventarioNegocio(Long idTableroInventarioNegocio) {
		this.idTableroInventarioNegocio = idTableroInventarioNegocio;
	}

	public Long getCantidadTienda() {
		return cantidadTienda;
	}

	public void setCantidadTienda(Long cantidadTienda) {
		this.cantidadTienda = cantidadTienda;
	}

	public Long getCantidadPista() {
		return cantidadPista;
	}

	public void setCantidadPista(Long cantidadPista) {
		this.cantidadPista = cantidadPista;
	}

	public Long getCantidadPistaTienda() {
		return cantidadPistaTienda;
	}

	public void setCantidadPistaTienda(Long cantidadPistaTienda) {
		this.cantidadPistaTienda = cantidadPistaTienda;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof TableroInventarioNegocioEt) {

			TableroInventarioNegocioEt other = (TableroInventarioNegocioEt) obj;
			if (this.idTableroInventarioNegocio == null)
				return this == other;

			return this.idTableroInventarioNegocio.equals(other.idTableroInventarioNegocio);
		}
		return false;
	}

}
