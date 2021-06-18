package com.sundeinfo.sypglass.relay;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger {

    @Bean
    public Docket allApis() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<Parameter>();
        //tokenPar.name("x-access-token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();    	pars.add(tokenPar.build());	//添加head参数end
        parameterBuilder.name("Authorization").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        parameters.add(parameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("all")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sundeinfo.sypglass.relay.apis"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(parameters);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("耀皮 API 手册")
                .description("此在线API手册为客户端用户提供开发参考")
                .termsOfServiceUrl("http://www.sundeinfo.cn:9090/xap/kms")
                .version("1.0")
                .license("双地|MIT")
                .license("http://www.sundeinfo.cn")
                .build();
    }
}
