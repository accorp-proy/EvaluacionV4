package com.primax.srv.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.pla.PlanAccionInvMesEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IPlanAccionInvMesDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class PlanAccionInvMesDao extends GenericDao<PlanAccionInvMesEt, Long> implements IPlanAccionInvMesDao {

	public PlanAccionInvMesDao() {
		super(PlanAccionInvMesEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarPlanAccionMes(PlanAccionInvMesEt planAccionMes, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (planAccionMes.getIdPlanAccionInvMes() == null) {
			planAccionMes.audit(usuario, ActionAuditedEnum.NEW);
			crear(planAccionMes);
		} else {
			planAccionMes.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(planAccionMes);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String generar(Long idUsuario, Long anio, Long idPlanAccionInvAnio) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getGenerarOrgPlnInvMes");
		query.setParameter("idUsuario", idUsuario);
		String respuesta = (String) query.getOutputParameterValue("respuesta");
		return respuesta;
	}

	@Override
	public List<PlanAccionInvMesEt> getPlanAccionMesList(UsuarioEt usuario, Long idPlanAccionInvAnio, Long anio) {
		sql = new StringBuilder("FROM PlanAccionInvMesEt o ");
		sql.append(" WHERE o.estado = :estado ");
		sql.append(" AND o.anio     = :anio ");
		sql.append(" AND o.usuarioRegistra = :usuario ");
		sql.append(" AND o.idPlanAccionInvAnio = :idPlanAccionInvAnio ");
		TypedQuery<PlanAccionInvMesEt> query = em.createQuery(sql.toString(), PlanAccionInvMesEt.class);
		query.setParameter("anio", anio);
		query.setParameter("usuario", usuario);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("idPlanAccionInvAnio", idPlanAccionInvAnio);
		List<PlanAccionInvMesEt> result = query.getResultList();
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
