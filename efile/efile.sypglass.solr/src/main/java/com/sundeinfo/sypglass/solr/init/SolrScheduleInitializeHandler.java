package com.sundeinfo.sypglass.solr.init;

import com.sundeinfo.core.mvc.component.AbstractComponent;

import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.sypglass.solr.define.SolrDefine;
import com.sundeinfo.sypglass.solr.model.QuartzSetting;
import com.sundeinfo.sypglass.solr.component.SolrSchedulerAdapter;
import org.quartz.CronTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SolrScheduleInitializeHandler extends AbstractComponent<SolrScheduleInitializeHandler> {

    @Autowired
    private SolrSchedulerAdapter solrSchedulerAdapter;

    public void execute() throws Exception {
        logger.info("读取定时任务配置信息");
        List<QuartzSetting> taskCronExpressions = solrSchedulerAdapter.findSolrJobSettings();
        for (QuartzSetting setting:taskCronExpressions) {
            Trigger.TriggerState state = solrSchedulerAdapter.confirmJobStatus(setting.getGroupname(),setting.getClassname());
            switch (state){
                case NONE://不存在
                    solrSchedulerAdapter.addJob(setting.getGroupname(),setting.getClassname(),setting.getCron());
                    logger.info("发现并创建了新的定时任务：class--" + setting.getClassname() + " cron--" + setting.getCron());
                    if (!SolrDefine.STATUS_RUNNING.equals(setting.getStatus())){
                        logger.info("暂停定时任务任务：class--" + setting.getClassname());
                        solrSchedulerAdapter.pauseJob(setting.getGroupname(),setting.getClassname());
                    }
                    break;
                case NORMAL://正常
                    CronTrigger trigger = solrSchedulerAdapter.findJob(setting.getGroupname(),setting.getClassname());
                    if (StringUtils.isNotBlank(setting.getCron()) && !trigger.getCronExpression().equalsIgnoreCase(setting.getCron())) {
                        solrSchedulerAdapter.reScheduleJob(setting.getGroupname(), setting.getClassname(), setting.getCron());
                        logger.info("发现到新的定时任务配置，并更新配置完毕：class--" + setting.getClassname() + " cron--" + setting.getCron());
                    }else{
                        if (!SolrDefine.STATUS_RUNNING.equals(setting.getStatus())){
                            logger.info("暂停定时任务任务：class--" + setting.getClassname());
                            solrSchedulerAdapter.pauseJob(setting.getGroupname(),setting.getClassname());
                        }else {
                            logger.info("定时任务配置没有变化");
                        }
                    }
                    break;
                case PAUSED://暂停
                    if (SolrDefine.STATUS_RUNNING.equals(setting.getStatus())){
                        logger.info("恢复定时任务任务：class--" + setting.getClassname());
                        solrSchedulerAdapter.resumeJob(setting.getGroupname(),setting.getClassname());
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