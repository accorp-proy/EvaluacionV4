package com.primax.bean.vs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.ParametrosGeneralesEt;
import com.primax.jpa.param.TipoEstacionEt;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.param.ZonaEt;
import com.primax.jpa.param.ZonaUsuarioEt;
import com.primax.jpa.pla.TableroInventarioCabeceraEt;
import com.primax.jpa.pla.TableroInventarioDetalleEt;
import com.primax.jpa.pla.TableroInventarioEstacionZonaEt;
import com.primax.jpa.pla.TableroInventarioMesEt;
import com.primax.jpa.pla.TableroInventarioNegocioEt;
import com.primax.jpa.pla.TableroInventarioZonaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.IParametrolGeneralDao;
import com.primax.srv.idao.ITableroDetalleInvEstacionDao;
import com.primax.srv.idao.ITableroInventarioCabeceraDao;
import com.primax.srv.idao.ITableroInventarioDetalleDao;
import com.primax.srv.idao.ITableroInventarioEstacionZonaDao;
import com.primax.srv.idao.ITableroInventarioMesDao;
import com.primax.srv.idao.ITableroInventarioNegocioDao;
import com.primax.srv.idao.ITableroInventarioZonaDao;
import com.primax.srv.idao.ITipoEstacionDao;
import com.primax.srv.idao.ITipoInventarioDao;
import com.primax.srv.idao.IUsuarioDao;
import com.primax.srv.idao.IZonaDao;

@Named("TableroControlInvBn")
@ViewScoped
public class TableroControlInvBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IZonaDao iZonaDao;
	@EJB
	private IUsuarioDao iUsuarioDao;
	@EJB
	private IAgenciaDao iAgenciaDao;
	@EJB
	private ITipoEstacionDao iTipoEstacionDao;
	@EJB
	private ITipoInventarioDao iTipoInventarioDao;
	@EJB
	private IParametrolGeneralDao iParametrolGeneralDao;
	@EJB
	private ITableroInventarioMesDao iTableroInventarioMesDao;
	@EJB
	private ITableroInventarioZonaDao iTableroInventarioZonaDao;
	@EJB
	private ITableroInventarioNegocioDao iTableroInventarioNegocioDao;
	@EJB
	private ITableroInventarioDetalleDao iTableroInventarioDetalleDao;
	@EJB
	private ITableroDetalleInvEstacionDao iTableroDetalleInvEstacionDao;
	@EJB
	private ITableroInventarioCabeceraDao iTableroInventarioCabeceraDao;
	@EJB
	private ITableroInventarioEstacionZonaDao iTableroInventarioEstacionZonaDao;

	@Inject
	private AppMain appMain;

	private Long nivelT = 0L;
	private Long nivelE = 0L;
	private Long nivelC = 0L;
	private Long nivelF = 0L;
	private Long cantEstT = 0L;
	private Long cantEstP = 0L;
	private Long cantEstPT = 0L;
	private Long nivel01T = 0L;
	private Long nivel02T = 0L;
	private Long nivel03T = 0L;
	private Long nivel04T = 0L;
	private Long nivel05T = 0L;
	private Long nivel06T = 0L;
	private Long nivel01E = 0L;
	private Long nivel02E = 0L;
	private Long nivel03E = 0L;
	private Long nivel04E = 0L;
	private Long nivel05E = 0L;
	private Long nivel06E = 0L;
	private Long nivel01F = 0L;
	private Long nivel02F = 0L;
	private Long nivel03F = 0L;
	private Long nivel04F = 0L;
	private Long nivel05F = 0L;
	private Long nivel06F = 0L;
	private String nivel01 = "0";
	private String nivel02 = "0";
	private String nivel03 = "0";
	private String nivel04 = "0";
	private String nivel05 = "0";
	private String nivel06 = "0";
	private String nivel07 = "0";
	private String nivel08 = "0";
	private String nivel09 = "0";
	private String nivel10 = "0";
	private Double frecuenciaT = 0D;
	private Double frecuenciaC = 0D;
	private Double frecuenciaP = 0D;
	private String porcentajeC = "";
	private String porcentajeE = "";
	private String frecuenciaTS = "";
	private String frecuenciaCS = "";
	private String frecuenciaPS = "";

	private AgenciaEt estacionSeleccionada;
	private List<ZonaEt> zonaSeleccionadas;
	private List<TableroInventarioMesEt> tabla4;
	private TableroInventarioCabeceraEt tableroC;
	private List<TableroInventarioZonaEt> tabla1;
	private ParametrosGeneralesEt anioSeleccionado;
	private List<TableroInventarioDetalleEt> tabla3;
	private List<TipoInventarioEt> tipoInvSeleccionados;
	private List<TableroInventarioEstacionZonaEt> tabla2;
	private List<ParametrosGeneralesEt> mesesSeleccionados;
	private List<TipoEstacionEt> tipoEstacionSeleccionados;

	@Override
	public void init() {
		inicialzarObj();
		filtrarModelD();
	}

	public void inicialzarObj() {
		tableroC = null;
		tabla1 = new ArrayList<>();
		tabla2 = new ArrayList<>();
		tabla3 = new ArrayList<>();
		tabla4 = new ArrayList<>();
		estacionSeleccionada = null;
		zonaSeleccionadas = new ArrayList<>();
		mesesSeleccionados = new ArrayList<>();
		tipoEstacionSeleccionados = new ArrayList<>();
	}

	public void filtrarModelD() {
		Long idUsuario = 0L;
		try {
			UsuarioEt usuario = appMain.getUsuario();
			idUsuario = usuario.getIdUsuario();
			List<TipoInventarioEt> tipoInventarios = iTipoInventarioDao.getTipoInventarioList(null);
			mostrarColumna(tipoInventarios);
			iTableroInventarioDetalleDao.limpiarTablero(idUsuario);
			iTableroDetalleInvEstacionDao.generar(primerDiaMes(), ultimoDiaMes(), 0L, 0L, 0L, 0L, idUsuario);
			iTableroInventarioZonaDao.generar(idUsuario);
			tabla1 = iTableroInventarioZonaDao.getTablaList(usuario);
			tabla2 = iTableroInventarioEstacionZonaDao.getTablaList(usuario);
			tabla3 = iTableroInventarioDetalleDao.getTablaList(usuario);
			tabla4 = iTableroInventarioMesDao.getTablaList(usuario);
			tableroC = iTableroInventarioCabeceraDao.getTablaCabecera(usuario);
			mostrarTotal(tabla1, tabla2, tableroC);
			if (tableroC == null) {
				tableroC = new TableroInventarioCabeceraEt();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método filtrarModelD " + " " + e.getMessage());
		}
	}

	public void filtrarModel() {
		int anio = 0;
		Long idZona = 0L;
		Long idUsuario = 0L;
		Long idAgencia = 0L;
		String mensaje = "";
		Long idTipoEstacion = 0L;
		Long idTipoInventario = 0L;
		try {
			limpiarObj();
			UsuarioEt usuario = appMain.getUsuario();
			idUsuario = usuario.getIdUsuario();
			mensaje = validarFiltrar();
			iTableroInventarioDetalleDao.limpiarTablero(idUsuario);
			if (mensaje.equals("")) {
				anio = Integer.parseInt((anioSeleccionado.getValorLista()));

				if (estacionSeleccionada != null) {
					idAgencia = estacionSeleccionada.getIdAgencia();
				}
				for (TipoEstacionEt tipoEstacion : tipoEstacionSeleccionados) {
					idTipoEstacion = tipoEstacion.getIdTipoEstacion();
					for (ZonaEt zona : zonaSeleccionadas) {
						idZona = zona.getIdZona();
						mostrarColumna(tipoInvSeleccionados);
						for (TipoInventarioEt tipoInventario : tipoInvSeleccionados) {
							idTipoInventario = tipoInventario.getIdTipoInventario();
							for (ParametrosGeneralesEt parametrosGenerales : mesesSeleccionados) {
								int mes = Integer.parseInt(parametrosGenerales.getValorLista());
								Date fechaDesde = getFechaDesde(mes, anio);
								Date fechaHasta = getFechaHasta(mes, anio);
								iTableroDetalleInvEstacionDao.generar(fechaDesde, fechaHasta, idTipoEstacion, idZona, idAgencia, idTipoInventario, idUsuario);
							}
						}

					}
				}
				iTableroInventarioZonaDao.generar(idUsuario);
				tabla1 = iTableroInventarioZonaDao.getTablaList(usuario);
				tabla2 = iTableroInventarioEstacionZonaDao.getTablaList(usuario);
				tabla3 = iTableroInventarioDetalleDao.getTablaList(usuario);
				tabla4 = iTableroInventarioMesDao.getTablaList(usuario);
				tableroC = iTableroInventarioCabeceraDao.getTablaCabecera(usuario);
				mostrarTotal(tabla1, tabla2, tableroC);
			} else {
				showInfo("Notificación", FacesMessage.SEVERITY_INFO, null, mensaje);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método filtrarModel " + " " + e.getMessage());
		}
	}

	public void guardarNegocio(UsuarioEt usuario) {
		try {
			TableroInventarioNegocioEt tabInvNegocio = new TableroInventarioNegocioEt();
			tabInvNegocio.setCantidadPista(cantEstP);
			tabInvNegocio.setCantidadTienda(cantEstT);
			tabInvNegocio.setUsuarioRegistra(usuario);
			tabInvNegocio.setCantidadPistaTienda(cantEstPT);
			iTableroInventarioNegocioDao.guardarTableroInventarioNegocio(tabInvNegocio, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarNegocio " + " " + e.getMessage());
		}
	}

	public void limpiarObj() {
		try {
			nivel01F = 0L;
			nivel02F = 0L;
			nivel03F = 0L;
			nivel04F = 0L;
			nivel05F = 0L;
			nivel06F = 0L;
			nivel01 = "0";
			nivel02 = "0";
			nivel03 = "0";
			nivel04 = "0";
			nivel05 = "0";
			nivel06 = "0";
			nivel07 = "0";
			nivel08 = "0";
			nivel09 = "0";
			nivel10 = "0";
			cantEstT = 0L;
			cantEstP = 0L;
			cantEstPT = 0L;
			porcentajeC = "";
			porcentajeE = "";
			frecuenciaTS = "";
			frecuenciaCS = "";
			frecuenciaPS = "";
			frecuenciaT = 0.0D;
			frecuenciaC = 0.0D;
			frecuenciaP = 0.0D;
			tabla1 = new ArrayList<>();
			tabla2 = new ArrayList<>();
			tabla3 = new ArrayList<>();
			tabla4 = new ArrayList<>();
			tableroC = new TableroInventarioCabeceraEt();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método limoiarObj " + " " + e.getMessage());
		}
	}

	public void mostrarTotal(List<TableroInventarioZonaEt> tablaT1, List<TableroInventarioEstacionZonaEt> tablaT2, TableroInventarioCabeceraEt tablaCab) {
		Double a = 0.0D;
		Double cantidad = 0D;
		Double frecuencia = 0D;
		DecimalFormat formateador = new DecimalFormat("0.00");
		try {
			nivel01T = tablaT1.stream().mapToLong(p -> p.getNivel01()).sum();
			nivel02T = tablaT1.stream().mapToLong(p -> p.getNivel02()).sum();
			nivel03T = tablaT1.stream().mapToLong(p -> p.getNivel03()).sum();
			nivel04T = tablaT1.stream().mapToLong(p -> p.getNivel04()).sum();
			nivel05T = tablaT1.stream().mapToLong(p -> p.getNivel05()).sum();
			nivel06T = tablaT1.stream().mapToLong(p -> p.getNivel06()).sum();
			nivelT = tablaT1.stream().mapToLong(p -> p.getTotal()).sum();
			if (tablaCab != null) {
				if (tablaCab.getCantidadTienda() + tablaCab.getCantidadTiendaPista() != 0L) {
					cantEstT = tablaCab.getCantidadTienda();
					cantEstPT = tablaCab.getCantidadTiendaPista();
					cantidad = (double) (cantEstT + cantEstPT);
					frecuencia = (double) (nivel01T / cantidad);
					frecuenciaT = new BigDecimal(frecuencia).setScale(2, RoundingMode.HALF_UP).doubleValue();
					frecuenciaTS = formateador.format(frecuenciaT);
				}
				if (tablaCab.getCantidadPista() + tablaCab.getCantidadTiendaPista() != 0L) {
					cantEstP = tablaCab.getCantidadPista();
					cantEstPT = tablaCab.getCantidadTiendaPista();
					cantidad = (double) (cantEstP + cantEstPT);
					frecuencia = (double) (nivel02T / cantidad);
					frecuenciaC = new BigDecimal(frecuencia).setScale(2, RoundingMode.HALF_UP).doubleValue();
					frecuenciaCS = formateador.format(frecuenciaC);
				}
				if (tablaCab.getCantidadPista() != 0L) {
					cantEstP = tablaCab.getCantidadPista();
					cantidad = cantEstP.doubleValue();
					frecuencia = (double) (nivel03T / cantidad);
					frecuenciaP = new BigDecimal(frecuencia).setScale(2, RoundingMode.HALF_UP).doubleValue();
					frecuenciaPS = formateador.format(frecuenciaP);
				}

			}
			nivel01E = tablaT2.stream().mapToLong(p -> p.getNivel01()).sum();
			nivel02E = tablaT2.stream().mapToLong(p -> p.getNivel02()).sum();
			nivel03E = tablaT2.stream().mapToLong(p -> p.getNivel03()).sum();
			nivel04E = tablaT2.stream().mapToLong(p -> p.getNivel04()).sum();
			nivel05E = tablaT2.stream().mapToLong(p -> p.getNivel05()).sum();
			nivel06E = tablaT2.stream().mapToLong(p -> p.getNivel06()).sum();
			nivelE = tablaT2.stream().mapToLong(p -> p.getTotal()).sum();
			if (nivel01E != 0) {
				a = (double) nivel01E / nivelE;
				nivel01F = (long) precision(a * 100, 0);
			}
			porcentajeE += nivel01F + ",";
			if (nivel02E != 0) {
				a = (double) nivel02E / nivelE;
				nivel02F = (long) precision(a * 100, 0);
			}
			porcentajeE += nivel02F + ",";
			if (nivel03E != 0) {
				a = (double) nivel03E / nivelE;
				nivel03F = (long) precision(a * 100, 0);
			}
			porcentajeE += nivel03F + ",";
			if (nivel04E != 0) {
				a = (double) nivel04E / nivelE;
				nivel04F = (long) precision(a * 100, 0);
			}
			porcentajeE += nivel04F + ",";
			if (nivel05E != 0) {
				a = (double) nivel05E / nivelE;
				nivel05F = (long) precision(a * 100, 0);
			}
			porcentajeE += nivel05F + ",";
			if (nivel06E != 0) {
				a = (double) nivel06E / nivelE;
				nivel06F = (long) precision(a * 100, 0);
			}
			porcentajeE += nivel06F;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mostrarTotal" + " " + e.getMessage());
		}
	}

	public double precision(double numero, int digitos) {
		double resultado;
		resultado = numero * Math.pow(10, digitos);
		resultado = Math.round(resultado);
		resultado = resultado / Math.pow(10, digitos);
		return resultado;
	}

	public void mostrarColumna(List<TipoInventarioEt> tipoInventarios) {
		try {
			for (TipoInventarioEt det : tipoInventarios) {
				if (det.getIdTipoInventario() == 1L) {
					nivel01 = det.getDescripcion();
				} else if (det.getIdTipoInventario() == 2L) {
					nivel02 = det.getDescripcion();
				} else if (det.getIdTipoInventario() == 3L) {
					nivel03 = det.getDescripcion();
				} else if (det.getIdTipoInventario() == 4L) {
					nivel04 = det.getDescripcion();
				} else if (det.getIdTipoInventario() == 5L) {
					nivel05 = det.getDescripcion();
				} else if (det.getIdTipoInventario() == 6L) {
					nivel06 = det.getDescripcion();
				} else if (det.getIdTipoInventario() == 7L) {
					nivel07 = det.getDescripcion();
				} else if (det.getIdTipoInventario() == 8L) {
					nivel08 = det.getDescripcion();
				} else if (det.getIdTipoInventario() == 9L) {
					nivel09 = det.getDescripcion();
				} else if (det.getIdTipoInventario() == 10L) {
					nivel10 = det.getDescripcion();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mostrarColumna " + " " + e.getMessage());
		}
	}

	public String validarFiltrar() {
		String mensaje = "";
		try {
			if (anioSeleccionado == null) {
				mensaje = "Por Favor Seleccionar Año";
			}
			if (mesesSeleccionados == null) {
				mensaje = "Por Favor Seleccionar Mes";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método validarFiltrar " + " " + e.getMessage());
		}

		return mensaje;
	}

	public List<ParametrosGeneralesEt> getAnioList() {
		List<ParametrosGeneralesEt> parAnio = new ArrayList<ParametrosGeneralesEt>();
		try {
			ParametrosGeneralesEt parG = iParametrolGeneralDao.getObjPadre("106");
			parAnio = iParametrolGeneralDao.getListaHIjos(parG);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getAnioList " + " " + e.getMessage());
		}
		return parAnio;
	}

	public List<ParametrosGeneralesEt> getMesList() {
		List<ParametrosGeneralesEt> parMes = new ArrayList<ParametrosGeneralesEt>();
		try {
			ParametrosGeneralesEt parG = iParametrolGeneralDao.getObjPadre("108");
			parMes = iParametrolGeneralDao.getListaHIjos(parG);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getMesList " + " " + e.getMessage());
		}
		return parMes;
	}

	public List<AgenciaEt> getAgenciaList() {
		List<AgenciaEt> agencias = new ArrayList<AgenciaEt>();
		try {
			iAgenciaDao.clear();
			if (zonaSeleccionadas != null) {
				for (TipoEstacionEt tipoEstacion : tipoEstacionSeleccionados) {
					for (ZonaEt zona : zonaSeleccionadas) {
						agencias.addAll(iAgenciaDao.getAgenciasPorZonas(zona, tipoEstacion));
					}
				}
			} else {
				agencias = iAgenciaDao.getAgencias(EstadoEnum.ACT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getAgenciaList " + " " + e.getMessage());
		}
		return agencias;
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

	public List<TipoEstacionEt> getTipoEstacionList() {
		List<TipoEstacionEt> tipoEstaciones = new ArrayList<>();
		try {
			tipoEstaciones = iTipoEstacionDao.getTipoEstacionList(null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getTipoEstacionList " + " " + e.getMessage());
		}
		return tipoEstaciones;
	}

	public List<ZonaEt> getZonaList() {
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

	public static String dateMonth(Date date) {
		String result = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = 0;

		try {
			month = calendar.get(Calendar.MONTH);
		} catch (Exception ex) {
		}
		switch (month) {
		case 0: {
			result = "ENERO";
			break;
		}
		case 1: {
			result = "FEBRERO";
			break;
		}
		case 2: {
			result = "MARZO";
			break;
		}
		case 3: {
			result = "ABRIL";
			break;
		}
		case 4: {
			result = "MAYO";
			break;
		}
		case 5: {
			result = "JUNIO";
			break;
		}
		case 6: {
			result = "JULIO";
			break;
		}
		case 7: {
			result = "AGOSTO";
			break;
		}
		case 8: {
			result = "SEPTIEMBRE";
			break;
		}
		case 9: {
			result = "OCTUBRE";
			break;
		}
		case 10: {
			result = "NOVIEMBRE";
			break;
		}
		case 11: {
			result = "DICIEMBRE";
			break;
		}
		default: {
			result = "Error";
			break;
		}
		}
		return result;
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

	public ParametrosGeneralesEt getAnioSeleccionado() {
		return anioSeleccionado;
	}

	public void setAnioSeleccionado(ParametrosGeneralesEt anioSeleccionado) {
		this.anioSeleccionado = anioSeleccionado;
	}

	public AgenciaEt getEstacionSeleccionada() {
		return estacionSeleccionada;
	}

	public void setEstacionSeleccionada(AgenciaEt estacionSeleccionada) {
		this.estacionSeleccionada = estacionSeleccionada;
	}

	public List<ParametrosGeneralesEt> getMesesSeleccionados() {
		return mesesSeleccionados;
	}

	public void setMesesSeleccionados(List<ParametrosGeneralesEt> mesesSeleccionados) {
		this.mesesSeleccionados = mesesSeleccionados;
	}

	public String getNivel01() {
		return nivel01;
	}

	public void setNivel01(String nivel01) {
		this.nivel01 = nivel01;
	}

	public String getNivel02() {
		return nivel02;
	}

	public void setNivel02(String nivel02) {
		this.nivel02 = nivel02;
	}

	public String getNivel03() {
		return nivel03;
	}

	public void setNivel03(String nivel03) {
		this.nivel03 = nivel03;
	}

	public String getNivel04() {
		return nivel04;
	}

	public void setNivel04(String nivel04) {
		this.nivel04 = nivel04;
	}

	public String getNivel05() {
		return nivel05;
	}

	public void setNivel05(String nivel05) {
		this.nivel05 = nivel05;
	}

	public String getNivel06() {
		return nivel06;
	}

	public void setNivel06(String nivel06) {
		this.nivel06 = nivel06;
	}

	public String getNivel07() {
		return nivel07;
	}

	public void setNivel07(String nivel07) {
		this.nivel07 = nivel07;
	}

	public String getNivel08() {
		return nivel08;
	}

	public void setNivel08(String nivel08) {
		this.nivel08 = nivel08;
	}

	public String getNivel09() {
		return nivel09;
	}

	public void setNivel09(String nivel09) {
		this.nivel09 = nivel09;
	}

	public String getNivel10() {
		return nivel10;
	}

	public void setNivel10(String nivel10) {
		this.nivel10 = nivel10;
	}

	public Long getNivel02T() {
		return nivel02T;
	}

	public void setNivel02T(Long nivel02t) {
		nivel02T = nivel02t;
	}

	public Long getNivel03T() {
		return nivel03T;
	}

	public void setNivel03T(Long nivel03t) {
		nivel03T = nivel03t;
	}

	public Long getNivel04T() {
		return nivel04T;
	}

	public void setNivel04T(Long nivel04t) {
		nivel04T = nivel04t;
	}

	public Long getNivel05T() {
		return nivel05T;
	}

	public void setNivel05T(Long nivel05t) {
		nivel05T = nivel05t;
	}

	public Long getNivel06T() {
		return nivel06T;
	}

	public void setNivel06T(Long nivel06t) {
		nivel06T = nivel06t;
	}

	public Long getNivel01T() {
		return nivel01T;
	}

	public void setNivel01T(Long nivel01t) {
		nivel01T = nivel01t;
	}

	public Long getNivelT() {
		return nivelT;
	}

	public void setNivelT(Long nivelT) {
		this.nivelT = nivelT;
	}

	public Long getNivelE() {
		return nivelE;
	}

	public void setNivelE(Long nivelE) {
		this.nivelE = nivelE;
	}

	public Long getNivel01E() {
		return nivel01E;
	}

	public void setNivel01E(Long nivel01e) {
		nivel01E = nivel01e;
	}

	public Long getNivel02E() {
		return nivel02E;
	}

	public void setNivel02E(Long nivel02e) {
		nivel02E = nivel02e;
	}

	public Long getNivel03E() {
		return nivel03E;
	}

	public void setNivel03E(Long nivel03e) {
		nivel03E = nivel03e;
	}

	public Long getNivel04E() {
		return nivel04E;
	}

	public void setNivel04E(Long nivel04e) {
		nivel04E = nivel04e;
	}

	public Long getNivel05E() {
		return nivel05E;
	}

	public void setNivel05E(Long nivel05e) {
		nivel05E = nivel05e;
	}

	public Long getNivel06E() {
		return nivel06E;
	}

	public void setNivel06E(Long nivel06e) {
		nivel06E = nivel06e;
	}

	public TableroInventarioCabeceraEt getTableroC() {
		return tableroC;
	}

	public void setTableroC(TableroInventarioCabeceraEt tableroC) {
		this.tableroC = tableroC;
	}

	public List<ZonaEt> getZonaSeleccionadas() {
		return zonaSeleccionadas;
	}

	public void setZonaSeleccionadas(List<ZonaEt> zonaSeleccionadas) {
		this.zonaSeleccionadas = zonaSeleccionadas;
	}

	public Long getNivelC() {
		return nivelC;
	}

	public void setNivelC(Long nivelC) {
		this.nivelC = nivelC;
	}

	public Long getNivelF() {
		return nivelF;
	}

	public void setNivelF(Long nivelF) {
		this.nivelF = nivelF;
	}

	public Long getNivel01F() {
		return nivel01F;
	}

	public void setNivel01F(Long nivel01f) {
		nivel01F = nivel01f;
	}

	public Long getNivel02F() {
		return nivel02F;
	}

	public void setNivel02F(Long nivel02f) {
		nivel02F = nivel02f;
	}

	public Long getNivel03F() {
		return nivel03F;
	}

	public void setNivel03F(Long nivel03f) {
		nivel03F = nivel03f;
	}

	public Long getNivel04F() {
		return nivel04F;
	}

	public void setNivel04F(Long nivel04f) {
		nivel04F = nivel04f;
	}

	public Long getNivel05F() {
		return nivel05F;
	}

	public void setNivel05F(Long nivel05f) {
		nivel05F = nivel05f;
	}

	public Long getNivel06F() {
		return nivel06F;
	}

	public void setNivel06F(Long nivel06f) {
		nivel06F = nivel06f;
	}

	public List<TipoEstacionEt> getTipoEstacionSeleccionados() {
		return tipoEstacionSeleccionados;
	}

	public void setTipoEstacionSeleccionados(List<TipoEstacionEt> tipoEstacionSeleccionados) {
		this.tipoEstacionSeleccionados = tipoEstacionSeleccionados;
	}

	public String getPorcentajeC() {
		return porcentajeC;
	}

	public void setPorcentajeC(String porcentajeC) {
		this.porcentajeC = porcentajeC;
	}

	public String getPorcentajeE() {
		return porcentajeE;
	}

	public void setPorcentajeE(String porcentajeE) {
		this.porcentajeE = porcentajeE;
	}

	public List<TableroInventarioMesEt> getTabla4() {
		return tabla4;
	}

	public void setTabla4(List<TableroInventarioMesEt> tabla4) {
		this.tabla4 = tabla4;
	}

	public List<TableroInventarioZonaEt> getTabla1() {
		return tabla1;
	}

	public void setTabla1(List<TableroInventarioZonaEt> tabla1) {
		this.tabla1 = tabla1;
	}

	public List<TableroInventarioDetalleEt> getTabla3() {
		return tabla3;
	}

	public void setTabla3(List<TableroInventarioDetalleEt> tabla3) {
		this.tabla3 = tabla3;
	}

	public List<TableroInventarioEstacionZonaEt> getTabla2() {
		return tabla2;
	}

	public void setTabla2(List<TableroInventarioEstacionZonaEt> tabla2) {
		this.tabla2 = tabla2;
	}

	public List<TipoInventarioEt> getTipoInvSeleccionados() {
		return tipoInvSeleccionados;
	}

	public void setTipoInvSeleccionados(List<TipoInventarioEt> tipoInvSeleccionados) {
		this.tipoInvSeleccionados = tipoInvSeleccionados;
	}

	public Long getCantEstT() {
		return cantEstT;
	}

	public void setCantEstT(Long cantEstT) {
		this.cantEstT = cantEstT;
	}

	public Long getCantEstP() {
		return cantEstP;
	}

	public void setCantEstP(Long cantEstP) {
		this.cantEstP = cantEstP;
	}

	public Long getCantEstPT() {
		return cantEstPT;
	}

	public void setCantEstPT(Long cantEstPT) {
		this.cantEstPT = cantEstPT;
	}

	public Double getFrecuenciaT() {
		return frecuenciaT;
	}

	public void setFrecuenciaT(Double frecuenciaT) {
		this.frecuenciaT = frecuenciaT;
	}

	public Double getFrecuenciaC() {
		return frecuenciaC;
	}

	public void setFrecuenciaC(Double frecuenciaC) {
		this.frecuenciaC = frecuenciaC;
	}

	public Double getFrecuenciaP() {
		return frecuenciaP;
	}

	public void setFrecuenciaP(Double frecuenciaP) {
		this.frecuenciaP = frecuenciaP;
	}

	public String getFrecuenciaTS() {
		return frecuenciaTS;
	}

	public void setFrecuenciaTS(String frecuenciaTS) {
		this.frecuenciaTS = frecuenciaTS;
	}

	public String getFrecuenciaCS() {
		return frecuenciaCS;
	}

	public void setFrecuenciaCS(String frecuenciaCS) {
		this.frecuenciaCS = frecuenciaCS;
	}

	public String getFrecuenciaPS() {
		return frecuenciaPS;
	}

	public void setFrecuenciaPS(String frecuenciaPS) {
		this.frecuenciaPS = frecuenciaPS;
	}

	@Override
	public void onDestroy() {
		iZonaDao.remove();
		iZonaDao.remove();
		iAgenciaDao.remove();
		iTipoEstacionDao.remove();
		iTipoInventarioDao.remove();
		iParametrolGeneralDao.remove();
		iTableroInventarioMesDao.remove();
		iTableroInventarioZonaDao.remove();
		iTableroInventarioNegocioDao.remove();
		iTableroInventarioDetalleDao.remove();
		iTableroInventarioCabeceraDao.remove();
		iTableroDetalleInvEstacionDao.remove();
		iTableroInventarioEstacionZonaDao.remove();
	}

}
