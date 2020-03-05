package org.cboard.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportDataDao {

	List<LinkedHashMap<String, ?>> getDailyReport(Map<String, ?> map);

	List<Map<String, Object>> getReportJsonData(Map<String, Object> map);
	
	List<Map<String, Object>> getReportJsonDataByDetail(Map<String, Object> map);

	List<Map<String, ?>> queryEmailData(Map<String, ?> map);

	int insertReportJsonData(Map<String, ?> map);
	
	int deleteReportJsonData(Map<String, ?> map);

	int updateReportJsonData(Map<String, ?> map);
}
