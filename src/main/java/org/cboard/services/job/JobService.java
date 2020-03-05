package org.cboard.services.job;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.cboard.dao.JobDao;
import org.cboard.dto.ViewDashboardJob;
import org.cboard.pojo.DashboardJob;
import org.cboard.services.MailService;
import org.cboard.services.OtherService;
import org.cboard.services.ServiceStatus;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by yfyuan on 2017/2/17.
 */
@Service
public class JobService implements InitializingBean {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private JobDao jobDao;

    @Value("${admin_user_id}")
    private String adminUserId;

    @Autowired
    private MailService mailService;
    
    @Autowired
    private OtherService otherService;

    private static Logger logger = LoggerFactory.getLogger(JobService.class);

    public void configScheduler() {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        try {
            scheduler.clear();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        List<DashboardJob> jobList = jobDao.getJobList(adminUserId);
        for (DashboardJob job : jobList) {
            try {
                long startTimeStamp = job.getStartDate().getTime();
                long endTimeStamp = job.getEndDate().getTime();
                if (endTimeStamp < System.currentTimeMillis()) {
                    // Skip expired job
                    continue;
                }
                JobDetail jobDetail = JobBuilder.newJob(getJobExecutor(job)).withIdentity(job.getId().toString()).build();
                CronTrigger trigger = TriggerBuilder.newTrigger()
                        .startAt(new Date().getTime() < startTimeStamp ? job.getStartDate() : new Date())
                        .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExp()).withMisfireHandlingInstructionDoNothing())
                        .endAt(job.getEndDate())
                        .build();
                jobDetail.getJobDataMap().put("job", job);
                scheduler.scheduleJob(jobDetail, trigger);
            } catch (SchedulerException e) {
                logger.error("{} Job id: {}", e.getMessage(), job.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Class<? extends Job> getJobExecutor(DashboardJob job) {
        switch (job.getJobType()) {
            case "mail":
                return MailJobExecutor.class;
            case "other":
            	try {
            		JSONObject config = JSONObject.parseObject(job.getConfig());
        			Class job1 = Class.forName(config.getString("class"));
        			System.out.println(job1.toString());
        			return job1;
        		} catch (ClassNotFoundException e) {
        			jobDao.updateStatus(job.getId(), ViewDashboardJob.STATUS_FAIL, ExceptionUtils.getStackTrace(e));
        		}
            	
        }
    	try {
			Class job1 = Class.forName(job.getConfig());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			jobDao.updateStatus(job.getId(), ViewDashboardJob.STATUS_FAIL, ExceptionUtils.getStackTrace(e));
		}
        return null;
    }

    protected void sendMail(DashboardJob job) {
        jobDao.updateLastExecTime(job.getId(), new Date());
        try {
            jobDao.updateStatus(job.getId(), ViewDashboardJob.STATUS_PROCESSING, "");
            mailService.sendDashboard(job);
            jobDao.updateStatus(job.getId(), ViewDashboardJob.STATUS_FINISH, "");
        } catch (Exception e) {
            jobDao.updateStatus(job.getId(), ViewDashboardJob.STATUS_FAIL, ExceptionUtils.getStackTrace(e));
        }
    }
    protected void doOther(DashboardJob job){
    	jobDao.updateLastExecTime(job.getId(), new Date());
    	Scheduler scheduler = schedulerFactoryBean.getScheduler();
    	 try {
             jobDao.updateStatus(job.getId(), ViewDashboardJob.STATUS_PROCESSING, "");
//             mailService.sendDashboard(job);
             JobKey jobKey = JobKey.jobKey(job.getId().toString());
             if(!scheduler.checkExists(jobKey))
            	 logger.error("手动执行定时任务出错>>任务无效或过期");
             scheduler.triggerJob(jobKey);
             otherService.doOther(job);
             jobDao.updateStatus(job.getId(), ViewDashboardJob.STATUS_FINISH, "");
         } catch (Exception e) {
             jobDao.updateStatus(job.getId(), ViewDashboardJob.STATUS_FAIL, ExceptionUtils.getStackTrace(e));
         }
    }

    public ServiceStatus save(String userId, String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        DashboardJob job = new DashboardJob();
        job.setUserId(userId);
        job.setName(jsonObject.getString("name"));
        job.setConfig(jsonObject.getString("config"));
        job.setCronExp(jsonObject.getString("cronExp"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            job.setStartDate(format.parse(jsonObject.getJSONObject("daterange").getString("startDate")));
            job.setEndDate(format.parse(jsonObject.getJSONObject("daterange").getString("endDate")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        job.setJobType(jsonObject.getString("jobType"));
        jobDao.save(job);
        configScheduler();
        return new ServiceStatus(ServiceStatus.Status.Success, "success");
    }

    public ServiceStatus update(String userId, String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        DashboardJob job = new DashboardJob();
        job.setId(jsonObject.getLong("id"));
        job.setName(jsonObject.getString("name"));
        job.setConfig(jsonObject.getString("config"));
        job.setCronExp(jsonObject.getString("cronExp"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            job.setStartDate(format.parse(jsonObject.getJSONObject("daterange").getString("startDate")));
            job.setEndDate(format.parse(jsonObject.getJSONObject("daterange").getString("endDate")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        job.setJobType(jsonObject.getString("jobType"));
        jobDao.update(job);
        configScheduler();
        return new ServiceStatus(ServiceStatus.Status.Success, "success");
    }

    public ServiceStatus delete(String userId, Long id) {
        jobDao.delete(id);
        configScheduler();
        return new ServiceStatus(ServiceStatus.Status.Success, "success");
    }

    public ServiceStatus exec(String userId, Long id) {
        DashboardJob job = jobDao.getJob(id);
        if(job.getJobType().equals("mail")){
        	 new Thread(() ->
             sendMail(job)
        			 ).start();
        }else{
        	new Thread(() ->
            doOther(job)
       			 ).start();
        }
       
        
        return new ServiceStatus(ServiceStatus.Status.Success, "success");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        configScheduler();
    }
}
