package org.cboard.services;

import org.cboard.dao.ReportDataDao;
import org.cboard.util.CboardUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportDataService {

	@Autowired
	private ReportDataDao reportDataDao;

	private static final Logger LOG = LoggerFactory.getLogger(ReportDataService.class);

	public List<LinkedHashMap<String, ?>> getDailyReport(Map<String, ?> map) {
		List<LinkedHashMap<String, ?>> eportData = reportDataDao.getDailyReport(map);
		return eportData;
	}

	public List<Map<String, ?>> getReportJsonData(Map<String, ?> map) {
		List<Map<String, ?>> data = reportDataDao.getReportJsonData(map);
		return data;
	}

	public List<Map<String, ?>> queryEmailData(Map<String, ?> map) {
		List<Map<String, ?>> data = reportDataDao.queryEmailData(map);
		return data;
	}

	public void insertReportJsonData(String dataJson) throws Exception {
		LOG.info("开始处理自动点击获取的Json字符串...");
		if (CboardUtils.isEmpty(dataJson)) {
			LOG.error("Json字符串为空");
			return;
		}
		// Json字符串解析
		JSONObject data;
		try {
			data = JSONObject.parseObject(dataJson);
		} catch (Exception e) {
			LOG.error("Json字符串解析异常>>" + e.getMessage());
			return;
		}
		// 判断是否为空，例如解析后的结果：{}
		if (data.isEmpty()) {
			LOG.error("解析完成后的JSONObject为空>>" + data);
			return;
		}
		// 完整的报表导出名称，例xx报表-品牌-时间
		String tablename = data.containsKey("tablename") ? data.getString("tablename") : "";
		JSONArray paramArray = data.containsKey("paramArray") ? data.getJSONArray("paramArray") : new JSONArray();
		JSONArray timeArray = data.containsKey("timeArray") ? data.getJSONArray("timeArray") : new JSONArray();

		// 报表条件存储实例
		List criterion = new ArrayList<>();

		StringBuffer stbf = new StringBuffer();
		paramArray.forEach(str -> {
			criterion.add(str);
			stbf.append("-").append(str);
		});

		StringBuffer stbf1 = new StringBuffer();
		timeArray.forEach(str -> {
			criterion.add(str);
			stbf1.append("-").append(str);
		});
		// 将多个报表条件以逗号分隔
		StringBuffer stbf2 = new StringBuffer();
		criterion.forEach(str -> stbf2.append(str).append(","));

		// 报表名称+品牌
		int rnbIndex = tablename.indexOf(stbf1.toString());
		String rpNameBrand = tablename.substring(0, rnbIndex <= 0 ? tablename.length() : rnbIndex);

		// 报表名称
		int rnIndex = rpNameBrand.indexOf(stbf.toString());
		String rpName = rpNameBrand.substring(0, rnIndex <= 0 ? rpNameBrand.length() : rnIndex);

		// 要保存的对象
		Map map = new HashMap();
		map.put("report_json", dataJson);
		map.put("report_name", rpName);
		map.put("report_name_brand", rpNameBrand);
		map.put("report_criterion", stbf2.toString());
		map.put("is_valid", 1);

		if (CboardUtils.isNotEmpty(rpNameBrand)) {
			// 检测报表名称的记录，存在就删除
			Map param = new HashMap();
			param.put("report_name_brand", rpNameBrand);
			LOG.info("删除report_name_brand字段值为{" + rpNameBrand + "}的记录");
			reportDataDao.deleteReportJsonData(map);
		}
		reportDataDao.insertReportJsonData(map);
		LOG.info("自动点击获取的报表数据保存完成>>>>>");
	}

}