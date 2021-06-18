package com.sundeinfo.sypglass.relay.model.oa;

import lombok.Data;

@Data
public class DocImageFile {

    private int id;

    private int docid;

    private int imagefileid;

    private String imagefilename;

    private int versionid;

    public DocImageFile(Integer id, Integer docid, Integer imagefileid, String imagefilename, Integer versionid) {
        this.id = id;
        this.docid = docid;
        this.imagefileid = imagefileid;
        this.imagefilename = imagefilename;
        this.versionid = versionid;
    }

    private ImageFile imageFile;
}