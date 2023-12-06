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

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.pla.TableroInventarioMesEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ITableroInventarioMesDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class TableroInventarioMesDao extends GenericDao<TableroInventarioMesEt, Long> implements ITableroInventarioMesDao {

	public TableroInventarioMesDao() {
		super(TableroInventarioMesEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TableroInventarioMesEt> getTablaList(UsuarioEt usuario) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM TableroInventarioMesEt o ");
		sql.append(" WHERE o.estado        = :estado  ");
		sql.append(" AND o.usuarioRegistra = :usuario ");
		sql.append(" ORDER BY o.idTableroInventarioMes ");
		TypedQuery<TableroInventarioMesEt> query = em.createQuery(sql.toString(), TableroInventarioMesEt.class);
		query.setParameter("usuario", usuario);
		query.setParameter("estado", EstadoEnum.ACT);
		List<TableroInventarioMesEt> result = query.getResultList();
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
