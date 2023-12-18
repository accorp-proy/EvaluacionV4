package com.primax.srv.dao;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.PlanAccionInvCategoriaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IPlanAccionInvCategoriaDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class PlanAccionInvCategoriaDao extends GenericDao<PlanAccionInvCategoriaEt, Long> implements IPlanAccionInvCategoriaDao {

	public PlanAccionInvCategoriaDao() {
		super(PlanAccionInvCategoriaEt.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarPlanAccionInvCategoria(PlanAccionInvCategoriaEt planAccionInvCategoria, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (planAccionInvCategoria.getIdPlanAccionInvCategoria() == null) {
			planAccionInvCategoria.audit(usuario, ActionAuditedEnum.NEW);
			crear(planAccionInvCategoria);
		} else {
			planAccionInvCategoria.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(planAccionInvCategoria);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PlanAccionInvCategoriaEt getPlanAccionInvCategoriaById(long id) {
		try {
			PlanAccionInvCategoriaEt planAccionInvCategoria = recuperar(id);
			return planAccionInvCategoria;
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
