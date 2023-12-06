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
import com.primax.jpa.pla.CheckListKpiEjecucionAdjuntoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListKpiEjecucionAdjuntoDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListKpiEjecucionAdjuntoDao extends GenericDao<CheckListKpiEjecucionAdjuntoEt, Long> implements ICheckListKpiEjecucionAdjuntoDao {

	public CheckListKpiEjecucionAdjuntoDao() {
		super(CheckListKpiEjecucionAdjuntoEt.class);
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListKpiEjecucionAdjunto(CheckListKpiEjecucionAdjuntoEt checkListKpiEjecucionAdjunto, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (checkListKpiEjecucionAdjunto.getIdCheckListKpiEjecucionAdjunto() == null) {
			checkListKpiEjecucionAdjunto.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListKpiEjecucionAdjunto);
		} else {
			checkListKpiEjecucionAdjunto.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListKpiEjecucionAdjunto);
		}
		em.flush();
		em.clear();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CheckListKpiEjecucionAdjuntoEt getCheckListKpiEjecucionAdjunto(long id) {
		try {
			CheckListKpiEjecucionAdjuntoEt checkListKpiEjecucionAdjunto = recuperar(id);
			return checkListKpiEjecucionAdjunto;
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
