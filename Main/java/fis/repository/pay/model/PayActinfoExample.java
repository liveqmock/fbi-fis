package fis.repository.pay.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PayActinfoExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table FIS.PAY_ACTINFO
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table FIS.PAY_ACTINFO
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table FIS.PAY_ACTINFO
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_ACTINFO
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    public PayActinfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_ACTINFO
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_ACTINFO
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_ACTINFO
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_ACTINFO
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_ACTINFO
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_ACTINFO
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_ACTINFO
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_ACTINFO
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_ACTINFO
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_ACTINFO
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table FIS.PAY_ACTINFO
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
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

        public Criteria andPkidIsNull() {
            addCriterion("PKID is null");
            return (Criteria) this;
        }

        public Criteria andPkidIsNotNull() {
            addCriterion("PKID is not null");
            return (Criteria) this;
        }

        public Criteria andPkidEqualTo(String value) {
            addCriterion("PKID =", value, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidNotEqualTo(String value) {
            addCriterion("PKID <>", value, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidGreaterThan(String value) {
            addCriterion("PKID >", value, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidGreaterThanOrEqualTo(String value) {
            addCriterion("PKID >=", value, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidLessThan(String value) {
            addCriterion("PKID <", value, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidLessThanOrEqualTo(String value) {
            addCriterion("PKID <=", value, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidLike(String value) {
            addCriterion("PKID like", value, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidNotLike(String value) {
            addCriterion("PKID not like", value, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidIn(List<String> values) {
            addCriterion("PKID in", values, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidNotIn(List<String> values) {
            addCriterion("PKID not in", values, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidBetween(String value1, String value2) {
            addCriterion("PKID between", value1, value2, "pkid");
            return (Criteria) this;
        }

        public Criteria andPkidNotBetween(String value1, String value2) {
            addCriterion("PKID not between", value1, value2, "pkid");
            return (Criteria) this;
        }

        public Criteria andActnoIsNull() {
            addCriterion("ACTNO is null");
            return (Criteria) this;
        }

        public Criteria andActnoIsNotNull() {
            addCriterion("ACTNO is not null");
            return (Criteria) this;
        }

        public Criteria andActnoEqualTo(String value) {
            addCriterion("ACTNO =", value, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoNotEqualTo(String value) {
            addCriterion("ACTNO <>", value, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoGreaterThan(String value) {
            addCriterion("ACTNO >", value, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoGreaterThanOrEqualTo(String value) {
            addCriterion("ACTNO >=", value, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoLessThan(String value) {
            addCriterion("ACTNO <", value, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoLessThanOrEqualTo(String value) {
            addCriterion("ACTNO <=", value, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoLike(String value) {
            addCriterion("ACTNO like", value, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoNotLike(String value) {
            addCriterion("ACTNO not like", value, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoIn(List<String> values) {
            addCriterion("ACTNO in", values, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoNotIn(List<String> values) {
            addCriterion("ACTNO not in", values, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoBetween(String value1, String value2) {
            addCriterion("ACTNO between", value1, value2, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoNotBetween(String value1, String value2) {
            addCriterion("ACTNO not between", value1, value2, "actno");
            return (Criteria) this;
        }

        public Criteria andActnameIsNull() {
            addCriterion("ACTNAME is null");
            return (Criteria) this;
        }

        public Criteria andActnameIsNotNull() {
            addCriterion("ACTNAME is not null");
            return (Criteria) this;
        }

        public Criteria andActnameEqualTo(String value) {
            addCriterion("ACTNAME =", value, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameNotEqualTo(String value) {
            addCriterion("ACTNAME <>", value, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameGreaterThan(String value) {
            addCriterion("ACTNAME >", value, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameGreaterThanOrEqualTo(String value) {
            addCriterion("ACTNAME >=", value, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameLessThan(String value) {
            addCriterion("ACTNAME <", value, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameLessThanOrEqualTo(String value) {
            addCriterion("ACTNAME <=", value, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameLike(String value) {
            addCriterion("ACTNAME like", value, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameNotLike(String value) {
            addCriterion("ACTNAME not like", value, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameIn(List<String> values) {
            addCriterion("ACTNAME in", values, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameNotIn(List<String> values) {
            addCriterion("ACTNAME not in", values, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameBetween(String value1, String value2) {
            addCriterion("ACTNAME between", value1, value2, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameNotBetween(String value1, String value2) {
            addCriterion("ACTNAME not between", value1, value2, "actname");
            return (Criteria) this;
        }

        public Criteria andActtypeIsNull() {
            addCriterion("ACTTYPE is null");
            return (Criteria) this;
        }

        public Criteria andActtypeIsNotNull() {
            addCriterion("ACTTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andActtypeEqualTo(String value) {
            addCriterion("ACTTYPE =", value, "acttype");
            return (Criteria) this;
        }

        public Criteria andActtypeNotEqualTo(String value) {
            addCriterion("ACTTYPE <>", value, "acttype");
            return (Criteria) this;
        }

        public Criteria andActtypeGreaterThan(String value) {
            addCriterion("ACTTYPE >", value, "acttype");
            return (Criteria) this;
        }

        public Criteria andActtypeGreaterThanOrEqualTo(String value) {
            addCriterion("ACTTYPE >=", value, "acttype");
            return (Criteria) this;
        }

        public Criteria andActtypeLessThan(String value) {
            addCriterion("ACTTYPE <", value, "acttype");
            return (Criteria) this;
        }

        public Criteria andActtypeLessThanOrEqualTo(String value) {
            addCriterion("ACTTYPE <=", value, "acttype");
            return (Criteria) this;
        }

        public Criteria andActtypeLike(String value) {
            addCriterion("ACTTYPE like", value, "acttype");
            return (Criteria) this;
        }

        public Criteria andActtypeNotLike(String value) {
            addCriterion("ACTTYPE not like", value, "acttype");
            return (Criteria) this;
        }

        public Criteria andActtypeIn(List<String> values) {
            addCriterion("ACTTYPE in", values, "acttype");
            return (Criteria) this;
        }

        public Criteria andActtypeNotIn(List<String> values) {
            addCriterion("ACTTYPE not in", values, "acttype");
            return (Criteria) this;
        }

        public Criteria andActtypeBetween(String value1, String value2) {
            addCriterion("ACTTYPE between", value1, value2, "acttype");
            return (Criteria) this;
        }

        public Criteria andActtypeNotBetween(String value1, String value2) {
            addCriterion("ACTTYPE not between", value1, value2, "acttype");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andDeletedFlagIsNull() {
            addCriterion("DELETED_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andDeletedFlagIsNotNull() {
            addCriterion("DELETED_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedFlagEqualTo(String value) {
            addCriterion("DELETED_FLAG =", value, "deletedFlag");
            return (Criteria) this;
        }

        public Criteria andDeletedFlagNotEqualTo(String value) {
            addCriterion("DELETED_FLAG <>", value, "deletedFlag");
            return (Criteria) this;
        }

        public Criteria andDeletedFlagGreaterThan(String value) {
            addCriterion("DELETED_FLAG >", value, "deletedFlag");
            return (Criteria) this;
        }

        public Criteria andDeletedFlagGreaterThanOrEqualTo(String value) {
            addCriterion("DELETED_FLAG >=", value, "deletedFlag");
            return (Criteria) this;
        }

        public Criteria andDeletedFlagLessThan(String value) {
            addCriterion("DELETED_FLAG <", value, "deletedFlag");
            return (Criteria) this;
        }

        public Criteria andDeletedFlagLessThanOrEqualTo(String value) {
            addCriterion("DELETED_FLAG <=", value, "deletedFlag");
            return (Criteria) this;
        }

        public Criteria andDeletedFlagLike(String value) {
            addCriterion("DELETED_FLAG like", value, "deletedFlag");
            return (Criteria) this;
        }

        public Criteria andDeletedFlagNotLike(String value) {
            addCriterion("DELETED_FLAG not like", value, "deletedFlag");
            return (Criteria) this;
        }

        public Criteria andDeletedFlagIn(List<String> values) {
            addCriterion("DELETED_FLAG in", values, "deletedFlag");
            return (Criteria) this;
        }

        public Criteria andDeletedFlagNotIn(List<String> values) {
            addCriterion("DELETED_FLAG not in", values, "deletedFlag");
            return (Criteria) this;
        }

        public Criteria andDeletedFlagBetween(String value1, String value2) {
            addCriterion("DELETED_FLAG between", value1, value2, "deletedFlag");
            return (Criteria) this;
        }

        public Criteria andDeletedFlagNotBetween(String value1, String value2) {
            addCriterion("DELETED_FLAG not between", value1, value2, "deletedFlag");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNull() {
            addCriterion("CREATED_BY is null");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNotNull() {
            addCriterion("CREATED_BY is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedByEqualTo(String value) {
            addCriterion("CREATED_BY =", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotEqualTo(String value) {
            addCriterion("CREATED_BY <>", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThan(String value) {
            addCriterion("CREATED_BY >", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThanOrEqualTo(String value) {
            addCriterion("CREATED_BY >=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThan(String value) {
            addCriterion("CREATED_BY <", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThanOrEqualTo(String value) {
            addCriterion("CREATED_BY <=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLike(String value) {
            addCriterion("CREATED_BY like", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotLike(String value) {
            addCriterion("CREATED_BY not like", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByIn(List<String> values) {
            addCriterion("CREATED_BY in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotIn(List<String> values) {
            addCriterion("CREATED_BY not in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByBetween(String value1, String value2) {
            addCriterion("CREATED_BY between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotBetween(String value1, String value2) {
            addCriterion("CREATED_BY not between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNull() {
            addCriterion("CREATED_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNotNull() {
            addCriterion("CREATED_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateEqualTo(Date value) {
            addCriterion("CREATED_DATE =", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotEqualTo(Date value) {
            addCriterion("CREATED_DATE <>", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThan(Date value) {
            addCriterion("CREATED_DATE >", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATED_DATE >=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThan(Date value) {
            addCriterion("CREATED_DATE <", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThanOrEqualTo(Date value) {
            addCriterion("CREATED_DATE <=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIn(List<Date> values) {
            addCriterion("CREATED_DATE in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotIn(List<Date> values) {
            addCriterion("CREATED_DATE not in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateBetween(Date value1, Date value2) {
            addCriterion("CREATED_DATE between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotBetween(Date value1, Date value2) {
            addCriterion("CREATED_DATE not between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdByIsNull() {
            addCriterion("LAST_UPD_BY is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdByIsNotNull() {
            addCriterion("LAST_UPD_BY is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdByEqualTo(String value) {
            addCriterion("LAST_UPD_BY =", value, "lastUpdBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdByNotEqualTo(String value) {
            addCriterion("LAST_UPD_BY <>", value, "lastUpdBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdByGreaterThan(String value) {
            addCriterion("LAST_UPD_BY >", value, "lastUpdBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdByGreaterThanOrEqualTo(String value) {
            addCriterion("LAST_UPD_BY >=", value, "lastUpdBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdByLessThan(String value) {
            addCriterion("LAST_UPD_BY <", value, "lastUpdBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdByLessThanOrEqualTo(String value) {
            addCriterion("LAST_UPD_BY <=", value, "lastUpdBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdByLike(String value) {
            addCriterion("LAST_UPD_BY like", value, "lastUpdBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdByNotLike(String value) {
            addCriterion("LAST_UPD_BY not like", value, "lastUpdBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdByIn(List<String> values) {
            addCriterion("LAST_UPD_BY in", values, "lastUpdBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdByNotIn(List<String> values) {
            addCriterion("LAST_UPD_BY not in", values, "lastUpdBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdByBetween(String value1, String value2) {
            addCriterion("LAST_UPD_BY between", value1, value2, "lastUpdBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdByNotBetween(String value1, String value2) {
            addCriterion("LAST_UPD_BY not between", value1, value2, "lastUpdBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdDateIsNull() {
            addCriterion("LAST_UPD_DATE is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdDateIsNotNull() {
            addCriterion("LAST_UPD_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdDateEqualTo(Date value) {
            addCriterion("LAST_UPD_DATE =", value, "lastUpdDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdDateNotEqualTo(Date value) {
            addCriterion("LAST_UPD_DATE <>", value, "lastUpdDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdDateGreaterThan(Date value) {
            addCriterion("LAST_UPD_DATE >", value, "lastUpdDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdDateGreaterThanOrEqualTo(Date value) {
            addCriterion("LAST_UPD_DATE >=", value, "lastUpdDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdDateLessThan(Date value) {
            addCriterion("LAST_UPD_DATE <", value, "lastUpdDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdDateLessThanOrEqualTo(Date value) {
            addCriterion("LAST_UPD_DATE <=", value, "lastUpdDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdDateIn(List<Date> values) {
            addCriterion("LAST_UPD_DATE in", values, "lastUpdDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdDateNotIn(List<Date> values) {
            addCriterion("LAST_UPD_DATE not in", values, "lastUpdDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdDateBetween(Date value1, Date value2) {
            addCriterion("LAST_UPD_DATE between", value1, value2, "lastUpdDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdDateNotBetween(Date value1, Date value2) {
            addCriterion("LAST_UPD_DATE not between", value1, value2, "lastUpdDate");
            return (Criteria) this;
        }

        public Criteria andRecversionIsNull() {
            addCriterion("RECVERSION is null");
            return (Criteria) this;
        }

        public Criteria andRecversionIsNotNull() {
            addCriterion("RECVERSION is not null");
            return (Criteria) this;
        }

        public Criteria andRecversionEqualTo(Integer value) {
            addCriterion("RECVERSION =", value, "recversion");
            return (Criteria) this;
        }

        public Criteria andRecversionNotEqualTo(Integer value) {
            addCriterion("RECVERSION <>", value, "recversion");
            return (Criteria) this;
        }

        public Criteria andRecversionGreaterThan(Integer value) {
            addCriterion("RECVERSION >", value, "recversion");
            return (Criteria) this;
        }

        public Criteria andRecversionGreaterThanOrEqualTo(Integer value) {
            addCriterion("RECVERSION >=", value, "recversion");
            return (Criteria) this;
        }

        public Criteria andRecversionLessThan(Integer value) {
            addCriterion("RECVERSION <", value, "recversion");
            return (Criteria) this;
        }

        public Criteria andRecversionLessThanOrEqualTo(Integer value) {
            addCriterion("RECVERSION <=", value, "recversion");
            return (Criteria) this;
        }

        public Criteria andRecversionIn(List<Integer> values) {
            addCriterion("RECVERSION in", values, "recversion");
            return (Criteria) this;
        }

        public Criteria andRecversionNotIn(List<Integer> values) {
            addCriterion("RECVERSION not in", values, "recversion");
            return (Criteria) this;
        }

        public Criteria andRecversionBetween(Integer value1, Integer value2) {
            addCriterion("RECVERSION between", value1, value2, "recversion");
            return (Criteria) this;
        }

        public Criteria andRecversionNotBetween(Integer value1, Integer value2) {
            addCriterion("RECVERSION not between", value1, value2, "recversion");
            return (Criteria) this;
        }

        public Criteria andAreacodeIsNull() {
            addCriterion("AREACODE is null");
            return (Criteria) this;
        }

        public Criteria andAreacodeIsNotNull() {
            addCriterion("AREACODE is not null");
            return (Criteria) this;
        }

        public Criteria andAreacodeEqualTo(String value) {
            addCriterion("AREACODE =", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotEqualTo(String value) {
            addCriterion("AREACODE <>", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeGreaterThan(String value) {
            addCriterion("AREACODE >", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeGreaterThanOrEqualTo(String value) {
            addCriterion("AREACODE >=", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeLessThan(String value) {
            addCriterion("AREACODE <", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeLessThanOrEqualTo(String value) {
            addCriterion("AREACODE <=", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeLike(String value) {
            addCriterion("AREACODE like", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotLike(String value) {
            addCriterion("AREACODE not like", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeIn(List<String> values) {
            addCriterion("AREACODE in", values, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotIn(List<String> values) {
            addCriterion("AREACODE not in", values, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeBetween(String value1, String value2) {
            addCriterion("AREACODE between", value1, value2, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotBetween(String value1, String value2) {
            addCriterion("AREACODE not between", value1, value2, "areacode");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table FIS.PAY_ACTINFO
     *
     * @mbggenerated do_not_delete_during_merge Mon Feb 06 14:16:38 CST 2012
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table FIS.PAY_ACTINFO
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
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