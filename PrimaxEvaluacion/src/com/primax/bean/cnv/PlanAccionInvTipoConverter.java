package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.pla.PlanAccionInventarioTipoEt;
import com.primax.srv.idao.IPlanAccionInventarioTipoDao;

@Named
@ApplicationScoped
public class PlanAccionInvTipoConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		IPlanAccionInventarioTipoDao iPlanAccionInvTipoDao = EJB(EnumNaming.IPlanAccionInventarioTipoDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			PlanAccionInventarioTipoEt tipoInventario = iPlanAccionInvTipoDao.getTipoInventarioById(id);
			iPlanAccionInvTipoDao.remove();
			return tipoInventario;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !value.toString().isEmpty()) {
			PlanAccionInventarioTipoEt var = (PlanAccionInventarioTipoEt) value;
			return (var != null && var.getIdPlanAccionInventarioTipo() != null) ? var.getIdPlanAccionInventarioTipo().toString() : null;
		} else {
			return "";
		}
	}

}
