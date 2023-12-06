package com.primax.bean.maker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import com.primax.bean.as.WebAppUtil;
import com.primax.bean.util.SignatureConvert;
import com.primax.bean.vs.base.BaseReport;
import com.primax.bean.vs.base.DataSourceConnection;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.enm.gen.RutaFileEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.pla.CheckListEjecucionFirmaEt;
import com.primax.srv.idao.ICheckListEjecucionDao;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.srv.idao.IResponsableDao;
import com.primax.srv.idao.IUsuarioDao;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;

public class ReporteIndividualCheckListEjecutadoEspecifico extends BaseReport {

	public ByteArrayOutputStream getReport(Map<String, String[]> params, String localPath, ServletContext servlet,
			boolean isexcel) throws NumberFormatException, EntidadNoEncontradaException {

		String nombreAuditor = "";
		String nombreGerente = "";
		String nombreSoporte = "";
		String firmaAuditor = null;
		String firmaGerente = null;
		String firmaSoporte = null;
		ByteArrayOutputStream printStream = null;
		IUsuarioDao iUsuarioDao = EJB(EnumNaming.IUsuarioDao);
		Map<String, Object> paramRpt = new HashMap<String, Object>();
		IResponsableDao iResponsableDao = EJB(EnumNaming.IResponsableDao);
		ICheckListEjecucionDao iCheckListEjecucionDao = EJB(EnumNaming.ICheckListEjecucionDao);
		try {
			String nombreUsuario = params.get("p1")[0];
			Long id = Long.parseLong(params.get("p2")[0]);
			String nombreCheckList = params.get("p3")[0];
			String codigoCheckList = params.get("p4")[0];
			CheckListEjecucionEt checkListEjecucion = iCheckListEjecucionDao.getCheckListEjecucion(id);
			for (CheckListEjecucionFirmaEt checkListEjecucionFirma : checkListEjecucion.getCheckListEjecucionFirma()) {
				if (checkListEjecucionFirma.getOrden() == 0L) {
					if (checkListEjecucionFirma.isFirmado()) {
						firmaAuditor = generarRutaFirma(checkListEjecucionFirma);
					}
					nombreAuditor = checkListEjecucionFirma.getNombre();
				}
				if (checkListEjecucionFirma.getOrden() == 1L) {
					if (checkListEjecucionFirma.isFirmado()) {
						firmaSoporte = generarRutaFirma(checkListEjecucionFirma);
					}
					nombreSoporte = checkListEjecucionFirma.getNombre();
				}
				if (checkListEjecucionFirma.getOrden() == 2L) {
					if (checkListEjecucionFirma.isFirmado()) {
						firmaGerente = generarRutaFirma(checkListEjecucionFirma);
					}
					nombreGerente = checkListEjecucionFirma.getNombre();
				}
			}
			paramRpt.put("par_usuario", nombreUsuario);
			paramRpt.put("par_codigo", codigoCheckList);
			paramRpt.put("logo", getLogoAtimasa(servlet));
			paramRpt.put("par_id_check_list_ejecucion", id);
			paramRpt.put("par_firma_auditor", firmaAuditor);
			paramRpt.put("par_firma_gerente", firmaGerente);
			paramRpt.put("par_firma_soporte", firmaSoporte);
			paramRpt.put("par_nombre_auditor", nombreAuditor);
			paramRpt.put("par_nombre_gerente", nombreGerente);
			paramRpt.put("par_nombre_soporte", nombreSoporte);
			paramRpt.put("par_nombre_check_list", nombreCheckList.toUpperCase());
			paramRpt.put("SUBREPORT_DIR", localPath + File.separator + "ejecucion" + File.separator);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getReport " + " " + e.getMessage());
		}
		Connection cn = null;

		try {
			cn = DataSourceConnection.getJNDIConnection();
			JasperPrint jprint = JasperFillManager.fillReport(localPath + File.separator + "ejecucion" + File.separator
					+ "rptCheckListEjecutadoEspecifico.jasper", paramRpt, cn);
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
				iUsuarioDao.remove();
				iResponsableDao.remove();
				iCheckListEjecucionDao.remove();
			} catch (SQLException e) {
				System.err.println("Error en cerrar conexion :" + e.getMessage());
			}
		}
		return printStream;
	}

	public String generarRutaFirma(CheckListEjecucionFirmaEt check) {
		Long id = 0L;
		Long idF = 0L;
		String pathTemp = "";
		byte[] bytesF = null;
		String pathFirma = "";
		InputStream inputStreamImg = null;
		String pathP = WebAppUtil.getWebPath();
		SignatureConvert signatureConvert = new SignatureConvert();
		IGeneralUtilsDao iGUtilsDao = EJB(EnumNaming.IGeneralUtilsDao);
		String pathImgTemp = RutaFileEnum.RUTA_IMAGEN_TEMPORAL.getDescripcion();
		try {
			id = check.getCheckListEjecucion().getIdCheckListEjecucion();
			idF = check.getIdCheckListEjecucionFirma();
			bytesF = signatureConvert.redrawSignature(signatureConvert.extractSignature(check.getFirma()));
			inputStreamImg = new ByteArrayInputStream(bytesF);
			pathTemp = iGUtilsDao.creaRuta(id,
					pathP + File.separatorChar + pathImgTemp + File.separatorChar + "CHECKLIST");
			iGUtilsDao.copyFile(idF + ".png", inputStreamImg, pathTemp);
			pathFirma = pathTemp + idF + ".png";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generarRutaFirma " + " " + e.getMessage());
		}
		return pathFirma;
	}

}
