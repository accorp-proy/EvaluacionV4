package com.primax.srv.idao;

import java.util.Date;

import com.primax.jpa.pla.ReporteTipoInventarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IReporteTipoInventarioDao extends IGenericDao<ReporteTipoInventarioEt, Long> {

	public void remove();

	public String generar(Date fechaDesde, Date fechaHasta, Long idZona, Long idTipoInventario, Long idAgencia, Long idUsuario);

}
