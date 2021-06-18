package com.sundeinfo.sypglass.model;

import java.util.ArrayList;
import java.util.List;

public class DeptJoinExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DeptJoinExample() {
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

        public Criteria andSysidIsNull() {
            addCriterion("sysid is null");
            return (Criteria) this;
        }

        public Criteria andSysidIsNotNull() {
            addCriterion("sysid is not null");
            return (Criteria) this;
        }

        public Criteria andSysidEqualTo(Long value) {
            addCriterion("sysid =", value, "sysid");
            return (Criteria) this;
        }

        public Criteria andSysidNotEqualTo(Long value) {
            addCriterion("sysid <>", value, "sysid");
            return (Criteria) this;
        }

        public Criteria andSysidGreaterThan(Long value) {
            addCriterion("sysid >", value, "sysid");
            return (Criteria) this;
        }

        public Criteria andSysidGreaterThanOrEqualTo(Long value) {
            addCriterion("sysid >=", value, "sysid");
            return (Criteria) this;
        }

        public Criteria andSysidLessThan(Long value) {
            addCriterion("sysid <", value, "sysid");
            return (Criteria) this;
        }

        public Criteria andSysidLessThanOrEqualTo(Long value) {
            addCriterion("sysid <=", value, "sysid");
            return (Criteria) this;
        }

        public Criteria andSysidIn(List<Long> values) {
            addCriterion("sysid in", values, "sysid");
            return (Criteria) this;
        }

        public Criteria andSysidNotIn(List<Long> values) {
            addCriterion("sysid not in", values, "sysid");
            return (Criteria) this;
        }

        public Criteria andSysidBetween(Long value1, Long value2) {
            addCriterion("sysid between", value1, value2, "sysid");
            return (Criteria) this;
        }

        public Criteria andSysidNotBetween(Long value1, Long value2) {
            addCriterion("sysid not between", value1, value2, "sysid");
            return (Criteria) this;
        }

        public Criteria andOacompidIsNull() {
            addCriterion("oacompid is null");
            return (Criteria) this;
        }

        public Criteria andOacompidIsNotNull() {
            addCriterion("oacompid is not null");
            return (Criteria) this;
        }

        public Criteria andOacompidEqualTo(Long value) {
            addCriterion("oacompid =", value, "oacompid");
            return (Criteria) this;
        }

        public Criteria andOacompidNotEqualTo(Long value) {
            addCriterion("oacompid <>", value, "oacompid");
            return (Criteria) this;
        }

        public Criteria andOacompidGreaterThan(Long value) {
            addCriterion("oacompid >", value, "oacompid");
            return (Criteria) this;
        }

        public Criteria andOacompidGreaterThanOrEqualTo(Long value) {
            addCriterion("oacompid >=", value, "oacompid");
            return (Criteria) this;
        }

        public Criteria andOacompidLessThan(Long value) {
            addCriterion("oacompid <", value, "oacompid");
            return (Criteria) this;
        }

        public Criteria andOacompidLessThanOrEqualTo(Long value) {
            addCriterion("oacompid <=", value, "oacompid");
            return (Criteria) this;
        }

        public Criteria andOacompidIn(List<Long> values) {
            addCriterion("oacompid in", values, "oacompid");
            return (Criteria) this;
        }

        public Criteria andOacompidNotIn(List<Long> values) {
            addCriterion("oacompid not in", values, "oacompid");
            return (Criteria) this;
        }

        public Criteria andOacompidBetween(Long value1, Long value2) {
            addCriterion("oacompid between", value1, value2, "oacompid");
            return (Criteria) this;
        }

        public Criteria andOacompidNotBetween(Long value1, Long value2) {
            addCriterion("oacompid not between", value1, value2, "oacompid");
            return (Criteria) this;
        }

        public Criteria andOadeptidIsNull() {
            addCriterion("oadeptid is null");
            return (Criteria) this;
        }

        public Criteria andOadeptidIsNotNull() {
            addCriterion("oadeptid is not null");
            return (Criteria) this;
        }

        public Criteria andOadeptidEqualTo(Long value) {
            addCriterion("oadeptid =", value, "oadeptid");
            return (Criteria) this;
        }

        public Criteria andOadeptidNotEqualTo(Long value) {
            addCriterion("oadeptid <>", value, "oadeptid");
            return (Criteria) this;
        }

        public Criteria andOadeptidGreaterThan(Long value) {
            addCriterion("oadeptid >", value, "oadeptid");
            return (Criteria) this;
        }

        public Criteria andOadeptidGreaterThanOrEqualTo(Long value) {
            addCriterion("oadeptid >=", value, "oadeptid");
            return (Criteria) this;
        }

        public Criteria andOadeptidLessThan(Long value) {
            addCriterion("oadeptid <", value, "oadeptid");
            return (Criteria) this;
        }

        public Criteria andOadeptidLessThanOrEqualTo(Long value) {
            addCriterion("oadeptid <=", value, "oadeptid");
            return (Criteria) this;
        }

        public Criteria andOadeptidIn(List<Long> values) {
            addCriterion("oadeptid in", values, "oadeptid");
            return (Criteria) this;
        }

        public Criteria andOadeptidNotIn(List<Long> values) {
            addCriterion("oadeptid not in", values, "oadeptid");
            return (Criteria) this;
        }

        public Criteria andOadeptidBetween(Long value1, Long value2) {
            addCriterion("oadeptid between", value1, value2, "oadeptid");
            return (Criteria) this;
        }

        public Criteria andOadeptidNotBetween(Long value1, Long value2) {
            addCriterion("oadeptid not between", value1, value2, "oadeptid");
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