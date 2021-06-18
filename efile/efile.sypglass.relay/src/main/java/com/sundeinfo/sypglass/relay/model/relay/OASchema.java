package com.sundeinfo.sypglass.relay.model.relay;

public class OASchema {
    private String tablename;

    private Integer columnindex;

    private String columnname;

    private String isid;

    private String iskey;

    private String columntype;

    private Integer columnbyte;

    private Integer columnlength;

    private Integer decimallength;

    private String cannull;

    private String defaultvalue;

    private String comment;

    public OASchema(String tablename, Integer columnindex, String columnname, String isid, String iskey, String columntype, Integer columnbyte, Integer columnlength, Integer decimallength, String cannull, String defaultvalue, String comment) {
        this.tablename = tablename;
        this.columnindex = columnindex;
        this.columnname = columnname;
        this.isid = isid;
        this.iskey = iskey;
        this.columntype = columntype;
        this.columnbyte = columnbyte;
        this.columnlength = columnlength;
        this.decimallength = decimallength;
        this.cannull = cannull;
        this.defaultvalue = defaultvalue;
        this.comment = comment;
    }

    public OASchema() {
        super();
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename == null ? null : tablename.trim();
    }

    public Integer getColumnindex() {
        return columnindex;
    }

    public void setColumnindex(Integer columnindex) {
        this.columnindex = columnindex;
    }

    public String getColumnname() {
        return columnname;
    }

    public void setColumnname(String columnname) {
        this.columnname = columnname == null ? null : columnname.trim();
    }

    public String getIsid() {
        return isid;
    }

    public void setIsid(String isid) {
        this.isid = isid == null ? null : isid.trim();
    }

    public String getIskey() {
        return iskey;
    }

    public void setIskey(String iskey) {
        this.iskey = iskey == null ? null : iskey.trim();
    }

    public String getColumntype() {
        return columntype;
    }

    public void setColumntype(String columntype) {
        this.columntype = columntype == null ? null : columntype.trim();
    }

    public Integer getColumnbyte() {
        return columnbyte;
    }

    public void setColumnbyte(Integer columnbyte) {
        this.columnbyte = columnbyte;
    }

    public Integer getColumnlength() {
        return columnlength;
    }

    public void setColumnlength(Integer columnlength) {
        this.columnlength = columnlength;
    }

    public Integer getDecimallength() {
        return decimallength;
    }

    public void setDecimallength(Integer decimallength) {
        this.decimallength = decimallength;
    }

    public String getCannull() {
        return cannull;
    }

    public void setCannull(String cannull) {
        this.cannull = cannull == null ? null : cannull.trim();
    }

    public String getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue == null ? null : defaultvalue.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}