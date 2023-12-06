package com.primax.bean.maker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import com.primax.bean.vs.base.BaseReport;
import com.primax.bean.vs.base.DataSourceConnection;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.pla.CheckListEt;
import com.primax.srv.idao.ICheckListDao;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;

public class ReporteIndividualInformeDinamico extends BaseReport {

	public ByteArrayOutputStream getReport(Map<String, String[]> params, String localPath, ServletContext servlet,
			boolean isexcel) throws NumberFormatException, EntidadNoEncontradaException {

		String rutaLogo = "";
		Long idCheckList = Long.valueOf(params.get("p1")[0]);
		ICheckListDao iCheckListDao = EJB(EnumNaming.ICheckListDao);
		CheckListEt checkList = iCheckListDao.getCheckList(idCheckList);

		ByteArrayOutputStream printStream = null;
		Map<String, Object> paramRpt = new HashMap<String, Object>();
		paramRpt.put("par_id_check_list", Long.valueOf(params.get("p1")[0]));
		if (checkList.getParametroLogo().getIdParametroGeneral() == 168L) {
			rutaLogo = rutaLogo(servlet);
		} else {
			rutaLogo = rutaLogoP(servlet);
		}
		paramRpt.put("logo", rutaLogo);
		paramRpt.put("SUBREPORT_DIR", localPath + File.separator + "informeDinamico" + File.separator);
		Connection cn = null;
		try {
			cn = DataSourceConnection.getJNDIConnection();
			JasperPrint jprint = JasperFillManager.fillReport(
					localPath + File.separator + "informeDinamico" + File.separator + "rptInformeDinamico.jasper",
					paramRpt, cn);
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
				iCheckListDao.remove();
			} catch (SQLException e) {
				System.err.println("Error en cerrar conexion :" + e.getMessage());
			}
		}
		return printStream;
	}

	public String rutaLogoA(ServletContext ctx) {
		String path = "";
		try {
			path = ctx.getRealPath("/resources/ultima-layout/images/atimasa.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	public String rutaLogoP(ServletContext ctx) {
		String path = "";
		try {
			path = ctx.getRealPath("/resources/ultima-layout/images/logo.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
}
