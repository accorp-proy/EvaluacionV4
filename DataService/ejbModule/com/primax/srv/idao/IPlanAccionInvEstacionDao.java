package com.primax.srv.idao;

import java.util.List;

import com.primax.jpa.pla.PlanAccionInvEstacionEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanAccionInvEstacionDao extends IGenericDao<PlanAccionInvEstacionEt, Long> {

	public void remove();

	public List<PlanAccionInvEstacionEt> getPlanAccionMesList(Long idPlanAccionAnio, Long anio, Long mes, Long idZona);

}
