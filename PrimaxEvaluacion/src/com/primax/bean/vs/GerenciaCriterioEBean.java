package com.primax.bean.vs;

import java.io.IOException;
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
import org.primefaces.event.FileUploadEvent;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.enm.gen.RutaFileEnum;
import com.primax.enm.gen.UtilEnum;
import com.primax.enm.msg.Mensajes;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.enums.EstadoPlanAccionEnum;
import com.primax.jpa.enums.TipoCheckListEnum;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.pla.CheckListKpiEjecucionAdjuntoEt;
import com.primax.jpa.pla.CheckListKpiEjecucionEt;
import com.primax.jpa.pla.CheckListProcesoEjecucionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ICheckListEjecucionDao;
import com.primax.srv.idao.ICheckListKpiEjecucionAdjuntoDao;
import com.primax.srv.idao.ICheckListKpiEjecucionDao;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.srv.idao.IParametrolGeneralDao;
import com.primax.srv.idao.IResponsableDao;
import com.primax.srv.idao.IUsuarioDao;

@Named("GerenciaCriterioEBn")
@ViewScoped
public class GerenciaCriterioEBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IUsuarioDao iUsuarioDao;
	@EJB
	private IResponsableDao iResponsableDao;
	@EJB
	private IGeneralUtilsDao iGeneralUtilsDao;
	@EJB
	private IParametrolGeneralDao iParametrolGeneralDao;
	@EJB
	private ICheckListEjecucionDao iCheckListEjecucionDao;
	@EJB
	private ICheckListKpiEjecucionDao iCheckListKpiEjecucionDao;
	@EJB
	private ICheckListKpiEjecucionAdjuntoDao iCheckListKpiEjecucionAdjuntoDao;

	private Long totalPuntaje = 0L;
	private Long totalPuntajeCalificacion = 0L;
	private CheckListEjecucionEt checkListEjecucion;
	private CheckListKpiEjecucionEt checkListKpiEjecucionSeleccionado;
	private List<CheckListKpiEjecucionAdjuntoEt> checkListKpiEjecucionAdjuntoEliminado;

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
			checkListEjecucion = iCheckListEjecucionDao
					.getCheckListEjecucionPlanAccion(TipoCheckListEnum.CRITERIO_ESPECIFICO, responsable.getAgencia());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}

	}

	public void inicializarObj() {
		checkListKpiEjecucionAdjuntoEliminado = new ArrayList<>();
	}

	public void guardar() {
		String pagina = "";
		String mensaje = "";
		try {
			mensaje = validarguardar();
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_WARN);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			FacesContext contex = FacesContext.getCurrentInstance();
			checkListEjecucion.setEstadoPlanAccion(EstadoPlanAccionEnum.INGRESADO);
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, usuario);
			pagina = "/PrimaxEvaluacion/pages/main.jsf";
			contex.getExternalContext().redirect(pagina);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public String validarguardar() {
		String mensaje = "";
		try {
			for (CheckListProcesoEjecucionEt checkListProceso : checkListEjecucion.getCheckListProcesoEjecucion()) {
				for (CheckListKpiEjecucionEt checkListKpi : checkListProceso.getCheckListKpiEjecucion()) {
					if (checkListKpi.getComentarioPlanAccion() == null) {
						mensaje = "Por favor ingresar plan de acción del KPI " + " " + checkListKpi.getDescripcion();
						return mensaje;
					}
				}
			}

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
			pagina = "/PrimaxEvaluacion/pages/main.jsf";
			contex.getExternalContext().redirect(pagina);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método retroceder " + " " + e.getMessage());
		}
	}

	public void upload(FileUploadEvent event) throws EntidadNoGrabadaException {
		String ruta;
		String nombreArchivo = "";
		try {
			CheckListKpiEjecucionEt checkListKpiEjecucion = checkListKpiEjecucionSeleccionado;
			nombreArchivo = event.getFile().getFileName();
			CheckListKpiEjecucionAdjuntoEt reg = new CheckListKpiEjecucionAdjuntoEt();
			reg.setNombreAdjunto(nombreArchivo);
			reg.setCheckListKpiEjecucion(checkListKpiEjecucionSeleccionado);
			reg.setFile(event.getFile().getInputstream());
			for (CheckListKpiEjecucionAdjuntoEt doc : checkListKpiEjecucion.getCheckListKpiEjecucionAdjunto()) {
				if (doc.getNombreAdjunto().equals(reg.getNombreAdjunto())) {
					showInfo("" + Mensajes._ERROR_UPLOAD_DOCUMENTO.getDescripcion(), FacesMessage.SEVERITY_ERROR);
					return;
				}
			}
			if (nombreArchivo.toLowerCase().contains(".png") || nombreArchivo.toLowerCase().contains(".jpg")) {
				ruta = iGeneralUtilsDao.creaRuta(checkListKpiEjecucionSeleccionado.getIdCheckListKpiEjecucion(),
						RutaFileEnum.RUTA_CONTROL_INTERNO.getDescripcion());
			} else {
				ruta = iGeneralUtilsDao.creaRuta(checkListKpiEjecucionSeleccionado.getIdCheckListKpiEjecucion(),
						RutaFileEnum.RUTA_CONTROL_INTERNO.getDescripcion());
			}
			iGeneralUtilsDao.copyFile(reg.getNombreAdjunto(), reg.getFile(), ruta);
			checkListKpiEjecucionSeleccionado.getCheckListKpiEjecucionAdjunto().add(reg);
			FacesMessage msg = new FacesMessage("Satisfactorio! ",
					event.getFile().getFileName() + "  " + "Esta subido Correctamente.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (IOException e) {
			FacesMessage msg = new FacesMessage("Error! ",
					event.getFile().getFileName() + "  " + "El archivo no se subio.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		}
	}

	public void adjuntoKPI(CheckListKpiEjecucionEt kpi) {
		try {
			System.out.println("Prueba Jema");
			checkListKpiEjecucionSeleccionado = kpi;
			if (checkListKpiEjecucionSeleccionado.getCheckListKpiEjecucionAdjunto() == null
					|| checkListKpiEjecucionSeleccionado.getCheckListKpiEjecucionAdjunto().isEmpty()) {
				checkListKpiEjecucionSeleccionado.setCheckListKpiEjecucionAdjunto(new ArrayList<>());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método adjuntoKPI " + " " + e.getMessage());
		}
	}

	public void quitarAdjunto(CheckListKpiEjecucionAdjuntoEt checkListKpiEjecucionAdjunto) {
		try {
			Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
			checkListKpiEjecucionAdjunto.setFechaModificacion(fechaRegistro);
			checkListKpiEjecucionAdjunto.setEstado(EstadoEnum.INA);
			checkListKpiEjecucionAdjuntoEliminado.add(checkListKpiEjecucionAdjunto);
			checkListKpiEjecucionSeleccionado.getCheckListKpiEjecucionAdjunto().remove(checkListKpiEjecucionAdjunto);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método quitarAdjunto " + " " + e.getMessage());
		}

	}

	public void guardarAdj() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (!checkListKpiEjecucionAdjuntoEliminado.isEmpty()) {
				for (CheckListKpiEjecucionAdjuntoEt checkListKpiEjecucionAdjunto : checkListKpiEjecucionAdjuntoEliminado) {
					iCheckListKpiEjecucionAdjuntoDao.guardarCheckListKpiEjecucionAdjunto(checkListKpiEjecucionAdjunto,
							usuario);
				}
			}
			iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucionSeleccionado, usuario);
			showInfo("Dato Guardado", FacesMessage.SEVERITY_INFO, null, null);
			RequestContext.getCurrentInstance().execute("PF('dlg_ger_003_1').hide();");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public List<ParametrosGeneralesEt> getTipoCategoriaList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<ParametrosGeneralesEt>();
		try {
			ParametrosGeneralesEt parametrosGeneral = iParametrolGeneralDao.getObjPadre("65");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametrosGeneral);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoCategoriaList " + " " + e.getMessage());
		}
		return parametrosGenerales;
	}

	public CheckListEjecucionEt getCheckListEjecucion() {
		return checkListEjecucion;
	}

	public void setCheckListEjecucion(CheckListEjecucionEt checkListEjecucion) {
		this.checkListEjecucion = checkListEjecucion;
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

	public CheckListKpiEjecucionEt getCheckListKpiEjecucionSeleccionado() {
		return checkListKpiEjecucionSeleccionado;
	}

	public void setCheckListKpiEjecucionSeleccionado(CheckListKpiEjecucionEt checkListKpiEjecucionSeleccionado) {
		this.checkListKpiEjecucionSeleccionado = checkListKpiEjecucionSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iUsuarioDao.remove();
		iResponsableDao.remove();
		iParametrolGeneralDao.remove();
		iCheckListEjecucionDao.remove();
		iCheckListKpiEjecucionDao.remove();
		iCheckListKpiEjecucionAdjuntoDao.remove();
	}

}
