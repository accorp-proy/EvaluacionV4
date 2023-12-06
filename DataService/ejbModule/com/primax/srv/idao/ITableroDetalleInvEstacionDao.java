package com.primax.srv.idao;

import java.util.Date;

import com.primax.jpa.pla.TableroDetalleInvEstacionEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ITableroDetalleInvEstacionDao extends IGenericDao<TableroDetalleInvEstacionEt, Long> {

	public void remove();

	public String generar(Date fechaDesde, Date fechaHasta,Long idTipoEstacion,Long idZona, Long idAgencia, Long idUsuario);

}
