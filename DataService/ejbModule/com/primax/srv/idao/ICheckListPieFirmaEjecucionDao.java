package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListPieFirmaEjecucionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListPieFirmaEjecucionDao extends IGenericDao<CheckListPieFirmaEjecucionEt, Long> {

	public void remove();

	public void guardarcheckListPieFirma(CheckListPieFirmaEjecucionEt checkListPieFirma, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
