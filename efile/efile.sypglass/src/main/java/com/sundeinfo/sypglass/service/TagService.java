package com.sundeinfo.sypglass.service;


import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.sypglass.define.TagType;
import com.sundeinfo.sypglass.dto.TagDto;
import com.sundeinfo.sypglass.mapper.TagMapper;
import com.sundeinfo.sypglass.mapper.UserTagMapper;
import com.sundeinfo.sypglass.model.Tag;
import com.sundeinfo.sypglass.model.TagExample;
import com.sundeinfo.sypglass.model.UserTag;
import com.sundeinfo.sypglass.model.UserTagExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("TagService")
public class TagService extends AbstractService<TagService,TagDto> {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private UserTagMapper userTagMapper;


    public List<TagDto> findSystemTags() throws InstantiationException, IllegalAccessException {
        TagExample example = new TagExample();
        TagExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo((byte) TagType.SYSTEM.code());
        List<Tag> tags = tagMapper.selectByExample(example);
        return ConvertUtils.convertForPage(tags,TagDto.class);
    }

    public List<TagDto> findUserTags(long userId) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        UserTagExample example = new UserTagExample();
        UserTagExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(userId);
        List<UserTag> userTags = userTagMapper.selectByExample(example);
        if (userTags == null || userTags.size() == 0) return new ArrayList<>();
        List<Long> ids = ReflectUtils.reflectDataList(userTags,"tagid", Long.class);
        return findByIds(ids);
    }

    public List<TagDto> findByIds(List<Long> ids) throws InstantiationException, IllegalAccessException {
        TagExample example = new TagExample();
        TagExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        List<Tag> tags = tagMapper.selectByExample(example);
        return ConvertUtils.convertList(tags,TagDto.class);
    }

    /********************************以下数据库读写****************************************/

    @Transactional
    public int save(TagDto tagDto,long userId){
        Tag tag = new Tag();
        tag.setName(tagDto.getName());
        tag.setType(tagDto.getType());
        int i = save(tag);
        if (i == 0){
            return 0;
        }
        tagDto.setId(tag.getId());
        UserTag userTag = new UserTag();
        userTag.setTagid(tag.getId());
        userTag.setUserid(userId);
        return userTagMapper.insert(userTag);
    }

    @Transactional
    public int save(String name){
        Tag tag = new Tag();
        tag.setName(name);
        tag.setType((byte)TagType.SYSTEM.code());
        return save(tag);
    }

    private int save(Tag tag){
        return tagMapper.insert(tag);
    }

    @Transactional
    public int modName(long id,String name){
        Tag tag = new Tag();
        tag.setName(name);

        TagExample example = new TagExample();
        TagExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);

        return tagMapper.updateByExampleSelective(tag,example);

    }

    @Transactional
    public int delete(long id){
        UserTagExample example = new UserTagExample();
        UserTagExample.Criteria criteria = example.createCriteria();
        criteria.andTagidEqualTo(id);
        userTagMapper.deleteByExample(example);
        tagMapper.deleteByPrimaryKey(id);
        return 1;
    }

    @Transactional
    public int delete(List<Long> ids){
        UserTagExample example1 = new UserTagExample();
        UserTagExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andTagidIn(ids);
        userTagMapper.deleteByExample(example1);

        TagExample example2 = new TagExample();
        TagExample.Criteria criteria2 = example2.createCriteria();
        criteria2.andIdIn(ids);
        tagMapper.deleteByExample(example2);

        return 1;
    }

}