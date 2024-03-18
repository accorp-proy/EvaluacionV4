package com.primax.bean.maker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.primax.bean.vs.base.BaseReport;
import com.primax.bean.vs.base.DataSourceConnection;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.ResponsableEt;
import com.primax.jpa.pla.PlanAccionInventarioTipoEt;
import com.primax.srv.idao.IAgenciaDao;
import com.primax.srv.idao.IPlanAccionInventarioTipoDao;
import com.primax.srv.idao.IResponsableDao;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;

public class ReporteIndividualPlanAccionInventario extends BaseReport {

	public ByteArrayOutputStream getReport(Map<String, String[]> params, String localPath, ServletContext servlet, boolean isexcel) throws NumberFormatException, EntidadNoEncontradaException {

		String gerente = "";
		String nombreAuditor = "";
		String nombreSoporteO = "";
		String nombreGerente = "N/A";
		ByteArrayOutputStream printStream = null;
		String cargoEstacion = "Gerente de Estación";
		IAgenciaDao iAgenciaDao = EJB(EnumNaming.IAgenciaDao);
		Map<String, Object> paramRpt = new HashMap<String, Object>();
		IResponsableDao iResponsableDao = EJB(EnumNaming.IResponsableDao);
		IPlanAccionInventarioTipoDao iPlnAccionInvTipoDao = EJB(EnumNaming.IPlanAccionInventarioTipoDao);
		try {
			String nombreUsuario = params.get("p1")[0];
			Long par_id_pln_inv_accion_tipo = Long.parseLong(params.get("p2")[0]);
			Long idAgencia = Long.parseLong(params.get("p3")[0]);
			String condicion = params.get("p4")[0];
			if (condicion.equals("1")) {
				PlanAccionInventarioTipoEt tipo = iPlnAccionInvTipoDao.getPlanAccionInvTipo(par_id_pln_inv_accion_tipo);
				nombreAuditor = tipo.getUsuarioRegistra().getPersonaUsuario().getNombreCompleto();
				par_id_pln_inv_accion_tipo = 0L;
				if (tipo != null) {
					par_id_pln_inv_accion_tipo = tipo.getIdPlanAccionInventarioTipo();
				}
			}
			AgenciaEt agencia = iAgenciaDao.getAgenciaById(idAgencia);
			if (agencia != null) {
				List<ResponsableEt> responsables = iResponsableDao.getResponsableByAgencia1List(agencia);
				if (responsables != null && !responsables.isEmpty()) {
					for (ResponsableEt responsable : responsables) {
						if (responsable.getTipoCargo().getDescripcion().equals("GERENTE")) {
							nombreGerente = responsable.getPersona().getNombreCompleto();
							cargoEstacion = responsable.getTipoCargo().getFirma() + " " + ":";
						}
					}
					if (nombreGerente != null && nombreGerente.equals("N/A")) {
						for (ResponsableEt responsable : responsables) {
							if (responsable.getTipoCargo().getDescripcion().equals("SOPORTE OPERATIVO")) {
								nombreSoporteO = responsable.getPersona().getNombreCompleto();
								cargoEstacion = responsable.getTipoCargo().getFirma() + " " + ":";
							}
						}
					}
				}
			}
			if (nombreGerente.equals("N/A")) {
				gerente = nombreSoporteO;
			} else {
				gerente = nombreGerente;
			}
			paramRpt.put("par_gerente", gerente);
			paramRpt.put("par_usuario", nombreUsuario);
			paramRpt.put("par_cargo_estacion", cargoEstacion);
			paramRpt.put("par_nombre_gerente", nombreGerente);
			paramRpt.put("par_nombre_soporte", nombreSoporteO);
			paramRpt.put("par_nombre_auditor", nombreAuditor);
			paramRpt.put("par_id_pln_inv_accion_tipo", par_id_pln_inv_accion_tipo);
			// paramRpt.put("logo", getLogoAtimasa(servlet));
			paramRpt.put("SUBREPORT_DIR", localPath + File.separator + "planAccion" + File.separator);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getReport " + " " + e.getMessage());
		}
		Connection cn = null;

		try {
			cn = DataSourceConnection.getJNDIConnection();
			JasperPrint jprint = JasperFillManager.fillReport(localPath + File.separator + "planAccion" + File.separator + "rptPlanAccionInventario.jasper", paramRpt, cn);
			printStream = new ByteArrayOutputStream();
			if (isexcel) {
				printStream = new ByteArrayOutputStream();
				JRXlsxExporter xlsx = new JRXlsxExporter();
				xlsx.setExporterOutput(new SimpleOutputStreamExporterOutput(printStream));
				xlsx.setExporterInput(new SimpleExporterInput(jprint));
				SimpleXlsxExporterConfiguration conf = new SimpleXlsxExporterConfiguration();
				xlsx.setConfiguration(conf);
				xlsx.exportReport();
			} else {
				printStream = new ByteArrayOutputStream();
				JasperExportManager.exportReportToPdfStream(jprint, printStream);
			}
		} catch (Exception e) {
			System.err.println("Error:No fue posible elaborar el reporte :" + e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				System.err.println("Error en cerrar conexion :" + e.getMessage());
			}
		}
		return printStream;
	}

	public Date getFechaMesAnterior(Date fechaActual) {
		Calendar calendar = Calendar.getInstance();
		int anio = 0, mes = 0, dia = 1;
		Calendar mesAnterior = null;
		Calendar fechaMesAnterior = null;
		try {
			calendar.setTime(fechaActual);
			anio = calendar.get(Calendar.YEAR);
			mes = calendar.get(Calendar.MONTH);
			mesAnterior = new GregorianCalendar(anio, mes - 1, dia);
			anio = mesAnterior.get(Calendar.YEAR);
			mes = mesAnterior.get(Calendar.MONTH);
			dia = mesAnterior.getActualMaximum(Calendar.DAY_OF_MONTH);
			fechaMesAnterior = new GregorianCalendar(anio, mes, dia);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getFechaMesAnterior " + " " + e.getMessage());
		}
		return fechaMesAnterior.getTime();
	}
}
