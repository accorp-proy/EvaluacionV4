package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.pla.TableroInventarioEstacionZonaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ITableroInventarioEstacionZonaDao extends IGenericDao<TableroInventarioEstacionZonaEt, Long> {

	public void remove();

	public List<TableroInventarioEstacionZonaEt> getTablaList(UsuarioEt usuario) throws EntidadNoEncontradaException;

}
