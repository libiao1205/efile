package com.sundeinfo.sypglass.model;

import java.util.ArrayList;
import java.util.List;

public class FolderSettingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FolderSettingExample() {
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

        public Criteria andWorkflownameIsNull() {
            addCriterion("workflowname is null");
            return (Criteria) this;
        }

        public Criteria andWorkflownameIsNotNull() {
            addCriterion("workflowname is not null");
            return (Criteria) this;
        }

        public Criteria andWorkflownameEqualTo(String value) {
            addCriterion("workflowname =", value, "workflowname");
            return (Criteria) this;
        }

        public Criteria andWorkflownameNotEqualTo(String value) {
            addCriterion("workflowname <>", value, "workflowname");
            return (Criteria) this;
        }

        public Criteria andWorkflownameGreaterThan(String value) {
            addCriterion("workflowname >", value, "workflowname");
            return (Criteria) this;
        }

        public Criteria andWorkflownameGreaterThanOrEqualTo(String value) {
            addCriterion("workflowname >=", value, "workflowname");
            return (Criteria) this;
        }

        public Criteria andWorkflownameLessThan(String value) {
            addCriterion("workflowname <", value, "workflowname");
            return (Criteria) this;
        }

        public Criteria andWorkflownameLessThanOrEqualTo(String value) {
            addCriterion("workflowname <=", value, "workflowname");
            return (Criteria) this;
        }

        public Criteria andWorkflownameLike(String value) {
            addCriterion("workflowname like", value, "workflowname");
            return (Criteria) this;
        }

        public Criteria andWorkflownameNotLike(String value) {
            addCriterion("workflowname not like", value, "workflowname");
            return (Criteria) this;
        }

        public Criteria andWorkflownameIn(List<String> values) {
            addCriterion("workflowname in", values, "workflowname");
            return (Criteria) this;
        }

        public Criteria andWorkflownameNotIn(List<String> values) {
            addCriterion("workflowname not in", values, "workflowname");
            return (Criteria) this;
        }

        public Criteria andWorkflownameBetween(String value1, String value2) {
            addCriterion("workflowname between", value1, value2, "workflowname");
            return (Criteria) this;
        }

        public Criteria andWorkflownameNotBetween(String value1, String value2) {
            addCriterion("workflowname not between", value1, value2, "workflowname");
            return (Criteria) this;
        }

        public Criteria andFirstdictcodeIsNull() {
            addCriterion("firstdictcode is null");
            return (Criteria) this;
        }

        public Criteria andFirstdictcodeIsNotNull() {
            addCriterion("firstdictcode is not null");
            return (Criteria) this;
        }

        public Criteria andFirstdictcodeEqualTo(String value) {
            addCriterion("firstdictcode =", value, "firstdictcode");
            return (Criteria) this;
        }

        public Criteria andFirstdictcodeNotEqualTo(String value) {
            addCriterion("firstdictcode <>", value, "firstdictcode");
            return (Criteria) this;
        }

        public Criteria andFirstdictcodeGreaterThan(String value) {
            addCriterion("firstdictcode >", value, "firstdictcode");
            return (Criteria) this;
        }

        public Criteria andFirstdictcodeGreaterThanOrEqualTo(String value) {
            addCriterion("firstdictcode >=", value, "firstdictcode");
            return (Criteria) this;
        }

        public Criteria andFirstdictcodeLessThan(String value) {
            addCriterion("firstdictcode <", value, "firstdictcode");
            return (Criteria) this;
        }

        public Criteria andFirstdictcodeLessThanOrEqualTo(String value) {
            addCriterion("firstdictcode <=", value, "firstdictcode");
            return (Criteria) this;
        }

        public Criteria andFirstdictcodeLike(String value) {
            addCriterion("firstdictcode like", value, "firstdictcode");
            return (Criteria) this;
        }

        public Criteria andFirstdictcodeNotLike(String value) {
            addCriterion("firstdictcode not like", value, "firstdictcode");
            return (Criteria) this;
        }

        public Criteria andFirstdictcodeIn(List<String> values) {
            addCriterion("firstdictcode in", values, "firstdictcode");
            return (Criteria) this;
        }

        public Criteria andFirstdictcodeNotIn(List<String> values) {
            addCriterion("firstdictcode not in", values, "firstdictcode");
            return (Criteria) this;
        }

        public Criteria andFirstdictcodeBetween(String value1, String value2) {
            addCriterion("firstdictcode between", value1, value2, "firstdictcode");
            return (Criteria) this;
        }

        public Criteria andFirstdictcodeNotBetween(String value1, String value2) {
            addCriterion("firstdictcode not between", value1, value2, "firstdictcode");
            return (Criteria) this;
        }

        public Criteria andFirstdictnameIsNull() {
            addCriterion("firstdictname is null");
            return (Criteria) this;
        }

        public Criteria andFirstdictnameIsNotNull() {
            addCriterion("firstdictname is not null");
            return (Criteria) this;
        }

        public Criteria andFirstdictnameEqualTo(String value) {
            addCriterion("firstdictname =", value, "firstdictname");
            return (Criteria) this;
        }

        public Criteria andFirstdictnameNotEqualTo(String value) {
            addCriterion("firstdictname <>", value, "firstdictname");
            return (Criteria) this;
        }

        public Criteria andFirstdictnameGreaterThan(String value) {
            addCriterion("firstdictname >", value, "firstdictname");
            return (Criteria) this;
        }

        public Criteria andFirstdictnameGreaterThanOrEqualTo(String value) {
            addCriterion("firstdictname >=", value, "firstdictname");
            return (Criteria) this;
        }

        public Criteria andFirstdictnameLessThan(String value) {
            addCriterion("firstdictname <", value, "firstdictname");
            return (Criteria) this;
        }

        public Criteria andFirstdictnameLessThanOrEqualTo(String value) {
            addCriterion("firstdictname <=", value, "firstdictname");
            return (Criteria) this;
        }

        public Criteria andFirstdictnameLike(String value) {
            addCriterion("firstdictname like", value, "firstdictname");
            return (Criteria) this;
        }

        public Criteria andFirstdictnameNotLike(String value) {
            addCriterion("firstdictname not like", value, "firstdictname");
            return (Criteria) this;
        }

        public Criteria andFirstdictnameIn(List<String> values) {
            addCriterion("firstdictname in", values, "firstdictname");
            return (Criteria) this;
        }

        public Criteria andFirstdictnameNotIn(List<String> values) {
            addCriterion("firstdictname not in", values, "firstdictname");
            return (Criteria) this;
        }

        public Criteria andFirstdictnameBetween(String value1, String value2) {
            addCriterion("firstdictname between", value1, value2, "firstdictname");
            return (Criteria) this;
        }

        public Criteria andFirstdictnameNotBetween(String value1, String value2) {
            addCriterion("firstdictname not between", value1, value2, "firstdictname");
            return (Criteria) this;
        }

        public Criteria andSeconddictcodeIsNull() {
            addCriterion("seconddictcode is null");
            return (Criteria) this;
        }

        public Criteria andSeconddictcodeIsNotNull() {
            addCriterion("seconddictcode is not null");
            return (Criteria) this;
        }

        public Criteria andSeconddictcodeEqualTo(String value) {
            addCriterion("seconddictcode =", value, "seconddictcode");
            return (Criteria) this;
        }

        public Criteria andSeconddictcodeNotEqualTo(String value) {
            addCriterion("seconddictcode <>", value, "seconddictcode");
            return (Criteria) this;
        }

        public Criteria andSeconddictcodeGreaterThan(String value) {
            addCriterion("seconddictcode >", value, "seconddictcode");
            return (Criteria) this;
        }

        public Criteria andSeconddictcodeGreaterThanOrEqualTo(String value) {
            addCriterion("seconddictcode >=", value, "seconddictcode");
            return (Criteria) this;
        }

        public Criteria andSeconddictcodeLessThan(String value) {
            addCriterion("seconddictcode <", value, "seconddictcode");
            return (Criteria) this;
        }

        public Criteria andSeconddictcodeLessThanOrEqualTo(String value) {
            addCriterion("seconddictcode <=", value, "seconddictcode");
            return (Criteria) this;
        }

        public Criteria andSeconddictcodeLike(String value) {
            addCriterion("seconddictcode like", value, "seconddictcode");
            return (Criteria) this;
        }

        public Criteria andSeconddictcodeNotLike(String value) {
            addCriterion("seconddictcode not like", value, "seconddictcode");
            return (Criteria) this;
        }

        public Criteria andSeconddictcodeIn(List<String> values) {
            addCriterion("seconddictcode in", values, "seconddictcode");
            return (Criteria) this;
        }

        public Criteria andSeconddictcodeNotIn(List<String> values) {
            addCriterion("seconddictcode not in", values, "seconddictcode");
            return (Criteria) this;
        }

        public Criteria andSeconddictcodeBetween(String value1, String value2) {
            addCriterion("seconddictcode between", value1, value2, "seconddictcode");
            return (Criteria) this;
        }

        public Criteria andSeconddictcodeNotBetween(String value1, String value2) {
            addCriterion("seconddictcode not between", value1, value2, "seconddictcode");
            return (Criteria) this;
        }

        public Criteria andSeconddictnameIsNull() {
            addCriterion("seconddictname is null");
            return (Criteria) this;
        }

        public Criteria andSeconddictnameIsNotNull() {
            addCriterion("seconddictname is not null");
            return (Criteria) this;
        }

        public Criteria andSeconddictnameEqualTo(String value) {
            addCriterion("seconddictname =", value, "seconddictname");
            return (Criteria) this;
        }

        public Criteria andSeconddictnameNotEqualTo(String value) {
            addCriterion("seconddictname <>", value, "seconddictname");
            return (Criteria) this;
        }

        public Criteria andSeconddictnameGreaterThan(String value) {
            addCriterion("seconddictname >", value, "seconddictname");
            return (Criteria) this;
        }

        public Criteria andSeconddictnameGreaterThanOrEqualTo(String value) {
            addCriterion("seconddictname >=", value, "seconddictname");
            return (Criteria) this;
        }

        public Criteria andSeconddictnameLessThan(String value) {
            addCriterion("seconddictname <", value, "seconddictname");
            return (Criteria) this;
        }

        public Criteria andSeconddictnameLessThanOrEqualTo(String value) {
            addCriterion("seconddictname <=", value, "seconddictname");
            return (Criteria) this;
        }

        public Criteria andSeconddictnameLike(String value) {
            addCriterion("seconddictname like", value, "seconddictname");
            return (Criteria) this;
        }

        public Criteria andSeconddictnameNotLike(String value) {
            addCriterion("seconddictname not like", value, "seconddictname");
            return (Criteria) this;
        }

        public Criteria andSeconddictnameIn(List<String> values) {
            addCriterion("seconddictname in", values, "seconddictname");
            return (Criteria) this;
        }

        public Criteria andSeconddictnameNotIn(List<String> values) {
            addCriterion("seconddictname not in", values, "seconddictname");
            return (Criteria) this;
        }

        public Criteria andSeconddictnameBetween(String value1, String value2) {
            addCriterion("seconddictname between", value1, value2, "seconddictname");
            return (Criteria) this;
        }

        public Criteria andSeconddictnameNotBetween(String value1, String value2) {
            addCriterion("seconddictname not between", value1, value2, "seconddictname");
            return (Criteria) this;
        }

        public Criteria andThirddictcodeIsNull() {
            addCriterion("thirddictcode is null");
            return (Criteria) this;
        }

        public Criteria andThirddictcodeIsNotNull() {
            addCriterion("thirddictcode is not null");
            return (Criteria) this;
        }

        public Criteria andThirddictcodeEqualTo(String value) {
            addCriterion("thirddictcode =", value, "thirddictcode");
            return (Criteria) this;
        }

        public Criteria andThirddictcodeNotEqualTo(String value) {
            addCriterion("thirddictcode <>", value, "thirddictcode");
            return (Criteria) this;
        }

        public Criteria andThirddictcodeGreaterThan(String value) {
            addCriterion("thirddictcode >", value, "thirddictcode");
            return (Criteria) this;
        }

        public Criteria andThirddictcodeGreaterThanOrEqualTo(String value) {
            addCriterion("thirddictcode >=", value, "thirddictcode");
            return (Criteria) this;
        }

        public Criteria andThirddictcodeLessThan(String value) {
            addCriterion("thirddictcode <", value, "thirddictcode");
            return (Criteria) this;
        }

        public Criteria andThirddictcodeLessThanOrEqualTo(String value) {
            addCriterion("thirddictcode <=", value, "thirddictcode");
            return (Criteria) this;
        }

        public Criteria andThirddictcodeLike(String value) {
            addCriterion("thirddictcode like", value, "thirddictcode");
            return (Criteria) this;
        }

        public Criteria andThirddictcodeNotLike(String value) {
            addCriterion("thirddictcode not like", value, "thirddictcode");
            return (Criteria) this;
        }

        public Criteria andThirddictcodeIn(List<String> values) {
            addCriterion("thirddictcode in", values, "thirddictcode");
            return (Criteria) this;
        }

        public Criteria andThirddictcodeNotIn(List<String> values) {
            addCriterion("thirddictcode not in", values, "thirddictcode");
            return (Criteria) this;
        }

        public Criteria andThirddictcodeBetween(String value1, String value2) {
            addCriterion("thirddictcode between", value1, value2, "thirddictcode");
            return (Criteria) this;
        }

        public Criteria andThirddictcodeNotBetween(String value1, String value2) {
            addCriterion("thirddictcode not between", value1, value2, "thirddictcode");
            return (Criteria) this;
        }

        public Criteria andThirddictnameIsNull() {
            addCriterion("thirddictname is null");
            return (Criteria) this;
        }

        public Criteria andThirddictnameIsNotNull() {
            addCriterion("thirddictname is not null");
            return (Criteria) this;
        }

        public Criteria andThirddictnameEqualTo(String value) {
            addCriterion("thirddictname =", value, "thirddictname");
            return (Criteria) this;
        }

        public Criteria andThirddictnameNotEqualTo(String value) {
            addCriterion("thirddictname <>", value, "thirddictname");
            return (Criteria) this;
        }

        public Criteria andThirddictnameGreaterThan(String value) {
            addCriterion("thirddictname >", value, "thirddictname");
            return (Criteria) this;
        }

        public Criteria andThirddictnameGreaterThanOrEqualTo(String value) {
            addCriterion("thirddictname >=", value, "thirddictname");
            return (Criteria) this;
        }

        public Criteria andThirddictnameLessThan(String value) {
            addCriterion("thirddictname <", value, "thirddictname");
            return (Criteria) this;
        }

        public Criteria andThirddictnameLessThanOrEqualTo(String value) {
            addCriterion("thirddictname <=", value, "thirddictname");
            return (Criteria) this;
        }

        public Criteria andThirddictnameLike(String value) {
            addCriterion("thirddictname like", value, "thirddictname");
            return (Criteria) this;
        }

        public Criteria andThirddictnameNotLike(String value) {
            addCriterion("thirddictname not like", value, "thirddictname");
            return (Criteria) this;
        }

        public Criteria andThirddictnameIn(List<String> values) {
            addCriterion("thirddictname in", values, "thirddictname");
            return (Criteria) this;
        }

        public Criteria andThirddictnameNotIn(List<String> values) {
            addCriterion("thirddictname not in", values, "thirddictname");
            return (Criteria) this;
        }

        public Criteria andThirddictnameBetween(String value1, String value2) {
            addCriterion("thirddictname between", value1, value2, "thirddictname");
            return (Criteria) this;
        }

        public Criteria andThirddictnameNotBetween(String value1, String value2) {
            addCriterion("thirddictname not between", value1, value2, "thirddictname");
            return (Criteria) this;
        }

        public Criteria andFourthdictcodeIsNull() {
            addCriterion("fourthdictcode is null");
            return (Criteria) this;
        }

        public Criteria andFourthdictcodeIsNotNull() {
            addCriterion("fourthdictcode is not null");
            return (Criteria) this;
        }

        public Criteria andFourthdictcodeEqualTo(String value) {
            addCriterion("fourthdictcode =", value, "fourthdictcode");
            return (Criteria) this;
        }

        public Criteria andFourthdictcodeNotEqualTo(String value) {
            addCriterion("fourthdictcode <>", value, "fourthdictcode");
            return (Criteria) this;
        }

        public Criteria andFourthdictcodeGreaterThan(String value) {
            addCriterion("fourthdictcode >", value, "fourthdictcode");
            return (Criteria) this;
        }

        public Criteria andFourthdictcodeGreaterThanOrEqualTo(String value) {
            addCriterion("fourthdictcode >=", value, "fourthdictcode");
            return (Criteria) this;
        }

        public Criteria andFourthdictcodeLessThan(String value) {
            addCriterion("fourthdictcode <", value, "fourthdictcode");
            return (Criteria) this;
        }

        public Criteria andFourthdictcodeLessThanOrEqualTo(String value) {
            addCriterion("fourthdictcode <=", value, "fourthdictcode");
            return (Criteria) this;
        }

        public Criteria andFourthdictcodeLike(String value) {
            addCriterion("fourthdictcode like", value, "fourthdictcode");
            return (Criteria) this;
        }

        public Criteria andFourthdictcodeNotLike(String value) {
            addCriterion("fourthdictcode not like", value, "fourthdictcode");
            return (Criteria) this;
        }

        public Criteria andFourthdictcodeIn(List<String> values) {
            addCriterion("fourthdictcode in", values, "fourthdictcode");
            return (Criteria) this;
        }

        public Criteria andFourthdictcodeNotIn(List<String> values) {
            addCriterion("fourthdictcode not in", values, "fourthdictcode");
            return (Criteria) this;
        }

        public Criteria andFourthdictcodeBetween(String value1, String value2) {
            addCriterion("fourthdictcode between", value1, value2, "fourthdictcode");
            return (Criteria) this;
        }

        public Criteria andFourthdictcodeNotBetween(String value1, String value2) {
            addCriterion("fourthdictcode not between", value1, value2, "fourthdictcode");
            return (Criteria) this;
        }

        public Criteria andFourthdictnameIsNull() {
            addCriterion("fourthdictname is null");
            return (Criteria) this;
        }

        public Criteria andFourthdictnameIsNotNull() {
            addCriterion("fourthdictname is not null");
            return (Criteria) this;
        }

        public Criteria andFourthdictnameEqualTo(String value) {
            addCriterion("fourthdictname =", value, "fourthdictname");
            return (Criteria) this;
        }

        public Criteria andFourthdictnameNotEqualTo(String value) {
            addCriterion("fourthdictname <>", value, "fourthdictname");
            return (Criteria) this;
        }

        public Criteria andFourthdictnameGreaterThan(String value) {
            addCriterion("fourthdictname >", value, "fourthdictname");
            return (Criteria) this;
        }

        public Criteria andFourthdictnameGreaterThanOrEqualTo(String value) {
            addCriterion("fourthdictname >=", value, "fourthdictname");
            return (Criteria) this;
        }

        public Criteria andFourthdictnameLessThan(String value) {
            addCriterion("fourthdictname <", value, "fourthdictname");
            return (Criteria) this;
        }

        public Criteria andFourthdictnameLessThanOrEqualTo(String value) {
            addCriterion("fourthdictname <=", value, "fourthdictname");
            return (Criteria) this;
        }

        public Criteria andFourthdictnameLike(String value) {
            addCriterion("fourthdictname like", value, "fourthdictname");
            return (Criteria) this;
        }

        public Criteria andFourthdictnameNotLike(String value) {
            addCriterion("fourthdictname not like", value, "fourthdictname");
            return (Criteria) this;
        }

        public Criteria andFourthdictnameIn(List<String> values) {
            addCriterion("fourthdictname in", values, "fourthdictname");
            return (Criteria) this;
        }

        public Criteria andFourthdictnameNotIn(List<String> values) {
            addCriterion("fourthdictname not in", values, "fourthdictname");
            return (Criteria) this;
        }

        public Criteria andFourthdictnameBetween(String value1, String value2) {
            addCriterion("fourthdictname between", value1, value2, "fourthdictname");
            return (Criteria) this;
        }

        public Criteria andFourthdictnameNotBetween(String value1, String value2) {
            addCriterion("fourthdictname not between", value1, value2, "fourthdictname");
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

        public Criteria andFoldernameIsNull() {
            addCriterion("foldername is null");
            return (Criteria) this;
        }

        public Criteria andFoldernameIsNotNull() {
            addCriterion("foldername is not null");
            return (Criteria) this;
        }

        public Criteria andFoldernameEqualTo(String value) {
            addCriterion("foldername =", value, "foldername");
            return (Criteria) this;
        }

        public Criteria andFoldernameNotEqualTo(String value) {
            addCriterion("foldername <>", value, "foldername");
            return (Criteria) this;
        }

        public Criteria andFoldernameGreaterThan(String value) {
            addCriterion("foldername >", value, "foldername");
            return (Criteria) this;
        }

        public Criteria andFoldernameGreaterThanOrEqualTo(String value) {
            addCriterion("foldername >=", value, "foldername");
            return (Criteria) this;
        }

        public Criteria andFoldernameLessThan(String value) {
            addCriterion("foldername <", value, "foldername");
            return (Criteria) this;
        }

        public Criteria andFoldernameLessThanOrEqualTo(String value) {
            addCriterion("foldername <=", value, "foldername");
            return (Criteria) this;
        }

        public Criteria andFoldernameLike(String value) {
            addCriterion("foldername like", value, "foldername");
            return (Criteria) this;
        }

        public Criteria andFoldernameNotLike(String value) {
            addCriterion("foldername not like", value, "foldername");
            return (Criteria) this;
        }

        public Criteria andFoldernameIn(List<String> values) {
            addCriterion("foldername in", values, "foldername");
            return (Criteria) this;
        }

        public Criteria andFoldernameNotIn(List<String> values) {
            addCriterion("foldername not in", values, "foldername");
            return (Criteria) this;
        }

        public Criteria andFoldernameBetween(String value1, String value2) {
            addCriterion("foldername between", value1, value2, "foldername");
            return (Criteria) this;
        }

        public Criteria andFoldernameNotBetween(String value1, String value2) {
            addCriterion("foldername not between", value1, value2, "foldername");
            return (Criteria) this;
        }

        public Criteria andFullpathIsNull() {
            addCriterion("fullpath is null");
            return (Criteria) this;
        }

        public Criteria andFullpathIsNotNull() {
            addCriterion("fullpath is not null");
            return (Criteria) this;
        }

        public Criteria andFullpathEqualTo(String value) {
            addCriterion("fullpath =", value, "fullpath");
            return (Criteria) this;
        }

        public Criteria andFullpathNotEqualTo(String value) {
            addCriterion("fullpath <>", value, "fullpath");
            return (Criteria) this;
        }

        public Criteria andFullpathGreaterThan(String value) {
            addCriterion("fullpath >", value, "fullpath");
            return (Criteria) this;
        }

        public Criteria andFullpathGreaterThanOrEqualTo(String value) {
            addCriterion("fullpath >=", value, "fullpath");
            return (Criteria) this;
        }

        public Criteria andFullpathLessThan(String value) {
            addCriterion("fullpath <", value, "fullpath");
            return (Criteria) this;
        }

        public Criteria andFullpathLessThanOrEqualTo(String value) {
            addCriterion("fullpath <=", value, "fullpath");
            return (Criteria) this;
        }

        public Criteria andFullpathLike(String value) {
            addCriterion("fullpath like", value, "fullpath");
            return (Criteria) this;
        }

        public Criteria andFullpathNotLike(String value) {
            addCriterion("fullpath not like", value, "fullpath");
            return (Criteria) this;
        }

        public Criteria andFullpathIn(List<String> values) {
            addCriterion("fullpath in", values, "fullpath");
            return (Criteria) this;
        }

        public Criteria andFullpathNotIn(List<String> values) {
            addCriterion("fullpath not in", values, "fullpath");
            return (Criteria) this;
        }

        public Criteria andFullpathBetween(String value1, String value2) {
            addCriterion("fullpath between", value1, value2, "fullpath");
            return (Criteria) this;
        }

        public Criteria andFullpathNotBetween(String value1, String value2) {
            addCriterion("fullpath not between", value1, value2, "fullpath");
            return (Criteria) this;
        }

        public Criteria andFullnameIsNull() {
            addCriterion("fullname is null");
            return (Criteria) this;
        }

        public Criteria andFullnameIsNotNull() {
            addCriterion("fullname is not null");
            return (Criteria) this;
        }

        public Criteria andFullnameEqualTo(String value) {
            addCriterion("fullname =", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameNotEqualTo(String value) {
            addCriterion("fullname <>", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameGreaterThan(String value) {
            addCriterion("fullname >", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameGreaterThanOrEqualTo(String value) {
            addCriterion("fullname >=", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameLessThan(String value) {
            addCriterion("fullname <", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameLessThanOrEqualTo(String value) {
            addCriterion("fullname <=", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameLike(String value) {
            addCriterion("fullname like", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameNotLike(String value) {
            addCriterion("fullname not like", value, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameIn(List<String> values) {
            addCriterion("fullname in", values, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameNotIn(List<String> values) {
            addCriterion("fullname not in", values, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameBetween(String value1, String value2) {
            addCriterion("fullname between", value1, value2, "fullname");
            return (Criteria) this;
        }

        public Criteria andFullnameNotBetween(String value1, String value2) {
            addCriterion("fullname not between", value1, value2, "fullname");
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