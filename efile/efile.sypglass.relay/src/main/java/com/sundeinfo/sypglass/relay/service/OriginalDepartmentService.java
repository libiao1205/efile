package com.sundeinfo.sypglass.relay.service;

import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.sypglass.relay.mapper.oa.OriginalDepartmentMapper;
import com.sundeinfo.sypglass.relay.mapper.relay.DepartmentInfoMapper;
import com.sundeinfo.sypglass.relay.model.oa.HrmDepartment;
import com.sundeinfo.sypglass.relay.model.oa.HrmSubCompany;
import com.sundeinfo.sypglass.relay.model.relay.DepartmentInfo;
import com.sundeinfo.sypglass.relay.model.relay.DepartmentInfoExample;
import com.sundeinfo.core.utility.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("OriginalDepartmentService")
public class OriginalDepartmentService extends AbstractComponent<OriginalDepartmentService> {

    @Autowired
    private DepartmentInfoMapper departmentInfoMapper;

    @Autowired
    private OriginalDepartmentMapper originalDepartmentMapper;

    public List<DepartmentInfo> findAll(){
        DepartmentInfoExample example = new DepartmentInfoExample();
        DepartmentInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();

        return departmentInfoMapper.selectByExample(example);
    }

    public List<DepartmentInfo> findByDate(Date date){
        DepartmentInfoExample example = new DepartmentInfoExample();
        DepartmentInfoExample.Criteria criteria = example.createCriteria();
        criteria.andSynchrodateGreaterThan(date);

        return departmentInfoMapper.selectByExample(example);
    }

    public int importDepartment(){
        List<DepartmentInfo> oaDepartmentInfos = new ArrayList<>();

        List<HrmSubCompany> companies = originalDepartmentMapper.selectAllHrmSubCompany();
        for (HrmSubCompany company:companies) {
            oaDepartmentInfos.add(convert(company));
        }
        List<HrmDepartment> departments = originalDepartmentMapper.selectAllHrmDepartment();
        for (HrmDepartment department:departments) {
            oaDepartmentInfos.add(convert(department));
        }

        List<DepartmentInfo> insertDepartmentInfos = new ArrayList<>();
        List<DepartmentInfo> updateDepartmentInfos = new ArrayList<>();

        for(DepartmentInfo oaNew:oaDepartmentInfos){
            DepartmentInfo existing = findByOaInfo(oaNew.getOaid(),oaNew.getOakind());
            if (existing != null){
                if (!isSameDepartmentInfo(existing,oaNew)){
                    oaNew.setId(existing.getId());
                    updateDepartmentInfos.add(oaNew);
                }
            }else{
                oaNew.setSynchrodate(new Date());
                insertDepartmentInfos.add(oaNew);
            }
        }
        int insertResult = insertDepartmentInfo(insertDepartmentInfos);
        int updateResult = updateDepartmentInfo(updateDepartmentInfos);
        return (insertResult + updateResult) == 2 ? 1 : 0;
    }

    public DepartmentInfo findByOaInfo(int id,String kind){
        DepartmentInfoExample example = new DepartmentInfoExample();
        DepartmentInfoExample.Criteria criteria = example.createCriteria();
        criteria.andOaidEqualTo(id);
        criteria.andOakindEqualTo(kind);

        List<DepartmentInfo> departmentInfos = departmentInfoMapper.selectByExample(example);
        if (departmentInfos == null || departmentInfos.size() == 0) return null;
        if (departmentInfos.size() > 1){
            logger.warn(String.format("发现复数个组织部门记录，请确认数据正确性,id:%s  kind:%s",id,kind));
        }
        return departmentInfos.get(0);
    }

    @Transactional
    public int insertDepartmentInfo(List<DepartmentInfo> departmentInfos){
        int result = 0;
        for (DepartmentInfo departmentInfo:departmentInfos) {
            result = insertDepartmentInfo(departmentInfo);
            if (result < 0) return 0;
        }
        return 1;
    }

    public int insertDepartmentInfo(DepartmentInfo departmentInfo){
        departmentInfo.setSynchrodate(new Date());
        return departmentInfoMapper.insert(departmentInfo);
    }

    @Transactional
    public int updateDepartmentInfo(List<DepartmentInfo> departmentInfos){
        int result = 0;
        for (DepartmentInfo departmentInfo:departmentInfos) {
            result = updateDepartmentInfo(departmentInfo);
            if (result < 0) return 0;
        }
        return 1;
    }

    public int updateDepartmentInfo(DepartmentInfo departmentInfo){
        departmentInfo.setSynchrodate(new Date());

        DepartmentInfoExample example = new DepartmentInfoExample();
        DepartmentInfoExample.Criteria criteria = example.createCriteria();
        criteria.andOaidEqualTo(departmentInfo.getOaid());
        criteria.andOakindEqualTo(departmentInfo.getOakind());

        return departmentInfoMapper.updateByExample(departmentInfo,example);
    }

    private DepartmentInfo convert(HrmSubCompany company){
        DepartmentInfo departmentInfo = new DepartmentInfo();
        departmentInfo.setOaid(company.getId());
        departmentInfo.setOakind("Company");
        departmentInfo.setName(company.getSubcompanyname());
        departmentInfo.setMark(company.getSubcompanydesc());
        departmentInfo.setCompanyid(company.getCompanyid());
        departmentInfo.setParentid(company.getSupsubcomid());
        departmentInfo.setCanceled(StringUtils.isEqualsString("1",company.getCanceled())? (byte)1:(byte)0);
        departmentInfo.setCode(company.getSubcompanycode());
        return departmentInfo;
    }

    private DepartmentInfo convert(HrmDepartment department){
        DepartmentInfo departmentInfo = new DepartmentInfo();
        departmentInfo.setOaid(department.getId());
        departmentInfo.setOakind("Department");
        departmentInfo.setName(department.getDepartmentname());
        departmentInfo.setMark(department.getDepartmentmark());
        departmentInfo.setCompanyid(department.getSubcompanyid1());
        departmentInfo.setParentid(department.getSupdepid());
        departmentInfo.setCanceled(StringUtils.isEqualsString("1",department.getCanceled())? (byte)1:(byte)0);
        departmentInfo.setCode(department.getDepartmentcode());
        return departmentInfo;
    }

    private boolean isSameDepartmentInfo(DepartmentInfo source,DepartmentInfo target){
        Assert.notNull(source,"DepartmentInfo is null");
        Assert.notNull(target,"DepartmentInfo is null");

        if (!StringUtils.isEqualsString(source.getName(),target.getName()))return false;
        if (!StringUtils.isEqualsString(source.getMark(),target.getMark()))return false;
        if (!(source.getCompanyid() != null && source.getCompanyid().equals(target.getCompanyid())))return false;
        if (!(source.getParentid() != null && source.getParentid().equals(target.getParentid())))return false;
        if (!(source.getCanceled() != null && source.getCanceled().equals(target.getCanceled())))return false;
        if (!StringUtils.isEqualsString(source.getCode(),target.getCode()))return false;
        return true;
    }
}