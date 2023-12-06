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
import com.primax.jpa.pla.TableroInventarioCabeceraEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ITableroInventarioCabeceraDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class TableroInventarioCabeceraDao extends GenericDao<TableroInventarioCabeceraEt, Long> implements ITableroInventarioCabeceraDao {

	public TableroInventarioCabeceraDao() {
		super(TableroInventarioCabeceraEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public TableroInventarioCabeceraEt getTablaCabecera(UsuarioEt usuario) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM TableroInventarioCabeceraEt o ");
		sql.append(" WHERE o.estado        = :estado  ");
		sql.append(" AND o.usuarioRegistra = :usuario ");
		TypedQuery<TableroInventarioCabeceraEt> query = em.createQuery(sql.toString(), TableroInventarioCabeceraEt.class);
		query.setParameter("usuario", usuario);
		query.setParameter("estado", EstadoEnum.ACT);
		List<TableroInventarioCabeceraEt> result = query.getResultList();
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
