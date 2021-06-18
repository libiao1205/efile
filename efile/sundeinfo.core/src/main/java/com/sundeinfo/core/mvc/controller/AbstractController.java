package com.sundeinfo.core.mvc.controller;

import com.sundeinfo.core.request.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.core.mvc.service.ServiceManager;

import javax.annotation.PostConstruct;


public abstract class AbstractController<T> extends AbstractComponent<T> implements Controller<T> {

    @Autowired
    protected RequestHandler requestHandler;

}
