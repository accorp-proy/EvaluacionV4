package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.PlanAccionInvCategoriaAdjuntoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanAccionInvCategoriaAdjuntoDao extends IGenericDao<PlanAccionInvCategoriaAdjuntoEt, Long> {

	public void remove();

	public PlanAccionInvCategoriaAdjuntoEt getPlnAccionInvCategoriaAdjById(long id);

	public void guardarPlnAccionInvCategoriaAdj(PlanAccionInvCategoriaAdjuntoEt plnAccionInvCategoriaAdj, UsuarioEt usuario)
			throws EntidadNoGrabadaException;

}
