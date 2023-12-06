package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.CheckListProcesoEjecucionFormularioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface ICheckListProcesoEjecucionFormularioDao extends IGenericDao<CheckListProcesoEjecucionFormularioEt, Long> {

	public void remove();

	public List<CheckListProcesoEjecucionFormularioEt> getFrm(Long idCheckListProEje) throws EntidadNoEncontradaException;

	public List<CheckListProcesoEjecucionFormularioEt> getFrm(Long idCheckListProEje, Long idCheckListPrForm) throws EntidadNoEncontradaException;

	public void guardarCheckListProEjeForm(CheckListProcesoEjecucionFormularioEt checkListProFrm, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
