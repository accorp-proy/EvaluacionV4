package com.primax.bean.vs;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.primax.enm.gen.MessageType;
import com.primax.enm.gen.RutaFileEnum;
import com.primax.enm.gen.UtilEnum;
import com.primax.enm.msg.Mensajes;
import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.enums.TipoCheckListEnum;
import com.primax.jpa.param.CorreoEt;
import com.primax.jpa.param.CriterioEvaluacionDetalleEt;
import com.primax.jpa.param.KPICriticoEt;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.pla.CheckListEjecucionAdjuntoEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.pla.CheckListEjecucionReporteEt;
import com.primax.jpa.pla.CheckListInformeCabeceraEjecucionEt;
import com.primax.jpa.pla.CheckListKpiEjecucionEt;
import com.primax.jpa.pla.CheckListPieFirmaEjecucionEt;
import com.primax.jpa.pla.CheckListProcesoEjecucionEt;
import com.primax.jpa.pla.CheckListProcesoEjecucionFormularioEt;
import com.primax.jpa.pla.CheckListProcesoFormularioEt;
import com.primax.jpa.pla.PlanificacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ICheckListEjecucionAdjuntoDao;
import com.primax.srv.idao.ICheckListEjecucionDao;
import com.primax.srv.idao.ICheckListEjecucionReporteDao;
import com.primax.srv.idao.ICheckListInformeCabeceraEjecucionDao;
import com.primax.srv.idao.ICheckListKpiEjecucionDao;
import com.primax.srv.idao.ICheckListPieFirmaEjecucionDao;
import com.primax.srv.idao.ICheckListProcesoEjecucionDao;
import com.primax.srv.idao.ICheckListProcesoEjecucionFormularioDao;
import com.primax.srv.idao.ICorreoDao;
import com.primax.srv.idao.ICriterioEvaluacionDetalleDao;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.srv.idao.IKPICriticoDao;
import com.primax.srv.idao.IResponsableDao;
import com.primax.srv.util.msg.MessageCenter;
import com.primax.srv.util.msg.MessageFactory;

@Named("CheckListEjecucionBn")
@ViewScoped
public class CheckListEjecucionBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ICorreoDao iCorreoDao;
	@EJB
	private IKPICriticoDao iKPICriticoDao;
	@EJB
	private IResponsableDao iResponsableDao;
	@EJB
	private IGeneralUtilsDao iGeneralUtilsDao;
	@EJB
	private ICheckListEjecucionDao iCheckListEjecucionDao;
	@EJB
	private ICheckListKpiEjecucionDao iCheckListKpiEjecucionDao;
	@EJB
	private ICheckListProcesoEjecucionDao iCheckListProcesoEjecucionDao;
	@EJB
	private ICriterioEvaluacionDetalleDao iCriterioEvaluacionDetalleDao;
	@EJB
	private ICheckListEjecucionAdjuntoDao iCheckListEjecucionAdjuntoDao;
	@EJB
	private ICheckListEjecucionReporteDao iCheckListEjecucionReporteDao;
	@EJB
	private ICheckListPieFirmaEjecucionDao iCheckListPieFirmaEjecucionDao;
	@EJB
	private ICheckListInformeCabeceraEjecucionDao iCheckListInfoCabEjeDao;
	@EJB
	private ICheckListProcesoEjecucionFormularioDao iCheckListProEjeFormDao;

	private boolean pro01Frm01V = false;
	private boolean pro01Frm02V = false;
	private boolean pro01Frm03V = false;
	private CheckListProcesoFormularioEt pro01Frm01;
	private CheckListProcesoFormularioEt pro01Frm02;
	private CheckListProcesoFormularioEt pro01Frm03;
	private List<CheckListProcesoEjecucionFormularioEt> pro01Frm01List;
	private List<CheckListProcesoEjecucionFormularioEt> pro01Frm02List;
	private List<CheckListProcesoEjecucionFormularioEt> pro01Frm03List;

	private boolean pro02Frm01V = false;
	private boolean pro02Frm02V = false;
	private boolean pro02Frm03V = false;
	private CheckListProcesoFormularioEt pro02Frm01;
	private CheckListProcesoFormularioEt pro02Frm02;
	private CheckListProcesoFormularioEt pro02Frm03;
	private List<CheckListProcesoEjecucionFormularioEt> pro02Frm01List;
	private List<CheckListProcesoEjecucionFormularioEt> pro02Frm02List;
	private List<CheckListProcesoEjecucionFormularioEt> pro02Frm03List;

	private boolean pro03Frm01V = false;
	private boolean pro03Frm02V = false;
	private boolean pro03Frm03V = false;
	private CheckListProcesoFormularioEt pro03Frm01;
	private CheckListProcesoFormularioEt pro03Frm02;
	private CheckListProcesoFormularioEt pro03Frm03;
	private List<CheckListProcesoEjecucionFormularioEt> pro03Frm01List;
	private List<CheckListProcesoEjecucionFormularioEt> pro03Frm02List;
	private List<CheckListProcesoEjecucionFormularioEt> pro03Frm03List;

	private boolean pro04Frm01V = false;
	private boolean pro04Frm02V = false;
	private boolean pro04Frm03V = false;
	private CheckListProcesoFormularioEt pro04Frm01;
	private CheckListProcesoFormularioEt pro04Frm02;
	private CheckListProcesoFormularioEt pro04Frm03;
	private List<CheckListProcesoEjecucionFormularioEt> pro04Frm01List;
	private List<CheckListProcesoEjecucionFormularioEt> pro04Frm02List;
	private List<CheckListProcesoEjecucionFormularioEt> pro04Frm03List;

	private boolean pro05Frm01V = false;
	private boolean pro05Frm02V = false;
	private boolean pro05Frm03V = false;
	private CheckListProcesoFormularioEt pro05Frm01;
	private CheckListProcesoFormularioEt pro05Frm02;
	private CheckListProcesoFormularioEt pro05Frm03;
	private List<CheckListProcesoEjecucionFormularioEt> pro05Frm01List;
	private List<CheckListProcesoEjecucionFormularioEt> pro05Frm02List;
	private List<CheckListProcesoEjecucionFormularioEt> pro05Frm03List;

	private Long totalPuntaje = 0L;
	private String descripcion = "";
	private boolean validarGuardar = false;
	private Long totalPuntajeCalificacion = 0L;
	private CheckListEjecucionEt checkListEjecucion;
	private CheckListEjecucionEt checkListEjecucionImprimir;
	private CriterioEvaluacionDetalleEt criterioEvaluacionDetalleSeleccionado;
	private CheckListEjecucionReporteEt checkListEjecucionReporteSeleccionado;
	private List<CheckListEjecucionAdjuntoEt> checkListEjecucionAdjuntoEliminado;

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
			checkListEjecucion = iCheckListEjecucionDao.getCheckListEjecucion(TipoCheckListEnum.CRITERIO_GENERAL,
					usuario);
			if (checkListEjecucion != null) {
				if (checkListEjecucion.getCheckListEjecucionAdjunto() == null
						|| checkListEjecucion.getCheckListEjecucionAdjunto().isEmpty()) {
					checkListEjecucion.setCheckListEjecucionAdjunto(new ArrayList<>());
					cargarForularioCheckList(checkListEjecucion);
				}
				if (checkListEjecucion.isVisualizarComentario()) {
					descripcion = "Observación";
				}
				mostrarTotal(checkListEjecucion);
				mostrarTotalCalificacion(checkListEjecucion);
			}
			if (checkListEjecucion.getCheckListEjecucionReporte() != null
					&& !checkListEjecucion.getCheckListEjecucionReporte().isEmpty()) {
				for (CheckListEjecucionReporteEt reporte : checkListEjecucion.getCheckListEjecucionReporte()) {
					checkListEjecucionReporteSeleccionado = reporte;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}

	}

	public void inicializarObj() {
		criterioEvaluacionDetalleSeleccionado = null;
		checkListEjecucionAdjuntoEliminado = new ArrayList<>();
	}

	public void guardarReporte() {
		String codigo = "";
		String secuencial = "";
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (checkListEjecucionReporteSeleccionado.getSecuencial() < 10) {
				secuencial = "00" + checkListEjecucionReporteSeleccionado.getSecuencial();
			} else if (checkListEjecucionReporteSeleccionado.getSecuencial() > 10
					&& checkListEjecucionReporteSeleccionado.getSecuencial() < 100) {
				secuencial = "0" + checkListEjecucionReporteSeleccionado.getSecuencial();
			} else {
				secuencial = "" + checkListEjecucionReporteSeleccionado.getSecuencial();
			}
			codigo = checkListEjecucionReporteSeleccionado.getCodigo() + secuencial;
			checkListEjecucionReporteSeleccionado.setCodigoReplace(codigo);
			checkListEjecucionReporteSeleccionado
					.setDescripcionReplace(remplazarD(checkListEjecucionReporteSeleccionado));
			iCheckListEjecucionReporteDao.guardarCheckListEjecucionReporte(checkListEjecucionReporteSeleccionado,
					usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarReporte " + " " + e.getMessage());
		}

	}

	public void guardar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}

	}

	public void guardarCheckList() {
		String pagina = "";
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (!checkListEjecucionAdjuntoEliminado.isEmpty()) {
				for (CheckListEjecucionAdjuntoEt checkListEjecucionAdjunto : checkListEjecucionAdjuntoEliminado) {
					iCheckListEjecucionAdjuntoDao.guardarCheckListEjecucionAdjunto(checkListEjecucionAdjunto, usuario);
				}
			}
			guardaArchivo(checkListEjecucion);
			FacesContext contex = FacesContext.getCurrentInstance();
			checkListEjecucion.setEstadoCheckList(EstadoCheckListEnum.EJECUTADO);
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, usuario);
			iCheckListEjecucionDao.generarActNivelRiesgo(checkListEjecucion.getNivelEvaluacion().getIdNivelEvaluacion(),
					checkListEjecucion.getIdCheckListEjecucion());
			pagina = "/PrimaxEvaluacion/pages/main.xhtml";
			contex.getExternalContext().redirect(pagina);
			showInfo("Grabado con Éxito ", FacesMessage.SEVERITY_INFO);
			if (checkListEjecucion.isModificado()) {
				enviarEmail(checkListEjecucion.getPlanificacion());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarCheckList " + " " + e.getMessage());
		}
	}

	public void validaCompleto() {
		validarGuardar = false;
		try {
			for (CheckListProcesoEjecucionEt checkListProcesoEjecucion : checkListEjecucion
					.getCheckListProcesoEjecucion()) {
				for (CheckListKpiEjecucionEt checkListKpiEjecucion : checkListProcesoEjecucion
						.getCheckListKpiEjecucion()) {
					if (checkListKpiEjecucion.getCriterioEvaluacionDetalle() == null) {
						validarGuardar = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validaCompleto " + " " + e.getMessage());
		}
	}

	public Long sumarProceso(CheckListProcesoEjecucionEt checkListProcesoEjecucion) {
		Long totalPuntajeD = 0L;
		try {
			for (int i = 0; i < checkListProcesoEjecucion.getCheckListKpiEjecucion().size(); i++) {
				if (checkListProcesoEjecucion.getCheckListKpiEjecucion().get(i).getkPICritico() == null) {
					totalPuntajeD += checkListProcesoEjecucion.getCheckListKpiEjecucion().get(i).getProcesoDetalle()
							.getPuntaje();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarProceso " + " " + e.getMessage());
		}
		return totalPuntajeD;
	}

	public Long sumarProcesoCalificacion(CheckListProcesoEjecucionEt checkListProcesoEjecucion) {
		Long totalPuntajeD = 0L;
		try {
			for (int i = 0; i < checkListProcesoEjecucion.getCheckListKpiEjecucion().size(); i++) {
				if (checkListProcesoEjecucion.getCheckListKpiEjecucion().get(i).getkPICritico() == null
						&& checkListProcesoEjecucion.getCheckListKpiEjecucion().get(i).isSumar()) {
					totalPuntajeD += checkListProcesoEjecucion.getCheckListKpiEjecucion().get(i).getPuntajeEjecucion();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método sumarProceso " + " " + e.getMessage());
		}
		return totalPuntajeD;
	}

	public void mostrarTotal(CheckListEjecucionEt checkListEjecucion) {
		totalPuntaje = 0L;
		Long totalKpi = 0L;
		try {
			for (CheckListProcesoEjecucionEt checkListProceso : checkListEjecucion.getCheckListProcesoEjecucion()) {
				for (int i = 0; i < checkListProceso.getCheckListKpiEjecucion().size(); i++) {
					if (checkListProceso.getCheckListKpiEjecucion().get(i).getkPICritico() == null
							&& checkListProceso.getCheckListKpiEjecucion().get(i).isSumar()) {
						totalKpi += checkListProceso.getCheckListKpiEjecucion().get(i).getProcesoDetalle().getPuntaje();
					}
				}
			}
			totalPuntaje = totalKpi;
			System.out.println("Total Puntaje" + " " + totalPuntaje);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mostrarTotal " + " " + e.getMessage());
		}
	}

	public void mostrarTotalCalificacion(CheckListEjecucionEt checkListEjecucion) {
		totalPuntajeCalificacion = 0L;
		Long totalKpi = 0L;
		try {
			for (CheckListProcesoEjecucionEt checkListProcesoEjecucion : checkListEjecucion
					.getCheckListProcesoEjecucion()) {
				totalKpi += checkListProcesoEjecucion.getCheckListKpiEjecucion().stream().filter(p -> p.isSumar())
						.mapToLong(p -> p.getPuntajeEjecucion()).sum();
			}
			totalPuntajeCalificacion = totalKpi;
			System.out.println("Total Puntaje Calificación" + " " + totalPuntajeCalificacion);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mostrarTotal " + " " + e.getMessage());
		}
	}

	public void guardarSeleccion(CheckListKpiEjecucionEt checkListKpiEjecucion) {
		Long puntaje = 0L;
		KPICriticoEt kPICritico = null;
		boolean condicionCritica = false;
		boolean condicionCriticaFoco = false;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (checkListKpiEjecucion.getkPICritico() == null) {
				if (checkListKpiEjecucion.getCriterioEvaluacionDetalle() != null) {
					if (checkListKpiEjecucion.getCriterioEvaluacionDetalle().getNombre().equals("Cumple")) {
						puntaje = checkListKpiEjecucion.getPuntaje();
					}
				}
				checkListKpiEjecucion.setPuntajeEjecucion(puntaje);
				iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucion, usuario);
			} else {
				iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpiEjecucion, usuario);
				iCheckListKpiEjecucionDao.clear();
				condicionCritica = verificacionCondicionEstrella(usuario);
				kPICritico = checkListKpiEjecucion.getkPICritico();
				if (!checkListKpiEjecucion.getCriterioEvaluacionDetalle().getNombre().equals("Cumple")) {
					if (kPICritico.getNombre().equals("Foco")) {
						CheckListProcesoEjecucionEt checkListProcesos = checkListKpiEjecucion
								.getCheckListProcesoEjecucion();
						for (CheckListKpiEjecucionEt checkListKpi : checkListProcesos.getCheckListKpiEjecucion()) {
							checkListKpi.setSumar(false);
							iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpi, usuario);
						}
					} else if (kPICritico.getNombre().equals("Estrella")) {
						for (CheckListProcesoEjecucionEt checkListProcesos : checkListEjecucion
								.getCheckListProcesoEjecucion()) {
							for (CheckListKpiEjecucionEt checkListKpi : checkListProcesos.getCheckListKpiEjecucion()) {
								checkListKpi.setSumar(false);
								iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpi, usuario);
							}
						}
					}
				} else if (checkListKpiEjecucion.getCriterioEvaluacionDetalle().getNombre().equals("Cumple")
						&& !condicionCritica) {
					if (kPICritico.getNombre().equals("Foco")) {
						CheckListProcesoEjecucionEt checkListProcesos = checkListKpiEjecucion
								.getCheckListProcesoEjecucion();
						for (CheckListKpiEjecucionEt checkListKpi : checkListProcesos.getCheckListKpiEjecucion()) {
							checkListKpi.setSumar(true);
							iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpi, usuario);
						}
					} else if (kPICritico.getNombre().equals("Estrella") && !condicionCritica) {
						for (CheckListProcesoEjecucionEt checkListProcesos : checkListEjecucion
								.getCheckListProcesoEjecucion()) {
							condicionCriticaFoco = verificacionCondicionFoco(checkListProcesos, usuario);
							if (!condicionCriticaFoco) {
								for (CheckListKpiEjecucionEt checkListKpi : checkListProcesos
										.getCheckListKpiEjecucion()) {
									checkListKpi.setSumar(true);
									iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpi, usuario);
								}
							}
						}
					}
				}
			}
			sumarProcesoCalificacion(checkListKpiEjecucion.getCheckListProcesoEjecucion());
			mostrarTotalCalificacion(checkListEjecucion);
			validaCompleto();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarSeleccion " + " " + e.getMessage());
		}
	}

	public boolean verificacionCondicionEstrella(UsuarioEt usuario) {
		boolean condicionCritica = false;
		try {
			// jema
			KPICriticoEt kPICritico = iKPICriticoDao.getKPICritico(2L);
			CriterioEvaluacionDetalleEt criterio = iCriterioEvaluacionDetalleDao.getCriterioEvaluacionDetalle(23);
			condicionCritica = iCheckListKpiEjecucionDao.getExisteCondicionCritica(checkListEjecucion, kPICritico,
					criterio);
			if (condicionCritica) {
				for (CheckListProcesoEjecucionEt checkListProcesos : checkListEjecucion
						.getCheckListProcesoEjecucion()) {
					for (CheckListKpiEjecucionEt checkListKpi : checkListProcesos.getCheckListKpiEjecucion()) {
						checkListKpi.setSumar(false);
						iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpi, usuario);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método verificacionCondicion " + " " + e.getMessage());
		}
		return condicionCritica;
	}

	public boolean verificacionCondicionFoco(CheckListProcesoEjecucionEt checkListProcesoEjecucion, UsuarioEt usuario) {
		boolean condicionCritica = false;
		try {
			KPICriticoEt kPICritico = iKPICriticoDao.getKPICritico(1L);
			CriterioEvaluacionDetalleEt criterio = iCriterioEvaluacionDetalleDao.getCriterioEvaluacionDetalle(23);
			condicionCritica = iCheckListKpiEjecucionDao.getExisteCondicionCriticaFoco(checkListProcesoEjecucion,
					kPICritico, criterio);
			if (condicionCritica) {
				for (CheckListKpiEjecucionEt checkListKpi : checkListProcesoEjecucion.getCheckListKpiEjecucion()) {
					checkListKpi.setSumar(false);
					iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(checkListKpi, usuario);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método verificacionCondicion " + " " + e.getMessage());
		}
		return condicionCritica;
	}

	public void upload(FileUploadEvent event) {
		String ruta;
		String nombreArchivo = "";
		String nombreArchivoR = "";
		try {
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, appMain.getUsuario());
			CheckListEjecucionAdjuntoEt reg = new CheckListEjecucionAdjuntoEt();
			reg.setCheckListEjecucion(checkListEjecucion);
			reg.setFile(event.getFile().getInputstream());
			nombreArchivoR = moveChar(event.getFile().getFileName());
			nombreArchivo = nombreArchivoR.replace(" ", "_");
			reg.setNombreAdjunto(nombreArchivo);
			for (CheckListEjecucionAdjuntoEt doc : checkListEjecucion.getCheckListEjecucionAdjunto()) {
				if (doc.getNombreAdjunto().equals(reg.getNombreAdjunto())) {
					showInfo("" + Mensajes._ERROR_UPLOAD_DOCUMENTO.getDescripcion(), FacesMessage.SEVERITY_ERROR);
					return;
				}
			}
			if (nombreArchivo.toLowerCase().contains(".png") || nombreArchivo.toLowerCase().contains(".jpg")) {
				ruta = iGeneralUtilsDao.creaRuta(checkListEjecucion.getIdCheckListEjecucion(),
						RutaFileEnum.RUTA_CONTROL_INTERNO.getDescripcion());
			} else {
				ruta = iGeneralUtilsDao.creaRuta(checkListEjecucion.getIdCheckListEjecucion(),
						RutaFileEnum.RUTA_CONTROL_INTERNO.getDescripcion());
			}
			reg.setImagenPath(ruta);
			iGeneralUtilsDao.copyFile(reg.getNombreAdjunto(), reg.getFile(), ruta);
			checkListEjecucion.getCheckListEjecucionAdjunto().add(reg);
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucion, appMain.getUsuario());
			FacesMessage msg = new FacesMessage("Satisfactorio! ", nombreArchivo + "  " + "Esta subido Correctamente.");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (IOException e) {
			FacesMessage msg = new FacesMessage("Error! ", nombreArchivo + "  " + "El archivo no se subio.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método quitarAdjunto " + " " + e.getMessage());
		}

	}

	public static String moveChar(String input) {
		String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
		String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
		String output = input;
		for (int i = 0; i < original.length(); i++) {
			output = output.replace(original.charAt(i), ascii.charAt(i));
		}
		return output;
	}

	public void quitarAdjunto(CheckListEjecucionAdjuntoEt checkListEjecucionAdjunto) {
		try {
			Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
			checkListEjecucionAdjunto.setFechaModificacion(fechaRegistro);
			checkListEjecucionAdjunto.setEstado(EstadoEnum.INA);
			checkListEjecucionAdjuntoEliminado.add(checkListEjecucionAdjunto);
			iCheckListEjecucionAdjuntoDao.guardarCheckListEjecucionAdjunto(checkListEjecucionAdjunto,
					appMain.getUsuario());
			checkListEjecucion.getCheckListEjecucionAdjunto().remove(checkListEjecucionAdjunto);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método quitarAdjunto " + " " + e.getMessage());
		}
	}

	public void guardaArchivo(CheckListEjecucionEt checkListEjecucion) {
		try {
			String ruta;
			ruta = iGeneralUtilsDao.creaRuta(checkListEjecucion.getIdCheckListEjecucion(),
					RutaFileEnum.RUTA_CONTROL_INTERNO.getDescripcion());
			for (int i = 0; i < checkListEjecucion.getCheckListEjecucionAdjunto().size(); i++) {
				iGeneralUtilsDao.copyFile(checkListEjecucion.getCheckListEjecucionAdjunto().get(i).getNombreAdjunto(),
						checkListEjecucion.getCheckListEjecucionAdjunto().get(i).getFile(), ruta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void enviarEmail(PlanificacionEt planificacion) {
		// String email = "";
		// String email0 = "";
		// String email1 = "";
		// StringBuilder recipient = new StringBuilder();
		try {
			CorreoEt config = iCorreoDao.getCorreoExiste(1L);
			MessageFactory msg = new MessageFactory(MessageType.MAIL);
			MessageCenter msc = msg.getMessenger();
			msc.setSubject(planificacion.getAgencia().getNombreAgencia() + " :Visita de Control Interno");
			msc.setFrom("notificacionControlInterno@atimasa.com.ec");
			msc.setMessage("Se ha modificado CheckList Prueba");
			// email0 = appMain.getUsuario().getPersonaUsuario().getEmail();
			// email1 = recipient.toString() + email0;
			// System.out.println(email1);
			msc.setRecipient("acorrea@accorp.com.ec,jeffersonmaji@hotmail.com");
			// msc.setRecipient(email1);
			msc.setConfig(config);
			msc.sendMessage();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método enviarEmail " + " " + e.getMessage());
		}
	}

	public String remplazarD(CheckListEjecucionReporteEt checkListEjecucionReporte) {
		String dia = "";
		String mesS = "";
		String anio = "";
		String horaI = "";
		String horaF = "";
		String descripcion = "";
		try {
			Calendar calendar = Calendar.getInstance();
			Calendar calendarI = Calendar.getInstance();
			Calendar calendarF = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.UK);
			calendar.setTime(checkListEjecucionReporte.getFechaVerificacion());
			calendarI.setTime(checkListEjecucionReporte.getHoraInicio());
			calendarF.setTime(checkListEjecucionReporte.getHoraFin());
			dia = "" + calendar.get(Calendar.DAY_OF_MONTH);
			Month mes = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
					calendar.get(Calendar.DAY_OF_MONTH)).getMonth();
			mesS = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
			anio = "" + calendar.get(Calendar.YEAR);
			horaI = dateFormat.format(calendarI.getTime()).replace(":", "h");
			horaF = dateFormat.format(calendarF.getTime()).replace(":", "h");
			descripcion = checkListEjecucionReporte.getDescripcion().replace("{dia}", dia);
			descripcion = descripcion.replace("{mes}", mesS);
			descripcion = descripcion.replace("{anio}", anio);
			descripcion = descripcion.replace("{hora_inicio}", horaI);
			descripcion = descripcion.replace("{hora_fin}", horaF);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método remplazarD " + " " + e.getMessage());
		}
		return descripcion;
	}
	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public void cargarForularioCheckList(CheckListEjecucionEt check) {
		try {
			for (CheckListProcesoEjecucionEt checkListProceso : check.getCheckListProcesoEjecucion()) {
				if (checkListProceso.getCheckListProcesoEjecucionFormulario() != null
						&& !checkListProceso.getCheckListProcesoEjecucionFormulario().isEmpty()) {
					if (checkListProceso.getOrden() == 1L) {
						proceso1(checkListProceso);
					}
					if (checkListProceso.getOrden() == 2L) {
						proceso2(checkListProceso);
					}
					if (checkListProceso.getOrden() == 3L) {
						proceso3(checkListProceso);
					}
					if (checkListProceso.getOrden() == 4L) {
						proceso4(checkListProceso);
					}
					if (checkListProceso.getOrden() == 5L) {
						proceso5(checkListProceso);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método cargarForularioCheckList " + " " + e.getMessage());
		}
	}

	public void proceso1(CheckListProcesoEjecucionEt procesoCheckList) {

		pro01Frm01List = new ArrayList<>();
		pro01Frm02List = new ArrayList<>();
		pro01Frm03List = new ArrayList<>();
		CheckListProcesoFormularioEt objFrm;
		CheckListProcesoEjecucionFormularioEt obj;
		List<CheckListProcesoEjecucionFormularioEt> frms = new ArrayList<>();
		try {
			frms = iCheckListProEjeFormDao.getFrm(procesoCheckList.getIdCheckListProcesoEjecucion());
			for (int i = 0; i < frms.size(); i++) {

				if (i == 0) {
					pro01Frm01V = true;
					obj = procesoCheckList.getCheckListProcesoEjecucionFormulario().get(i);
					objFrm = obj.getCheckListProcesoFormulario();
					pro01Frm01 = objFrm;
					cargarItemsPro01Frm01(i, procesoCheckList, objFrm);
				}
				if (i == 1) {
					pro01Frm02V = true;
					obj = procesoCheckList.getCheckListProcesoEjecucionFormulario().get(i);
					objFrm = obj.getCheckListProcesoFormulario();
					pro01Frm02 = objFrm;
					cargarItemsPro01Frm01(i, procesoCheckList, objFrm);
				}
				if (i == 2) {
					pro01Frm03V = true;
					obj = procesoCheckList.getCheckListProcesoEjecucionFormulario().get(i);
					objFrm = obj.getCheckListProcesoFormulario();
					pro01Frm03 = objFrm;
					cargarItemsPro01Frm01(i, procesoCheckList, objFrm);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método proceso1 " + " " + e.getMessage());
		}
	}

	public void proceso2(CheckListProcesoEjecucionEt procesoCheckList) {

		pro02Frm01List = new ArrayList<>();
		pro02Frm02List = new ArrayList<>();
		pro02Frm03List = new ArrayList<>();
		CheckListProcesoFormularioEt objFrm;
		CheckListProcesoEjecucionFormularioEt obj;
		List<CheckListProcesoEjecucionFormularioEt> frms = new ArrayList<>();
		try {
			frms = iCheckListProEjeFormDao.getFrm(procesoCheckList.getIdCheckListProcesoEjecucion());
			for (int i = 0; i < frms.size(); i++) {

				if (i == 0) {
					pro02Frm01V = true;
					obj = procesoCheckList.getCheckListProcesoEjecucionFormulario().get(i);
					objFrm = obj.getCheckListProcesoFormulario();
					pro02Frm01 = objFrm;
					cargarItemsPro02Frm01(i, procesoCheckList, objFrm);
				}
				if (i == 1) {
					pro02Frm02V = true;
					obj = procesoCheckList.getCheckListProcesoEjecucionFormulario().get(i);
					objFrm = obj.getCheckListProcesoFormulario();
					pro02Frm02 = objFrm;
					cargarItemsPro02Frm01(i, procesoCheckList, objFrm);
				}
				if (i == 2) {
					pro02Frm03V = true;
					obj = procesoCheckList.getCheckListProcesoEjecucionFormulario().get(i);
					objFrm = obj.getCheckListProcesoFormulario();
					pro02Frm03 = objFrm;
					cargarItemsPro02Frm01(i, procesoCheckList, objFrm);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método proceso1 " + " " + e.getMessage());
		}
	}

	public void proceso3(CheckListProcesoEjecucionEt procesoCheckList) {
		pro03Frm01List = new ArrayList<>();
		pro03Frm02List = new ArrayList<>();
		pro03Frm03List = new ArrayList<>();
		CheckListProcesoFormularioEt objFrm;
		CheckListProcesoEjecucionFormularioEt obj;
		List<CheckListProcesoEjecucionFormularioEt> frms = new ArrayList<>();
		try {
			frms = iCheckListProEjeFormDao.getFrm(procesoCheckList.getIdCheckListProcesoEjecucion());
			for (int i = 0; i < frms.size(); i++) {

				if (i == 0) {
					pro03Frm01V = true;
					obj = procesoCheckList.getCheckListProcesoEjecucionFormulario().get(i);
					objFrm = obj.getCheckListProcesoFormulario();
					pro03Frm01 = objFrm;
					cargarItemsPro03Frm01(i, procesoCheckList, objFrm);
				}
				if (i == 1) {
					pro03Frm02V = true;
					obj = procesoCheckList.getCheckListProcesoEjecucionFormulario().get(i);
					objFrm = obj.getCheckListProcesoFormulario();
					pro03Frm02 = objFrm;
					cargarItemsPro03Frm01(i, procesoCheckList, objFrm);
				}
				if (i == 2) {
					pro03Frm03V = true;
					obj = procesoCheckList.getCheckListProcesoEjecucionFormulario().get(i);
					objFrm = obj.getCheckListProcesoFormulario();
					pro03Frm03 = objFrm;
					cargarItemsPro03Frm01(i, procesoCheckList, objFrm);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método proceso1 " + " " + e.getMessage());
		}
	}

	public void proceso4(CheckListProcesoEjecucionEt procesoCheckList) {
		pro04Frm01List = new ArrayList<>();
		pro04Frm02List = new ArrayList<>();
		pro04Frm03List = new ArrayList<>();
		CheckListProcesoFormularioEt objFrm;
		CheckListProcesoEjecucionFormularioEt obj;
		List<CheckListProcesoEjecucionFormularioEt> frms = new ArrayList<>();
		try {
			frms = iCheckListProEjeFormDao.getFrm(procesoCheckList.getIdCheckListProcesoEjecucion());
			for (int i = 0; i < frms.size(); i++) {

				if (i == 0) {
					pro04Frm01V = true;
					obj = procesoCheckList.getCheckListProcesoEjecucionFormulario().get(i);
					objFrm = obj.getCheckListProcesoFormulario();
					pro04Frm01 = objFrm;
					cargarItemsPro04Frm01(i, procesoCheckList, objFrm);
				}
				if (i == 1) {
					pro04Frm02V = true;
					obj = procesoCheckList.getCheckListProcesoEjecucionFormulario().get(i);
					objFrm = obj.getCheckListProcesoFormulario();
					pro04Frm02 = objFrm;
					cargarItemsPro04Frm01(i, procesoCheckList, objFrm);
				}
				if (i == 2) {
					pro04Frm03V = true;
					obj = procesoCheckList.getCheckListProcesoEjecucionFormulario().get(i);
					objFrm = obj.getCheckListProcesoFormulario();
					pro04Frm03 = objFrm;
					cargarItemsPro04Frm01(i, procesoCheckList, objFrm);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método proceso1 " + " " + e.getMessage());
		}
	}

	public void proceso5(CheckListProcesoEjecucionEt procesoCheckList) {
		pro05Frm01List = new ArrayList<>();
		pro05Frm02List = new ArrayList<>();
		pro05Frm03List = new ArrayList<>();
		CheckListProcesoFormularioEt objFrm;
		CheckListProcesoEjecucionFormularioEt obj;
		List<CheckListProcesoEjecucionFormularioEt> frms = new ArrayList<>();
		try {
			frms = iCheckListProEjeFormDao.getFrm(procesoCheckList.getIdCheckListProcesoEjecucion());
			for (int i = 0; i < frms.size(); i++) {
				if (i == 0) {
					pro05Frm01V = true;
					obj = procesoCheckList.getCheckListProcesoEjecucionFormulario().get(i);
					objFrm = obj.getCheckListProcesoFormulario();
					pro05Frm01 = objFrm;
					cargarItemsPro05Frm01(i, procesoCheckList, objFrm);
				}
				if (i == 1) {
					pro05Frm02V = true;
					obj = procesoCheckList.getCheckListProcesoEjecucionFormulario().get(i);
					objFrm = obj.getCheckListProcesoFormulario();
					pro05Frm02 = objFrm;
					cargarItemsPro05Frm01(i, procesoCheckList, objFrm);
				}
				if (i == 2) {
					pro05Frm03V = true;
					obj = procesoCheckList.getCheckListProcesoEjecucionFormulario().get(i);
					objFrm = obj.getCheckListProcesoFormulario();
					pro05Frm03 = objFrm;
					cargarItemsPro05Frm01(i, procesoCheckList, objFrm);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método proceso5 " + " " + e.getMessage());
		}
	}

	public void cargarItemsPro01Frm01(int orden, CheckListProcesoEjecucionEt checkListProFrm,
			CheckListProcesoFormularioEt formulario) {
		try {
			List<CheckListProcesoEjecucionFormularioEt> list = iCheckListProEjeFormDao.getFrm(
					checkListProFrm.getIdCheckListProcesoEjecucion(), formulario.getIdCheckListProcesoFormulario());
			if (orden == 0) {
				pro01Frm01List.addAll(list);
			}
			if (orden == 1) {
				pro01Frm02List.addAll(list);
			}
			if (orden == 2) {
				pro01Frm03List.addAll(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método cargarItemsPro01Frm01 " + " " + e.getMessage());
		}

	}

	public void cargarItemsPro02Frm01(int orden, CheckListProcesoEjecucionEt checkListProFrm,
			CheckListProcesoFormularioEt formulario) {

		try {
			List<CheckListProcesoEjecucionFormularioEt> list = iCheckListProEjeFormDao.getFrm(
					checkListProFrm.getIdCheckListProcesoEjecucion(), formulario.getIdCheckListProcesoFormulario());
			if (orden == 0) {
				pro02Frm01List.addAll(list);
			}
			if (orden == 1) {
				pro02Frm02List.addAll(list);
			}
			if (orden == 2) {
				pro02Frm03List.addAll(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método cargarItemsPro01Frm01 " + " " + e.getMessage());
		}

	}

	public void cargarItemsPro03Frm01(int orden, CheckListProcesoEjecucionEt checkListProFrm,
			CheckListProcesoFormularioEt formulario) {
		try {
			List<CheckListProcesoEjecucionFormularioEt> list = iCheckListProEjeFormDao.getFrm(
					checkListProFrm.getIdCheckListProcesoEjecucion(), formulario.getIdCheckListProcesoFormulario());
			if (orden == 0) {
				pro03Frm01List.addAll(list);
			}
			if (orden == 1) {
				pro03Frm02List.addAll(list);
			}
			if (orden == 2) {
				pro03Frm03List.addAll(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método cargarItemsPro01Frm01 " + " " + e.getMessage());
		}

	}

	public void cargarItemsPro04Frm01(int orden, CheckListProcesoEjecucionEt checkListProFrm,
			CheckListProcesoFormularioEt formulario) {

		try {
			List<CheckListProcesoEjecucionFormularioEt> list = iCheckListProEjeFormDao.getFrm(
					checkListProFrm.getIdCheckListProcesoEjecucion(), formulario.getIdCheckListProcesoFormulario());
			if (orden == 0) {
				pro04Frm01List.addAll(list);
			}
			if (orden == 1) {
				pro04Frm02List.addAll(list);
			}
			if (orden == 2) {
				pro04Frm03List.addAll(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método cargarItemsPro01Frm01 " + " " + e.getMessage());
		}

	}

	public void cargarItemsPro05Frm01(int orden, CheckListProcesoEjecucionEt checkListProFrm,
			CheckListProcesoFormularioEt formulario) {

		try {
			List<CheckListProcesoEjecucionFormularioEt> list = iCheckListProEjeFormDao.getFrm(
					checkListProFrm.getIdCheckListProcesoEjecucion(), formulario.getIdCheckListProcesoFormulario());
			if (orden == 0) {
				pro05Frm01List.addAll(list);
			}
			if (orden == 1) {
				pro05Frm02List.addAll(list);
			}
			if (orden == 2) {
				pro05Frm03List.addAll(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método cargarItemsPro05Frm01 " + " " + e.getMessage());
		}

	}

	public void anadirPro01Frm1() {
		String mensaje = "";
		try {
			mensaje = validacionPro01Frm01(0L);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			CheckListProcesoEjecucionFormularioEt checkListProFrm = new CheckListProcesoEjecucionFormularioEt();
			checkListProFrm.setCheckListProcesoEjecucion(pro01Frm01List.get(0).getCheckListProcesoEjecucion());
			checkListProFrm.setCheckListProcesoFormulario(pro01Frm01List.get(0).getCheckListProcesoFormulario());
			pro01Frm01List.add(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirPro01Frm1 " + " " + e.getMessage());
		}
	}

	public void anadirPro01Frm2() {
		String mensaje = "";
		try {
			mensaje = validacionPro01Frm01(1L);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			CheckListProcesoEjecucionFormularioEt checkListProFrm = new CheckListProcesoEjecucionFormularioEt();
			checkListProFrm.setCheckListProcesoEjecucion(pro01Frm02List.get(0).getCheckListProcesoEjecucion());
			checkListProFrm.setCheckListProcesoFormulario(pro01Frm02List.get(0).getCheckListProcesoFormulario());
			pro01Frm02List.add(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirPro01Frm2 " + " " + e.getMessage());
		}
	}

	public void anadirPro01Frm3() {
		String mensaje = "";
		try {
			mensaje = validacionPro01Frm01(2L);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			CheckListProcesoEjecucionFormularioEt checkListProFrm = new CheckListProcesoEjecucionFormularioEt();
			checkListProFrm.setCheckListProcesoEjecucion(pro01Frm03List.get(0).getCheckListProcesoEjecucion());
			checkListProFrm.setCheckListProcesoFormulario(pro01Frm03List.get(0).getCheckListProcesoFormulario());
			pro01Frm03List.add(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirPro01Frm2 " + " " + e.getMessage());
		}
	}

	public void anadirPro02Frm1() {
		String mensaje = "";
		try {
			mensaje = validacionPro02Frm01(0L);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			CheckListProcesoEjecucionFormularioEt checkListProFrm = new CheckListProcesoEjecucionFormularioEt();
			checkListProFrm.setCheckListProcesoEjecucion(pro02Frm01List.get(0).getCheckListProcesoEjecucion());
			checkListProFrm.setCheckListProcesoFormulario(pro02Frm01List.get(0).getCheckListProcesoFormulario());
			pro02Frm01List.add(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirPro02Frm1 " + " " + e.getMessage());
		}
	}

	public void anadirPro02Frm2() {
		String mensaje = "";
		try {
			mensaje = validacionPro02Frm01(1L);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			CheckListProcesoEjecucionFormularioEt checkListProFrm = new CheckListProcesoEjecucionFormularioEt();
			checkListProFrm.setCheckListProcesoEjecucion(pro02Frm02List.get(0).getCheckListProcesoEjecucion());
			checkListProFrm.setCheckListProcesoFormulario(pro02Frm02List.get(0).getCheckListProcesoFormulario());
			pro02Frm02List.add(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirPro02Frm2 " + " " + e.getMessage());
		}
	}

	public void anadirPro02Frm3() {
		String mensaje = "";
		try {
			mensaje = validacionPro02Frm01(2L);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			CheckListProcesoEjecucionFormularioEt checkListProFrm = new CheckListProcesoEjecucionFormularioEt();
			checkListProFrm.setCheckListProcesoEjecucion(pro02Frm03List.get(0).getCheckListProcesoEjecucion());
			checkListProFrm.setCheckListProcesoFormulario(pro02Frm03List.get(0).getCheckListProcesoFormulario());
			pro02Frm03List.add(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirPro02Frm3 " + " " + e.getMessage());
		}
	}

	public void anadirPro03Frm1() {
		String mensaje = "";
		try {
			mensaje = validacionPro03Frm01(0L);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			CheckListProcesoEjecucionFormularioEt checkListProFrm = new CheckListProcesoEjecucionFormularioEt();
			checkListProFrm.setCheckListProcesoEjecucion(pro03Frm01List.get(0).getCheckListProcesoEjecucion());
			checkListProFrm.setCheckListProcesoFormulario(pro03Frm01List.get(0).getCheckListProcesoFormulario());
			pro03Frm01List.add(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirPro01Frm1 " + " " + e.getMessage());
		}
	}

	public void anadirPro03Frm2() {
		String mensaje = "";
		try {
			mensaje = validacionPro03Frm01(1L);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			CheckListProcesoEjecucionFormularioEt checkListProFrm = new CheckListProcesoEjecucionFormularioEt();
			checkListProFrm.setCheckListProcesoEjecucion(pro03Frm02List.get(0).getCheckListProcesoEjecucion());
			checkListProFrm.setCheckListProcesoFormulario(pro03Frm02List.get(0).getCheckListProcesoFormulario());
			pro03Frm02List.add(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirPro01Frm2 " + " " + e.getMessage());
		}
	}

	public void anadirPro03Frm3() {
		String mensaje = "";
		try {
			mensaje = validacionPro03Frm01(2L);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			CheckListProcesoEjecucionFormularioEt checkListProFrm = new CheckListProcesoEjecucionFormularioEt();
			checkListProFrm.setCheckListProcesoEjecucion(pro03Frm03List.get(0).getCheckListProcesoEjecucion());
			checkListProFrm.setCheckListProcesoFormulario(pro03Frm03List.get(0).getCheckListProcesoFormulario());
			pro03Frm03List.add(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirPro01Frm2 " + " " + e.getMessage());
		}
	}

	public void anadirPro04Frm1() {
		String mensaje = "";
		try {
			mensaje = validacionPro04Frm01(0L);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			CheckListProcesoEjecucionFormularioEt checkListProFrm = new CheckListProcesoEjecucionFormularioEt();
			checkListProFrm.setCheckListProcesoEjecucion(pro04Frm01List.get(0).getCheckListProcesoEjecucion());
			checkListProFrm.setCheckListProcesoFormulario(pro04Frm01List.get(0).getCheckListProcesoFormulario());
			pro04Frm01List.add(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirPro01Frm1 " + " " + e.getMessage());
		}
	}

	public void anadirPro04Frm2() {
		String mensaje = "";
		try {
			mensaje = validacionPro04Frm01(1L);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			CheckListProcesoEjecucionFormularioEt checkListProFrm = new CheckListProcesoEjecucionFormularioEt();
			checkListProFrm.setCheckListProcesoEjecucion(pro04Frm02List.get(0).getCheckListProcesoEjecucion());
			checkListProFrm.setCheckListProcesoFormulario(pro04Frm02List.get(0).getCheckListProcesoFormulario());
			pro04Frm02List.add(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirPro01Frm2 " + " " + e.getMessage());
		}
	}

	public void anadirPro04Frm3() {
		String mensaje = "";
		try {
			mensaje = validacionPro01Frm01(2L);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			CheckListProcesoEjecucionFormularioEt checkListProFrm = new CheckListProcesoEjecucionFormularioEt();
			checkListProFrm.setCheckListProcesoEjecucion(pro04Frm03List.get(0).getCheckListProcesoEjecucion());
			checkListProFrm.setCheckListProcesoFormulario(pro04Frm03List.get(0).getCheckListProcesoFormulario());
			pro04Frm03List.add(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirPro01Frm2 " + " " + e.getMessage());
		}
	}

	public void anadirPro05Frm1() {
		String mensaje = "";
		try {
			mensaje = validacionPro04Frm01(0L);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			CheckListProcesoEjecucionFormularioEt checkListProFrm = new CheckListProcesoEjecucionFormularioEt();
			checkListProFrm.setCheckListProcesoEjecucion(pro05Frm01List.get(0).getCheckListProcesoEjecucion());
			checkListProFrm.setCheckListProcesoFormulario(pro05Frm01List.get(0).getCheckListProcesoFormulario());
			pro05Frm01List.add(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirPro01Frm1 " + " " + e.getMessage());
		}
	}

	public void anadirPro05Frm2() {
		String mensaje = "";
		try {
			mensaje = validacionPro04Frm01(1L);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			CheckListProcesoEjecucionFormularioEt checkListProFrm = new CheckListProcesoEjecucionFormularioEt();
			checkListProFrm.setCheckListProcesoEjecucion(pro05Frm02List.get(0).getCheckListProcesoEjecucion());
			checkListProFrm.setCheckListProcesoFormulario(pro05Frm02List.get(0).getCheckListProcesoFormulario());
			pro05Frm02List.add(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirPro01Frm2 " + " " + e.getMessage());
		}
	}

	public void anadirPro05Frm3() {
		String mensaje = "";
		try {
			mensaje = validacionPro01Frm01(2L);
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_INFO);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			CheckListProcesoEjecucionFormularioEt checkListProFrm = new CheckListProcesoEjecucionFormularioEt();
			checkListProFrm.setCheckListProcesoEjecucion(pro05Frm03List.get(0).getCheckListProcesoEjecucion());
			checkListProFrm.setCheckListProcesoFormulario(pro05Frm03List.get(0).getCheckListProcesoFormulario());
			pro05Frm03List.add(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método anadirPro05Frm3 " + " " + e.getMessage());
		}
	}

	public void quitarPro01Frm1(int orden, CheckListProcesoEjecucionFormularioEt checkListProFrm) {
		try {
			if (orden == 0) {
				if (pro01Frm01List.size() == 1) {
					showInfo("Item no puede ser eliminado ", FacesMessage.SEVERITY_INFO);
					return;
				}
			}
			if (orden == 1) {
				if (pro01Frm02List.size() == 1) {
					showInfo("Item no puede ser eliminado ", FacesMessage.SEVERITY_INFO);
					return;
				}
			}
			if (orden == 2) {
				if (pro01Frm03List.size() == 1) {
					showInfo("Item no puede ser eliminado ", FacesMessage.SEVERITY_INFO);
					return;
				}
			}
			UsuarioEt usuario = appMain.getUsuario();
			checkListProFrm.setEstado(EstadoEnum.INA);
			Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
			checkListProFrm.setFechaModificacion(fechaRegistro);
			CheckListProcesoEjecucionEt checkListProcesoEjecucion = checkListProFrm.getCheckListProcesoEjecucion();
			checkListProcesoEjecucion.getCheckListProcesoEjecucionFormulario().remove(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
			iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucion, usuario);
			cargarItemsPro01Frm01(orden, checkListProFrm.getCheckListProcesoEjecucion(),
					checkListProFrm.getCheckListProcesoFormulario());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método quitarPro01Frm1 " + " " + e.getMessage());
		}

	}

	public void quitarPro02Frm1(int orden, CheckListProcesoEjecucionFormularioEt checkListProFrm) {
		try {
			if (orden == 0) {
				if (pro02Frm01List.size() == 1) {
					showInfo("Item no puede ser eliminado ", FacesMessage.SEVERITY_INFO);
					return;
				}
			}
			if (orden == 1) {
				if (pro02Frm02List.size() == 1) {
					showInfo("Item no puede ser eliminado ", FacesMessage.SEVERITY_INFO);
					return;
				}
			}
			if (orden == 2) {
				if (pro02Frm03List.size() == 1) {
					showInfo("Item no puede ser eliminado ", FacesMessage.SEVERITY_INFO);
					return;
				}
			}
			UsuarioEt usuario = appMain.getUsuario();
			Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
			checkListProFrm.setFechaModificacion(fechaRegistro);
			checkListProFrm.setEstado(EstadoEnum.INA);
			CheckListProcesoEjecucionEt checkListProcesoEjecucion = checkListProFrm.getCheckListProcesoEjecucion();
			checkListProcesoEjecucion.getCheckListProcesoEjecucionFormulario().remove(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
			iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucion, usuario);
			cargarItemsPro02Frm01(orden, checkListProFrm.getCheckListProcesoEjecucion(),
					checkListProFrm.getCheckListProcesoFormulario());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método quitarPro02Frm1 " + " " + e.getMessage());
		}

	}

	public void quitarPro03Frm1(int orden, CheckListProcesoEjecucionFormularioEt checkListProFrm) {
		try {
			if (orden == 0) {
				if (pro03Frm01List.size() == 1) {
					showInfo("Item no puede ser eliminado ", FacesMessage.SEVERITY_INFO);
					return;
				}
			}
			if (orden == 1) {
				if (pro03Frm02List.size() == 1) {
					showInfo("Item no puede ser eliminado ", FacesMessage.SEVERITY_INFO);
					return;
				}
			}
			if (orden == 2) {
				if (pro03Frm03List.size() == 1) {
					showInfo("Item no puede ser eliminado ", FacesMessage.SEVERITY_INFO);
					return;
				}
			}
			UsuarioEt usuario = appMain.getUsuario();
			Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
			checkListProFrm.setFechaModificacion(fechaRegistro);
			checkListProFrm.setEstado(EstadoEnum.INA);
			CheckListProcesoEjecucionEt checkListProcesoEjecucion = checkListProFrm.getCheckListProcesoEjecucion();
			checkListProcesoEjecucion.getCheckListProcesoEjecucionFormulario().remove(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
			iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucion, usuario);
			cargarItemsPro03Frm01(orden, checkListProFrm.getCheckListProcesoEjecucion(),
					checkListProFrm.getCheckListProcesoFormulario());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método quitarPro02Frm1 " + " " + e.getMessage());
		}

	}

	public void quitarPro04Frm1(int orden, CheckListProcesoEjecucionFormularioEt checkListProFrm) {
		try {
			if (orden == 0) {
				if (pro04Frm01List.size() == 1) {
					showInfo("Item no puede ser eliminado ", FacesMessage.SEVERITY_INFO);
					return;
				}
			}
			if (orden == 1) {
				if (pro04Frm02List.size() == 1) {
					showInfo("Item no puede ser eliminado ", FacesMessage.SEVERITY_INFO);
					return;
				}
			}
			if (orden == 2) {
				if (pro04Frm03List.size() == 1) {
					showInfo("Item no puede ser eliminado ", FacesMessage.SEVERITY_INFO);
					return;
				}
			}
			UsuarioEt usuario = appMain.getUsuario();
			Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
			checkListProFrm.setFechaModificacion(fechaRegistro);
			checkListProFrm.setEstado(EstadoEnum.INA);
			CheckListProcesoEjecucionEt checkListProcesoEjecucion = checkListProFrm.getCheckListProcesoEjecucion();
			checkListProcesoEjecucion.getCheckListProcesoEjecucionFormulario().remove(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
			iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucion, usuario);
			cargarItemsPro04Frm01(orden, checkListProFrm.getCheckListProcesoEjecucion(),
					checkListProFrm.getCheckListProcesoFormulario());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método quitarPro02Frm1 " + " " + e.getMessage());
		}

	}

	public void quitarPro05Frm1(int orden, CheckListProcesoEjecucionFormularioEt checkListProFrm) {
		try {
			if (orden == 0) {
				if (pro05Frm01List.size() == 1) {
					showInfo("Item no puede ser eliminado ", FacesMessage.SEVERITY_INFO);
					return;
				}
			}
			if (orden == 1) {
				if (pro05Frm02List.size() == 1) {
					showInfo("Item no puede ser eliminado ", FacesMessage.SEVERITY_INFO);
					return;
				}
			}
			if (orden == 2) {
				if (pro05Frm03List.size() == 1) {
					showInfo("Item no puede ser eliminado ", FacesMessage.SEVERITY_INFO);
					return;
				}
			}
			UsuarioEt usuario = appMain.getUsuario();
			Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
			checkListProFrm.setFechaModificacion(fechaRegistro);
			checkListProFrm.setEstado(EstadoEnum.INA);
			CheckListProcesoEjecucionEt checkListProcesoEjecucion = checkListProFrm.getCheckListProcesoEjecucion();
			checkListProcesoEjecucion.getCheckListProcesoEjecucionFormulario().remove(checkListProFrm);
			iCheckListProEjeFormDao.guardarCheckListProEjeForm(checkListProFrm, usuario);
			iCheckListProcesoEjecucionDao.guardarCheckListProcesoEjecucion(checkListProcesoEjecucion, usuario);
			cargarItemsPro05Frm01(orden, checkListProFrm.getCheckListProcesoEjecucion(),
					checkListProFrm.getCheckListProcesoFormulario());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método quitarPro02Frm1 " + " " + e.getMessage());
		}

	}

	public void guardarPro01Frm01() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			for (CheckListProcesoEjecucionFormularioEt formulario : pro01Frm01List) {
				iCheckListProEjeFormDao.guardarCheckListProEjeForm(formulario, usuario);
			}
			for (CheckListProcesoEjecucionFormularioEt formulario : pro01Frm02List) {
				iCheckListProEjeFormDao.guardarCheckListProEjeForm(formulario, usuario);
			}
			for (CheckListProcesoEjecucionFormularioEt formulario : pro01Frm03List) {
				iCheckListProEjeFormDao.guardarCheckListProEjeForm(formulario, usuario);
			}
			RequestContext.getCurrentInstance().execute("PF('dlg_eje_001_1').hide();");
			cargarForularioCheckList(checkListEjecucion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarPro01Frm01 " + " " + e.getMessage());
		}

	}

	public String validacionPro01Frm01(Long orden) {
		String mensaje = "";
		try {
			if (orden == 0 && pro01Frm01List.size() == 10) {
				mensaje = "Máximo de items que puede ingresar son 10";
				return mensaje;
			}
			if (orden == 1 && pro01Frm02List.size() == 10) {
				mensaje = "Máximo de items que puede ingresar son 10";
				return mensaje;
			}
			if (orden == 2 && pro01Frm03List.size() == 10) {
				mensaje = "Máximo de items que puede ingresar son 10";
				return mensaje;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validacionPro01Frm01 " + " " + e.getMessage());
		}
		return mensaje;
	}

	public void guardarPro02Frm01() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			for (CheckListProcesoEjecucionFormularioEt formulario : pro02Frm01List) {
				iCheckListProEjeFormDao.guardarCheckListProEjeForm(formulario, usuario);
			}
			for (CheckListProcesoEjecucionFormularioEt formulario : pro02Frm02List) {
				iCheckListProEjeFormDao.guardarCheckListProEjeForm(formulario, usuario);
			}
			for (CheckListProcesoEjecucionFormularioEt formulario : pro02Frm03List) {
				iCheckListProEjeFormDao.guardarCheckListProEjeForm(formulario, usuario);
			}
			RequestContext.getCurrentInstance().execute("PF('dlg_eje_001_2').hide();");
			cargarForularioCheckList(checkListEjecucion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarPro02Frm01 " + " " + e.getMessage());
		}

	}

	public String validacionPro02Frm01(Long orden) {
		String mensaje = "";
		try {
			if (orden == 0 && pro02Frm01List.size() == 10) {
				mensaje = "Máximo de items que puede ingresar son 10";
				return mensaje;
			}
			if (orden == 1 && pro02Frm02List.size() == 10) {
				mensaje = "Máximo de items que puede ingresar son 10";
				return mensaje;
			}
			if (orden == 2 && pro02Frm03List.size() == 10) {
				mensaje = "Máximo de items que puede ingresar son 10";
				return mensaje;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validacionPro01Frm01 " + " " + e.getMessage());
		}
		return mensaje;
	}

	public void guardarPro03Frm01() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			for (CheckListProcesoEjecucionFormularioEt formulario : pro03Frm01List) {
				iCheckListProEjeFormDao.guardarCheckListProEjeForm(formulario, usuario);
			}
			for (CheckListProcesoEjecucionFormularioEt formulario : pro03Frm02List) {
				iCheckListProEjeFormDao.guardarCheckListProEjeForm(formulario, usuario);
			}
			for (CheckListProcesoEjecucionFormularioEt formulario : pro03Frm03List) {
				iCheckListProEjeFormDao.guardarCheckListProEjeForm(formulario, usuario);
			}
			RequestContext.getCurrentInstance().execute("PF('dlg_eje_001_3').hide();");
			cargarForularioCheckList(checkListEjecucion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarPro01Frm01 " + " " + e.getMessage());
		}

	}

	public String validacionPro03Frm01(Long orden) {
		String mensaje = "";
		try {
			if (orden == 0 && pro03Frm01List.size() == 10) {
				mensaje = "Máximo de items que puede ingresar son 10";
				return mensaje;
			}
			if (orden == 1 && pro03Frm02List.size() == 10) {
				mensaje = "Máximo de items que puede ingresar son 10";
				return mensaje;
			}
			if (orden == 2 && pro03Frm03List.size() == 10) {
				mensaje = "Máximo de items que puede ingresar son 10";
				return mensaje;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validacionPro01Frm01 " + " " + e.getMessage());
		}
		return mensaje;
	}

	public void guardarPro04Frm01() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			for (CheckListProcesoEjecucionFormularioEt formulario : pro04Frm01List) {
				iCheckListProEjeFormDao.guardarCheckListProEjeForm(formulario, usuario);
			}
			for (CheckListProcesoEjecucionFormularioEt formulario : pro04Frm02List) {
				iCheckListProEjeFormDao.guardarCheckListProEjeForm(formulario, usuario);
			}
			for (CheckListProcesoEjecucionFormularioEt formulario : pro04Frm03List) {
				iCheckListProEjeFormDao.guardarCheckListProEjeForm(formulario, usuario);
			}
			RequestContext.getCurrentInstance().execute("PF('dlg_eje_001_4').hide();");
			cargarForularioCheckList(checkListEjecucion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarPro01Frm01 " + " " + e.getMessage());
		}

	}

	public String validacionPro04Frm01(Long orden) {
		String mensaje = "";
		try {
			if (orden == 0 && pro04Frm01List.size() == 10) {
				mensaje = "Máximo de items que puede ingresar son 10";
				return mensaje;
			}
			if (orden == 1 && pro04Frm02List.size() == 10) {
				mensaje = "Máximo de items que puede ingresar son 10";
				return mensaje;
			}
			if (orden == 2 && pro04Frm03List.size() == 10) {
				mensaje = "Máximo de items que puede ingresar son 10";
				return mensaje;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validacionPro01Frm01 " + " " + e.getMessage());
		}
		return mensaje;
	}

	public void guardarPro05Frm01() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			for (CheckListProcesoEjecucionFormularioEt formulario : pro05Frm01List) {
				iCheckListProEjeFormDao.guardarCheckListProEjeForm(formulario, usuario);
			}
			for (CheckListProcesoEjecucionFormularioEt formulario : pro05Frm02List) {
				iCheckListProEjeFormDao.guardarCheckListProEjeForm(formulario, usuario);
			}
			for (CheckListProcesoEjecucionFormularioEt formulario : pro05Frm03List) {
				iCheckListProEjeFormDao.guardarCheckListProEjeForm(formulario, usuario);
			}
			RequestContext.getCurrentInstance().execute("PF('dlg_eje_001_5').hide();");
			cargarForularioCheckList(checkListEjecucion);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarPro01Frm01 " + " " + e.getMessage());
		}

	}

	public String validacionPro05Frm01(Long orden) {
		String mensaje = "";
		try {
			if (orden == 0 && pro05Frm01List.size() == 10) {
				mensaje = "Máximo de items que puede ingresar son 10";
				return mensaje;
			}
			if (orden == 1 && pro05Frm02List.size() == 10) {
				mensaje = "Máximo de items que puede ingresar son 10";
				return mensaje;
			}
			if (orden == 2 && pro05Frm03List.size() == 10) {
				mensaje = "Máximo de items que puede ingresar son 10";
				return mensaje;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validacionPro01Frm01 " + " " + e.getMessage());
		}
		return mensaje;
	}

	public void imprimir() {
		ResponsableEt responsable = null;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			checkListEjecucionImprimir = checkListEjecucion;

			for (CheckListProcesoEjecucionEt proceso : checkListEjecucion.getCheckListProcesoEjecucion()) {
				if (proceso.isVisualizarReporte()) {
					for (CheckListKpiEjecucionEt kpi : proceso.getCheckListKpiEjecucion()) {
						if (kpi.getCheckListKpi().isVisualizarReporte()) {
							kpi.setSeccion(reemplazarEtiquetaKPI(kpi.getCheckListKpi().getSeccion(), kpi));
							kpi.setSeccion0(reemplazarEtiquetaKPI(kpi.getCheckListKpi().getSeccion0(), kpi));
							iCheckListKpiEjecucionDao.guardarCheckListKpiEjecucion(kpi, usuario);
						}
					}
				}
			}
			for (CheckListPieFirmaEjecucionEt pieFirma : checkListEjecucion.getCheckListPieFirmaEjecucion()) {
				responsable = iResponsableDao.getResponsableByCargo(checkListEjecucion.getPlanificacion().getAgencia(),
						pieFirma.getTipoCargo().getIdTipoCargo());
				if (responsable != null) {
					pieFirma.setResponsable(responsable.getPersona().getNombreCompleto());
					iCheckListPieFirmaEjecucionDao.guardarcheckListPieFirma(pieFirma, usuario);
				}
			}
			for (CheckListInformeCabeceraEjecucionEt cabecera : checkListEjecucion
					.getCheckListInformeCabeceraEjecucion()) {
				if (cabecera.getTitulo().contains("{")) {
					cabecera.setTitulo(reemplazarEtiqueta(cabecera.getTitulo(), checkListEjecucion));
					iCheckListInfoCabEjeDao.guardarCheckListInfoCabEje(cabecera, usuario);
				}
				if (cabecera.getContenido().contains("{")) {
					cabecera.setTitulo(reemplazarEtiqueta(cabecera.getTitulo(), checkListEjecucion));
					cabecera.setContenido(reemplazarEtiqueta(cabecera.getContenido(), checkListEjecucion));
					iCheckListInfoCabEjeDao.guardarCheckListInfoCabEje(cabecera, usuario);
				}

			}
			iCheckListEjecucionDao.guardarCheckListEjecucion(checkListEjecucionImprimir, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método imprimir" + " " + e.getMessage());
		}
	}

	public String reemplazarEtiqueta(String original, CheckListEjecucionEt checkListEje) {
		String reemplazo = "";
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		try {
			reemplazo = original;
			if (reemplazo.contains("{nivel_evaluacion}")) {
				reemplazo = reemplazo.replace("{nivel_evaluacion}", checkListEje.getNivelEvaluacion().getDescripcion());
			}
			if (reemplazo.contains("{evaluacion}")) {
				reemplazo = reemplazo.replace("{evaluacion}", checkListEje.getEvaluacion().getDescripcion());
			}
			if (reemplazo.contains("{tipo_check_list}")) {
				reemplazo = reemplazo.replace("{tipo_check_list}", checkListEje.getTipoChecKList().getDescripcion());
			}
			if (reemplazo.contains("{fecha_auditoria}")) {
				reemplazo = reemplazo.replace("{fecha_auditoria}", format.format(checkListEje.getFechaEjecucion()));
			}
			if (reemplazo.contains("{nombre_estacion}")) {
				reemplazo = reemplazo.replace("{nombre_estacion}",
						checkListEje.getPlanificacion().getAgencia().getNombreAgencia());
			}
			// ------------------------------------------------------------------------------------------------------
			if (reemplazo.contains("{gerente}")) {
				reemplazo = reemplazo.replace("{gerente}", iResponsableDao.getResponsableEtiqueta("{gerente}"));
			}
			if (reemplazo.contains("{verificador}")) {
				reemplazo = reemplazo.replace("{verificador}", iResponsableDao.getResponsableEtiqueta("{verificador}"));
			}
			if (reemplazo.contains("{soporte_operativo}")) {
				reemplazo = reemplazo.replace("{soporte_operativo}",
						iResponsableDao.getResponsableEtiqueta("{soporte_operativo}"));
			}
			if (reemplazo.contains("{soporte_administrativo}")) {
				reemplazo = reemplazo.replace("{soporte_administrativo}",
						iResponsableDao.getResponsableEtiqueta("{soporte_administrativo}"));
			}
			if (reemplazo.contains("{supervisor_efectivo}")) {
				reemplazo = reemplazo.replace("{supervisor_efectivo}",
						iResponsableDao.getResponsableEtiqueta("{supervisor_efectivo}"));
			}
			if (reemplazo.contains("{jefe_operacion_tienda}")) {
				reemplazo = reemplazo.replace("{jefe_operacion_tienda}",
						iResponsableDao.getResponsableEtiqueta("{jefe_operacion_tienda}"));
			}
			if (reemplazo.contains("{jefe_operacion_pista}")) {
				reemplazo = reemplazo.replace("{jefe_operacion_pista}",
						iResponsableDao.getResponsableEtiqueta("{jefe_operacion_pista}"));
			}
			// ------------------------------------------------------------------------------------------------------

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método reemplazarEtiquetaTitulo" + " " + e.getMessage());
		}
		return reemplazo;
	}

	public String reemplazarEtiquetaKPI(String original, CheckListKpiEjecucionEt checkListKpiEje) {
		String reemplazo = "";
		try {
			if (original == null) {
				original = "";
			}
			reemplazo = original;
			if (reemplazo.contains("{criterio_evaluacion}") && checkListKpiEje.getCriterioEvaluacionDetalle() != null) {
				reemplazo = reemplazo.replace("{criterio_evaluacion}",
						checkListKpiEje.getCriterioEvaluacionDetalle().getNombre());
			}
			if (reemplazo.contains("{comentario_auditor}") && checkListKpiEje.getComentarioControl() != null) {
				reemplazo = reemplazo.replace("{comentario_auditor}", checkListKpiEje.getComentarioControl());
			}
			if (reemplazo.contains("{comentario_adminstrativo}") && checkListKpiEje.getComentarioEstacion() != null) {
				reemplazo = reemplazo.replace("{comentario_adminstrativo}", checkListKpiEje.getComentarioEstacion());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método reemplazarEtiquetaKPI" + " " + e.getMessage());
		}
		return reemplazo;
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

	public CriterioEvaluacionDetalleEt getCriterioEvaluacionDetalleSeleccionado() {
		return criterioEvaluacionDetalleSeleccionado;
	}

	public void setCriterioEvaluacionDetalleSeleccionado(
			CriterioEvaluacionDetalleEt criterioEvaluacionDetalleSeleccionado) {
		this.criterioEvaluacionDetalleSeleccionado = criterioEvaluacionDetalleSeleccionado;
	}

	public Long getTotalPuntajeCalificacion() {
		return totalPuntajeCalificacion;
	}

	public void setTotalPuntajeCalificacion(Long totalPuntajeCalificacion) {
		this.totalPuntajeCalificacion = totalPuntajeCalificacion;
	}

	public boolean isValidarGuardar() {
		return validarGuardar;
	}

	public void setValidarGuardar(boolean validarGuardar) {
		this.validarGuardar = validarGuardar;
	}

	public CheckListEjecucionReporteEt getCheckListEjecucionReporteSeleccionado() {
		return checkListEjecucionReporteSeleccionado;
	}

	public void setCheckListEjecucionReporteSeleccionado(
			CheckListEjecucionReporteEt checkListEjecucionReporteSeleccionado) {
		this.checkListEjecucionReporteSeleccionado = checkListEjecucionReporteSeleccionado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public CheckListEjecucionEt getCheckListEjecucionImprimir() {
		return checkListEjecucionImprimir;
	}

	public void setCheckListEjecucionImprimir(CheckListEjecucionEt checkListEjecucionImprimir) {
		this.checkListEjecucionImprimir = checkListEjecucionImprimir;
	}

	public boolean isPro01Frm01V() {
		return pro01Frm01V;
	}

	public void setPro01Frm01V(boolean pro01Frm01V) {
		this.pro01Frm01V = pro01Frm01V;
	}

	public boolean isPro01Frm02V() {
		return pro01Frm02V;
	}

	public void setPro01Frm02V(boolean pro01Frm02V) {
		this.pro01Frm02V = pro01Frm02V;
	}

	public boolean isPro01Frm03V() {
		return pro01Frm03V;
	}

	public void setPro01Frm03V(boolean pro01Frm03V) {
		this.pro01Frm03V = pro01Frm03V;
	}

	public CheckListProcesoFormularioEt getPro01Frm01() {
		return pro01Frm01;
	}

	public void setPro01Frm01(CheckListProcesoFormularioEt pro01Frm01) {
		this.pro01Frm01 = pro01Frm01;
	}

	public CheckListProcesoFormularioEt getPro01Frm02() {
		return pro01Frm02;
	}

	public void setPro01Frm02(CheckListProcesoFormularioEt pro01Frm02) {
		this.pro01Frm02 = pro01Frm02;
	}

	public CheckListProcesoFormularioEt getPro01Frm03() {
		return pro01Frm03;
	}

	public void setPro01Frm03(CheckListProcesoFormularioEt pro01Frm03) {
		this.pro01Frm03 = pro01Frm03;
	}

	public List<CheckListProcesoEjecucionFormularioEt> getPro01Frm01List() {
		return pro01Frm01List;
	}

	public void setPro01Frm01List(List<CheckListProcesoEjecucionFormularioEt> pro01Frm01List) {
		this.pro01Frm01List = pro01Frm01List;
	}

	public List<CheckListProcesoEjecucionFormularioEt> getPro01Frm02List() {
		return pro01Frm02List;
	}

	public void setPro01Frm02List(List<CheckListProcesoEjecucionFormularioEt> pro01Frm02List) {
		this.pro01Frm02List = pro01Frm02List;
	}

	public List<CheckListProcesoEjecucionFormularioEt> getPro01Frm03List() {
		return pro01Frm03List;
	}

	public void setPro01Frm03List(List<CheckListProcesoEjecucionFormularioEt> pro01Frm03List) {
		this.pro01Frm03List = pro01Frm03List;
	}

	public boolean isPro02Frm01V() {
		return pro02Frm01V;
	}

	public void setPro02Frm01V(boolean pro02Frm01V) {
		this.pro02Frm01V = pro02Frm01V;
	}

	public boolean isPro02Frm02V() {
		return pro02Frm02V;
	}

	public void setPro02Frm02V(boolean pro02Frm02V) {
		this.pro02Frm02V = pro02Frm02V;
	}

	public boolean isPro02Frm03V() {
		return pro02Frm03V;
	}

	public void setPro02Frm03V(boolean pro02Frm03V) {
		this.pro02Frm03V = pro02Frm03V;
	}

	public CheckListProcesoFormularioEt getPro02Frm01() {
		return pro02Frm01;
	}

	public void setPro02Frm01(CheckListProcesoFormularioEt pro02Frm01) {
		this.pro02Frm01 = pro02Frm01;
	}

	public CheckListProcesoFormularioEt getPro02Frm02() {
		return pro02Frm02;
	}

	public void setPro02Frm02(CheckListProcesoFormularioEt pro02Frm02) {
		this.pro02Frm02 = pro02Frm02;
	}

	public CheckListProcesoFormularioEt getPro02Frm03() {
		return pro02Frm03;
	}

	public void setPro02Frm03(CheckListProcesoFormularioEt pro02Frm03) {
		this.pro02Frm03 = pro02Frm03;
	}

	public List<CheckListProcesoEjecucionFormularioEt> getPro02Frm01List() {
		return pro02Frm01List;
	}

	public void setPro02Frm01List(List<CheckListProcesoEjecucionFormularioEt> pro02Frm01List) {
		this.pro02Frm01List = pro02Frm01List;
	}

	public List<CheckListProcesoEjecucionFormularioEt> getPro02Frm02List() {
		return pro02Frm02List;
	}

	public void setPro02Frm02List(List<CheckListProcesoEjecucionFormularioEt> pro02Frm02List) {
		this.pro02Frm02List = pro02Frm02List;
	}

	public List<CheckListProcesoEjecucionFormularioEt> getPro02Frm03List() {
		return pro02Frm03List;
	}

	public void setPro02Frm03List(List<CheckListProcesoEjecucionFormularioEt> pro02Frm03List) {
		this.pro02Frm03List = pro02Frm03List;
	}

	public boolean isPro03Frm01V() {
		return pro03Frm01V;
	}

	public void setPro03Frm01V(boolean pro03Frm01V) {
		this.pro03Frm01V = pro03Frm01V;
	}

	public boolean isPro03Frm02V() {
		return pro03Frm02V;
	}

	public void setPro03Frm02V(boolean pro03Frm02V) {
		this.pro03Frm02V = pro03Frm02V;
	}

	public boolean isPro03Frm03V() {
		return pro03Frm03V;
	}

	public void setPro03Frm03V(boolean pro03Frm03V) {
		this.pro03Frm03V = pro03Frm03V;
	}

	public CheckListProcesoFormularioEt getPro03Frm01() {
		return pro03Frm01;
	}

	public void setPro03Frm01(CheckListProcesoFormularioEt pro03Frm01) {
		this.pro03Frm01 = pro03Frm01;
	}

	public CheckListProcesoFormularioEt getPro03Frm02() {
		return pro03Frm02;
	}

	public void setPro03Frm02(CheckListProcesoFormularioEt pro03Frm02) {
		this.pro03Frm02 = pro03Frm02;
	}

	public CheckListProcesoFormularioEt getPro03Frm03() {
		return pro03Frm03;
	}

	public void setPro03Frm03(CheckListProcesoFormularioEt pro03Frm03) {
		this.pro03Frm03 = pro03Frm03;
	}

	public List<CheckListProcesoEjecucionFormularioEt> getPro03Frm01List() {
		return pro03Frm01List;
	}

	public void setPro03Frm01List(List<CheckListProcesoEjecucionFormularioEt> pro03Frm01List) {
		this.pro03Frm01List = pro03Frm01List;
	}

	public List<CheckListProcesoEjecucionFormularioEt> getPro03Frm02List() {
		return pro03Frm02List;
	}

	public void setPro03Frm02List(List<CheckListProcesoEjecucionFormularioEt> pro03Frm02List) {
		this.pro03Frm02List = pro03Frm02List;
	}

	public List<CheckListProcesoEjecucionFormularioEt> getPro03Frm03List() {
		return pro03Frm03List;
	}

	public void setPro03Frm03List(List<CheckListProcesoEjecucionFormularioEt> pro03Frm03List) {
		this.pro03Frm03List = pro03Frm03List;
	}

	public boolean isPro04Frm01V() {
		return pro04Frm01V;
	}

	public void setPro04Frm01V(boolean pro04Frm01V) {
		this.pro04Frm01V = pro04Frm01V;
	}

	public boolean isPro04Frm02V() {
		return pro04Frm02V;
	}

	public void setPro04Frm02V(boolean pro04Frm02V) {
		this.pro04Frm02V = pro04Frm02V;
	}

	public boolean isPro04Frm03V() {
		return pro04Frm03V;
	}

	public void setPro04Frm03V(boolean pro04Frm03V) {
		this.pro04Frm03V = pro04Frm03V;
	}

	public CheckListProcesoFormularioEt getPro04Frm01() {
		return pro04Frm01;
	}

	public void setPro04Frm01(CheckListProcesoFormularioEt pro04Frm01) {
		this.pro04Frm01 = pro04Frm01;
	}

	public CheckListProcesoFormularioEt getPro04Frm02() {
		return pro04Frm02;
	}

	public void setPro04Frm02(CheckListProcesoFormularioEt pro04Frm02) {
		this.pro04Frm02 = pro04Frm02;
	}

	public CheckListProcesoFormularioEt getPro04Frm03() {
		return pro04Frm03;
	}

	public void setPro04Frm03(CheckListProcesoFormularioEt pro04Frm03) {
		this.pro04Frm03 = pro04Frm03;
	}

	public List<CheckListProcesoEjecucionFormularioEt> getPro04Frm01List() {
		return pro04Frm01List;
	}

	public void setPro04Frm01List(List<CheckListProcesoEjecucionFormularioEt> pro04Frm01List) {
		this.pro04Frm01List = pro04Frm01List;
	}

	public List<CheckListProcesoEjecucionFormularioEt> getPro04Frm02List() {
		return pro04Frm02List;
	}

	public void setPro04Frm02List(List<CheckListProcesoEjecucionFormularioEt> pro04Frm02List) {
		this.pro04Frm02List = pro04Frm02List;
	}

	public List<CheckListProcesoEjecucionFormularioEt> getPro04Frm03List() {
		return pro04Frm03List;
	}

	public void setPro04Frm03List(List<CheckListProcesoEjecucionFormularioEt> pro04Frm03List) {
		this.pro04Frm03List = pro04Frm03List;
	}

	public boolean isPro05Frm01V() {
		return pro05Frm01V;
	}

	public void setPro05Frm01V(boolean pro05Frm01V) {
		this.pro05Frm01V = pro05Frm01V;
	}

	public boolean isPro05Frm02V() {
		return pro05Frm02V;
	}

	public void setPro05Frm02V(boolean pro05Frm02V) {
		this.pro05Frm02V = pro05Frm02V;
	}

	public boolean isPro05Frm03V() {
		return pro05Frm03V;
	}

	public void setPro05Frm03V(boolean pro05Frm03V) {
		this.pro05Frm03V = pro05Frm03V;
	}

	public CheckListProcesoFormularioEt getPro05Frm01() {
		return pro05Frm01;
	}

	public void setPro05Frm01(CheckListProcesoFormularioEt pro05Frm01) {
		this.pro05Frm01 = pro05Frm01;
	}

	public CheckListProcesoFormularioEt getPro05Frm02() {
		return pro05Frm02;
	}

	public void setPro05Frm02(CheckListProcesoFormularioEt pro05Frm02) {
		this.pro05Frm02 = pro05Frm02;
	}

	public CheckListProcesoFormularioEt getPro05Frm03() {
		return pro05Frm03;
	}

	public void setPro05Frm03(CheckListProcesoFormularioEt pro05Frm03) {
		this.pro05Frm03 = pro05Frm03;
	}

	public List<CheckListProcesoEjecucionFormularioEt> getPro05Frm01List() {
		return pro05Frm01List;
	}

	public void setPro05Frm01List(List<CheckListProcesoEjecucionFormularioEt> pro05Frm01List) {
		this.pro05Frm01List = pro05Frm01List;
	}

	public List<CheckListProcesoEjecucionFormularioEt> getPro05Frm02List() {
		return pro05Frm02List;
	}

	public void setPro05Frm02List(List<CheckListProcesoEjecucionFormularioEt> pro05Frm02List) {
		this.pro05Frm02List = pro05Frm02List;
	}

	public List<CheckListProcesoEjecucionFormularioEt> getPro05Frm03List() {
		return pro05Frm03List;
	}

	public void setPro05Frm03List(List<CheckListProcesoEjecucionFormularioEt> pro05Frm03List) {
		this.pro05Frm03List = pro05Frm03List;
	}

	@Override
	protected void onDestroy() {
		iCorreoDao.remove();
		iKPICriticoDao.remove();
		iResponsableDao.remove();
		iResponsableDao.remove();
		iCheckListEjecucionDao.remove();
		iCheckListInfoCabEjeDao.remove();
		iCheckListProEjeFormDao.remove();
		iCheckListKpiEjecucionDao.remove();
		iCriterioEvaluacionDetalleDao.remove();
		iCheckListEjecucionAdjuntoDao.remove();
		iCheckListEjecucionReporteDao.remove();
		iCheckListProcesoEjecucionDao.remove();
		iCheckListPieFirmaEjecucionDao.remove();

	}
}
