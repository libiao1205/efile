package com.sundeinfo.sypglass.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FolderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FolderExample() {
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

        public Criteria andParentidIsNull() {
            addCriterion("parentId is null");
            return (Criteria) this;
        }

        public Criteria andParentidIsNotNull() {
            addCriterion("parentId is not null");
            return (Criteria) this;
        }

        public Criteria andParentidEqualTo(Long value) {
            addCriterion("parentId =", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidNotEqualTo(Long value) {
            addCriterion("parentId <>", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidGreaterThan(Long value) {
            addCriterion("parentId >", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidGreaterThanOrEqualTo(Long value) {
            addCriterion("parentId >=", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidLessThan(Long value) {
            addCriterion("parentId <", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidLessThanOrEqualTo(Long value) {
            addCriterion("parentId <=", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidIn(List<Long> values) {
            addCriterion("parentId in", values, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidNotIn(List<Long> values) {
            addCriterion("parentId not in", values, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidBetween(Long value1, Long value2) {
            addCriterion("parentId between", value1, value2, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidNotBetween(Long value1, Long value2) {
            addCriterion("parentId not between", value1, value2, "parentid");
            return (Criteria) this;
        }

        public Criteria andSortnoIsNull() {
            addCriterion("sortno is null");
            return (Criteria) this;
        }

        public Criteria andSortnoIsNotNull() {
            addCriterion("sortno is not null");
            return (Criteria) this;
        }

        public Criteria andSortnoEqualTo(Integer value) {
            addCriterion("sortno =", value, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoNotEqualTo(Integer value) {
            addCriterion("sortno <>", value, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoGreaterThan(Integer value) {
            addCriterion("sortno >", value, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("sortno >=", value, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoLessThan(Integer value) {
            addCriterion("sortno <", value, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoLessThanOrEqualTo(Integer value) {
            addCriterion("sortno <=", value, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoIn(List<Integer> values) {
            addCriterion("sortno in", values, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoNotIn(List<Integer> values) {
            addCriterion("sortno not in", values, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoBetween(Integer value1, Integer value2) {
            addCriterion("sortno between", value1, value2, "sortno");
            return (Criteria) this;
        }

        public Criteria andSortnoNotBetween(Integer value1, Integer value2) {
            addCriterion("sortno not between", value1, value2, "sortno");
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

        public Criteria andParentpathIsNull() {
            addCriterion("parentpath is null");
            return (Criteria) this;
        }

        public Criteria andParentpathIsNotNull() {
            addCriterion("parentpath is not null");
            return (Criteria) this;
        }

        public Criteria andParentpathEqualTo(String value) {
            addCriterion("parentpath =", value, "parentpath");
            return (Criteria) this;
        }

        public Criteria andParentpathNotEqualTo(String value) {
            addCriterion("parentpath <>", value, "parentpath");
            return (Criteria) this;
        }

        public Criteria andParentpathGreaterThan(String value) {
            addCriterion("parentpath >", value, "parentpath");
            return (Criteria) this;
        }

        public Criteria andParentpathGreaterThanOrEqualTo(String value) {
            addCriterion("parentpath >=", value, "parentpath");
            return (Criteria) this;
        }

        public Criteria andParentpathLessThan(String value) {
            addCriterion("parentpath <", value, "parentpath");
            return (Criteria) this;
        }

        public Criteria andParentpathLessThanOrEqualTo(String value) {
            addCriterion("parentpath <=", value, "parentpath");
            return (Criteria) this;
        }

        public Criteria andParentpathLike(String value) {
            addCriterion("parentpath like", value, "parentpath");
            return (Criteria) this;
        }

        public Criteria andParentpathNotLike(String value) {
            addCriterion("parentpath not like", value, "parentpath");
            return (Criteria) this;
        }

        public Criteria andParentpathIn(List<String> values) {
            addCriterion("parentpath in", values, "parentpath");
            return (Criteria) this;
        }

        public Criteria andParentpathNotIn(List<String> values) {
            addCriterion("parentpath not in", values, "parentpath");
            return (Criteria) this;
        }

        public Criteria andParentpathBetween(String value1, String value2) {
            addCriterion("parentpath between", value1, value2, "parentpath");
            return (Criteria) this;
        }

        public Criteria andParentpathNotBetween(String value1, String value2) {
            addCriterion("parentpath not between", value1, value2, "parentpath");
            return (Criteria) this;
        }

        public Criteria andParentpathnameIsNull() {
            addCriterion("parentpathname is null");
            return (Criteria) this;
        }

        public Criteria andParentpathnameIsNotNull() {
            addCriterion("parentpathname is not null");
            return (Criteria) this;
        }

        public Criteria andParentpathnameEqualTo(String value) {
            addCriterion("parentpathname =", value, "parentpathname");
            return (Criteria) this;
        }

        public Criteria andParentpathnameNotEqualTo(String value) {
            addCriterion("parentpathname <>", value, "parentpathname");
            return (Criteria) this;
        }

        public Criteria andParentpathnameGreaterThan(String value) {
            addCriterion("parentpathname >", value, "parentpathname");
            return (Criteria) this;
        }

        public Criteria andParentpathnameGreaterThanOrEqualTo(String value) {
            addCriterion("parentpathname >=", value, "parentpathname");
            return (Criteria) this;
        }

        public Criteria andParentpathnameLessThan(String value) {
            addCriterion("parentpathname <", value, "parentpathname");
            return (Criteria) this;
        }

        public Criteria andParentpathnameLessThanOrEqualTo(String value) {
            addCriterion("parentpathname <=", value, "parentpathname");
            return (Criteria) this;
        }

        public Criteria andParentpathnameLike(String value) {
            addCriterion("parentpathname like", value, "parentpathname");
            return (Criteria) this;
        }

        public Criteria andParentpathnameNotLike(String value) {
            addCriterion("parentpathname not like", value, "parentpathname");
            return (Criteria) this;
        }

        public Criteria andParentpathnameIn(List<String> values) {
            addCriterion("parentpathname in", values, "parentpathname");
            return (Criteria) this;
        }

        public Criteria andParentpathnameNotIn(List<String> values) {
            addCriterion("parentpathname not in", values, "parentpathname");
            return (Criteria) this;
        }

        public Criteria andParentpathnameBetween(String value1, String value2) {
            addCriterion("parentpathname between", value1, value2, "parentpathname");
            return (Criteria) this;
        }

        public Criteria andParentpathnameNotBetween(String value1, String value2) {
            addCriterion("parentpathname not between", value1, value2, "parentpathname");
            return (Criteria) this;
        }

        public Criteria andFilesizeIsNull() {
            addCriterion("filesize is null");
            return (Criteria) this;
        }

        public Criteria andFilesizeIsNotNull() {
            addCriterion("filesize is not null");
            return (Criteria) this;
        }

        public Criteria andFilesizeEqualTo(Integer value) {
            addCriterion("filesize =", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeNotEqualTo(Integer value) {
            addCriterion("filesize <>", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeGreaterThan(Integer value) {
            addCriterion("filesize >", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("filesize >=", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeLessThan(Integer value) {
            addCriterion("filesize <", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeLessThanOrEqualTo(Integer value) {
            addCriterion("filesize <=", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeIn(List<Integer> values) {
            addCriterion("filesize in", values, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeNotIn(List<Integer> values) {
            addCriterion("filesize not in", values, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeBetween(Integer value1, Integer value2) {
            addCriterion("filesize between", value1, value2, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeNotBetween(Integer value1, Integer value2) {
            addCriterion("filesize not between", value1, value2, "filesize");
            return (Criteria) this;
        }

        public Criteria andMaxsizeIsNull() {
            addCriterion("maxsize is null");
            return (Criteria) this;
        }

        public Criteria andMaxsizeIsNotNull() {
            addCriterion("maxsize is not null");
            return (Criteria) this;
        }

        public Criteria andMaxsizeEqualTo(Integer value) {
            addCriterion("maxsize =", value, "maxsize");
            return (Criteria) this;
        }

        public Criteria andMaxsizeNotEqualTo(Integer value) {
            addCriterion("maxsize <>", value, "maxsize");
            return (Criteria) this;
        }

        public Criteria andMaxsizeGreaterThan(Integer value) {
            addCriterion("maxsize >", value, "maxsize");
            return (Criteria) this;
        }

        public Criteria andMaxsizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("maxsize >=", value, "maxsize");
            return (Criteria) this;
        }

        public Criteria andMaxsizeLessThan(Integer value) {
            addCriterion("maxsize <", value, "maxsize");
            return (Criteria) this;
        }

        public Criteria andMaxsizeLessThanOrEqualTo(Integer value) {
            addCriterion("maxsize <=", value, "maxsize");
            return (Criteria) this;
        }

        public Criteria andMaxsizeIn(List<Integer> values) {
            addCriterion("maxsize in", values, "maxsize");
            return (Criteria) this;
        }

        public Criteria andMaxsizeNotIn(List<Integer> values) {
            addCriterion("maxsize not in", values, "maxsize");
            return (Criteria) this;
        }

        public Criteria andMaxsizeBetween(Integer value1, Integer value2) {
            addCriterion("maxsize between", value1, value2, "maxsize");
            return (Criteria) this;
        }

        public Criteria andMaxsizeNotBetween(Integer value1, Integer value2) {
            addCriterion("maxsize not between", value1, value2, "maxsize");
            return (Criteria) this;
        }

        public Criteria andOaworkflowidIsNull() {
            addCriterion("oaworkflowid is null");
            return (Criteria) this;
        }

        public Criteria andOaworkflowidIsNotNull() {
            addCriterion("oaworkflowid is not null");
            return (Criteria) this;
        }

        public Criteria andOaworkflowidEqualTo(Long value) {
            addCriterion("oaworkflowid =", value, "oaworkflowid");
            return (Criteria) this;
        }

        public Criteria andOaworkflowidNotEqualTo(Long value) {
            addCriterion("oaworkflowid <>", value, "oaworkflowid");
            return (Criteria) this;
        }

        public Criteria andOaworkflowidGreaterThan(Long value) {
            addCriterion("oaworkflowid >", value, "oaworkflowid");
            return (Criteria) this;
        }

        public Criteria andOaworkflowidGreaterThanOrEqualTo(Long value) {
            addCriterion("oaworkflowid >=", value, "oaworkflowid");
            return (Criteria) this;
        }

        public Criteria andOaworkflowidLessThan(Long value) {
            addCriterion("oaworkflowid <", value, "oaworkflowid");
            return (Criteria) this;
        }

        public Criteria andOaworkflowidLessThanOrEqualTo(Long value) {
            addCriterion("oaworkflowid <=", value, "oaworkflowid");
            return (Criteria) this;
        }

        public Criteria andOaworkflowidIn(List<Long> values) {
            addCriterion("oaworkflowid in", values, "oaworkflowid");
            return (Criteria) this;
        }

        public Criteria andOaworkflowidNotIn(List<Long> values) {
            addCriterion("oaworkflowid not in", values, "oaworkflowid");
            return (Criteria) this;
        }

        public Criteria andOaworkflowidBetween(Long value1, Long value2) {
            addCriterion("oaworkflowid between", value1, value2, "oaworkflowid");
            return (Criteria) this;
        }

        public Criteria andOaworkflowidNotBetween(Long value1, Long value2) {
            addCriterion("oaworkflowid not between", value1, value2, "oaworkflowid");
            return (Criteria) this;
        }

        public Criteria andOaworkflowinfoIsNull() {
            addCriterion("oaworkflowinfo is null");
            return (Criteria) this;
        }

        public Criteria andOaworkflowinfoIsNotNull() {
            addCriterion("oaworkflowinfo is not null");
            return (Criteria) this;
        }

        public Criteria andOaworkflowinfoEqualTo(String value) {
            addCriterion("oaworkflowinfo =", value, "oaworkflowinfo");
            return (Criteria) this;
        }

        public Criteria andOaworkflowinfoNotEqualTo(String value) {
            addCriterion("oaworkflowinfo <>", value, "oaworkflowinfo");
            return (Criteria) this;
        }

        public Criteria andOaworkflowinfoGreaterThan(String value) {
            addCriterion("oaworkflowinfo >", value, "oaworkflowinfo");
            return (Criteria) this;
        }

        public Criteria andOaworkflowinfoGreaterThanOrEqualTo(String value) {
            addCriterion("oaworkflowinfo >=", value, "oaworkflowinfo");
            return (Criteria) this;
        }

        public Criteria andOaworkflowinfoLessThan(String value) {
            addCriterion("oaworkflowinfo <", value, "oaworkflowinfo");
            return (Criteria) this;
        }

        public Criteria andOaworkflowinfoLessThanOrEqualTo(String value) {
            addCriterion("oaworkflowinfo <=", value, "oaworkflowinfo");
            return (Criteria) this;
        }

        public Criteria andOaworkflowinfoLike(String value) {
            addCriterion("oaworkflowinfo like", value, "oaworkflowinfo");
            return (Criteria) this;
        }

        public Criteria andOaworkflowinfoNotLike(String value) {
            addCriterion("oaworkflowinfo not like", value, "oaworkflowinfo");
            return (Criteria) this;
        }

        public Criteria andOaworkflowinfoIn(List<String> values) {
            addCriterion("oaworkflowinfo in", values, "oaworkflowinfo");
            return (Criteria) this;
        }

        public Criteria andOaworkflowinfoNotIn(List<String> values) {
            addCriterion("oaworkflowinfo not in", values, "oaworkflowinfo");
            return (Criteria) this;
        }

        public Criteria andOaworkflowinfoBetween(String value1, String value2) {
            addCriterion("oaworkflowinfo between", value1, value2, "oaworkflowinfo");
            return (Criteria) this;
        }

        public Criteria andOaworkflowinfoNotBetween(String value1, String value2) {
            addCriterion("oaworkflowinfo not between", value1, value2, "oaworkflowinfo");
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