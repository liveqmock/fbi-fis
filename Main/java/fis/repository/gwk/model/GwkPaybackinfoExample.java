package fis.repository.gwk.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GwkPaybackinfoExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table FIS.GWK_PAYBACKINFO
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table FIS.GWK_PAYBACKINFO
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table FIS.GWK_PAYBACKINFO
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_PAYBACKINFO
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    public GwkPaybackinfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_PAYBACKINFO
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_PAYBACKINFO
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_PAYBACKINFO
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_PAYBACKINFO
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_PAYBACKINFO
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_PAYBACKINFO
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_PAYBACKINFO
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_PAYBACKINFO
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
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
     * This method corresponds to the database table FIS.GWK_PAYBACKINFO
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_PAYBACKINFO
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table FIS.GWK_PAYBACKINFO
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
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

        public Criteria andVoucheridIsNull() {
            addCriterion("VOUCHERID is null");
            return (Criteria) this;
        }

        public Criteria andVoucheridIsNotNull() {
            addCriterion("VOUCHERID is not null");
            return (Criteria) this;
        }

        public Criteria andVoucheridEqualTo(String value) {
            addCriterion("VOUCHERID =", value, "voucherid");
            return (Criteria) this;
        }

        public Criteria andVoucheridNotEqualTo(String value) {
            addCriterion("VOUCHERID <>", value, "voucherid");
            return (Criteria) this;
        }

        public Criteria andVoucheridGreaterThan(String value) {
            addCriterion("VOUCHERID >", value, "voucherid");
            return (Criteria) this;
        }

        public Criteria andVoucheridGreaterThanOrEqualTo(String value) {
            addCriterion("VOUCHERID >=", value, "voucherid");
            return (Criteria) this;
        }

        public Criteria andVoucheridLessThan(String value) {
            addCriterion("VOUCHERID <", value, "voucherid");
            return (Criteria) this;
        }

        public Criteria andVoucheridLessThanOrEqualTo(String value) {
            addCriterion("VOUCHERID <=", value, "voucherid");
            return (Criteria) this;
        }

        public Criteria andVoucheridLike(String value) {
            addCriterion("VOUCHERID like", value, "voucherid");
            return (Criteria) this;
        }

        public Criteria andVoucheridNotLike(String value) {
            addCriterion("VOUCHERID not like", value, "voucherid");
            return (Criteria) this;
        }

        public Criteria andVoucheridIn(List<String> values) {
            addCriterion("VOUCHERID in", values, "voucherid");
            return (Criteria) this;
        }

        public Criteria andVoucheridNotIn(List<String> values) {
            addCriterion("VOUCHERID not in", values, "voucherid");
            return (Criteria) this;
        }

        public Criteria andVoucheridBetween(String value1, String value2) {
            addCriterion("VOUCHERID between", value1, value2, "voucherid");
            return (Criteria) this;
        }

        public Criteria andVoucheridNotBetween(String value1, String value2) {
            addCriterion("VOUCHERID not between", value1, value2, "voucherid");
            return (Criteria) this;
        }

        public Criteria andAccountIsNull() {
            addCriterion("ACCOUNT is null");
            return (Criteria) this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("ACCOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEqualTo(String value) {
            addCriterion("ACCOUNT =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(String value) {
            addCriterion("ACCOUNT <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(String value) {
            addCriterion("ACCOUNT >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(String value) {
            addCriterion("ACCOUNT >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(String value) {
            addCriterion("ACCOUNT <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(String value) {
            addCriterion("ACCOUNT <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLike(String value) {
            addCriterion("ACCOUNT like", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotLike(String value) {
            addCriterion("ACCOUNT not like", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<String> values) {
            addCriterion("ACCOUNT in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<String> values) {
            addCriterion("ACCOUNT not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(String value1, String value2) {
            addCriterion("ACCOUNT between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(String value1, String value2) {
            addCriterion("ACCOUNT not between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andCardnameIsNull() {
            addCriterion("CARDNAME is null");
            return (Criteria) this;
        }

        public Criteria andCardnameIsNotNull() {
            addCriterion("CARDNAME is not null");
            return (Criteria) this;
        }

        public Criteria andCardnameEqualTo(String value) {
            addCriterion("CARDNAME =", value, "cardname");
            return (Criteria) this;
        }

        public Criteria andCardnameNotEqualTo(String value) {
            addCriterion("CARDNAME <>", value, "cardname");
            return (Criteria) this;
        }

        public Criteria andCardnameGreaterThan(String value) {
            addCriterion("CARDNAME >", value, "cardname");
            return (Criteria) this;
        }

        public Criteria andCardnameGreaterThanOrEqualTo(String value) {
            addCriterion("CARDNAME >=", value, "cardname");
            return (Criteria) this;
        }

        public Criteria andCardnameLessThan(String value) {
            addCriterion("CARDNAME <", value, "cardname");
            return (Criteria) this;
        }

        public Criteria andCardnameLessThanOrEqualTo(String value) {
            addCriterion("CARDNAME <=", value, "cardname");
            return (Criteria) this;
        }

        public Criteria andCardnameLike(String value) {
            addCriterion("CARDNAME like", value, "cardname");
            return (Criteria) this;
        }

        public Criteria andCardnameNotLike(String value) {
            addCriterion("CARDNAME not like", value, "cardname");
            return (Criteria) this;
        }

        public Criteria andCardnameIn(List<String> values) {
            addCriterion("CARDNAME in", values, "cardname");
            return (Criteria) this;
        }

        public Criteria andCardnameNotIn(List<String> values) {
            addCriterion("CARDNAME not in", values, "cardname");
            return (Criteria) this;
        }

        public Criteria andCardnameBetween(String value1, String value2) {
            addCriterion("CARDNAME between", value1, value2, "cardname");
            return (Criteria) this;
        }

        public Criteria andCardnameNotBetween(String value1, String value2) {
            addCriterion("CARDNAME not between", value1, value2, "cardname");
            return (Criteria) this;
        }

        public Criteria andAmtIsNull() {
            addCriterion("AMT is null");
            return (Criteria) this;
        }

        public Criteria andAmtIsNotNull() {
            addCriterion("AMT is not null");
            return (Criteria) this;
        }

        public Criteria andAmtEqualTo(BigDecimal value) {
            addCriterion("AMT =", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtNotEqualTo(BigDecimal value) {
            addCriterion("AMT <>", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtGreaterThan(BigDecimal value) {
            addCriterion("AMT >", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AMT >=", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtLessThan(BigDecimal value) {
            addCriterion("AMT <", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AMT <=", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtIn(List<BigDecimal> values) {
            addCriterion("AMT in", values, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtNotIn(List<BigDecimal> values) {
            addCriterion("AMT not in", values, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AMT between", value1, value2, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AMT not between", value1, value2, "amt");
            return (Criteria) this;
        }

        public Criteria andQuerydateIsNull() {
            addCriterion("QUERYDATE is null");
            return (Criteria) this;
        }

        public Criteria andQuerydateIsNotNull() {
            addCriterion("QUERYDATE is not null");
            return (Criteria) this;
        }

        public Criteria andQuerydateEqualTo(String value) {
            addCriterion("QUERYDATE =", value, "querydate");
            return (Criteria) this;
        }

        public Criteria andQuerydateNotEqualTo(String value) {
            addCriterion("QUERYDATE <>", value, "querydate");
            return (Criteria) this;
        }

        public Criteria andQuerydateGreaterThan(String value) {
            addCriterion("QUERYDATE >", value, "querydate");
            return (Criteria) this;
        }

        public Criteria andQuerydateGreaterThanOrEqualTo(String value) {
            addCriterion("QUERYDATE >=", value, "querydate");
            return (Criteria) this;
        }

        public Criteria andQuerydateLessThan(String value) {
            addCriterion("QUERYDATE <", value, "querydate");
            return (Criteria) this;
        }

        public Criteria andQuerydateLessThanOrEqualTo(String value) {
            addCriterion("QUERYDATE <=", value, "querydate");
            return (Criteria) this;
        }

        public Criteria andQuerydateLike(String value) {
            addCriterion("QUERYDATE like", value, "querydate");
            return (Criteria) this;
        }

        public Criteria andQuerydateNotLike(String value) {
            addCriterion("QUERYDATE not like", value, "querydate");
            return (Criteria) this;
        }

        public Criteria andQuerydateIn(List<String> values) {
            addCriterion("QUERYDATE in", values, "querydate");
            return (Criteria) this;
        }

        public Criteria andQuerydateNotIn(List<String> values) {
            addCriterion("QUERYDATE not in", values, "querydate");
            return (Criteria) this;
        }

        public Criteria andQuerydateBetween(String value1, String value2) {
            addCriterion("QUERYDATE between", value1, value2, "querydate");
            return (Criteria) this;
        }

        public Criteria andQuerydateNotBetween(String value1, String value2) {
            addCriterion("QUERYDATE not between", value1, value2, "querydate");
            return (Criteria) this;
        }

        public Criteria andOperidIsNull() {
            addCriterion("OPERID is null");
            return (Criteria) this;
        }

        public Criteria andOperidIsNotNull() {
            addCriterion("OPERID is not null");
            return (Criteria) this;
        }

        public Criteria andOperidEqualTo(String value) {
            addCriterion("OPERID =", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidNotEqualTo(String value) {
            addCriterion("OPERID <>", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidGreaterThan(String value) {
            addCriterion("OPERID >", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidGreaterThanOrEqualTo(String value) {
            addCriterion("OPERID >=", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidLessThan(String value) {
            addCriterion("OPERID <", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidLessThanOrEqualTo(String value) {
            addCriterion("OPERID <=", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidLike(String value) {
            addCriterion("OPERID like", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidNotLike(String value) {
            addCriterion("OPERID not like", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidIn(List<String> values) {
            addCriterion("OPERID in", values, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidNotIn(List<String> values) {
            addCriterion("OPERID not in", values, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidBetween(String value1, String value2) {
            addCriterion("OPERID between", value1, value2, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidNotBetween(String value1, String value2) {
            addCriterion("OPERID not between", value1, value2, "operid");
            return (Criteria) this;
        }

        public Criteria andOperdateIsNull() {
            addCriterion("OPERDATE is null");
            return (Criteria) this;
        }

        public Criteria andOperdateIsNotNull() {
            addCriterion("OPERDATE is not null");
            return (Criteria) this;
        }

        public Criteria andOperdateEqualTo(Date value) {
            addCriterion("OPERDATE =", value, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateNotEqualTo(Date value) {
            addCriterion("OPERDATE <>", value, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateGreaterThan(Date value) {
            addCriterion("OPERDATE >", value, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateGreaterThanOrEqualTo(Date value) {
            addCriterion("OPERDATE >=", value, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateLessThan(Date value) {
            addCriterion("OPERDATE <", value, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateLessThanOrEqualTo(Date value) {
            addCriterion("OPERDATE <=", value, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateIn(List<Date> values) {
            addCriterion("OPERDATE in", values, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateNotIn(List<Date> values) {
            addCriterion("OPERDATE not in", values, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateBetween(Date value1, Date value2) {
            addCriterion("OPERDATE between", value1, value2, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateNotBetween(Date value1, Date value2) {
            addCriterion("OPERDATE not between", value1, value2, "operdate");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("STATUS like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("STATUS not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andPaybackdateIsNull() {
            addCriterion("PAYBACKDATE is null");
            return (Criteria) this;
        }

        public Criteria andPaybackdateIsNotNull() {
            addCriterion("PAYBACKDATE is not null");
            return (Criteria) this;
        }

        public Criteria andPaybackdateEqualTo(String value) {
            addCriterion("PAYBACKDATE =", value, "paybackdate");
            return (Criteria) this;
        }

        public Criteria andPaybackdateNotEqualTo(String value) {
            addCriterion("PAYBACKDATE <>", value, "paybackdate");
            return (Criteria) this;
        }

        public Criteria andPaybackdateGreaterThan(String value) {
            addCriterion("PAYBACKDATE >", value, "paybackdate");
            return (Criteria) this;
        }

        public Criteria andPaybackdateGreaterThanOrEqualTo(String value) {
            addCriterion("PAYBACKDATE >=", value, "paybackdate");
            return (Criteria) this;
        }

        public Criteria andPaybackdateLessThan(String value) {
            addCriterion("PAYBACKDATE <", value, "paybackdate");
            return (Criteria) this;
        }

        public Criteria andPaybackdateLessThanOrEqualTo(String value) {
            addCriterion("PAYBACKDATE <=", value, "paybackdate");
            return (Criteria) this;
        }

        public Criteria andPaybackdateLike(String value) {
            addCriterion("PAYBACKDATE like", value, "paybackdate");
            return (Criteria) this;
        }

        public Criteria andPaybackdateNotLike(String value) {
            addCriterion("PAYBACKDATE not like", value, "paybackdate");
            return (Criteria) this;
        }

        public Criteria andPaybackdateIn(List<String> values) {
            addCriterion("PAYBACKDATE in", values, "paybackdate");
            return (Criteria) this;
        }

        public Criteria andPaybackdateNotIn(List<String> values) {
            addCriterion("PAYBACKDATE not in", values, "paybackdate");
            return (Criteria) this;
        }

        public Criteria andPaybackdateBetween(String value1, String value2) {
            addCriterion("PAYBACKDATE between", value1, value2, "paybackdate");
            return (Criteria) this;
        }

        public Criteria andPaybackdateNotBetween(String value1, String value2) {
            addCriterion("PAYBACKDATE not between", value1, value2, "paybackdate");
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

        public Criteria andYearIsNull() {
            addCriterion("YEAR is null");
            return (Criteria) this;
        }

        public Criteria andYearIsNotNull() {
            addCriterion("YEAR is not null");
            return (Criteria) this;
        }

        public Criteria andYearEqualTo(String value) {
            addCriterion("YEAR =", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotEqualTo(String value) {
            addCriterion("YEAR <>", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThan(String value) {
            addCriterion("YEAR >", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThanOrEqualTo(String value) {
            addCriterion("YEAR >=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThan(String value) {
            addCriterion("YEAR <", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThanOrEqualTo(String value) {
            addCriterion("YEAR <=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLike(String value) {
            addCriterion("YEAR like", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotLike(String value) {
            addCriterion("YEAR not like", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearIn(List<String> values) {
            addCriterion("YEAR in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotIn(List<String> values) {
            addCriterion("YEAR not in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearBetween(String value1, String value2) {
            addCriterion("YEAR between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotBetween(String value1, String value2) {
            addCriterion("YEAR not between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andBranchidIsNull() {
            addCriterion("BRANCHID is null");
            return (Criteria) this;
        }

        public Criteria andBranchidIsNotNull() {
            addCriterion("BRANCHID is not null");
            return (Criteria) this;
        }

        public Criteria andBranchidEqualTo(String value) {
            addCriterion("BRANCHID =", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidNotEqualTo(String value) {
            addCriterion("BRANCHID <>", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidGreaterThan(String value) {
            addCriterion("BRANCHID >", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidGreaterThanOrEqualTo(String value) {
            addCriterion("BRANCHID >=", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidLessThan(String value) {
            addCriterion("BRANCHID <", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidLessThanOrEqualTo(String value) {
            addCriterion("BRANCHID <=", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidLike(String value) {
            addCriterion("BRANCHID like", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidNotLike(String value) {
            addCriterion("BRANCHID not like", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidIn(List<String> values) {
            addCriterion("BRANCHID in", values, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidNotIn(List<String> values) {
            addCriterion("BRANCHID not in", values, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidBetween(String value1, String value2) {
            addCriterion("BRANCHID between", value1, value2, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidNotBetween(String value1, String value2) {
            addCriterion("BRANCHID not between", value1, value2, "branchid");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table FIS.GWK_PAYBACKINFO
     *
     * @mbggenerated do_not_delete_during_merge Fri Jan 06 07:43:12 CST 2012
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table FIS.GWK_PAYBACKINFO
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
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