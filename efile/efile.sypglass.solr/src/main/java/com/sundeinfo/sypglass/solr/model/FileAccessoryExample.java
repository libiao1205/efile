package com.sundeinfo.sypglass.solr.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileAccessoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FileAccessoryExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andFileidIsNull() {
            addCriterion("fileid is null");
            return (Criteria) this;
        }

        public Criteria andFileidIsNotNull() {
            addCriterion("fileid is not null");
            return (Criteria) this;
        }

        public Criteria andFileidEqualTo(Long value) {
            addCriterion("fileid =", value, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidNotEqualTo(Long value) {
            addCriterion("fileid <>", value, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidGreaterThan(Long value) {
            addCriterion("fileid >", value, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidGreaterThanOrEqualTo(Long value) {
            addCriterion("fileid >=", value, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidLessThan(Long value) {
            addCriterion("fileid <", value, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidLessThanOrEqualTo(Long value) {
            addCriterion("fileid <=", value, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidIn(List<Long> values) {
            addCriterion("fileid in", values, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidNotIn(List<Long> values) {
            addCriterion("fileid not in", values, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidBetween(Long value1, Long value2) {
            addCriterion("fileid between", value1, value2, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidNotBetween(Long value1, Long value2) {
            addCriterion("fileid not between", value1, value2, "fileid");
            return (Criteria) this;
        }

        public Criteria andFolderidIsNull() {
            addCriterion("folderid is null");
            return (Criteria) this;
        }

        public Criteria andFolderidIsNotNull() {
            addCriterion("folderid is not null");
            return (Criteria) this;
        }

        public Criteria andFolderidEqualTo(Long value) {
            addCriterion("folderid =", value, "folderid");
            return (Criteria) this;
        }

        public Criteria andFolderidNotEqualTo(Long value) {
            addCriterion("folderid <>", value, "folderid");
            return (Criteria) this;
        }

        public Criteria andFolderidGreaterThan(Long value) {
            addCriterion("folderid >", value, "folderid");
            return (Criteria) this;
        }

        public Criteria andFolderidGreaterThanOrEqualTo(Long value) {
            addCriterion("folderid >=", value, "folderid");
            return (Criteria) this;
        }

        public Criteria andFolderidLessThan(Long value) {
            addCriterion("folderid <", value, "folderid");
            return (Criteria) this;
        }

        public Criteria andFolderidLessThanOrEqualTo(Long value) {
            addCriterion("folderid <=", value, "folderid");
            return (Criteria) this;
        }

        public Criteria andFolderidIn(List<Long> values) {
            addCriterion("folderid in", values, "folderid");
            return (Criteria) this;
        }

        public Criteria andFolderidNotIn(List<Long> values) {
            addCriterion("folderid not in", values, "folderid");
            return (Criteria) this;
        }

        public Criteria andFolderidBetween(Long value1, Long value2) {
            addCriterion("folderid between", value1, value2, "folderid");
            return (Criteria) this;
        }

        public Criteria andFolderidNotBetween(Long value1, Long value2) {
            addCriterion("folderid not between", value1, value2, "folderid");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andSuffixIsNull() {
            addCriterion("suffix is null");
            return (Criteria) this;
        }

        public Criteria andSuffixIsNotNull() {
            addCriterion("suffix is not null");
            return (Criteria) this;
        }

        public Criteria andSuffixEqualTo(String value) {
            addCriterion("suffix =", value, "suffix");
            return (Criteria) this;
        }

        public Criteria andSuffixNotEqualTo(String value) {
            addCriterion("suffix <>", value, "suffix");
            return (Criteria) this;
        }

        public Criteria andSuffixGreaterThan(String value) {
            addCriterion("suffix >", value, "suffix");
            return (Criteria) this;
        }

        public Criteria andSuffixGreaterThanOrEqualTo(String value) {
            addCriterion("suffix >=", value, "suffix");
            return (Criteria) this;
        }

        public Criteria andSuffixLessThan(String value) {
            addCriterion("suffix <", value, "suffix");
            return (Criteria) this;
        }

        public Criteria andSuffixLessThanOrEqualTo(String value) {
            addCriterion("suffix <=", value, "suffix");
            return (Criteria) this;
        }

        public Criteria andSuffixLike(String value) {
            addCriterion("suffix like", value, "suffix");
            return (Criteria) this;
        }

        public Criteria andSuffixNotLike(String value) {
            addCriterion("suffix not like", value, "suffix");
            return (Criteria) this;
        }

        public Criteria andSuffixIn(List<String> values) {
            addCriterion("suffix in", values, "suffix");
            return (Criteria) this;
        }

        public Criteria andSuffixNotIn(List<String> values) {
            addCriterion("suffix not in", values, "suffix");
            return (Criteria) this;
        }

        public Criteria andSuffixBetween(String value1, String value2) {
            addCriterion("suffix between", value1, value2, "suffix");
            return (Criteria) this;
        }

        public Criteria andSuffixNotBetween(String value1, String value2) {
            addCriterion("suffix not between", value1, value2, "suffix");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andSizeIsNull() {
            addCriterion("size is null");
            return (Criteria) this;
        }

        public Criteria andSizeIsNotNull() {
            addCriterion("size is not null");
            return (Criteria) this;
        }

        public Criteria andSizeEqualTo(String value) {
            addCriterion("size =", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotEqualTo(String value) {
            addCriterion("size <>", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThan(String value) {
            addCriterion("size >", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThanOrEqualTo(String value) {
            addCriterion("size >=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThan(String value) {
            addCriterion("size <", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThanOrEqualTo(String value) {
            addCriterion("size <=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLike(String value) {
            addCriterion("size like", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotLike(String value) {
            addCriterion("size not like", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeIn(List<String> values) {
            addCriterion("size in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotIn(List<String> values) {
            addCriterion("size not in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeBetween(String value1, String value2) {
            addCriterion("size between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotBetween(String value1, String value2) {
            addCriterion("size not between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andCreateuseridIsNull() {
            addCriterion("createuserid is null");
            return (Criteria) this;
        }

        public Criteria andCreateuseridIsNotNull() {
            addCriterion("createuserid is not null");
            return (Criteria) this;
        }

        public Criteria andCreateuseridEqualTo(Long value) {
            addCriterion("createuserid =", value, "createuserid");
            return (Criteria) this;
        }

        public Criteria andCreateuseridNotEqualTo(Long value) {
            addCriterion("createuserid <>", value, "createuserid");
            return (Criteria) this;
        }

        public Criteria andCreateuseridGreaterThan(Long value) {
            addCriterion("createuserid >", value, "createuserid");
            return (Criteria) this;
        }

        public Criteria andCreateuseridGreaterThanOrEqualTo(Long value) {
            addCriterion("createuserid >=", value, "createuserid");
            return (Criteria) this;
        }

        public Criteria andCreateuseridLessThan(Long value) {
            addCriterion("createuserid <", value, "createuserid");
            return (Criteria) this;
        }

        public Criteria andCreateuseridLessThanOrEqualTo(Long value) {
            addCriterion("createuserid <=", value, "createuserid");
            return (Criteria) this;
        }

        public Criteria andCreateuseridIn(List<Long> values) {
            addCriterion("createuserid in", values, "createuserid");
            return (Criteria) this;
        }

        public Criteria andCreateuseridNotIn(List<Long> values) {
            addCriterion("createuserid not in", values, "createuserid");
            return (Criteria) this;
        }

        public Criteria andCreateuseridBetween(Long value1, Long value2) {
            addCriterion("createuserid between", value1, value2, "createuserid");
            return (Criteria) this;
        }

        public Criteria andCreateuseridNotBetween(Long value1, Long value2) {
            addCriterion("createuserid not between", value1, value2, "createuserid");
            return (Criteria) this;
        }

        public Criteria andCreateusernameIsNull() {
            addCriterion("createusername is null");
            return (Criteria) this;
        }

        public Criteria andCreateusernameIsNotNull() {
            addCriterion("createusername is not null");
            return (Criteria) this;
        }

        public Criteria andCreateusernameEqualTo(String value) {
            addCriterion("createusername =", value, "createusername");
            return (Criteria) this;
        }

        public Criteria andCreateusernameNotEqualTo(String value) {
            addCriterion("createusername <>", value, "createusername");
            return (Criteria) this;
        }

        public Criteria andCreateusernameGreaterThan(String value) {
            addCriterion("createusername >", value, "createusername");
            return (Criteria) this;
        }

        public Criteria andCreateusernameGreaterThanOrEqualTo(String value) {
            addCriterion("createusername >=", value, "createusername");
            return (Criteria) this;
        }

        public Criteria andCreateusernameLessThan(String value) {
            addCriterion("createusername <", value, "createusername");
            return (Criteria) this;
        }

        public Criteria andCreateusernameLessThanOrEqualTo(String value) {
            addCriterion("createusername <=", value, "createusername");
            return (Criteria) this;
        }

        public Criteria andCreateusernameLike(String value) {
            addCriterion("createusername like", value, "createusername");
            return (Criteria) this;
        }

        public Criteria andCreateusernameNotLike(String value) {
            addCriterion("createusername not like", value, "createusername");
            return (Criteria) this;
        }

        public Criteria andCreateusernameIn(List<String> values) {
            addCriterion("createusername in", values, "createusername");
            return (Criteria) this;
        }

        public Criteria andCreateusernameNotIn(List<String> values) {
            addCriterion("createusername not in", values, "createusername");
            return (Criteria) this;
        }

        public Criteria andCreateusernameBetween(String value1, String value2) {
            addCriterion("createusername between", value1, value2, "createusername");
            return (Criteria) this;
        }

        public Criteria andCreateusernameNotBetween(String value1, String value2) {
            addCriterion("createusername not between", value1, value2, "createusername");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNull() {
            addCriterion("createdate is null");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNotNull() {
            addCriterion("createdate is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedateEqualTo(Date value) {
            addCriterion("createdate =", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotEqualTo(Date value) {
            addCriterion("createdate <>", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThan(Date value) {
            addCriterion("createdate >", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThanOrEqualTo(Date value) {
            addCriterion("createdate >=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThan(Date value) {
            addCriterion("createdate <", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThanOrEqualTo(Date value) {
            addCriterion("createdate <=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateIn(List<Date> values) {
            addCriterion("createdate in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotIn(List<Date> values) {
            addCriterion("createdate not in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateBetween(Date value1, Date value2) {
            addCriterion("createdate between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotBetween(Date value1, Date value2) {
            addCriterion("createdate not between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andLastupdateusernameIsNull() {
            addCriterion("lastupdateusername is null");
            return (Criteria) this;
        }

        public Criteria andLastupdateusernameIsNotNull() {
            addCriterion("lastupdateusername is not null");
            return (Criteria) this;
        }

        public Criteria andLastupdateusernameEqualTo(String value) {
            addCriterion("lastupdateusername =", value, "lastupdateusername");
            return (Criteria) this;
        }

        public Criteria andLastupdateusernameNotEqualTo(String value) {
            addCriterion("lastupdateusername <>", value, "lastupdateusername");
            return (Criteria) this;
        }

        public Criteria andLastupdateusernameGreaterThan(String value) {
            addCriterion("lastupdateusername >", value, "lastupdateusername");
            return (Criteria) this;
        }

        public Criteria andLastupdateusernameGreaterThanOrEqualTo(String value) {
            addCriterion("lastupdateusername >=", value, "lastupdateusername");
            return (Criteria) this;
        }

        public Criteria andLastupdateusernameLessThan(String value) {
            addCriterion("lastupdateusername <", value, "lastupdateusername");
            return (Criteria) this;
        }

        public Criteria andLastupdateusernameLessThanOrEqualTo(String value) {
            addCriterion("lastupdateusername <=", value, "lastupdateusername");
            return (Criteria) this;
        }

        public Criteria andLastupdateusernameLike(String value) {
            addCriterion("lastupdateusername like", value, "lastupdateusername");
            return (Criteria) this;
        }

        public Criteria andLastupdateusernameNotLike(String value) {
            addCriterion("lastupdateusername not like", value, "lastupdateusername");
            return (Criteria) this;
        }

        public Criteria andLastupdateusernameIn(List<String> values) {
            addCriterion("lastupdateusername in", values, "lastupdateusername");
            return (Criteria) this;
        }

        public Criteria andLastupdateusernameNotIn(List<String> values) {
            addCriterion("lastupdateusername not in", values, "lastupdateusername");
            return (Criteria) this;
        }

        public Criteria andLastupdateusernameBetween(String value1, String value2) {
            addCriterion("lastupdateusername between", value1, value2, "lastupdateusername");
            return (Criteria) this;
        }

        public Criteria andLastupdateusernameNotBetween(String value1, String value2) {
            addCriterion("lastupdateusername not between", value1, value2, "lastupdateusername");
            return (Criteria) this;
        }

        public Criteria andLastupdateuseridIsNull() {
            addCriterion("lastupdateuserid is null");
            return (Criteria) this;
        }

        public Criteria andLastupdateuseridIsNotNull() {
            addCriterion("lastupdateuserid is not null");
            return (Criteria) this;
        }

        public Criteria andLastupdateuseridEqualTo(Long value) {
            addCriterion("lastupdateuserid =", value, "lastupdateuserid");
            return (Criteria) this;
        }

        public Criteria andLastupdateuseridNotEqualTo(Long value) {
            addCriterion("lastupdateuserid <>", value, "lastupdateuserid");
            return (Criteria) this;
        }

        public Criteria andLastupdateuseridGreaterThan(Long value) {
            addCriterion("lastupdateuserid >", value, "lastupdateuserid");
            return (Criteria) this;
        }

        public Criteria andLastupdateuseridGreaterThanOrEqualTo(Long value) {
            addCriterion("lastupdateuserid >=", value, "lastupdateuserid");
            return (Criteria) this;
        }

        public Criteria andLastupdateuseridLessThan(Long value) {
            addCriterion("lastupdateuserid <", value, "lastupdateuserid");
            return (Criteria) this;
        }

        public Criteria andLastupdateuseridLessThanOrEqualTo(Long value) {
            addCriterion("lastupdateuserid <=", value, "lastupdateuserid");
            return (Criteria) this;
        }

        public Criteria andLastupdateuseridIn(List<Long> values) {
            addCriterion("lastupdateuserid in", values, "lastupdateuserid");
            return (Criteria) this;
        }

        public Criteria andLastupdateuseridNotIn(List<Long> values) {
            addCriterion("lastupdateuserid not in", values, "lastupdateuserid");
            return (Criteria) this;
        }

        public Criteria andLastupdateuseridBetween(Long value1, Long value2) {
            addCriterion("lastupdateuserid between", value1, value2, "lastupdateuserid");
            return (Criteria) this;
        }

        public Criteria andLastupdateuseridNotBetween(Long value1, Long value2) {
            addCriterion("lastupdateuserid not between", value1, value2, "lastupdateuserid");
            return (Criteria) this;
        }

        public Criteria andLastupdatedateIsNull() {
            addCriterion("lastupdatedate is null");
            return (Criteria) this;
        }

        public Criteria andLastupdatedateIsNotNull() {
            addCriterion("lastupdatedate is not null");
            return (Criteria) this;
        }

        public Criteria andLastupdatedateEqualTo(Date value) {
            addCriterion("lastupdatedate =", value, "lastupdatedate");
            return (Criteria) this;
        }

        public Criteria andLastupdatedateNotEqualTo(Date value) {
            addCriterion("lastupdatedate <>", value, "lastupdatedate");
            return (Criteria) this;
        }

        public Criteria andLastupdatedateGreaterThan(Date value) {
            addCriterion("lastupdatedate >", value, "lastupdatedate");
            return (Criteria) this;
        }

        public Criteria andLastupdatedateGreaterThanOrEqualTo(Date value) {
            addCriterion("lastupdatedate >=", value, "lastupdatedate");
            return (Criteria) this;
        }

        public Criteria andLastupdatedateLessThan(Date value) {
            addCriterion("lastupdatedate <", value, "lastupdatedate");
            return (Criteria) this;
        }

        public Criteria andLastupdatedateLessThanOrEqualTo(Date value) {
            addCriterion("lastupdatedate <=", value, "lastupdatedate");
            return (Criteria) this;
        }

        public Criteria andLastupdatedateIn(List<Date> values) {
            addCriterion("lastupdatedate in", values, "lastupdatedate");
            return (Criteria) this;
        }

        public Criteria andLastupdatedateNotIn(List<Date> values) {
            addCriterion("lastupdatedate not in", values, "lastupdatedate");
            return (Criteria) this;
        }

        public Criteria andLastupdatedateBetween(Date value1, Date value2) {
            addCriterion("lastupdatedate between", value1, value2, "lastupdatedate");
            return (Criteria) this;
        }

        public Criteria andLastupdatedateNotBetween(Date value1, Date value2) {
            addCriterion("lastupdatedate not between", value1, value2, "lastupdatedate");
            return (Criteria) this;
        }

        public Criteria andIsdeleteIsNull() {
            addCriterion("isdelete is null");
            return (Criteria) this;
        }

        public Criteria andIsdeleteIsNotNull() {
            addCriterion("isdelete is not null");
            return (Criteria) this;
        }

        public Criteria andIsdeleteEqualTo(Byte value) {
            addCriterion("isdelete =", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteNotEqualTo(Byte value) {
            addCriterion("isdelete <>", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteGreaterThan(Byte value) {
            addCriterion("isdelete >", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteGreaterThanOrEqualTo(Byte value) {
            addCriterion("isdelete >=", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteLessThan(Byte value) {
            addCriterion("isdelete <", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteLessThanOrEqualTo(Byte value) {
            addCriterion("isdelete <=", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteIn(List<Byte> values) {
            addCriterion("isdelete in", values, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteNotIn(List<Byte> values) {
            addCriterion("isdelete not in", values, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteBetween(Byte value1, Byte value2) {
            addCriterion("isdelete between", value1, value2, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteNotBetween(Byte value1, Byte value2) {
            addCriterion("isdelete not between", value1, value2, "isdelete");
            return (Criteria) this;
        }

        public Criteria andDeleteuseridIsNull() {
            addCriterion("deleteuserid is null");
            return (Criteria) this;
        }

        public Criteria andDeleteuseridIsNotNull() {
            addCriterion("deleteuserid is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteuseridEqualTo(Long value) {
            addCriterion("deleteuserid =", value, "deleteuserid");
            return (Criteria) this;
        }

        public Criteria andDeleteuseridNotEqualTo(Long value) {
            addCriterion("deleteuserid <>", value, "deleteuserid");
            return (Criteria) this;
        }

        public Criteria andDeleteuseridGreaterThan(Long value) {
            addCriterion("deleteuserid >", value, "deleteuserid");
            return (Criteria) this;
        }

        public Criteria andDeleteuseridGreaterThanOrEqualTo(Long value) {
            addCriterion("deleteuserid >=", value, "deleteuserid");
            return (Criteria) this;
        }

        public Criteria andDeleteuseridLessThan(Long value) {
            addCriterion("deleteuserid <", value, "deleteuserid");
            return (Criteria) this;
        }

        public Criteria andDeleteuseridLessThanOrEqualTo(Long value) {
            addCriterion("deleteuserid <=", value, "deleteuserid");
            return (Criteria) this;
        }

        public Criteria andDeleteuseridIn(List<Long> values) {
            addCriterion("deleteuserid in", values, "deleteuserid");
            return (Criteria) this;
        }

        public Criteria andDeleteuseridNotIn(List<Long> values) {
            addCriterion("deleteuserid not in", values, "deleteuserid");
            return (Criteria) this;
        }

        public Criteria andDeleteuseridBetween(Long value1, Long value2) {
            addCriterion("deleteuserid between", value1, value2, "deleteuserid");
            return (Criteria) this;
        }

        public Criteria andDeleteuseridNotBetween(Long value1, Long value2) {
            addCriterion("deleteuserid not between", value1, value2, "deleteuserid");
            return (Criteria) this;
        }

        public Criteria andDeleteusernameIsNull() {
            addCriterion("deleteusername is null");
            return (Criteria) this;
        }

        public Criteria andDeleteusernameIsNotNull() {
            addCriterion("deleteusername is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteusernameEqualTo(String value) {
            addCriterion("deleteusername =", value, "deleteusername");
            return (Criteria) this;
        }

        public Criteria andDeleteusernameNotEqualTo(String value) {
            addCriterion("deleteusername <>", value, "deleteusername");
            return (Criteria) this;
        }

        public Criteria andDeleteusernameGreaterThan(String value) {
            addCriterion("deleteusername >", value, "deleteusername");
            return (Criteria) this;
        }

        public Criteria andDeleteusernameGreaterThanOrEqualTo(String value) {
            addCriterion("deleteusername >=", value, "deleteusername");
            return (Criteria) this;
        }

        public Criteria andDeleteusernameLessThan(String value) {
            addCriterion("deleteusername <", value, "deleteusername");
            return (Criteria) this;
        }

        public Criteria andDeleteusernameLessThanOrEqualTo(String value) {
            addCriterion("deleteusername <=", value, "deleteusername");
            return (Criteria) this;
        }

        public Criteria andDeleteusernameLike(String value) {
            addCriterion("deleteusername like", value, "deleteusername");
            return (Criteria) this;
        }

        public Criteria andDeleteusernameNotLike(String value) {
            addCriterion("deleteusername not like", value, "deleteusername");
            return (Criteria) this;
        }

        public Criteria andDeleteusernameIn(List<String> values) {
            addCriterion("deleteusername in", values, "deleteusername");
            return (Criteria) this;
        }

        public Criteria andDeleteusernameNotIn(List<String> values) {
            addCriterion("deleteusername not in", values, "deleteusername");
            return (Criteria) this;
        }

        public Criteria andDeleteusernameBetween(String value1, String value2) {
            addCriterion("deleteusername between", value1, value2, "deleteusername");
            return (Criteria) this;
        }

        public Criteria andDeleteusernameNotBetween(String value1, String value2) {
            addCriterion("deleteusername not between", value1, value2, "deleteusername");
            return (Criteria) this;
        }

        public Criteria andDeletedateIsNull() {
            addCriterion("deletedate is null");
            return (Criteria) this;
        }

        public Criteria andDeletedateIsNotNull() {
            addCriterion("deletedate is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedateEqualTo(Date value) {
            addCriterion("deletedate =", value, "deletedate");
            return (Criteria) this;
        }

        public Criteria andDeletedateNotEqualTo(Date value) {
            addCriterion("deletedate <>", value, "deletedate");
            return (Criteria) this;
        }

        public Criteria andDeletedateGreaterThan(Date value) {
            addCriterion("deletedate >", value, "deletedate");
            return (Criteria) this;
        }

        public Criteria andDeletedateGreaterThanOrEqualTo(Date value) {
            addCriterion("deletedate >=", value, "deletedate");
            return (Criteria) this;
        }

        public Criteria andDeletedateLessThan(Date value) {
            addCriterion("deletedate <", value, "deletedate");
            return (Criteria) this;
        }

        public Criteria andDeletedateLessThanOrEqualTo(Date value) {
            addCriterion("deletedate <=", value, "deletedate");
            return (Criteria) this;
        }

        public Criteria andDeletedateIn(List<Date> values) {
            addCriterion("deletedate in", values, "deletedate");
            return (Criteria) this;
        }

        public Criteria andDeletedateNotIn(List<Date> values) {
            addCriterion("deletedate not in", values, "deletedate");
            return (Criteria) this;
        }

        public Criteria andDeletedateBetween(Date value1, Date value2) {
            addCriterion("deletedate between", value1, value2, "deletedate");
            return (Criteria) this;
        }

        public Criteria andDeletedateNotBetween(Date value1, Date value2) {
            addCriterion("deletedate not between", value1, value2, "deletedate");
            return (Criteria) this;
        }

        public Criteria andCommentIsNull() {
            addCriterion("comment is null");
            return (Criteria) this;
        }

        public Criteria andCommentIsNotNull() {
            addCriterion("comment is not null");
            return (Criteria) this;
        }

        public Criteria andCommentEqualTo(String value) {
            addCriterion("comment =", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotEqualTo(String value) {
            addCriterion("comment <>", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentGreaterThan(String value) {
            addCriterion("comment >", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentGreaterThanOrEqualTo(String value) {
            addCriterion("comment >=", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLessThan(String value) {
            addCriterion("comment <", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLessThanOrEqualTo(String value) {
            addCriterion("comment <=", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLike(String value) {
            addCriterion("comment like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotLike(String value) {
            addCriterion("comment not like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentIn(List<String> values) {
            addCriterion("comment in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotIn(List<String> values) {
            addCriterion("comment not in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentBetween(String value1, String value2) {
            addCriterion("comment between", value1, value2, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotBetween(String value1, String value2) {
            addCriterion("comment not between", value1, value2, "comment");
            return (Criteria) this;
        }

        public Criteria andSaveidIsNull() {
            addCriterion("saveid is null");
            return (Criteria) this;
        }

        public Criteria andSaveidIsNotNull() {
            addCriterion("saveid is not null");
            return (Criteria) this;
        }

        public Criteria andSaveidEqualTo(String value) {
            addCriterion("saveid =", value, "saveid");
            return (Criteria) this;
        }

        public Criteria andSaveidNotEqualTo(String value) {
            addCriterion("saveid <>", value, "saveid");
            return (Criteria) this;
        }

        public Criteria andSaveidGreaterThan(String value) {
            addCriterion("saveid >", value, "saveid");
            return (Criteria) this;
        }

        public Criteria andSaveidGreaterThanOrEqualTo(String value) {
            addCriterion("saveid >=", value, "saveid");
            return (Criteria) this;
        }

        public Criteria andSaveidLessThan(String value) {
            addCriterion("saveid <", value, "saveid");
            return (Criteria) this;
        }

        public Criteria andSaveidLessThanOrEqualTo(String value) {
            addCriterion("saveid <=", value, "saveid");
            return (Criteria) this;
        }

        public Criteria andSaveidLike(String value) {
            addCriterion("saveid like", value, "saveid");
            return (Criteria) this;
        }

        public Criteria andSaveidNotLike(String value) {
            addCriterion("saveid not like", value, "saveid");
            return (Criteria) this;
        }

        public Criteria andSaveidIn(List<String> values) {
            addCriterion("saveid in", values, "saveid");
            return (Criteria) this;
        }

        public Criteria andSaveidNotIn(List<String> values) {
            addCriterion("saveid not in", values, "saveid");
            return (Criteria) this;
        }

        public Criteria andSaveidBetween(String value1, String value2) {
            addCriterion("saveid between", value1, value2, "saveid");
            return (Criteria) this;
        }

        public Criteria andSaveidNotBetween(String value1, String value2) {
            addCriterion("saveid not between", value1, value2, "saveid");
            return (Criteria) this;
        }

        public Criteria andSavemd5IsNull() {
            addCriterion("savemd5 is null");
            return (Criteria) this;
        }

        public Criteria andSavemd5IsNotNull() {
            addCriterion("savemd5 is not null");
            return (Criteria) this;
        }

        public Criteria andSavemd5EqualTo(String value) {
            addCriterion("savemd5 =", value, "savemd5");
            return (Criteria) this;
        }

        public Criteria andSavemd5NotEqualTo(String value) {
            addCriterion("savemd5 <>", value, "savemd5");
            return (Criteria) this;
        }

        public Criteria andSavemd5GreaterThan(String value) {
            addCriterion("savemd5 >", value, "savemd5");
            return (Criteria) this;
        }

        public Criteria andSavemd5GreaterThanOrEqualTo(String value) {
            addCriterion("savemd5 >=", value, "savemd5");
            return (Criteria) this;
        }

        public Criteria andSavemd5LessThan(String value) {
            addCriterion("savemd5 <", value, "savemd5");
            return (Criteria) this;
        }

        public Criteria andSavemd5LessThanOrEqualTo(String value) {
            addCriterion("savemd5 <=", value, "savemd5");
            return (Criteria) this;
        }

        public Criteria andSavemd5Like(String value) {
            addCriterion("savemd5 like", value, "savemd5");
            return (Criteria) this;
        }

        public Criteria andSavemd5NotLike(String value) {
            addCriterion("savemd5 not like", value, "savemd5");
            return (Criteria) this;
        }

        public Criteria andSavemd5In(List<String> values) {
            addCriterion("savemd5 in", values, "savemd5");
            return (Criteria) this;
        }

        public Criteria andSavemd5NotIn(List<String> values) {
            addCriterion("savemd5 not in", values, "savemd5");
            return (Criteria) this;
        }

        public Criteria andSavemd5Between(String value1, String value2) {
            addCriterion("savemd5 between", value1, value2, "savemd5");
            return (Criteria) this;
        }

        public Criteria andSavemd5NotBetween(String value1, String value2) {
            addCriterion("savemd5 not between", value1, value2, "savemd5");
            return (Criteria) this;
        }

        public Criteria andSavelengthIsNull() {
            addCriterion("savelength is null");
            return (Criteria) this;
        }

        public Criteria andSavelengthIsNotNull() {
            addCriterion("savelength is not null");
            return (Criteria) this;
        }

        public Criteria andSavelengthEqualTo(Long value) {
            addCriterion("savelength =", value, "savelength");
            return (Criteria) this;
        }

        public Criteria andSavelengthNotEqualTo(Long value) {
            addCriterion("savelength <>", value, "savelength");
            return (Criteria) this;
        }

        public Criteria andSavelengthGreaterThan(Long value) {
            addCriterion("savelength >", value, "savelength");
            return (Criteria) this;
        }

        public Criteria andSavelengthGreaterThanOrEqualTo(Long value) {
            addCriterion("savelength >=", value, "savelength");
            return (Criteria) this;
        }

        public Criteria andSavelengthLessThan(Long value) {
            addCriterion("savelength <", value, "savelength");
            return (Criteria) this;
        }

        public Criteria andSavelengthLessThanOrEqualTo(Long value) {
            addCriterion("savelength <=", value, "savelength");
            return (Criteria) this;
        }

        public Criteria andSavelengthIn(List<Long> values) {
            addCriterion("savelength in", values, "savelength");
            return (Criteria) this;
        }

        public Criteria andSavelengthNotIn(List<Long> values) {
            addCriterion("savelength not in", values, "savelength");
            return (Criteria) this;
        }

        public Criteria andSavelengthBetween(Long value1, Long value2) {
            addCriterion("savelength between", value1, value2, "savelength");
            return (Criteria) this;
        }

        public Criteria andSavelengthNotBetween(Long value1, Long value2) {
            addCriterion("savelength not between", value1, value2, "savelength");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}