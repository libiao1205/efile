package com.sundeinfo.sypglass.solr.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.file.fulltext.FullTextReader;
import com.sundeinfo.sypglass.solr.define.ReadHandle;
import com.sundeinfo.sypglass.solr.dto.SolrAndMetadataDto;
import com.sundeinfo.sypglass.solr.model.File;
import com.sundeinfo.sypglass.solr.model.SolrHolder;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service("SolrService")
public class SolrService extends AbstractService<SolrService,Object> {

    @Value("${solr.core.name}")
    private String coreName;

    @Autowired
    private SolrHolderService solrHolderService;

    @Autowired
    private SolrClient client;

    public String getDocumentById(String id) throws SolrServerException, IOException {
        SolrDocument document = client.getById(coreName, id);
        logger.info(document.toString());
        return document.toString();
    }

    public int save(File file, SolrHolder holder, FullTextReader reader, List<SolrAndMetadataDto> solrAndMetadataDtos, Date start) throws IOException, SolrServerException {
        int result;
        String fullText = reader.read();
        if (StringUtils.isBlank(fullText)){
            if (holder != null){
                this.delete(file.getId().toString());
            }
            result = this.insert(file,"",solrAndMetadataDtos);
            if (result > 0){
                if (reader.isSupport()){
                    solrHolderService.save(holder,file.getId(),file.getName(),file.getSaveid(),start,ReadHandle.HANDLE_ERROR,file.getParentpathname());
                    logger.info("文件：" + file.getName() + "." + file.getSuffix() + "处理完毕,但是文档读取可能发生异常。");
                }else{
                    solrHolderService.save(holder,file.getId(),file.getName(),file.getSaveid(),start,ReadHandle.HANDLE_NO_PROCESS,file.getParentpathname());
                    logger.info("文件：" + file.getName() + "." + file.getSuffix() + "不是全文搜索对象");
                }
            }else{
                solrHolderService.save(holder,file.getId(),file.getName(),file.getSaveid(),start,ReadHandle.HANDLE_ERROR,file.getParentpathname());
                logger.info("文件：" + file.getName() + "." + file.getSuffix() + "处理完毕,但是文档读取可能发生异常。");
            }
        }else{
            if (holder != null){
                this.delete(file.getId().toString());
            }
            result = this.insert(file,fullText,solrAndMetadataDtos);
            if (result > 0){
                solrHolderService.save(holder,file.getId(),file.getName(),file.getSaveid(),start,ReadHandle.HANDLE_SUCCESS,file.getParentpathname());
                logger.info("文件：" + file.getName() + "." + file.getSuffix() + "处理完毕");
            }else{
                solrHolderService.save(holder,file.getId(),file.getName(),file.getSaveid(),start,ReadHandle.HANDLE_ERROR,file.getParentpathname());
                logger.info("文件：" + file.getName() + "." + file.getSuffix() + "处理完毕,但是文档读取可能发生异常。");
            }
        }
        return result;
    }

    public int insert(File file,String fullText,List<SolrAndMetadataDto> solrAndMetadataDtos) throws IOException, SolrServerException {
        try {
            SolrInputDocument doc = getDocument(file,fullText,solrAndMetadataDtos);
            client.add(coreName, doc);
            client.commit(coreName);
            return 1;
        } catch (Exception e) {
            logger.error("导入Solr出现异常：" + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

//    public int insert(String message) throws IOException, SolrServerException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
//        String dateString = sdf.format(new Date());
//        try {
//            for (int i = 0;i < 100; i++){
//                SolrInputDocument doc = new SolrInputDocument();
//                doc.setField("id", dateString + i);
//                doc.setField("createusername", "create By:" + i);
//                doc.setField("lastupdateusername", "update By:" + i);
//                doc.setField("deleteusername", "delete By:" + i);
//                doc.setField("parentpathname", "parent By:" + i);
//                doc.setField("name", "name:" + i);
//                doc.setField("suffix", "suffix:" + i);
//                doc.setField("comment", "comment:" + i);
//
//                doc.setField("content", message+UUID.randomUUID().toString());
//                client.add(SOLR_DB_NAME, doc);
//                client.commit(SOLR_DB_NAME);
//            }
//            return 1;
//        } catch (Exception e) {
//            logger.error("导入Solr出现异常：" + e.getMessage());
//            e.printStackTrace();
//            return 0;
//        }
//    }

    public int deleteAll() {
        try {
            client.deleteByQuery(coreName, "*:*");
            client.commit(coreName);
            return 1;
        } catch (Exception e) {
            logger.error("删除Solr出现异常：" + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(String id) {
        try {
            client.deleteById(coreName, id);
            client.commit(coreName);
            return 1;
        } catch (Exception e) {
            logger.error("删除Solr出现异常：" + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public int update(File file,String fullText) throws IOException, SolrServerException {
        try {
            SolrInputDocument doc = getDocument(file,fullText,null);
            client.add(coreName, doc);
            client.commit(coreName);
            return 1;
        } catch (Exception e) {
            logger.error("更新Solr出现异常：" + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }


    private SolrInputDocument getDocument(File file,String fullText,List<SolrAndMetadataDto> solrAndMetadataDtos){
        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id", file.getId());
        doc.setField("createusername", file.getCreateusername());
        doc.setField("lastupdateusername", file.getLastupdateusername());
        doc.setField("deleteusername", file.getDeleteusername());
        doc.setField("parentpathname",file.getParentpathname());
        doc.setField("name", file.getName());
        doc.setField("suffix", file.getSuffix());
        doc.setField("comment",file.getComment());
        doc.setField("content", fullText);
        if(solrAndMetadataDtos != null){
            solrAndMetadataDtos.forEach(dto->{
                doc.setField(dto.getSolrName(),dto.getMetadataValue());
            });
        }
        return doc;
    }
}
