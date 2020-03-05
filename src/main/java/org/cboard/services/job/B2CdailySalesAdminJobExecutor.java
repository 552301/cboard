package org.cboard.services.job;

import java.io.InputStream;
import java.util.HashMap;
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
 * B2C每日销售报表-管理层 报表定时任务
 * 
 * @author lu.wang
 *
 */
public class B2CdailySalesAdminJobExecutor implements Job {

	private static final Logger LOG = LoggerFactory.getLogger(B2CdailySalesAdminJobExecutor.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		this.mainJob();
	}

	private void mainJob() {
		String reportName = "B2C每日销售目标-管理层";
		//业务名，主要用于日志输出
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
					SendEmail.sendMail(reportName, emailAddress, "EPO E-COMMERCE Daily Report", is, reportName + ".xls");
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
