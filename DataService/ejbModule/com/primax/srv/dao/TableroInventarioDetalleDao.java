package com.primax.srv.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.pla.TableroInventarioDetalleEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ITableroInventarioDetalleDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class TableroInventarioDetalleDao extends GenericDao<TableroInventarioDetalleEt, Long> implements ITableroInventarioDetalleDao {

	public TableroInventarioDetalleDao() {
		super(TableroInventarioDetalleEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TableroInventarioDetalleEt> getTablaList(UsuarioEt usuario) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM TableroInventarioDetalleEt o ");
		sql.append(" WHERE o.estado        = :estado  ");
		sql.append(" AND o.usuarioRegistra = :usuario ");
		sql.append(" ORDER BY o.idTableroInventarioDetalle ");
		TypedQuery<TableroInventarioDetalleEt> query = em.createQuery(sql.toString(), TableroInventarioDetalleEt.class);
		query.setParameter("usuario", usuario);
		query.setParameter("estado", EstadoEnum.ACT);
		List<TableroInventarioDetalleEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String limpiarTablero(Long idUsuario) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getLimpiarTableroInventario");
		query.setParameter("idUsuario", idUsuario);
		String respuesta = (String) query.getOutputParameterValue("respuesta");
		return respuesta;
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
