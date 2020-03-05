package org.cboard.services.job;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.cboard.services.job.task.ReportJobTask;
import org.cboard.util.SendEmail;
import org.cboard.util.SpringContextUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Online Daily Sales by Platform 报表定时任务
 * 
 * @author 
 *
 */
public class OnlineDailySalesbyPlatformJobExecutor implements Job {

	private static final Logger LOG = LoggerFactory.getLogger(OnlineDailySalesbyPlatformJobExecutor.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		this.mainJob();
	}

	private void mainJob() {
		String[] reportNames = {"MO&Co-Online Daily Sales by Platform", 
				"Edition-Online Daily Sales by Platform", 
				"little MO&Co-Online Daily Sales by Platform"};
		for(int idx = 0; idx < reportNames.length; idx++) {
			String reportName = reportNames[idx];
			// 业务名，主要用于日志输出
			String sn = reportName + ">报表定时任务>>";
			LOG.info(sn + "开始执行...");
			// 查询报表Json参数
			Map queryJsonParam = new HashMap<>();
			queryJsonParam.put("is_valid", 1);
			queryJsonParam.put("report_name", reportName);

			// 查询邮箱地址参数
			Map queryEmailParam = new HashMap<>();
			queryEmailParam.put("is_valid", 1);

			ReportJobTask rjt = SpringContextUtil.getBean(ReportJobTask.class);
			InputStream is = null;
			try {
				String emailAddress = rjt.getEmailAddress(reportName, queryEmailParam);
				if (emailAddress != null) {
					is = rjt.getExcelInputStream(sn, queryJsonParam);
					if(is != null) {  
						Locale l = new Locale("en");
				    	Date date = new Date();
				    	String month = String.format(l,"%tb", date);
//				    	String year = String.format(l,"%ty", headDate);
				    	String year =Integer.toString(1900 + date.getYear());
				    	System.out.println(reportName + " " + month + " " + year);
						SendEmail.sendMail(reportName + " " + month + " " + year, emailAddress, reportName + " " + month + " " + year, is, reportName + " " + month + " " + year + ".xls");
						LOG.info(sn + "邮件发送完成");
					}
				}
			} catch (Exception e) {
				LOG.error(sn);
				e.printStackTrace();
			} finally {
				rjt.closeInputStream(is);
			}
		}
		
	}
}
