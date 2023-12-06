package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListKpiEjecucionAdjuntoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListKpiEjecucionAdjuntoDao extends IGenericDao<CheckListKpiEjecucionAdjuntoEt, Long> {

	public void remove();

	public CheckListKpiEjecucionAdjuntoEt getCheckListKpiEjecucionAdjunto(long id);

	public void guardarCheckListKpiEjecucionAdjunto(CheckListKpiEjecucionAdjuntoEt checkListKpiEjecucionAdjunto, UsuarioEt usuario)
			throws EntidadNoGrabadaException;

}
