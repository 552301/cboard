package org.cboard.services.persist.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Excel导出器(适用于WebAPP)
 *
 * @author OSWorks-XC
 * @since 2010-08-12
 */
public class ExcelExporter {

	private String templatePath;
	private Map parametersDto;
	private List fieldsList;
	private String filename = "Excel.xls";

	/**
	 * 设置数据
	 *
	 * @param pDto
	 *            参数集合
	 * @param pList
	 *            字段集合
	 */
	public void setData(Map pDto, List pList) {
		parametersDto = pDto;
		fieldsList = pList;
	}

	/**
	 * 导出Excel
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/vnd.ms-excel");
		filename = "";// 下载文件名
		response.setHeader("Content-Disposition", "attachment; filename=" + filename + ";");
		ExcelData excelData = new ExcelData(parametersDto, fieldsList);
		ExcelTemplate excelTemplate = new ExcelTemplate();
		excelTemplate.setTemplatePath(getTemplatePath());
		excelTemplate.parse(request);
		ExcelFiller excelFiller = new ExcelFiller(excelTemplate, excelData);
		ByteArrayOutputStream bos = excelFiller.fill(request);
		ServletOutputStream os = response.getOutputStream();
		os.write(bos.toByteArray());
		os.flush();
		os.close();
	}

	/**
	 * 生成Excel到本地（服务运行的系统）
	 * 
	 * @param fileName
	 *            文件名
	 * @param templateName
	 *            模板名
	 * @throws IOException
	 */
	public void export(String fileName, String templateName) throws IOException {
		filename = fileName;// 下载文件名
		InputStream is = new FileInputStream(templateName);
		ExcelData excelData = new ExcelData(parametersDto, fieldsList);
		ExcelTemplate excelTemplate = new ExcelTemplate();
		excelTemplate.setTemplatePath(getTemplatePath());
		excelTemplate.parse(is);
		ExcelFiller excelFiller = new ExcelFiller(excelTemplate, excelData);
		ByteArrayOutputStream bos = excelFiller.fill(excelTemplate.getTemplatePath());
		// 设置要生成的Excel路径
		String excelFullPath = ExcelExporter.class.getResource("/").getPath() + "template/";
		File file = new File(excelFullPath, fileName);
		FileOutputStream fos = new FileOutputStream(file);
		// 当前时间
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		System.err.println(LocalDateTime.now().format(dtf) + "====正在生成Excel文件：" + excelFullPath + fileName);
		bos.writeTo(fos);
		fos.flush();
		fos.close();
		bos.flush();
		bos.close();
		System.err.println(fileName + "生成Excel成功");
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public Map getParametersDto() {
		return parametersDto;
	}

	public void setParametersDto(Map parametersDto) {
		this.parametersDto = parametersDto;
	}

	public List getFieldsList() {
		return fieldsList;
	}

	public void setFieldsList(List fieldsList) {
		this.fieldsList = fieldsList;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
