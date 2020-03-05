package org.cboard.services;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.cboard.dao.DaySalesTargetDao;
import org.cboard.pojo.DaySalesTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DaySalesTargetService {

	@Autowired
	private DaySalesTargetDao targetDao;
	
	public ServiceStatus save(DaySalesTarget target) {		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("platform", target.getPlatform());
		map.put("brand", target.getBrand());
		map.put("store", target.getStore());
		map.put("date", target.getDate());
		if(getDaySalesTargetList(map).size() == 0) {
			if(targetDao.save(target) > 0)
				return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
		}else {
			return new ServiceStatus(ServiceStatus.Status.Fail, "保存失败，记录已存在");
		}
		
		
		return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
	}
	
	public ServiceStatus update(DaySalesTarget target) {
		int result = targetDao.update(target);
		if(result > 0)
			return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
			
		return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
	}
	
	public ServiceStatus delete(long id) {
		if(targetDao.delete(id) > 0) 
			return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
		
		return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
	}
	
	public List<DaySalesTarget> getDaySalesTargetList(Map<String, Object> map){		
		List<DaySalesTarget> targetList = targetDao.getDaySalesTargetList(map);
		return targetList;
	}
	
	public DaySalesTarget getDaySalesTarget(long id) {
		DaySalesTarget target = targetDao.getDaySalesTarget(id);
		return target;
	}
	
	public List<String> getHistorySalesObject(String dimension){
		List<String> targetObjects = targetDao.getHistoryDayTargetObjects(dimension);
		return targetObjects;
	}
}