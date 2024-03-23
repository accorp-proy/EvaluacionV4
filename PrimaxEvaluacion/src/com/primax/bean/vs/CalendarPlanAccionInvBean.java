package com.primax.bean.vs;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.pla.PlanAccionInventarioEt;
import com.primax.jpa.pla.PlanAccionInventarioTipoEt;
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.IPlanAccionInventarioDao;
import com.primax.srv.idao.IPlanAccionInventarioTipoDao;
import com.primax.srv.idao.IPlanificacionInventarioTipoDao;
import com.primax.srv.idao.IResponsableDao;
import com.primax.srv.idao.IRolEtDao;
import com.primax.srv.idao.IUsuarioDao;

@Named("CalendarPlanAccionInvBn")
@ViewScoped
public class CalendarPlanAccionInvBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -734535679991041247L;

	@EJB
	private IRolEtDao iRolEtDao;
	@EJB
	private IAgenciaDao iAgenciaDao;
	@EJB
	private IUsuarioDao iUsuarioDao;
	@EJB
	private IResponsableDao iResponsableDao;
	@EJB
	private IPlanAccionInventarioDao iPlanAccionInventarioDao;
	@EJB
	private IPlanAccionInventarioTipoDao iPlanAccionInventarioTipoDao;
	@EJB
	private IPlanificacionInventarioTipoDao iPlanificacionInventarioTipoDao;

	private List<PlanAccionInventarioEt> planAccionIventarios;
	private List<PlanAccionInventarioTipoEt> tipoInventarioSeleccionados;

	@Inject
	private AppMain appMain;

	/**
	 * Variables Calendario
	 */
	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();

	private String titulo = "";
	private TipoChecKListEt tipoChecListSeleccionado;
	private PlanAccionInventarioEt planAccionInvSeleccionado;

	@Override
	protected void init() {
		inicializarObj();
	}

	public void inicializarObj() {
		List<RolUsuarioEt> rolUsuario = null;
		RolEt rol = iRolEtDao.getRolbyId(9L);
		UsuarioEt usuario = appMain.getUsuario();
		rolUsuario = usuario.getRolesUsario();
		tipoInventarioSeleccionados = new ArrayList<>();
		planAccionInvSeleccionado = new PlanAccionInventarioEt();
		event = new DefaultScheduleEvent();
		if (containsRol(rolUsuario, rol)) {
			iniCalendarInventario();
		}
		titulo = "CONTROL DE INVENTARIO";

	}

	public void iniCalendarInventario() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			eventModel = new LazyScheduleModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public void loadEvents(Date desde, Date hasta) {
					try {
						String tema = "";
						String estado = "";
						String codigo = "";
						String estacion = "";
						// String inventario = "";
						String responsable = "";

						UsuarioEt usuario = appMain.getUsuario();
						ResponsableEt responsableC = iResponsableDao.getResponsableEstacion(usuario.getPersonaUsuario());
						planAccionIventarios = iPlanAccionInventarioDao.getPlanAccionInventarioList(desde, hasta, responsableC.getAgencia());
						DefaultScheduleEvent scheduleEventAllDay;
						for (PlanAccionInventarioEt planificacion : planAccionIventarios) {
							String leyenda0 = "";
							String leyenda1 = "";
							tema = "schedule-agendada";

							// codigo = "EVALUACIÓN:" + " " +
							// checkListE.getCodigo() + "<br/>";
							estado = "ESTADO:" + " " + planificacion.getEstadoPlanAccionInv().getDescripcion() + "<br/>";
							estacion = planificacion.getPlanificacionInventario().getAgencia().getNombreAgencia() + "<br/>";
							// responsable = "RESPONSABLE:" + " "
							// +
							// checkListE.getUsuarioAsignado().getPersonaUsuario().getNombreCompleto()
							// + "<br/>";
							if (leyenda0.equals("")) {
								leyenda0 = estacion + codigo + responsable + estado;
							} else {
								leyenda1 = estacion + codigo + responsable + estado;
								leyenda0 += "<br/>" + leyenda1;
							}
							switch (planificacion.getEstadoPlanAccionInv().getDescripcion()) {
							case "PENDIENTE":
								tema = "schedule-agendada";
								break;
							case "INGRESADO":
								tema = "schedule-en-ejecucion";
								break;
							}

							String strDate = dateFormat.format(planificacion.getPlanificacionInventario().getFechaPlanificacion());
							Date fechaD = dateFormat.parse(strDate);
							scheduleEventAllDay = new DefaultScheduleEvent(planificacion.getPlanificacionInventario().getAgencia().getNombreAgencia(), fechaD, fechaD, tema);
							scheduleEventAllDay.setData(planificacion);
							scheduleEventAllDay.setId(String.valueOf(planificacion.getIdPlanAccionInventario()));
							scheduleEventAllDay.setDescription(leyenda0);
							scheduleEventAllDay.setAllDay(true);
							eventModel.addEvent(scheduleEventAllDay);
						}

					} catch (EntidadNoEncontradaException | ParseException e) {
						e.printStackTrace();
						System.out.println("Error :Método cargarCheckList " + " " + e.getMessage());
					}
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método inicializarCalendario " + " " + e.getMessage());
		}
	}

//	public void onEventSelect(SelectEvent selectEvent) {
//		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//		event = (ScheduleEvent) selectEvent.getObject();
//		planificacionSeleccionada = (PlanificacionEt) event.getData();
//		Date fechaEjecucion = planificacionSeleccionada.getFechaPlanificacion();
//		String fechaActual = dateFormat.format(new Date());
//		String fechaEjecucionS = dateFormat.format(fechaEjecucion.getTime());
//		if (!fechaActual.equals(fechaEjecucionS)) {
//			showInfo("CheckList solo puede ser ejecutado en la fecha planificada.", FacesMessage.SEVERITY_INFO, null,
//					"");
//		}
//	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
		PlanAccionInventarioEt planificacion = null;
		planificacion = (PlanAccionInventarioEt) event.getData();
		planAccionInvSeleccionado = iPlanAccionInventarioDao.getPlanAccionInventarioById(planificacion.getIdPlanAccionInventario());
		for (PlanAccionInventarioTipoEt planAccionInvTipo : planAccionInvSeleccionado.getPlanAccionInventarioTipo()) {
			if (planAccionInvTipo.isEjecutado()) {
				tipoInventarioSeleccionados.add(planAccionInvTipo);
			}
		}
	}

	public void guardarInv() {
		try {
			redireccionar();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}

	}

	public void redireccionar() {
		String pagina = "";
		try {
			if (tipoInventarioSeleccionados.isEmpty()) {
				showInfo("Por favor seleccionar tipo de Inventario.", FacesMessage.SEVERITY_INFO, null, "");
				return;
			}
			if (tipoInventarioSeleccionados.size() != 1) {
				showInfo("Por favor seleccionar un tipo de Inventario.", FacesMessage.SEVERITY_INFO, null, "");
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			ResponsableEt responsable = iResponsableDao.getResponsableEstacion(usuario.getPersonaUsuario());
			List<PlanAccionInventarioEt> planAccionInventarios = iPlanAccionInventarioDao.getPlanAccionInvByAgencia(responsable.getAgencia());
			if (planAccionInventarios != null && !planAccionInventarios.isEmpty()) {
				for (PlanAccionInventarioEt planAccionInvC : planAccionInventarios) {
					planAccionInvC.setPlanAccion(false);
					iPlanAccionInventarioDao.guardarPlanAccionInventario(planAccionInvC, usuario);
				}
			}
			for (PlanAccionInventarioTipoEt tipoInv : tipoInventarioSeleccionados) {
				PlanAccionInventarioTipoEt tipo = iPlanAccionInventarioTipoDao.getPlanAccionInv(planAccionInvSeleccionado, tipoInv.getTipoInventario());
				tipo.setEnEjecucion(false);
				iPlanAccionInventarioTipoDao.guardarPlanAccionInvTipo(tipo, usuario);
			}
			planAccionInvSeleccionado.setPlanAccion(true);
			iPlanAccionInventarioDao.guardarPlanAccionInventario(planAccionInvSeleccionado, usuario);
			PlanAccionInventarioTipoEt tipoSele = tipoInventarioSeleccionados.get(0);
			tipoSele.setEnEjecucion(true);
			iPlanAccionInventarioTipoDao.guardarPlanAccionInvTipo(tipoSele, usuario);
			FacesContext contex = FacesContext.getCurrentInstance();
			pagina = "/PrimaxEvaluacionPruebas/pages/gerencia/ger_004.xhtml";
			contex.getExternalContext().redirect(pagina);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método redireccionar" + " " + " " + e.getMessage());
		}

	}

	public Date sumarRestarDiasFecha(Date fecha, int dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, dias);
		return calendar.getTime();
	}

	public Date sumarRestarMesesFecha(Date fecha, int meses) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.MONTH, meses);
		return calendar.getTime();
	}

	public boolean containsRol(final List<RolUsuarioEt> list, final RolEt rol) {
		return list.stream().filter(o -> o.getRol().equals(rol)).findFirst().isPresent();
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	public LocalDateTime getRandomDateTime(LocalDateTime base) {
		LocalDateTime dateTime = base.withMinute(0).withSecond(0).withNano(0);
		return dateTime.plusDays(((int) (Math.random() * 30)));
	}

	public void onEventResize(ScheduleEntryResizeEvent event) {

	}

	public TipoChecKListEt getTipoChecListSeleccionado() {
		return tipoChecListSeleccionado;
	}

	public void setTipoChecListSeleccionado(TipoChecKListEt tipoChecListSeleccionado) {
		this.tipoChecListSeleccionado = tipoChecListSeleccionado;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<PlanAccionInventarioTipoEt> getTipoInventarioSeleccionados() {
		return tipoInventarioSeleccionados;
	}

	public void setTipoInventarioSeleccionados(List<PlanAccionInventarioTipoEt> tipoInventarioSeleccionados) {
		this.tipoInventarioSeleccionados = tipoInventarioSeleccionados;
	}

	public List<PlanAccionInventarioEt> getPlanAccionIventarios() {
		return planAccionIventarios;
	}

	public void setPlanAccionIventarios(List<PlanAccionInventarioEt> planAccionIventarios) {
		this.planAccionIventarios = planAccionIventarios;
	}

	public PlanAccionInventarioEt getPlanAccionInvSeleccionado() {
		return planAccionInvSeleccionado;
	}

	public void setPlanAccionInvSeleccionado(PlanAccionInventarioEt planAccionInvSeleccionado) {
		this.planAccionInvSeleccionado = planAccionInvSeleccionado;
	}

	@Override
	protected void onDestroy() {
		iRolEtDao.remove();
		iUsuarioDao.remove();
		iAgenciaDao.remove();
		iResponsableDao.remove();
		iPlanAccionInventarioDao.remove();
		iPlanAccionInventarioTipoDao.remove();
		iPlanificacionInventarioTipoDao.remove();
	}

}
