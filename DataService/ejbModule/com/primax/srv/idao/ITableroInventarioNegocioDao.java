package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.TableroInventarioNegocioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ITableroInventarioNegocioDao extends IGenericDao<TableroInventarioNegocioEt, Long> {

	public void remove();

	public TableroInventarioNegocioEt getTablaList(UsuarioEt usuario) throws EntidadNoEncontradaException;

	public void guardarTableroInventarioNegocio(TableroInventarioNegocioEt tableroInventarioNegocio, UsuarioEt usuario)
			throws EntidadNoGrabadaException;

}
