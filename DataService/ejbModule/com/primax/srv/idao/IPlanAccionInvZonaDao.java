package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.PlanAccionInvZonaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanAccionInvZonaDao extends IGenericDao<PlanAccionInvZonaEt, Long> {

	public void remove();

	public String generar(Long idUsuario, Long anio, Long idPlanAccionInvAnio, Long mes);

	public List<PlanAccionInvZonaEt> getPlanAccionZonaList(Long idPlanAccionInvAnio, Long anio, Long mes);

	public void guardarPlanAccionMes(PlanAccionInvZonaEt planAccionZona, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
