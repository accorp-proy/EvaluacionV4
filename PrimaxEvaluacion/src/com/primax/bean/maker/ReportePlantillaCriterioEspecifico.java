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
import com.primax.exc.gen.EntidadNoEncontradaException;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;

public class ReportePlantillaCriterioEspecifico extends BaseReport {

	public ByteArrayOutputStream getReport(Map<String, String[]> params, String localPath, ServletContext servlet,
			boolean isexcel) throws NumberFormatException, EntidadNoEncontradaException {

		String nombreCheckList = params.get("p3")[0] == null ? "CheckList" : params.get("p3")[0].toUpperCase();
		ByteArrayOutputStream printStream = null;
		Map<String, Object> paramRpt = new HashMap<String, Object>();
		paramRpt.put("par_usuario", params.get("p1")[0]);
		paramRpt.put("id_check_list", Long.valueOf(params.get("p2")[0]));
		paramRpt.put("par_nombre_check_list", nombreCheckList);
		paramRpt.put("par_codigo", params.get("p4")[0]);
		Connection cn = null;
		try {
			cn = DataSourceConnection.getJNDIConnection();
			JasperPrint jprint = JasperFillManager.fillReport(
					localPath + File.separator + "plantilla" + File.separator + "rptPlantillaCriterioEspecifico.jasper", paramRpt,
					cn);
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
}
