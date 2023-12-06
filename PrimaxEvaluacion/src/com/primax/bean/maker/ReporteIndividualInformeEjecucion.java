package com.primax.bean.maker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.primax.bean.vs.base.BaseReport;
import com.primax.bean.vs.base.DataSourceConnection;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.enm.gen.PersonaEnum;
import com.primax.enm.gen.RutaFileEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.pla.CheckListEjecucionAdjuntoEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ICheckListEjecucionAdjuntoDao;
import com.primax.srv.idao.ICheckListEjecucionDao;
import com.primax.srv.idao.IGeneralUtilsDao;
import com.primax.srv.idao.IUsuarioDao;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;

public class ReporteIndividualInformeEjecucion extends BaseReport {

	public ByteArrayOutputStream getReport(Map<String, String[]> params, String localPath, ServletContext servlet,
			boolean isexcel) throws NumberFormatException, EntidadNoEncontradaException {

		String rutaLogo = "";
		String rutaTemp = "";
		String nombreArchivo = "";

		IUsuarioDao iUsuarioDao = EJB(EnumNaming.IUsuarioDao);
		IGeneralUtilsDao iGeneralUtilsDao = EJB(EnumNaming.IGeneralUtilsDao);
		ICheckListEjecucionDao iCheckListEjecucionDao = EJB(EnumNaming.ICheckListEjecucionDao);
		ICheckListEjecucionAdjuntoDao iCheckListEjecucionAdjuntoDao = EJB(EnumNaming.ICheckListEjecucionAdjuntoDao);

		ByteArrayOutputStream printStream = null;
		Map<String, Object> paramRpt = new HashMap<String, Object>();
		try {
			Long idCheckList = Long.valueOf(params.get("p1")[0]);
			CheckListEjecucionEt checkList = iCheckListEjecucionDao.getCheckListEjecucion(idCheckList);
			if (checkList.getCheckListPlantilla().getParametroLogo().getIdParametroGeneral() == 168L) {
				rutaLogo = rutaLogo(servlet);
			} else {
				rutaLogo = rutaLogoP(servlet);
			}
			UsuarioEt usuario = iUsuarioDao.getUsuarioId(1L);
			List<CheckListEjecucionAdjuntoEt> adjuntos = iCheckListEjecucionAdjuntoDao.getAdjunto(idCheckList);
			for (CheckListEjecucionAdjuntoEt adjunto : adjuntos) {
				nombreArchivo = adjunto.getNombreAdjunto();
				if (nombreArchivo.toLowerCase().contains(".png") || nombreArchivo.toLowerCase().contains(".jpg")) {
					String pathProyecto = RutaFileEnum.RUTA_PROYECTO_DEPLOYED.getDescripcion();
					String pathImagenTemp = RutaFileEnum.RUTA_IMAGEN_TEMPORAL.getDescripcion();
					rutaTemp = iGeneralUtilsDao.creaRuta(adjunto.getIdCheckListEjecucionAdjunto(),
							pathProyecto + File.separatorChar + pathImagenTemp + File.separatorChar
									+ PersonaEnum.USUARIO.getDescripcion());

					String pathControl = RutaFileEnum.RUTA_CONTROL_INTERNO.getDescripcion() + File.separatorChar
							+ idCheckList + File.separatorChar + nombreArchivo;
					InputStream inputStreamImg = getImg(pathControl);
					iGeneralUtilsDao.copyFile(nombreArchivo, inputStreamImg, rutaTemp);
					adjunto.setImagenPath(rutaTemp + File.separatorChar + nombreArchivo);
					iCheckListEjecucionAdjuntoDao.guardarCheckListEjecucionAdjunto(adjunto, usuario);

				}
			}
			paramRpt.put("logo", rutaLogo);
			paramRpt.put("par_id_check_list_ejecucion", Long.valueOf(params.get("p1")[0]));
			paramRpt.put("SUBREPORT_DIR", localPath + File.separator + "ejecucionInforme" + File.separator);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método getReport " + " " + e.getMessage());
		}
		Connection cn = null;
		try {
			cn = DataSourceConnection.getJNDIConnection();
			JasperPrint jprint = JasperFillManager.fillReport(localPath + File.separator + "ejecucionInforme"
					+ File.separator + "rptEjecucionInformeDinamico.jasper", paramRpt, cn);
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
				iCheckListEjecucionDao.remove();
				iCheckListEjecucionAdjuntoDao.remove();
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

	public InputStream getImg(String pathImg) {
		String path = pathImg;
		System.out.println(path);
		File arch = new File(path);
		InputStream img = null;
		try {
			img = new FileInputStream(arch);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return img;
	}
}
