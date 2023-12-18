package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.CategoriaInventarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ICategoriaInventarioDao;
import com.primax.srv.idao.IUsuarioDao;

@Named
@ApplicationScoped
public class CategoriaInvConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ICategoriaInventarioDao iCategoriaInventarioDao = EJB(EnumNaming.ICategoriaInventarioDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			CategoriaInventarioEt categoriaInv = iCategoriaInventarioDao.getCategoriaInventario(id);
			iCategoriaInventarioDao.remove();
			return categoriaInv;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && ((CategoriaInventarioEt) value).getIdCategoriaInventario() != null) {
			return ((CategoriaInventarioEt) value).getIdCategoriaInventario().toString();
		} else {
			return "";
		}
	}

}
