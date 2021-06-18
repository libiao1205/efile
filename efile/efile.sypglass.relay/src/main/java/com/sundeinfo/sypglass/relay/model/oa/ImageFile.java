package com.sundeinfo.sypglass.relay.model.oa;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImageFile implements Serializable {

    private int imagefileid;

    private String imagefilename;

    private String imagefiletype;

    private String filerealpath;

    private String iszip;

    private String filesize;

    public ImageFile(Integer imagefileid, String imagefilename, String imagefiletype, String filerealpath, String iszip, String filesize) {
        this.imagefileid = imagefileid;
        this.imagefilename = imagefilename;
        this.imagefiletype = imagefiletype;
        this.filerealpath = filerealpath;
        this.iszip = iszip;
        this.filesize = filesize;
    }
}