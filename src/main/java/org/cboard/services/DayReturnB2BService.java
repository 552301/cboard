package org.cboard.services;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.cboard.dao.DayReturnB2BDao;
import org.cboard.dto.ViewDayReturnB2B;
import org.cboard.pojo.DayReturnB2B;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DayReturnB2BService {

	@Autowired
	private DayReturnB2BDao dayReturnB2BDao;
	
	public ServiceStatus save(DayReturnB2B dreturn) {
		if(dayReturnB2BDao.getSkuCount(dreturn.getSku()) == 0) {
			return new ServiceStatus(ServiceStatus.Status.Fail, "sku无效");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("caustomer", dreturn.getCaustomer());
		map.put("date", dreturn.getDate());
		map.put("sku", dreturn.getSku());
		if(getViewDayReturnB2BList(map).size() == 0) {
			if(dayReturnB2BDao.save(dreturn) > 0)
				return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
		}else {
			return new ServiceStatus(ServiceStatus.Status.Fail, "保存失败，记录已存在");
		}
		
		
		return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
	}
	
	public ServiceStatus update(DayReturnB2B dreturn) {
		int result = dayReturnB2BDao.update(dreturn);
		if(result > 0)
			return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
			
		return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
	}
	
	public ServiceStatus delete(long id) {
		if(dayReturnB2BDao.delete(id) > 0) 
			return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
		
		return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
	}
	
	public List<ViewDayReturnB2B> getViewDayReturnB2BList(Map<String, Object> map){		
		List<ViewDayReturnB2B> dreturnList = dayReturnB2BDao.getViewDayReturnB2BList(map);
		return dreturnList;
	}
	
	public DayReturnB2B getDayReturnB2B(long id) {
		DayReturnB2B dreturn = dayReturnB2BDao.getDayReturnB2B(id);
		return dreturn;
	}
	
	public ViewDayReturnB2B getViewDayReturnB2B(long id) {
		ViewDayReturnB2B vreturn = dayReturnB2BDao.getViewDayReturnB2B(id);
		return vreturn;
	}
}