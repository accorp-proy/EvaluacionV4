package com.primax.srv.dao;

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
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.pla.PlanAccionInventarioEt;
import com.primax.jpa.pla.PlanAccionInventarioTipoEt;
import com.primax.jpa.pla.PlanificacionInventarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IPlanAccionInventarioDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class PlanAccionInventarioDao extends GenericDao<PlanAccionInventarioEt, Long> implements IPlanAccionInventarioDao {

	public PlanAccionInventarioDao() {
		super(PlanAccionInventarioEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarPlanAccionInventario(PlanAccionInventarioEt PlanAccionInventario, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (PlanAccionInventario.getIdPlanAccionInventario() == null) {
			PlanAccionInventario.audit(usuario, ActionAuditedEnum.NEW);
			crear(PlanAccionInventario);
		} else {
			PlanAccionInventario.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(PlanAccionInventario);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PlanAccionInventarioEt> getPlanAccionInventarioList(Date fechaDesde, Date fechaHasta) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM PlanAccionInventarioEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND date_trunc('day',o.fechaEjecucion) BETWEEN :fDesde AND :fHasta ");
		sql.append(" ORDER BY o.fechaPlanAccion  ");
		TypedQuery<PlanAccionInventarioEt> query = em.createQuery(sql.toString(), PlanAccionInventarioEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("fDesde", fechaDesde, TemporalType.DATE);
		query.setParameter("fHasta", fechaHasta, TemporalType.DATE);
		List<PlanAccionInventarioEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PlanAccionInventarioEt getPlanAccionInvExiste(PlanificacionInventarioEt planificacionInv) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM PlanAccionInventarioEt o ");
		sql.append(" WHERE o.estado = :estado ");
		sql.append(" AND o.planificacionInventario = :planificacionInv ");
		TypedQuery<PlanAccionInventarioEt> query = em.createQuery(sql.toString(), PlanAccionInventarioEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("planificacionInv", planificacionInv);
		List<PlanAccionInventarioEt> result = query.getResultList();
		return getUnique(result);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PlanAccionInventarioEt> getPlanAccionInventarioList(Date fechaDesde, Date fechaHasta, AgenciaEt agencia)
			throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM PlanAccionInventarioEt o ");
		sql.append(" WHERE o.estado  = :estado ");
		sql.append(" AND date_trunc('day',o.planificacionInventario.fechaPlanificacion) BETWEEN :fDesde AND :fHasta ");
		sql.append(" AND o.planificacionInventario.agencia = :agencia ");
		TypedQuery<PlanAccionInventarioEt> query = em.createQuery(sql.toString(), PlanAccionInventarioEt.class);
		query.setParameter("agencia", agencia);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("fDesde", fechaDesde, TemporalType.DATE);
		query.setParameter("fHasta", fechaHasta, TemporalType.DATE);
		List<PlanAccionInventarioEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PlanAccionInventarioEt> getPlanAccionInvByAgencia(AgenciaEt agencia) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM PlanAccionInventarioEt o ");
		sql.append(" WHERE o.estado = :estado   ");
		sql.append(" AND o.planificacionInventario.agencia = :agencia ");
		sql.append(" ORDER BY o.planificacionInventario.fechaPlanificacion ");
		TypedQuery<PlanAccionInventarioEt> query = em.createQuery(sql.toString(), PlanAccionInventarioEt.class);
		query.setParameter("agencia", agencia);
		query.setParameter("estado", EstadoEnum.ACT);
		List<PlanAccionInventarioEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PlanAccionInventarioEt getPlanAccionInventarioById(long id) {
		try {
			PlanAccionInventarioEt planAccionInventario = recuperar(id);
			planAccionInventario.getPlanAccionInventarioTipo().size();
			for (PlanAccionInventarioTipoEt tipos : planAccionInventario.getPlanAccionInventarioTipo()) {
				tipos.getPlanAccionInvCategoria().size();
			}
			return planAccionInventario;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PlanAccionInventarioEt getPlanAccionInv(AgenciaEt agencia) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM PlanAccionInventarioEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND o.planificacionInventario.agencia = :agencia ");
		sql.append(" AND o.planAccion = :planAccion ");
		TypedQuery<PlanAccionInventarioEt> query = em.createQuery(sql.toString(), PlanAccionInventarioEt.class);
		query.setParameter("agencia", agencia);
		query.setParameter("planAccion", true);
		query.setParameter("estado", EstadoEnum.ACT);
		List<PlanAccionInventarioEt> result = query.getResultList();
		PlanAccionInventarioEt consultado = getUnique(result);
		if (consultado != null) {
			consultado.getPlanAccionInventarioTipo().size();
			for (PlanAccionInventarioTipoEt planAccionInventarioTipos : consultado.getPlanAccionInventarioTipo()) {
				planAccionInventarioTipos.getPlanAccionInvCategoria().size();

			}
		}
		return consultado;
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
