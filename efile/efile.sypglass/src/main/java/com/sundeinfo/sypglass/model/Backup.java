package com.sundeinfo.sypglass.model;

import java.util.Date;

public class Backup {
    private Long id;

    private Date time;

    private String directory;

    private String result;

    public Backup(Long id, Date time, String directory, String result) {
        this.id = id;
        this.time = time;
        this.directory = directory;
        this.result = result;
    }

    public Backup() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory == null ? null : directory.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }
}