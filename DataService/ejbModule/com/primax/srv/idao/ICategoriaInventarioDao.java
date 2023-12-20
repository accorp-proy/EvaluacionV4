package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.CategoriaInventarioEt;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICategoriaInventarioDao extends IGenericDao<CategoriaInventarioEt, Long> {

	public void remove();

	public String limpiarReporte(Long idUsuario);

	public CategoriaInventarioEt getCategoriaInventario(long id);

	public Long getOrdenCategoriaInv() throws EntidadNoEncontradaException;

	public String getCodigoCategoriaInv() throws EntidadNoEncontradaException;

	public List<CategoriaInventarioEt> getCategoriaInventarioList(String condicion) throws EntidadNoEncontradaException;

	public List<CategoriaInventarioEt> getCategoriaInvByTipoInv(TipoInventarioEt tipoInventario) throws EntidadNoEncontradaException;

	public void guardarCategoriaInventario(CategoriaInventarioEt categoriaInventario, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
