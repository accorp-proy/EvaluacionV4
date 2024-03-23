package com.primax.srv.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.enums.EstadoPlanAccionInvEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.jpa.pla.PlanAccionInvCategoriaEt;
import com.primax.jpa.pla.PlanAccionInventarioEt;
import com.primax.jpa.pla.PlanAccionInventarioTipoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IPlanAccionInventarioTipoDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class PlanAccionInventarioTipoDao extends GenericDao<PlanAccionInventarioTipoEt, Long> implements IPlanAccionInventarioTipoDao {

	public PlanAccionInventarioTipoDao() {
		super(PlanAccionInventarioTipoEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarPlanAccionInvTipo(PlanAccionInventarioTipoEt planAccionInvTipo, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (planAccionInvTipo.getIdPlanAccionInventarioTipo() == null) {
			planAccionInvTipo.audit(usuario, ActionAuditedEnum.NEW);
			crear(planAccionInvTipo);
		} else {
			planAccionInvTipo.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(planAccionInvTipo);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PlanAccionInventarioTipoEt getPlanAccionInvTipo(Long id, Long idTipoInv) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM PlanAccionInventarioTipoEt o ");
		sql.append(" WHERE o.estado = :estado ");
		sql.append(" AND o.tipoInventario.idTipoInventario = :idTipoInv ");
		sql.append(" AND o.planificacionInventarioTipo.idPlanificacionInventarioTipo = :id ");
		TypedQuery<PlanAccionInventarioTipoEt> query = em.createQuery(sql.toString(), PlanAccionInventarioTipoEt.class);
		query.setParameter("id", id);
		query.setParameter("idTipoInv", idTipoInv);
		query.setParameter("estado", EstadoEnum.ACT);
		List<PlanAccionInventarioTipoEt> result = query.getResultList();
		return getUnique(result);
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PlanAccionInventarioTipoEt getPlanAccionInvTipo(Long id) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM PlanAccionInventarioTipoEt o ");
		sql.append(" WHERE o.estado = :estado ");
		sql.append(" AND o.planificacionInventarioTipo.idPlanificacionInventarioTipo = :id ");
		TypedQuery<PlanAccionInventarioTipoEt> query = em.createQuery(sql.toString(), PlanAccionInventarioTipoEt.class);
		query.setParameter("id", id);
		query.setParameter("estado", EstadoEnum.ACT);
		List<PlanAccionInventarioTipoEt> result = query.getResultList();
		return getUnique(result);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PlanAccionInventarioTipoEt getPlanAccionInv(PlanAccionInventarioEt planAccionInv, TipoInventarioEt tipoInv)
			throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM PlanAccionInventarioTipoEt o ");
		sql.append(" WHERE o.estado  = :estado ");
		sql.append(" AND o.tipoInventario  = :tipoInventario ");
		sql.append(" AND o.planAccionInventario  = :planAccionInv ");
		TypedQuery<PlanAccionInventarioTipoEt> query = em.createQuery(sql.toString(), PlanAccionInventarioTipoEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("tipoInventario", tipoInv);
		query.setParameter("planAccionInv", planAccionInv);
		List<PlanAccionInventarioTipoEt> result = query.getResultList();
		return getUnique(result);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long getPlnInvEjecutado(PlanAccionInventarioEt planAccionInv) throws EntidadNoEncontradaException {
		Long ejecutada = 0L;
		sql = new StringBuilder("FROM PlanAccionInventarioTipoEt o ");
		sql.append(" WHERE o.estado  = :estado ");
		sql.append(" AND o.ejecutado = 'true' ");
		sql.append(" AND o.planAccionInventario  = :planAccionInv ");
		TypedQuery<PlanAccionInventarioTipoEt> query = em.createQuery(sql.toString(), PlanAccionInventarioTipoEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("planAccionInv", planAccionInv);
		List<PlanAccionInventarioTipoEt> result = query.getResultList();
		if (getUnique(result) != null) {
			ejecutada = (long) result.size();
		}
		return ejecutada;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PlanAccionInventarioTipoEt getTipoInventarioById(long id) {
		try {
			PlanAccionInventarioTipoEt tipoInventario = recuperar(id);
			return tipoInventario;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PlanAccionInventarioTipoEt getPlanAccionInventarioTipoDif(Long idPlanAccionInventarioTipo) throws EntidadNoEncontradaException {
		List<PlanAccionInvCategoriaEt> planAccionInvCategorias = new ArrayList<PlanAccionInvCategoriaEt>();
		sql = new StringBuilder("FROM PlanAccionInventarioTipoEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND o.estadoPlanAccionInv = :estadoPlanAccion ");
		sql.append(" AND o.idPlanAccionInventarioTipo = :idPlanAccionInventarioTipo ");
		sql.append(" ORDER BY  o.fechaRegistro desc ");
		TypedQuery<PlanAccionInventarioTipoEt> query = em.createQuery(sql.toString(), PlanAccionInventarioTipoEt.class);
		query.setParameter("idPlanAccionInventarioTipo", idPlanAccionInventarioTipo);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("estadoPlanAccion", EstadoPlanAccionInvEnum.INGRESADO);
		List<PlanAccionInventarioTipoEt> result = query.getResultList();
		PlanAccionInventarioTipoEt consultado = getUnique(result);
		if (consultado != null) {
			consultado.getPlanAccionInvCategoria().size();
			for (PlanAccionInvCategoriaEt planAccionInvCategoria : consultado.getPlanAccionInvCategoria()) {
				Double valVariacion = planAccionInvCategoria.getValorVariacion();
				Double valRevision = planAccionInvCategoria.getValorRevision();
				if (valVariacion == valRevision) {
					planAccionInvCategorias.add(planAccionInvCategoria);
				}
			}
			consultado.getPlanAccionInvCategoria().removeAll(planAccionInvCategorias);
		}
		return consultado;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PlanAccionInventarioTipoEt> getPlanAccionInvTipoAccesoZonaList(AgenciaEt estacion, TipoInventarioEt tipoInventario, Date fechaDesde, Date fechaHasta, EstadoPlanAccionInvEnum estadoPlanAccion, UsuarioEt usuario)
			throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM PlanAccionInventarioTipoEt o  ");
		sql.append(" WHERE o.estado        = :estado   ");
		sql.append(" AND date_trunc('day',o.planificacionInventarioTipo.planificacionInventario.fechaEjecucion) BETWEEN :fDesde AND :fHasta ");
		sql.append(" AND o.planAccionInventario.planificacionInventario.estadoInventario = :estadoCheckList ");
		if (!usuario.getZonaUsuario().isEmpty()) {
			sql.append(" AND o.planAccionInventario.planificacionInventario.agencia.zona in (:zonas) ");
		}
		if (estacion != null) {
			sql.append(" AND o.planAccionInventario.planificacionInventario.agencia = :estacion ");
		}
		if (tipoInventario != null) {
			sql.append(" AND o.tipoInventario = :tipoInventario ");
		}
		if (estadoPlanAccion != null && !estadoPlanAccion.getDescripcion().equals("Todos")) {
			sql.append(" AND o.estadoPlanAccionInv = :estadoPlanAccion ");
		}
		sql.append(" ORDER BY o.planificacionInventarioTipo.planificacionInventario.fechaEjecucion ");
		TypedQuery<PlanAccionInventarioTipoEt> query = em.createQuery(sql.toString(), PlanAccionInventarioTipoEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		List<ZonaEt> zonas = new ArrayList<ZonaEt>();
		query.setParameter("fDesde", fechaDesde, TemporalType.DATE);
		query.setParameter("fHasta", fechaHasta, TemporalType.DATE);
		query.setParameter("estadoCheckList", EstadoCheckListEnum.EJECUTADO);
		if (estadoPlanAccion != null && !estadoPlanAccion.getDescripcion().equals("Todos")) {
			query.setParameter("estadoPlanAccion", estadoPlanAccion);
		}
		if (!usuario.getZonaUsuario().isEmpty()) {
			for (ZonaUsuarioEt zonaUsuario : usuario.getZonaUsuario()) {
				zonas.add(zonaUsuario.getZona());
			}
			query.setParameter("zonas", zonas);
		}
		if (estacion != null) {
			query.setParameter("estacion", estacion);
		}
		if (tipoInventario != null) {
			query.setParameter("tipoInventario", tipoInventario);
		}
		List<PlanAccionInventarioTipoEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PlanAccionInventarioTipoEt> getPlanAccionInvTipoList(ZonaEt zona, AgenciaEt estacion, TipoInventarioEt tipoInventario, Date fechaDesde, Date fechaHasta, EstadoPlanAccionInvEnum estadoPlanAccion)
			throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM PlanAccionInventarioTipoEt o ");
		sql.append(" WHERE o.estado = :estado   ");
		sql.append(" AND date_trunc('day',o.planificacionInventarioTipo.planificacionInventario.fechaEjecucion) BETWEEN :fDesde AND :fHasta ");
		sql.append(" AND o.planAccionInventario.planificacionInventario.estadoInventario = :estadoCheckList ");
		if (zona != null) {
			sql.append(" AND o.planAccionInventario.planificacionInventario.agencia.zona = :zona ");
		}
		if (estacion != null) {
			sql.append(" AND o.planAccionInventario.planificacionInventario.agencia = :estacion ");
		}
		if (tipoInventario != null) {
			sql.append(" AND o.tipoInventario = :tipoInventario ");
		}
		if (estadoPlanAccion != null && !estadoPlanAccion.getDescripcion().equals("Todos")) {
			sql.append(" AND o.estadoPlanAccionInv = :estadoPlanAccion ");
		}
		sql.append(" ORDER BY o.planificacionInventarioTipo.planificacionInventario.fechaEjecucion ");
		TypedQuery<PlanAccionInventarioTipoEt> query = em.createQuery(sql.toString(), PlanAccionInventarioTipoEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("fDesde", fechaDesde, TemporalType.DATE);
		query.setParameter("fHasta", fechaHasta, TemporalType.DATE);
		query.setParameter("estadoCheckList", EstadoCheckListEnum.EJECUTADO);
		if (estadoPlanAccion != null && !estadoPlanAccion.getDescripcion().equals("Todos")) {
			query.setParameter("estadoPlanAccion", estadoPlanAccion);
		}
		if (zona != null) {
			query.setParameter("zona", zona);
		}
		if (estacion != null) {
			query.setParameter("estacion", estacion);
		}
		if (tipoInventario != null) {
			query.setParameter("tipoInventario", tipoInventario);
		}
		List<PlanAccionInventarioTipoEt> result = query.getResultList();
		return result;
	}

	@Remove
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void remove() {
		System.out.println("Finalizado Statefull Bean : " + this.getClass().getCanonicalName());
	}

	@PreDestroy
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void detached() {
		System.out.println("Terminado Statefull Bean : " + this.getClass().getCanonicalName());
	}

}
