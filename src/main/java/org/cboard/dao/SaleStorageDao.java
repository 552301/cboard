package org.cboard.dao;

import java.util.List;
import java.util.Map;

import org.cboard.dto.ViewSaleStorage;
import org.cboard.pojo.SaleStorage;
import org.springframework.stereotype.Repository;

/**
 * 商品进销存基础数据
 *
 */
@Repository
public interface SaleStorageDao {
	int save(SaleStorage sales);

    int update(SaleStorage sales);

    SaleStorage getSaleStorage(long id);
    
    ViewSaleStorage getViewSaleStorage(long id);
    
    List<ViewSaleStorage> getViewSaleStorageList(Map<String, Object> map);
    
    int delete(long id);
    
    int getSkuCount(String sku);
    
   
}
