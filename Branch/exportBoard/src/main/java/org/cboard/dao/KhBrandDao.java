package org.cboard.dao;

import java.util.Map;

import org.cboard.pojo.KhBrand;
import org.springframework.stereotype.Repository;

@Repository
public interface KhBrandDao {
	KhBrand getKhBrandByKhmc(Map<String, Object> map);
	KhBrand getKhBrandByLylx(Map<String, Object> map);
}
