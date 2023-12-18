package com.primax.srv.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.TipoInventarioEt;
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
