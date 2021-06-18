package com.sundeinfo.sypglass.relay.service;

import com.sundeinfo.core.job.JobScheduler;
import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.job.AbstractTaskStarter;
import com.sundeinfo.sypglass.relay.mapper.relay.ProcessSettingMapper;
import com.sundeinfo.sypglass.relay.model.relay.ProcessSetting;
import com.sundeinfo.sypglass.relay.model.relay.ProcessSettingExample;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("JobService")
public class JobService extends JobScheduler<JobService> {

    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    @Autowired
    private ProcessSettingMapper processSettingMapper;

    @Override
    protected Scheduler getScheduler() {
        return scheduler;
    }

    public List<ProcessSetting> findAllJobSettings(){
        ProcessSettingExample example = new ProcessSettingExample();
        ProcessSettingExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        return processSettingMapper.selectByExample(example);
    }

    public int reScheduleJob(long id,String cron){
        ProcessSettingExample example = new ProcessSettingExample();
        ProcessSettingExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);

        ProcessSetting setting = processSettingMapper.selectByPrimaryKey(id);
        setting.setCron(cron);

        //修改定时任务执行时间
        reScheduleJob(setting.getGroupname(),setting.getClassname(),cron);
        //修改定时任务执行时间
        return processSettingMapper.updateByExampleSelective(setting,example);
    }

    public void deleteJob(String group,String clazz)
    {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(clazz, group));
            scheduler.unscheduleJob(TriggerKey.triggerKey(clazz, group));
            scheduler.deleteJob(JobKey.jobKey(clazz, group));
            logger.info("已删除定时任务：class--" + clazz);
        }catch (Exception e) {
            logger.error("删除定时任务失败" + e );
            e.printStackTrace();
            throw new RuntimeException("删除定时任务失败");
        }
    }

    public int pauseJob(long id){
        ProcessSettingExample example = new ProcessSettingExample();
        ProcessSettingExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);

        ProcessSetting setting = processSettingMapper.selectByPrimaryKey(id);
        setting.setStatus((byte)9);

        //暂停定时任务
        pauseJob(setting.getGroupname(),setting.getClassname());
        //设定暂停标识
        return processSettingMapper.updateByExampleSelective(setting,example);
    }

    public int resumeJob(long id){
        ProcessSettingExample example = new ProcessSettingExample();
        ProcessSettingExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);

        ProcessSetting setting = processSettingMapper.selectByPrimaryKey(id);
        setting.setStatus((byte)1);

        //恢复定时任务
        resumeJob(setting.getGroupname(),setting.getClassname());
        //设定恢复标识
        return processSettingMapper.updateByExampleSelective(setting,example);
    }

}
