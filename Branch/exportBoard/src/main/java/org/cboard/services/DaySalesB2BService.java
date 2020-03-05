package org.cboard.services;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.cboard.dao.DaySalesB2BDao;
import org.cboard.dto.ViewDaySalesB2B;
import org.cboard.pojo.DaySalesB2B;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DaySalesB2BService {

	@Autowired
	private DaySalesB2BDao salesDao;
	
	public ServiceStatus save(DaySalesB2B sales) {		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("caustomer", sales.getCaustomer());
		map.put("date", sales.getDate());
		map.put("sku", sales.getSku());
		if(getViewDaySalesB2BList(map).size() == 0) {
			if(salesDao.save(sales) > 0)
				return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
		}else {
			return new ServiceStatus(ServiceStatus.Status.Fail, "保存失败，记录已存在");
		}
		
		
		return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
	}
	
	public ServiceStatus update(DaySalesB2B sales) {
		int result = salesDao.update(sales);
		if(result > 0)
			return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
			
		return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
	}
	
	public ServiceStatus delete(long id) {
		if(salesDao.delete(id) > 0) 
			return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
		
		return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
	}
	
	public List<ViewDaySalesB2B> getViewDaySalesB2BList(Map<String, Object> map){		
		List<ViewDaySalesB2B> salesList = salesDao.getViewDaySalesB2BList(map);
		return salesList;
	}
	
	public DaySalesB2B getDaySalesB2B(long id) {
		DaySalesB2B sales = salesDao.getDaySalesB2B(id);
		return sales;
	}
	
	public ViewDaySalesB2B getViewDaySalesB2B(long id) {
		ViewDaySalesB2B vsales = salesDao.getViewDaySalesB2B(id);
		return vsales;
	}
}