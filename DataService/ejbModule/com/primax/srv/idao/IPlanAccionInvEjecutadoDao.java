package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.PlanAccionInvEjecutadoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanAccionInvEjecutadoDao extends IGenericDao<PlanAccionInvEjecutadoEt, Long> {

	public void remove();

	public void guardarPlanAccionInvEjecutado(PlanAccionInvEjecutadoEt planAccionInvEjecutado, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public List<PlanAccionInvEjecutadoEt> getPlanAccionInvEjecutadoList(Long idPlanAccionAnioInv, Long anio, Long mes, Long idZona, Long idAgencia);

}
