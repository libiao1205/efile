package com.sundeinfo.sypglass.solr.model;

public class SolrStructure {
    private Long id;

    private String doctype;

    private String code;

    private String name;

    private Integer datatype;

    private Integer multivalued;

    private Integer indexed;

    private Integer notnull;

    private Integer iskey;

    private Integer clustering;

    private String comment;

    private Integer status;

    public SolrStructure(Long id, String doctype, String code, String name, Integer datatype, Integer multivalued, Integer indexed, Integer notnull, Integer iskey, Integer clustering, String comment, Integer status) {
        this.id = id;
        this.doctype = doctype;
        this.code = code;
        this.name = name;
        this.datatype = datatype;
        this.multivalued = multivalued;
        this.indexed = indexed;
        this.notnull = notnull;
        this.iskey = iskey;
        this.clustering = clustering;
        this.comment = comment;
        this.status = status;
    }

    public SolrStructure() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype == null ? null : doctype.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getDatatype() {
        return datatype;
    }

    public void setDatatype(Integer datatype) {
        this.datatype = datatype;
    }

    public Integer getMultivalued() {
        return multivalued;
    }

    public void setMultivalued(Integer multivalued) {
        this.multivalued = multivalued;
    }

    public Integer getIndexed() {
        return indexed;
    }

    public void setIndexed(Integer indexed) {
        this.indexed = indexed;
    }

    public Integer getNotnull() {
        return notnull;
    }

    public void setNotnull(Integer notnull) {
        this.notnull = notnull;
    }

    public Integer getIskey() {
        return iskey;
    }

    public void setIskey(Integer iskey) {
        this.iskey = iskey;
    }

    public Integer getClustering() {
        return clustering;
    }

    public void setClustering(Integer clustering) {
        this.clustering = clustering;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}