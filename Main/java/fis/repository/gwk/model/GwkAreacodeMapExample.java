package fis.repository.gwk.model;

import java.util.ArrayList;
import java.util.List;

public class GwkAreacodeMapExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table FIS.GWK_AREACODE_MAP
     *
     * @mbggenerated Mon Dec 17 21:55:18 CST 2012
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table FIS.GWK_AREACODE_MAP
     *
     * @mbggenerated Mon Dec 17 21:55:18 CST 2012
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table FIS.GWK_AREACODE_MAP
     *
     * @mbggenerated Mon Dec 17 21:55:18 CST 2012
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_AREACODE_MAP
     *
     * @mbggenerated Mon Dec 17 21:55:18 CST 2012
     */
    public GwkAreacodeMapExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_AREACODE_MAP
     *
     * @mbggenerated Mon Dec 17 21:55:18 CST 2012
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_AREACODE_MAP
     *
     * @mbggenerated Mon Dec 17 21:55:18 CST 2012
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_AREACODE_MAP
     *
     * @mbggenerated Mon Dec 17 21:55:18 CST 2012
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_AREACODE_MAP
     *
     * @mbggenerated Mon Dec 17 21:55:18 CST 2012
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_AREACODE_MAP
     *
     * @mbggenerated Mon Dec 17 21:55:18 CST 2012
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_AREACODE_MAP
     *
     * @mbggenerated Mon Dec 17 21:55:18 CST 2012
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_AREACODE_MAP
     *
     * @mbggenerated Mon Dec 17 21:55:18 CST 2012
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_AREACODE_MAP
     *
     * @mbggenerated Mon Dec 17 21:55:18 CST 2012
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
     * This method corresponds to the database table FIS.GWK_AREACODE_MAP
     *
     * @mbggenerated Mon Dec 17 21:55:18 CST 2012
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_AREACODE_MAP
     *
     * @mbggenerated Mon Dec 17 21:55:18 CST 2012
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table FIS.GWK_AREACODE_MAP
     *
     * @mbggenerated Mon Dec 17 21:55:18 CST 2012
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

        public Criteria andBranchbankcodeIsNull() {
            addCriterion("BRANCHBANKCODE is null");
            return (Criteria) this;
        }

        public Criteria andBranchbankcodeIsNotNull() {
            addCriterion("BRANCHBANKCODE is not null");
            return (Criteria) this;
        }

        public Criteria andBranchbankcodeEqualTo(String value) {
            addCriterion("BRANCHBANKCODE =", value, "branchbankcode");
            return (Criteria) this;
        }

        public Criteria andBranchbankcodeNotEqualTo(String value) {
            addCriterion("BRANCHBANKCODE <>", value, "branchbankcode");
            return (Criteria) this;
        }

        public Criteria andBranchbankcodeGreaterThan(String value) {
            addCriterion("BRANCHBANKCODE >", value, "branchbankcode");
            return (Criteria) this;
        }

        public Criteria andBranchbankcodeGreaterThanOrEqualTo(String value) {
            addCriterion("BRANCHBANKCODE >=", value, "branchbankcode");
            return (Criteria) this;
        }

        public Criteria andBranchbankcodeLessThan(String value) {
            addCriterion("BRANCHBANKCODE <", value, "branchbankcode");
            return (Criteria) this;
        }

        public Criteria andBranchbankcodeLessThanOrEqualTo(String value) {
            addCriterion("BRANCHBANKCODE <=", value, "branchbankcode");
            return (Criteria) this;
        }

        public Criteria andBranchbankcodeLike(String value) {
            addCriterion("BRANCHBANKCODE like", value, "branchbankcode");
            return (Criteria) this;
        }

        public Criteria andBranchbankcodeNotLike(String value) {
            addCriterion("BRANCHBANKCODE not like", value, "branchbankcode");
            return (Criteria) this;
        }

        public Criteria andBranchbankcodeIn(List<String> values) {
            addCriterion("BRANCHBANKCODE in", values, "branchbankcode");
            return (Criteria) this;
        }

        public Criteria andBranchbankcodeNotIn(List<String> values) {
            addCriterion("BRANCHBANKCODE not in", values, "branchbankcode");
            return (Criteria) this;
        }

        public Criteria andBranchbankcodeBetween(String value1, String value2) {
            addCriterion("BRANCHBANKCODE between", value1, value2, "branchbankcode");
            return (Criteria) this;
        }

        public Criteria andBranchbankcodeNotBetween(String value1, String value2) {
            addCriterion("BRANCHBANKCODE not between", value1, value2, "branchbankcode");
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

        public Criteria andAreanameIsNull() {
            addCriterion("AREANAME is null");
            return (Criteria) this;
        }

        public Criteria andAreanameIsNotNull() {
            addCriterion("AREANAME is not null");
            return (Criteria) this;
        }

        public Criteria andAreanameEqualTo(String value) {
            addCriterion("AREANAME =", value, "areaname");
            return (Criteria) this;
        }

        public Criteria andAreanameNotEqualTo(String value) {
            addCriterion("AREANAME <>", value, "areaname");
            return (Criteria) this;
        }

        public Criteria andAreanameGreaterThan(String value) {
            addCriterion("AREANAME >", value, "areaname");
            return (Criteria) this;
        }

        public Criteria andAreanameGreaterThanOrEqualTo(String value) {
            addCriterion("AREANAME >=", value, "areaname");
            return (Criteria) this;
        }

        public Criteria andAreanameLessThan(String value) {
            addCriterion("AREANAME <", value, "areaname");
            return (Criteria) this;
        }

        public Criteria andAreanameLessThanOrEqualTo(String value) {
            addCriterion("AREANAME <=", value, "areaname");
            return (Criteria) this;
        }

        public Criteria andAreanameLike(String value) {
            addCriterion("AREANAME like", value, "areaname");
            return (Criteria) this;
        }

        public Criteria andAreanameNotLike(String value) {
            addCriterion("AREANAME not like", value, "areaname");
            return (Criteria) this;
        }

        public Criteria andAreanameIn(List<String> values) {
            addCriterion("AREANAME in", values, "areaname");
            return (Criteria) this;
        }

        public Criteria andAreanameNotIn(List<String> values) {
            addCriterion("AREANAME not in", values, "areaname");
            return (Criteria) this;
        }

        public Criteria andAreanameBetween(String value1, String value2) {
            addCriterion("AREANAME between", value1, value2, "areaname");
            return (Criteria) this;
        }

        public Criteria andAreanameNotBetween(String value1, String value2) {
            addCriterion("AREANAME not between", value1, value2, "areaname");
            return (Criteria) this;
        }

        public Criteria andBranchbanknameIsNull() {
            addCriterion("BRANCHBANKNAME is null");
            return (Criteria) this;
        }

        public Criteria andBranchbanknameIsNotNull() {
            addCriterion("BRANCHBANKNAME is not null");
            return (Criteria) this;
        }

        public Criteria andBranchbanknameEqualTo(String value) {
            addCriterion("BRANCHBANKNAME =", value, "branchbankname");
            return (Criteria) this;
        }

        public Criteria andBranchbanknameNotEqualTo(String value) {
            addCriterion("BRANCHBANKNAME <>", value, "branchbankname");
            return (Criteria) this;
        }

        public Criteria andBranchbanknameGreaterThan(String value) {
            addCriterion("BRANCHBANKNAME >", value, "branchbankname");
            return (Criteria) this;
        }

        public Criteria andBranchbanknameGreaterThanOrEqualTo(String value) {
            addCriterion("BRANCHBANKNAME >=", value, "branchbankname");
            return (Criteria) this;
        }

        public Criteria andBranchbanknameLessThan(String value) {
            addCriterion("BRANCHBANKNAME <", value, "branchbankname");
            return (Criteria) this;
        }

        public Criteria andBranchbanknameLessThanOrEqualTo(String value) {
            addCriterion("BRANCHBANKNAME <=", value, "branchbankname");
            return (Criteria) this;
        }

        public Criteria andBranchbanknameLike(String value) {
            addCriterion("BRANCHBANKNAME like", value, "branchbankname");
            return (Criteria) this;
        }

        public Criteria andBranchbanknameNotLike(String value) {
            addCriterion("BRANCHBANKNAME not like", value, "branchbankname");
            return (Criteria) this;
        }

        public Criteria andBranchbanknameIn(List<String> values) {
            addCriterion("BRANCHBANKNAME in", values, "branchbankname");
            return (Criteria) this;
        }

        public Criteria andBranchbanknameNotIn(List<String> values) {
            addCriterion("BRANCHBANKNAME not in", values, "branchbankname");
            return (Criteria) this;
        }

        public Criteria andBranchbanknameBetween(String value1, String value2) {
            addCriterion("BRANCHBANKNAME between", value1, value2, "branchbankname");
            return (Criteria) this;
        }

        public Criteria andBranchbanknameNotBetween(String value1, String value2) {
            addCriterion("BRANCHBANKNAME not between", value1, value2, "branchbankname");
            return (Criteria) this;
        }

        public Criteria andFinbankcodeIsNull() {
            addCriterion("FINBANKCODE is null");
            return (Criteria) this;
        }

        public Criteria andFinbankcodeIsNotNull() {
            addCriterion("FINBANKCODE is not null");
            return (Criteria) this;
        }

        public Criteria andFinbankcodeEqualTo(String value) {
            addCriterion("FINBANKCODE =", value, "finbankcode");
            return (Criteria) this;
        }

        public Criteria andFinbankcodeNotEqualTo(String value) {
            addCriterion("FINBANKCODE <>", value, "finbankcode");
            return (Criteria) this;
        }

        public Criteria andFinbankcodeGreaterThan(String value) {
            addCriterion("FINBANKCODE >", value, "finbankcode");
            return (Criteria) this;
        }

        public Criteria andFinbankcodeGreaterThanOrEqualTo(String value) {
            addCriterion("FINBANKCODE >=", value, "finbankcode");
            return (Criteria) this;
        }

        public Criteria andFinbankcodeLessThan(String value) {
            addCriterion("FINBANKCODE <", value, "finbankcode");
            return (Criteria) this;
        }

        public Criteria andFinbankcodeLessThanOrEqualTo(String value) {
            addCriterion("FINBANKCODE <=", value, "finbankcode");
            return (Criteria) this;
        }

        public Criteria andFinbankcodeLike(String value) {
            addCriterion("FINBANKCODE like", value, "finbankcode");
            return (Criteria) this;
        }

        public Criteria andFinbankcodeNotLike(String value) {
            addCriterion("FINBANKCODE not like", value, "finbankcode");
            return (Criteria) this;
        }

        public Criteria andFinbankcodeIn(List<String> values) {
            addCriterion("FINBANKCODE in", values, "finbankcode");
            return (Criteria) this;
        }

        public Criteria andFinbankcodeNotIn(List<String> values) {
            addCriterion("FINBANKCODE not in", values, "finbankcode");
            return (Criteria) this;
        }

        public Criteria andFinbankcodeBetween(String value1, String value2) {
            addCriterion("FINBANKCODE between", value1, value2, "finbankcode");
            return (Criteria) this;
        }

        public Criteria andFinbankcodeNotBetween(String value1, String value2) {
            addCriterion("FINBANKCODE not between", value1, value2, "finbankcode");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctnameIsNull() {
            addCriterion("GATHERINGBANKACCTNAME is null");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctnameIsNotNull() {
            addCriterion("GATHERINGBANKACCTNAME is not null");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctnameEqualTo(String value) {
            addCriterion("GATHERINGBANKACCTNAME =", value, "gatheringbankacctname");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctnameNotEqualTo(String value) {
            addCriterion("GATHERINGBANKACCTNAME <>", value, "gatheringbankacctname");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctnameGreaterThan(String value) {
            addCriterion("GATHERINGBANKACCTNAME >", value, "gatheringbankacctname");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctnameGreaterThanOrEqualTo(String value) {
            addCriterion("GATHERINGBANKACCTNAME >=", value, "gatheringbankacctname");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctnameLessThan(String value) {
            addCriterion("GATHERINGBANKACCTNAME <", value, "gatheringbankacctname");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctnameLessThanOrEqualTo(String value) {
            addCriterion("GATHERINGBANKACCTNAME <=", value, "gatheringbankacctname");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctnameLike(String value) {
            addCriterion("GATHERINGBANKACCTNAME like", value, "gatheringbankacctname");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctnameNotLike(String value) {
            addCriterion("GATHERINGBANKACCTNAME not like", value, "gatheringbankacctname");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctnameIn(List<String> values) {
            addCriterion("GATHERINGBANKACCTNAME in", values, "gatheringbankacctname");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctnameNotIn(List<String> values) {
            addCriterion("GATHERINGBANKACCTNAME not in", values, "gatheringbankacctname");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctnameBetween(String value1, String value2) {
            addCriterion("GATHERINGBANKACCTNAME between", value1, value2, "gatheringbankacctname");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctnameNotBetween(String value1, String value2) {
            addCriterion("GATHERINGBANKACCTNAME not between", value1, value2, "gatheringbankacctname");
            return (Criteria) this;
        }

        public Criteria andGatheringbanknameIsNull() {
            addCriterion("GATHERINGBANKNAME is null");
            return (Criteria) this;
        }

        public Criteria andGatheringbanknameIsNotNull() {
            addCriterion("GATHERINGBANKNAME is not null");
            return (Criteria) this;
        }

        public Criteria andGatheringbanknameEqualTo(String value) {
            addCriterion("GATHERINGBANKNAME =", value, "gatheringbankname");
            return (Criteria) this;
        }

        public Criteria andGatheringbanknameNotEqualTo(String value) {
            addCriterion("GATHERINGBANKNAME <>", value, "gatheringbankname");
            return (Criteria) this;
        }

        public Criteria andGatheringbanknameGreaterThan(String value) {
            addCriterion("GATHERINGBANKNAME >", value, "gatheringbankname");
            return (Criteria) this;
        }

        public Criteria andGatheringbanknameGreaterThanOrEqualTo(String value) {
            addCriterion("GATHERINGBANKNAME >=", value, "gatheringbankname");
            return (Criteria) this;
        }

        public Criteria andGatheringbanknameLessThan(String value) {
            addCriterion("GATHERINGBANKNAME <", value, "gatheringbankname");
            return (Criteria) this;
        }

        public Criteria andGatheringbanknameLessThanOrEqualTo(String value) {
            addCriterion("GATHERINGBANKNAME <=", value, "gatheringbankname");
            return (Criteria) this;
        }

        public Criteria andGatheringbanknameLike(String value) {
            addCriterion("GATHERINGBANKNAME like", value, "gatheringbankname");
            return (Criteria) this;
        }

        public Criteria andGatheringbanknameNotLike(String value) {
            addCriterion("GATHERINGBANKNAME not like", value, "gatheringbankname");
            return (Criteria) this;
        }

        public Criteria andGatheringbanknameIn(List<String> values) {
            addCriterion("GATHERINGBANKNAME in", values, "gatheringbankname");
            return (Criteria) this;
        }

        public Criteria andGatheringbanknameNotIn(List<String> values) {
            addCriterion("GATHERINGBANKNAME not in", values, "gatheringbankname");
            return (Criteria) this;
        }

        public Criteria andGatheringbanknameBetween(String value1, String value2) {
            addCriterion("GATHERINGBANKNAME between", value1, value2, "gatheringbankname");
            return (Criteria) this;
        }

        public Criteria andGatheringbanknameNotBetween(String value1, String value2) {
            addCriterion("GATHERINGBANKNAME not between", value1, value2, "gatheringbankname");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctcodeIsNull() {
            addCriterion("GATHERINGBANKACCTCODE is null");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctcodeIsNotNull() {
            addCriterion("GATHERINGBANKACCTCODE is not null");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctcodeEqualTo(String value) {
            addCriterion("GATHERINGBANKACCTCODE =", value, "gatheringbankacctcode");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctcodeNotEqualTo(String value) {
            addCriterion("GATHERINGBANKACCTCODE <>", value, "gatheringbankacctcode");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctcodeGreaterThan(String value) {
            addCriterion("GATHERINGBANKACCTCODE >", value, "gatheringbankacctcode");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctcodeGreaterThanOrEqualTo(String value) {
            addCriterion("GATHERINGBANKACCTCODE >=", value, "gatheringbankacctcode");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctcodeLessThan(String value) {
            addCriterion("GATHERINGBANKACCTCODE <", value, "gatheringbankacctcode");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctcodeLessThanOrEqualTo(String value) {
            addCriterion("GATHERINGBANKACCTCODE <=", value, "gatheringbankacctcode");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctcodeLike(String value) {
            addCriterion("GATHERINGBANKACCTCODE like", value, "gatheringbankacctcode");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctcodeNotLike(String value) {
            addCriterion("GATHERINGBANKACCTCODE not like", value, "gatheringbankacctcode");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctcodeIn(List<String> values) {
            addCriterion("GATHERINGBANKACCTCODE in", values, "gatheringbankacctcode");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctcodeNotIn(List<String> values) {
            addCriterion("GATHERINGBANKACCTCODE not in", values, "gatheringbankacctcode");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctcodeBetween(String value1, String value2) {
            addCriterion("GATHERINGBANKACCTCODE between", value1, value2, "gatheringbankacctcode");
            return (Criteria) this;
        }

        public Criteria andGatheringbankacctcodeNotBetween(String value1, String value2) {
            addCriterion("GATHERINGBANKACCTCODE not between", value1, value2, "gatheringbankacctcode");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table FIS.GWK_AREACODE_MAP
     *
     * @mbggenerated do_not_delete_during_merge Mon Dec 17 21:55:18 CST 2012
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table FIS.GWK_AREACODE_MAP
     *
     * @mbggenerated Mon Dec 17 21:55:18 CST 2012
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