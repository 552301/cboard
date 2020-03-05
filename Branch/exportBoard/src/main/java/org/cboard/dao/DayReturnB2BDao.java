package org.cboard.dao;

import org.cboard.dto.ViewDayReturnB2B;
import org.cboard.pojo.DayReturnB2B;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DayReturnB2BDao {
    int save(DayReturnB2B drturn);

    int update(DayReturnB2B dreturn);

    DayReturnB2B getDayReturnB2B(long id);
    
    ViewDayReturnB2B getViewDayReturnB2B(long id);
    
    List<ViewDayReturnB2B> getViewDayReturnB2BList(Map<String, Object> map);
    
    int delete(long id);
    
    int getSkuCount(String sku);
}
