package com.sundeinfo.sypglass.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LinkExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LinkExample() {
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

        public Criteria andRandomfoldernameIsNull() {
            addCriterion("randomfoldername is null");
            return (Criteria) this;
        }

        public Criteria andRandomfoldernameIsNotNull() {
            addCriterion("randomfoldername is not null");
            return (Criteria) this;
        }

        public Criteria andRandomfoldernameEqualTo(String value) {
            addCriterion("randomfoldername =", value, "randomfoldername");
            return (Criteria) this;
        }

        public Criteria andRandomfoldernameNotEqualTo(String value) {
            addCriterion("randomfoldername <>", value, "randomfoldername");
            return (Criteria) this;
        }

        public Criteria andRandomfoldernameGreaterThan(String value) {
            addCriterion("randomfoldername >", value, "randomfoldername");
            return (Criteria) this;
        }

        public Criteria andRandomfoldernameGreaterThanOrEqualTo(String value) {
            addCriterion("randomfoldername >=", value, "randomfoldername");
            return (Criteria) this;
        }

        public Criteria andRandomfoldernameLessThan(String value) {
            addCriterion("randomfoldername <", value, "randomfoldername");
            return (Criteria) this;
        }

        public Criteria andRandomfoldernameLessThanOrEqualTo(String value) {
            addCriterion("randomfoldername <=", value, "randomfoldername");
            return (Criteria) this;
        }

        public Criteria andRandomfoldernameLike(String value) {
            addCriterion("randomfoldername like", value, "randomfoldername");
            return (Criteria) this;
        }

        public Criteria andRandomfoldernameNotLike(String value) {
            addCriterion("randomfoldername not like", value, "randomfoldername");
            return (Criteria) this;
        }

        public Criteria andRandomfoldernameIn(List<String> values) {
            addCriterion("randomfoldername in", values, "randomfoldername");
            return (Criteria) this;
        }

        public Criteria andRandomfoldernameNotIn(List<String> values) {
            addCriterion("randomfoldername not in", values, "randomfoldername");
            return (Criteria) this;
        }

        public Criteria andRandomfoldernameBetween(String value1, String value2) {
            addCriterion("randomfoldername between", value1, value2, "randomfoldername");
            return (Criteria) this;
        }

        public Criteria andRandomfoldernameNotBetween(String value1, String value2) {
            addCriterion("randomfoldername not between", value1, value2, "randomfoldername");
            return (Criteria) this;
        }

        public Criteria andFullurlIsNull() {
            addCriterion("fullurl is null");
            return (Criteria) this;
        }

        public Criteria andFullurlIsNotNull() {
            addCriterion("fullurl is not null");
            return (Criteria) this;
        }

        public Criteria andFullurlEqualTo(String value) {
            addCriterion("fullurl =", value, "fullurl");
            return (Criteria) this;
        }

        public Criteria andFullurlNotEqualTo(String value) {
            addCriterion("fullurl <>", value, "fullurl");
            return (Criteria) this;
        }

        public Criteria andFullurlGreaterThan(String value) {
            addCriterion("fullurl >", value, "fullurl");
            return (Criteria) this;
        }

        public Criteria andFullurlGreaterThanOrEqualTo(String value) {
            addCriterion("fullurl >=", value, "fullurl");
            return (Criteria) this;
        }

        public Criteria andFullurlLessThan(String value) {
            addCriterion("fullurl <", value, "fullurl");
            return (Criteria) this;
        }

        public Criteria andFullurlLessThanOrEqualTo(String value) {
            addCriterion("fullurl <=", value, "fullurl");
            return (Criteria) this;
        }

        public Criteria andFullurlLike(String value) {
            addCriterion("fullurl like", value, "fullurl");
            return (Criteria) this;
        }

        public Criteria andFullurlNotLike(String value) {
            addCriterion("fullurl not like", value, "fullurl");
            return (Criteria) this;
        }

        public Criteria andFullurlIn(List<String> values) {
            addCriterion("fullurl in", values, "fullurl");
            return (Criteria) this;
        }

        public Criteria andFullurlNotIn(List<String> values) {
            addCriterion("fullurl not in", values, "fullurl");
            return (Criteria) this;
        }

        public Criteria andFullurlBetween(String value1, String value2) {
            addCriterion("fullurl between", value1, value2, "fullurl");
            return (Criteria) this;
        }

        public Criteria andFullurlNotBetween(String value1, String value2) {
            addCriterion("fullurl not between", value1, value2, "fullurl");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andUseridIsNull() {
            addCriterion("userid is null");
            return (Criteria) this;
        }

        public Criteria andUseridIsNotNull() {
            addCriterion("userid is not null");
            return (Criteria) this;
        }

        public Criteria andUseridEqualTo(Long value) {
            addCriterion("userid =", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotEqualTo(Long value) {
            addCriterion("userid <>", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThan(Long value) {
            addCriterion("userid >", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThanOrEqualTo(Long value) {
            addCriterion("userid >=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThan(Long value) {
            addCriterion("userid <", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThanOrEqualTo(Long value) {
            addCriterion("userid <=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridIn(List<Long> values) {
            addCriterion("userid in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotIn(List<Long> values) {
            addCriterion("userid not in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridBetween(Long value1, Long value2) {
            addCriterion("userid between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotBetween(Long value1, Long value2) {
            addCriterion("userid not between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andStartdateIsNull() {
            addCriterion("startdate is null");
            return (Criteria) this;
        }

        public Criteria andStartdateIsNotNull() {
            addCriterion("startdate is not null");
            return (Criteria) this;
        }

        public Criteria andStartdateEqualTo(Date value) {
            addCriterion("startdate =", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateNotEqualTo(Date value) {
            addCriterion("startdate <>", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateGreaterThan(Date value) {
            addCriterion("startdate >", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateGreaterThanOrEqualTo(Date value) {
            addCriterion("startdate >=", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateLessThan(Date value) {
            addCriterion("startdate <", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateLessThanOrEqualTo(Date value) {
            addCriterion("startdate <=", value, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateIn(List<Date> values) {
            addCriterion("startdate in", values, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateNotIn(List<Date> values) {
            addCriterion("startdate not in", values, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateBetween(Date value1, Date value2) {
            addCriterion("startdate between", value1, value2, "startdate");
            return (Criteria) this;
        }

        public Criteria andStartdateNotBetween(Date value1, Date value2) {
            addCriterion("startdate not between", value1, value2, "startdate");
            return (Criteria) this;
        }

        public Criteria andEnddateIsNull() {
            addCriterion("enddate is null");
            return (Criteria) this;
        }

        public Criteria andEnddateIsNotNull() {
            addCriterion("enddate is not null");
            return (Criteria) this;
        }

        public Criteria andEnddateEqualTo(Date value) {
            addCriterion("enddate =", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotEqualTo(Date value) {
            addCriterion("enddate <>", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateGreaterThan(Date value) {
            addCriterion("enddate >", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateGreaterThanOrEqualTo(Date value) {
            addCriterion("enddate >=", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateLessThan(Date value) {
            addCriterion("enddate <", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateLessThanOrEqualTo(Date value) {
            addCriterion("enddate <=", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateIn(List<Date> values) {
            addCriterion("enddate in", values, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotIn(List<Date> values) {
            addCriterion("enddate not in", values, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateBetween(Date value1, Date value2) {
            addCriterion("enddate between", value1, value2, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotBetween(Date value1, Date value2) {
            addCriterion("enddate not between", value1, value2, "enddate");
            return (Criteria) this;
        }

        public Criteria andDaysIsNull() {
            addCriterion("days is null");
            return (Criteria) this;
        }

        public Criteria andDaysIsNotNull() {
            addCriterion("days is not null");
            return (Criteria) this;
        }

        public Criteria andDaysEqualTo(Integer value) {
            addCriterion("days =", value, "days");
            return (Criteria) this;
        }

        public Criteria andDaysNotEqualTo(Integer value) {
            addCriterion("days <>", value, "days");
            return (Criteria) this;
        }

        public Criteria andDaysGreaterThan(Integer value) {
            addCriterion("days >", value, "days");
            return (Criteria) this;
        }

        public Criteria andDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("days >=", value, "days");
            return (Criteria) this;
        }

        public Criteria andDaysLessThan(Integer value) {
            addCriterion("days <", value, "days");
            return (Criteria) this;
        }

        public Criteria andDaysLessThanOrEqualTo(Integer value) {
            addCriterion("days <=", value, "days");
            return (Criteria) this;
        }

        public Criteria andDaysIn(List<Integer> values) {
            addCriterion("days in", values, "days");
            return (Criteria) this;
        }

        public Criteria andDaysNotIn(List<Integer> values) {
            addCriterion("days not in", values, "days");
            return (Criteria) this;
        }

        public Criteria andDaysBetween(Integer value1, Integer value2) {
            addCriterion("days between", value1, value2, "days");
            return (Criteria) this;
        }

        public Criteria andDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("days not between", value1, value2, "days");
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

        public Criteria andDeleteuserIsNull() {
            addCriterion("deleteuser is null");
            return (Criteria) this;
        }

        public Criteria andDeleteuserIsNotNull() {
            addCriterion("deleteuser is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteuserEqualTo(Long value) {
            addCriterion("deleteuser =", value, "deleteuser");
            return (Criteria) this;
        }

        public Criteria andDeleteuserNotEqualTo(Long value) {
            addCriterion("deleteuser <>", value, "deleteuser");
            return (Criteria) this;
        }

        public Criteria andDeleteuserGreaterThan(Long value) {
            addCriterion("deleteuser >", value, "deleteuser");
            return (Criteria) this;
        }

        public Criteria andDeleteuserGreaterThanOrEqualTo(Long value) {
            addCriterion("deleteuser >=", value, "deleteuser");
            return (Criteria) this;
        }

        public Criteria andDeleteuserLessThan(Long value) {
            addCriterion("deleteuser <", value, "deleteuser");
            return (Criteria) this;
        }

        public Criteria andDeleteuserLessThanOrEqualTo(Long value) {
            addCriterion("deleteuser <=", value, "deleteuser");
            return (Criteria) this;
        }

        public Criteria andDeleteuserIn(List<Long> values) {
            addCriterion("deleteuser in", values, "deleteuser");
            return (Criteria) this;
        }

        public Criteria andDeleteuserNotIn(List<Long> values) {
            addCriterion("deleteuser not in", values, "deleteuser");
            return (Criteria) this;
        }

        public Criteria andDeleteuserBetween(Long value1, Long value2) {
            addCriterion("deleteuser between", value1, value2, "deleteuser");
            return (Criteria) this;
        }

        public Criteria andDeleteuserNotBetween(Long value1, Long value2) {
            addCriterion("deleteuser not between", value1, value2, "deleteuser");
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