package org.cboard.services;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.cboard.dao.DayReturnB2BDao;
import org.cboard.dao.MonthExchangeRateDao;
import org.cboard.pojo.MonthExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MonthExchangeRateService {

	@Autowired
	private MonthExchangeRateDao monthExchangeRateDao;
	
	public ServiceStatus save(MonthExchangeRate dreturn) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("caustomer", dreturn.getCaustomer());
		map.put("month", dreturn.getMonth());
		map.put("rate", dreturn.getRate());
		if(monthExchangeRateDao.getMonthExchangeRateList(map).size() == 0) {
			if(monthExchangeRateDao.save(dreturn) > 0)
				return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
		}else {
			return new ServiceStatus(ServiceStatus.Status.Fail, "保存失败，记录已存在");
		}
		
		
		return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
	}
	
	public ServiceStatus update(MonthExchangeRate dreturn) {
		int result = monthExchangeRateDao.update(dreturn);
		if(result > 0)
			return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
			
		return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
	}
	
	public ServiceStatus delete(long id) {
		if(monthExchangeRateDao.delete(id) > 0) 
			return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
		
		return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
	}
	
	public List<MonthExchangeRate> getMonthExchangeRateList(Map<String, Object> map){		
		List<MonthExchangeRate> dreturnList = monthExchangeRateDao.getMonthExchangeRateList(map);
		return dreturnList;
	}
	
	public MonthExchangeRate getMonthExchangeRate(long id) {
		MonthExchangeRate dreturn = monthExchangeRateDao.getMonthExchangeRate(id);
		return dreturn;
	}
}