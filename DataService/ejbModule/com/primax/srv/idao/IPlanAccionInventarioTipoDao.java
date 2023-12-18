package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.pla.PlanAccionInventarioEt;
import com.primax.jpa.pla.PlanAccionInventarioTipoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanAccionInventarioTipoDao extends IGenericDao<PlanAccionInventarioTipoEt, Long> {

	public void remove();

	public PlanAccionInventarioTipoEt getTipoInventarioById(long id);

	public Long getPlnInvEjecutado(PlanAccionInventarioEt planAccionInv) throws EntidadNoEncontradaException;

	public PlanAccionInventarioTipoEt getPlanAccionInv(PlanAccionInventarioEt planAccionInv, TipoInventarioEt tipoInv)
			throws EntidadNoEncontradaException;

	public void guardarPlanAccionInvTipo(PlanAccionInventarioTipoEt planAccionInvTipo, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
