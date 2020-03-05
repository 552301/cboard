package org.cboard.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cboard.dao.SaleStorageDao;
import org.cboard.dto.ViewSaleStorage;
import org.cboard.pojo.DaySalesB2B;
import org.cboard.pojo.SaleStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SaleStorageService {

	@Autowired
	private SaleStorageDao saleStorageDao;

	// 保存数据
	public ServiceStatus save(SaleStorage sales) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sku", sales.getSku());
		map.put("date", sales.getDate());
		if (getViewSaleStorageList(map).size() == 0) {
			if (saleStorageDao.save(sales) > 0) { // 是否存在
				return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
			}
		} else {
			return new ServiceStatus(ServiceStatus.Status.Fail, "保存失败，记录已存在");
		}
		return new ServiceStatus(ServiceStatus.Status.Fail, "保存失败");
	}

	// 更新数据
	public ServiceStatus update(SaleStorage sales) {
		int result = saleStorageDao.update(sales);
		if (result > 0)
			return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");

		return new ServiceStatus(ServiceStatus.Status.Fail, "保存失败");
	}

	// 更新数据
	public ServiceStatus delete(long id) {
		int result = saleStorageDao.delete(id);
		if (result > 0)
			return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");

		return new ServiceStatus(ServiceStatus.Status.Fail, "保存失败");
	}

	public List<ViewSaleStorage> getViewSaleStorageList(Map<String, Object> map) {
		List<ViewSaleStorage> salesList = saleStorageDao
				.getViewSaleStorageList(map);
		return salesList;
	}

	public ViewSaleStorage getViewSaleStorage(Long id) {
		ViewSaleStorage vsales = saleStorageDao.getViewSaleStorage(id);
		return vsales;
	}

}
