package com.sundeinfo.sypglass.apis.message;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mongodb.gridfs.GridFSDBFile;
import com.sundeinfo.core.token.Token;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.permission.FunctionType;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.sypglass.component.PermissionAdapter;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.service.*;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.dto.*;
import com.sundeinfo.sypglass.utility.EncodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Api(description = "用户系统API")
@RestController
@RequestMapping("user")
public class MessageController extends AbstractController<MessageController> {

    @Autowired
    private MessageService messageService;

    @Autowired
    private GridfsService gridfsService;

    @Autowired
    private FileService fileService;

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @Autowired
    private MessageSettingService messageSettingService;


    @ApiOperation(value="批量查找用户的消息列表", notes="批量查找用户的消息列表")
    @Token()
    @GetMapping(value = "messages/count")
    public ResponseEntity<Result<Long>> getUserMessageCount(){
        return requestHandler.doGet(
                new GetCallback<Long>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public Long invoke() throws Exception {
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        return messageService.findUnreadCountByUser(anyUser.getUser().getId());
                    }
                }
        );
    }

    @ApiOperation(value="批量查找用户的消息列表", notes="批量查找用户的消息列表")
    @Token()
    @GetMapping(value = "messages/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<MessageDto>>> getUserMessages(@PathVariable(value = "pagenum") int pagenum, @PathVariable(value = "pagesize") int pagesize){
        return requestHandler.doGet(
                new GetCallback<PageInfo<MessageDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<MessageDto> invoke() throws Exception {
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        MessageSettingDto dto = messageSettingService.findByUser(anyUser.getUser().getId());

                        PageHelper.startPage(pagenum, pagesize);
                        List<MessageDto> messageDtos = new ArrayList<>();
                        if(dto != null){
                            List<Byte> inStatuses = new ArrayList<>();
                            if(dto.getUpdatenotice()){ inStatuses.add((byte)1); }
                            if(dto.getDeletenotice()){ inStatuses.add((byte)3); }
                            if(dto.getDownloadnotice()){ inStatuses.add((byte)5); }
                            if(dto.getDownloadnotice()){ inStatuses.add((byte)6); }
                            messageDtos = messageService.findByUserAndType(anyUser.getUser().getId(),inStatuses);
                        }else{
                            messageDtos = messageService.findByUser(anyUser.getUser().getId());
                        }

                        PageInfo<MessageDto> pageInfo = new PageInfo<>(messageDtos);
                        return pageInfo;
                    }
                }
        );
    }

    @ApiOperation(value="批量查找用户的消息列表", notes="批量查找用户的消息列表")
    @Token()
    @GetMapping(value = "messagesbysearchkey/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<MessageDto>>> getUserMessagesBySearchKey(@PathVariable(value = "pagenum") int pagenum, @PathVariable(value = "pagesize") int pagesize,@RequestParam String key){
        return requestHandler.doGet(
                new GetCallback<PageInfo<MessageDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<MessageDto> invoke() throws Exception {
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        PageHelper.startPage(pagenum, pagesize);
                        List<MessageDto> messageDtos = messageService.findByUserAndSearchKey(anyUser.getUser().getId(),key);
                        PageInfo<MessageDto> pageInfo = new PageInfo<>(messageDtos);
                        return pageInfo;
                    }
                }
        );
    }

    @ApiOperation(value="用户的所有消息变已读", notes="用户的所有消息变已读")
    @PutMapping(value = "messages/read")
    public ResponseEntity<Result> putMessagesToRead(){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        AnyUser anyUser = authenticationGetter.getAnyUser();
                        int i = messageService.modAllStatusToRead(anyUser.getUser().getId());
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="用户消息变已读", notes="用户消息变已读")
    @PutMapping("message")
    public ResponseEntity<Result> putMessagesToRead(@RequestBody MessageDto messageDto) {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = messageService.modStatusToRead(messageDto.getId());
                        return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="用户消息变已读", notes="用户消息变已读")
    @PutMapping("messages")
    public ResponseEntity<Result> putMessageToRead(@RequestBody List<MessageDto> messageDtos) {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        List<Long> ids = ReflectUtils.reflectDataList(messageDtos,"id",Long.class);
                        int i = messageService.modStatusToRead(ids);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="用户消息删除", notes="用户消息删除")
    @DeleteMapping("message")
    public ResponseEntity<Result> deleteMessages(@RequestBody MessageDto messageDto) {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = messageService.delete(messageDto);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="用户消息删除", notes="用户消息删除")
    @DeleteMapping("messages")
    public ResponseEntity<Result> deleteMessage(@RequestBody List<MessageDto> messageDtos) {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        List<Long> ids = ReflectUtils.reflectDataList(messageDtos,"id",Long.class);
                        int i = messageService.delete(ids);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="文件下载", notes="文件下载")
    @PostMapping("message/{id}/seq/0/download")
    public void downloadFile(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MessageDto messageDto = messageService.findById(id);
        if (SypGlass.RESOURCE_TYPE_FOLDER.equals(messageDto.getResourceType())){
            return;
        }
        messageService.modStatusToRead(id);

        FileDto fileDto = fileService.findWithAdditionalById(messageDto.getResourceId());
        if (fileDto.getStatus() == 9){
            return;
        }

        GridFSDBFile file = gridfsService.getById(new ObjectId(fileDto.getSaveId()));
        if (file == null) {
            return;
        }

        OutputStream outputStream = null;
        try {

            String OriginalfileName = EncodeUtils.encoder(request,file.getFilename().replace(",", ""));
            //处理斜杠问题 比如：A+SUV转移武汉/VW标准/DIN/DIN EN 10263-3 2002-02 EN.pdf 保存到本地会出错
            String[] fileNameSplit = OriginalfileName.split("/");
            String fileName = fileNameSplit[fileNameSplit.length -1];
            // 通知浏览器进行文件下载
            outputStream = response.getOutputStream();
            response.addHeader("Access-Control-Expose-Headers","Content-Disposition");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Content-Length", "" + file.getLength());
            response.setContentType("application/octet-stream");

            file.writeTo(outputStream);
            outputStream.flush();
            outputStream.close();

            AnyUser anyUser = authenticationGetter.getAnyUser();
            fileService.addActionHistory(id,FunctionType.DOWNLOAD,anyUser.getUser().getId(),anyUser.getUser().getName());
        } catch (Exception e) {
            try{
                if (outputStream != null) {
                    outputStream.close();
                }
            }catch (Exception ignored){}
            e.printStackTrace();
        }
    }

}
