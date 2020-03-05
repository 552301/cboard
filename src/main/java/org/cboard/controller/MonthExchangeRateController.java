package org.cboard.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.cboard.pojo.KhBrand;
import org.cboard.pojo.MonthExchangeRate;
import org.cboard.services.KhBrandService;
import org.cboard.services.MonthExchangeRateService;
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
@RequestMapping("/monthexchangerate")
public class MonthExchangeRateController {

	@Autowired
	private MonthExchangeRateService monthExchangeRateService;

	@Autowired
	private KhBrandService khBrandService;
	
	@RequestMapping(value = "/saveMonthExchangeRate")
	public ServiceStatus saveMonthExchangeRate(@RequestParam(name = "json") String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		MonthExchangeRate dreturn = new MonthExchangeRate();
		dreturn.setYear(jsonObject.getIntValue("year"));
		dreturn.setMonth(jsonObject.getIntValue("month"));
		dreturn.setCaustomer(jsonObject.getString("caustomer"));
		dreturn.setRate(jsonObject.getDoubleValue("rate"));
		return monthExchangeRateService.save(dreturn);
	}

	@RequestMapping(value = "/updateMonthExchangeRate")
	public ServiceStatus updateMonthExchangeRate(@RequestParam(name = "json") String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		MonthExchangeRate dreturn = new MonthExchangeRate();
		dreturn.setId(jsonObject.getIntValue("id"));
		dreturn.setYear(jsonObject.getIntValue("year"));
		dreturn.setMonth(jsonObject.getIntValue("month"));
		dreturn.setCaustomer(jsonObject.getString("caustomer"));
		dreturn.setRate(jsonObject.getDoubleValue("rate"));

		return monthExchangeRateService.update(dreturn);
	}

	@RequestMapping(value = "/deleteMonthExchangeRate")
	public ServiceStatus deleteMonthExchangeRate(@RequestParam(name = "id") Long id) {
		return monthExchangeRateService.delete(id);
	}

	@RequestMapping(value = "/getMonthExchangeRateList")
	public List<MonthExchangeRate> getMonthExchangeRateList(@RequestParam(name = "params") String params) {
		JSONObject jsonObject = JSONObject.parseObject(params);
		Map<String, Object> map = new HashMap<String, Object>();
		if (jsonObject.containsKey("year")){
			map.put("year", jsonObject.getIntValue("year"));
		}
		if(jsonObject.containsKey("month")) {
			map.put("month", jsonObject.getIntValue("month"));
		}
		if (jsonObject.containsKey("caustomer")) {
			if (!jsonObject.getString("caustomer").isEmpty())
				map.put("caustomer", jsonObject.getString("caustomer"));
		}

		List<MonthExchangeRate> vts = monthExchangeRateService.getMonthExchangeRateList(map);
		return vts;
	}

	@RequestMapping(value = "/getMonthExchangeRate")
	public MonthExchangeRate MonthExchangeRate(@RequestParam(name = "id") Long id) {
		MonthExchangeRate veturn = monthExchangeRateService.getMonthExchangeRate(id);
		return veturn;
	}

	/**
	 * 通过Excel导入汇率
	 *
	 * @param file
	 * @return
	 **/
	@RequestMapping(value = "/importMonthExchangeRates")
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
					MonthExchangeRate ds = new MonthExchangeRate();
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
								"导入失败，行：" + (currentRow + 1) + " ；汇率不能小于0");
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
					List<MonthExchangeRate> ts = new ArrayList<MonthExchangeRate>();
					ts = monthExchangeRateService.getMonthExchangeRateList(map);
					ServiceStatus status = null;
					if (ts.size() > 0) {
						ds.setId(ts.get(0).getId());
						status = monthExchangeRateService.update(ds);
					}else {
						status = monthExchangeRateService.save(ds);
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
