package com.primax.srv.idao;

import java.util.Date;
import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.PlanAccionInvAnioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanAccionInvAnioDao extends IGenericDao<PlanAccionInvAnioEt, Long> {

	public void remove();

	public List<PlanAccionInvAnioEt> getPlanAccionAnioList(UsuarioEt usuario);

	public void guardarPlanAccionAnio(PlanAccionInvAnioEt planAccionAnio, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public String generar(Long anio, Date fechaDesde, Date fechaHasta, Long idZona, Long idEstacion, Long idTipoInventario, Long idUsuario);

}
