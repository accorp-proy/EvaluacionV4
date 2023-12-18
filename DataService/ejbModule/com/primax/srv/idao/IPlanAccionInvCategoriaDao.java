package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.PlanAccionInvCategoriaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanAccionInvCategoriaDao extends IGenericDao<PlanAccionInvCategoriaEt, Long> {

	public void remove();

	public PlanAccionInvCategoriaEt getPlanAccionInvCategoriaById(long id);

	public void guardarPlanAccionInvCategoria(PlanAccionInvCategoriaEt planAccionInvCategoria, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
