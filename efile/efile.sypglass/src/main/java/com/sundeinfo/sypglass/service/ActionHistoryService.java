package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.permission.FunctionType;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.UserDto;
import com.sundeinfo.sypglass.mapper.ActionHistoryMapper;
import com.sundeinfo.sypglass.model.ActionHistory;
import com.sundeinfo.sypglass.model.ActionHistoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("ActionHistoryService")
public class ActionHistoryService extends AbstractService<ActionHistoryService,ActionHistory> {

    @Autowired
    private ActionHistoryMapper actionHistoryMapper;

    public List<ActionHistory> findByKindAndId(String kind,long id){
        ActionHistoryExample example = new ActionHistoryExample();
        ActionHistoryExample.Criteria criteria = example.createCriteria();
        criteria.andResourceidEqualTo(id);
        criteria.andKindEqualTo(kind);
        example.setOrderByClause("SEQ DESC");
        return actionHistoryMapper.selectByExample(example);
    }

    public int saveFileAction(long id, FunctionType functionType, long userId, String username){
        ActionHistory actionHistory = new ActionHistory();
        actionHistory.setAction(functionType.value());
        actionHistory.setResourceid(id);
        actionHistory.setKind(SypGlass.RESOURCE_TYPE_FILE);
        actionHistory.setSeq(getMax(findByKindAndId(SypGlass.RESOURCE_TYPE_FILE,id)) + 1);
        actionHistory.setUserid(userId);
        actionHistory.setUsername(username);
        actionHistory.setUpdatedate(new Date());

        return actionHistoryMapper.insert(actionHistory);
    }

    public int saveFolderAction(long id, FunctionType functionType, long userId, String username){
        ActionHistory actionHistory = new ActionHistory();
        actionHistory.setAction(functionType.value());
        actionHistory.setResourceid(id);
        actionHistory.setKind(SypGlass.RESOURCE_TYPE_FOLDER);
        actionHistory.setSeq(getMax(findByKindAndId(SypGlass.RESOURCE_TYPE_FOLDER,id)) + 1);
        actionHistory.setUserid(userId);
        actionHistory.setUsername(username);
        actionHistory.setUpdatedate(new Date());

        return actionHistoryMapper.insert(actionHistory);
    }

    private int getMax(List<ActionHistory> actionHistories){
        if (actionHistories.size() == 0) return 0;
        actionHistories = actionHistories.stream().sorted(Comparator.comparing(ActionHistory::getSeq).reversed()).collect(Collectors.toList());
        return actionHistories.get(0).getSeq();
    }

}
