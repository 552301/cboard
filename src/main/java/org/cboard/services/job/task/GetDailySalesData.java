package org.cboard.services.job.task;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cboard.services.ReportDataService;
import org.cboard.services.persist.excel.ExcelExporter;
import org.cboard.util.DateUtil;

/**
 * （停用）
 * @author EBD403
 *
 */
public class GetDailySalesData {

	
//	public static void exportAllExcel(ReportDataService reportDataService) {
//    	String fileName = "B2C每日销售报表-管理层-total.xls";
//
//    	String templatePath = GetDailySalesData.class.getResource("/").getPath()+"template/dailysalesTotal.xls";
//    	
//    	Map<String,String> map = new HashMap<>();
//    	map.put("btime", DateUtil.getFirstDay());
//    	map.put("etime", DateUtil.getLastDay());
//    	List dailyList = reportDataService.getDailyReport(map);
//    	ExcelExporter excelExporter = new ExcelExporter();
//		excelExporter.setTemplatePath(templatePath);//模板路径
//		excelExporter.setData(null, dailyList);
//		excelExporter.setFilename(fileName);
//		try {
//			excelExporter.export(fileName, templatePath);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    }
//
//	public static void exportMocoExcel(ReportDataService reportDataService) {
//    	String fileName = "B2C每日销售报表-管理层-MOCO.xls";
//
//    	String templatePath = GetDailySalesData.class.getResource("/").getPath()+"template/dailysales-MOCO.xls";
//    	Map<String,String> map = new HashMap<>();
//    	map.put("brand", "MOCO");
//    	map.put("btime", DateUtil.getFirstDay());
//    	map.put("etime", DateUtil.getLastDay());
//    	List dailyList = reportDataService.getDailyReport(map);
//    	ExcelExporter excelExporter = new ExcelExporter();
//		excelExporter.setTemplatePath(templatePath);//模板路径
//		excelExporter.setData(null, dailyList);
//		excelExporter.setFilename(fileName);
//		try {
//			excelExporter.export(fileName, templatePath);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    }
//
//	public static void exportEdExcel(ReportDataService reportDataService) {
//    	String fileName = "B2C每日销售报表-管理层-ED.xls";
//
//    	String templatePath = GetDailySalesData.class.getResource("/").getPath()+"template/dailysales-ED.xls";
//    	Map<String,String> map = new HashMap<>();
//    	map.put("brand", "Edition10");
//    	map.put("btime", DateUtil.getFirstDay());
//    	map.put("etime", DateUtil.getLastDay());
//    	List dailyList = reportDataService.getDailyReport(map);
//    	ExcelExporter excelExporter = new ExcelExporter();
//		excelExporter.setTemplatePath(templatePath);//模板路径
//		excelExporter.setData(null, dailyList);
//		excelExporter.setFilename(fileName);
//		try {
//			excelExporter.export(fileName, templatePath);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    }
//
//	public static void exportLmExcel(ReportDataService reportDataService) {
//    	String fileName = "B2C每日销售报表-管理层-LM.xls";
//
//    	String templatePath = GetDailySalesData.class.getResource("/").getPath()+"template/dailysales-LM.xls";
//    	Map<String,String> map = new HashMap<>();
//    	map.put("brand", "MOKIDS");
//    	map.put("btime", DateUtil.getFirstDay());
//    	map.put("etime", DateUtil.getLastDay());
//    	List dailyList = reportDataService.getDailyReport(map);
//    	ExcelExporter excelExporter = new ExcelExporter();
//		excelExporter.setTemplatePath(templatePath);//模板路径
//		excelExporter.setData(null, dailyList);
//		excelExporter.setFilename(fileName);
//		try {
//			excelExporter.export(fileName, templatePath);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    }

}
