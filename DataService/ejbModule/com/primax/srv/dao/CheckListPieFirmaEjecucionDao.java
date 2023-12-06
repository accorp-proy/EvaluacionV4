package com.primax.srv.dao;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListPieFirmaEjecucionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListPieFirmaEjecucionDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListPieFirmaEjecucionDao extends GenericDao<CheckListPieFirmaEjecucionEt, Long> implements ICheckListPieFirmaEjecucionDao {

	public CheckListPieFirmaEjecucionDao() {
		super(CheckListPieFirmaEjecucionEt.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarcheckListPieFirma(CheckListPieFirmaEjecucionEt checkListPieFirma, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (checkListPieFirma.getIdCheckListPieFirmaEjecucion() == null) {
			checkListPieFirma.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListPieFirma);
		} else {
			checkListPieFirma.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListPieFirma);
		}
		em.flush();
		em.clear();
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
