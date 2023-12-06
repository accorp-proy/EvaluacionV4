package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListInformeCabeceraEjecucionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListInformeCabeceraEjecucionDao extends IGenericDao<CheckListInformeCabeceraEjecucionEt, Long> {

	public void remove();

	public void guardarCheckListInfoCabEje(CheckListInformeCabeceraEjecucionEt checkList, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
