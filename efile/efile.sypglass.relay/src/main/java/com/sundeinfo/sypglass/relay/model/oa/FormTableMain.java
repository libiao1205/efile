package com.sundeinfo.sypglass.relay.model.oa;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class FormTableMain implements Serializable {

    //表单ID
    private int id;

    //流程ID
    private int requestId;

    //日期
    private String requestDate;

    //基地
    private int comp;

    //部门
    private int dept;

    //责任人
    private int leader;

    //附件1
    private String additional1;

    //附件2
    private String additional2;

    //附件3
    private String additional3;


    public FormTableMain(Integer id, Integer requestId,String requestDate,
                         Integer comp,Integer dept, Integer leader,
                         String additional1,String additional2,String additional3) {
        this.id = id;
        this.requestId = requestId;
        this.requestDate = requestDate;
        this.comp = comp;
        this.dept = dept;
        this.leader = leader;
        this.additional1 = additional1;
        this.additional2 = additional2;
        this.additional3 = additional3;
        docImageFiles = new ArrayList<>();
    }

    private List<DocImageFile>  docImageFiles;

    public void addDocImageFile(DocImageFile docImageFile){
        this.docImageFiles.add(docImageFile);
    }

    public void addDocImageFiles(List<DocImageFile> docImageFiles){
        this.docImageFiles.addAll(docImageFiles);
    }
}