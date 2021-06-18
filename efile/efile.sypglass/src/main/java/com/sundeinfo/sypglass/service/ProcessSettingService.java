package com.sundeinfo.sypglass.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sundeinfo.core.http.HttpService;
import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.sypglass.dto.ProcessSettingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("ProcessSettingService")
public class ProcessSettingService extends AbstractService<ProcessSettingService,ProcessSettingDto> {

    @Value("${schedule.send.enabled}")
    private String send_enabled;

    @Value("${schedule.setting.path}")
    private String find_url;

    @Value("${schedule.start.path}")
    private String start_url;

    @Value("${schedule.cron.path}")
    private String cron_url;

    @Value("${schedule.pause.path}")
    private String pause_url;

    @Value("${schedule.resume.path}")
    private String resume_url;

    @Autowired
    private HttpService httpService;

    public List<ProcessSettingDto> findAll() throws Exception {
        if ("true".equals(send_enabled)){
            String json = httpService.doGet(find_url);

            if (StringUtils.isEmpty(json)){
                return new ArrayList<>();
            }else
            {
                return convertToClass(json);
            }
        }else{
            return new ArrayList<>();
        }
    }

    public int pause(long id) throws Exception {
        if ("true".equals(send_enabled)){
            String url = String.format(pause_url,id);
            httpService.doPut(url);
        }
        return 1;
    }

    public int resume(long id) throws Exception {
        if ("true".equals(send_enabled)){
            String url = String.format(resume_url,id);
            httpService.doPut(url);
        }
        return 1;
    }

    public int updateCron(long id,String cron) throws Exception {
        if ("true".equals(send_enabled)){
            String urlCron = cron.replaceAll(" ", "%20");
            String url = String.format(cron_url,id) + "?cron=" + urlCron;
            httpService.doPut(url);
        }
        return 1;
    }

    private List<ProcessSettingDto> convertToClass(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        List<ProcessSettingDto> settings = new ArrayList<>();
        for (JsonNode node:root.get("ctt")){
            String ctt = node.toString();
            settings.add(mapper.readValue(ctt, new TypeReference<ProcessSettingDto>() {}));
        }
        return settings;
    }
}