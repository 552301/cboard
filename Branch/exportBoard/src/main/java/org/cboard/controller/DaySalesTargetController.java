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
import org.cboard.dto.ViewDaySalesTarget;
import org.cboard.pojo.DaySalesTarget;
import org.cboard.pojo.KhBrand;
import org.cboard.services.DaySalesTargetService;
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
@RequestMapping("/daysalestarget")
public class DaySalesTargetController {

	@Autowired
	private DaySalesTargetService targetService;

	@Autowired
	private KhBrandService khBrandService;
	
	@RequestMapping(value = "/saveDaySalesTarget")
	public ServiceStatus saveSalesTarget(@RequestParam(name = "json") String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		DaySalesTarget target = new DaySalesTarget();
		target.setDate(jsonObject.getDate("date"));
		target.setPlatform(jsonObject.getString("platform"));
		target.setBrand(jsonObject.getString("brand"));
		target.setStore(jsonObject.getString("store"));
		target.setSalesTarget(jsonObject.getFloatValue("salesTarget"));
		target.setRefundTarget(jsonObject.getFloatValue("refundTarget"));
		target.setBookSalesAmount(jsonObject.getFloatValue("bookSalesAmount"));
		target.setCashbackTarget(jsonObject.getFloat("cashbackTarget"));

		return targetService.save(target);
	}

	@RequestMapping(value = "/updateDaySalesTarget")
	public ServiceStatus updateSalesTarget(@RequestParam(name = "json") String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		DaySalesTarget target = new DaySalesTarget();
		target.setId(jsonObject.getLong("id"));
		target.setDate(jsonObject.getDate("date"));
		target.setPlatform(jsonObject.getString("platform"));
		target.setBrand(jsonObject.getString("brand"));
		target.setStore(jsonObject.getString("store"));
		target.setSalesTarget(jsonObject.getFloatValue("salesTarget"));
		target.setRefundTarget(jsonObject.getFloatValue("refundTarget"));
		target.setBookSalesAmount(jsonObject.getFloatValue("bookSalesAmount"));
		target.setCashbackTarget(jsonObject.getFloat("cashbackTarget"));

		return targetService.update(target);
	}

	@RequestMapping(value = "/deleteDaySalesTarget")
	public ServiceStatus deleteSalesTarget(@RequestParam(name = "id") Long id) {
		return targetService.delete(id);
	}

	@RequestMapping(value = "/getDaySalesTargetList")
	public List<ViewDaySalesTarget> getDaySalesTargetList(@RequestParam(name = "params") String params) {
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
		if (jsonObject.containsKey("store")) {
			if (!jsonObject.getString("store").isEmpty())
				map.put("store", jsonObject.getString("store"));
		}
		if (jsonObject.containsKey("date")) {
			if (!jsonObject.getString("date").isEmpty())
				map.put("date", jsonObject.getString("date"));
		}

		List<ViewDaySalesTarget> vts = new ArrayList<ViewDaySalesTarget>();
		List<DaySalesTarget> ts = targetService.getDaySalesTargetList(map);
		for (DaySalesTarget t : ts) {
			vts.add(new ViewDaySalesTarget(t));
		}

		return vts;
	}

	@RequestMapping(value = "/getDaySalesTarget")
	public ViewDaySalesTarget getDaySalesTarge(@RequestParam(name = "id") Long id) {
		DaySalesTarget t = targetService.getDaySalesTarget(id);
		return new ViewDaySalesTarget(t);
	}

	/**
	 * 通过Excel导入目标
	 *
	 * @param file
	 * @return
	 **/
	@RequestMapping(value = "/importDaySalesTargets")
	public ServiceStatus importDaySalesTargets(@RequestParam(name = "file") MultipartFile file,
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
			if (totalCols != 8) {
				return new ServiceStatus(ServiceStatus.Status.Fail, "导入失败，不符合预期的数据，请使用模板进行数据导入");
			}

			int importedCount = 0;
			int currentRow = 1;
			while (currentRow < totalRows) {
				Row row = sheet.getRow(currentRow);
				try {
					DaySalesTarget dst = new DaySalesTarget();
					dst.setDate(row.getCell(0).getDateCellValue());
					dst.setPlatform(row.getCell(1).getStringCellValue());
					dst.setBrand(row.getCell(2).getStringCellValue());
					dst.setStore(row.getCell(3).getStringCellValue());
					if (row.getCell(4) != null) {
						dst.setCashbackTarget(row.getCell(4).getNumericCellValue());
					}
					if (row.getCell(5) != null) {
						dst.setSalesTarget(row.getCell(5).getNumericCellValue());
					}
					if (row.getCell(6) != null) {
						dst.setRefundTarget(row.getCell(6).getNumericCellValue());
					}
					if (row.getCell(7) != null) {
						dst.setBookSalesAmount(row.getCell(7).getNumericCellValue());
					}

					if (dst.getDate() != null && dst.getPlatform() != null && dst.getBrand() != null
							&& dst.getStore() != null) {
						KhBrand khBrand = khBrandService.getKhBrandByKhmc(dst.getStore());
						if(khBrand == null) {
							return new ServiceStatus(ServiceStatus.Status.Fail,
									"导入失败，行：" + (currentRow + 1) + " ；无效的店铺名称");
						}
						if(!khBrand.getBrand().equals(dst.getBrand()) ) {
							return new ServiceStatus(ServiceStatus.Status.Fail,
									"导入失败，行：" + (currentRow + 1) + " ；店铺和品牌不对应");
						}
						if(!khBrand.getLylx().equals(dst.getPlatform()) ) {
							return new ServiceStatus(ServiceStatus.Status.Fail,
									"导入失败，行：" + (currentRow + 1) + " ；店铺和平台不对应");
						}
						
						// 判断是否已有数据
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("platform", dst.getPlatform());
						map.put("brand", dst.getBrand());
						map.put("store", dst.getStore());
						map.put("date", dst.getDate());
						List<DaySalesTarget> ts = new ArrayList<DaySalesTarget>();
						ts = targetService.getDaySalesTargetList(map);
						if (ts.size() > 0) {
							dst.setId(ts.get(0).getId());
							if (targetService.update(dst).getStatus().equals(ServiceStatus.Status.Success.toString()))
								importedCount++;
						}else {
							if (targetService.save(dst).getStatus().equals(ServiceStatus.Status.Success.toString()))
								importedCount++;
						}

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
