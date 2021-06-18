package com.sundeinfo.sypglass.solr.model;

import java.util.ArrayList;
import java.util.List;

public class SolrStructureExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SolrStructureExample() {
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

        public Criteria andDoctypeEqualTo(String value) {
            addCriterion("doctype =", value, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeNotEqualTo(String value) {
            addCriterion("doctype <>", value, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeGreaterThan(String value) {
            addCriterion("doctype >", value, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeGreaterThanOrEqualTo(String value) {
            addCriterion("doctype >=", value, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeLessThan(String value) {
            addCriterion("doctype <", value, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeLessThanOrEqualTo(String value) {
            addCriterion("doctype <=", value, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeLike(String value) {
            addCriterion("doctype like", value, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeNotLike(String value) {
            addCriterion("doctype not like", value, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeIn(List<String> values) {
            addCriterion("doctype in", values, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeNotIn(List<String> values) {
            addCriterion("doctype not in", values, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeBetween(String value1, String value2) {
            addCriterion("doctype between", value1, value2, "doctype");
            return (Criteria) this;
        }

        public Criteria andDoctypeNotBetween(String value1, String value2) {
            addCriterion("doctype not between", value1, value2, "doctype");
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

        public Criteria andMultivaluedIsNull() {
            addCriterion("multiValued is null");
            return (Criteria) this;
        }

        public Criteria andMultivaluedIsNotNull() {
            addCriterion("multiValued is not null");
            return (Criteria) this;
        }

        public Criteria andMultivaluedEqualTo(Integer value) {
            addCriterion("multiValued =", value, "multivalued");
            return (Criteria) this;
        }

        public Criteria andMultivaluedNotEqualTo(Integer value) {
            addCriterion("multiValued <>", value, "multivalued");
            return (Criteria) this;
        }

        public Criteria andMultivaluedGreaterThan(Integer value) {
            addCriterion("multiValued >", value, "multivalued");
            return (Criteria) this;
        }

        public Criteria andMultivaluedGreaterThanOrEqualTo(Integer value) {
            addCriterion("multiValued >=", value, "multivalued");
            return (Criteria) this;
        }

        public Criteria andMultivaluedLessThan(Integer value) {
            addCriterion("multiValued <", value, "multivalued");
            return (Criteria) this;
        }

        public Criteria andMultivaluedLessThanOrEqualTo(Integer value) {
            addCriterion("multiValued <=", value, "multivalued");
            return (Criteria) this;
        }

        public Criteria andMultivaluedIn(List<Integer> values) {
            addCriterion("multiValued in", values, "multivalued");
            return (Criteria) this;
        }

        public Criteria andMultivaluedNotIn(List<Integer> values) {
            addCriterion("multiValued not in", values, "multivalued");
            return (Criteria) this;
        }

        public Criteria andMultivaluedBetween(Integer value1, Integer value2) {
            addCriterion("multiValued between", value1, value2, "multivalued");
            return (Criteria) this;
        }

        public Criteria andMultivaluedNotBetween(Integer value1, Integer value2) {
            addCriterion("multiValued not between", value1, value2, "multivalued");
            return (Criteria) this;
        }

        public Criteria andIndexedIsNull() {
            addCriterion("indexed is null");
            return (Criteria) this;
        }

        public Criteria andIndexedIsNotNull() {
            addCriterion("indexed is not null");
            return (Criteria) this;
        }

        public Criteria andIndexedEqualTo(Integer value) {
            addCriterion("indexed =", value, "indexed");
            return (Criteria) this;
        }

        public Criteria andIndexedNotEqualTo(Integer value) {
            addCriterion("indexed <>", value, "indexed");
            return (Criteria) this;
        }

        public Criteria andIndexedGreaterThan(Integer value) {
            addCriterion("indexed >", value, "indexed");
            return (Criteria) this;
        }

        public Criteria andIndexedGreaterThanOrEqualTo(Integer value) {
            addCriterion("indexed >=", value, "indexed");
            return (Criteria) this;
        }

        public Criteria andIndexedLessThan(Integer value) {
            addCriterion("indexed <", value, "indexed");
            return (Criteria) this;
        }

        public Criteria andIndexedLessThanOrEqualTo(Integer value) {
            addCriterion("indexed <=", value, "indexed");
            return (Criteria) this;
        }

        public Criteria andIndexedIn(List<Integer> values) {
            addCriterion("indexed in", values, "indexed");
            return (Criteria) this;
        }

        public Criteria andIndexedNotIn(List<Integer> values) {
            addCriterion("indexed not in", values, "indexed");
            return (Criteria) this;
        }

        public Criteria andIndexedBetween(Integer value1, Integer value2) {
            addCriterion("indexed between", value1, value2, "indexed");
            return (Criteria) this;
        }

        public Criteria andIndexedNotBetween(Integer value1, Integer value2) {
            addCriterion("indexed not between", value1, value2, "indexed");
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

        public Criteria andIskeyIsNull() {
            addCriterion("iskey is null");
            return (Criteria) this;
        }

        public Criteria andIskeyIsNotNull() {
            addCriterion("iskey is not null");
            return (Criteria) this;
        }

        public Criteria andIskeyEqualTo(Integer value) {
            addCriterion("iskey =", value, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyNotEqualTo(Integer value) {
            addCriterion("iskey <>", value, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyGreaterThan(Integer value) {
            addCriterion("iskey >", value, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyGreaterThanOrEqualTo(Integer value) {
            addCriterion("iskey >=", value, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyLessThan(Integer value) {
            addCriterion("iskey <", value, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyLessThanOrEqualTo(Integer value) {
            addCriterion("iskey <=", value, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyIn(List<Integer> values) {
            addCriterion("iskey in", values, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyNotIn(List<Integer> values) {
            addCriterion("iskey not in", values, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyBetween(Integer value1, Integer value2) {
            addCriterion("iskey between", value1, value2, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyNotBetween(Integer value1, Integer value2) {
            addCriterion("iskey not between", value1, value2, "iskey");
            return (Criteria) this;
        }

        public Criteria andClusteringIsNull() {
            addCriterion("clustering is null");
            return (Criteria) this;
        }

        public Criteria andClusteringIsNotNull() {
            addCriterion("clustering is not null");
            return (Criteria) this;
        }

        public Criteria andClusteringEqualTo(Integer value) {
            addCriterion("clustering =", value, "clustering");
            return (Criteria) this;
        }

        public Criteria andClusteringNotEqualTo(Integer value) {
            addCriterion("clustering <>", value, "clustering");
            return (Criteria) this;
        }

        public Criteria andClusteringGreaterThan(Integer value) {
            addCriterion("clustering >", value, "clustering");
            return (Criteria) this;
        }

        public Criteria andClusteringGreaterThanOrEqualTo(Integer value) {
            addCriterion("clustering >=", value, "clustering");
            return (Criteria) this;
        }

        public Criteria andClusteringLessThan(Integer value) {
            addCriterion("clustering <", value, "clustering");
            return (Criteria) this;
        }

        public Criteria andClusteringLessThanOrEqualTo(Integer value) {
            addCriterion("clustering <=", value, "clustering");
            return (Criteria) this;
        }

        public Criteria andClusteringIn(List<Integer> values) {
            addCriterion("clustering in", values, "clustering");
            return (Criteria) this;
        }

        public Criteria andClusteringNotIn(List<Integer> values) {
            addCriterion("clustering not in", values, "clustering");
            return (Criteria) this;
        }

        public Criteria andClusteringBetween(Integer value1, Integer value2) {
            addCriterion("clustering between", value1, value2, "clustering");
            return (Criteria) this;
        }

        public Criteria andClusteringNotBetween(Integer value1, Integer value2) {
            addCriterion("clustering not between", value1, value2, "clustering");
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