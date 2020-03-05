package org.cboard.dao;

import org.cboard.pojo.SalesTarget;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

public interface SalesTargetDao {
    int save(SalesTarget target);

    int update(SalesTarget target);

    SalesTarget getSalesTarget(long id);
    
    List<SalesTarget> getSalesTargetList();
    
    List<SalesTarget> getSalesTargetListYear(int year);

    List<SalesTarget> getSalesTargetListYearDimension(int year, String dimension);
    
    List<String> getHistoryTargetObjects(String dimension);
    
    int delete(long id);
}
