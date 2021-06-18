package com.sundeinfo.sypglass.apis.document;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.sundeinfo.core.log.ActionLog;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.component.PermissionAdapter;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.*;
import com.sundeinfo.sypglass.log.action.SearchLog;
import com.sundeinfo.sypglass.service.FolderService;
import com.sundeinfo.sypglass.service.SearchService;
import com.sundeinfo.sypglass.service.SolrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Api(description = "检索系统API")
@RestController
@RequestMapping("search")
public class SearchController extends com.sundeinfo.core.mvc.controller.AbstractController<SearchController> {

    @Value("${solr.enabled}")
    private String SOLR_ENABLED;

    @Autowired
    private SearchService searchService;

    @Autowired
    private SolrService solrService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @Autowired
    private PermissionAdapter permissionAdapter;

    @ApiOperation(value="依据关键字查找文档", notes="依据关键字查找文档")
    @GetMapping(value = "key/{pagenum}-{pagesize}")
    @ActionLog(execClass = SearchLog.class)
    public ResponseEntity<Result<SearchResultPageDto>> search(@PathVariable int pagenum, @PathVariable int pagesize,@RequestParam String key,@RequestParam String paramStr){
        return requestHandler.doGet(
                new GetCallback<SearchResultPageDto>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public SearchResultPageDto invoke() throws Exception {
                        SearchResultList searchResultDtos;
                        if ("true".equals(SOLR_ENABLED)){
                            //String q = URLEncoder.encode(key,"UTF-8");
                            searchResultDtos  = solrService.select(key,paramStr,pagenum ,pagesize);
                        }else{
                            JiebaSegmenter segmenter = new JiebaSegmenter();
                            List<SegToken> segTokens = segmenter.process(key, JiebaSegmenter.SegMode.INDEX);
                            logger.info("分词后检索关键词：" + segTokens.toString());
                            searchResultDtos = searchService.searchWithJieba(key,segTokens,authenticationGetter.getAnyUser().getUser().getId(),pagenum, pagesize);
                        }
                        System.out.println(searchResultDtos.getSolrFacet().size());
                        for (SearchResultDto result:searchResultDtos) {
                            List<FolderDto> parentFolderDtos = folderService.findParentListByFolderId(result.getParentId());
                            if (parentFolderDtos.get(0).getId() == SypGlass.ROOT_FOLDER_ID){
                                Collections.reverse(parentFolderDtos);
                            }
                            permissionAdapter.setDocumentPermissionMap(result,parentFolderDtos);
                        }
                        System.out.println(searchResultDtos.getSolrFacet().size());
                        PageInfo pageInfo = new PageInfo<>(searchResultDtos);
                        pageInfo.setTotal(searchResultDtos.getTotal());
                        SearchResultPageDto searchResultPageDto = new SearchResultPageDto();
                        searchResultPageDto.setPageList(pageInfo);
                        searchResultPageDto.setSolrFacet(searchResultDtos.getSolrFacet());
                        return searchResultPageDto;
                    }
                }
        );
    }
    @ApiOperation(value="查找用户最近10条检索关键字", notes="查找用户最近10条检索关键字")
    @GetMapping(value = "keys")
    public ResponseEntity<Result<List<SearchHistory>>> getKeys(){
        return requestHandler.doGet(
                new GetCallback<List<SearchHistory>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<SearchHistory> invoke() throws Exception {
                        return searchService.findByDate(authenticationGetter.getAnyUser().getUser().getId());
                    }
                }
        );
    }



}
