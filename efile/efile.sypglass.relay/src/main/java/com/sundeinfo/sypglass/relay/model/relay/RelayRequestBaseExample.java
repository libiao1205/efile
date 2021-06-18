package com.sundeinfo.sypglass.relay.model.relay;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class RelayRequestBaseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RelayRequestBaseExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andRequestidEqualTo(Long value) {
            addCriterion("requestid =", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidNotEqualTo(Long value) {
            addCriterion("requestid <>", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidGreaterThan(Long value) {
            addCriterion("requestid >", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidGreaterThanOrEqualTo(Long value) {
            addCriterion("requestid >=", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidLessThan(Long value) {
            addCriterion("requestid <", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidLessThanOrEqualTo(Long value) {
            addCriterion("requestid <=", value, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidIn(List<Long> values) {
            addCriterion("requestid in", values, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidNotIn(List<Long> values) {
            addCriterion("requestid not in", values, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidBetween(Long value1, Long value2) {
            addCriterion("requestid between", value1, value2, "requestid");
            return (Criteria) this;
        }

        public Criteria andRequestidNotBetween(Long value1, Long value2) {
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

        public Criteria andWorkflowidEqualTo(Long value) {
            addCriterion("workflowid =", value, "workflowid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidNotEqualTo(Long value) {
            addCriterion("workflowid <>", value, "workflowid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidGreaterThan(Long value) {
            addCriterion("workflowid >", value, "workflowid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidGreaterThanOrEqualTo(Long value) {
            addCriterion("workflowid >=", value, "workflowid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidLessThan(Long value) {
            addCriterion("workflowid <", value, "workflowid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidLessThanOrEqualTo(Long value) {
            addCriterion("workflowid <=", value, "workflowid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidIn(List<Long> values) {
            addCriterion("workflowid in", values, "workflowid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidNotIn(List<Long> values) {
            addCriterion("workflowid not in", values, "workflowid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidBetween(Long value1, Long value2) {
            addCriterion("workflowid between", value1, value2, "workflowid");
            return (Criteria) this;
        }

        public Criteria andWorkflowidNotBetween(Long value1, Long value2) {
            addCriterion("workflowid not between", value1, value2, "workflowid");
            return (Criteria) this;
        }

        public Criteria andLastnodeidIsNull() {
            addCriterion("lastnodeid is null");
            return (Criteria) this;
        }

        public Criteria andLastnodeidIsNotNull() {
            addCriterion("lastnodeid is not null");
            return (Criteria) this;
        }

        public Criteria andLastnodeidEqualTo(Integer value) {
            addCriterion("lastnodeid =", value, "lastnodeid");
            return (Criteria) this;
        }

        public Criteria andLastnodeidNotEqualTo(Integer value) {
            addCriterion("lastnodeid <>", value, "lastnodeid");
            return (Criteria) this;
        }

        public Criteria andLastnodeidGreaterThan(Integer value) {
            addCriterion("lastnodeid >", value, "lastnodeid");
            return (Criteria) this;
        }

        public Criteria andLastnodeidGreaterThanOrEqualTo(Integer value) {
            addCriterion("lastnodeid >=", value, "lastnodeid");
            return (Criteria) this;
        }

        public Criteria andLastnodeidLessThan(Integer value) {
            addCriterion("lastnodeid <", value, "lastnodeid");
            return (Criteria) this;
        }

        public Criteria andLastnodeidLessThanOrEqualTo(Integer value) {
            addCriterion("lastnodeid <=", value, "lastnodeid");
            return (Criteria) this;
        }

        public Criteria andLastnodeidIn(List<Integer> values) {
            addCriterion("lastnodeid in", values, "lastnodeid");
            return (Criteria) this;
        }

        public Criteria andLastnodeidNotIn(List<Integer> values) {
            addCriterion("lastnodeid not in", values, "lastnodeid");
            return (Criteria) this;
        }

        public Criteria andLastnodeidBetween(Integer value1, Integer value2) {
            addCriterion("lastnodeid between", value1, value2, "lastnodeid");
            return (Criteria) this;
        }

        public Criteria andLastnodeidNotBetween(Integer value1, Integer value2) {
            addCriterion("lastnodeid not between", value1, value2, "lastnodeid");
            return (Criteria) this;
        }

        public Criteria andLastnodetypeIsNull() {
            addCriterion("lastnodetype is null");
            return (Criteria) this;
        }

        public Criteria andLastnodetypeIsNotNull() {
            addCriterion("lastnodetype is not null");
            return (Criteria) this;
        }

        public Criteria andLastnodetypeEqualTo(String value) {
            addCriterion("lastnodetype =", value, "lastnodetype");
            return (Criteria) this;
        }

        public Criteria andLastnodetypeNotEqualTo(String value) {
            addCriterion("lastnodetype <>", value, "lastnodetype");
            return (Criteria) this;
        }

        public Criteria andLastnodetypeGreaterThan(String value) {
            addCriterion("lastnodetype >", value, "lastnodetype");
            return (Criteria) this;
        }

        public Criteria andLastnodetypeGreaterThanOrEqualTo(String value) {
            addCriterion("lastnodetype >=", value, "lastnodetype");
            return (Criteria) this;
        }

        public Criteria andLastnodetypeLessThan(String value) {
            addCriterion("lastnodetype <", value, "lastnodetype");
            return (Criteria) this;
        }

        public Criteria andLastnodetypeLessThanOrEqualTo(String value) {
            addCriterion("lastnodetype <=", value, "lastnodetype");
            return (Criteria) this;
        }

        public Criteria andLastnodetypeLike(String value) {
            addCriterion("lastnodetype like", value, "lastnodetype");
            return (Criteria) this;
        }

        public Criteria andLastnodetypeNotLike(String value) {
            addCriterion("lastnodetype not like", value, "lastnodetype");
            return (Criteria) this;
        }

        public Criteria andLastnodetypeIn(List<String> values) {
            addCriterion("lastnodetype in", values, "lastnodetype");
            return (Criteria) this;
        }

        public Criteria andLastnodetypeNotIn(List<String> values) {
            addCriterion("lastnodetype not in", values, "lastnodetype");
            return (Criteria) this;
        }

        public Criteria andLastnodetypeBetween(String value1, String value2) {
            addCriterion("lastnodetype between", value1, value2, "lastnodetype");
            return (Criteria) this;
        }

        public Criteria andLastnodetypeNotBetween(String value1, String value2) {
            addCriterion("lastnodetype not between", value1, value2, "lastnodetype");
            return (Criteria) this;
        }

        public Criteria andCurrentnodeidIsNull() {
            addCriterion("currentnodeid is null");
            return (Criteria) this;
        }

        public Criteria andCurrentnodeidIsNotNull() {
            addCriterion("currentnodeid is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentnodeidEqualTo(Integer value) {
            addCriterion("currentnodeid =", value, "currentnodeid");
            return (Criteria) this;
        }

        public Criteria andCurrentnodeidNotEqualTo(Integer value) {
            addCriterion("currentnodeid <>", value, "currentnodeid");
            return (Criteria) this;
        }

        public Criteria andCurrentnodeidGreaterThan(Integer value) {
            addCriterion("currentnodeid >", value, "currentnodeid");
            return (Criteria) this;
        }

        public Criteria andCurrentnodeidGreaterThanOrEqualTo(Integer value) {
            addCriterion("currentnodeid >=", value, "currentnodeid");
            return (Criteria) this;
        }

        public Criteria andCurrentnodeidLessThan(Integer value) {
            addCriterion("currentnodeid <", value, "currentnodeid");
            return (Criteria) this;
        }

        public Criteria andCurrentnodeidLessThanOrEqualTo(Integer value) {
            addCriterion("currentnodeid <=", value, "currentnodeid");
            return (Criteria) this;
        }

        public Criteria andCurrentnodeidIn(List<Integer> values) {
            addCriterion("currentnodeid in", values, "currentnodeid");
            return (Criteria) this;
        }

        public Criteria andCurrentnodeidNotIn(List<Integer> values) {
            addCriterion("currentnodeid not in", values, "currentnodeid");
            return (Criteria) this;
        }

        public Criteria andCurrentnodeidBetween(Integer value1, Integer value2) {
            addCriterion("currentnodeid between", value1, value2, "currentnodeid");
            return (Criteria) this;
        }

        public Criteria andCurrentnodeidNotBetween(Integer value1, Integer value2) {
            addCriterion("currentnodeid not between", value1, value2, "currentnodeid");
            return (Criteria) this;
        }

        public Criteria andCurrentnodetypeIsNull() {
            addCriterion("currentnodetype is null");
            return (Criteria) this;
        }

        public Criteria andCurrentnodetypeIsNotNull() {
            addCriterion("currentnodetype is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentnodetypeEqualTo(String value) {
            addCriterion("currentnodetype =", value, "currentnodetype");
            return (Criteria) this;
        }

        public Criteria andCurrentnodetypeNotEqualTo(String value) {
            addCriterion("currentnodetype <>", value, "currentnodetype");
            return (Criteria) this;
        }

        public Criteria andCurrentnodetypeGreaterThan(String value) {
            addCriterion("currentnodetype >", value, "currentnodetype");
            return (Criteria) this;
        }

        public Criteria andCurrentnodetypeGreaterThanOrEqualTo(String value) {
            addCriterion("currentnodetype >=", value, "currentnodetype");
            return (Criteria) this;
        }

        public Criteria andCurrentnodetypeLessThan(String value) {
            addCriterion("currentnodetype <", value, "currentnodetype");
            return (Criteria) this;
        }

        public Criteria andCurrentnodetypeLessThanOrEqualTo(String value) {
            addCriterion("currentnodetype <=", value, "currentnodetype");
            return (Criteria) this;
        }

        public Criteria andCurrentnodetypeLike(String value) {
            addCriterion("currentnodetype like", value, "currentnodetype");
            return (Criteria) this;
        }

        public Criteria andCurrentnodetypeNotLike(String value) {
            addCriterion("currentnodetype not like", value, "currentnodetype");
            return (Criteria) this;
        }

        public Criteria andCurrentnodetypeIn(List<String> values) {
            addCriterion("currentnodetype in", values, "currentnodetype");
            return (Criteria) this;
        }

        public Criteria andCurrentnodetypeNotIn(List<String> values) {
            addCriterion("currentnodetype not in", values, "currentnodetype");
            return (Criteria) this;
        }

        public Criteria andCurrentnodetypeBetween(String value1, String value2) {
            addCriterion("currentnodetype between", value1, value2, "currentnodetype");
            return (Criteria) this;
        }

        public Criteria andCurrentnodetypeNotBetween(String value1, String value2) {
            addCriterion("currentnodetype not between", value1, value2, "currentnodetype");
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

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andPassedgroupsIsNull() {
            addCriterion("passedgroups is null");
            return (Criteria) this;
        }

        public Criteria andPassedgroupsIsNotNull() {
            addCriterion("passedgroups is not null");
            return (Criteria) this;
        }

        public Criteria andPassedgroupsEqualTo(Integer value) {
            addCriterion("passedgroups =", value, "passedgroups");
            return (Criteria) this;
        }

        public Criteria andPassedgroupsNotEqualTo(Integer value) {
            addCriterion("passedgroups <>", value, "passedgroups");
            return (Criteria) this;
        }

        public Criteria andPassedgroupsGreaterThan(Integer value) {
            addCriterion("passedgroups >", value, "passedgroups");
            return (Criteria) this;
        }

        public Criteria andPassedgroupsGreaterThanOrEqualTo(Integer value) {
            addCriterion("passedgroups >=", value, "passedgroups");
            return (Criteria) this;
        }

        public Criteria andPassedgroupsLessThan(Integer value) {
            addCriterion("passedgroups <", value, "passedgroups");
            return (Criteria) this;
        }

        public Criteria andPassedgroupsLessThanOrEqualTo(Integer value) {
            addCriterion("passedgroups <=", value, "passedgroups");
            return (Criteria) this;
        }

        public Criteria andPassedgroupsIn(List<Integer> values) {
            addCriterion("passedgroups in", values, "passedgroups");
            return (Criteria) this;
        }

        public Criteria andPassedgroupsNotIn(List<Integer> values) {
            addCriterion("passedgroups not in", values, "passedgroups");
            return (Criteria) this;
        }

        public Criteria andPassedgroupsBetween(Integer value1, Integer value2) {
            addCriterion("passedgroups between", value1, value2, "passedgroups");
            return (Criteria) this;
        }

        public Criteria andPassedgroupsNotBetween(Integer value1, Integer value2) {
            addCriterion("passedgroups not between", value1, value2, "passedgroups");
            return (Criteria) this;
        }

        public Criteria andTotalgroupsIsNull() {
            addCriterion("totalgroups is null");
            return (Criteria) this;
        }

        public Criteria andTotalgroupsIsNotNull() {
            addCriterion("totalgroups is not null");
            return (Criteria) this;
        }

        public Criteria andTotalgroupsEqualTo(Integer value) {
            addCriterion("totalgroups =", value, "totalgroups");
            return (Criteria) this;
        }

        public Criteria andTotalgroupsNotEqualTo(Integer value) {
            addCriterion("totalgroups <>", value, "totalgroups");
            return (Criteria) this;
        }

        public Criteria andTotalgroupsGreaterThan(Integer value) {
            addCriterion("totalgroups >", value, "totalgroups");
            return (Criteria) this;
        }

        public Criteria andTotalgroupsGreaterThanOrEqualTo(Integer value) {
            addCriterion("totalgroups >=", value, "totalgroups");
            return (Criteria) this;
        }

        public Criteria andTotalgroupsLessThan(Integer value) {
            addCriterion("totalgroups <", value, "totalgroups");
            return (Criteria) this;
        }

        public Criteria andTotalgroupsLessThanOrEqualTo(Integer value) {
            addCriterion("totalgroups <=", value, "totalgroups");
            return (Criteria) this;
        }

        public Criteria andTotalgroupsIn(List<Integer> values) {
            addCriterion("totalgroups in", values, "totalgroups");
            return (Criteria) this;
        }

        public Criteria andTotalgroupsNotIn(List<Integer> values) {
            addCriterion("totalgroups not in", values, "totalgroups");
            return (Criteria) this;
        }

        public Criteria andTotalgroupsBetween(Integer value1, Integer value2) {
            addCriterion("totalgroups between", value1, value2, "totalgroups");
            return (Criteria) this;
        }

        public Criteria andTotalgroupsNotBetween(Integer value1, Integer value2) {
            addCriterion("totalgroups not between", value1, value2, "totalgroups");
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

        public Criteria andCreaterEqualTo(Integer value) {
            addCriterion("creater =", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotEqualTo(Integer value) {
            addCriterion("creater <>", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterGreaterThan(Integer value) {
            addCriterion("creater >", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterGreaterThanOrEqualTo(Integer value) {
            addCriterion("creater >=", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLessThan(Integer value) {
            addCriterion("creater <", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLessThanOrEqualTo(Integer value) {
            addCriterion("creater <=", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterIn(List<Integer> values) {
            addCriterion("creater in", values, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotIn(List<Integer> values) {
            addCriterion("creater not in", values, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterBetween(Integer value1, Integer value2) {
            addCriterion("creater between", value1, value2, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotBetween(Integer value1, Integer value2) {
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

        public Criteria andLastoperatorEqualTo(Integer value) {
            addCriterion("lastoperator =", value, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorNotEqualTo(Integer value) {
            addCriterion("lastoperator <>", value, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorGreaterThan(Integer value) {
            addCriterion("lastoperator >", value, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorGreaterThanOrEqualTo(Integer value) {
            addCriterion("lastoperator >=", value, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorLessThan(Integer value) {
            addCriterion("lastoperator <", value, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorLessThanOrEqualTo(Integer value) {
            addCriterion("lastoperator <=", value, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorIn(List<Integer> values) {
            addCriterion("lastoperator in", values, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorNotIn(List<Integer> values) {
            addCriterion("lastoperator not in", values, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorBetween(Integer value1, Integer value2) {
            addCriterion("lastoperator between", value1, value2, "lastoperator");
            return (Criteria) this;
        }

        public Criteria andLastoperatorNotBetween(Integer value1, Integer value2) {
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

        public Criteria andCreatertypeIsNull() {
            addCriterion("creatertype is null");
            return (Criteria) this;
        }

        public Criteria andCreatertypeIsNotNull() {
            addCriterion("creatertype is not null");
            return (Criteria) this;
        }

        public Criteria andCreatertypeEqualTo(Integer value) {
            addCriterion("creatertype =", value, "creatertype");
            return (Criteria) this;
        }

        public Criteria andCreatertypeNotEqualTo(Integer value) {
            addCriterion("creatertype <>", value, "creatertype");
            return (Criteria) this;
        }

        public Criteria andCreatertypeGreaterThan(Integer value) {
            addCriterion("creatertype >", value, "creatertype");
            return (Criteria) this;
        }

        public Criteria andCreatertypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("creatertype >=", value, "creatertype");
            return (Criteria) this;
        }

        public Criteria andCreatertypeLessThan(Integer value) {
            addCriterion("creatertype <", value, "creatertype");
            return (Criteria) this;
        }

        public Criteria andCreatertypeLessThanOrEqualTo(Integer value) {
            addCriterion("creatertype <=", value, "creatertype");
            return (Criteria) this;
        }

        public Criteria andCreatertypeIn(List<Integer> values) {
            addCriterion("creatertype in", values, "creatertype");
            return (Criteria) this;
        }

        public Criteria andCreatertypeNotIn(List<Integer> values) {
            addCriterion("creatertype not in", values, "creatertype");
            return (Criteria) this;
        }

        public Criteria andCreatertypeBetween(Integer value1, Integer value2) {
            addCriterion("creatertype between", value1, value2, "creatertype");
            return (Criteria) this;
        }

        public Criteria andCreatertypeNotBetween(Integer value1, Integer value2) {
            addCriterion("creatertype not between", value1, value2, "creatertype");
            return (Criteria) this;
        }

        public Criteria andLastoperatortypeIsNull() {
            addCriterion("lastoperatortype is null");
            return (Criteria) this;
        }

        public Criteria andLastoperatortypeIsNotNull() {
            addCriterion("lastoperatortype is not null");
            return (Criteria) this;
        }

        public Criteria andLastoperatortypeEqualTo(Integer value) {
            addCriterion("lastoperatortype =", value, "lastoperatortype");
            return (Criteria) this;
        }

        public Criteria andLastoperatortypeNotEqualTo(Integer value) {
            addCriterion("lastoperatortype <>", value, "lastoperatortype");
            return (Criteria) this;
        }

        public Criteria andLastoperatortypeGreaterThan(Integer value) {
            addCriterion("lastoperatortype >", value, "lastoperatortype");
            return (Criteria) this;
        }

        public Criteria andLastoperatortypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("lastoperatortype >=", value, "lastoperatortype");
            return (Criteria) this;
        }

        public Criteria andLastoperatortypeLessThan(Integer value) {
            addCriterion("lastoperatortype <", value, "lastoperatortype");
            return (Criteria) this;
        }

        public Criteria andLastoperatortypeLessThanOrEqualTo(Integer value) {
            addCriterion("lastoperatortype <=", value, "lastoperatortype");
            return (Criteria) this;
        }

        public Criteria andLastoperatortypeIn(List<Integer> values) {
            addCriterion("lastoperatortype in", values, "lastoperatortype");
            return (Criteria) this;
        }

        public Criteria andLastoperatortypeNotIn(List<Integer> values) {
            addCriterion("lastoperatortype not in", values, "lastoperatortype");
            return (Criteria) this;
        }

        public Criteria andLastoperatortypeBetween(Integer value1, Integer value2) {
            addCriterion("lastoperatortype between", value1, value2, "lastoperatortype");
            return (Criteria) this;
        }

        public Criteria andLastoperatortypeNotBetween(Integer value1, Integer value2) {
            addCriterion("lastoperatortype not between", value1, value2, "lastoperatortype");
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
            addCriterionForJDBCDate("relaydate =", value, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaydateNotEqualTo(Date value) {
            addCriterionForJDBCDate("relaydate <>", value, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaydateGreaterThan(Date value) {
            addCriterionForJDBCDate("relaydate >", value, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaydateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("relaydate >=", value, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaydateLessThan(Date value) {
            addCriterionForJDBCDate("relaydate <", value, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaydateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("relaydate <=", value, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaydateIn(List<Date> values) {
            addCriterionForJDBCDate("relaydate in", values, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaydateNotIn(List<Date> values) {
            addCriterionForJDBCDate("relaydate not in", values, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaydateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("relaydate between", value1, value2, "relaydate");
            return (Criteria) this;
        }

        public Criteria andRelaydateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("relaydate not between", value1, value2, "relaydate");
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

        public Criteria andRelaymd5IsNull() {
            addCriterion("relaymd5 is null");
            return (Criteria) this;
        }

        public Criteria andRelaymd5IsNotNull() {
            addCriterion("relaymd5 is not null");
            return (Criteria) this;
        }

        public Criteria andRelaymd5EqualTo(String value) {
            addCriterion("relaymd5 =", value, "relaymd5");
            return (Criteria) this;
        }

        public Criteria andRelaymd5NotEqualTo(String value) {
            addCriterion("relaymd5 <>", value, "relaymd5");
            return (Criteria) this;
        }

        public Criteria andRelaymd5GreaterThan(String value) {
            addCriterion("relaymd5 >", value, "relaymd5");
            return (Criteria) this;
        }

        public Criteria andRelaymd5GreaterThanOrEqualTo(String value) {
            addCriterion("relaymd5 >=", value, "relaymd5");
            return (Criteria) this;
        }

        public Criteria andRelaymd5LessThan(String value) {
            addCriterion("relaymd5 <", value, "relaymd5");
            return (Criteria) this;
        }

        public Criteria andRelaymd5LessThanOrEqualTo(String value) {
            addCriterion("relaymd5 <=", value, "relaymd5");
            return (Criteria) this;
        }

        public Criteria andRelaymd5Like(String value) {
            addCriterion("relaymd5 like", value, "relaymd5");
            return (Criteria) this;
        }

        public Criteria andRelaymd5NotLike(String value) {
            addCriterion("relaymd5 not like", value, "relaymd5");
            return (Criteria) this;
        }

        public Criteria andRelaymd5In(List<String> values) {
            addCriterion("relaymd5 in", values, "relaymd5");
            return (Criteria) this;
        }

        public Criteria andRelaymd5NotIn(List<String> values) {
            addCriterion("relaymd5 not in", values, "relaymd5");
            return (Criteria) this;
        }

        public Criteria andRelaymd5Between(String value1, String value2) {
            addCriterion("relaymd5 between", value1, value2, "relaymd5");
            return (Criteria) this;
        }

        public Criteria andRelaymd5NotBetween(String value1, String value2) {
            addCriterion("relaymd5 not between", value1, value2, "relaymd5");
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