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
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.pla.PlanAccionInvAnioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IPlanAccionInvAnioDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class PlanAccionInvAnioDao extends GenericDao<PlanAccionInvAnioEt, Long> implements IPlanAccionInvAnioDao {

	public PlanAccionInvAnioDao() {
		super(PlanAccionInvAnioEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarPlanAccionAnio(PlanAccionInvAnioEt planAccionAnio, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (planAccionAnio.getIdPlanAccionInvAnio() == null) {
			planAccionAnio.audit(usuario, ActionAuditedEnum.NEW);
			crear(planAccionAnio);
		} else {
			planAccionAnio.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(planAccionAnio);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String generar(Long anio, Date fechaDesde, Date fechaHasta, Long idZona, Long idEstacion, Long idTipoInventario, Long idUsuario) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getGenerarOrgPlnInvAnio");
		query.setParameter("idAnio", anio);
		query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
		query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
		query.setParameter("idZona", idZona);
		query.setParameter("idEstacion", idEstacion);
		query.setParameter("idTipoInventario", idTipoInventario);
		query.setParameter("idUsuario", idUsuario);
		String respuesta = (String) query.getOutputParameterValue("respuesta");
		return respuesta;

	}

	@Override
	public List<PlanAccionInvAnioEt> getPlanAccionAnioList(UsuarioEt usuario) {
		sql = new StringBuilder("FROM PlanAccionInvAnioEt o ");
		sql.append("WHERE o.estado = :estado ");
		sql.append("AND o.usuarioRegistra = :usuario ");
		TypedQuery<PlanAccionInvAnioEt> query = em.createQuery(sql.toString(), PlanAccionInvAnioEt.class);
		query.setParameter("usuario", usuario);
		query.setParameter("estado", EstadoEnum.ACT);
		List<PlanAccionInvAnioEt> result = query.getResultList();
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
