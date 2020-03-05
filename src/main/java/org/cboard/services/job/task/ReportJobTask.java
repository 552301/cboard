package org.cboard.services.job.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cboard.services.ReportDataService;
import org.cboard.services.persist.excel.ExcelDispose;
import org.cboard.util.CboardUtils;
import org.cboard.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 报表任务处理
 * 
 * @author lu.wang
 *
 */
@Component
public class ReportJobTask {

	private static final Logger LOG = LoggerFactory.getLogger(ReportJobTask.class);

	/**
	 * 获取Excel（HSSFWorkbook） 输入流
	 * 
	 * @param sn
	 *            业务名，主要用于日志输出
	 * @param queryJsonParam
	 *            查询数据库Json参数
	 * @return Excel（HSSFWorkbook）输入流
	 * 
	 */
	public InputStream getExcelInputStream(String sn, Map queryJsonParam) throws Exception {
		if (CboardUtils.isEmpty(sn))
			sn = "Excel报表邮件数据处理>>";

		// 查询Json参数
		if (CboardUtils.isEmpty(queryJsonParam)) {
			queryJsonParam = new HashMap<>();
			queryJsonParam.put("is_valid", 1);
		}
		// 获取业务类实例
		ReportDataService rds = SpringContextUtil.getBean(ReportDataService.class);

		// =======查询并处理Json数据==========
		List<Map<String, ?>> list = rds.getReportJsonData(queryJsonParam);
		List<String> listJson = new ArrayList<>();
		list.forEach(map -> map.forEach((k, v) -> {
			if (k.equals("report_json")) {
				if (v != null && v.toString().trim().length() > 0)
					listJson.add(v.toString().trim());
			}
		}));

		if (listJson.size() == 0) {
			LOG.error(sn + "未查询到Excel需要的Json数据");
			return null;
		}
		ExcelDispose excelDispose = SpringContextUtil.getBean(ExcelDispose.class);
		// 获取发送邮件需要的Excel输入流
		InputStream is = excelDispose.hssfWorkBookToInputStream(excelDispose.getfullHssfWorkBook(listJson));
		if (is == null) {
			LOG.error(sn + "需要的InputStream为null");
			return null;
		}
		return is;
	}

	/**
	 * 获取邮箱地址
	 * 
	 * @param sn
	 *            报表名
	 * @param queryEmailParam
	 *            查询数据库邮箱参数
	 * @return
	 */
	public String getEmailAddress(String sn, Map queryEmailParam) throws Exception {
		if (CboardUtils.isEmpty(sn))
			sn = "邮箱信息获取>>";

		if (CboardUtils.isEmpty(queryEmailParam)) {
			queryEmailParam = new HashMap<>();
			queryEmailParam.put("is_valid", 1);
		}
		ReportDataService rds = SpringContextUtil.getBean(ReportDataService.class);
		List<Map<String, ?>> listMail = rds.queryEmailData(queryEmailParam);
		if (CboardUtils.isEmpty(listMail))
			return null;
		StringBuffer sb = new StringBuffer();
		for (Map<String, ?> map : listMail) {
			Object obj = map.get("report_name");
			String reportName[] = obj != null && CboardUtils.isNotEmpty(obj.toString()) ? obj.toString().trim().split(",") : null;
			if (reportName != null) {
				boolean lean = false;
				for (int i = 0; i < reportName.length; i++) {
					lean = reportName[i].equals(sn);
					if (lean)
						break;
				}
				if (lean) {
					Object obj1 = map.get("email_address");
					String emAdd = obj1 != null && CboardUtils.isNotEmpty(obj1.toString()) ? obj1.toString().trim() : null;
					if (emAdd != null)
						sb.append(emAdd).append(",");
				}
			}
		}

		if (sb.length() == 0) {
			LOG.warn(sn + ">>根据配置查询需要发送的邮箱地址为空");
			return null;
		}
		return sb.toString();
	}

	/**
	 * 关闭输入流{@link InputStream}
	 * 
	 * @param is
	 */
	public void closeInputStream(InputStream is) {
		if (is == null)
			return;
		try {
			is.close();
		} catch (IOException e) {
			LOG.equals("关闭输入流InputStream异常>>" + e.getMessage());
		}
	}
	
	
	
}
