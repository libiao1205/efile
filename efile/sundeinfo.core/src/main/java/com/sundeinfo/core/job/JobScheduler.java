package com.sundeinfo.core.job;

import com.sundeinfo.core.mvc.component.AbstractComponent;
import org.quartz.*;

public abstract class JobScheduler<T> extends AbstractComponent<T> {

    protected abstract Scheduler getScheduler();

    private AbstractTaskStarter getClass(String classname) throws Exception {
        Class<?> clazz = Class.forName(classname);
        return (AbstractTaskStarter)clazz.newInstance();
    }

    public Trigger.TriggerState confirmJobStatus(String group, String clazz) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(clazz, group);
        return getScheduler().getTriggerState(triggerKey);
    }

    public CronTrigger findJob(String group, String clazz) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(clazz, group);
        return (CronTrigger)getScheduler().getTrigger(triggerKey);
    }

    public void addJob(String group, String clazz, String cronExpression){
        try {
            // 启动调度器
            getScheduler().start();
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(clazz).getClass()).withIdentity(clazz, group).build();
            //表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(clazz, group).withSchedule(scheduleBuilder).build();
            getScheduler().scheduleJob(jobDetail, trigger);
            logger.info("已创建定时任务：class--" + clazz + "  cron--" + cronExpression);
        } catch (Exception e) {
            logger.error("创建定时任务失败" + e );
            e.printStackTrace();
            throw new RuntimeException("创建定时任务失败");
        }
    }


    public void reScheduleJob(String group, String clazz, String cronExpression)
    {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(clazz, group);
            CronTrigger trigger = (CronTrigger) getScheduler().getTrigger(triggerKey);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            // 按新的trigger重新设置job执行
            getScheduler().rescheduleJob(triggerKey, trigger);
            logger.info("已重置定时任务：class--" + clazz + "  cron--" + cronExpression);
        } catch (Exception e) {
            logger.error("更新定时任务失败" + e );
            e.printStackTrace();
            throw new RuntimeException("更新定时任务失败");
        }
    }

    public void deleteJob(String group,String clazz)
    {
        try {
            getScheduler().pauseTrigger(TriggerKey.triggerKey(clazz, group));
            getScheduler().unscheduleJob(TriggerKey.triggerKey(clazz, group));
            getScheduler().deleteJob(JobKey.jobKey(clazz, group));
            logger.info("已删除定时任务：class--" + clazz);
        }catch (Exception e) {
            logger.error("删除定时任务失败" + e );
            e.printStackTrace();
            throw new RuntimeException("删除定时任务失败");
        }
    }

    public void pauseJob(String group,String clazz)
    {
        try {
            getScheduler().pauseJob(JobKey.jobKey(clazz, group));
            logger.info("已暂停定时任务：class--" + clazz);
        }catch (Exception e) {
            logger.error("暂停定时任务失败" + e );
            e.printStackTrace();
            throw new RuntimeException("暂停定时任务失败");
        }
    }

    public void resumeJob(String group,String clazz){
        try {
            getScheduler().resumeJob(JobKey.jobKey(clazz, group));
            logger.info("已恢复定时任务：class--" + clazz);
        }catch (Exception e) {
            logger.error("恢复定时任务失败" + e );
            e.printStackTrace();
            throw new RuntimeException("恢复定时任务失败");
        }
    }
}
