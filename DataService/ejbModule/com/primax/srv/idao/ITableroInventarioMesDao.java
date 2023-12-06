package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.pla.TableroInventarioMesEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ITableroInventarioMesDao extends IGenericDao<TableroInventarioMesEt, Long> {

	public void remove();

	public List<TableroInventarioMesEt> getTablaList(UsuarioEt usuario) throws EntidadNoEncontradaException;

}
