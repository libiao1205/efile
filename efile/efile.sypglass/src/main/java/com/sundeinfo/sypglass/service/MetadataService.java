package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.sypglass.dto.MetadataDto;
import com.sundeinfo.sypglass.mapper.MetadataMapper;
import com.sundeinfo.sypglass.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("MetadataService")
public class MetadataService extends AbstractService<MetadataService,Metadata> {

    @Autowired
    private SolrStructureService solrStructureService;

    @Autowired
    private MetadataMapper metadataMapper;

    /**
     *
     * @return
     */
    public List<MetadataDto> findAll(String name){
        MetadataExample example = new MetadataExample();
        MetadataExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andStatusEqualTo(1);

        MetadataExample.Criteria criteria2 = example.createCriteria();
        if(StringUtils.isNotBlank(name)){
            criteria2.andNameLike("%" + name + "%");
        }
        criteria2.andStatusEqualTo(9);
        example.or(criteria2);
        List<Metadata> list = metadataMapper.selectByExample(example);
        return this.listRecordToListDto(list);
    }

    public MetadataDto findById(Long id){
        Metadata metadata = metadataMapper.selectByPrimaryKey(id);
        MetadataDto metadataDto = this.recordToDto(metadata);
        return metadataDto;
    }

    public List<MetadataDto> findByDocType(int type){
        MetadataExample example = new MetadataExample();
        MetadataExample.Criteria criteria = example.createCriteria();
        criteria.andDoctypeEqualTo(type);
        criteria.andStatusEqualTo(1);
        List<Metadata> list = metadataMapper.selectByExample(example);
        return this.listRecordToListDto(list);
    }

    public int save(MetadataDto metadata){
        Metadata record = this.dtoToRecord(metadata);
        record.setStatus(1);
        return metadataMapper.insert(record);
    }

    public int update(MetadataDto metadata){
        Metadata record = this.dtoToRecord(metadata);
        MetadataExample example = new MetadataExample();
        MetadataExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(metadata.getId());
        return metadataMapper.updateByExampleSelective(record,example);
    }

    public int delete(MetadataDto metadata){
        metadata.setStatus(0);
        return this.update(metadata);
    }

    private Metadata dtoToRecord(MetadataDto metadataDto){
        Metadata record = new Metadata();
        record.setId(metadataDto.getId());
        record.setDatatype(metadataDto.getDataType());
        record.setDoctype(metadataDto.getDocType());
        record.setCode(metadataDto.getCode());
        record.setName(metadataDto.getName());
        record.setSeq(metadataDto.getSeq());
        record.setSize(metadataDto.getSize());
        record.setNotnull(metadataDto.getNotnull());
        record.setSolrid(metadataDto.getSolrId());
        record.setSolrname(metadataDto.getSolrName());
        record.setComment(metadataDto.getComment());
        record.setStatus(metadataDto.getStatus());
        record.setSolrname(metadataDto.getSolrName());
        return record;
    }
    private MetadataDto recordToDto(Metadata record){
        MetadataDto metadataDto = new MetadataDto();
        metadataDto.setId(record.getId());
        metadataDto.setDataType(record.getDatatype());
        metadataDto.setDocType(record.getDoctype());
        metadataDto.setCode(record.getCode());
        metadataDto.setName(record.getName());
        metadataDto.setSeq(record.getSeq());
        metadataDto.setSize(record.getSize());
        metadataDto.setNotnull(record.getNotnull());
        metadataDto.setSolrId(record.getSolrid());
        metadataDto.setSolrName(record.getSolrname());
        metadataDto.setComment(record.getComment());
        metadataDto.setStatus(record.getStatus());
        metadataDto.setSolrName(record.getSolrname());
        return metadataDto;
    }
    private List<MetadataDto> listRecordToListDto(List<Metadata> list){
        List<MetadataDto> listDto = new ArrayList<>();
        list.forEach(data ->{
            listDto.add(this.recordToDto(data));
        });
        return listDto;
    }

}
