package com.sundeinfo.sypglass.relay.model.relay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RelayProcessPoolExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RelayProcessPoolExample() {
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

        public Criteria andRequestidIsNull() {
            addCriterion("requestid is null");
            return (Criteria) this;
        }

        public Criteria andRequestidIsNotNull() {
            addCriterion("requestid is not null");
            return (Criteria) this;
        }

        public Criteria andRequestidEqualTo(Integer value) {
            addCriterion("requestid =", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidNotEqualTo(Integer value) {
            addCriterion("requestid <>", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidGreaterThan(Integer value) {
            addCriterion("requestid >", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidGreaterThanOrEqualTo(Integer value) {
            addCriterion("requestid >=", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidLessThan(Integer value) {
            addCriterion("requestid <", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidLessThanOrEqualTo(Integer value) {
            addCriterion("requestid <=", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidIn(List<Integer> values) {
            addCriterion("requestid in", values, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidNotIn(List<Integer> values) {
            addCriterion("requestid not in", values, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidBetween(Integer value1, Integer value2) {
            addCriterion("requestid between", value1, value2, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidNotBetween(Integer value1, Integer value2) {
            addCriterion("requestid not between", value1, value2, "requestid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidIsNull() {
            addCriterion("workflowid is null");
            return (Criteria) this;
        }

        public Criteria andWorkflowidIsNotNull() {
            addCriterion("workflowid is not null");
            return (Criteria) this;
        }

        public Criteria andWorkflowidEqualTo(Integer value) {
            addCriterion("workflowid =", value, "workflowid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidNotEqualTo(Integer value) {
            addCriterion("workflowid <>", value, "workflowid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidGreaterThan(Integer value) {
            addCriterion("workflowid >", value, "workflowid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidGreaterThanOrEqualTo(Integer value) {
            addCriterion("workflowid >=", value, "workflowid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidLessThan(Integer value) {
            addCriterion("workflowid <", value, "workflowid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidLessThanOrEqualTo(Integer value) {
            addCriterion("workflowid <=", value, "workflowid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidIn(List<Integer> values) {
            addCriterion("workflowid in", values, "workflowid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidNotIn(List<Integer> values) {
            addCriterion("workflowid not in", values, "workflowid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidBetween(Integer value1, Integer value2) {
            addCriterion("workflowid between", value1, value2, "workflowid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidNotBetween(Integer value1, Integer value2) {
            addCriterion("workflowid not between", value1, value2, "workflowid");
            return (Criteria) this;
        }

        public Criteria andRequestnameIsNull() {
            addCriterion("requestname is null");
            return (Criteria) this;
        }

        public Criteria andRequestnameIsNotNull() {
            addCriterion("requestname is not null");
            return (Criteria) this;
        }

        public Criteria andRequestnameEqualTo(String value) {
            addCriterion("requestname =", value, "requestname");
            return (Criteria) this;
        }

        public Criteria andRequestnameNotEqualTo(String value) {
            addCriterion("requestname <>", value, "requestname");
            return (Criteria) this;
        }

        public Criteria andRequestnameGreaterThan(String value) {
            addCriterion("requestname >", value, "requestname");
            return (Criteria) this;
        }

        public Criteria andRequestnameGreaterThanOrEqualTo(String value) {
            addCriterion("requestname >=", value, "requestname");
            return (Criteria) this;
        }

        public Criteria andRequestnameLessThan(String value) {
            addCriterion("requestname <", value, "requestname");
            return (Criteria) this;
        }

        public Criteria andRequestnameLessThanOrEqualTo(String value) {
            addCriterion("requestname <=", value, "requestname");
            return (Criteria) this;
        }

        public Criteria andRequestnameLike(String value) {
            addCriterion("requestname like", value, "requestname");
            return (Criteria) this;
        }

        public Criteria andRequestnameNotLike(String value) {
            addCriterion("requestname not like", value, "requestname");
            return (Criteria) this;
        }

        public Criteria andRequestnameIn(List<String> values) {
            addCriterion("requestname in", values, "requestname");
            return (Criteria) this;
        }

        public Criteria andRequestnameNotIn(List<String> values) {
            addCriterion("requestname not in", values, "requestname");
            return (Criteria) this;
        }

        public Criteria andRequestnameBetween(String value1, String value2) {
            addCriterion("requestname between", value1, value2, "requestname");
            return (Criteria) this;
        }

        public Criteria andRequestnameNotBetween(String value1, String value2) {
            addCriterion("requestname not between", value1, value2, "requestname");
            return (Criteria) this;
        }

        public Criteria andCreaterIsNull() {
            addCriterion("creater is null");
            return (Criteria) this;
        }

        public Criteria andCreaterIsNotNull() {
            addCriterion("creater is not null");
            return (Criteria) this;
        }

        public Criteria andCreaterEqualTo(String value) {
            addCriterion("creater =", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotEqualTo(String value) {
            addCriterion("creater <>", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterGreaterThan(String value) {
            addCriterion("creater >", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterGreaterThanOrEqualTo(String value) {
            addCriterion("creater >=", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLessThan(String value) {
            addCriterion("creater <", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLessThanOrEqualTo(String value) {
            addCriterion("creater <=", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLike(String value) {
            addCriterion("creater like", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotLike(String value) {
            addCriterion("creater not like", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterIn(List<String> values) {
            addCriterion("creater in", values, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotIn(List<String> values) {
            addCriterion("creater not in", values, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterBetween(String value1, String value2) {
            addCriterion("creater between", value1, value2, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotBetween(String value1, String value2) {
            addCriterion("creater not between", value1, value2, "creater");
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

        public Criteria andCreatedateEqualTo(String value) {
            addCriterion("createdate =", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotEqualTo(String value) {
            addCriterion("createdate <>", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThan(String value) {
            addCriterion("createdate >", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThanOrEqualTo(String value) {
            addCriterion("createdate >=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThan(String value) {
            addCriterion("createdate <", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThanOrEqualTo(String value) {
            addCriterion("createdate <=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLike(String value) {
            addCriterion("createdate like", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotLike(String value) {
            addCriterion("createdate not like", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateIn(List<String> values) {
            addCriterion("createdate in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotIn(List<String> values) {
            addCriterion("createdate not in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateBetween(String value1, String value2) {
            addCriterion("createdate between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotBetween(String value1, String value2) {
            addCriterion("createdate not between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createtime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createtime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(String value) {
            addCriterion("createtime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(String value) {
            addCriterion("createtime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(String value) {
            addCriterion("createtime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(String value) {
            addCriterion("createtime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(String value) {
            addCriterion("createtime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(String value) {
            addCriterion("createtime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLike(String value) {
            addCriterion("createtime like", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotLike(String value) {
            addCriterion("createtime not like", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<String> values) {
            addCriterion("createtime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<String> values) {
            addCriterion("createtime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(String value1, String value2) {
            addCriterion("createtime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(String value1, String value2) {
            addCriterion("createtime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andLastoperatorIsNull() {
            addCriterion("lastoperator is null");
            return (Criteria) this;
        }

        public Criteria andLastoperatorIsNotNull() {
            addCriterion("lastoperator is not null");
            return (Criteria) this;
        }

        public Criteria andLastoperatorEqualTo(String value) {
            addCriterion("lastoperator =", value, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorNotEqualTo(String value) {
            addCriterion("lastoperator <>", value, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorGreaterThan(String value) {
            addCriterion("lastoperator >", value, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorGreaterThanOrEqualTo(String value) {
            addCriterion("lastoperator >=", value, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorLessThan(String value) {
            addCriterion("lastoperator <", value, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorLessThanOrEqualTo(String value) {
            addCriterion("lastoperator <=", value, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorLike(String value) {
            addCriterion("lastoperator like", value, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorNotLike(String value) {
            addCriterion("lastoperator not like", value, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorIn(List<String> values) {
            addCriterion("lastoperator in", values, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorNotIn(List<String> values) {
            addCriterion("lastoperator not in", values, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorBetween(String value1, String value2) {
            addCriterion("lastoperator between", value1, value2, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorNotBetween(String value1, String value2) {
            addCriterion("lastoperator not between", value1, value2, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatedateIsNull() {
            addCriterion("lastoperatedate is null");
            return (Criteria) this;
        }

        public Criteria andLastoperatedateIsNotNull() {
            addCriterion("lastoperatedate is not null");
            return (Criteria) this;
        }

        public Criteria andLastoperatedateEqualTo(String value) {
            addCriterion("lastoperatedate =", value, "lastoperatedate");
            return (Criteria) this;
        }

        public Criteria andLastoperatedateNotEqualTo(String value) {
            addCriterion("lastoperatedate <>", value, "lastoperatedate");
            return (Criteria) this;
        }

        public Criteria andLastoperatedateGreaterThan(String value) {
            addCriterion("lastoperatedate >", value, "lastoperatedate");
            return (Criteria) this;
        }

        public Criteria andLastoperatedateGreaterThanOrEqualTo(String value) {
            addCriterion("lastoperatedate >=", value, "lastoperatedate");
            return (Criteria) this;
        }

        public Criteria andLastoperatedateLessThan(String value) {
            addCriterion("lastoperatedate <", value, "lastoperatedate");
            return (Criteria) this;
        }

        public Criteria andLastoperatedateLessThanOrEqualTo(String value) {
            addCriterion("lastoperatedate <=", value, "lastoperatedate");
            return (Criteria) this;
        }

        public Criteria andLastoperatedateLike(String value) {
            addCriterion("lastoperatedate like", value, "lastoperatedate");
            return (Criteria) this;
        }

        public Criteria andLastoperatedateNotLike(String value) {
            addCriterion("lastoperatedate not like", value, "lastoperatedate");
            return (Criteria) this;
        }

        public Criteria andLastoperatedateIn(List<String> values) {
            addCriterion("lastoperatedate in", values, "lastoperatedate");
            return (Criteria) this;
        }

        public Criteria andLastoperatedateNotIn(List<String> values) {
            addCriterion("lastoperatedate not in", values, "lastoperatedate");
            return (Criteria) this;
        }

        public Criteria andLastoperatedateBetween(String value1, String value2) {
            addCriterion("lastoperatedate between", value1, value2, "lastoperatedate");
            return (Criteria) this;
        }

        public Criteria andLastoperatedateNotBetween(String value1, String value2) {
            addCriterion("lastoperatedate not between", value1, value2, "lastoperatedate");
            return (Criteria) this;
        }

        public Criteria andLastoperatetimeIsNull() {
            addCriterion("lastoperatetime is null");
            return (Criteria) this;
        }

        public Criteria andLastoperatetimeIsNotNull() {
            addCriterion("lastoperatetime is not null");
            return (Criteria) this;
        }

        public Criteria andLastoperatetimeEqualTo(String value) {
            addCriterion("lastoperatetime =", value, "lastoperatetime");
            return (Criteria) this;
        }

        public Criteria andLastoperatetimeNotEqualTo(String value) {
            addCriterion("lastoperatetime <>", value, "lastoperatetime");
            return (Criteria) this;
        }

        public Criteria andLastoperatetimeGreaterThan(String value) {
            addCriterion("lastoperatetime >", value, "lastoperatetime");
            return (Criteria) this;
        }

        public Criteria andLastoperatetimeGreaterThanOrEqualTo(String value) {
            addCriterion("lastoperatetime >=", value, "lastoperatetime");
            return (Criteria) this;
        }

        public Criteria andLastoperatetimeLessThan(String value) {
            addCriterion("lastoperatetime <", value, "lastoperatetime");
            return (Criteria) this;
        }

        public Criteria andLastoperatetimeLessThanOrEqualTo(String value) {
            addCriterion("lastoperatetime <=", value, "lastoperatetime");
            return (Criteria) this;
        }

        public Criteria andLastoperatetimeLike(String value) {
            addCriterion("lastoperatetime like", value, "lastoperatetime");
            return (Criteria) this;
        }

        public Criteria andLastoperatetimeNotLike(String value) {
            addCriterion("lastoperatetime not like", value, "lastoperatetime");
            return (Criteria) this;
        }

        public Criteria andLastoperatetimeIn(List<String> values) {
            addCriterion("lastoperatetime in", values, "lastoperatetime");
            return (Criteria) this;
        }

        public Criteria andLastoperatetimeNotIn(List<String> values) {
            addCriterion("lastoperatetime not in", values, "lastoperatetime");
            return (Criteria) this;
        }

        public Criteria andLastoperatetimeBetween(String value1, String value2) {
            addCriterion("lastoperatetime between", value1, value2, "lastoperatetime");
            return (Criteria) this;
        }

        public Criteria andLastoperatetimeNotBetween(String value1, String value2) {
            addCriterion("lastoperatetime not between", value1, value2, "lastoperatetime");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNull() {
            addCriterion("deleted is null");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNotNull() {
            addCriterion("deleted is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedEqualTo(Byte value) {
            addCriterion("deleted =", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(Byte value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(Byte value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(Byte value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(Byte value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(Byte value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedIn(List<Byte> values) {
            addCriterion("deleted in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotIn(List<Byte> values) {
            addCriterion("deleted not in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedBetween(Byte value1, Byte value2) {
            addCriterion("deleted between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotBetween(Byte value1, Byte value2) {
            addCriterion("deleted not between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andFormcodeIsNull() {
            addCriterion("formcode is null");
            return (Criteria) this;
        }

        public Criteria andFormcodeIsNotNull() {
            addCriterion("formcode is not null");
            return (Criteria) this;
        }

        public Criteria andFormcodeEqualTo(String value) {
            addCriterion("formcode =", value, "formcode");
            return (Criteria) this;
        }

        public Criteria andFormcodeNotEqualTo(String value) {
            addCriterion("formcode <>", value, "formcode");
            return (Criteria) this;
        }

        public Criteria andFormcodeGreaterThan(String value) {
            addCriterion("formcode >", value, "formcode");
            return (Criteria) this;
        }

        public Criteria andFormcodeGreaterThanOrEqualTo(String value) {
            addCriterion("formcode >=", value, "formcode");
            return (Criteria) this;
        }

        public Criteria andFormcodeLessThan(String value) {
            addCriterion("formcode <", value, "formcode");
            return (Criteria) this;
        }

        public Criteria andFormcodeLessThanOrEqualTo(String value) {
            addCriterion("formcode <=", value, "formcode");
            return (Criteria) this;
        }

        public Criteria andFormcodeLike(String value) {
            addCriterion("formcode like", value, "formcode");
            return (Criteria) this;
        }

        public Criteria andFormcodeNotLike(String value) {
            addCriterion("formcode not like", value, "formcode");
            return (Criteria) this;
        }

        public Criteria andFormcodeIn(List<String> values) {
            addCriterion("formcode in", values, "formcode");
            return (Criteria) this;
        }

        public Criteria andFormcodeNotIn(List<String> values) {
            addCriterion("formcode not in", values, "formcode");
            return (Criteria) this;
        }

        public Criteria andFormcodeBetween(String value1, String value2) {
            addCriterion("formcode between", value1, value2, "formcode");
            return (Criteria) this;
        }

        public Criteria andFormcodeNotBetween(String value1, String value2) {
            addCriterion("formcode not between", value1, value2, "formcode");
            return (Criteria) this;
        }

        public Criteria andFormidIsNull() {
            addCriterion("formid is null");
            return (Criteria) this;
        }

        public Criteria andFormidIsNotNull() {
            addCriterion("formid is not null");
            return (Criteria) this;
        }

        public Criteria andFormidEqualTo(Long value) {
            addCriterion("formid =", value, "formid");
            return (Criteria) this;
        }

        public Criteria andFormidNotEqualTo(Long value) {
            addCriterion("formid <>", value, "formid");
            return (Criteria) this;
        }

        public Criteria andFormidGreaterThan(Long value) {
            addCriterion("formid >", value, "formid");
            return (Criteria) this;
        }

        public Criteria andFormidGreaterThanOrEqualTo(Long value) {
            addCriterion("formid >=", value, "formid");
            return (Criteria) this;
        }

        public Criteria andFormidLessThan(Long value) {
            addCriterion("formid <", value, "formid");
            return (Criteria) this;
        }

        public Criteria andFormidLessThanOrEqualTo(Long value) {
            addCriterion("formid <=", value, "formid");
            return (Criteria) this;
        }

        public Criteria andFormidIn(List<Long> values) {
            addCriterion("formid in", values, "formid");
            return (Criteria) this;
        }

        public Criteria andFormidNotIn(List<Long> values) {
            addCriterion("formid not in", values, "formid");
            return (Criteria) this;
        }

        public Criteria andFormidBetween(Long value1, Long value2) {
            addCriterion("formid between", value1, value2, "formid");
            return (Criteria) this;
        }

        public Criteria andFormidNotBetween(Long value1, Long value2) {
            addCriterion("formid not between", value1, value2, "formid");
            return (Criteria) this;
        }

        public Criteria andRequestdateIsNull() {
            addCriterion("requestdate is null");
            return (Criteria) this;
        }

        public Criteria andRequestdateIsNotNull() {
            addCriterion("requestdate is not null");
            return (Criteria) this;
        }

        public Criteria andRequestdateEqualTo(String value) {
            addCriterion("requestdate =", value, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateNotEqualTo(String value) {
            addCriterion("requestdate <>", value, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateGreaterThan(String value) {
            addCriterion("requestdate >", value, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateGreaterThanOrEqualTo(String value) {
            addCriterion("requestdate >=", value, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateLessThan(String value) {
            addCriterion("requestdate <", value, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateLessThanOrEqualTo(String value) {
            addCriterion("requestdate <=", value, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateLike(String value) {
            addCriterion("requestdate like", value, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateNotLike(String value) {
            addCriterion("requestdate not like", value, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateIn(List<String> values) {
            addCriterion("requestdate in", values, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateNotIn(List<String> values) {
            addCriterion("requestdate not in", values, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateBetween(String value1, String value2) {
            addCriterion("requestdate between", value1, value2, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateNotBetween(String value1, String value2) {
            addCriterion("requestdate not between", value1, value2, "requestdate");
            return (Criteria) this;
        }

        public Criteria andCompIsNull() {
            addCriterion("comp is null");
            return (Criteria) this;
        }

        public Criteria andCompIsNotNull() {
            addCriterion("comp is not null");
            return (Criteria) this;
        }

        public Criteria andCompEqualTo(Long value) {
            addCriterion("comp =", value, "comp");
            return (Criteria) this;
        }

        public Criteria andCompNotEqualTo(Long value) {
            addCriterion("comp <>", value, "comp");
            return (Criteria) this;
        }

        public Criteria andCompGreaterThan(Long value) {
            addCriterion("comp >", value, "comp");
            return (Criteria) this;
        }

        public Criteria andCompGreaterThanOrEqualTo(Long value) {
            addCriterion("comp >=", value, "comp");
            return (Criteria) this;
        }

        public Criteria andCompLessThan(Long value) {
            addCriterion("comp <", value, "comp");
            return (Criteria) this;
        }

        public Criteria andCompLessThanOrEqualTo(Long value) {
            addCriterion("comp <=", value, "comp");
            return (Criteria) this;
        }

        public Criteria andCompIn(List<Long> values) {
            addCriterion("comp in", values, "comp");
            return (Criteria) this;
        }

        public Criteria andCompNotIn(List<Long> values) {
            addCriterion("comp not in", values, "comp");
            return (Criteria) this;
        }

        public Criteria andCompBetween(Long value1, Long value2) {
            addCriterion("comp between", value1, value2, "comp");
            return (Criteria) this;
        }

        public Criteria andCompNotBetween(Long value1, Long value2) {
            addCriterion("comp not between", value1, value2, "comp");
            return (Criteria) this;
        }

        public Criteria andDeptIsNull() {
            addCriterion("dept is null");
            return (Criteria) this;
        }

        public Criteria andDeptIsNotNull() {
            addCriterion("dept is not null");
            return (Criteria) this;
        }

        public Criteria andDeptEqualTo(Long value) {
            addCriterion("dept =", value, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptNotEqualTo(Long value) {
            addCriterion("dept <>", value, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptGreaterThan(Long value) {
            addCriterion("dept >", value, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptGreaterThanOrEqualTo(Long value) {
            addCriterion("dept >=", value, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptLessThan(Long value) {
            addCriterion("dept <", value, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptLessThanOrEqualTo(Long value) {
            addCriterion("dept <=", value, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptIn(List<Long> values) {
            addCriterion("dept in", values, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptNotIn(List<Long> values) {
            addCriterion("dept not in", values, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptBetween(Long value1, Long value2) {
            addCriterion("dept between", value1, value2, "dept");
            return (Criteria) this;
        }

        public Criteria andDeptNotBetween(Long value1, Long value2) {
            addCriterion("dept not between", value1, value2, "dept");
            return (Criteria) this;
        }

        public Criteria andLeaderIsNull() {
            addCriterion("leader is null");
            return (Criteria) this;
        }

        public Criteria andLeaderIsNotNull() {
            addCriterion("leader is not null");
            return (Criteria) this;
        }

        public Criteria andLeaderEqualTo(Long value) {
            addCriterion("leader =", value, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderNotEqualTo(Long value) {
            addCriterion("leader <>", value, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderGreaterThan(Long value) {
            addCriterion("leader >", value, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderGreaterThanOrEqualTo(Long value) {
            addCriterion("leader >=", value, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderLessThan(Long value) {
            addCriterion("leader <", value, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderLessThanOrEqualTo(Long value) {
            addCriterion("leader <=", value, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderIn(List<Long> values) {
            addCriterion("leader in", values, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderNotIn(List<Long> values) {
            addCriterion("leader not in", values, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderBetween(Long value1, Long value2) {
            addCriterion("leader between", value1, value2, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderNotBetween(Long value1, Long value2) {
            addCriterion("leader not between", value1, value2, "leader");
            return (Criteria) this;
        }

        public Criteria andAdditional1IsNull() {
            addCriterion("additional1 is null");
            return (Criteria) this;
        }

        public Criteria andAdditional1IsNotNull() {
            addCriterion("additional1 is not null");
            return (Criteria) this;
        }

        public Criteria andAdditional1EqualTo(String value) {
            addCriterion("additional1 =", value, "additional1");
            return (Criteria) this;
        }

        public Criteria andAdditional1NotEqualTo(String value) {
            addCriterion("additional1 <>", value, "additional1");
            return (Criteria) this;
        }

        public Criteria andAdditional1GreaterThan(String value) {
            addCriterion("additional1 >", value, "additional1");
            return (Criteria) this;
        }

        public Criteria andAdditional1GreaterThanOrEqualTo(String value) {
            addCriterion("additional1 >=", value, "additional1");
            return (Criteria) this;
        }

        public Criteria andAdditional1LessThan(String value) {
            addCriterion("additional1 <", value, "additional1");
            return (Criteria) this;
        }

        public Criteria andAdditional1LessThanOrEqualTo(String value) {
            addCriterion("additional1 <=", value, "additional1");
            return (Criteria) this;
        }

        public Criteria andAdditional1Like(String value) {
            addCriterion("additional1 like", value, "additional1");
            return (Criteria) this;
        }

        public Criteria andAdditional1NotLike(String value) {
            addCriterion("additional1 not like", value, "additional1");
            return (Criteria) this;
        }

        public Criteria andAdditional1In(List<String> values) {
            addCriterion("additional1 in", values, "additional1");
            return (Criteria) this;
        }

        public Criteria andAdditional1NotIn(List<String> values) {
            addCriterion("additional1 not in", values, "additional1");
            return (Criteria) this;
        }

        public Criteria andAdditional1Between(String value1, String value2) {
            addCriterion("additional1 between", value1, value2, "additional1");
            return (Criteria) this;
        }

        public Criteria andAdditional1NotBetween(String value1, String value2) {
            addCriterion("additional1 not between", value1, value2, "additional1");
            return (Criteria) this;
        }

        public Criteria andAdditional2IsNull() {
            addCriterion("additional2 is null");
            return (Criteria) this;
        }

        public Criteria andAdditional2IsNotNull() {
            addCriterion("additional2 is not null");
            return (Criteria) this;
        }

        public Criteria andAdditional2EqualTo(String value) {
            addCriterion("additional2 =", value, "additional2");
            return (Criteria) this;
        }

        public Criteria andAdditional2NotEqualTo(String value) {
            addCriterion("additional2 <>", value, "additional2");
            return (Criteria) this;
        }

        public Criteria andAdditional2GreaterThan(String value) {
            addCriterion("additional2 >", value, "additional2");
            return (Criteria) this;
        }

        public Criteria andAdditional2GreaterThanOrEqualTo(String value) {
            addCriterion("additional2 >=", value, "additional2");
            return (Criteria) this;
        }

        public Criteria andAdditional2LessThan(String value) {
            addCriterion("additional2 <", value, "additional2");
            return (Criteria) this;
        }

        public Criteria andAdditional2LessThanOrEqualTo(String value) {
            addCriterion("additional2 <=", value, "additional2");
            return (Criteria) this;
        }

        public Criteria andAdditional2Like(String value) {
            addCriterion("additional2 like", value, "additional2");
            return (Criteria) this;
        }

        public Criteria andAdditional2NotLike(String value) {
            addCriterion("additional2 not like", value, "additional2");
            return (Criteria) this;
        }

        public Criteria andAdditional2In(List<String> values) {
            addCriterion("additional2 in", values, "additional2");
            return (Criteria) this;
        }

        public Criteria andAdditional2NotIn(List<String> values) {
            addCriterion("additional2 not in", values, "additional2");
            return (Criteria) this;
        }

        public Criteria andAdditional2Between(String value1, String value2) {
            addCriterion("additional2 between", value1, value2, "additional2");
            return (Criteria) this;
        }

        public Criteria andAdditional2NotBetween(String value1, String value2) {
            addCriterion("additional2 not between", value1, value2, "additional2");
            return (Criteria) this;
        }

        public Criteria andAdditional3IsNull() {
            addCriterion("additional3 is null");
            return (Criteria) this;
        }

        public Criteria andAdditional3IsNotNull() {
            addCriterion("additional3 is not null");
            return (Criteria) this;
        }

        public Criteria andAdditional3EqualTo(String value) {
            addCriterion("additional3 =", value, "additional3");
            return (Criteria) this;
        }

        public Criteria andAdditional3NotEqualTo(String value) {
            addCriterion("additional3 <>", value, "additional3");
            return (Criteria) this;
        }

        public Criteria andAdditional3GreaterThan(String value) {
            addCriterion("additional3 >", value, "additional3");
            return (Criteria) this;
        }

        public Criteria andAdditional3GreaterThanOrEqualTo(String value) {
            addCriterion("additional3 >=", value, "additional3");
            return (Criteria) this;
        }

        public Criteria andAdditional3LessThan(String value) {
            addCriterion("additional3 <", value, "additional3");
            return (Criteria) this;
        }

        public Criteria andAdditional3LessThanOrEqualTo(String value) {
            addCriterion("additional3 <=", value, "additional3");
            return (Criteria) this;
        }

        public Criteria andAdditional3Like(String value) {
            addCriterion("additional3 like", value, "additional3");
            return (Criteria) this;
        }

        public Criteria andAdditional3NotLike(String value) {
            addCriterion("additional3 not like", value, "additional3");
            return (Criteria) this;
        }

        public Criteria andAdditional3In(List<String> values) {
            addCriterion("additional3 in", values, "additional3");
            return (Criteria) this;
        }

        public Criteria andAdditional3NotIn(List<String> values) {
            addCriterion("additional3 not in", values, "additional3");
            return (Criteria) this;
        }

        public Criteria andAdditional3Between(String value1, String value2) {
            addCriterion("additional3 between", value1, value2, "additional3");
            return (Criteria) this;
        }

        public Criteria andAdditional3NotBetween(String value1, String value2) {
            addCriterion("additional3 not between", value1, value2, "additional3");
            return (Criteria) this;
        }

        public Criteria andDocimageidIsNull() {
            addCriterion("docimageid is null");
            return (Criteria) this;
        }

        public Criteria andDocimageidIsNotNull() {
            addCriterion("docimageid is not null");
            return (Criteria) this;
        }

        public Criteria andDocimageidEqualTo(Long value) {
            addCriterion("docimageid =", value, "docimageid");
            return (Criteria) this;
        }

        public Criteria andDocimageidNotEqualTo(Long value) {
            addCriterion("docimageid <>", value, "docimageid");
            return (Criteria) this;
        }

        public Criteria andDocimageidGreaterThan(Long value) {
            addCriterion("docimageid >", value, "docimageid");
            return (Criteria) this;
        }

        public Criteria andDocimageidGreaterThanOrEqualTo(Long value) {
            addCriterion("docimageid >=", value, "docimageid");
            return (Criteria) this;
        }

        public Criteria andDocimageidLessThan(Long value) {
            addCriterion("docimageid <", value, "docimageid");
            return (Criteria) this;
        }

        public Criteria andDocimageidLessThanOrEqualTo(Long value) {
            addCriterion("docimageid <=", value, "docimageid");
            return (Criteria) this;
        }

        public Criteria andDocimageidIn(List<Long> values) {
            addCriterion("docimageid in", values, "docimageid");
            return (Criteria) this;
        }

        public Criteria andDocimageidNotIn(List<Long> values) {
            addCriterion("docimageid not in", values, "docimageid");
            return (Criteria) this;
        }

        public Criteria andDocimageidBetween(Long value1, Long value2) {
            addCriterion("docimageid between", value1, value2, "docimageid");
            return (Criteria) this;
        }

        public Criteria andDocimageidNotBetween(Long value1, Long value2) {
            addCriterion("docimageid not between", value1, value2, "docimageid");
            return (Criteria) this;
        }

        public Criteria andDocidIsNull() {
            addCriterion("docid is null");
            return (Criteria) this;
        }

        public Criteria andDocidIsNotNull() {
            addCriterion("docid is not null");
            return (Criteria) this;
        }

        public Criteria andDocidEqualTo(Long value) {
            addCriterion("docid =", value, "docid");
            return (Criteria) this;
        }

        public Criteria andDocidNotEqualTo(Long value) {
            addCriterion("docid <>", value, "docid");
            return (Criteria) this;
        }

        public Criteria andDocidGreaterThan(Long value) {
            addCriterion("docid >", value, "docid");
            return (Criteria) this;
        }

        public Criteria andDocidGreaterThanOrEqualTo(Long value) {
            addCriterion("docid >=", value, "docid");
            return (Criteria) this;
        }

        public Criteria andDocidLessThan(Long value) {
            addCriterion("docid <", value, "docid");
            return (Criteria) this;
        }

        public Criteria andDocidLessThanOrEqualTo(Long value) {
            addCriterion("docid <=", value, "docid");
            return (Criteria) this;
        }

        public Criteria andDocidIn(List<Long> values) {
            addCriterion("docid in", values, "docid");
            return (Criteria) this;
        }

        public Criteria andDocidNotIn(List<Long> values) {
            addCriterion("docid not in", values, "docid");
            return (Criteria) this;
        }

        public Criteria andDocidBetween(Long value1, Long value2) {
            addCriterion("docid between", value1, value2, "docid");
            return (Criteria) this;
        }

        public Criteria andDocidNotBetween(Long value1, Long value2) {
            addCriterion("docid not between", value1, value2, "docid");
            return (Criteria) this;
        }

        public Criteria andImagefileidIsNull() {
            addCriterion("imagefileid is null");
            return (Criteria) this;
        }

        public Criteria andImagefileidIsNotNull() {
            addCriterion("imagefileid is not null");
            return (Criteria) this;
        }

        public Criteria andImagefileidEqualTo(Long value) {
            addCriterion("imagefileid =", value, "imagefileid");
            return (Criteria) this;
        }

        public Criteria andImagefileidNotEqualTo(Long value) {
            addCriterion("imagefileid <>", value, "imagefileid");
            return (Criteria) this;
        }

        public Criteria andImagefileidGreaterThan(Long value) {
            addCriterion("imagefileid >", value, "imagefileid");
            return (Criteria) this;
        }

        public Criteria andImagefileidGreaterThanOrEqualTo(Long value) {
            addCriterion("imagefileid >=", value, "imagefileid");
            return (Criteria) this;
        }

        public Criteria andImagefileidLessThan(Long value) {
            addCriterion("imagefileid <", value, "imagefileid");
            return (Criteria) this;
        }

        public Criteria andImagefileidLessThanOrEqualTo(Long value) {
            addCriterion("imagefileid <=", value, "imagefileid");
            return (Criteria) this;
        }

        public Criteria andImagefileidIn(List<Long> values) {
            addCriterion("imagefileid in", values, "imagefileid");
            return (Criteria) this;
        }

        public Criteria andImagefileidNotIn(List<Long> values) {
            addCriterion("imagefileid not in", values, "imagefileid");
            return (Criteria) this;
        }

        public Criteria andImagefileidBetween(Long value1, Long value2) {
            addCriterion("imagefileid between", value1, value2, "imagefileid");
            return (Criteria) this;
        }

        public Criteria andImagefileidNotBetween(Long value1, Long value2) {
            addCriterion("imagefileid not between", value1, value2, "imagefileid");
            return (Criteria) this;
        }

        public Criteria andImagefilenameIsNull() {
            addCriterion("imagefilename is null");
            return (Criteria) this;
        }

        public Criteria andImagefilenameIsNotNull() {
            addCriterion("imagefilename is not null");
            return (Criteria) this;
        }

        public Criteria andImagefilenameEqualTo(String value) {
            addCriterion("imagefilename =", value, "imagefilename");
            return (Criteria) this;
        }

        public Criteria andImagefilenameNotEqualTo(String value) {
            addCriterion("imagefilename <>", value, "imagefilename");
            return (Criteria) this;
        }

        public Criteria andImagefilenameGreaterThan(String value) {
            addCriterion("imagefilename >", value, "imagefilename");
            return (Criteria) this;
        }

        public Criteria andImagefilenameGreaterThanOrEqualTo(String value) {
            addCriterion("imagefilename >=", value, "imagefilename");
            return (Criteria) this;
        }

        public Criteria andImagefilenameLessThan(String value) {
            addCriterion("imagefilename <", value, "imagefilename");
            return (Criteria) this;
        }

        public Criteria andImagefilenameLessThanOrEqualTo(String value) {
            addCriterion("imagefilename <=", value, "imagefilename");
            return (Criteria) this;
        }

        public Criteria andImagefilenameLike(String value) {
            addCriterion("imagefilename like", value, "imagefilename");
            return (Criteria) this;
        }

        public Criteria andImagefilenameNotLike(String value) {
            addCriterion("imagefilename not like", value, "imagefilename");
            return (Criteria) this;
        }

        public Criteria andImagefilenameIn(List<String> values) {
            addCriterion("imagefilename in", values, "imagefilename");
            return (Criteria) this;
        }

        public Criteria andImagefilenameNotIn(List<String> values) {
            addCriterion("imagefilename not in", values, "imagefilename");
            return (Criteria) this;
        }

        public Criteria andImagefilenameBetween(String value1, String value2) {
            addCriterion("imagefilename between", value1, value2, "imagefilename");
            return (Criteria) this;
        }

        public Criteria andImagefilenameNotBetween(String value1, String value2) {
            addCriterion("imagefilename not between", value1, value2, "imagefilename");
            return (Criteria) this;
        }

        public Criteria andImagefiletypeIsNull() {
            addCriterion("imagefiletype is null");
            return (Criteria) this;
        }

        public Criteria andImagefiletypeIsNotNull() {
            addCriterion("imagefiletype is not null");
            return (Criteria) this;
        }

        public Criteria andImagefiletypeEqualTo(String value) {
            addCriterion("imagefiletype =", value, "imagefiletype");
            return (Criteria) this;
        }

        public Criteria andImagefiletypeNotEqualTo(String value) {
            addCriterion("imagefiletype <>", value, "imagefiletype");
            return (Criteria) this;
        }

        public Criteria andImagefiletypeGreaterThan(String value) {
            addCriterion("imagefiletype >", value, "imagefiletype");
            return (Criteria) this;
        }

        public Criteria andImagefiletypeGreaterThanOrEqualTo(String value) {
            addCriterion("imagefiletype >=", value, "imagefiletype");
            return (Criteria) this;
        }

        public Criteria andImagefiletypeLessThan(String value) {
            addCriterion("imagefiletype <", value, "imagefiletype");
            return (Criteria) this;
        }

        public Criteria andImagefiletypeLessThanOrEqualTo(String value) {
            addCriterion("imagefiletype <=", value, "imagefiletype");
            return (Criteria) this;
        }

        public Criteria andImagefiletypeLike(String value) {
            addCriterion("imagefiletype like", value, "imagefiletype");
            return (Criteria) this;
        }

        public Criteria andImagefiletypeNotLike(String value) {
            addCriterion("imagefiletype not like", value, "imagefiletype");
            return (Criteria) this;
        }

        public Criteria andImagefiletypeIn(List<String> values) {
            addCriterion("imagefiletype in", values, "imagefiletype");
            return (Criteria) this;
        }

        public Criteria andImagefiletypeNotIn(List<String> values) {
            addCriterion("imagefiletype not in", values, "imagefiletype");
            return (Criteria) this;
        }

        public Criteria andImagefiletypeBetween(String value1, String value2) {
            addCriterion("imagefiletype between", value1, value2, "imagefiletype");
            return (Criteria) this;
        }

        public Criteria andImagefiletypeNotBetween(String value1, String value2) {
            addCriterion("imagefiletype not between", value1, value2, "imagefiletype");
            return (Criteria) this;
        }

        public Criteria andVersionidIsNull() {
            addCriterion("versionid is null");
            return (Criteria) this;
        }

        public Criteria andVersionidIsNotNull() {
            addCriterion("versionid is not null");
            return (Criteria) this;
        }

        public Criteria andVersionidEqualTo(Integer value) {
            addCriterion("versionid =", value, "versionid");
            return (Criteria) this;
        }

        public Criteria andVersionidNotEqualTo(Integer value) {
            addCriterion("versionid <>", value, "versionid");
            return (Criteria) this;
        }

        public Criteria andVersionidGreaterThan(Integer value) {
            addCriterion("versionid >", value, "versionid");
            return (Criteria) this;
        }

        public Criteria andVersionidGreaterThanOrEqualTo(Integer value) {
            addCriterion("versionid >=", value, "versionid");
            return (Criteria) this;
        }

        public Criteria andVersionidLessThan(Integer value) {
            addCriterion("versionid <", value, "versionid");
            return (Criteria) this;
        }

        public Criteria andVersionidLessThanOrEqualTo(Integer value) {
            addCriterion("versionid <=", value, "versionid");
            return (Criteria) this;
        }

        public Criteria andVersionidIn(List<Integer> values) {
            addCriterion("versionid in", values, "versionid");
            return (Criteria) this;
        }

        public Criteria andVersionidNotIn(List<Integer> values) {
            addCriterion("versionid not in", values, "versionid");
            return (Criteria) this;
        }

        public Criteria andVersionidBetween(Integer value1, Integer value2) {
            addCriterion("versionid between", value1, value2, "versionid");
            return (Criteria) this;
        }

        public Criteria andVersionidNotBetween(Integer value1, Integer value2) {
            addCriterion("versionid not between", value1, value2, "versionid");
            return (Criteria) this;
        }

        public Criteria andFilerealpathIsNull() {
            addCriterion("filerealpath is null");
            return (Criteria) this;
        }

        public Criteria andFilerealpathIsNotNull() {
            addCriterion("filerealpath is not null");
            return (Criteria) this;
        }

        public Criteria andFilerealpathEqualTo(String value) {
            addCriterion("filerealpath =", value, "filerealpath");
            return (Criteria) this;
        }

        public Criteria andFilerealpathNotEqualTo(String value) {
            addCriterion("filerealpath <>", value, "filerealpath");
            return (Criteria) this;
        }

        public Criteria andFilerealpathGreaterThan(String value) {
            addCriterion("filerealpath >", value, "filerealpath");
            return (Criteria) this;
        }

        public Criteria andFilerealpathGreaterThanOrEqualTo(String value) {
            addCriterion("filerealpath >=", value, "filerealpath");
            return (Criteria) this;
        }

        public Criteria andFilerealpathLessThan(String value) {
            addCriterion("filerealpath <", value, "filerealpath");
            return (Criteria) this;
        }

        public Criteria andFilerealpathLessThanOrEqualTo(String value) {
            addCriterion("filerealpath <=", value, "filerealpath");
            return (Criteria) this;
        }

        public Criteria andFilerealpathLike(String value) {
            addCriterion("filerealpath like", value, "filerealpath");
            return (Criteria) this;
        }

        public Criteria andFilerealpathNotLike(String value) {
            addCriterion("filerealpath not like", value, "filerealpath");
            return (Criteria) this;
        }

        public Criteria andFilerealpathIn(List<String> values) {
            addCriterion("filerealpath in", values, "filerealpath");
            return (Criteria) this;
        }

        public Criteria andFilerealpathNotIn(List<String> values) {
            addCriterion("filerealpath not in", values, "filerealpath");
            return (Criteria) this;
        }

        public Criteria andFilerealpathBetween(String value1, String value2) {
            addCriterion("filerealpath between", value1, value2, "filerealpath");
            return (Criteria) this;
        }

        public Criteria andFilerealpathNotBetween(String value1, String value2) {
            addCriterion("filerealpath not between", value1, value2, "filerealpath");
            return (Criteria) this;
        }

        public Criteria andIszipIsNull() {
            addCriterion("iszip is null");
            return (Criteria) this;
        }

        public Criteria andIszipIsNotNull() {
            addCriterion("iszip is not null");
            return (Criteria) this;
        }

        public Criteria andIszipEqualTo(String value) {
            addCriterion("iszip =", value, "iszip");
            return (Criteria) this;
        }

        public Criteria andIszipNotEqualTo(String value) {
            addCriterion("iszip <>", value, "iszip");
            return (Criteria) this;
        }

        public Criteria andIszipGreaterThan(String value) {
            addCriterion("iszip >", value, "iszip");
            return (Criteria) this;
        }

        public Criteria andIszipGreaterThanOrEqualTo(String value) {
            addCriterion("iszip >=", value, "iszip");
            return (Criteria) this;
        }

        public Criteria andIszipLessThan(String value) {
            addCriterion("iszip <", value, "iszip");
            return (Criteria) this;
        }

        public Criteria andIszipLessThanOrEqualTo(String value) {
            addCriterion("iszip <=", value, "iszip");
            return (Criteria) this;
        }

        public Criteria andIszipLike(String value) {
            addCriterion("iszip like", value, "iszip");
            return (Criteria) this;
        }

        public Criteria andIszipNotLike(String value) {
            addCriterion("iszip not like", value, "iszip");
            return (Criteria) this;
        }

        public Criteria andIszipIn(List<String> values) {
            addCriterion("iszip in", values, "iszip");
            return (Criteria) this;
        }

        public Criteria andIszipNotIn(List<String> values) {
            addCriterion("iszip not in", values, "iszip");
            return (Criteria) this;
        }

        public Criteria andIszipBetween(String value1, String value2) {
            addCriterion("iszip between", value1, value2, "iszip");
            return (Criteria) this;
        }

        public Criteria andIszipNotBetween(String value1, String value2) {
            addCriterion("iszip not between", value1, value2, "iszip");
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

        public Criteria andFilesizeEqualTo(String value) {
            addCriterion("filesize =", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeNotEqualTo(String value) {
            addCriterion("filesize <>", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeGreaterThan(String value) {
            addCriterion("filesize >", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeGreaterThanOrEqualTo(String value) {
            addCriterion("filesize >=", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeLessThan(String value) {
            addCriterion("filesize <", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeLessThanOrEqualTo(String value) {
            addCriterion("filesize <=", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeLike(String value) {
            addCriterion("filesize like", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeNotLike(String value) {
            addCriterion("filesize not like", value, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeIn(List<String> values) {
            addCriterion("filesize in", values, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeNotIn(List<String> values) {
            addCriterion("filesize not in", values, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeBetween(String value1, String value2) {
            addCriterion("filesize between", value1, value2, "filesize");
            return (Criteria) this;
        }

        public Criteria andFilesizeNotBetween(String value1, String value2) {
            addCriterion("filesize not between", value1, value2, "filesize");
            return (Criteria) this;
        }

        public Criteria andRelaydateIsNull() {
            addCriterion("relaydate is null");
            return (Criteria) this;
        }

        public Criteria andRelaydateIsNotNull() {
            addCriterion("relaydate is not null");
            return (Criteria) this;
        }

        public Criteria andRelaydateEqualTo(Date value) {
            addCriterion("relaydate =", value, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaydateNotEqualTo(Date value) {
            addCriterion("relaydate <>", value, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaydateGreaterThan(Date value) {
            addCriterion("relaydate >", value, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaydateGreaterThanOrEqualTo(Date value) {
            addCriterion("relaydate >=", value, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaydateLessThan(Date value) {
            addCriterion("relaydate <", value, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaydateLessThanOrEqualTo(Date value) {
            addCriterion("relaydate <=", value, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaydateIn(List<Date> values) {
            addCriterion("relaydate in", values, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaydateNotIn(List<Date> values) {
            addCriterion("relaydate not in", values, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaydateBetween(Date value1, Date value2) {
            addCriterion("relaydate between", value1, value2, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaydateNotBetween(Date value1, Date value2) {
            addCriterion("relaydate not between", value1, value2, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaystatusIsNull() {
            addCriterion("relaystatus is null");
            return (Criteria) this;
        }

        public Criteria andRelaystatusIsNotNull() {
            addCriterion("relaystatus is not null");
            return (Criteria) this;
        }

        public Criteria andRelaystatusEqualTo(Byte value) {
            addCriterion("relaystatus =", value, "relaystatus");
            return (Criteria) this;
        }

        public Criteria andRelaystatusNotEqualTo(Byte value) {
            addCriterion("relaystatus <>", value, "relaystatus");
            return (Criteria) this;
        }

        public Criteria andRelaystatusGreaterThan(Byte value) {
            addCriterion("relaystatus >", value, "relaystatus");
            return (Criteria) this;
        }

        public Criteria andRelaystatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("relaystatus >=", value, "relaystatus");
            return (Criteria) this;
        }

        public Criteria andRelaystatusLessThan(Byte value) {
            addCriterion("relaystatus <", value, "relaystatus");
            return (Criteria) this;
        }

        public Criteria andRelaystatusLessThanOrEqualTo(Byte value) {
            addCriterion("relaystatus <=", value, "relaystatus");
            return (Criteria) this;
        }

        public Criteria andRelaystatusIn(List<Byte> values) {
            addCriterion("relaystatus in", values, "relaystatus");
            return (Criteria) this;
        }

        public Criteria andRelaystatusNotIn(List<Byte> values) {
            addCriterion("relaystatus not in", values, "relaystatus");
            return (Criteria) this;
        }

        public Criteria andRelaystatusBetween(Byte value1, Byte value2) {
            addCriterion("relaystatus between", value1, value2, "relaystatus");
            return (Criteria) this;
        }

        public Criteria andRelaystatusNotBetween(Byte value1, Byte value2) {
            addCriterion("relaystatus not between", value1, value2, "relaystatus");
            return (Criteria) this;
        }

        public Criteria andRelayfilepathIsNull() {
            addCriterion("relayfilepath is null");
            return (Criteria) this;
        }

        public Criteria andRelayfilepathIsNotNull() {
            addCriterion("relayfilepath is not null");
            return (Criteria) this;
        }

        public Criteria andRelayfilepathEqualTo(String value) {
            addCriterion("relayfilepath =", value, "relayfilepath");
            return (Criteria) this;
        }

        public Criteria andRelayfilepathNotEqualTo(String value) {
            addCriterion("relayfilepath <>", value, "relayfilepath");
            return (Criteria) this;
        }

        public Criteria andRelayfilepathGreaterThan(String value) {
            addCriterion("relayfilepath >", value, "relayfilepath");
            return (Criteria) this;
        }

        public Criteria andRelayfilepathGreaterThanOrEqualTo(String value) {
            addCriterion("relayfilepath >=", value, "relayfilepath");
            return (Criteria) this;
        }

        public Criteria andRelayfilepathLessThan(String value) {
            addCriterion("relayfilepath <", value, "relayfilepath");
            return (Criteria) this;
        }

        public Criteria andRelayfilepathLessThanOrEqualTo(String value) {
            addCriterion("relayfilepath <=", value, "relayfilepath");
            return (Criteria) this;
        }

        public Criteria andRelayfilepathLike(String value) {
            addCriterion("relayfilepath like", value, "relayfilepath");
            return (Criteria) this;
        }

        public Criteria andRelayfilepathNotLike(String value) {
            addCriterion("relayfilepath not like", value, "relayfilepath");
            return (Criteria) this;
        }

        public Criteria andRelayfilepathIn(List<String> values) {
            addCriterion("relayfilepath in", values, "relayfilepath");
            return (Criteria) this;
        }

        public Criteria andRelayfilepathNotIn(List<String> values) {
            addCriterion("relayfilepath not in", values, "relayfilepath");
            return (Criteria) this;
        }

        public Criteria andRelayfilepathBetween(String value1, String value2) {
            addCriterion("relayfilepath between", value1, value2, "relayfilepath");
            return (Criteria) this;
        }

        public Criteria andRelayfilepathNotBetween(String value1, String value2) {
            addCriterion("relayfilepath not between", value1, value2, "relayfilepath");
            return (Criteria) this;
        }

        public Criteria andRelayinfoIsNull() {
            addCriterion("relayinfo is null");
            return (Criteria) this;
        }

        public Criteria andRelayinfoIsNotNull() {
            addCriterion("relayinfo is not null");
            return (Criteria) this;
        }

        public Criteria andRelayinfoEqualTo(String value) {
            addCriterion("relayinfo =", value, "relayinfo");
            return (Criteria) this;
        }

        public Criteria andRelayinfoNotEqualTo(String value) {
            addCriterion("relayinfo <>", value, "relayinfo");
            return (Criteria) this;
        }

        public Criteria andRelayinfoGreaterThan(String value) {
            addCriterion("relayinfo >", value, "relayinfo");
            return (Criteria) this;
        }

        public Criteria andRelayinfoGreaterThanOrEqualTo(String value) {
            addCriterion("relayinfo >=", value, "relayinfo");
            return (Criteria) this;
        }

        public Criteria andRelayinfoLessThan(String value) {
            addCriterion("relayinfo <", value, "relayinfo");
            return (Criteria) this;
        }

        public Criteria andRelayinfoLessThanOrEqualTo(String value) {
            addCriterion("relayinfo <=", value, "relayinfo");
            return (Criteria) this;
        }

        public Criteria andRelayinfoLike(String value) {
            addCriterion("relayinfo like", value, "relayinfo");
            return (Criteria) this;
        }

        public Criteria andRelayinfoNotLike(String value) {
            addCriterion("relayinfo not like", value, "relayinfo");
            return (Criteria) this;
        }

        public Criteria andRelayinfoIn(List<String> values) {
            addCriterion("relayinfo in", values, "relayinfo");
            return (Criteria) this;
        }

        public Criteria andRelayinfoNotIn(List<String> values) {
            addCriterion("relayinfo not in", values, "relayinfo");
            return (Criteria) this;
        }

        public Criteria andRelayinfoBetween(String value1, String value2) {
            addCriterion("relayinfo between", value1, value2, "relayinfo");
            return (Criteria) this;
        }

        public Criteria andRelayinfoNotBetween(String value1, String value2) {
            addCriterion("relayinfo not between", value1, value2, "relayinfo");
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