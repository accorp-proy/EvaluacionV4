package com.primax.bean.vs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.enums.EstadoPlanAccionInvEnum;
import com.primax.jpa.param.CategoriaInventarioEt;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.pla.PlanAccionInvCategoriaEt;
import com.primax.jpa.pla.PlanAccionInventarioEt;
import com.primax.jpa.pla.PlanAccionInventarioTipoEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ICategoriaInventarioDao;
import com.primax.srv.idao.IPlanAccionInvCategoriaDao;
import com.primax.srv.idao.IPlanAccionInventarioDao;
import com.primax.srv.idao.IPlanAccionInventarioTipoDao;
import com.primax.srv.idao.IResponsableDao;

@Named("GerenciaInventarioBn")
@ViewScoped
public class GerenciaInventarioBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IResponsableDao iResponsableDao;
	@EJB
	private ICategoriaInventarioDao iCategoriaInventarioDao;
	@EJB
	private IPlanAccionInventarioDao iPlanAccionInventarioDao;
	@EJB
	private IPlanAccionInvCategoriaDao iPlanAccionInvCategoriaDao;
	@EJB
	private IPlanAccionInventarioTipoDao iPlanAccionInventarioTipoDao;

	private Long totalPuntaje = 0L;
	private Long totalPuntajeCalificacion = 0L;
	private PlanAccionInventarioEt planAccionInv;
	private List<CategoriaInventarioEt> categoriasInv;
	private PlanAccionInventarioTipoEt planAccionInvTipo;
	private CategoriaInventarioEt categoriaInvSeleccionada;

	@Inject
	private AppMain appMain;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void buscar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			ResponsableEt responsable = iResponsableDao.getResponsableEstacion(usuario.getPersonaUsuario());
			planAccionInv = iPlanAccionInventarioDao.getPlanAccionInv(responsable.getAgencia());
			for (PlanAccionInventarioTipoEt tipoInv : planAccionInv.getPlanAccionInventarioTipo()) {
				if (tipoInv.isEnEjecucion() && tipoInv.getEstadoPlanAccionInv().equals(EstadoPlanAccionInvEnum.PENDIENTE)) {
					planAccionInvTipo = tipoInv;
					if (planAccionInvTipo.getPlanAccionInvCategoria() == null || planAccionInvTipo.getPlanAccionInvCategoria().isEmpty()) {
						planAccionInvTipo.setPlanAccionInvCategoria(new ArrayList<>());
						break;
					}
					buscarCategoriaInv(planAccionInvTipo.getTipoInventario());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}

	}

	public void inicializarObj() {
		categoriaInvSeleccionada = null;
	}

	public void guardar() {
		Long check0 = 0L;
		Long check1 = 0L;
		String mensaje = "";
		try {
			mensaje = validarguardar();
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_WARN);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			planAccionInvTipo.setEjecutado(true);
			planAccionInvTipo.setEnEjecucion(false);
			planAccionInvTipo.setEstadoPlanAccionInv(EstadoPlanAccionInvEnum.INGRESADO);
			iPlanAccionInventarioTipoDao.guardarPlanAccionInvTipo(planAccionInvTipo, usuario);
			check0 = iPlanAccionInventarioTipoDao.getPlnInvEjecutado(planAccionInv);
			check1 = (long) planAccionInvTipo.getPlanAccionInvCategoria().size();
			if (check0 == check1) {
				planAccionInv.setFechaFin(new Date());
				planAccionInv.setFechaEjecucion(new Date());
				planAccionInv.setEstadoPlanAccionInv(EstadoPlanAccionInvEnum.INGRESADO);
				iPlanAccionInventarioDao.guardarPlanAccionInventario(planAccionInv, usuario);
			}
			retroceder();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public String validarguardar() {
		String mensaje = "";
		try {
//			for (CheckListProcesoEjecucionEt checkListProceso : checkListEjecucion.getCheckListProcesoEjecucion()) {
//				for (CheckListKpiEjecucionEt checkListKpi : checkListProceso.getCheckListKpiEjecucion()) {
//					if (checkListKpi.getComentarioPlanAccion() == null) {
//						mensaje = "Por favor ingresar plan de acción del KPI " + " " + checkListKpi.getDescripcion();
//						break;
//					}
//				}
//			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mensaje " + " " + e.getMessage());
		}
		return mensaje;
	}

	public void retroceder() {
		String pagina = "";
		try {
			FacesContext contex = FacesContext.getCurrentInstance();
			pagina = "/PrimaxEvaluacionPruebas/pages/reporte/rpt_017.xhtml";
			contex.getExternalContext().redirect(pagina);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método retroceder " + " " + e.getMessage());
		}
	}

	public void buscarCategoriaInv(TipoInventarioEt tipoInventario) {

		try {
			categoriasInv = iCategoriaInventarioDao.getCategoriaInvByTipoInv(tipoInventario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarProceso " + " " + e.getMessage());
		}

	}

	public void anadirCategoria() {
		PlanAccionInvCategoriaEt planAccionInvCategoriaN = null;
		try {
			if (categoriaInvSeleccionada == null) {
				showInfo("Por favor seleccione una categoría para continuar.", FacesMessage.SEVERITY_INFO, null, "");
				return;
			}
			if (!planAccionInvTipo.getPlanAccionInvCategoria().isEmpty()) {
				for (PlanAccionInvCategoriaEt planAccionInvCategoria : planAccionInvTipo.getPlanAccionInvCategoria()) {
					if (planAccionInvCategoria.getCategoriaInventario().getIdCategoriaInventario() == categoriaInvSeleccionada.getIdCategoriaInventario()) {
						showInfo("Categoría ya se encuentra agregada en el plan de acción.", FacesMessage.SEVERITY_INFO, null, "");
						return;
					}
				}
			}
			planAccionInvCategoriaN = new PlanAccionInvCategoriaEt();
			planAccionInvCategoriaN.setPlanAccionInventarioTipo(planAccionInvTipo);
			planAccionInvCategoriaN.setCategoriaInventario(categoriaInvSeleccionada);
			planAccionInvTipo.getPlanAccionInvCategoria().add(planAccionInvCategoriaN);
			iPlanAccionInventarioTipoDao.guardarPlanAccionInvTipo(planAccionInvTipo, appMain.getUsuario());
			RequestContext.getCurrentInstance().execute("PF('dlg_ger_004_1').hide();");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirCheckList " + " " + e.getMessage());
		}
	}

	public void eliminarCategoria(PlanAccionInvCategoriaEt planAccionInvCategoria) {
		try {
			planAccionInvCategoria.setEstado(EstadoEnum.INA);
			planAccionInvTipo.getPlanAccionInvCategoria().remove(planAccionInvCategoria);
			iPlanAccionInvCategoriaDao.guardarPlanAccionInvCategoria(planAccionInvCategoria, appMain.getUsuario());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método eliminarCheckListEjecucion " + " " + e.getMessage());
		}
	}

	public Long getTotalPuntaje() {
		return totalPuntaje;
	}

	public void setTotalPuntaje(Long totalPuntaje) {
		this.totalPuntaje = totalPuntaje;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	public Long getTotalPuntajeCalificacion() {
		return totalPuntajeCalificacion;
	}

	public void setTotalPuntajeCalificacion(Long totalPuntajeCalificacion) {
		this.totalPuntajeCalificacion = totalPuntajeCalificacion;
	}

	public PlanAccionInventarioEt getPlanAccionInv() {
		return planAccionInv;
	}

	public void setPlanAccionInv(PlanAccionInventarioEt planAccionInv) {
		this.planAccionInv = planAccionInv;
	}

	public PlanAccionInventarioTipoEt getPlanAccionInvTipo() {
		return planAccionInvTipo;
	}

	public void setPlanAccionInvTipo(PlanAccionInventarioTipoEt planAccionInvTipo) {
		this.planAccionInvTipo = planAccionInvTipo;
	}

	public List<CategoriaInventarioEt> getCategoriasInv() {
		return categoriasInv;
	}

	public void setCategoriasInv(List<CategoriaInventarioEt> categoriasInv) {
		this.categoriasInv = categoriasInv;
	}

	public CategoriaInventarioEt getCategoriaInvSeleccionada() {
		return categoriaInvSeleccionada;
	}

	public void setCategoriaInvSeleccionada(CategoriaInventarioEt categoriaInvSeleccionada) {
		this.categoriaInvSeleccionada = categoriaInvSeleccionada;
	}

	@Override
	protected void onDestroy() {
		iResponsableDao.remove();
		iCategoriaInventarioDao.remove();
		iPlanAccionInventarioDao.remove();
		iPlanAccionInvCategoriaDao.remove();
		iPlanAccionInventarioTipoDao.remove();
	}
}
