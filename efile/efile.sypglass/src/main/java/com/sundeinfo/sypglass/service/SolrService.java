package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.sypglass.configuration.ConfigService;
import com.sundeinfo.sypglass.define.FileDocType;
import com.sundeinfo.sypglass.define.SypGlassConfig;
import com.sundeinfo.sypglass.dto.FileDto;
import com.sundeinfo.sypglass.dto.SearchResultDto;
import com.sundeinfo.sypglass.dto.SearchResultList;
import com.sundeinfo.sypglass.mapper.FileMapper;
import com.sundeinfo.sypglass.model.Config;
import com.sundeinfo.sypglass.model.File;
import com.sundeinfo.sypglass.model.FileExample;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service("SolrService")
public class SolrService extends AbstractService<FileService,Object> {

    @Value("${solr.core.name}")
    private String coreName;

    @Value("${solr.query.Template}")
    private String queryTemplate;

    @Autowired
    private SolrClient client;

    @Autowired
    private FileService fileService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private FileMapper fileMapper;

    public Map<String, Object> getAll() throws SolrServerException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        return map;
    }

    public String getDocumentById(String id) throws SolrServerException, IOException {
        SolrDocument document = client.getById(coreName, id);
        logger.info(document.toString());
        return document.toString();
    }

    public SearchResultList select(String q, String paramStr, Integer page, Integer size)
            throws SolrServerException, IOException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo((byte)9);
        List<File> files = fileMapper.selectByExample(example);
//        Config config = configService.findOneByNameAndCode(SypGlassConfig.SOLR_QUERY_TEMPLATE_NAME,SypGlassConfig.SOLR_QUERY_TEMPLATE_CODE);
        String query = queryTemplate.replace("value",escapeQueryChars(q));
//        if(config != null){
//            if(StringUtils.isNotBlank(config.getValue())){
//                System.out.println(config.getValue());
//                query = config.getValue().replace("value",escapeQueryChars(q));
//            }
//        }
        // 查询条件转义和替换处理
        //String query = queryTemplate.replace("value",q);

        SolrQuery params = new SolrQuery();
        if(files.size() > 0){
            files.forEach((file) ->{
                params.addFilterQuery("-id:"+file.getId());
            });
        }
        params.set("q", query);
        params.addField("id");
        params.addField("name");
        params.addField("suffix");
        params.addField("content");
        //聚类
        params.setFacet(true);
        params.addFacetField("docType","author","authorUnit","projectName","keyword");
        if(StringUtils.isNotBlank(paramStr)){
            String [] strArr =  paramStr.split("@");
            for(int i = 0; i < strArr.length; i++){
                if(StringUtils.isNotBlank(strArr[i])) {
                    params.addFilterQuery(strArr[i]);
                }
            }
        }
        // 排序
        //params.addSort("id", SolrQuery.ORDER.desc);
        // 分页
        params.setStart((page - 1) * size);
        params.setRows(size);

        // 开启高亮
        params.setHighlight(true);
        // 设置前缀
        params.setHighlightSimplePre("<span style='color:red'>");
        // 设置后缀
        params.setHighlightSimplePost("</span>");
        // 高亮字段
        params.setParam("hl.fl", "content");

        QueryResponse queryResponse = client.query(coreName, params);

        //获取检索结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        // 获取高亮显示的结果, 高亮显示的结果和查询结果是分开放的
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

        List<Long> ids = new ArrayList<>();
        for (SolrDocument solrDocument:solrDocumentList) {
            ids.add(Long.parseLong(solrDocument.getFieldValue("id").toString()));
        }

        //获取聚类结果和总条数
        SearchResultList searchResultDtos = this.selectCluster(params);
        if (ids.size() == 0){
            return searchResultDtos;
        }
        List<FileDto> fileDtos = fileService.findByIds(ids);
        SearchResultDto searchResultDto;
        StringBuilder builder;
        for (SolrDocument solrDocument:solrDocumentList) {
            FileDto fileDto = findById(fileDtos,Long.parseLong(solrDocument.getFieldValue("id").toString()));
            if (fileDto == null){
                continue;
            }

            searchResultDto = ConvertUtils.convert(fileDto,SearchResultDto.class);
            searchResultDto.setParentId(fileDto.getFolderId());

            if (searchResultDto.getDocType() == FileDocType.LITERATURE.getStatus()){
                //如果是文献类型就追加附件信息和元数据信息
                searchResultDto.setFileAccessoryDto(fileService.findFileAccessoryByFileId(fileDto.getId()));
                searchResultDto.setFileMetadataDto(fileService.findFileMetadataByFileId(fileDto.getId()));
            }

            List<String> contents = highlighting.get(String.valueOf(fileDto.getId())).get("content");
            builder = new StringBuilder();
            if (contents != null){
                for(String content:contents){
                    String clearedContent = content.replace("\n","")
                                                   .replace("/n","")
                                                   .replace("\r","")
                                                   .replace("  ","&nbsp;");
                    builder.append(clearedContent);
                }
            }
            searchResultDto.setHighLight(builder.toString());

            searchResultDtos.add(searchResultDto);
        }
        return searchResultDtos;
    }

    private SearchResultList selectCluster(SolrQuery params) throws IOException, SolrServerException {
        //获取聚类结果
        params.setStart(0);
        params.setRows(0);
        QueryResponse queryResponse = client.query(coreName, params);
        List<FacetField> facetFields = queryResponse.getFacetFields();
        List<Map<String,List<Map<String,Object>>>> facetFieldList = new ArrayList<>();
        facetFields.forEach(facetField -> {
            Map<String,List<Map<String,Object>>> faceMap = new HashMap<>();
            List<Map<String,Object>> valueList = new ArrayList<>();
            facetField.getValues().forEach(value ->{
                Map<String,Object> map = new HashMap<>();
                map.put(value.getName(),value.getCount());
                valueList.add(map);
            });
            faceMap.put(facetField.getName(),valueList);
            facetFieldList.add(faceMap);
        });
        SearchResultList searchResultDtos = new SearchResultList();
        searchResultDtos.setTotal(queryResponse.getResults().getNumFound());
        searchResultDtos.setSolrFacet(facetFieldList);
        return searchResultDtos;
    }

    private FileDto findById(List<FileDto> source,Long id){
        for (FileDto file:source) {
            if (id.equals(file.getId()) && file.getStatus() == 1){
                return file;
            }
        }
        return null;
    }

    private String escapeQueryChars(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // These characters are part of the query syntax and must be escaped
            if (c == '\\' || c == '+' || c == '-' || c == '!'  || c == '(' || c == ')' || c == ':'
                    || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~'
                    || c == '*' || c == '?' || c == '|' || c == '&'  || c == ';' || c == '/'
                    || Character.isWhitespace(c)) {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }

}
