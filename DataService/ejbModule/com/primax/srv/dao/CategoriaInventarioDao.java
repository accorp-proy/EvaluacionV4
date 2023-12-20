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

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.CategoriaInventarioEt;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICategoriaInventarioDao;
import com.primax.srv.util.QUL;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CategoriaInventarioDao extends GenericDao<CategoriaInventarioEt, Long> implements ICategoriaInventarioDao {

	public CategoriaInventarioDao() {
		super(CategoriaInventarioEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCategoriaInventario(CategoriaInventarioEt categoriaInventario, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (categoriaInventario.getIdCategoriaInventario() == null) {
			categoriaInventario.audit(usuario, ActionAuditedEnum.NEW);
			crear(categoriaInventario);
		} else {
			categoriaInventario.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(categoriaInventario);
		}
		em.flush();
		em.clear();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CategoriaInventarioEt getCategoriaInventario(long id) {
		try {
			CategoriaInventarioEt categoriaInventario = recuperar(id);
			return categoriaInventario;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CategoriaInventarioEt> getCategoriaInventarioList(String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CategoriaInventarioEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND o.descripcion like :condicion ");
		}
		TypedQuery<CategoriaInventarioEt> query = em.createQuery(sql.toString(), CategoriaInventarioEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + QUL.getString(condicion) + "%");
		}
		List<CategoriaInventarioEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CategoriaInventarioEt> getCategoriaInvByTipoInv(TipoInventarioEt tipoInventario) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CategoriaInventarioEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND o.tipoInventario = :tipoInventario ");
		TypedQuery<CategoriaInventarioEt> query = em.createQuery(sql.toString(), CategoriaInventarioEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("tipoInventario", tipoInventario);
		List<CategoriaInventarioEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String getCodigoCategoriaInv() throws EntidadNoEncontradaException {
		String codigo = "";
		String formatString;
		try {

			sql = new StringBuilder(" SELECT  (MAX(o.idCategoriaInventario) + 1)  AS id  FROM  CategoriaInventarioEt o ");
			Long id = ((Long) em.createQuery(sql.toString()).getSingleResult());
			if (id == null) {
				id = 1L;
			}
			formatString = String.format("%03d", id);
			codigo = "CAT-" + String.format(formatString, id);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Metodo getCodigoCategoriaInv " + " " + e.getMessage());
		}
		return codigo;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long getOrdenCategoriaInv() throws EntidadNoEncontradaException {
		Long orden = 0L;
		try {

			sql = new StringBuilder(" SELECT  (MAX(o.orden) + 1)  AS id  FROM  CategoriaInventarioEt o ");
			Long id = ((Long) em.createQuery(sql.toString()).getSingleResult());
			if (id == null) {
				id = 1L;
			}
			orden = id;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Metodo getOrdenCategoriaInv " + " " + e.getMessage());
		}
		return orden;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String limpiarReporte(Long idUsuario) {
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("getReporteOrgPlnInvAnio");
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
