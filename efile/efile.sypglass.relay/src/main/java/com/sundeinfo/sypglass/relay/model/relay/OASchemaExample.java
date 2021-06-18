package com.sundeinfo.sypglass.relay.model.relay;

import java.util.ArrayList;
import java.util.List;

public class OASchemaExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OASchemaExample() {
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

        public Criteria andTablenameIsNull() {
            addCriterion("tablename is null");
            return (Criteria) this;
        }

        public Criteria andTablenameIsNotNull() {
            addCriterion("tablename is not null");
            return (Criteria) this;
        }

        public Criteria andTablenameEqualTo(String value) {
            addCriterion("tablename =", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameNotEqualTo(String value) {
            addCriterion("tablename <>", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameGreaterThan(String value) {
            addCriterion("tablename >", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameGreaterThanOrEqualTo(String value) {
            addCriterion("tablename >=", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameLessThan(String value) {
            addCriterion("tablename <", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameLessThanOrEqualTo(String value) {
            addCriterion("tablename <=", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameLike(String value) {
            addCriterion("tablename like", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameNotLike(String value) {
            addCriterion("tablename not like", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameIn(List<String> values) {
            addCriterion("tablename in", values, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameNotIn(List<String> values) {
            addCriterion("tablename not in", values, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameBetween(String value1, String value2) {
            addCriterion("tablename between", value1, value2, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameNotBetween(String value1, String value2) {
            addCriterion("tablename not between", value1, value2, "tablename");
            return (Criteria) this;
        }

        public Criteria andColumnindexIsNull() {
            addCriterion("columnindex is null");
            return (Criteria) this;
        }

        public Criteria andColumnindexIsNotNull() {
            addCriterion("columnindex is not null");
            return (Criteria) this;
        }

        public Criteria andColumnindexEqualTo(Integer value) {
            addCriterion("columnindex =", value, "columnindex");
            return (Criteria) this;
        }

        public Criteria andColumnindexNotEqualTo(Integer value) {
            addCriterion("columnindex <>", value, "columnindex");
            return (Criteria) this;
        }

        public Criteria andColumnindexGreaterThan(Integer value) {
            addCriterion("columnindex >", value, "columnindex");
            return (Criteria) this;
        }

        public Criteria andColumnindexGreaterThanOrEqualTo(Integer value) {
            addCriterion("columnindex >=", value, "columnindex");
            return (Criteria) this;
        }

        public Criteria andColumnindexLessThan(Integer value) {
            addCriterion("columnindex <", value, "columnindex");
            return (Criteria) this;
        }

        public Criteria andColumnindexLessThanOrEqualTo(Integer value) {
            addCriterion("columnindex <=", value, "columnindex");
            return (Criteria) this;
        }

        public Criteria andColumnindexIn(List<Integer> values) {
            addCriterion("columnindex in", values, "columnindex");
            return (Criteria) this;
        }

        public Criteria andColumnindexNotIn(List<Integer> values) {
            addCriterion("columnindex not in", values, "columnindex");
            return (Criteria) this;
        }

        public Criteria andColumnindexBetween(Integer value1, Integer value2) {
            addCriterion("columnindex between", value1, value2, "columnindex");
            return (Criteria) this;
        }

        public Criteria andColumnindexNotBetween(Integer value1, Integer value2) {
            addCriterion("columnindex not between", value1, value2, "columnindex");
            return (Criteria) this;
        }

        public Criteria andColumnnameIsNull() {
            addCriterion("columnname is null");
            return (Criteria) this;
        }

        public Criteria andColumnnameIsNotNull() {
            addCriterion("columnname is not null");
            return (Criteria) this;
        }

        public Criteria andColumnnameEqualTo(String value) {
            addCriterion("columnname =", value, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameNotEqualTo(String value) {
            addCriterion("columnname <>", value, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameGreaterThan(String value) {
            addCriterion("columnname >", value, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameGreaterThanOrEqualTo(String value) {
            addCriterion("columnname >=", value, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameLessThan(String value) {
            addCriterion("columnname <", value, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameLessThanOrEqualTo(String value) {
            addCriterion("columnname <=", value, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameLike(String value) {
            addCriterion("columnname like", value, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameNotLike(String value) {
            addCriterion("columnname not like", value, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameIn(List<String> values) {
            addCriterion("columnname in", values, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameNotIn(List<String> values) {
            addCriterion("columnname not in", values, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameBetween(String value1, String value2) {
            addCriterion("columnname between", value1, value2, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameNotBetween(String value1, String value2) {
            addCriterion("columnname not between", value1, value2, "columnname");
            return (Criteria) this;
        }

        public Criteria andIsidIsNull() {
            addCriterion("isid is null");
            return (Criteria) this;
        }

        public Criteria andIsidIsNotNull() {
            addCriterion("isid is not null");
            return (Criteria) this;
        }

        public Criteria andIsidEqualTo(String value) {
            addCriterion("isid =", value, "isid");
            return (Criteria) this;
        }

        public Criteria andIsidNotEqualTo(String value) {
            addCriterion("isid <>", value, "isid");
            return (Criteria) this;
        }

        public Criteria andIsidGreaterThan(String value) {
            addCriterion("isid >", value, "isid");
            return (Criteria) this;
        }

        public Criteria andIsidGreaterThanOrEqualTo(String value) {
            addCriterion("isid >=", value, "isid");
            return (Criteria) this;
        }

        public Criteria andIsidLessThan(String value) {
            addCriterion("isid <", value, "isid");
            return (Criteria) this;
        }

        public Criteria andIsidLessThanOrEqualTo(String value) {
            addCriterion("isid <=", value, "isid");
            return (Criteria) this;
        }

        public Criteria andIsidLike(String value) {
            addCriterion("isid like", value, "isid");
            return (Criteria) this;
        }

        public Criteria andIsidNotLike(String value) {
            addCriterion("isid not like", value, "isid");
            return (Criteria) this;
        }

        public Criteria andIsidIn(List<String> values) {
            addCriterion("isid in", values, "isid");
            return (Criteria) this;
        }

        public Criteria andIsidNotIn(List<String> values) {
            addCriterion("isid not in", values, "isid");
            return (Criteria) this;
        }

        public Criteria andIsidBetween(String value1, String value2) {
            addCriterion("isid between", value1, value2, "isid");
            return (Criteria) this;
        }

        public Criteria andIsidNotBetween(String value1, String value2) {
            addCriterion("isid not between", value1, value2, "isid");
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

        public Criteria andIskeyEqualTo(String value) {
            addCriterion("iskey =", value, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyNotEqualTo(String value) {
            addCriterion("iskey <>", value, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyGreaterThan(String value) {
            addCriterion("iskey >", value, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyGreaterThanOrEqualTo(String value) {
            addCriterion("iskey >=", value, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyLessThan(String value) {
            addCriterion("iskey <", value, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyLessThanOrEqualTo(String value) {
            addCriterion("iskey <=", value, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyLike(String value) {
            addCriterion("iskey like", value, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyNotLike(String value) {
            addCriterion("iskey not like", value, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyIn(List<String> values) {
            addCriterion("iskey in", values, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyNotIn(List<String> values) {
            addCriterion("iskey not in", values, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyBetween(String value1, String value2) {
            addCriterion("iskey between", value1, value2, "iskey");
            return (Criteria) this;
        }

        public Criteria andIskeyNotBetween(String value1, String value2) {
            addCriterion("iskey not between", value1, value2, "iskey");
            return (Criteria) this;
        }

        public Criteria andColumntypeIsNull() {
            addCriterion("columntype is null");
            return (Criteria) this;
        }

        public Criteria andColumntypeIsNotNull() {
            addCriterion("columntype is not null");
            return (Criteria) this;
        }

        public Criteria andColumntypeEqualTo(String value) {
            addCriterion("columntype =", value, "columntype");
            return (Criteria) this;
        }

        public Criteria andColumntypeNotEqualTo(String value) {
            addCriterion("columntype <>", value, "columntype");
            return (Criteria) this;
        }

        public Criteria andColumntypeGreaterThan(String value) {
            addCriterion("columntype >", value, "columntype");
            return (Criteria) this;
        }

        public Criteria andColumntypeGreaterThanOrEqualTo(String value) {
            addCriterion("columntype >=", value, "columntype");
            return (Criteria) this;
        }

        public Criteria andColumntypeLessThan(String value) {
            addCriterion("columntype <", value, "columntype");
            return (Criteria) this;
        }

        public Criteria andColumntypeLessThanOrEqualTo(String value) {
            addCriterion("columntype <=", value, "columntype");
            return (Criteria) this;
        }

        public Criteria andColumntypeLike(String value) {
            addCriterion("columntype like", value, "columntype");
            return (Criteria) this;
        }

        public Criteria andColumntypeNotLike(String value) {
            addCriterion("columntype not like", value, "columntype");
            return (Criteria) this;
        }

        public Criteria andColumntypeIn(List<String> values) {
            addCriterion("columntype in", values, "columntype");
            return (Criteria) this;
        }

        public Criteria andColumntypeNotIn(List<String> values) {
            addCriterion("columntype not in", values, "columntype");
            return (Criteria) this;
        }

        public Criteria andColumntypeBetween(String value1, String value2) {
            addCriterion("columntype between", value1, value2, "columntype");
            return (Criteria) this;
        }

        public Criteria andColumntypeNotBetween(String value1, String value2) {
            addCriterion("columntype not between", value1, value2, "columntype");
            return (Criteria) this;
        }

        public Criteria andColumnbyteIsNull() {
            addCriterion("columnbyte is null");
            return (Criteria) this;
        }

        public Criteria andColumnbyteIsNotNull() {
            addCriterion("columnbyte is not null");
            return (Criteria) this;
        }

        public Criteria andColumnbyteEqualTo(Integer value) {
            addCriterion("columnbyte =", value, "columnbyte");
            return (Criteria) this;
        }

        public Criteria andColumnbyteNotEqualTo(Integer value) {
            addCriterion("columnbyte <>", value, "columnbyte");
            return (Criteria) this;
        }

        public Criteria andColumnbyteGreaterThan(Integer value) {
            addCriterion("columnbyte >", value, "columnbyte");
            return (Criteria) this;
        }

        public Criteria andColumnbyteGreaterThanOrEqualTo(Integer value) {
            addCriterion("columnbyte >=", value, "columnbyte");
            return (Criteria) this;
        }

        public Criteria andColumnbyteLessThan(Integer value) {
            addCriterion("columnbyte <", value, "columnbyte");
            return (Criteria) this;
        }

        public Criteria andColumnbyteLessThanOrEqualTo(Integer value) {
            addCriterion("columnbyte <=", value, "columnbyte");
            return (Criteria) this;
        }

        public Criteria andColumnbyteIn(List<Integer> values) {
            addCriterion("columnbyte in", values, "columnbyte");
            return (Criteria) this;
        }

        public Criteria andColumnbyteNotIn(List<Integer> values) {
            addCriterion("columnbyte not in", values, "columnbyte");
            return (Criteria) this;
        }

        public Criteria andColumnbyteBetween(Integer value1, Integer value2) {
            addCriterion("columnbyte between", value1, value2, "columnbyte");
            return (Criteria) this;
        }

        public Criteria andColumnbyteNotBetween(Integer value1, Integer value2) {
            addCriterion("columnbyte not between", value1, value2, "columnbyte");
            return (Criteria) this;
        }

        public Criteria andColumnlengthIsNull() {
            addCriterion("columnlength is null");
            return (Criteria) this;
        }

        public Criteria andColumnlengthIsNotNull() {
            addCriterion("columnlength is not null");
            return (Criteria) this;
        }

        public Criteria andColumnlengthEqualTo(Integer value) {
            addCriterion("columnlength =", value, "columnlength");
            return (Criteria) this;
        }

        public Criteria andColumnlengthNotEqualTo(Integer value) {
            addCriterion("columnlength <>", value, "columnlength");
            return (Criteria) this;
        }

        public Criteria andColumnlengthGreaterThan(Integer value) {
            addCriterion("columnlength >", value, "columnlength");
            return (Criteria) this;
        }

        public Criteria andColumnlengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("columnlength >=", value, "columnlength");
            return (Criteria) this;
        }

        public Criteria andColumnlengthLessThan(Integer value) {
            addCriterion("columnlength <", value, "columnlength");
            return (Criteria) this;
        }

        public Criteria andColumnlengthLessThanOrEqualTo(Integer value) {
            addCriterion("columnlength <=", value, "columnlength");
            return (Criteria) this;
        }

        public Criteria andColumnlengthIn(List<Integer> values) {
            addCriterion("columnlength in", values, "columnlength");
            return (Criteria) this;
        }

        public Criteria andColumnlengthNotIn(List<Integer> values) {
            addCriterion("columnlength not in", values, "columnlength");
            return (Criteria) this;
        }

        public Criteria andColumnlengthBetween(Integer value1, Integer value2) {
            addCriterion("columnlength between", value1, value2, "columnlength");
            return (Criteria) this;
        }

        public Criteria andColumnlengthNotBetween(Integer value1, Integer value2) {
            addCriterion("columnlength not between", value1, value2, "columnlength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthIsNull() {
            addCriterion("decimallength is null");
            return (Criteria) this;
        }

        public Criteria andDecimallengthIsNotNull() {
            addCriterion("decimallength is not null");
            return (Criteria) this;
        }

        public Criteria andDecimallengthEqualTo(Integer value) {
            addCriterion("decimallength =", value, "decimallength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthNotEqualTo(Integer value) {
            addCriterion("decimallength <>", value, "decimallength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthGreaterThan(Integer value) {
            addCriterion("decimallength >", value, "decimallength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("decimallength >=", value, "decimallength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthLessThan(Integer value) {
            addCriterion("decimallength <", value, "decimallength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthLessThanOrEqualTo(Integer value) {
            addCriterion("decimallength <=", value, "decimallength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthIn(List<Integer> values) {
            addCriterion("decimallength in", values, "decimallength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthNotIn(List<Integer> values) {
            addCriterion("decimallength not in", values, "decimallength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthBetween(Integer value1, Integer value2) {
            addCriterion("decimallength between", value1, value2, "decimallength");
            return (Criteria) this;
        }

        public Criteria andDecimallengthNotBetween(Integer value1, Integer value2) {
            addCriterion("decimallength not between", value1, value2, "decimallength");
            return (Criteria) this;
        }

        public Criteria andCannullIsNull() {
            addCriterion("cannull is null");
            return (Criteria) this;
        }

        public Criteria andCannullIsNotNull() {
            addCriterion("cannull is not null");
            return (Criteria) this;
        }

        public Criteria andCannullEqualTo(String value) {
            addCriterion("cannull =", value, "cannull");
            return (Criteria) this;
        }

        public Criteria andCannullNotEqualTo(String value) {
            addCriterion("cannull <>", value, "cannull");
            return (Criteria) this;
        }

        public Criteria andCannullGreaterThan(String value) {
            addCriterion("cannull >", value, "cannull");
            return (Criteria) this;
        }

        public Criteria andCannullGreaterThanOrEqualTo(String value) {
            addCriterion("cannull >=", value, "cannull");
            return (Criteria) this;
        }

        public Criteria andCannullLessThan(String value) {
            addCriterion("cannull <", value, "cannull");
            return (Criteria) this;
        }

        public Criteria andCannullLessThanOrEqualTo(String value) {
            addCriterion("cannull <=", value, "cannull");
            return (Criteria) this;
        }

        public Criteria andCannullLike(String value) {
            addCriterion("cannull like", value, "cannull");
            return (Criteria) this;
        }

        public Criteria andCannullNotLike(String value) {
            addCriterion("cannull not like", value, "cannull");
            return (Criteria) this;
        }

        public Criteria andCannullIn(List<String> values) {
            addCriterion("cannull in", values, "cannull");
            return (Criteria) this;
        }

        public Criteria andCannullNotIn(List<String> values) {
            addCriterion("cannull not in", values, "cannull");
            return (Criteria) this;
        }

        public Criteria andCannullBetween(String value1, String value2) {
            addCriterion("cannull between", value1, value2, "cannull");
            return (Criteria) this;
        }

        public Criteria andCannullNotBetween(String value1, String value2) {
            addCriterion("cannull not between", value1, value2, "cannull");
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