package com.sundeinfo.file.define;


public enum FileType {
    /*图片*/
    picture("pictureNormalizerExecutor"),
    /*压缩文件*/
    compress("compressNormalizerExecutor"),
    /*微软Office*/
    office("officeNormalizerExecutor"),
    /*txt文档*/
    simText("txtNormalizerExecutor"),
    /*pdf*/
    pdf("pdfNormalizerExecutor"),
    /*cad*/
    cad("cadNormalizerExecutor"),
    /*media*/
    media("mediaNormalizerExecutor"),
    /*un know*/
    other("otherNormalizerExecutor");

    private String instanceName;

    FileType(String instanceName){
        this.instanceName=instanceName;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
}
