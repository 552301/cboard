package org.cboard.dao;

import org.cboard.pojo.MonthReturnRateB2B;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MonthReturnRateB2BDao {
    int save(MonthReturnRateB2B drturn);

    int update(MonthReturnRateB2B dreturn);

    MonthReturnRateB2B getMonthReturnRateB2B(long id);
    
    List<MonthReturnRateB2B> getMonthReturnRateB2BList(Map<String, Object> map);
    
    int delete(long id);
}
