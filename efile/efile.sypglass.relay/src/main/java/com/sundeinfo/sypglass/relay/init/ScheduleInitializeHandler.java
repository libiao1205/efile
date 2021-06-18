package com.sundeinfo.sypglass.relay.init;

import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.sypglass.relay.define.RelayDefine;
import com.sundeinfo.sypglass.relay.model.relay.ProcessSetting;
import com.sundeinfo.sypglass.relay.service.JobService;
import com.sundeinfo.core.utility.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleInitializeHandler extends AbstractComponent<ScheduleInitializeHandler> {

    @Autowired
    private JobService jobService;

    public void execute() throws Exception {
        logger.info("读取定时任务配置信息");
        List<ProcessSetting> taskCronExpressions = jobService.findAllJobSettings();
        for (ProcessSetting processSetting:taskCronExpressions) {
            Trigger.TriggerState state = jobService.confirmJobStatus(processSetting.getGroupname(),processSetting.getClassname());
            switch (state){
                case NONE://不存在
                    jobService.addJob(processSetting.getGroupname(),processSetting.getClassname(),processSetting.getCron());
                    logger.info("发现并创建了新的定时任务：class--" + processSetting.getClassname() + " cron--" + processSetting.getCron());
                    if (!RelayDefine.STATUS_RUNNING.equals(processSetting.getStatus())){
                        logger.info("暂停定时任务任务：class--" + processSetting.getClassname());
                        jobService.pauseJob(processSetting.getGroupname(),processSetting.getClassname());
                    }
                    break;
                case NORMAL://正常
                    CronTrigger trigger = jobService.findJob(processSetting.getGroupname(),processSetting.getClassname());
                    if (StringUtils.isNotBlank(processSetting.getCron()) && !trigger.getCronExpression().equalsIgnoreCase(processSetting.getCron())) {
                        jobService.reScheduleJob(processSetting.getGroupname(), processSetting.getClassname(), processSetting.getCron());
                        logger.info("发现到新的定时任务配置，并更新配置完毕：class--" + processSetting.getClassname() + " cron--" + processSetting.getCron());
                    }else{
                        if (!RelayDefine.STATUS_RUNNING.equals(processSetting.getStatus())){
                            logger.info("暂停定时任务任务：class--" + processSetting.getClassname());
                            jobService.pauseJob(processSetting.getGroupname(),processSetting.getClassname());
                        }else {
                            logger.info("定时任务配置没有变化");
                        }
                    }
                    break;
                case PAUSED://暂停
                    if (RelayDefine.STATUS_RUNNING.equals(processSetting.getStatus())){
                        logger.info("恢复定时任务任务：class--" + processSetting.getClassname());
                        jobService.resumeJob(processSetting.getGroupname(),processSetting.getClassname());
                    }
                    break;
                case BLOCKED://阻塞
                case ERROR://执行异常
                    break;
                case COMPLETE://执行完成
                    break;
                default:
                    break;
            }

        }
    }
}