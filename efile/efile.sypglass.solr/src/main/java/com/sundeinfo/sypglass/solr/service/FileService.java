package com.sundeinfo.sypglass.solr.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.sypglass.solr.dto.SolrAndMetadataDto;
import com.sundeinfo.sypglass.solr.mapper.*;
import com.sundeinfo.sypglass.solr.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("FileService")
public class FileService extends AbstractService<FileService,File> {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileAccessoryMapper fileAccessoryMapper;

    @Autowired
    private FileMetadataMapper fileMetadataMapper;

    @Autowired
    private MetadataMapper metadataMapper;

    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private SolrStructureMapper solrStructureMapper;

    public long countFile(){
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        return fileMapper.countByExample(example);
    }

    public List<File> getFileList(){
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        return fileMapper.selectByExample(example);
    }

    public File getFile(Long id){
        return fileMapper.selectByPrimaryKey(id);
    }

    public List<FileAccessory> getFileAccessory(Long fileId){
        FileAccessoryExample example = new FileAccessoryExample();
        FileAccessoryExample.Criteria criteria = example.createCriteria();
        criteria.andFileidEqualTo(fileId);
        return fileAccessoryMapper.selectByExample(example);
    }

    public List<FileMetadata> getFileMetadata(Long fileId){
        FileMetadataExample example = new FileMetadataExample();
        FileMetadataExample.Criteria criteria = example.createCriteria();
        criteria.andFileidEqualTo(fileId);
        return fileMetadataMapper.selectByExample(example);
    }

    public List<SolrAndMetadataDto> getFileMetadataAndSolr(Long fileId){
        FileMetadataExample example = new FileMetadataExample();
        FileMetadataExample.Criteria criteria = example.createCriteria();
        criteria.andFileidEqualTo(fileId);
        List<FileMetadata> list = fileMetadataMapper.selectByExample(example);
        List<SolrAndMetadataDto> solrAndMetadataDtos = new ArrayList<>();
        list.forEach(dto->{
            SolrAndMetadataDto solrAndMetadataDto = new SolrAndMetadataDto();
            Metadata metadata = metadataMapper.selectByPrimaryKey(dto.getMetadataid());
            SolrStructure solrStructure = solrStructureMapper.selectByPrimaryKey(metadata.getSolrid());
            ConfigExample exampleConfig = new ConfigExample();
            ConfigExample.Criteria criteria1Config = exampleConfig.createCriteria();
            criteria1Config.andValueEqualTo(String.valueOf(metadata.getDoctype()));
            criteria1Config.andNameEqualTo("doc_type");
            criteria1Config.andCodeEqualTo("docType");
            List<Config> configs = configMapper.selectByExample(exampleConfig);
            if(configs.size() > 0){
                SolrAndMetadataDto solrAndMetadataDto1 = new SolrAndMetadataDto();
                solrAndMetadataDto1.setMetadataValue(configs.get(0).getComment());
                solrAndMetadataDto1.setSolrName("docType");
                solrAndMetadataDtos.add(solrAndMetadataDto1);
            }
            solrAndMetadataDto.setMetadataValue(dto.getValue());
            solrAndMetadataDto.setSolrName(solrStructure.getCode());
            solrAndMetadataDtos.add(solrAndMetadataDto);
        });
        return solrAndMetadataDtos;
    }
}
