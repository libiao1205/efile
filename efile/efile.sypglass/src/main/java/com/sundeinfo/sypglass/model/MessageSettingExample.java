package com.sundeinfo.sypglass.model;

import java.util.ArrayList;
import java.util.List;

public class MessageSettingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MessageSettingExample() {
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

        public Criteria andUpdatenoticeIsNull() {
            addCriterion("updatenotice is null");
            return (Criteria) this;
        }

        public Criteria andUpdatenoticeIsNotNull() {
            addCriterion("updatenotice is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatenoticeEqualTo(Boolean value) {
            addCriterion("updatenotice =", value, "updatenotice");
            return (Criteria) this;
        }

        public Criteria andUpdatenoticeNotEqualTo(Boolean value) {
            addCriterion("updatenotice <>", value, "updatenotice");
            return (Criteria) this;
        }

        public Criteria andUpdatenoticeGreaterThan(Boolean value) {
            addCriterion("updatenotice >", value, "updatenotice");
            return (Criteria) this;
        }

        public Criteria andUpdatenoticeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("updatenotice >=", value, "updatenotice");
            return (Criteria) this;
        }

        public Criteria andUpdatenoticeLessThan(Boolean value) {
            addCriterion("updatenotice <", value, "updatenotice");
            return (Criteria) this;
        }

        public Criteria andUpdatenoticeLessThanOrEqualTo(Boolean value) {
            addCriterion("updatenotice <=", value, "updatenotice");
            return (Criteria) this;
        }

        public Criteria andUpdatenoticeIn(List<Boolean> values) {
            addCriterion("updatenotice in", values, "updatenotice");
            return (Criteria) this;
        }

        public Criteria andUpdatenoticeNotIn(List<Boolean> values) {
            addCriterion("updatenotice not in", values, "updatenotice");
            return (Criteria) this;
        }

        public Criteria andUpdatenoticeBetween(Boolean value1, Boolean value2) {
            addCriterion("updatenotice between", value1, value2, "updatenotice");
            return (Criteria) this;
        }

        public Criteria andUpdatenoticeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("updatenotice not between", value1, value2, "updatenotice");
            return (Criteria) this;
        }

        public Criteria andDeletenoticeIsNull() {
            addCriterion("deletenotice is null");
            return (Criteria) this;
        }

        public Criteria andDeletenoticeIsNotNull() {
            addCriterion("deletenotice is not null");
            return (Criteria) this;
        }

        public Criteria andDeletenoticeEqualTo(Boolean value) {
            addCriterion("deletenotice =", value, "deletenotice");
            return (Criteria) this;
        }

        public Criteria andDeletenoticeNotEqualTo(Boolean value) {
            addCriterion("deletenotice <>", value, "deletenotice");
            return (Criteria) this;
        }

        public Criteria andDeletenoticeGreaterThan(Boolean value) {
            addCriterion("deletenotice >", value, "deletenotice");
            return (Criteria) this;
        }

        public Criteria andDeletenoticeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deletenotice >=", value, "deletenotice");
            return (Criteria) this;
        }

        public Criteria andDeletenoticeLessThan(Boolean value) {
            addCriterion("deletenotice <", value, "deletenotice");
            return (Criteria) this;
        }

        public Criteria andDeletenoticeLessThanOrEqualTo(Boolean value) {
            addCriterion("deletenotice <=", value, "deletenotice");
            return (Criteria) this;
        }

        public Criteria andDeletenoticeIn(List<Boolean> values) {
            addCriterion("deletenotice in", values, "deletenotice");
            return (Criteria) this;
        }

        public Criteria andDeletenoticeNotIn(List<Boolean> values) {
            addCriterion("deletenotice not in", values, "deletenotice");
            return (Criteria) this;
        }

        public Criteria andDeletenoticeBetween(Boolean value1, Boolean value2) {
            addCriterion("deletenotice between", value1, value2, "deletenotice");
            return (Criteria) this;
        }

        public Criteria andDeletenoticeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("deletenotice not between", value1, value2, "deletenotice");
            return (Criteria) this;
        }

        public Criteria andDownloadnoticeIsNull() {
            addCriterion("downloadnotice is null");
            return (Criteria) this;
        }

        public Criteria andDownloadnoticeIsNotNull() {
            addCriterion("downloadnotice is not null");
            return (Criteria) this;
        }

        public Criteria andDownloadnoticeEqualTo(Boolean value) {
            addCriterion("downloadnotice =", value, "downloadnotice");
            return (Criteria) this;
        }

        public Criteria andDownloadnoticeNotEqualTo(Boolean value) {
            addCriterion("downloadnotice <>", value, "downloadnotice");
            return (Criteria) this;
        }

        public Criteria andDownloadnoticeGreaterThan(Boolean value) {
            addCriterion("downloadnotice >", value, "downloadnotice");
            return (Criteria) this;
        }

        public Criteria andDownloadnoticeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("downloadnotice >=", value, "downloadnotice");
            return (Criteria) this;
        }

        public Criteria andDownloadnoticeLessThan(Boolean value) {
            addCriterion("downloadnotice <", value, "downloadnotice");
            return (Criteria) this;
        }

        public Criteria andDownloadnoticeLessThanOrEqualTo(Boolean value) {
            addCriterion("downloadnotice <=", value, "downloadnotice");
            return (Criteria) this;
        }

        public Criteria andDownloadnoticeIn(List<Boolean> values) {
            addCriterion("downloadnotice in", values, "downloadnotice");
            return (Criteria) this;
        }

        public Criteria andDownloadnoticeNotIn(List<Boolean> values) {
            addCriterion("downloadnotice not in", values, "downloadnotice");
            return (Criteria) this;
        }

        public Criteria andDownloadnoticeBetween(Boolean value1, Boolean value2) {
            addCriterion("downloadnotice between", value1, value2, "downloadnotice");
            return (Criteria) this;
        }

        public Criteria andDownloadnoticeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("downloadnotice not between", value1, value2, "downloadnotice");
            return (Criteria) this;
        }

        public Criteria andNotice1IsNull() {
            addCriterion("notice_1 is null");
            return (Criteria) this;
        }

        public Criteria andNotice1IsNotNull() {
            addCriterion("notice_1 is not null");
            return (Criteria) this;
        }

        public Criteria andNotice1EqualTo(Boolean value) {
            addCriterion("notice_1 =", value, "notice1");
            return (Criteria) this;
        }

        public Criteria andNotice1NotEqualTo(Boolean value) {
            addCriterion("notice_1 <>", value, "notice1");
            return (Criteria) this;
        }

        public Criteria andNotice1GreaterThan(Boolean value) {
            addCriterion("notice_1 >", value, "notice1");
            return (Criteria) this;
        }

        public Criteria andNotice1GreaterThanOrEqualTo(Boolean value) {
            addCriterion("notice_1 >=", value, "notice1");
            return (Criteria) this;
        }

        public Criteria andNotice1LessThan(Boolean value) {
            addCriterion("notice_1 <", value, "notice1");
            return (Criteria) this;
        }

        public Criteria andNotice1LessThanOrEqualTo(Boolean value) {
            addCriterion("notice_1 <=", value, "notice1");
            return (Criteria) this;
        }

        public Criteria andNotice1In(List<Boolean> values) {
            addCriterion("notice_1 in", values, "notice1");
            return (Criteria) this;
        }

        public Criteria andNotice1NotIn(List<Boolean> values) {
            addCriterion("notice_1 not in", values, "notice1");
            return (Criteria) this;
        }

        public Criteria andNotice1Between(Boolean value1, Boolean value2) {
            addCriterion("notice_1 between", value1, value2, "notice1");
            return (Criteria) this;
        }

        public Criteria andNotice1NotBetween(Boolean value1, Boolean value2) {
            addCriterion("notice_1 not between", value1, value2, "notice1");
            return (Criteria) this;
        }

        public Criteria andNotice2IsNull() {
            addCriterion("notice_2 is null");
            return (Criteria) this;
        }

        public Criteria andNotice2IsNotNull() {
            addCriterion("notice_2 is not null");
            return (Criteria) this;
        }

        public Criteria andNotice2EqualTo(Boolean value) {
            addCriterion("notice_2 =", value, "notice2");
            return (Criteria) this;
        }

        public Criteria andNotice2NotEqualTo(Boolean value) {
            addCriterion("notice_2 <>", value, "notice2");
            return (Criteria) this;
        }

        public Criteria andNotice2GreaterThan(Boolean value) {
            addCriterion("notice_2 >", value, "notice2");
            return (Criteria) this;
        }

        public Criteria andNotice2GreaterThanOrEqualTo(Boolean value) {
            addCriterion("notice_2 >=", value, "notice2");
            return (Criteria) this;
        }

        public Criteria andNotice2LessThan(Boolean value) {
            addCriterion("notice_2 <", value, "notice2");
            return (Criteria) this;
        }

        public Criteria andNotice2LessThanOrEqualTo(Boolean value) {
            addCriterion("notice_2 <=", value, "notice2");
            return (Criteria) this;
        }

        public Criteria andNotice2In(List<Boolean> values) {
            addCriterion("notice_2 in", values, "notice2");
            return (Criteria) this;
        }

        public Criteria andNotice2NotIn(List<Boolean> values) {
            addCriterion("notice_2 not in", values, "notice2");
            return (Criteria) this;
        }

        public Criteria andNotice2Between(Boolean value1, Boolean value2) {
            addCriterion("notice_2 between", value1, value2, "notice2");
            return (Criteria) this;
        }

        public Criteria andNotice2NotBetween(Boolean value1, Boolean value2) {
            addCriterion("notice_2 not between", value1, value2, "notice2");
            return (Criteria) this;
        }

        public Criteria andNotice3IsNull() {
            addCriterion("notice_3 is null");
            return (Criteria) this;
        }

        public Criteria andNotice3IsNotNull() {
            addCriterion("notice_3 is not null");
            return (Criteria) this;
        }

        public Criteria andNotice3EqualTo(Boolean value) {
            addCriterion("notice_3 =", value, "notice3");
            return (Criteria) this;
        }

        public Criteria andNotice3NotEqualTo(Boolean value) {
            addCriterion("notice_3 <>", value, "notice3");
            return (Criteria) this;
        }

        public Criteria andNotice3GreaterThan(Boolean value) {
            addCriterion("notice_3 >", value, "notice3");
            return (Criteria) this;
        }

        public Criteria andNotice3GreaterThanOrEqualTo(Boolean value) {
            addCriterion("notice_3 >=", value, "notice3");
            return (Criteria) this;
        }

        public Criteria andNotice3LessThan(Boolean value) {
            addCriterion("notice_3 <", value, "notice3");
            return (Criteria) this;
        }

        public Criteria andNotice3LessThanOrEqualTo(Boolean value) {
            addCriterion("notice_3 <=", value, "notice3");
            return (Criteria) this;
        }

        public Criteria andNotice3In(List<Boolean> values) {
            addCriterion("notice_3 in", values, "notice3");
            return (Criteria) this;
        }

        public Criteria andNotice3NotIn(List<Boolean> values) {
            addCriterion("notice_3 not in", values, "notice3");
            return (Criteria) this;
        }

        public Criteria andNotice3Between(Boolean value1, Boolean value2) {
            addCriterion("notice_3 between", value1, value2, "notice3");
            return (Criteria) this;
        }

        public Criteria andNotice3NotBetween(Boolean value1, Boolean value2) {
            addCriterion("notice_3 not between", value1, value2, "notice3");
            return (Criteria) this;
        }

        public Criteria andNotice4IsNull() {
            addCriterion("notice_4 is null");
            return (Criteria) this;
        }

        public Criteria andNotice4IsNotNull() {
            addCriterion("notice_4 is not null");
            return (Criteria) this;
        }

        public Criteria andNotice4EqualTo(Boolean value) {
            addCriterion("notice_4 =", value, "notice4");
            return (Criteria) this;
        }

        public Criteria andNotice4NotEqualTo(Boolean value) {
            addCriterion("notice_4 <>", value, "notice4");
            return (Criteria) this;
        }

        public Criteria andNotice4GreaterThan(Boolean value) {
            addCriterion("notice_4 >", value, "notice4");
            return (Criteria) this;
        }

        public Criteria andNotice4GreaterThanOrEqualTo(Boolean value) {
            addCriterion("notice_4 >=", value, "notice4");
            return (Criteria) this;
        }

        public Criteria andNotice4LessThan(Boolean value) {
            addCriterion("notice_4 <", value, "notice4");
            return (Criteria) this;
        }

        public Criteria andNotice4LessThanOrEqualTo(Boolean value) {
            addCriterion("notice_4 <=", value, "notice4");
            return (Criteria) this;
        }

        public Criteria andNotice4In(List<Boolean> values) {
            addCriterion("notice_4 in", values, "notice4");
            return (Criteria) this;
        }

        public Criteria andNotice4NotIn(List<Boolean> values) {
            addCriterion("notice_4 not in", values, "notice4");
            return (Criteria) this;
        }

        public Criteria andNotice4Between(Boolean value1, Boolean value2) {
            addCriterion("notice_4 between", value1, value2, "notice4");
            return (Criteria) this;
        }

        public Criteria andNotice4NotBetween(Boolean value1, Boolean value2) {
            addCriterion("notice_4 not between", value1, value2, "notice4");
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