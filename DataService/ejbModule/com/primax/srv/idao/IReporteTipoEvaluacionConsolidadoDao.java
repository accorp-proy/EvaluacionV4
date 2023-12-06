package com.primax.srv.idao;

import java.util.Date;

import com.primax.jpa.pla.ReporteTipoEvaluacionConsolidadoEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IReporteTipoEvaluacionConsolidadoDao extends IGenericDao<ReporteTipoEvaluacionConsolidadoEt, Long> {

	public void remove();

	public String generar(Date fechaDesde, Date fechaHasta, Long idZona, Long idEstacion, Long idUsuario);

}
