package com.sundeinfo.sypglass.dto;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.apache.solr.client.solrj.response.FacetField;

import java.util.List;
import java.util.Map;

@Data
public class SearchResultPageDto {
    private PageInfo<SearchResultDto> pageList;

    private List<Map<String,List<Map<String,Object>>>> solrFacet;
}
