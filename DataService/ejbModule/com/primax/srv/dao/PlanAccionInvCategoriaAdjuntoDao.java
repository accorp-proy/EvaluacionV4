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
import com.primax.jpa.pla.PlanAccionInvCategoriaAdjuntoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IPlanAccionInvCategoriaAdjuntoDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class PlanAccionInvCategoriaAdjuntoDao extends GenericDao<PlanAccionInvCategoriaAdjuntoEt, Long> implements IPlanAccionInvCategoriaAdjuntoDao {

	public PlanAccionInvCategoriaAdjuntoDao() {
		super(PlanAccionInvCategoriaAdjuntoEt.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarPlnAccionInvCategoriaAdj(PlanAccionInvCategoriaAdjuntoEt plnAccionInvCategoriaAdj, UsuarioEt usuario)
			throws EntidadNoGrabadaException {
		if (plnAccionInvCategoriaAdj.getIdPlanAccionInvCategoriaAdjunto() == null) {
			plnAccionInvCategoriaAdj.audit(usuario, ActionAuditedEnum.NEW);
			crear(plnAccionInvCategoriaAdj);
		} else {
			plnAccionInvCategoriaAdj.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(plnAccionInvCategoriaAdj);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PlanAccionInvCategoriaAdjuntoEt getPlnAccionInvCategoriaAdjById(long id) {
		try {
			PlanAccionInvCategoriaAdjuntoEt plnAccionInvCategoriaAdj = recuperar(id);
			return plnAccionInvCategoriaAdj;
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
