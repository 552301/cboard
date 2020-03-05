package org.cboard.services;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.cboard.dao.SalesTargetDao;
import org.cboard.pojo.SalesTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SalesTargetService {

	@Autowired
	private SalesTargetDao targetDao;
	
	public ServiceStatus save(String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		SalesTarget target = new SalesTarget();
		target.setYear(jsonObject.getIntValue("year"));
		target.setMonth(jsonObject.getIntValue("month"));
		target.setDimension(jsonObject.getString("dimension"));
		target.setObject(jsonObject.getString("object"));
		target.setTarget_Payment(jsonObject.getFloatValue("target_payment"));
		target.setTarget_Num(jsonObject.getIntValue("target_num"));
		
		if(targetDao.save(target) > 0)
			return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
		
		return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
	}
	
	public ServiceStatus update(String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		SalesTarget target = new SalesTarget();
		target.setYear(jsonObject.getIntValue("year"));
		target.setMonth(jsonObject.getIntValue("month"));
		target.setDimension(jsonObject.getString("dimension"));
		target.setObject(jsonObject.getString("object"));
		target.setTarget_Payment(jsonObject.getFloatValue("target_payment"));
		target.setTarget_Num(jsonObject.getIntValue("target_num"));
		target.setId(jsonObject.getLong("id"));
		
		if(targetDao.update(target) > 0)
			return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
			
		return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
	}
	
	public ServiceStatus delete(long id) {
		if(targetDao.delete(id) > 0) 
			return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
		
		return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
	}
	
	public List<SalesTarget> getSalesTargetList(){
		List<SalesTarget> targetList = targetDao.getSalesTargetList();
		return targetList;
	}
	
	public List<SalesTarget> getSalesTargetListYear(int year){
		List<SalesTarget> targetList = targetDao.getSalesTargetListYear(year);
		return targetList;
	}
	
	
	public List<SalesTarget> getSalesTargetListYearDimension(int year, String dimension){
		List<SalesTarget> targetList = targetDao.getSalesTargetListYearDimension(year, dimension);
		return targetList;
	}
	
	public SalesTarget getSalesTarget(long id) {
		SalesTarget target = targetDao.getSalesTarget(id);
		return target;
	}
	
	public List<String> getHistorySalesObject(String dimension){
		List<String> targetObjects = targetDao.getHistoryTargetObjects(dimension);
		return targetObjects;
	}
}
