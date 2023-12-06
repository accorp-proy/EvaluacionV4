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
import com.primax.jpa.pla.CheckListProcesoEjecucionFormularioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICheckListProcesoEjecucionFormularioDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CheckListProcesoEjecucionFormularioDao extends GenericDao<CheckListProcesoEjecucionFormularioEt, Long>
		implements ICheckListProcesoEjecucionFormularioDao {

	public CheckListProcesoEjecucionFormularioDao() {
		super(CheckListProcesoEjecucionFormularioEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCheckListProEjeForm(CheckListProcesoEjecucionFormularioEt checkListProFrm, UsuarioEt usuario)
			throws EntidadNoGrabadaException {
		if (checkListProFrm.getIdCheckListProcesoEjecucionFormulario() == null) {
			checkListProFrm.audit(usuario, ActionAuditedEnum.NEW);
			crear(checkListProFrm);
		} else {
			checkListProFrm.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(checkListProFrm);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListProcesoEjecucionFormularioEt> getFrm(Long idCheckListProEje, Long idCheckListPrForm) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CheckListProcesoEjecucionFormularioEt o ");
		sql.append(" WHERE o.estado  = :estado ");
		sql.append(" AND o.checkListProcesoEjecucion.idCheckListProcesoEjecucion   = :idCheckListProcesoEjecucion ");
		sql.append(" AND o.checkListProcesoFormulario.idCheckListProcesoFormulario = :idCheckListProcesoFormulario ");
		sql.append(" ORDER BY  o.idCheckListProcesoEjecucionFormulario ");
		TypedQuery<CheckListProcesoEjecucionFormularioEt> query = em.createQuery(sql.toString(), CheckListProcesoEjecucionFormularioEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("idCheckListProcesoEjecucion", idCheckListProEje);
		query.setParameter("idCheckListProcesoFormulario", idCheckListPrForm);
		List<CheckListProcesoEjecucionFormularioEt> result = query.getResultList();
		return result;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CheckListProcesoEjecucionFormularioEt> getFrm(Long idCheckListProEje) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CheckListProcesoEjecucionFormularioEt o ");
		sql.append(" WHERE o.estado  = :estado ");
		sql.append(" AND o.checkListProcesoEjecucion.idCheckListProcesoEjecucion   = :idCheckListProcesoEjecucion ");
		sql.append(" AND o.condicion = :condicion ");
		sql.append(" ORDER BY  o.idCheckListProcesoEjecucionFormulario ");
		TypedQuery<CheckListProcesoEjecucionFormularioEt> query = em.createQuery(sql.toString(), CheckListProcesoEjecucionFormularioEt.class);
		query.setParameter("condicion", true);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("idCheckListProcesoEjecucion", idCheckListProEje);
		List<CheckListProcesoEjecucionFormularioEt> result = query.getResultList();
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
