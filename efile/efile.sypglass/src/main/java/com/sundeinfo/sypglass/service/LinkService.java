package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.sypglass.define.FileDocType;
import com.sundeinfo.sypglass.define.LinkStatus;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.*;
import com.sundeinfo.sypglass.mapper.LinkMapper;
import com.sundeinfo.sypglass.mapper.LinkResourceMapper;
import com.sundeinfo.sypglass.model.*;
import com.sundeinfo.sypglass.utility.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("LinkService")
public class LinkService extends AbstractService<LinkService,LinkDto>{

    @Value("${link.path}")
    private String linkPath;

    @Autowired
    private LinkMapper linkMapper;

    @Autowired
    private LinkResourceMapper linkResourceMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private UserService userService;

    public LinkDto createNew(LinkDto linkDto,long id){
        LinkDto newDto = new LinkDto();
        newDto.setName(SypGlass.getLinkName(new Date()));
        newDto.setRandomFolderName(linkDto.getRandomFolderName());
        newDto.setFullUrl(getLinkUrl(linkDto.getRandomFolderName()));
        newDto.setPassword(linkDto.getPassword());
        newDto.setUserId(id);
        newDto.setStartDate(DateUtils.getDayStart(new Date()));
        if (linkDto.getEndDate() == null){
            newDto.setDays(0);
            newDto.setEndDate(DateUtils.strToDate(SypGlass.INDEFINITE_DATE));
        }else{
            int days = (int)DateUtils.getDateDiff(newDto.getStartDate(),linkDto.getEndDate());
            newDto.setDays(days + 1);
            newDto.setEndDate(linkDto.getEndDate());
        }
        newDto.setIsDelete((byte)0);
        newDto.setResources(linkDto.getResources());
        return newDto;
    }

    public LinkDto findById(long id) throws InstantiationException, IllegalAccessException {
        Link link = linkMapper.selectByPrimaryKey(id);
        LinkDto linkDto = ConvertUtils.convert(link,LinkDto.class);
//        linkDto.setStatus(getLinkStatus(linkDto));
        return linkDto;
    }

    public List<LinkDto> findAll() throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        LinkExample example = new LinkExample();
        LinkExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        criteria.andUseridNotEqualTo(1L);
        List<Link> links = linkMapper.selectByExample(example);
        if (links.size() == 0) { return new ArrayList<>(); }
        List<LinkDto> linkDtos = ConvertUtils.convertList(links,LinkDto.class);
        List<Long> linkIds = ReflectUtils.reflectDataList(links,"id",Long.class);
        List<LinkResourceDto> linkResourceDtos = findLinkResourceByLinks(linkIds);
        List<Long> userIds = ReflectUtils.reflectDataList(linkDtos,"userId",Long.class);
        List<UserDto> userDtos = userService.findByIds(userIds);

        for (LinkDto linkDto:linkDtos) {
            linkDto.setUser(userDtos.stream().filter(user-> linkDto.getUserId() == user.getId()).findFirst().get());
            linkDto.setResources(linkResourceDtos.stream().filter(lr-> linkDto.getId() == lr.getLinkId()).collect(Collectors.toList()));
        }
        return linkDtos;
    }

    public List<LinkDto> findAllByPeriod(String start,String end) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        LinkExample example = new LinkExample();
        LinkExample.Criteria criteria = example.createCriteria();
        criteria.andStartdateBetween(DateUtils.strToDate(start),DateUtils.strToDate(end));
        List<Link> links = linkMapper.selectByExample(example);
        if (links.size() == 0) { return new ArrayList<>(); }
        List<LinkDto> linkDtos = ConvertUtils.convertList(links,LinkDto.class);
        List<Long> linkIds = ReflectUtils.reflectDataList(links,"id",Long.class);
        List<LinkResourceDto> linkResourceDtos = findLinkResourceByLinks(linkIds);
        List<Long> userIds = ReflectUtils.reflectDataList(linkDtos,"userId",Long.class);
        List<UserDto> userDtos = userService.findByIds(userIds);

        for (LinkDto linkDto:linkDtos) {
            linkDto.setUser(userDtos.stream().filter(user-> linkDto.getUserId() == user.getId()).findFirst().get());
            linkDto.setResources(linkResourceDtos.stream().filter(lr-> linkDto.getId() == lr.getLinkId()).collect(Collectors.toList()));
        }
        return linkDtos;
    }

    public List<LinkDto> findAllForPage() throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        LinkExample example = new LinkExample();
        LinkExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        List<Link> links = linkMapper.selectByExample(example);
        if (links.size() == 0) { return new ArrayList<>(); }
        List<LinkDto> linkDtos = ConvertUtils.convertForPage(links,LinkDto.class);
        List<Long> linkIds = ReflectUtils.reflectDataList(links,"id",Long.class);
        List<LinkResourceDto> linkResourceDtos = findLinkResourceByLinks(linkIds);
        List<Long> userIds = ReflectUtils.reflectDataList(linkDtos,"userId",Long.class);
        List<UserDto> userDtos = userService.findByIds(userIds);

        for (LinkDto linkDto:linkDtos) {
            linkDto.setUser(userDtos.stream().filter(user-> linkDto.getUserId() == user.getId()).findFirst().orElse(null));
            linkDto.setResources(linkResourceDtos.stream().filter(lr-> linkDto.getId() == lr.getLinkId()).collect(Collectors.toList()));
        }
        return linkDtos;
    }

    public List<LinkDto> findByUsersForPage(List<Long> userIds) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        LinkExample example = new LinkExample();
        LinkExample.Criteria criteria = example.createCriteria();
        criteria.andUseridIn(userIds);
        List<Link> links = linkMapper.selectByExample(example);
        if (links.size() == 0) { return new ArrayList<>(); }
        List<LinkDto> linkDtos = ConvertUtils.convertForPage(links,LinkDto.class);
        List<Long> linkIds = ReflectUtils.reflectDataList(links,"id",Long.class);
        List<LinkResourceDto> linkResourceDtos = findLinkResourceByLinks(linkIds);
        for (LinkDto linkDto:linkDtos) {
            linkDto.setResources(linkResourceDtos.stream().filter(lr-> linkDto.getId() == lr.getLinkId()).collect(Collectors.toList()));
        }
        return linkDtos;
    }

    public List<LinkDto> findByUser(long userId) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        LinkExample example = new LinkExample();
        LinkExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(userId);
        criteria.andIsdeleteNotEqualTo((byte)1);
        example.setOrderByClause("STARTDATE DESC");
        List<Link> links = linkMapper.selectByExample(example);
        if (links.size() == 0) { return new ArrayList<>(); }
        List<LinkDto> linkDtos = ConvertUtils.convertForPage(links,LinkDto.class);
        List<Long> linkIds = ReflectUtils.reflectDataList(links,"id",Long.class);
        List<LinkResourceDto> linkResourceDtos = findLinkResourceByLinks(linkIds);
        for (LinkDto linkDto:linkDtos) {
            linkDto.setResources(linkResourceDtos.stream().filter(lr-> linkDto.getId() == lr.getLinkId()).collect(Collectors.toList()));
        }
        return linkDtos;
    }

    public List<LinkDto> findByUserAndKey(long userId,String key) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        LinkExample example = new LinkExample();
        LinkExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(userId);
        criteria.andIsdeleteNotEqualTo((byte)1);
        List<Link> links = linkMapper.selectByExample(example);
        if (links.size() == 0) { return new ArrayList<>(); }
        List<LinkDto> linkDtos = ConvertUtils.convertForPage(links,LinkDto.class);
        List<Long> linkIds = ReflectUtils.reflectDataList(links,"id",Long.class);
        List<LinkResourceDto> linkResourceDtos = findLinkResourceByLinks(linkIds,key);
        for (LinkDto linkDto:linkDtos) {
            linkDto.setResources(linkResourceDtos.stream().filter(lr-> linkDto.getId() == lr.getLinkId()).collect(Collectors.toList()));
        }
        List<LinkDto> haveNoResource = new ArrayList<>();
        for (LinkDto linkDto:linkDtos) {
            if (linkDto.getResources() == null || linkDto.getResources().size() == 0){
                haveNoResource.add(linkDto);
            }
        }
        linkDtos.removeAll(haveNoResource);
        return linkDtos;
    }

    public List<LinkDto> findByUsers(List<Long> userIds) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        LinkExample example = new LinkExample();
        LinkExample.Criteria criteria = example.createCriteria();
        criteria.andUseridIn(userIds);
        List<Link> links = linkMapper.selectByExample(example);
        if (links.size() == 0) { return new ArrayList<>(); }
        List<LinkDto> linkDtos = ConvertUtils.convertList(links,LinkDto.class);
        List<Long> linkIds = ReflectUtils.reflectDataList(links,"id",Long.class);
        List<LinkResourceDto> linkResourceDtos = findLinkResourceByLinks(linkIds);
        for (LinkDto linkDto:linkDtos) {
            linkDto.setUser(userService.findById(linkDto.getUserId()));
            linkDto.setResources(linkResourceDtos.stream().filter(lr-> linkDto.getId() == lr.getLinkId()).collect(Collectors.toList()));
        }
        return linkDtos;
    }



    public List<LinkDto> findByUsersAndPeriod(List<Long> userIds,String start,String end) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        LinkExample example = new LinkExample();
        LinkExample.Criteria criteria = example.createCriteria();
        criteria.andUseridIn(userIds);
        criteria.andStartdateBetween(DateUtils.strToDate(start),DateUtils.strToDate(end));
        List<Link> links = linkMapper.selectByExample(example);
        if (links.size() == 0) { return new ArrayList<>(); }
        List<LinkDto> linkDtos = ConvertUtils.convertList(links,LinkDto.class);
        List<Long> linkIds = ReflectUtils.reflectDataList(links,"id",Long.class);
        List<LinkResourceDto> linkResourceDtos = findLinkResourceByLinks(linkIds);
        for (LinkDto linkDto:linkDtos) {
            linkDto.setUser(userService.findById(linkDto.getUserId()));
            linkDto.setResources(linkResourceDtos.stream().filter(lr-> linkDto.getId() == lr.getLinkId()).collect(Collectors.toList()));
        }
        return linkDtos;
    }

    public LinkDto findByRandom(String random) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        LinkExample example = new LinkExample();
        LinkExample.Criteria criteria = example.createCriteria();
        criteria.andRandomfoldernameEqualTo(random);
        List<Link> links = linkMapper.selectByExample(example);
        if (links == null || links.size() == 0) {
            return null;
        }else {
            LinkDto linkDto = ConvertUtils.convert(links.get(0),LinkDto.class);
            linkDto.setResources(findLinkResourceByLink(linkDto.getId()));
            return linkDto;
        }
    }

    public LinkStatus checkValid(String random, String password){
        LinkExample example = new LinkExample();
        LinkExample.Criteria criteria = example.createCriteria();
        criteria.andRandomfoldernameEqualTo(random);
        criteria.andPasswordEqualTo(password);
        List<Link> links = linkMapper.selectByExample(example);
        if (links.size() == 0) return LinkStatus.UNKNOWN;
        Link link = links.get(0);
        if (link.getIsdelete() == 1) return LinkStatus.DELETE;
        if (link.getDays() == 0) return LinkStatus.NORMAL;
        if (DateUtils.getDateDiff(link.getStartdate(),DateUtils.getNow()) > link.getDays()) return LinkStatus.EXPIRE;
        return LinkStatus.NORMAL;
    }

    public boolean hasSameRandom(String random){
        LinkExample example = new LinkExample();
        LinkExample.Criteria criteria = example.createCriteria();
        criteria.andRandomfoldernameEqualTo(random);
        List<Link> links = linkMapper.selectByExample(example);
        return links.size() > 0;
    }

    private List<LinkResourceDto> findLinkResourceByLinks(List<Long> ids) throws InstantiationException, IllegalAccessException {
        LinkResourceExample example = new LinkResourceExample();
        LinkResourceExample.Criteria criteria = example.createCriteria();
        criteria.andLinkidIn(ids);
        List<LinkResource> linkResources = linkResourceMapper.selectByExample(example);
        return ConvertUtils.convertList(linkResources,LinkResourceDto.class);
    }

    private List<LinkResourceDto> findLinkResourceByLinks(List<Long> ids,String key) throws InstantiationException, IllegalAccessException {
        LinkResourceExample example = new LinkResourceExample();
        LinkResourceExample.Criteria criteria = example.createCriteria();
        criteria.andLinkidIn(ids);
        criteria.andMetadatavalueLike("%"+key+"%");
        List<LinkResource> linkResources = linkResourceMapper.selectByExample(example);
        return ConvertUtils.convertList(linkResources,LinkResourceDto.class);
    }

    private List<LinkResourceDto> findLinkResourceByLink(long id) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        LinkResourceExample example = new LinkResourceExample();
        LinkResourceExample.Criteria criteria = example.createCriteria();
        criteria.andLinkidEqualTo(id);
        List<LinkResource> linkResources = linkResourceMapper.selectByExample(example);
        List<LinkResourceDto> linkResourceDtos = ConvertUtils.convertList(linkResources,LinkResourceDto.class);

        for (LinkResourceDto lr: linkResourceDtos) {
            switch (lr.getKind()){
                case SypGlass.RESOURCE_TYPE_FOLDER:
                    lr.setResource(folderService.findById(lr.getResourceId()));continue;
                case SypGlass.RESOURCE_TYPE_FILE:
                    FileDto fileDto = fileService.findById(lr.getResourceId());
                    lr.setResource(fileDto);
                    if(fileDto.getDocType() != null && fileDto.getDocType() == FileDocType.LITERATURE.getStatus()){
                        //如果是文献类型就追加附件信息和元数据信息
                        lr.setFileAccessoryDto(fileService.findFileAccessoryByFileId(fileDto.getId()));
                        lr.setFileMetadataDto(fileService.findFileMetadataByFileId(fileDto.getId()));
                    }
            }
        }
        return linkResourceDtos;
    }

    private String getLinkUrl(String random){
        return String.format(linkPath,random);
    }

    @Transactional
    public int save(LinkDto linkDto) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        Link link = ConvertUtils.convert(linkDto,Link.class);
        linkMapper.insert(link);
        for (LinkResourceDto lrDto:linkDto.getResources()) {
            List<FileMetadataDto> fileMetadataDtos = fileService.findFileMetadataByFileId(lrDto.getResourceId());
            LinkResource linkResource = ConvertUtils.convert(lrDto,LinkResource.class);
            if(fileMetadataDtos != null){
                StringBuffer strbuf = new StringBuffer();
                fileMetadataDtos.forEach(dto->{
                    strbuf.append(dto.getValue());
                });
                linkResource.setMetadatavalue(strbuf.toString());
            } else {
              linkResource.setMetadatavalue(linkResource.getName());
            }
            linkResource.setLinkid(link.getId());
            linkResourceMapper.insert(linkResource);
        }
        return 1;
    }

    public int mod(LinkDto linkDto) throws InstantiationException, IllegalAccessException {
        Link link = ConvertUtils.convert(linkDto,Link.class);
        link.setId(null);
        link.setUserid(null);
        link.setIsdelete(null);
        link.setDeleteuser(null);
        link.setDeletedate(null);
        LinkExample example = new LinkExample();
        LinkExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(linkDto.getId());
        return linkMapper.updateByExampleSelective(link,example);
    }

    public int modToDelete(long id,long userId) {
        Link link = new Link();
        link.setIsdelete((byte)1);
        link.setDeleteuser(userId);
        link.setDeletedate(new Date());
        LinkExample example = new LinkExample();
        LinkExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        return linkMapper.updateByExampleSelective(link,example);
    }

    public int modToDelete(List<Long> ids,long userId) {
        Link link = new Link();
        link.setIsdelete((byte)1);
        link.setDeleteuser(userId);
        link.setDeletedate(new Date());
        LinkExample example = new LinkExample();
        LinkExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        return linkMapper.updateByExampleSelective(link,example);
    }
}
