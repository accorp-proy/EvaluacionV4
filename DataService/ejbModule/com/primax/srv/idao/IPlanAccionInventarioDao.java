package com.primax.srv.idao;

import java.util.Date;
import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.pla.PlanAccionInventarioEt;
import com.primax.jpa.pla.PlanificacionInventarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanAccionInventarioDao extends IGenericDao<PlanAccionInventarioEt, Long> {

	public void remove();

	public PlanAccionInventarioEt getPlanAccionInventarioById(long id);

	public PlanAccionInventarioEt getPlanAccionInv(AgenciaEt agencia) throws EntidadNoEncontradaException;

	public List<PlanAccionInventarioEt> getPlanAccionInvByAgencia(AgenciaEt agencia) throws EntidadNoEncontradaException;

	public PlanAccionInventarioEt getPlanAccionInvExiste(PlanificacionInventarioEt planificacionInv) throws EntidadNoEncontradaException;

	public List<PlanAccionInventarioEt> getPlanAccionInventarioList(Date fechaDesde, Date fechaHasta) throws EntidadNoEncontradaException;

	public void guardarPlanAccionInventario(PlanAccionInventarioEt PlanAccionInventario, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public List<PlanAccionInventarioEt> getPlanAccionInventarioList(Date fechaDesde, Date fechaHasta, AgenciaEt agencia)
			throws EntidadNoEncontradaException;

}
