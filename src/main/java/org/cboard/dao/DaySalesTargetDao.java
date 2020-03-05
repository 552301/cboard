package org.cboard.dao;

import org.cboard.pojo.DaySalesTarget;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface DaySalesTargetDao {
    int save(DaySalesTarget target);

    int update(DaySalesTarget target);

    DaySalesTarget getDaySalesTarget(long id);
    
    List<DaySalesTarget> getDaySalesTargetList(Map<String, Object> map);
    
    List<String> getHistoryDayTargetObjects(String dimension);
    
    int delete(long id);
}
