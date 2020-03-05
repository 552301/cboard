package org.cboard.services;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.cboard.dao.DayReturnB2BDao;
import org.cboard.dao.MonthReturnRateB2BDao;
import org.cboard.pojo.MonthReturnRateB2B;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MonthReturnRateB2BService {

	@Autowired
	private MonthReturnRateB2BDao monthReturnRateB2BDao;
	
	public ServiceStatus save(MonthReturnRateB2B dreturn) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("caustomer", dreturn.getCaustomer());
		map.put("month", dreturn.getMonth());
		map.put("rate", dreturn.getRate());
		if(monthReturnRateB2BDao.getMonthReturnRateB2BList(map).size() == 0) {
			if(monthReturnRateB2BDao.save(dreturn) > 0)
				return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
		}else {
			return new ServiceStatus(ServiceStatus.Status.Fail, "保存失败，记录已存在");
		}
		
		
		return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
	}
	
	public ServiceStatus update(MonthReturnRateB2B dreturn) {
		int result = monthReturnRateB2BDao.update(dreturn);
		if(result > 0)
			return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
			
		return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
	}
	
	public ServiceStatus delete(long id) {
		if(monthReturnRateB2BDao.delete(id) > 0) 
			return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
		
		return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
	}
	
	public List<MonthReturnRateB2B> getMonthReturnRateB2BList(Map<String, Object> map){		
		List<MonthReturnRateB2B> dreturnList = monthReturnRateB2BDao.getMonthReturnRateB2BList(map);
		return dreturnList;
	}
	
	public MonthReturnRateB2B getMonthReturnRateB2B(long id) {
		MonthReturnRateB2B dreturn = monthReturnRateB2BDao.getMonthReturnRateB2B(id);
		return dreturn;
	}
}