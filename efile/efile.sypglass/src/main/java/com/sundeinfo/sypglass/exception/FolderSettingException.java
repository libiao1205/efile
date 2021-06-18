package com.sundeinfo.sypglass.exception;

public class FolderSettingException extends RuntimeException  {

    private long workflowId;
    private String firstCode;
    private String secondCode;
    private String thirdCode;
    private String fourthCode;

    public FolderSettingException(String message, long workflowId, String firstCode, String secondCode, String thirdCode, String fourthCode) {
        super(message);
        this.workflowId = workflowId;
        this.firstCode = firstCode;
        this.secondCode = secondCode;
        this.thirdCode = thirdCode;
        this.fourthCode = fourthCode;
    }
}
