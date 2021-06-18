package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.security.model.AnyResource;
import com.sundeinfo.security.service.ResourceDomainService;
import com.sundeinfo.sypglass.mapper.DocumentMapper;
import com.sundeinfo.sypglass.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ResourceService")
public class ResourceService extends AbstractService<ResourceDomainService,AnyResource> implements ResourceDomainService {

    @Autowired
    private DocumentMapper documentMapper;

    @Override
    public AnyResource getResourceById(String kind, long id) {
        Document document = documentMapper.selectDocumentByid(id,kind);
        try {
            return ConvertUtils.convert(document,AnyResource.class);
        }catch (Exception e){
            return null;
        }
    }


}