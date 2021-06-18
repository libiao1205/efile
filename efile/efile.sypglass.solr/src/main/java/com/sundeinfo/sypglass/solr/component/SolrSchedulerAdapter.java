package com.sundeinfo.sypglass.solr.component;

import com.sundeinfo.core.job.JobScheduler;

import com.sundeinfo.sypglass.solr.mapper.QuartzSettingMapper;
import com.sundeinfo.sypglass.solr.model.QuartzSetting;
import com.sundeinfo.sypglass.solr.model.QuartzSettingExample;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("SolrSchedulerAdapter")
public class SolrSchedulerAdapter extends JobScheduler<SolrSchedulerAdapter> {

    private static final String SOLR_GROUP = "solrgroup";

    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    @Autowired
    private QuartzSettingMapper quartzSettingMapper;;

    @Override
    protected Scheduler getScheduler() {
        return scheduler;
    }

    public List<QuartzSetting> findSolrJobSettings(){
        QuartzSettingExample example = new QuartzSettingExample();
        QuartzSettingExample.Criteria criteria = example.createCriteria();
        criteria.andGroupnameEqualTo(SOLR_GROUP);
        return quartzSettingMapper.selectByExample(example);
    }

    public int reScheduleJob(long id,String cron){
        QuartzSettingExample example = new QuartzSettingExample();
        QuartzSettingExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);

        QuartzSetting setting = quartzSettingMapper.selectByPrimaryKey(id);
        setting.setCron(cron);

        //修改定时任务执行时间
        reScheduleJob(setting.getGroupname(),setting.getClassname(),cron);
        //修改定时任务执行时间
        return quartzSettingMapper.updateByExampleSelective(setting,example);
    }

    public int pauseJob(long id){
        QuartzSettingExample example = new QuartzSettingExample();
        QuartzSettingExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);

        QuartzSetting setting = quartzSettingMapper.selectByPrimaryKey(id);
        setting.setStatus((byte)9);

        //暂停定时任务
        pauseJob(setting.getGroupname(),setting.getClassname());
        //设定暂停标识
        return quartzSettingMapper.updateByExampleSelective(setting,example);
    }

    public int resumeJob(long id){
        QuartzSettingExample example = new QuartzSettingExample();
        QuartzSettingExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);

        QuartzSetting setting = quartzSettingMapper.selectByPrimaryKey(id);
        setting.setStatus((byte)1);

        //恢复定时任务
        resumeJob(setting.getGroupname(),setting.getClassname());
        //设定恢复标识
        return quartzSettingMapper.updateByExampleSelective(setting,example);
    }
}
