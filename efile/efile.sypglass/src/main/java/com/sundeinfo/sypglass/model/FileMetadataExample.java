package com.sundeinfo.sypglass.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileMetadataExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FileMetadataExample() {
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

        public Criteria andMetadataidIsNull() {
            addCriterion("metadataid is null");
            return (Criteria) this;
        }

        public Criteria andMetadataidIsNotNull() {
            addCriterion("metadataid is not null");
            return (Criteria) this;
        }

        public Criteria andMetadataidEqualTo(Long value) {
            addCriterion("metadataid =", value, "metadataid");
            return (Criteria) this;
        }

        public Criteria andMetadataidNotEqualTo(Long value) {
            addCriterion("metadataid <>", value, "metadataid");
            return (Criteria) this;
        }

        public Criteria andMetadataidGreaterThan(Long value) {
            addCriterion("metadataid >", value, "metadataid");
            return (Criteria) this;
        }

        public Criteria andMetadataidGreaterThanOrEqualTo(Long value) {
            addCriterion("metadataid >=", value, "metadataid");
            return (Criteria) this;
        }

        public Criteria andMetadataidLessThan(Long value) {
            addCriterion("metadataid <", value, "metadataid");
            return (Criteria) this;
        }

        public Criteria andMetadataidLessThanOrEqualTo(Long value) {
            addCriterion("metadataid <=", value, "metadataid");
            return (Criteria) this;
        }

        public Criteria andMetadataidIn(List<Long> values) {
            addCriterion("metadataid in", values, "metadataid");
            return (Criteria) this;
        }

        public Criteria andMetadataidNotIn(List<Long> values) {
            addCriterion("metadataid not in", values, "metadataid");
            return (Criteria) this;
        }

        public Criteria andMetadataidBetween(Long value1, Long value2) {
            addCriterion("metadataid between", value1, value2, "metadataid");
            return (Criteria) this;
        }

        public Criteria andMetadataidNotBetween(Long value1, Long value2) {
            addCriterion("metadataid not between", value1, value2, "metadataid");
            return (Criteria) this;
        }

        public Criteria andValueIsNull() {
            addCriterion("value is null");
            return (Criteria) this;
        }

        public Criteria andValueIsNotNull() {
            addCriterion("value is not null");
            return (Criteria) this;
        }

        public Criteria andValueEqualTo(String value) {
            addCriterion("value =", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotEqualTo(String value) {
            addCriterion("value <>", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueGreaterThan(String value) {
            addCriterion("value >", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueGreaterThanOrEqualTo(String value) {
            addCriterion("value >=", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueLessThan(String value) {
            addCriterion("value <", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueLessThanOrEqualTo(String value) {
            addCriterion("value <=", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueLike(String value) {
            addCriterion("value like", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotLike(String value) {
            addCriterion("value not like", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueIn(List<String> values) {
            addCriterion("value in", values, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotIn(List<String> values) {
            addCriterion("value not in", values, "value");
            return (Criteria) this;
        }

        public Criteria andValueBetween(String value1, String value2) {
            addCriterion("value between", value1, value2, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotBetween(String value1, String value2) {
            addCriterion("value not between", value1, value2, "value");
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