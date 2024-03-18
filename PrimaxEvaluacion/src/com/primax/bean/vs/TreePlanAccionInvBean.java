package com.primax.bean.vs;

import java.io.IOException;
import java.io.Serializable;
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
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.enm.gen.RutaFileEnum;
import com.primax.enm.gen.UtilEnum;
import com.primax.enm.msg.Mensajes;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.gen.PlanAccionOrganizacion;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.jpa.pla.PlanAccionInvAnioEt;
import com.primax.jpa.pla.PlanAccionInvCategoriaAdjuntoEt;
import com.primax.jpa.pla.PlanAccionInvCategoriaEt;
import com.primax.jpa.pla.PlanAccionInvEjecutadoEt;
import com.primax.jpa.pla.PlanAccionInvEstacionEt;
import com.primax.jpa.pla.PlanAccionInvMesEt;
import com.primax.jpa.pla.PlanAccionInvZonaEt;
import com.primax.jpa.pla.PlanAccionInventarioTipoEt;
import com.primax.jpa.pla.PlanificacionInventarioTipoEt;
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.ICategoriaInventarioDao;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.srv.idao.IParametrolGeneralDao;
import com.primax.srv.idao.IPlanAccionInvAnioDao;
import com.primax.srv.idao.IPlanAccionInvCategoriaAdjuntoDao;
import com.primax.srv.idao.IPlanAccionInvCategoriaDao;
import com.primax.srv.idao.IPlanAccionInvEjecutadoDao;
import com.primax.srv.idao.IPlanAccionInvEstacionDao;
import com.primax.srv.idao.IPlanAccionInvMesDao;
import com.primax.srv.idao.IPlanAccionInvZonaDao;
import com.primax.srv.idao.IPlanAccionInventarioDao;
import com.primax.srv.idao.IPlanAccionInventarioTipoDao;
import com.primax.srv.idao.IPlanificacionInventarioTipoDao;
import com.primax.srv.idao.IResponsableDao;
import com.primax.srv.idao.IRolEtDao;
import com.primax.srv.idao.ITipoInventarioDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.srv.idao.IZonaDao;

@Named("TreePlanAccionInvBn")
@ViewScoped
public class TreePlanAccionInvBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IZonaDao iZonaDao;
	@EJB
	private IRolEtDao iRolEtDao;
	@EJB
	private IUsuarioDao iUsuarioDao;
	@EJB
	private IAgenciaDao iAgenciaDao;
	@EJB
	private IResponsableDao iResponsableDao;
	@EJB
	private IGeneralUtilsDao iGeneralUtilsDao;
	@EJB
	private ITipoInventarioDao iTipoInventarioDao;
	@EJB
	private IPlanAccionInvMesDao iPlanAccionInvMesDao;
	@EJB
	private IPlanAccionInvAnioDao iPlanAccionInvAnioDao;
	@EJB
	private IPlanAccionInvZonaDao iPlanAccionInvZonaDao;
	@EJB
	private IParametrolGeneralDao iParametrolGeneralDao;
	@EJB
	private ICategoriaInventarioDao iCategoriaInventarioDao;
	@EJB
	private IPlanAccionInventarioDao iPlanAccionInventarioDao;
	@EJB
	private IPlanAccionInvEstacionDao iPlanAccionInvEstacionDao;
	@EJB
	private IPlanAccionInvEjecutadoDao iPlanAccionInvEjecutadoDao;
	@EJB
	private IPlanAccionInvCategoriaDao iPlanAccionInvCategoriaDao;
	@EJB
	private IPlanAccionInventarioTipoDao iPlanAccionInventarioTipoDao;
	@EJB
	private IPlanificacionInventarioTipoDao iPlanificacionInventarioTipoDao;
	@EJB
	private IPlanAccionInvCategoriaAdjuntoDao iPlanAccionInvCategoriaAdjuntoDao;

	@Inject
	private AppMain appMain;

	private TreeNode root;
	private TreeNode selectedNode;
	private ZonaEt zonaSeleccionada;
	private boolean tipoGerente = false;
	private boolean tipoAuditor = false;
	private AgenciaEt estacionSeleccionada;
	private TipoInventarioEt tipoInvSeleccionado;
	private ParametrosGeneralesEt anioSeleccionado;
	private List<ParametrosGeneralesEt> mesesSeleccionados;
	private List<PlanAccionInvCategoriaEt> plnAccionCategoriasT;
	private List<PlanAccionInvCategoriaEt> plnAccionCategoriasC;
	private List<PlanAccionInvCategoriaEt> plnAccionCategoriasP;
	private PlanAccionOrganizacion planAccionOrganizacionSelect;
	private PlanAccionInvCategoriaEt plnAcionInvCatSeleccionado;
	private List<PlanAccionInvCategoriaAdjuntoEt> plnAcionInvCatAdjuntoEliminado;

	@Override
	protected void init() {
		List<RolUsuarioEt> rolUsuario = null;
		RolEt rol = iRolEtDao.getRolbyId(9L);
		UsuarioEt usuario = appMain.getUsuario();
		rolUsuario = usuario.getRolesUsario();
		if (containsRol(rolUsuario, rol)) {
			tipoGerente = true;
		} else {
			tipoAuditor = true;
		}
		disenarTree(usuario);
		inicializarObj();

	}

	public boolean containsRol(final List<RolUsuarioEt> list, final RolEt rol) {
		return list.stream().filter(o -> o.getRol().equals(rol)).findFirst().isPresent();
	}

	public void inicializarObj() {
		plnAccionCategoriasT = new ArrayList<>();
		plnAccionCategoriasC = new ArrayList<>();
		plnAccionCategoriasP = new ArrayList<>();
		plnAcionInvCatAdjuntoEliminado = new ArrayList<>();
	}

	public void generar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (tipoAuditor) {
				if (anioSeleccionado != null) {
					generarTree(Integer.parseInt(anioSeleccionado.getValorLista()), usuario);
				}
			} else if (tipoGerente) {
				ResponsableEt responsable = iResponsableDao.getResponsableEstacion(usuario.getPersonaUsuario());
				estacionSeleccionada = responsable.getAgencia();
				generarTree(Integer.parseInt(anioSeleccionado.getValorLista()), usuario);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generar " + " " + e.getMessage());
		}

	}

	public void generarTree(int anio, UsuarioEt usuario) {

		Long idZona = 0L;
		Long idEstacion = 0L;
		Long idTipoInventario = 0L;
		try {
			Long idAnio = (long) anio;
			iCategoriaInventarioDao.limpiarReporte(usuario.getIdUsuario());
			if (zonaSeleccionada != null) {
				idZona = zonaSeleccionada.getIdZona();
			}
			if (estacionSeleccionada != null) {
				idEstacion = estacionSeleccionada.getIdAgencia();
			}
			if (tipoInvSeleccionado != null) {
				idTipoInventario = tipoInvSeleccionado.getIdTipoInventario();
			}
			for (ParametrosGeneralesEt parametrosGenerales : mesesSeleccionados) {
				int mes = Integer.parseInt(parametrosGenerales.getValorLista());
				Date fechaDesde = getFechaDesde(mes, anio);
				Date fechaHasta = getFechaHasta(mes, anio);
				iPlanAccionInvAnioDao.generar(idAnio, fechaDesde, fechaHasta, idZona, idEstacion, idTipoInventario, usuario.getIdUsuario());
			}
			disenarTree(usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generar " + " " + e.getMessage());
		}
	}

	public void disenarTree(UsuarioEt usuario) {
		Long idPlanAccion = 0L;
		try {
			root = new DefaultTreeNode("Files", null);
			List<PlanAccionInvAnioEt> anios = iPlanAccionInvAnioDao.getPlanAccionAnioList(usuario);
			for (PlanAccionInvAnioEt anio : anios) {
				idPlanAccion = anio.getIdPlanAccionInvAnio();
				TreeNode node0 = new DefaultTreeNode(new PlanAccionOrganizacion(0L, 0L, anio.getAnio().toString(), "Carpeta"), root);
				List<PlanAccionInvMesEt> meses = iPlanAccionInvMesDao.getPlanAccionMesList(usuario, idPlanAccion, anio.getAnio());
				for (PlanAccionInvMesEt mes : meses) {
					TreeNode node00 = new DefaultTreeNode(new PlanAccionOrganizacion(0L, 0L, mes.getMesLetra().toString(), "Carpeta"), node0);
					List<PlanAccionInvZonaEt> zonas = iPlanAccionInvZonaDao.getPlanAccionZonaList(idPlanAccion, anio.getAnio(), mes.getMesNumero());
					for (PlanAccionInvZonaEt zona : zonas) {
						TreeNode node000 = new DefaultTreeNode(new PlanAccionOrganizacion(0L, 0L, zona.getZona(), "Carpeta"), node00);
						List<PlanAccionInvEstacionEt> estaciones = iPlanAccionInvEstacionDao.getPlanAccionMesList(idPlanAccion, anio.getAnio(), mes.getMesNumero(), zona.getIdZona());
						for (PlanAccionInvEstacionEt estacion : estaciones) {
							TreeNode node0000 = new DefaultTreeNode(new PlanAccionOrganizacion(0L, 0L, estacion.getAgencia(), "Carpeta"), node000);
							List<PlanAccionInvEjecutadoEt> checks = iPlanAccionInvEjecutadoDao.getPlanAccionInvEjecutadoList(idPlanAccion, anio.getAnio(), mes.getMesNumero(), zona.getIdZona(), estacion.getIdAgencia());
							for (PlanAccionInvEjecutadoEt check : checks) {
								TreeNode node00000 = new DefaultTreeNode(new PlanAccionOrganizacion(check.getIdPlanificacionInventario(), 0L, check.getDescripcion(), "Carpeta"), node0000);
								List<PlanificacionInventarioTipoEt> tiposInv = iPlanificacionInventarioTipoDao.getPlanificacionInventarioTipoList(check.getIdPlanificacionInventario(), check.getIdTipoInventario());
								for (PlanificacionInventarioTipoEt tipoInv : tiposInv) {
									node00000.getChildren().add(new DefaultTreeNode(new PlanAccionOrganizacion(tipoInv.getIdPlanificacionInventarioTipo(), tipoInv.getPlanificacionInventario().getAgencia().getIdAgencia(), tipoInv.getTipoInventario().getDescripcion(), "Plan")));
								}
							}
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método disenarTree " + " " + e.getMessage());
		}
	}

	public void guardarAdj() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			if (!plnAcionInvCatAdjuntoEliminado.isEmpty()) {
				for (PlanAccionInvCategoriaAdjuntoEt plnAccionInvCategoriaAdj : plnAcionInvCatAdjuntoEliminado) {
					iPlanAccionInvCategoriaAdjuntoDao.guardarPlnAccionInvCategoriaAdj(plnAccionInvCategoriaAdj, usuario);
				}
			}
			iPlanAccionInvCategoriaDao.guardarPlanAccionInvCategoria(plnAcionInvCatSeleccionado, usuario);
			showInfo("Dato Guardado", FacesMessage.SEVERITY_INFO, null, null);
			RequestContext.getCurrentInstance().execute("PF('dlg_par_025_4').hide();");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public void onNodeSelect(NodeSelectEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().toString());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void adjuntoTienda(Long id) {
		try {
			plnAccionCategoriasT = new ArrayList<>();
			PlanAccionInventarioTipoEt tipoN = iPlanAccionInventarioTipoDao.getPlanAccionInvTipo(id);
			PlanAccionInventarioTipoEt tipo = iPlanAccionInventarioTipoDao.getPlanAccionInventarioTipoDif(tipoN.getIdPlanAccionInventarioTipo());
			if (tipo != null && tipo.getPlanAccionInvCategoria() != null && !tipo.getPlanAccionInvCategoria().isEmpty()) {
				for (PlanAccionInvCategoriaEt categoria : tipo.getPlanAccionInvCategoria()) {
					plnAccionCategoriasT.add(categoria);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método adjuntoKPI " + " " + e.getMessage());
		}

	}

	public void adjuntoCombustible(Long id) {
		try {
			plnAccionCategoriasC = new ArrayList<>();
			PlanAccionInventarioTipoEt tipoN = iPlanAccionInventarioTipoDao.getPlanAccionInvTipo(id);
			PlanAccionInventarioTipoEt tipo = iPlanAccionInventarioTipoDao.getPlanAccionInventarioTipoDif(tipoN.getIdPlanAccionInventarioTipo());
			if (tipo != null && tipo.getPlanAccionInvCategoria() != null && !tipo.getPlanAccionInvCategoria().isEmpty()) {
				for (PlanAccionInvCategoriaEt categoria : tipo.getPlanAccionInvCategoria()) {
					plnAccionCategoriasC.add(categoria);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método adjuntoKPI " + " " + e.getMessage());
		}

	}

	public void adjuntoPromocion(Long id) {
		try {
			plnAccionCategoriasP = new ArrayList<>();
			PlanAccionInventarioTipoEt tipoN = iPlanAccionInventarioTipoDao.getPlanAccionInvTipo(id);
			PlanAccionInventarioTipoEt tipo = iPlanAccionInventarioTipoDao.getPlanAccionInventarioTipoDif(tipoN.getIdPlanAccionInventarioTipo());
			if (tipo != null && tipo.getPlanAccionInvCategoria() != null && !tipo.getPlanAccionInvCategoria().isEmpty()) {
				for (PlanAccionInvCategoriaEt categoria : tipo.getPlanAccionInvCategoria()) {
					plnAccionCategoriasP.add(categoria);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método adjuntoKPI " + " " + e.getMessage());
		}

	}

	public void adjuntoKPIAdj(PlanAccionInvCategoriaEt catAdj) {
		try {
			System.out.println("Prueba Jema");
			plnAcionInvCatSeleccionado = catAdj;
			if (plnAcionInvCatSeleccionado.getPlanAccionInvCategoriaAdjunto() == null || plnAcionInvCatSeleccionado.getPlanAccionInvCategoriaAdjunto().isEmpty()) {
				plnAcionInvCatSeleccionado.setPlanAccionInvCategoriaAdjunto(new ArrayList<>());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método adjuntoKPI " + " " + e.getMessage());
		}
	}

	public void upload(FileUploadEvent event) throws EntidadNoGrabadaException {
		String ruta;
		String nombreArchivo = "";
		try {
			PlanAccionInvCategoriaEt planAccionInvCategoria = plnAcionInvCatSeleccionado;
			nombreArchivo = event.getFile().getFileName();
			PlanAccionInvCategoriaAdjuntoEt reg = new PlanAccionInvCategoriaAdjuntoEt();
			reg.setNombreAdjunto(nombreArchivo);
			reg.setPlanAccionInvCategoria(plnAcionInvCatSeleccionado);
			reg.setFile(event.getFile().getInputstream());
			for (PlanAccionInvCategoriaAdjuntoEt doc : planAccionInvCategoria.getPlanAccionInvCategoriaAdjunto()) {
				if (doc.getNombreAdjunto().equals(reg.getNombreAdjunto())) {
					showInfo("" + Mensajes._ERROR_UPLOAD_DOCUMENTO.getDescripcion(), FacesMessage.SEVERITY_ERROR);
					return;
				}
			}
			if (nombreArchivo.toLowerCase().contains(".png") || nombreArchivo.toLowerCase().contains(".jpg")) {
				ruta = iGeneralUtilsDao.creaRuta(plnAcionInvCatSeleccionado.getIdPlanAccionInvCategoria(), RutaFileEnum.RUTA_CONTROL_INTERNO.getDescripcion());
			} else {
				ruta = iGeneralUtilsDao.creaRuta(plnAcionInvCatSeleccionado.getIdPlanAccionInvCategoria(), RutaFileEnum.RUTA_CONTROL_INTERNO.getDescripcion());
			}
			iGeneralUtilsDao.copyFile(reg.getNombreAdjunto(), reg.getFile(), ruta);
			plnAcionInvCatSeleccionado.getPlanAccionInvCategoriaAdjunto().add(reg);
			FacesMessage msg = new FacesMessage("Satisfactorio! ", event.getFile().getFileName() + "  " + "Esta subido Correctamente.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (IOException e) {
			FacesMessage msg = new FacesMessage("Error! ", event.getFile().getFileName() + "  " + "El archivo no se subio.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		}
	}

	public void quitarAdjunto(PlanAccionInvCategoriaAdjuntoEt plnAccionInvCategoriaAdj) {
		try {
			Date fechaRegistro = UtilEnum.FECHA_REGISTRO.getValue();
			plnAccionInvCategoriaAdj.setFechaModificacion(fechaRegistro);
			plnAccionInvCategoriaAdj.setEstado(EstadoEnum.INA);
			plnAcionInvCatAdjuntoEliminado.add(plnAccionInvCategoriaAdj);
			plnAcionInvCatSeleccionado.getPlanAccionInvCategoriaAdjunto().remove(plnAccionInvCategoriaAdj);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método quitarAdjunto " + " " + e.getMessage());
		}

	}

	public void nodeSelect(PlanAccionOrganizacion planAccionOrganizacion) {
		System.out.println("Información" + " " + planAccionOrganizacion.getNombre());
	}

	public boolean customFilter(TreeNode treeNode, Object filter, Locale locale) {
		if (treeNode.getData() == null || filter == null) {
			return true;
		}
		String filterText = filter.toString().trim().toLowerCase(locale);
		if (filterText.isEmpty()) {
			return true;
		}
		return ((String) treeNode.getData()).toLowerCase(locale).contains(filterText);
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText.isEmpty()) {
			return true;
		}
		PlanAccionOrganizacion customer = (PlanAccionOrganizacion) value;
		return customer.getNombre().toLowerCase().contains(filterText) || customer.getTipo().toLowerCase().contains(filterText);
	}

	public Date getFechaDesde(int mes, int anio) {
		Calendar calDesd = Calendar.getInstance();
		calDesd.setTime(new Date());
		calDesd.set(Calendar.MONTH, mes);
		calDesd.set(Calendar.YEAR, anio);
		calDesd.set(Calendar.DAY_OF_MONTH, 1);
		// System.out.println("Fecha Desde" + " " + calDesd.getTime());
		return calDesd.getTime();

	}

	public Date getFechaHasta(int mes, int anio) {
		Calendar calHast = Calendar.getInstance();
		calHast.setTime(new Date());
		calHast.set(Calendar.MONTH, mes);
		calHast.set(Calendar.YEAR, anio);
		int ultimoDiaMes = calHast.getActualMaximum(Calendar.DAY_OF_MONTH);
		calHast.set(anio, mes, ultimoDiaMes);
		// System.out.println("Fecha Hasta" + " " + calHast.getTime());
		return calHast.getTime();
	}

	public List<ParametrosGeneralesEt> getTipoAnioList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<ParametrosGeneralesEt>();
		try {
			ParametrosGeneralesEt parametrosGeneral = iParametrolGeneralDao.getObjPadre("106");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametrosGeneral);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoAnioList " + " " + e.getMessage());
		}
		return parametrosGenerales;
	}

	public List<ParametrosGeneralesEt> getTipoMesList() {
		List<ParametrosGeneralesEt> parametrosGenerales = new ArrayList<ParametrosGeneralesEt>();
		try {
			ParametrosGeneralesEt parametrosGeneral = iParametrolGeneralDao.getObjPadre("108");
			parametrosGenerales = iParametrolGeneralDao.getListaHIjos(parametrosGeneral);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoMesList " + " " + e.getMessage());
		}
		return parametrosGenerales;
	}

	public List<ZonaEt> getTipoZonaList() {
		List<ZonaEt> zonas = new ArrayList<>();
		try {
			UsuarioEt usuario = iUsuarioDao.getUsuarioId(appMain.getUsuario().getIdUsuario());
			if (usuario.isAccesoZona() && !usuario.getZonaUsuario().isEmpty()) {
				for (ZonaUsuarioEt zonaUsuario : usuario.getZonaUsuario()) {
					zonas.add(zonaUsuario.getZona());
				}
			} else {
				zonas = iZonaDao.getZonaList(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoZonaList " + " " + e.getMessage());
		}
		return zonas;
	}

	public List<TipoInventarioEt> getTipoInvList() {
		List<TipoInventarioEt> tipoInventarios = new ArrayList<TipoInventarioEt>();
		try {
			tipoInventarios = iTipoInventarioDao.getTipoInventarioList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getEvaluacionList " + " " + e.getMessage());
		}
		return tipoInventarios;
	}

	public List<AgenciaEt> getEstacionList() {
		List<AgenciaEt> agencias = new ArrayList<AgenciaEt>();
		try {
			UsuarioEt usuario = iUsuarioDao.getUsuarioId(appMain.getUsuario().getIdUsuario());
			if (usuario.isAccesoZona()) {
				agencias = iAgenciaDao.getAgenciaAccesoZona(usuario, zonaSeleccionada);
			} else {
				agencias = iAgenciaDao.getAgenciasByZona(zonaSeleccionada);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getAgenciaList " + " " + e.getMessage());
		}
		return agencias;
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

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public PlanAccionOrganizacion getPlanAccionOrganizacionSelect() {
		return planAccionOrganizacionSelect;
	}

	public void setPlanAccionOrganizacionSelect(PlanAccionOrganizacion planAccionOrganizacionSelect) {
		this.planAccionOrganizacionSelect = planAccionOrganizacionSelect;
	}

	public ZonaEt getZonaSeleccionada() {
		return zonaSeleccionada;
	}

	public void setZonaSeleccionada(ZonaEt zonaSeleccionada) {
		this.zonaSeleccionada = zonaSeleccionada;
	}

	public TipoInventarioEt getTipoInvSeleccionado() {
		return tipoInvSeleccionado;
	}

	public void setTipoInvSeleccionado(TipoInventarioEt tipoInvSeleccionado) {
		this.tipoInvSeleccionado = tipoInvSeleccionado;
	}

	public ParametrosGeneralesEt getAnioSeleccionado() {
		return anioSeleccionado;
	}

	public void setAnioSeleccionado(ParametrosGeneralesEt anioSeleccionado) {
		this.anioSeleccionado = anioSeleccionado;
	}

	public List<ParametrosGeneralesEt> getMesesSeleccionados() {
		return mesesSeleccionados;
	}

	public void setMesesSeleccionados(List<ParametrosGeneralesEt> mesesSeleccionados) {
		this.mesesSeleccionados = mesesSeleccionados;
	}

	public AgenciaEt getEstacionSeleccionada() {
		return estacionSeleccionada;
	}

	public void setEstacionSeleccionada(AgenciaEt estacionSeleccionada) {
		this.estacionSeleccionada = estacionSeleccionada;
	}

	public List<PlanAccionInvCategoriaEt> getPlnAccionCategoriasT() {
		return plnAccionCategoriasT;
	}

	public void setPlnAccionCategoriasT(List<PlanAccionInvCategoriaEt> plnAccionCategoriasT) {
		this.plnAccionCategoriasT = plnAccionCategoriasT;
	}

	public List<PlanAccionInvCategoriaEt> getPlnAccionCategoriasC() {
		return plnAccionCategoriasC;
	}

	public void setPlnAccionCategoriasC(List<PlanAccionInvCategoriaEt> plnAccionCategoriasC) {
		this.plnAccionCategoriasC = plnAccionCategoriasC;
	}

	public List<PlanAccionInvCategoriaEt> getPlnAccionCategoriasP() {
		return plnAccionCategoriasP;
	}

	public void setPlnAccionCategoriasP(List<PlanAccionInvCategoriaEt> plnAccionCategoriasP) {
		this.plnAccionCategoriasP = plnAccionCategoriasP;
	}

	public PlanAccionInvCategoriaEt getPlnAcionInvCatSeleccionado() {
		return plnAcionInvCatSeleccionado;
	}

	public void setPlnAcionInvCatSeleccionado(PlanAccionInvCategoriaEt plnAcionInvCatSeleccionado) {
		this.plnAcionInvCatSeleccionado = plnAcionInvCatSeleccionado;
	}

	public List<PlanAccionInvCategoriaAdjuntoEt> getPlnAcionInvCatAdjuntoEliminado() {
		return plnAcionInvCatAdjuntoEliminado;
	}

	public void setPlnAcionInvCatAdjuntoEliminado(List<PlanAccionInvCategoriaAdjuntoEt> plnAcionInvCatAdjuntoEliminado) {
		this.plnAcionInvCatAdjuntoEliminado = plnAcionInvCatAdjuntoEliminado;
	}

	public boolean isTipoGerente() {
		return tipoGerente;
	}

	public void setTipoGerente(boolean tipoGerente) {
		this.tipoGerente = tipoGerente;
	}

	public boolean isTipoAuditor() {
		return tipoAuditor;
	}

	public void setTipoAuditor(boolean tipoAuditor) {
		this.tipoAuditor = tipoAuditor;
	}

	@Override
	protected void onDestroy() {
		iZonaDao.remove();
		iRolEtDao.remove();
		iAgenciaDao.remove();
		iResponsableDao.remove();
		iTipoInventarioDao.remove();
		iPlanAccionInvMesDao.remove();
		iPlanAccionInvAnioDao.remove();
		iPlanAccionInvZonaDao.remove();
		iParametrolGeneralDao.remove();
		iCategoriaInventarioDao.remove();
		iPlanAccionInventarioDao.remove();
		iPlanAccionInvEstacionDao.remove();
		iPlanAccionInvEjecutadoDao.remove();
		iPlanAccionInventarioTipoDao.remove();
		iPlanificacionInventarioTipoDao.remove();
		iPlanAccionInvCategoriaAdjuntoDao.remove();
	}

}
