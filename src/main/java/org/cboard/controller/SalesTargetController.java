package org.cboard.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.cboard.dao.SalesTargetDao;
import org.cboard.dataprovider.DataProviderManager;
import org.cboard.dataprovider.DataProviderViewManager;
import org.cboard.dataprovider.config.AggConfig;
import org.cboard.dataprovider.result.AggregateResult;
import org.cboard.dto.ViewSalesTarget;
import org.cboard.pojo.SalesTarget;
import org.cboard.services.SalesTargetService;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/salestarget")
public class SalesTargetController {

	@Autowired
	private SalesTargetService targetService;
	
	@RequestMapping(value = "/saveSalesTarget")
    public ServiceStatus saveSalesTarget(@RequestParam(name = "json") String json) {
		return targetService.save(json);
	}
	
	@RequestMapping(value = "/updateSalesTarget")
	public ServiceStatus updateSalesTarget(@RequestParam(name = "json") String json) {
		return targetService.update(json);
	}
	
	@RequestMapping(value = "/deleteSalesTarget")
	public ServiceStatus deleteSalesTarget(@RequestParam(name = "id") Long id) {
		return targetService.delete(id);
	}
	
	@RequestMapping(value = "/getSalesTargetList")
	public List<ViewSalesTarget> getSalesTargetList() {
		List<ViewSalesTarget> vts = new ArrayList<ViewSalesTarget>();
		List<SalesTarget> ts = targetService.getSalesTargetList();
		for(SalesTarget t : ts) {
			vts.add(new ViewSalesTarget(t));
		}
		
		return vts;
	}
	
	@RequestMapping(value = "/getSalesTargetListYear")
	public List<ViewSalesTarget> getSalesTargetListYear(@RequestParam(name = "year") int year){
		List<ViewSalesTarget> vts = new ArrayList<ViewSalesTarget>();
		List<SalesTarget> ts = targetService.getSalesTargetListYear(year);
		for(SalesTarget t : ts) {
			vts.add(new ViewSalesTarget(t));
		}
		
		return vts;
	}
	
	@RequestMapping(value = "/getSalesTargetListYearDimension")
	public List<ViewSalesTarget> getSalesTargetListYearDimension(@RequestParam(name = "year") int year, @RequestParam(name = "dimension") String dimension){
		List<ViewSalesTarget> vts = new ArrayList<ViewSalesTarget>();
		List<SalesTarget> ts = targetService.getSalesTargetListYearDimension(year, dimension);
		for(SalesTarget t : ts) {
			vts.add(new ViewSalesTarget(t));
		}
		
		return vts;
	}
	
	@RequestMapping(value = "/getSalesTarget")
	public ViewSalesTarget getSalesTarget(@RequestParam(name = "id") Long id) {
		SalesTarget t = targetService.getSalesTarget(id);
		return new ViewSalesTarget(t);
	}
	
	@RequestMapping(value = "/getHistoryTargetObjects")
	public List<String> getHistoryTargetObjects(@RequestParam(name = "enDimension") String enDimension) {
		String dimension = "";
		if(enDimension.equals("platform"))
			dimension = "平台";
		else if(enDimension.equals("brand"))
			dimension = "品牌";
		else if(enDimension.equals("store"))
			dimension = "店铺";
		else
			dimension = enDimension;
		
		return targetService.getHistorySalesObject(dimension);
	}
}
