package com.sundeinfo.sypglass.service;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.sypglass.dto.SearchHistory;
import com.sundeinfo.sypglass.dto.SearchResultDto;
import com.sundeinfo.sypglass.dto.SearchResultList;
import com.sundeinfo.sypglass.mapper.DocumentMapper;
import com.sundeinfo.sypglass.mapper.UserSearchHistoryMapper;
import com.sundeinfo.sypglass.model.Document;
import com.sundeinfo.sypglass.model.UserSearchHistory;
import com.sundeinfo.sypglass.model.UserSearchHistoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("SearchService")
public class SearchService extends AbstractService<SearchService,SearchResultDto> {

    @Autowired
    private DocumentMapper documentMapper;

    @Autowired
    private UserSearchHistoryMapper searchHistoryMapper;

//    public List<SearchResultDto> search(String key,long userId) throws InstantiationException, IllegalAccessException {
//        List<Document> documents = documentMapper.selectDocumentLikeKey(key);
//        save(key,userId);
//        return ConvertUtils.convertForPage(documents,SearchResultDto.class);
//    }

    public SearchResultList searchWithJieba(String orgKey, List<SegToken> segTokens, long userId, int pagenum, int pagesize) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        List<String> words = ReflectUtils.reflectDataList(segTokens,"word",String.class);
        List<Document> documents = documentMapper.selectDocumentLikeKeyWithJieba(words,pagenum,pagesize);
        save(orgKey,userId);
        List<SearchResultDto> searchResultDtos = ConvertUtils.convertList(documents,SearchResultDto.class);
        SearchResultList searchResultList = new SearchResultList();
        searchResultList.addAll(searchResultDtos);
        return searchResultList;
    }

    public List<Document> searchDeletedWithJieba(String orgKey,List<SegToken> segTokens) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        List<String> words = ReflectUtils.reflectDataList(segTokens,"word",String.class);
        return documentMapper.selectDeletedDocumentLikeKeyWithJieba(words);
    }

    public List<SearchHistory> findByDate(long userId) throws InstantiationException, IllegalAccessException {
        UserSearchHistoryExample example = new UserSearchHistoryExample();
        example.setOrderByClause("serachdate DESC limit 0,10");

        List<UserSearchHistory> userSearchHistories = searchHistoryMapper.selectByExample(example);
        return ConvertUtils.convertList(userSearchHistories,SearchHistory.class);
    }

    /********************************以下数据库读写****************************************/

    public int save(String serachKey,long userId){
        UserSearchHistory history = new UserSearchHistory();
        history.setSearchkey(serachKey);
        history.setUserid(userId);
        history.setSerachdate(new Date());
        return searchHistoryMapper.insert(history);
    }

}
