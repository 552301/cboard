package org.cboard.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.cboard.pojo.KhBrand;
import org.cboard.pojo.MonthReturnRateB2B;
import org.cboard.services.KhBrandService;
import org.cboard.services.MonthReturnRateB2BService;
import org.cboard.services.ServiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@RestController
@RequestMapping("/monthreturnrateb2b")
public class MonthReturnRateB2BController {

	@Autowired
	private MonthReturnRateB2BService monthReturnRateB2BService;

	@Autowired
	private KhBrandService khBrandService;
	
	@RequestMapping(value = "/saveMonthReturnRateB2B")
	public ServiceStatus saveMonthReturnRateB2B(@RequestParam(name = "json") String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		MonthReturnRateB2B dreturn = new MonthReturnRateB2B();
		dreturn.setYear(jsonObject.getIntValue("year"));
		dreturn.setMonth(jsonObject.getIntValue("month"));
		dreturn.setCaustomer(jsonObject.getString("caustomer"));
		dreturn.setCaustomer(jsonObject.getString("caustomer"));
		dreturn.setRate(jsonObject.getDoubleValue("rate"));
		return monthReturnRateB2BService.save(dreturn);
	}

	@RequestMapping(value = "/updateMonthReturnRateB2B")
	public ServiceStatus updateMonthReturnRateB2B(@RequestParam(name = "json") String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		MonthReturnRateB2B dreturn = new MonthReturnRateB2B();
		dreturn.setId(jsonObject.getIntValue("id"));
		dreturn.setYear(jsonObject.getIntValue("year"));
		dreturn.setMonth(jsonObject.getIntValue("month"));
		dreturn.setCaustomer(jsonObject.getString("caustomer"));
		dreturn.setCaustomer(jsonObject.getString("caustomer"));
		dreturn.setRate(jsonObject.getDoubleValue("rate"));

		return monthReturnRateB2BService.update(dreturn);
	}

	@RequestMapping(value = "/deleteMonthReturnRateB2B")
	public ServiceStatus deleteMonthReturnRateB2B(@RequestParam(name = "id") Long id) {
		return monthReturnRateB2BService.delete(id);
	}

	@RequestMapping(value = "/getMonthReturnRateB2BList")
	public List<MonthReturnRateB2B> getMonthReturnRateB2BList(@RequestParam(name = "params") String params) {
		JSONObject jsonObject = JSONObject.parseObject(params);
		Map<String, Object> map = new HashMap<String, Object>();
		if (jsonObject.containsKey("year") ) {
			String  theYear = jsonObject.getString("year");
			map.put("year", theYear);
		}
		if (jsonObject.containsKey("month")) {
			String  theMonth = jsonObject.getString("month");
			map.put("month", theMonth);
		}
		if (jsonObject.containsKey("caustomer")) {
			if (!jsonObject.getString("caustomer").isEmpty())
				map.put("caustomer", jsonObject.getString("caustomer"));
		}

		List<MonthReturnRateB2B> vts = monthReturnRateB2BService.getMonthReturnRateB2BList(map);
		return vts;
	}

	@RequestMapping(value = "/getMonthReturnRateB2B")
	public MonthReturnRateB2B getMonthReturnRateB2B(@RequestParam(name = "id") Long id) {
		MonthReturnRateB2B veturn = monthReturnRateB2BService.getMonthReturnRateB2B(id);
		return veturn;
	}

	/**
	 * 通过Excel导入B2B退货率
	 *
	 * @param file
	 * @return
	 **/
	@RequestMapping(value = "/importMonthReturnRateB2Bs")
	public ServiceStatus importMonthReturnRateB2Bs(@RequestParam(name = "file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			CommonsMultipartFile cmpf = (CommonsMultipartFile) file;
			File dir = new File(request.getSession().getServletContext().getRealPath("/uplpaded"));
			if (!dir.exists())
				dir.mkdirs();

			String fileName = request.getSession().getServletContext().getRealPath("/uplpaded") + "\\"
					+ new Date().getTime() + ".xlsx";
			File excelFile = new File(fileName);
			try {
				cmpf.getFileItem().write(excelFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			InputStream is = new FileInputStream(fileName);
			Workbook wb = new XSSFWorkbook(is);
			Sheet sheet = wb.getSheetAt(0);
			int totalRows = sheet.getPhysicalNumberOfRows();
			int totalCols = 0;

			if (totalRows > 1 && sheet.getRow(0) != null) {

				totalCols = sheet.getRow(0).getPhysicalNumberOfCells();
			}
			if (totalCols != 4) {
				return new ServiceStatus(ServiceStatus.Status.Fail, "导入失败，不符合预期的数据，请使用模板进行数据导入");
			}

			int importedCount = 0;
			int currentRow = 1;
			while (currentRow < totalRows) {
				Row row = sheet.getRow(currentRow);
				try {
					if(row.getCell(0) == null)
						break;
					
					MonthReturnRateB2B ds = new MonthReturnRateB2B();
					try {
						ds.setYear((int)row.getCell(0).getNumericCellValue());
						ds.setMonth((int)row.getCell(1).getNumericCellValue());
						
						new SimpleDateFormat("yyyyMM").parse(Integer.toString((ds.getYear() * 100 + ds.getMonth()))); 
					}catch(Exception ex) {
						return new ServiceStatus(ServiceStatus.Status.Fail,
								"导入失败，行：" + (currentRow + 1) + " ；无效的年月");
					}
					ds.setCaustomer(row.getCell(2).getStringCellValue());
					if (row.getCell(3) != null) {
						ds.setRate(row.getCell(3).getNumericCellValue());
					}
					
					if(ds.getRate() < 0 ) {
						return new ServiceStatus(ServiceStatus.Status.Fail,
								"导入失败，行：" + (currentRow + 1) + " ；退货率不能小于0");
					}
					
					if (ds.getCaustomer() != null) {
						KhBrand khBrand = khBrandService.getKhBrandByKhmc(ds.getCaustomer());
						if(khBrand == null) {
							return new ServiceStatus(ServiceStatus.Status.Fail,
									"导入失败，行：" + (currentRow + 1) + " ；无效的客户名称");
						}
					}
					
					// 判断是否已有数据
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("caustomer", ds.getCaustomer());
					map.put("year", ds.getYear());
					map.put("month", ds.getMonth());
					List<MonthReturnRateB2B> ts = new ArrayList<MonthReturnRateB2B>();
					ts = monthReturnRateB2BService.getMonthReturnRateB2BList(map);
					ServiceStatus status = null;
					if (ts.size() > 0) {
						ds.setId(ts.get(0).getId());
						status = monthReturnRateB2BService.update(ds);
					}else {
						status = monthReturnRateB2BService.save(ds);
					}
					
					if(status.getStatus().equals(ServiceStatus.Status.Success.toString()))
						importedCount++;
					else
						return new ServiceStatus(ServiceStatus.Status.Fail,
								"导入失败，行：" + (currentRow + 1) + " ；" + status.getMsg());
					
				} catch (IllegalStateException ie) {
					ie.printStackTrace();
				} catch (NumberFormatException ne) {
					ne.printStackTrace();
				}

				currentRow++;
			}
			return new ServiceStatus(ServiceStatus.Status.Success,
					"导入成功，共导入 " + importedCount + "/" + (totalRows - 1) + "条记录");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return new ServiceStatus(ServiceStatus.Status.Fail, "导入失败");
	}
}
