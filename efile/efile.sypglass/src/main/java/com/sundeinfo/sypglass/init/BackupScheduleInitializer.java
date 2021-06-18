package com.sundeinfo.sypglass.init;

import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.sypglass.component.BackupSchedulerAdapter;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.model.QuartzSetting;
import org.quartz.CronTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BackupScheduleInitializer extends AbstractComponent<BackupScheduleInitializer> implements ApplicationRunner {

    @Autowired
    private BackupSchedulerAdapter backupSchedulerAdapter;

    public void initialize() throws Exception {
        logger.info("--------------开始初始化定时备份服务配置--------------");
        logger.info("读取定时任务配置信息");
        List<QuartzSetting> taskCronExpressions = backupSchedulerAdapter.findBackupJobSettings();
        for (QuartzSetting setting:taskCronExpressions) {
            Trigger.TriggerState state = backupSchedulerAdapter.confirmJobStatus(setting.getGroupname(),setting.getClassname());
            switch (state){
                case NONE://不存在
                    backupSchedulerAdapter.addJob(setting.getGroupname(),setting.getClassname(),setting.getCron());
                    logger.info("发现并创建了新的定时任务：class--" + setting.getClassname() + " cron--" + setting.getCron());
                    if (!SypGlass.STATUS_RUNNING.equals(setting.getStatus())){
                        logger.info("暂停定时任务任务：class--" + setting.getClassname());
                        backupSchedulerAdapter.pauseJob(setting.getGroupname(),setting.getClassname());
                    }
                    break;
                case NORMAL://正常
                    CronTrigger trigger = backupSchedulerAdapter.findJob(setting.getGroupname(),setting.getClassname());
                    if (StringUtils.isNotBlank(setting.getCron()) && !trigger.getCronExpression().equalsIgnoreCase(setting.getCron())) {
                        backupSchedulerAdapter.reScheduleJob(setting.getGroupname(), setting.getClassname(), setting.getCron());
                        logger.info("发现到新的定时任务配置，并更新配置完毕：class--" + setting.getClassname() + " cron--" + setting.getCron());
                    }else{
                        if (!SypGlass.STATUS_RUNNING.equals(setting.getStatus())){
                            logger.info("暂停定时任务任务：class--" + setting.getClassname());
                            backupSchedulerAdapter.pauseJob(setting.getGroupname(),setting.getClassname());
                        }else {
                            logger.info("定时任务配置没有变化");
                        }
                    }
                    break;
                case PAUSED://暂停
                    if (SypGlass.STATUS_RUNNING.equals(setting.getStatus())){
                        logger.info("恢复定时任务任务：class--" + setting.getClassname());
                        backupSchedulerAdapter.resumeJob(setting.getGroupname(),setting.getClassname());
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
        logger.info("--------------定时备份服务配置初始化完成--------------");
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        this.initialize();
    }
}
