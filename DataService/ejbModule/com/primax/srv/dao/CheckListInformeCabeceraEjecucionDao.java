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
import com.primax.jpa.pla.CheckListInformeCabeceraEjecucionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListInformeCabeceraEjecucionDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListInformeCabeceraEjecucionDao extends GenericDao<CheckListInformeCabeceraEjecucionEt, Long>
		implements ICheckListInformeCabeceraEjecucionDao {

	public CheckListInformeCabeceraEjecucionDao() {
		super(CheckListInformeCabeceraEjecucionEt.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListInfoCabEje(CheckListInformeCabeceraEjecucionEt checkList, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (checkList.getIdCheckListInfCabEjecucion() == null) {
			checkList.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkList);
		} else {
			checkList.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkList);
		}
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
