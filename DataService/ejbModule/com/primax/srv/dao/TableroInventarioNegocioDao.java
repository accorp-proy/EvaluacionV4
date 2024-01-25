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
import com.primax.jpa.pla.TableroInventarioNegocioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ITableroInventarioNegocioDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class TableroInventarioNegocioDao extends GenericDao<TableroInventarioNegocioEt, Long> implements ITableroInventarioNegocioDao {

	public TableroInventarioNegocioDao() {
		super(TableroInventarioNegocioEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarTableroInventarioNegocio(TableroInventarioNegocioEt tableroInventarioNegocio, UsuarioEt usuario)
			throws EntidadNoGrabadaException {
		if (tableroInventarioNegocio.getIdTableroInventarioNegocio() == null) {
			tableroInventarioNegocio.audit(usuario, ActionAuditedEnum.NEW);
			crear(tableroInventarioNegocio);
		} else {
			tableroInventarioNegocio.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(tableroInventarioNegocio);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public TableroInventarioNegocioEt getTablaList(UsuarioEt usuario) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM TableroInventarioNegocioEt o ");
		sql.append(" WHERE o.estado        = :estado  ");
		sql.append(" AND o.usuarioRegistra = :usuario ");
		sql.append(" ORDER BY o.idTableroInventarioNegocio ");
		TypedQuery<TableroInventarioNegocioEt> query = em.createQuery(sql.toString(), TableroInventarioNegocioEt.class);
		query.setParameter("usuario", usuario);
		query.setParameter("estado", EstadoEnum.ACT);
		List<TableroInventarioNegocioEt> result = query.getResultList();
		return getUnique(result);
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
