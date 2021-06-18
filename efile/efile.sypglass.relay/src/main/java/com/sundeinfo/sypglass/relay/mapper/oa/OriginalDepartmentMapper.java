package com.sundeinfo.sypglass.relay.mapper.oa;

import com.sundeinfo.sypglass.relay.model.oa.HrmDepartment;
import com.sundeinfo.sypglass.relay.model.oa.HrmSubCompany;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OriginalDepartmentMapper {

    List<HrmSubCompany> selectAllHrmSubCompany();

    List<HrmDepartment> selectAllHrmDepartment();

}