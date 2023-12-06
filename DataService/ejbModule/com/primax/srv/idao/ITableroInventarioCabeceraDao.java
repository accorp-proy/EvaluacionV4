package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.pla.TableroInventarioCabeceraEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ITableroInventarioCabeceraDao extends IGenericDao<TableroInventarioCabeceraEt, Long> {

	public void remove();

	public TableroInventarioCabeceraEt getTablaCabecera(UsuarioEt usuario) throws EntidadNoEncontradaException;

}
