package com.sundeinfo.sypglass.model;

public class Permission {
    private Long id;

    private String code;

    private String action;

    private String kind;

    private String function;

    private Integer value;

    private String description;

    public Permission(Long id, String code, String action, String kind, String function, Integer value, String description) {
        this.id = id;
        this.code = code;
        this.action = action;
        this.kind = kind;
        this.function = function;
        this.value = value;
        this.description = description;
    }

    public Permission() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind == null ? null : kind.trim();
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function == null ? null : function.trim();
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}