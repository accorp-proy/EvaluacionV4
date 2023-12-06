package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.pla.CheckListProcesoEt;
import com.primax.srv.idao.ICheckListProcesoDao;

@Named
@ApplicationScoped
public class CheckListProcesoConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		ICheckListProcesoDao iCheckListProcesoDao = EJB(EnumNaming.ICheckListProcesoDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			CheckListProcesoEt checkListProceso = iCheckListProcesoDao.getCheckListProceso(id);
			iCheckListProcesoDao.remove();
			return checkListProceso;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			CheckListProcesoEt var = (CheckListProcesoEt) value;
			return (var != null && var.getIdCheckListProceso() != null) ? var.getIdCheckListProceso().toString() : null;
		} else {
			return "";
		}
	}

}
