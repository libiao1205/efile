package com.sundeinfo.sypglass.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration

public class WebMvcConfigurer extends WebMvcConfigurerAdapter{

    @Value("${file.tmpAccessPath}")
    private String tmpAccessPath;

    @Value("${file.folderTmpPath}")
    private String tmpPath;

    @Override
    public void configureAsyncSupport(final AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(300000);
        configurer.registerCallableInterceptors(timeoutInterceptor());
    }
    @Bean
    public TimeoutCallableProcessingInterceptor timeoutInterceptor() {
        return new TimeoutCallableProcessingInterceptor();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/400").setViewName("/400");
        registry.addViewController("/401").setViewName("/401");
        registry.addViewController("/403").setViewName("/403");
        registry.addViewController("/404").setViewName("/404");
        registry.addViewController("/405").setViewName("/405");
        registry.addViewController("/406").setViewName("/406");
        registry.addViewController("/407").setViewName("/407");
        registry.addViewController("/408").setViewName("/408");
        registry.addViewController("/409").setViewName("/409");
        registry.addViewController("/500").setViewName("/500");
        registry.addViewController("/501").setViewName("/501");
        registry.addViewController("/503").setViewName("/503");
    }

    //设置虚拟路径，访问绝对路径下资源
    //http://localhost:8080/tmp/file/name.suffix
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(tmpAccessPath + "**").addResourceLocations("file:" + tmpPath);
    }
}
