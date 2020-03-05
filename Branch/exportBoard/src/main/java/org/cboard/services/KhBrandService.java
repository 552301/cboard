package org.cboard.services;

import java.util.HashMap;
import java.util.Map;

import org.cboard.dao.KhBrandDao;
import org.cboard.pojo.KhBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class KhBrandService {
	@Autowired
	private KhBrandDao khBrandDao;
	
	public KhBrand getKhBrandByKhmc(String khmc) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("khmc", khmc);
		
		return khBrandDao.getKhBrandByKhmc(map);
	}
	
	public KhBrand getKhBrandByLylx(String lylx) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lylx", lylx);
		
		return khBrandDao.getKhBrandByLylx(map);
	}
}
