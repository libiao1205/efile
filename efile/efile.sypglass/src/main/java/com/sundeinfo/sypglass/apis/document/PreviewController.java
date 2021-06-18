package com.sundeinfo.sypglass.apis.document;

import com.sundeinfo.core.mvc.controller.AbstractController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(description = "预览系统API")
@RestController
@RequestMapping("preview")
public class PreviewController extends AbstractController<PreviewController> {


}
