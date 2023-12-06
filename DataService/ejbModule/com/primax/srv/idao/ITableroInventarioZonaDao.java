package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.pla.TableroInventarioZonaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ITableroInventarioZonaDao extends IGenericDao<TableroInventarioZonaEt, Long> {

	public void remove();

	public String generar(Long idUsuario);

	public List<TableroInventarioZonaEt> getTablaList(UsuarioEt usuario) throws EntidadNoEncontradaException;

}
