package org.cboard.services.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoClickToReportJsonJobExecutor implements Job {

	private static final Logger LOG = LoggerFactory.getLogger(AutoClickToReportJsonJobExecutor.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		this.mainJob();
	}

	private void mainJob() {
		String sn = "自动点击获取报表Json数据定时任务>>";
		// 可执行文件完整路径
		String exeFullPath = "D:\\CBoardAutoClicker\\CBoardAutoClicker.exe ";
		

//		// B2C每日销售报表-管理层url
//		String rp1Url = "http://localhost:8080/cboard/starter.html#/mine/30 admin root123";
//
//		// EPO E-COMMERCE Daily Achievement url
//		String rp2Url = "http://localhost:8080/cboard/starter.html#/mine/37 admin root123";
//
//		// EPO E-COMMERCE Daily Achievement(B2C) url
//		String rp3Url = "http://localhost:8080/cboard/starter.html#/mine/34 admin root123";

		List<String> param = new ArrayList<>();
		param.add("autorun");
//		param.add(rp2Url);
//		param.add(rp3Url);
		// 字符串命令
		StringBuffer command = new StringBuffer();
		for (int i = 0; i < param.size(); i++) {
			command.append(exeFullPath).append(param.get(i));
			try {
				LOG.info(sn + "开始第" + (i + 1) + "次调用：" + command.toString());
				// 创建一个本机进程
				Process process = Runtime.getRuntime().exec(command.toString());
				process.waitFor();
				command.setLength(0);
				LOG.info(sn + "执行成功");
			} catch (IOException | InterruptedException e) {
				LOG.error(sn + "异常：" + e.getMessage());
				return;
			}
		}
		LOG.info(sn + "全部执行完成");
	}

}
