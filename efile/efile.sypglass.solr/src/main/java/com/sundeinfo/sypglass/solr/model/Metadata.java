package com.sundeinfo.sypglass.solr.model;

public class Metadata {
    private Long id;

    private Integer doctype;

    private Integer dataproperty;

    private Integer datatype;

    private String code;

    private String name;

    private Integer seq;

    private Integer size;

    private String defaultvalue;

    private Long checkrule;

    private Integer notnull;

    private Integer mustsingle;

    private Long solrid;

    private String solrname;

    private String comment;

    private Integer status;

    public Metadata(Long id, Integer doctype, Integer dataproperty, Integer datatype, String code, String name, Integer seq, Integer size, String defaultvalue, Long checkrule, Integer notnull, Integer mustsingle, Long solrid, String solrname, String comment, Integer status) {
        this.id = id;
        this.doctype = doctype;
        this.dataproperty = dataproperty;
        this.datatype = datatype;
        this.code = code;
        this.name = name;
        this.seq = seq;
        this.size = size;
        this.defaultvalue = defaultvalue;
        this.checkrule = checkrule;
        this.notnull = notnull;
        this.mustsingle = mustsingle;
        this.solrid = solrid;
        this.solrname = solrname;
        this.comment = comment;
        this.status = status;
    }

    public Metadata() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDoctype() {
        return doctype;
    }

    public void setDoctype(Integer doctype) {
        this.doctype = doctype;
    }

    public Integer getDataproperty() {
        return dataproperty;
    }

    public void setDataproperty(Integer dataproperty) {
        this.dataproperty = dataproperty;
    }

    public Integer getDatatype() {
        return datatype;
    }

    public void setDatatype(Integer datatype) {
        this.datatype = datatype;
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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue == null ? null : defaultvalue.trim();
    }

    public Long getCheckrule() {
        return checkrule;
    }

    public void setCheckrule(Long checkrule) {
        this.checkrule = checkrule;
    }

    public Integer getNotnull() {
        return notnull;
    }

    public void setNotnull(Integer notnull) {
        this.notnull = notnull;
    }

    public Integer getMustsingle() {
        return mustsingle;
    }

    public void setMustsingle(Integer mustsingle) {
        this.mustsingle = mustsingle;
    }

    public Long getSolrid() {
        return solrid;
    }

    public void setSolrid(Long solrid) {
        this.solrid = solrid;
    }

    public String getSolrname() {
        return solrname;
    }

    public void setSolrname(String solrname) {
        this.solrname = solrname == null ? null : solrname.trim();
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