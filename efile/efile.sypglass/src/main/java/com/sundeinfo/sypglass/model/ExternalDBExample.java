package com.sundeinfo.sypglass.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExternalDBExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExternalDBExample() {
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

        public Criteria andDbnameIsNull() {
            addCriterion("dbname is null");
            return (Criteria) this;
        }

        public Criteria andDbnameIsNotNull() {
            addCriterion("dbname is not null");
            return (Criteria) this;
        }

        public Criteria andDbnameEqualTo(String value) {
            addCriterion("dbname =", value, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameNotEqualTo(String value) {
            addCriterion("dbname <>", value, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameGreaterThan(String value) {
            addCriterion("dbname >", value, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameGreaterThanOrEqualTo(String value) {
            addCriterion("dbname >=", value, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameLessThan(String value) {
            addCriterion("dbname <", value, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameLessThanOrEqualTo(String value) {
            addCriterion("dbname <=", value, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameLike(String value) {
            addCriterion("dbname like", value, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameNotLike(String value) {
            addCriterion("dbname not like", value, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameIn(List<String> values) {
            addCriterion("dbname in", values, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameNotIn(List<String> values) {
            addCriterion("dbname not in", values, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameBetween(String value1, String value2) {
            addCriterion("dbname between", value1, value2, "dbname");
            return (Criteria) this;
        }

        public Criteria andDbnameNotBetween(String value1, String value2) {
            addCriterion("dbname not between", value1, value2, "dbname");
            return (Criteria) this;
        }

        public Criteria andAliasnameIsNull() {
            addCriterion("aliasname is null");
            return (Criteria) this;
        }

        public Criteria andAliasnameIsNotNull() {
            addCriterion("aliasname is not null");
            return (Criteria) this;
        }

        public Criteria andAliasnameEqualTo(String value) {
            addCriterion("aliasname =", value, "aliasname");
            return (Criteria) this;
        }

        public Criteria andAliasnameNotEqualTo(String value) {
            addCriterion("aliasname <>", value, "aliasname");
            return (Criteria) this;
        }

        public Criteria andAliasnameGreaterThan(String value) {
            addCriterion("aliasname >", value, "aliasname");
            return (Criteria) this;
        }

        public Criteria andAliasnameGreaterThanOrEqualTo(String value) {
            addCriterion("aliasname >=", value, "aliasname");
            return (Criteria) this;
        }

        public Criteria andAliasnameLessThan(String value) {
            addCriterion("aliasname <", value, "aliasname");
            return (Criteria) this;
        }

        public Criteria andAliasnameLessThanOrEqualTo(String value) {
            addCriterion("aliasname <=", value, "aliasname");
            return (Criteria) this;
        }

        public Criteria andAliasnameLike(String value) {
            addCriterion("aliasname like", value, "aliasname");
            return (Criteria) this;
        }

        public Criteria andAliasnameNotLike(String value) {
            addCriterion("aliasname not like", value, "aliasname");
            return (Criteria) this;
        }

        public Criteria andAliasnameIn(List<String> values) {
            addCriterion("aliasname in", values, "aliasname");
            return (Criteria) this;
        }

        public Criteria andAliasnameNotIn(List<String> values) {
            addCriterion("aliasname not in", values, "aliasname");
            return (Criteria) this;
        }

        public Criteria andAliasnameBetween(String value1, String value2) {
            addCriterion("aliasname between", value1, value2, "aliasname");
            return (Criteria) this;
        }

        public Criteria andAliasnameNotBetween(String value1, String value2) {
            addCriterion("aliasname not between", value1, value2, "aliasname");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andPortIsNull() {
            addCriterion("port is null");
            return (Criteria) this;
        }

        public Criteria andPortIsNotNull() {
            addCriterion("port is not null");
            return (Criteria) this;
        }

        public Criteria andPortEqualTo(String value) {
            addCriterion("port =", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotEqualTo(String value) {
            addCriterion("port <>", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortGreaterThan(String value) {
            addCriterion("port >", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortGreaterThanOrEqualTo(String value) {
            addCriterion("port >=", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortLessThan(String value) {
            addCriterion("port <", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortLessThanOrEqualTo(String value) {
            addCriterion("port <=", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortLike(String value) {
            addCriterion("port like", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotLike(String value) {
            addCriterion("port not like", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortIn(List<String> values) {
            addCriterion("port in", values, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotIn(List<String> values) {
            addCriterion("port not in", values, "port");
            return (Criteria) this;
        }

        public Criteria andPortBetween(String value1, String value2) {
            addCriterion("port between", value1, value2, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotBetween(String value1, String value2) {
            addCriterion("port not between", value1, value2, "port");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
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

        public Criteria andDrivernameIsNull() {
            addCriterion("drivername is null");
            return (Criteria) this;
        }

        public Criteria andDrivernameIsNotNull() {
            addCriterion("drivername is not null");
            return (Criteria) this;
        }

        public Criteria andDrivernameEqualTo(String value) {
            addCriterion("drivername =", value, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameNotEqualTo(String value) {
            addCriterion("drivername <>", value, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameGreaterThan(String value) {
            addCriterion("drivername >", value, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameGreaterThanOrEqualTo(String value) {
            addCriterion("drivername >=", value, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameLessThan(String value) {
            addCriterion("drivername <", value, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameLessThanOrEqualTo(String value) {
            addCriterion("drivername <=", value, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameLike(String value) {
            addCriterion("drivername like", value, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameNotLike(String value) {
            addCriterion("drivername not like", value, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameIn(List<String> values) {
            addCriterion("drivername in", values, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameNotIn(List<String> values) {
            addCriterion("drivername not in", values, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameBetween(String value1, String value2) {
            addCriterion("drivername between", value1, value2, "drivername");
            return (Criteria) this;
        }

        public Criteria andDrivernameNotBetween(String value1, String value2) {
            addCriterion("drivername not between", value1, value2, "drivername");
            return (Criteria) this;
        }

        public Criteria andMapperpathIsNull() {
            addCriterion("mapperPath is null");
            return (Criteria) this;
        }

        public Criteria andMapperpathIsNotNull() {
            addCriterion("mapperPath is not null");
            return (Criteria) this;
        }

        public Criteria andMapperpathEqualTo(String value) {
            addCriterion("mapperPath =", value, "mapperpath");
            return (Criteria) this;
        }

        public Criteria andMapperpathNotEqualTo(String value) {
            addCriterion("mapperPath <>", value, "mapperpath");
            return (Criteria) this;
        }

        public Criteria andMapperpathGreaterThan(String value) {
            addCriterion("mapperPath >", value, "mapperpath");
            return (Criteria) this;
        }

        public Criteria andMapperpathGreaterThanOrEqualTo(String value) {
            addCriterion("mapperPath >=", value, "mapperpath");
            return (Criteria) this;
        }

        public Criteria andMapperpathLessThan(String value) {
            addCriterion("mapperPath <", value, "mapperpath");
            return (Criteria) this;
        }

        public Criteria andMapperpathLessThanOrEqualTo(String value) {
            addCriterion("mapperPath <=", value, "mapperpath");
            return (Criteria) this;
        }

        public Criteria andMapperpathLike(String value) {
            addCriterion("mapperPath like", value, "mapperpath");
            return (Criteria) this;
        }

        public Criteria andMapperpathNotLike(String value) {
            addCriterion("mapperPath not like", value, "mapperpath");
            return (Criteria) this;
        }

        public Criteria andMapperpathIn(List<String> values) {
            addCriterion("mapperPath in", values, "mapperpath");
            return (Criteria) this;
        }

        public Criteria andMapperpathNotIn(List<String> values) {
            addCriterion("mapperPath not in", values, "mapperpath");
            return (Criteria) this;
        }

        public Criteria andMapperpathBetween(String value1, String value2) {
            addCriterion("mapperPath between", value1, value2, "mapperpath");
            return (Criteria) this;
        }

        public Criteria andMapperpathNotBetween(String value1, String value2) {
            addCriterion("mapperPath not between", value1, value2, "mapperpath");
            return (Criteria) this;
        }

        public Criteria andMapperpackageIsNull() {
            addCriterion("mapperPackage is null");
            return (Criteria) this;
        }

        public Criteria andMapperpackageIsNotNull() {
            addCriterion("mapperPackage is not null");
            return (Criteria) this;
        }

        public Criteria andMapperpackageEqualTo(String value) {
            addCriterion("mapperPackage =", value, "mapperpackage");
            return (Criteria) this;
        }

        public Criteria andMapperpackageNotEqualTo(String value) {
            addCriterion("mapperPackage <>", value, "mapperpackage");
            return (Criteria) this;
        }

        public Criteria andMapperpackageGreaterThan(String value) {
            addCriterion("mapperPackage >", value, "mapperpackage");
            return (Criteria) this;
        }

        public Criteria andMapperpackageGreaterThanOrEqualTo(String value) {
            addCriterion("mapperPackage >=", value, "mapperpackage");
            return (Criteria) this;
        }

        public Criteria andMapperpackageLessThan(String value) {
            addCriterion("mapperPackage <", value, "mapperpackage");
            return (Criteria) this;
        }

        public Criteria andMapperpackageLessThanOrEqualTo(String value) {
            addCriterion("mapperPackage <=", value, "mapperpackage");
            return (Criteria) this;
        }

        public Criteria andMapperpackageLike(String value) {
            addCriterion("mapperPackage like", value, "mapperpackage");
            return (Criteria) this;
        }

        public Criteria andMapperpackageNotLike(String value) {
            addCriterion("mapperPackage not like", value, "mapperpackage");
            return (Criteria) this;
        }

        public Criteria andMapperpackageIn(List<String> values) {
            addCriterion("mapperPackage in", values, "mapperpackage");
            return (Criteria) this;
        }

        public Criteria andMapperpackageNotIn(List<String> values) {
            addCriterion("mapperPackage not in", values, "mapperpackage");
            return (Criteria) this;
        }

        public Criteria andMapperpackageBetween(String value1, String value2) {
            addCriterion("mapperPackage between", value1, value2, "mapperpackage");
            return (Criteria) this;
        }

        public Criteria andMapperpackageNotBetween(String value1, String value2) {
            addCriterion("mapperPackage not between", value1, value2, "mapperpackage");
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

        public Criteria andSynctimeIsNull() {
            addCriterion("synctime is null");
            return (Criteria) this;
        }

        public Criteria andSynctimeIsNotNull() {
            addCriterion("synctime is not null");
            return (Criteria) this;
        }

        public Criteria andSynctimeEqualTo(Date value) {
            addCriterion("synctime =", value, "synctime");
            return (Criteria) this;
        }

        public Criteria andSynctimeNotEqualTo(Date value) {
            addCriterion("synctime <>", value, "synctime");
            return (Criteria) this;
        }

        public Criteria andSynctimeGreaterThan(Date value) {
            addCriterion("synctime >", value, "synctime");
            return (Criteria) this;
        }

        public Criteria andSynctimeGreaterThanOrEqualTo(Date value) {
            addCriterion("synctime >=", value, "synctime");
            return (Criteria) this;
        }

        public Criteria andSynctimeLessThan(Date value) {
            addCriterion("synctime <", value, "synctime");
            return (Criteria) this;
        }

        public Criteria andSynctimeLessThanOrEqualTo(Date value) {
            addCriterion("synctime <=", value, "synctime");
            return (Criteria) this;
        }

        public Criteria andSynctimeIn(List<Date> values) {
            addCriterion("synctime in", values, "synctime");
            return (Criteria) this;
        }

        public Criteria andSynctimeNotIn(List<Date> values) {
            addCriterion("synctime not in", values, "synctime");
            return (Criteria) this;
        }

        public Criteria andSynctimeBetween(Date value1, Date value2) {
            addCriterion("synctime between", value1, value2, "synctime");
            return (Criteria) this;
        }

        public Criteria andSynctimeNotBetween(Date value1, Date value2) {
            addCriterion("synctime not between", value1, value2, "synctime");
            return (Criteria) this;
        }

        public Criteria andSyncuserIsNull() {
            addCriterion("syncuser is null");
            return (Criteria) this;
        }

        public Criteria andSyncuserIsNotNull() {
            addCriterion("syncuser is not null");
            return (Criteria) this;
        }

        public Criteria andSyncuserEqualTo(Long value) {
            addCriterion("syncuser =", value, "syncuser");
            return (Criteria) this;
        }

        public Criteria andSyncuserNotEqualTo(Long value) {
            addCriterion("syncuser <>", value, "syncuser");
            return (Criteria) this;
        }

        public Criteria andSyncuserGreaterThan(Long value) {
            addCriterion("syncuser >", value, "syncuser");
            return (Criteria) this;
        }

        public Criteria andSyncuserGreaterThanOrEqualTo(Long value) {
            addCriterion("syncuser >=", value, "syncuser");
            return (Criteria) this;
        }

        public Criteria andSyncuserLessThan(Long value) {
            addCriterion("syncuser <", value, "syncuser");
            return (Criteria) this;
        }

        public Criteria andSyncuserLessThanOrEqualTo(Long value) {
            addCriterion("syncuser <=", value, "syncuser");
            return (Criteria) this;
        }

        public Criteria andSyncuserIn(List<Long> values) {
            addCriterion("syncuser in", values, "syncuser");
            return (Criteria) this;
        }

        public Criteria andSyncuserNotIn(List<Long> values) {
            addCriterion("syncuser not in", values, "syncuser");
            return (Criteria) this;
        }

        public Criteria andSyncuserBetween(Long value1, Long value2) {
            addCriterion("syncuser between", value1, value2, "syncuser");
            return (Criteria) this;
        }

        public Criteria andSyncuserNotBetween(Long value1, Long value2) {
            addCriterion("syncuser not between", value1, value2, "syncuser");
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