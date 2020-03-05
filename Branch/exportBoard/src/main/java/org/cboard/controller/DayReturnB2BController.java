package org.cboard.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.cboard.dataprovider.DataProviderManager;
import org.cboard.dataprovider.DataProviderViewManager;
import org.cboard.dataprovider.config.AggConfig;
import org.cboard.dataprovider.result.AggregateResult;
import org.cboard.dto.ViewDayReturnB2B;
import org.cboard.pojo.DayReturnB2B;
import org.cboard.pojo.KhBrand;
import org.cboard.services.DayReturnB2BService;
import org.cboard.services.KhBrandService;
import org.cboard.services.ServiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@RestController
@RequestMapping("/dayreturnb2b")
public class DayReturnB2BController {

	@Autowired
	private DayReturnB2BService dayReturnB2BService;

	@Autowired
	private KhBrandService khBrandService;
	
	@RequestMapping(value = "/saveDayReturnB2B")
	public ServiceStatus saveSalesTarget(@RequestParam(name = "json") String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		DayReturnB2B dreturn = new DayReturnB2B();
		dreturn.setDate(jsonObject.getDate("date"));
		dreturn.setCaustomer(jsonObject.getString("caustomer"));
		dreturn.setSku(jsonObject.getString("sku"));
		dreturn.setQty(jsonObject.getIntValue("qty"));
		dreturn.setAmount(jsonObject.getDoubleValue("amount"));
		return dayReturnB2BService.save(dreturn);
	}

	@RequestMapping(value = "/updateDayReturnB2B")
	public ServiceStatus updateSalesTarget(@RequestParam(name = "json") String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		DayReturnB2B dreturn = new DayReturnB2B();
		dreturn.setId(jsonObject.getIntValue("id"));
		dreturn.setDate(jsonObject.getDate("date"));
		dreturn.setCaustomer(jsonObject.getString("caustomer"));
		dreturn.setSku(jsonObject.getString("sku"));
		dreturn.setQty(jsonObject.getIntValue("qty"));
		dreturn.setAmount(jsonObject.getDoubleValue("amount"));

		return dayReturnB2BService.update(dreturn);
	}

	@RequestMapping(value = "/deleteDayReturnB2B")
	public ServiceStatus deleteSalesTarget(@RequestParam(name = "id") Long id) {
		return dayReturnB2BService.delete(id);
	}

	@RequestMapping(value = "/getDayReturnB2BList")
	public List<ViewDayReturnB2B> getDayReturnB2BList(@RequestParam(name = "params") String params) {
		JSONObject jsonObject = JSONObject.parseObject(params);
		Map<String, Object> map = new HashMap<String, Object>();
		if (jsonObject.containsKey("year") && jsonObject.containsKey("month")) {
			int theMonth = jsonObject.getIntValue("year") * 100 + jsonObject.getIntValue("month");
			map.put("month", theMonth);
		}
		if (jsonObject.containsKey("platform")) {
			if (!jsonObject.getString("platform").isEmpty())
				map.put("platform", jsonObject.getString("platform"));
		}
		if (jsonObject.containsKey("brand")) {
			if (!jsonObject.getString("brand").isEmpty())
				map.put("brand", jsonObject.getString("brand"));
		}
		if (jsonObject.containsKey("caustomer")) {
			if (!jsonObject.getString("caustomer").isEmpty())
				map.put("caustomer", jsonObject.getString("caustomer"));
		}
		if (jsonObject.containsKey("date")) {
			if (!jsonObject.getString("date").isEmpty())
				map.put("date", jsonObject.getString("date"));
		}

		List<ViewDayReturnB2B> vts = dayReturnB2BService.getViewDayReturnB2BList(map);
		return vts;
	}

	@RequestMapping(value = "/getDayReturnB2B")
	public ViewDayReturnB2B getDayReturnB2B(@RequestParam(name = "id") Long id) {
		ViewDayReturnB2B veturn = dayReturnB2BService.getViewDayReturnB2B(id);
		return veturn;
	}

	/**
	 * 通过Excel导入B2B销售
	 *
	 * @param file
	 * @return
	 **/
	@RequestMapping(value = "/importDayReturnB2B")
	public ServiceStatus importDayReturnB2Bs(@RequestParam(name = "file") MultipartFile file,
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
			if (totalCols != 5) {
				return new ServiceStatus(ServiceStatus.Status.Fail, "导入失败，不符合预期的数据，请使用模板进行数据导入");
			}

			int importedCount = 0;
			int currentRow = 1;
			while (currentRow < totalRows) {
				Row row = sheet.getRow(currentRow);
				try {
					if(row.getCell(0) == null)
						break;
					
					DayReturnB2B ds = new DayReturnB2B();
					ds.setDate(row.getCell(0).getDateCellValue());
					ds.setCaustomer(row.getCell(1).getStringCellValue());
					ds.setSku(row.getCell(2).getStringCellValue());
					if (row.getCell(3) != null) {
						ds.setQty((int) row.getCell(3).getNumericCellValue());
					}
					if (row.getCell(4) != null) {
						ds.setAmount(row.getCell(4).getNumericCellValue());
					}
					
					if (ds.getDate() != null && ds.getCaustomer() != null) {
						KhBrand khBrand = khBrandService.getKhBrandByKhmc(ds.getCaustomer());
						if(khBrand == null) {
							return new ServiceStatus(ServiceStatus.Status.Fail,
									"导入失败，行：" + (currentRow + 1) + " ；无效的客户名称");
						}
						
						// 判断是否已有数据
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("caustomer", ds.getCaustomer());
						map.put("date", ds.getDate());
						map.put("sku", ds.getSku());
						List<ViewDayReturnB2B> ts = new ArrayList<ViewDayReturnB2B>();
						ts = dayReturnB2BService.getViewDayReturnB2BList(map);
						ServiceStatus status = null;
						if (ts.size() > 0) {
							ds.setId(ts.get(0).getId());
							status = dayReturnB2BService.update(ds);
						}else {
							status = dayReturnB2BService.save(ds);
						}
						
						if(status.getStatus().equals(ServiceStatus.Status.Success.toString()))
							importedCount++;
						else
							return new ServiceStatus(ServiceStatus.Status.Fail,
									"导入失败，行：" + (currentRow + 1) + " ；" + status.getMsg());
					}
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
