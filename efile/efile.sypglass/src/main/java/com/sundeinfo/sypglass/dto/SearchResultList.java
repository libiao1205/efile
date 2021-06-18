package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.solr.client.solrj.response.FacetField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class SearchResultList extends ArrayList<SearchResultDto> {

    private long total;

    //聚类结果
    private List<Map<String,List<Map<String,Object>>>> solrFacet;

}
