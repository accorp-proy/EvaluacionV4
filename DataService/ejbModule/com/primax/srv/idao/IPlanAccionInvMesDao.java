package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.PlanAccionInvMesEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanAccionInvMesDao extends IGenericDao<PlanAccionInvMesEt, Long> {

	public void remove();

	public String generar(Long idUsuario, Long anio, Long idPlanAccionAnio);

	public List<PlanAccionInvMesEt> getPlanAccionMesList(UsuarioEt usuario, Long idPlanAccionAnio, Long anio);

	public void guardarPlanAccionMes(PlanAccionInvMesEt planAccionMes, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
