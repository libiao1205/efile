package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.security.model.AnyResource;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.sypglass.define.MessageStatus;
import com.sundeinfo.sypglass.define.MessageType;
import com.sundeinfo.sypglass.dto.MessageDto;
import com.sundeinfo.sypglass.dto.UserDto;
import com.sundeinfo.sypglass.mapper.MessageMapper;
import com.sundeinfo.sypglass.model.Message;
import com.sundeinfo.sypglass.model.MessageExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("MessageService")
public class MessageService extends AbstractService<MessageService,MessageDto>{

    @Autowired
    private MessageMapper messageMapper;

    public MessageDto createNew(AnyUser user, AnyResource resource, MessageType type){
        MessageDto messageDto = new MessageDto();
        messageDto.setStatus((byte) MessageStatus.NEW.code());
        messageDto.setFromUser(user.getUser().getId());
        messageDto.setFromUserName(user.getUser().getName());
        messageDto.setFromDate(new Date());
        messageDto.setToDate(new Date());
        messageDto.setType((byte)type.type());
        messageDto.setName(type.value());
        messageDto.setInfo(type.info());
        messageDto.setResourceId(resource.getId());
        messageDto.setResourceType(resource.getKind());
        messageDto.setResourceName(resource.getName());
        messageDto.setResourceSuffix(resource.getSuffix());
        return messageDto;
    }

    public long findUnreadCountByUser(long userId){
        MessageExample example = new MessageExample();
        MessageExample.Criteria criteria = example.createCriteria();
        criteria.andTouserEqualTo(userId);
        criteria.andStatusEqualTo((byte)1);
        long count = messageMapper.countByExample(example);
        return count;
    }

    public List<MessageDto> findByUser(long userid) throws InstantiationException, IllegalAccessException {
        MessageExample example = new MessageExample();
        MessageExample.Criteria criteria = example.createCriteria();
        criteria.andTouserEqualTo(userid);
        example.setOrderByClause("TODATE DESC");
        List<Message> messages = messageMapper.selectByExample(example);
        return ConvertUtils.convertForPage(messages,MessageDto.class);
    }

    public List<MessageDto> findByUserAndType(long userid,List<Byte> list) throws InstantiationException, IllegalAccessException {
        MessageExample example = new MessageExample();
        MessageExample.Criteria criteria = example.createCriteria();
        criteria.andTouserEqualTo(userid);
        criteria.andTypeIn(list);
        example.setOrderByClause("TODATE DESC");
        List<Message> messages = messageMapper.selectByExample(example);
        return ConvertUtils.convertForPage(messages,MessageDto.class);
    }

    public List<MessageDto> findByUserAndSearchKey(long userid,String key) throws InstantiationException, IllegalAccessException {
        MessageExample example = new MessageExample();
        MessageExample.Criteria criteria = example.createCriteria();
        criteria.andTouserEqualTo(userid);
        criteria.andResourcenameLike("%" + key + "%");
        List<Message> messages = messageMapper.selectByExample(example);
        return ConvertUtils.convertForPage(messages,MessageDto.class);
    }

    public MessageDto findById(long id) throws InstantiationException, IllegalAccessException {
        Message message = messageMapper.selectByPrimaryKey(id);
        return ConvertUtils.convert(message,MessageDto.class);
    }

    /********************************以下数据库读写****************************************/

    @Transactional
    public int save(List<MessageDto> messageDtos) throws InstantiationException, IllegalAccessException {
        List<Message> messages = ConvertUtils.convertList(messageDtos,Message.class);
        for (Message message:messages) {
            messageMapper.insert(message);
        }
        return 1;
    }

    @Transactional
    public int save(MessageDto messageDto) throws InstantiationException, IllegalAccessException {
        Message message = ConvertUtils.convert(messageDto,Message.class);
        return messageMapper.insert(message);
    }

    @Transactional
    public int modAllStatusToRead(long userId) throws InstantiationException, IllegalAccessException {
        Message message = new Message();
        message.setStatus((byte)MessageStatus.READ.code());
        message.setTodate(new Date());
        MessageExample example = new MessageExample();
        MessageExample.Criteria criteria = example.createCriteria();
        criteria.andTouserEqualTo(userId);
        message.setStatus((byte)MessageStatus.READ.code());
        return messageMapper.updateByExampleSelective(message,example);
    }

    @Transactional
    public int modStatusToRead(long id) throws InstantiationException, IllegalAccessException {
        Message message = new Message();
        message.setStatus((byte)MessageStatus.READ.code());
        message.setTodate(new Date());
        MessageExample example = new MessageExample();
        MessageExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        return messageMapper.updateByExampleSelective(message,example);
    }

    @Transactional
    public int modStatusToRead(List<Long> ids) throws InstantiationException, IllegalAccessException {
        Message message = new Message();
        message.setStatus((byte)MessageStatus.READ.code());
        message.setTodate(new Date());
        MessageExample example = new MessageExample();
        MessageExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        return messageMapper.updateByExampleSelective(message,example);
    }

    @Transactional
    public int delete(MessageDto messageDto) {
        return messageMapper.deleteByPrimaryKey(messageDto.getId());
    }

    @Transactional
    public int delete(List<Long> ids) {
        MessageExample example = new MessageExample();
        MessageExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        return messageMapper.deleteByExample(example);
    }
}
