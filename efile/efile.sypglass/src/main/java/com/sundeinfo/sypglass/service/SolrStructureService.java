package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.sypglass.dto.SolrStructureDto;
import com.sundeinfo.sypglass.mapper.SolrStructureMapper;
import com.sundeinfo.sypglass.model.SolrStructure;
import com.sundeinfo.sypglass.model.SolrStructureExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("SolrStructureService")
public class SolrStructureService  extends AbstractService<SolrStructureService,SolrStructure> {

    @Autowired
    private SolrStructureMapper solrStructureMapper;


    public List<SolrStructureDto> findAll(String name){
        SolrStructureExample example = new SolrStructureExample();
        SolrStructureExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andStatusEqualTo(1);

        SolrStructureExample.Criteria criteria2 = example.createCriteria();
        if(StringUtils.isNotBlank(name)){
            criteria2.andNameLike("%" + name + "%");
        }
        criteria2.andStatusEqualTo(9);
        example.or(criteria2);
        List<SolrStructure> list = solrStructureMapper.selectByExample(example);
        return this.listRecordToListDto(list);
    }

    public SolrStructureDto findById(Long id){
        return new SolrStructureDto();
    }

    public List<SolrStructureDto> findByNameAndDocType(String name,int docType){
        SolrStructureExample example = new SolrStructureExample();
        SolrStructureExample.Criteria criteria = example.createCriteria();
        criteria.andDoctypeEqualTo(String.valueOf(docType));
        if(StringUtils.isNotBlank(name)){
            criteria.andNameLike("%"+ name +"%");
        }
        criteria.andStatusEqualTo(1);
        List<SolrStructure> list = solrStructureMapper.selectByExample(example);
        return this.listRecordToListDto(list);
    }

    public int save(SolrStructureDto solrStructureDto){
        SolrStructure record = this.dtoToRecord(solrStructureDto);
        record.setStatus(1);
        return solrStructureMapper.insert(record);
    }

    public int update(SolrStructureDto solrStructureDto){
        SolrStructure record = this.dtoToRecord(solrStructureDto);
        SolrStructureExample example = new SolrStructureExample();
        SolrStructureExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(solrStructureDto.getId());
        return solrStructureMapper.updateByExampleSelective(record,example);
    }

    public int delete(SolrStructureDto solrStructureDto){
        solrStructureDto.setStatus(0);
        return this.update(solrStructureDto);
    }

    private SolrStructure dtoToRecord(SolrStructureDto dto){
        SolrStructure record = new SolrStructure();
        record.setId(dto.getId());
        record.setDatatype(dto.getDataType());
        record.setDoctype(dto.getDocType());
        record.setCode(dto.getCode());
        record.setName(dto.getName());
        record.setMultivalued(dto.getMultivalued());
        record.setIndexed(dto.getIndexed());
        record.setNotnull(dto.getNotnull());
        record.setIskey(dto.getIsKey());
        record.setClustering(dto.getClustering());
        record.setComment(dto.getComment());
        record.setStatus(dto.getStatus());
        return record;
    }

    private SolrStructureDto recordToDto(SolrStructure record){
        SolrStructureDto dto = new SolrStructureDto();
        dto.setId(record.getId());
        dto.setDataType(record.getDatatype());
        dto.setDocType(record.getDoctype());
        dto.setCode(record.getCode());
        dto.setName(record.getName());
        dto.setMultivalued(record.getMultivalued());
        dto.setIndexed(record.getIndexed());
        dto.setNotnull(record.getNotnull());
        dto.setIsKey(record.getIskey());
        dto.setClustering(record.getClustering());
        dto.setComment(record.getComment());
        dto.setStatus(record.getStatus());
        return dto;
    }

    private List<SolrStructureDto> listRecordToListDto(List<SolrStructure> list){
        List<SolrStructureDto> listDto = new ArrayList<>();
        list.forEach(data ->{
            listDto.add(this.recordToDto(data));
        });
        return listDto;
    }
}
