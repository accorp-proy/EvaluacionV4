package com.primax.bean.vs;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.jpa.enums.EstadoPlanAccionInvEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.jpa.pla.PlanAccionInventarioTipoEt;
import com.primax.jpa.sec.RolEt;
import com.primax.jpa.sec.RolUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.IParametrolGeneralDao;
import com.primax.srv.idao.IPlanAccionInventarioTipoDao;
import com.primax.srv.idao.IRolEtDao;
import com.primax.srv.idao.ITipoInventarioDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.srv.idao.IZonaDao;

@Named("ConsultaPlanAccionInvBn")
@ViewScoped
public class ConsultaPlanAccionInvBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IZonaDao iZonaDao;
	@EJB
	private IRolEtDao iRolEtDao;
	@EJB
	private IAgenciaDao iAgenciaDao;
	@EJB
	private IUsuarioDao iUsuarioDao;
	@EJB
	private ITipoInventarioDao iTipoInventarioDao;
	@EJB
	private IParametrolGeneralDao iParametrolGeneralDao;
	@EJB
	private IPlanAccionInventarioTipoDao iPlanAccionInventarioTipoDao;

	@Inject
	private AppMain appMain;

	private ZonaEt zonaSeleccionada;
	private Date fDesde = new Date();
	private Date fHasta = new Date();
	private AgenciaEt estacionSeleccionada;
	private TipoInventarioEt tipoInventarioSeleccionado;
	private PlanAccionInventarioTipoEt plnAccInvTipSeleccionado;
	private EstadoPlanAccionInvEnum estadoPlanAccionSeleccionado;
	private List<PlanAccionInventarioTipoEt> planAccionInventarioTipos;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void inicializarObj() {
		fDesde = primerDiaMes();
		fHasta = ultimoDiaMes();
		zonaSeleccionada = null;
		estacionSeleccionada = null;
	}

	public void buscar() {
		try {
			UsuarioEt usuario = iUsuarioDao.getUsuarioId(appMain.getUsuario().getIdUsuario());
			if (usuario.isAccesoZona()) {
				planAccionInventarioTipos = iPlanAccionInventarioTipoDao.getPlanAccionInvTipoAccesoZonaList(estacionSeleccionada, tipoInventarioSeleccionado, fDesde, fHasta, estadoPlanAccionSeleccionado, usuario);
			} else {
				planAccionInventarioTipos = iPlanAccionInventarioTipoDao.getPlanAccionInvTipoList(zonaSeleccionada, estacionSeleccionada, tipoInventarioSeleccionado, fDesde, fHasta, estadoPlanAccionSeleccionado);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}
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

	public List<TipoInventarioEt> getTipoInventarioList() {
		List<TipoInventarioEt> tipoInventarios = new ArrayList<TipoInventarioEt>();
		try {
			tipoInventarios = iTipoInventarioDao.getTipoInventarioList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoInventarioList " + " " + e.getMessage());
		}
		return tipoInventarios;
	}

	public boolean containsRol(final List<RolUsuarioEt> list, final RolEt rol) {
		return list.stream().filter(o -> o.getRol().equals(rol)).findFirst().isPresent();
	}

	public Date primerDiaMes() {
		int mes = 0;
		int anio = 0;
		Calendar fechDesde = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			int primerDiaMes = fechDesde.getActualMinimum(Calendar.DAY_OF_MONTH);
			anio = fechDesde.get(Calendar.YEAR);
			mes = fechDesde.get(Calendar.MONTH);
			fechDesde.set(anio, mes, primerDiaMes);
			String fechDesdeS = df.format(fechDesde.getTime());
			System.out.println("Fecha Primer Día" + " " + fechDesdeS);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método primerDiaMes " + " " + e.getMessage());
		}
		return fechDesde.getTime();

	}

	public Date ultimoDiaMes() {
		int mes = 0;
		int anio = 0;
		Calendar fechHasta = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Calendar calendario = Calendar.getInstance();
			int ultimoDiaMes = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
			anio = calendario.get(Calendar.YEAR);
			mes = calendario.get(Calendar.MONTH);
			fechHasta.set(anio, mes, ultimoDiaMes);
			String fechaHastaS = df.format(fechHasta.getTime());
			System.out.println("Fecha Ultimo Día" + " " + fechaHastaS);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método ultimoDiaMes " + " " + e.getMessage());
		}
		return fechHasta.getTime();
	}

	public SelectItem[] getEstadoPlanAccion() {
		SelectItem[] items = new SelectItem[3];
		items[0] = new SelectItem(EstadoPlanAccionInvEnum.ESTADO_PLAN_A, EstadoPlanAccionInvEnum.ESTADO_PLAN_A.getDescripcion());
		items[1] = new SelectItem(EstadoPlanAccionInvEnum.INGRESADO, EstadoPlanAccionInvEnum.INGRESADO.getDescripcion());
		items[2] = new SelectItem(EstadoPlanAccionInvEnum.PENDIENTE, EstadoPlanAccionInvEnum.PENDIENTE.getDescripcion());
		return items;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}

	public Date getfDesde() {
		return fDesde;
	}

	public void setfDesde(Date fDesde) {
		this.fDesde = fDesde;
	}

	public Date getfHasta() {
		return fHasta;
	}

	public void setfHasta(Date fHasta) {
		this.fHasta = fHasta;
	}

	public AgenciaEt getEstacionSeleccionada() {
		return estacionSeleccionada;
	}

	public void setEstacionSeleccionada(AgenciaEt estacionSeleccionada) {
		this.estacionSeleccionada = estacionSeleccionada;
	}

	public ZonaEt getZonaSeleccionada() {
		return zonaSeleccionada;
	}

	public void setZonaSeleccionada(ZonaEt zonaSeleccionada) {
		this.zonaSeleccionada = zonaSeleccionada;
	}

	public TipoInventarioEt getTipoInventarioSeleccionado() {
		return tipoInventarioSeleccionado;
	}

	public void setTipoInventarioSeleccionado(TipoInventarioEt tipoInventarioSeleccionado) {
		this.tipoInventarioSeleccionado = tipoInventarioSeleccionado;
	}

	public PlanAccionInventarioTipoEt getPlnAccInvTipSeleccionado() {
		return plnAccInvTipSeleccionado;
	}

	public void setPlnAccInvTipSeleccionado(PlanAccionInventarioTipoEt plnAccInvTipSeleccionado) {
		this.plnAccInvTipSeleccionado = plnAccInvTipSeleccionado;
	}

	public EstadoPlanAccionInvEnum getEstadoPlanAccionSeleccionado() {
		return estadoPlanAccionSeleccionado;
	}

	public void setEstadoPlanAccionSeleccionado(EstadoPlanAccionInvEnum estadoPlanAccionSeleccionado) {
		this.estadoPlanAccionSeleccionado = estadoPlanAccionSeleccionado;
	}

	public List<PlanAccionInventarioTipoEt> getPlanAccionInventarioTipos() {
		return planAccionInventarioTipos;
	}

	public void setPlanAccionInventarioTipos(List<PlanAccionInventarioTipoEt> planAccionInventarioTipos) {
		this.planAccionInventarioTipos = planAccionInventarioTipos;
	}

	@Override
	protected void onDestroy() {
		iZonaDao.remove();
		iRolEtDao.remove();
		iUsuarioDao.remove();
		iAgenciaDao.remove();
		iTipoInventarioDao.remove();
		iParametrolGeneralDao.remove();
		iPlanAccionInventarioTipoDao.remove();
	}

}
