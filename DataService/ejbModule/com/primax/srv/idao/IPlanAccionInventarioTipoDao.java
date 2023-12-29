package com.primax.srv.idao;

import java.util.Date;
import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoPlanAccionInvEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.pla.PlanAccionInventarioEt;
import com.primax.jpa.pla.PlanAccionInventarioTipoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanAccionInventarioTipoDao extends IGenericDao<PlanAccionInventarioTipoEt, Long> {

	public void remove();

	public PlanAccionInventarioTipoEt getTipoInventarioById(long id);

	public PlanAccionInventarioTipoEt getPlanAccionInvTipo(Long id) throws EntidadNoEncontradaException;

	public Long getPlnInvEjecutado(PlanAccionInventarioEt planAccionInv) throws EntidadNoEncontradaException;

	public PlanAccionInventarioTipoEt getPlanAccionInv(PlanAccionInventarioEt planAccionInv, TipoInventarioEt tipoInv)
			throws EntidadNoEncontradaException;

	public PlanAccionInventarioTipoEt getPlanAccionInvTipo(Long id, Long idTipoInv) throws EntidadNoEncontradaException;

	public PlanAccionInventarioTipoEt getPlanAccionInventarioTipoDif(Long idPlanAccionInventarioTipo) throws EntidadNoEncontradaException;

	public void guardarPlanAccionInvTipo(PlanAccionInventarioTipoEt planAccionInvTipo, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public List<PlanAccionInventarioTipoEt> getPlanAccionInvTipoAccesoZonaList(AgenciaEt estacion, TipoInventarioEt tipoInventario, Date fechaDesde, Date fechaHasta, EstadoPlanAccionInvEnum estadoPlanAccion, UsuarioEt usuario)
			throws EntidadNoEncontradaException;

	public List<PlanAccionInventarioTipoEt> getPlanAccionInvTipoList(ZonaEt zona, AgenciaEt estacion, TipoInventarioEt tipoInventario, Date fechaDesde, Date fechaHasta, EstadoPlanAccionInvEnum estadoPlanAccion)
			throws EntidadNoEncontradaException;
}
