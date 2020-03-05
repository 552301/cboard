package org.cboard.dao;

import org.cboard.pojo.MonthExchangeRate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MonthExchangeRateDao {
    int save(MonthExchangeRate drturn);

    int update(MonthExchangeRate dreturn);

    MonthExchangeRate getMonthExchangeRate(long id);

    
    List<MonthExchangeRate> getMonthExchangeRateList(Map<String, Object> map);
    
    int delete(long id);
}
