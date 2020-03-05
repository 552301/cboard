package org.cboard.dao;

import org.cboard.dto.ViewDaySalesB2B;
import org.cboard.pojo.DaySalesB2B;
import org.cboard.pojo.DaySalesTarget;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface DaySalesB2BDao {
    int save(DaySalesB2B sales);

    int update(DaySalesB2B sales);

    DaySalesB2B getDaySalesB2B(long id);
    
    ViewDaySalesB2B getViewDaySalesB2B(long id);
    
    List<ViewDaySalesB2B> getViewDaySalesB2BList(Map<String, Object> map);
    
    int delete(long id);
    
    int getSkuCount(String sku);
}
