package org.cboard.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.cboard.dto.ViewSaleStorage;
import org.cboard.pojo.SaleStorage;
import org.cboard.services.SaleStorageService;
import org.cboard.services.ServiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;

/**
 * 进销存数据
 *
 */
@RestController
@RequestMapping("/salestorage")
public class SaleStorageController {

	@Autowired
	private SaleStorageService saleStorageService;

	// 保存数据
	@RequestMapping(value = "/saveSaleStorage")
	public ServiceStatus saveSaleStorage(
			@RequestParam(name = "json") String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		SaleStorage sales = new SaleStorage();
		sales.setDate(jsonObject.getDate("date"));
		sales.setSku(jsonObject.getString("sku"));
		return saleStorageService.save(sales);
	}

	// 更新数据
	@RequestMapping(value = "/updateSaleStorage")
	public ServiceStatus updateSaleStorage(
			@RequestParam(name = "json") String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		SaleStorage sales = new SaleStorage();
		sales.setId(jsonObject.getIntValue("id"));
		sales.setDate(jsonObject.getDate("date"));
		sales.setSku(jsonObject.getString("sku"));
		return saleStorageService.update(sales);
	}

	// 删除数据
	@RequestMapping(value = "/deleteSaleStorage")
	public ServiceStatus deleteSaleStorage(@RequestParam(name = "id") Long id) {
		return saleStorageService.delete(id);
	}

	// 查询列表
	@RequestMapping(value = "/getSaleStorageList")
	public List<ViewSaleStorage> getSaleStorageList(
			@RequestParam(name = "params") String params) {
		JSONObject jsonObject = JSONObject.parseObject(params);
		Map<String, Object> map = new HashMap<String, Object>();
		if (jsonObject.containsKey("sku")) {
			if (!jsonObject.getString("sku").isEmpty())
				map.put("sku", jsonObject.getString("sku"));
		}
		if (jsonObject.containsKey("date")) {
			if (!jsonObject.getString("date").isEmpty())
				map.put("date", jsonObject.getString("date"));
		}

		List<ViewSaleStorage> vts = saleStorageService
				.getViewSaleStorageList(map);
		return vts;
	}

	// 根据id获取
	@RequestMapping(value = "/getSaleStorage")
	public ViewSaleStorage getSaleStorage(@RequestParam(name = "id") Long id) {
		ViewSaleStorage vsales = saleStorageService.getViewSaleStorage(id);
		return vsales;
	}

	/**
	 * 通过Excel导入进销存基础数据
	 *
	 * @param file
	 * @return
	 **/
	@RequestMapping(value = "/importSalestorage")
	public ServiceStatus importSaleStorage(
			@RequestParam(name = "file") MultipartFile file,
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
			Workbook  wb = new XSSFWorkbook(is); 
			/*try { 
			    wb = new HSSFWorkbook(is); 
			} catch (Exception ex) {
			    // 解决read error异常
				System.out.println("-----------HSSFWorkbook---------------");
				is = new FileInputStream(fileName);
			    wb = new XSSFWorkbook(is); 
			}*/ 
			
			//Workbook wb = new XSSFWorkbook(is);
			Sheet sheet = wb.getSheetAt(0);
			int totalRows = sheet.getPhysicalNumberOfRows();
			int totalCols = 0;

			if (totalRows > 1 && sheet.getRow(0) != null) {

				totalCols = sheet.getRow(0).getPhysicalNumberOfCells();
			}
			if (totalCols != 2) {
				return new ServiceStatus(ServiceStatus.Status.Fail, "导入失败，不符合预期的数据，请使用模板进行数据导入");
			}

			int importedCount = 0;
			int currentRow = 1;
			while (currentRow < totalRows) {
				Row row = sheet.getRow(currentRow);
				try {
					if(row.getCell(0) == null || row.getCell(0).getDateCellValue() == null)
						break;
					
					SaleStorage ds = new SaleStorage();
					ds.setDate(row.getCell(0).getDateCellValue());
					ds.setSku(row.getCell(1).getStringCellValue());
					
					if (ds.getDate() != null && ds.getSku() != null) {
						
						// 判断是否已有数据
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("date", ds.getDate());
						map.put("sku", ds.getSku());
						List<ViewSaleStorage> ts = new ArrayList<ViewSaleStorage>();
						ts = saleStorageService.getViewSaleStorageList(map);

						ServiceStatus status = null;
						if (ts.size() > 0) {
							ds.setId(ts.get(0).getId());
							status = saleStorageService.update(ds);
						}else {
							status = saleStorageService.save(ds);
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
