package com.sundeinfo.sypglass.interceptor;

import com.github.pagehelper.PageInfo;
import com.sundeinfo.sypglass.annotation.ResourcePermission;
import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.permission.PermissionHelper;
import com.sundeinfo.security.domain.RolePermissionDomain;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.FileDto;
import com.sundeinfo.sypglass.dto.FolderDto;
import com.sundeinfo.sypglass.model.Document;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
public class ResourcePermissionInterceptor extends AbstractComponent<ResourcePermissionInterceptor> {

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @Pointcut("execution(* com.sundeinfo.*.apis..*(..)) && @annotation(com.sundeinfo.sypglass.annotation.ResourcePermission)")
    public void checkPointcut(){}

    @Around(value = "checkPointcut() && @annotation(resourcePermission)")
    public Object interceptor(ProceedingJoinPoint point, ResourcePermission resourcePermission) throws Throwable{

        Object result = point.proceed();
//        switch (resourcePermission.value()){
//            case TREE_FOLDER:
//                setFolderPermissionMap(convertResultToFolder(result));
//                break;
//            case PAGE_DOCUMENT:
//                setDocumentPermissionMap(convertResultToPageInfo(result).getList());
//                break;
//        }
        return result;
    }

//    private Object getResultCttObject(Object result){
//        if (((ResponseEntity)result).getBody() instanceof Result) {
//            return ((Result)((ResponseEntity)result).getBody()).getCtt();
//        }
//        return null;
//    }
//
//    private FolderDto convertResultToFolder(Object result){
//        Object ctt = getResultCttObject(result);
//        if(ctt instanceof FolderDto) {
//            return (FolderDto)ctt;
//        }
//        return null;
//    }
//
//    private void setFolderPermissionMap(FolderDto folder){
//        if (folder != null){
//            folder.setPermissionMap(getPermissionMap(SypGlass.RESOURCE_TYPE_FOLDER,folder.getId()));
//            for (FileDto file:folder.getFiles()) {
//                file.setPermissionMap(getPermissionMap(SypGlass.RESOURCE_TYPE_FILE,file.getId()));
//            }
//            for (FolderDto child:folder.getChildren()) {
//                setFolderPermissionMap(child);
//            }
//        }
//    }
//
//    private PageInfo convertResultToPageInfo(Object result){
//        Object ctt = getResultCttObject(result);
//        if(ctt instanceof PageInfo) {
//            return (PageInfo)ctt;
//        }
//        return null;
//    }
//
//    private void setDocumentPermissionMap(List<Document> list){
//        for (Document document:list) {
//            if (SypGlass.RESOURCE_TYPE_FOLDER.equals(document.getKind())){
//                Map<String,Boolean> permissionMap = getPermissionMap(SypGlass.RESOURCE_TYPE_FOLDER,document.getId());
//                document.setPermissionMap(permissionMap);
//                if (document.getResource() != null){
//                    ((FolderDto)document.getResource()).setPermissionMap(permissionMap);
//                }
//
//            }else{
//                Map<String,Boolean> permissionMap =getPermissionMap(SypGlass.RESOURCE_TYPE_FILE,document.getId());
//                document.setPermissionMap(permissionMap);
//                if (document.getResource() != null){
//                    ((FileDto)document.getResource()).setPermissionMap(permissionMap);
//                }
//            }
//        }
//    }
//
//    private Map<String,Boolean> getPermissionMap(String code,long id){
//        List<RolePermissionDomain> rolePermissionDomains = authenticationGetter.getAnyUser().getRolePermissions();
//        List<RolePermissionDomain> rpDomains = rolePermissionDomains.stream().filter(rp -> code.equals(rp.getCode())).filter(rp -> id == rp.getResourceId()).collect(Collectors.toList());
//        if (rpDomains == null || rpDomains.size() == 0){
//            if (code.equals(SypGlass.RESOURCE_TYPE_FILE)){
//                return PermissionHelper.getFullPermissionMap();
//            }else{
//                logger.error(String.format("没有找到文件夹(ID:%s)的角色权限！(角色ID:%s)",id,authenticationGetter.getAnyUser().getRoles().get(0).getId()));
//                return PermissionHelper.getDefaultPermissionMap();
//            }
//        }else{
//            return PermissionHelper.getPermissionMapByFunction(rpDomains.get(0).getValue());
//        }
//
//    }



}
