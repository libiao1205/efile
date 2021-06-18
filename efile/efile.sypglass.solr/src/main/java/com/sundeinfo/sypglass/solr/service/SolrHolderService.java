package com.sundeinfo.sypglass.solr.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.sypglass.solr.define.ReadHandle;
import com.sundeinfo.sypglass.solr.mapper.SolrHolderMapper;
import com.sundeinfo.sypglass.solr.model.SolrHolder;
import com.sundeinfo.sypglass.solr.model.SolrHolderExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("SolrHolderService")
public class SolrHolderService  extends AbstractService<SolrHolderService,SolrHolder> {


    @Autowired
    private SolrHolderMapper solrHolderMapper;


    public SolrHolder findByFile(long id){
        SolrHolderExample example = new SolrHolderExample();
        SolrHolderExample.Criteria criteria = example.createCriteria();
        criteria.andFileidEqualTo(id);

        List<SolrHolder> solrHolders = solrHolderMapper.selectByExample(example);
        if(solrHolders != null && solrHolders.size() > 0){
            return solrHolders.get(0);
        }else {
            return null;
        }
    }

    public SolrHolder findByFileAndLastUpdate(long id,Date lastUpdateDate){
        SolrHolderExample example = new SolrHolderExample();
        SolrHolderExample.Criteria criteria = example.createCriteria();
        criteria.andFileidEqualTo(id);
        criteria.andLastupdatedateGreaterThan(lastUpdateDate);
        List<SolrHolder> solrHolders = solrHolderMapper.selectByExample(example);
        if(solrHolders != null && solrHolders.size() > 0){
            return solrHolders.get(0);
        }else {
            return null;
        }
    }


    public int save(SolrHolder holder,long fileId,String name,String saveId, Date lastUpdate, ReadHandle handle,String path){
        if (holder == null){
            holder = new SolrHolder();
            holder.setFileid(fileId);
            holder.setName(name);
            holder.setSaveid(saveId);
            holder.setLastupdatedate(lastUpdate);
            holder.setHandle((byte)handle.code());
            holder.setPath(path);
            return solrHolderMapper.insert(holder);
        }else{
            holder.setFileid(fileId);
            holder.setName(name);
            holder.setSaveid(saveId);
            holder.setLastupdatedate(lastUpdate);
            holder.setHandle((byte)handle.code());
            holder.setPath(path);
            return solrHolderMapper.updateByPrimaryKey(holder);
        }
    }
}