package com.primax.bean.vs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.CategoriaInventarioEt;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ICategoriaInventarioDao;
import com.primax.srv.idao.ITipoInventarioDao;

@Named("CategoriaInventarioBn")
@ViewScoped
public class CategoriaInventarioBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ITipoInventarioDao iTipoInventarioDao;
	@EJB
	private ICategoriaInventarioDao iCategoriaInventarioDao;

	@Inject
	private AppMain appMain;

	private String condicion;
	private TipoInventarioEt tipoInventarioSeleccionado;
	private List<CategoriaInventarioEt> categoriasInventario;
	private CategoriaInventarioEt categoriaInventarioSeleccionado;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		tipoInventarioSeleccionado = null;
		categoriaInventarioSeleccionado = new CategoriaInventarioEt();
	}

	public void buscar() {
		try {
			categoriasInventario = iCategoriaInventarioDao.getCategoriaInventarioList(condicion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
	}

	public void guardar() {
		try {
			if (tipoInventarioSeleccionado == null) {
				showInfo("Por favor Seleccione Tipo de Inventario", FacesMessage.SEVERITY_ERROR, "msg", null);
				return;
			}
			if (categoriaInventarioSeleccionado.getDescripcion().equals("")) {
				showInfo("Por favor ingrese descripción", FacesMessage.SEVERITY_ERROR, "msg", null);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			categoriaInventarioSeleccionado.setTipoInventario(tipoInventarioSeleccionado);
			categoriaInventarioSeleccionado.setDescripcion(categoriaInventarioSeleccionado.getDescripcion().toUpperCase().trim());
			iCategoriaInventarioDao.guardarCategoriaInventario(categoriaInventarioSeleccionado, usuario);
			showInfo("Información Grabada con Éxito ", FacesMessage.SEVERITY_INFO);
			RequestContext.getCurrentInstance().execute("PF('dialog_24_1').hide();");
			buscar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void nuevo() {
		try {
			categoriaInventarioSeleccionado = new CategoriaInventarioEt();
			categoriaInventarioSeleccionado.setCodigo(iCategoriaInventarioDao.getCodigoCategoriaInv());
			categoriaInventarioSeleccionado.setOrden(iCategoriaInventarioDao.getOrdenCategoriaInv());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método nuevo " + " " + e.getMessage());
		}
	}

	public void modificar(CategoriaInventarioEt categoriaInventario) {
		categoriaInventarioSeleccionado = categoriaInventario;
		tipoInventarioSeleccionado = categoriaInventario.getTipoInventario();

	}

	public List<TipoInventarioEt> getTipoInventarioList() {
		List<TipoInventarioEt> tiposInventario = new ArrayList<TipoInventarioEt>();
		try {
			tiposInventario = iTipoInventarioDao.getTipoInventarioList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoInventarioList " + " " + e.getMessage());
		}
		return tiposInventario;
	}

	public SelectItem[] getEstadosActIna() {
		SelectItem[] items = new SelectItem[2];
		items[0] = new SelectItem(EstadoEnum.ACT, EstadoEnum.ACT.getDescripcion());
		items[1] = new SelectItem(EstadoEnum.INA, EstadoEnum.INA.getDescripcion());
		return items;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public TipoInventarioEt getTipoInventarioSeleccionado() {
		return tipoInventarioSeleccionado;
	}

	public void setTipoInventarioSeleccionado(TipoInventarioEt tipoInventarioSeleccionado) {
		this.tipoInventarioSeleccionado = tipoInventarioSeleccionado;
	}

	public List<CategoriaInventarioEt> getCategoriasInventario() {
		return categoriasInventario;
	}

	public void setCategoriasInventario(List<CategoriaInventarioEt> categoriasInventario) {
		this.categoriasInventario = categoriasInventario;
	}

	public CategoriaInventarioEt getCategoriaInventarioSeleccionado() {
		return categoriaInventarioSeleccionado;
	}

	public void setCategoriaInventarioSeleccionado(CategoriaInventarioEt categoriaInventarioSeleccionado) {
		this.categoriaInventarioSeleccionado = categoriaInventarioSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iTipoInventarioDao.remove();
		iCategoriaInventarioDao.remove();
	}

}
