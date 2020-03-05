package org.cboard.services.job;

import org.cboard.services.ReportDataService;
import org.cboard.services.job.task.GetDailySalesData;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

/**
 * （停用）
 * @author EBD403
 *
 */
public class DailySalesToExcelExeutor implements Job {
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//		try {
//			ReportDataService rds = ((ApplicationContext) jobExecutionContext.getScheduler().getContext()
//					.get("applicationContext")).getBean(ReportDataService.class);
////			GetDailySalesData.exportAllExcel(rds);
////			GetDailySalesData.exportMocoExcel(rds);
////			GetDailySalesData.exportEdExcel(rds);
////			GetDailySalesData.exportLmExcel(rds);
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
	}
}
