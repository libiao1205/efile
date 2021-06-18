package com.sundeinfo.sypglass.model;

public class LinkResource {
    private Long id;

    private Long linkid;

    private Long resourceid;

    private String kind;

    private String name;

    private String suffix;

    private String metadatavalue;

    public LinkResource(Long id, Long linkid, Long resourceid, String kind, String name, String suffix, String metadatavalue) {
        this.id = id;
        this.linkid = linkid;
        this.resourceid = resourceid;
        this.kind = kind;
        this.name = name;
        this.suffix = suffix;
        this.metadatavalue = metadatavalue;
    }

    public LinkResource() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLinkid() {
        return linkid;
    }

    public void setLinkid(Long linkid) {
        this.linkid = linkid;
    }

    public Long getResourceid() {
        return resourceid;
    }

    public void setResourceid(Long resourceid) {
        this.resourceid = resourceid;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind == null ? null : kind.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    public String getMetadatavalue() {
        return metadatavalue;
    }

    public void setMetadatavalue(String metadatavalue) {
        this.metadatavalue = metadatavalue == null ? null : metadatavalue.trim();
    }
}