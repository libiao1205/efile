package com.sundeinfo.sypglass.solr.model;

import java.util.ArrayList;
import java.util.List;

public class MetadataExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MetadataExample() {
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

        public Criteria andDoctypeIsNull() {
            addCriterion("doctype is null");
            return (Criteria) this;
        }

        public Criteria andDoctypeIsNotNull() {
            addCriterion("doctype is not null");
            return (Criteria) this;
        }

        public Criteria andDoctypeEqualTo(Integer value) {
            addCriterion("doctype =", value, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeNotEqualTo(Integer value) {
            addCriterion("doctype <>", value, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeGreaterThan(Integer value) {
            addCriterion("doctype >", value, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("doctype >=", value, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeLessThan(Integer value) {
            addCriterion("doctype <", value, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeLessThanOrEqualTo(Integer value) {
            addCriterion("doctype <=", value, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeIn(List<Integer> values) {
            addCriterion("doctype in", values, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeNotIn(List<Integer> values) {
            addCriterion("doctype not in", values, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeBetween(Integer value1, Integer value2) {
            addCriterion("doctype between", value1, value2, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeNotBetween(Integer value1, Integer value2) {
            addCriterion("doctype not between", value1, value2, "doctype");
            return (Criteria) this;
        }

        public Criteria andDatapropertyIsNull() {
            addCriterion("dataproperty is null");
            return (Criteria) this;
        }

        public Criteria andDatapropertyIsNotNull() {
            addCriterion("dataproperty is not null");
            return (Criteria) this;
        }

        public Criteria andDatapropertyEqualTo(Integer value) {
            addCriterion("dataproperty =", value, "dataproperty");
            return (Criteria) this;
        }

        public Criteria andDatapropertyNotEqualTo(Integer value) {
            addCriterion("dataproperty <>", value, "dataproperty");
            return (Criteria) this;
        }

        public Criteria andDatapropertyGreaterThan(Integer value) {
            addCriterion("dataproperty >", value, "dataproperty");
            return (Criteria) this;
        }

        public Criteria andDatapropertyGreaterThanOrEqualTo(Integer value) {
            addCriterion("dataproperty >=", value, "dataproperty");
            return (Criteria) this;
        }

        public Criteria andDatapropertyLessThan(Integer value) {
            addCriterion("dataproperty <", value, "dataproperty");
            return (Criteria) this;
        }

        public Criteria andDatapropertyLessThanOrEqualTo(Integer value) {
            addCriterion("dataproperty <=", value, "dataproperty");
            return (Criteria) this;
        }

        public Criteria andDatapropertyIn(List<Integer> values) {
            addCriterion("dataproperty in", values, "dataproperty");
            return (Criteria) this;
        }

        public Criteria andDatapropertyNotIn(List<Integer> values) {
            addCriterion("dataproperty not in", values, "dataproperty");
            return (Criteria) this;
        }

        public Criteria andDatapropertyBetween(Integer value1, Integer value2) {
            addCriterion("dataproperty between", value1, value2, "dataproperty");
            return (Criteria) this;
        }

        public Criteria andDatapropertyNotBetween(Integer value1, Integer value2) {
            addCriterion("dataproperty not between", value1, value2, "dataproperty");
            return (Criteria) this;
        }

        public Criteria andDatatypeIsNull() {
            addCriterion("datatype is null");
            return (Criteria) this;
        }

        public Criteria andDatatypeIsNotNull() {
            addCriterion("datatype is not null");
            return (Criteria) this;
        }

        public Criteria andDatatypeEqualTo(Integer value) {
            addCriterion("datatype =", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeNotEqualTo(Integer value) {
            addCriterion("datatype <>", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeGreaterThan(Integer value) {
            addCriterion("datatype >", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("datatype >=", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeLessThan(Integer value) {
            addCriterion("datatype <", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeLessThanOrEqualTo(Integer value) {
            addCriterion("datatype <=", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeIn(List<Integer> values) {
            addCriterion("datatype in", values, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeNotIn(List<Integer> values) {
            addCriterion("datatype not in", values, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeBetween(Integer value1, Integer value2) {
            addCriterion("datatype between", value1, value2, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeNotBetween(Integer value1, Integer value2) {
            addCriterion("datatype not between", value1, value2, "datatype");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
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

        public Criteria andSeqIsNull() {
            addCriterion("seq is null");
            return (Criteria) this;
        }

        public Criteria andSeqIsNotNull() {
            addCriterion("seq is not null");
            return (Criteria) this;
        }

        public Criteria andSeqEqualTo(Integer value) {
            addCriterion("seq =", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotEqualTo(Integer value) {
            addCriterion("seq <>", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThan(Integer value) {
            addCriterion("seq >", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThanOrEqualTo(Integer value) {
            addCriterion("seq >=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThan(Integer value) {
            addCriterion("seq <", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThanOrEqualTo(Integer value) {
            addCriterion("seq <=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqIn(List<Integer> values) {
            addCriterion("seq in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotIn(List<Integer> values) {
            addCriterion("seq not in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqBetween(Integer value1, Integer value2) {
            addCriterion("seq between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotBetween(Integer value1, Integer value2) {
            addCriterion("seq not between", value1, value2, "seq");
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

        public Criteria andSizeEqualTo(Integer value) {
            addCriterion("size =", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotEqualTo(Integer value) {
            addCriterion("size <>", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThan(Integer value) {
            addCriterion("size >", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("size >=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThan(Integer value) {
            addCriterion("size <", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThanOrEqualTo(Integer value) {
            addCriterion("size <=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeIn(List<Integer> values) {
            addCriterion("size in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotIn(List<Integer> values) {
            addCriterion("size not in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeBetween(Integer value1, Integer value2) {
            addCriterion("size between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotBetween(Integer value1, Integer value2) {
            addCriterion("size not between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueIsNull() {
            addCriterion("defaultvalue is null");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueIsNotNull() {
            addCriterion("defaultvalue is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueEqualTo(String value) {
            addCriterion("defaultvalue =", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueNotEqualTo(String value) {
            addCriterion("defaultvalue <>", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueGreaterThan(String value) {
            addCriterion("defaultvalue >", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueGreaterThanOrEqualTo(String value) {
            addCriterion("defaultvalue >=", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueLessThan(String value) {
            addCriterion("defaultvalue <", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueLessThanOrEqualTo(String value) {
            addCriterion("defaultvalue <=", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueLike(String value) {
            addCriterion("defaultvalue like", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueNotLike(String value) {
            addCriterion("defaultvalue not like", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueIn(List<String> values) {
            addCriterion("defaultvalue in", values, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueNotIn(List<String> values) {
            addCriterion("defaultvalue not in", values, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueBetween(String value1, String value2) {
            addCriterion("defaultvalue between", value1, value2, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueNotBetween(String value1, String value2) {
            addCriterion("defaultvalue not between", value1, value2, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andCheckruleIsNull() {
            addCriterion("checkrule is null");
            return (Criteria) this;
        }

        public Criteria andCheckruleIsNotNull() {
            addCriterion("checkrule is not null");
            return (Criteria) this;
        }

        public Criteria andCheckruleEqualTo(Long value) {
            addCriterion("checkrule =", value, "checkrule");
            return (Criteria) this;
        }

        public Criteria andCheckruleNotEqualTo(Long value) {
            addCriterion("checkrule <>", value, "checkrule");
            return (Criteria) this;
        }

        public Criteria andCheckruleGreaterThan(Long value) {
            addCriterion("checkrule >", value, "checkrule");
            return (Criteria) this;
        }

        public Criteria andCheckruleGreaterThanOrEqualTo(Long value) {
            addCriterion("checkrule >=", value, "checkrule");
            return (Criteria) this;
        }

        public Criteria andCheckruleLessThan(Long value) {
            addCriterion("checkrule <", value, "checkrule");
            return (Criteria) this;
        }

        public Criteria andCheckruleLessThanOrEqualTo(Long value) {
            addCriterion("checkrule <=", value, "checkrule");
            return (Criteria) this;
        }

        public Criteria andCheckruleIn(List<Long> values) {
            addCriterion("checkrule in", values, "checkrule");
            return (Criteria) this;
        }

        public Criteria andCheckruleNotIn(List<Long> values) {
            addCriterion("checkrule not in", values, "checkrule");
            return (Criteria) this;
        }

        public Criteria andCheckruleBetween(Long value1, Long value2) {
            addCriterion("checkrule between", value1, value2, "checkrule");
            return (Criteria) this;
        }

        public Criteria andCheckruleNotBetween(Long value1, Long value2) {
            addCriterion("checkrule not between", value1, value2, "checkrule");
            return (Criteria) this;
        }

        public Criteria andNotnullIsNull() {
            addCriterion("notnull is null");
            return (Criteria) this;
        }

        public Criteria andNotnullIsNotNull() {
            addCriterion("notnull is not null");
            return (Criteria) this;
        }

        public Criteria andNotnullEqualTo(Integer value) {
            addCriterion("notnull =", value, "notnull");
            return (Criteria) this;
        }

        public Criteria andNotnullNotEqualTo(Integer value) {
            addCriterion("notnull <>", value, "notnull");
            return (Criteria) this;
        }

        public Criteria andNotnullGreaterThan(Integer value) {
            addCriterion("notnull >", value, "notnull");
            return (Criteria) this;
        }

        public Criteria andNotnullGreaterThanOrEqualTo(Integer value) {
            addCriterion("notnull >=", value, "notnull");
            return (Criteria) this;
        }

        public Criteria andNotnullLessThan(Integer value) {
            addCriterion("notnull <", value, "notnull");
            return (Criteria) this;
        }

        public Criteria andNotnullLessThanOrEqualTo(Integer value) {
            addCriterion("notnull <=", value, "notnull");
            return (Criteria) this;
        }

        public Criteria andNotnullIn(List<Integer> values) {
            addCriterion("notnull in", values, "notnull");
            return (Criteria) this;
        }

        public Criteria andNotnullNotIn(List<Integer> values) {
            addCriterion("notnull not in", values, "notnull");
            return (Criteria) this;
        }

        public Criteria andNotnullBetween(Integer value1, Integer value2) {
            addCriterion("notnull between", value1, value2, "notnull");
            return (Criteria) this;
        }

        public Criteria andNotnullNotBetween(Integer value1, Integer value2) {
            addCriterion("notnull not between", value1, value2, "notnull");
            return (Criteria) this;
        }

        public Criteria andMustsingleIsNull() {
            addCriterion("mustsingle is null");
            return (Criteria) this;
        }

        public Criteria andMustsingleIsNotNull() {
            addCriterion("mustsingle is not null");
            return (Criteria) this;
        }

        public Criteria andMustsingleEqualTo(Integer value) {
            addCriterion("mustsingle =", value, "mustsingle");
            return (Criteria) this;
        }

        public Criteria andMustsingleNotEqualTo(Integer value) {
            addCriterion("mustsingle <>", value, "mustsingle");
            return (Criteria) this;
        }

        public Criteria andMustsingleGreaterThan(Integer value) {
            addCriterion("mustsingle >", value, "mustsingle");
            return (Criteria) this;
        }

        public Criteria andMustsingleGreaterThanOrEqualTo(Integer value) {
            addCriterion("mustsingle >=", value, "mustsingle");
            return (Criteria) this;
        }

        public Criteria andMustsingleLessThan(Integer value) {
            addCriterion("mustsingle <", value, "mustsingle");
            return (Criteria) this;
        }

        public Criteria andMustsingleLessThanOrEqualTo(Integer value) {
            addCriterion("mustsingle <=", value, "mustsingle");
            return (Criteria) this;
        }

        public Criteria andMustsingleIn(List<Integer> values) {
            addCriterion("mustsingle in", values, "mustsingle");
            return (Criteria) this;
        }

        public Criteria andMustsingleNotIn(List<Integer> values) {
            addCriterion("mustsingle not in", values, "mustsingle");
            return (Criteria) this;
        }

        public Criteria andMustsingleBetween(Integer value1, Integer value2) {
            addCriterion("mustsingle between", value1, value2, "mustsingle");
            return (Criteria) this;
        }

        public Criteria andMustsingleNotBetween(Integer value1, Integer value2) {
            addCriterion("mustsingle not between", value1, value2, "mustsingle");
            return (Criteria) this;
        }

        public Criteria andSolridIsNull() {
            addCriterion("solrid is null");
            return (Criteria) this;
        }

        public Criteria andSolridIsNotNull() {
            addCriterion("solrid is not null");
            return (Criteria) this;
        }

        public Criteria andSolridEqualTo(Long value) {
            addCriterion("solrid =", value, "solrid");
            return (Criteria) this;
        }

        public Criteria andSolridNotEqualTo(Long value) {
            addCriterion("solrid <>", value, "solrid");
            return (Criteria) this;
        }

        public Criteria andSolridGreaterThan(Long value) {
            addCriterion("solrid >", value, "solrid");
            return (Criteria) this;
        }

        public Criteria andSolridGreaterThanOrEqualTo(Long value) {
            addCriterion("solrid >=", value, "solrid");
            return (Criteria) this;
        }

        public Criteria andSolridLessThan(Long value) {
            addCriterion("solrid <", value, "solrid");
            return (Criteria) this;
        }

        public Criteria andSolridLessThanOrEqualTo(Long value) {
            addCriterion("solrid <=", value, "solrid");
            return (Criteria) this;
        }

        public Criteria andSolridIn(List<Long> values) {
            addCriterion("solrid in", values, "solrid");
            return (Criteria) this;
        }

        public Criteria andSolridNotIn(List<Long> values) {
            addCriterion("solrid not in", values, "solrid");
            return (Criteria) this;
        }

        public Criteria andSolridBetween(Long value1, Long value2) {
            addCriterion("solrid between", value1, value2, "solrid");
            return (Criteria) this;
        }

        public Criteria andSolridNotBetween(Long value1, Long value2) {
            addCriterion("solrid not between", value1, value2, "solrid");
            return (Criteria) this;
        }

        public Criteria andSolrnameIsNull() {
            addCriterion("solrname is null");
            return (Criteria) this;
        }

        public Criteria andSolrnameIsNotNull() {
            addCriterion("solrname is not null");
            return (Criteria) this;
        }

        public Criteria andSolrnameEqualTo(String value) {
            addCriterion("solrname =", value, "solrname");
            return (Criteria) this;
        }

        public Criteria andSolrnameNotEqualTo(String value) {
            addCriterion("solrname <>", value, "solrname");
            return (Criteria) this;
        }

        public Criteria andSolrnameGreaterThan(String value) {
            addCriterion("solrname >", value, "solrname");
            return (Criteria) this;
        }

        public Criteria andSolrnameGreaterThanOrEqualTo(String value) {
            addCriterion("solrname >=", value, "solrname");
            return (Criteria) this;
        }

        public Criteria andSolrnameLessThan(String value) {
            addCriterion("solrname <", value, "solrname");
            return (Criteria) this;
        }

        public Criteria andSolrnameLessThanOrEqualTo(String value) {
            addCriterion("solrname <=", value, "solrname");
            return (Criteria) this;
        }

        public Criteria andSolrnameLike(String value) {
            addCriterion("solrname like", value, "solrname");
            return (Criteria) this;
        }

        public Criteria andSolrnameNotLike(String value) {
            addCriterion("solrname not like", value, "solrname");
            return (Criteria) this;
        }

        public Criteria andSolrnameIn(List<String> values) {
            addCriterion("solrname in", values, "solrname");
            return (Criteria) this;
        }

        public Criteria andSolrnameNotIn(List<String> values) {
            addCriterion("solrname not in", values, "solrname");
            return (Criteria) this;
        }

        public Criteria andSolrnameBetween(String value1, String value2) {
            addCriterion("solrname between", value1, value2, "solrname");
            return (Criteria) this;
        }

        public Criteria andSolrnameNotBetween(String value1, String value2) {
            addCriterion("solrname not between", value1, value2, "solrname");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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