package fis.repository.model;

import java.math.BigDecimal;
import java.util.Date;

public class FsRefundinfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.PKID
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private String pkid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.BILLID
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private Integer billid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.REFUNDAPPLYCODE
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private String refundapplycode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.PAYNOTESCODE
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private String paynotescode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.PERFORMDEPT
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private Integer performdept;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.CREATEUSER
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private Integer createuser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.RECUSERNAME
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private String recusername;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.RECUSERBANK
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private String recuserbank;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.RECUSERBANKACCOUNT
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private String recuserbankaccount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.REFUNDREASON
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private String refundreason;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.AUDITOR
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private Integer auditor;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.AUDITAMT
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private BigDecimal auditamt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.CREATETIME
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private String createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.TOTALAMT
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private BigDecimal totalamt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.CREATER
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private Integer creater;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.AGENTBANK
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private Integer agentbank;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.PRINTTAG
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private Integer printtag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.VERSION
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private Integer version;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.OPERID
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private String operid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_REFUNDINFO.OPERDATE
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    private Date operdate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.PKID
     *
     * @return the value of FIS.FS_REFUNDINFO.PKID
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public String getPkid() {
        return pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.PKID
     *
     * @param pkid the value for FIS.FS_REFUNDINFO.PKID
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setPkid(String pkid) {
        this.pkid = pkid == null ? null : pkid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.BILLID
     *
     * @return the value of FIS.FS_REFUNDINFO.BILLID
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public Integer getBillid() {
        return billid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.BILLID
     *
     * @param billid the value for FIS.FS_REFUNDINFO.BILLID
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setBillid(Integer billid) {
        this.billid = billid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.REFUNDAPPLYCODE
     *
     * @return the value of FIS.FS_REFUNDINFO.REFUNDAPPLYCODE
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public String getRefundapplycode() {
        return refundapplycode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.REFUNDAPPLYCODE
     *
     * @param refundapplycode the value for FIS.FS_REFUNDINFO.REFUNDAPPLYCODE
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setRefundapplycode(String refundapplycode) {
        this.refundapplycode = refundapplycode == null ? null : refundapplycode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.PAYNOTESCODE
     *
     * @return the value of FIS.FS_REFUNDINFO.PAYNOTESCODE
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public String getPaynotescode() {
        return paynotescode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.PAYNOTESCODE
     *
     * @param paynotescode the value for FIS.FS_REFUNDINFO.PAYNOTESCODE
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setPaynotescode(String paynotescode) {
        this.paynotescode = paynotescode == null ? null : paynotescode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.PERFORMDEPT
     *
     * @return the value of FIS.FS_REFUNDINFO.PERFORMDEPT
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public Integer getPerformdept() {
        return performdept;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.PERFORMDEPT
     *
     * @param performdept the value for FIS.FS_REFUNDINFO.PERFORMDEPT
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setPerformdept(Integer performdept) {
        this.performdept = performdept;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.CREATEUSER
     *
     * @return the value of FIS.FS_REFUNDINFO.CREATEUSER
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public Integer getCreateuser() {
        return createuser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.CREATEUSER
     *
     * @param createuser the value for FIS.FS_REFUNDINFO.CREATEUSER
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setCreateuser(Integer createuser) {
        this.createuser = createuser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.RECUSERNAME
     *
     * @return the value of FIS.FS_REFUNDINFO.RECUSERNAME
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public String getRecusername() {
        return recusername;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.RECUSERNAME
     *
     * @param recusername the value for FIS.FS_REFUNDINFO.RECUSERNAME
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setRecusername(String recusername) {
        this.recusername = recusername == null ? null : recusername.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.RECUSERBANK
     *
     * @return the value of FIS.FS_REFUNDINFO.RECUSERBANK
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public String getRecuserbank() {
        return recuserbank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.RECUSERBANK
     *
     * @param recuserbank the value for FIS.FS_REFUNDINFO.RECUSERBANK
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setRecuserbank(String recuserbank) {
        this.recuserbank = recuserbank == null ? null : recuserbank.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.RECUSERBANKACCOUNT
     *
     * @return the value of FIS.FS_REFUNDINFO.RECUSERBANKACCOUNT
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public String getRecuserbankaccount() {
        return recuserbankaccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.RECUSERBANKACCOUNT
     *
     * @param recuserbankaccount the value for FIS.FS_REFUNDINFO.RECUSERBANKACCOUNT
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setRecuserbankaccount(String recuserbankaccount) {
        this.recuserbankaccount = recuserbankaccount == null ? null : recuserbankaccount.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.REFUNDREASON
     *
     * @return the value of FIS.FS_REFUNDINFO.REFUNDREASON
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public String getRefundreason() {
        return refundreason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.REFUNDREASON
     *
     * @param refundreason the value for FIS.FS_REFUNDINFO.REFUNDREASON
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setRefundreason(String refundreason) {
        this.refundreason = refundreason == null ? null : refundreason.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.AUDITOR
     *
     * @return the value of FIS.FS_REFUNDINFO.AUDITOR
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public Integer getAuditor() {
        return auditor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.AUDITOR
     *
     * @param auditor the value for FIS.FS_REFUNDINFO.AUDITOR
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setAuditor(Integer auditor) {
        this.auditor = auditor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.AUDITAMT
     *
     * @return the value of FIS.FS_REFUNDINFO.AUDITAMT
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public BigDecimal getAuditamt() {
        return auditamt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.AUDITAMT
     *
     * @param auditamt the value for FIS.FS_REFUNDINFO.AUDITAMT
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setAuditamt(BigDecimal auditamt) {
        this.auditamt = auditamt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.CREATETIME
     *
     * @return the value of FIS.FS_REFUNDINFO.CREATETIME
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public String getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.CREATETIME
     *
     * @param createtime the value for FIS.FS_REFUNDINFO.CREATETIME
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.TOTALAMT
     *
     * @return the value of FIS.FS_REFUNDINFO.TOTALAMT
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public BigDecimal getTotalamt() {
        return totalamt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.TOTALAMT
     *
     * @param totalamt the value for FIS.FS_REFUNDINFO.TOTALAMT
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setTotalamt(BigDecimal totalamt) {
        this.totalamt = totalamt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.CREATER
     *
     * @return the value of FIS.FS_REFUNDINFO.CREATER
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public Integer getCreater() {
        return creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.CREATER
     *
     * @param creater the value for FIS.FS_REFUNDINFO.CREATER
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.AGENTBANK
     *
     * @return the value of FIS.FS_REFUNDINFO.AGENTBANK
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public Integer getAgentbank() {
        return agentbank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.AGENTBANK
     *
     * @param agentbank the value for FIS.FS_REFUNDINFO.AGENTBANK
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setAgentbank(Integer agentbank) {
        this.agentbank = agentbank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.PRINTTAG
     *
     * @return the value of FIS.FS_REFUNDINFO.PRINTTAG
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public Integer getPrinttag() {
        return printtag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.PRINTTAG
     *
     * @param printtag the value for FIS.FS_REFUNDINFO.PRINTTAG
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setPrinttag(Integer printtag) {
        this.printtag = printtag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.VERSION
     *
     * @return the value of FIS.FS_REFUNDINFO.VERSION
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.VERSION
     *
     * @param version the value for FIS.FS_REFUNDINFO.VERSION
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.OPERID
     *
     * @return the value of FIS.FS_REFUNDINFO.OPERID
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public String getOperid() {
        return operid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.OPERID
     *
     * @param operid the value for FIS.FS_REFUNDINFO.OPERID
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setOperid(String operid) {
        this.operid = operid == null ? null : operid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_REFUNDINFO.OPERDATE
     *
     * @return the value of FIS.FS_REFUNDINFO.OPERDATE
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public Date getOperdate() {
        return operdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_REFUNDINFO.OPERDATE
     *
     * @param operdate the value for FIS.FS_REFUNDINFO.OPERDATE
     *
     * @mbggenerated Tue Dec 20 16:12:39 CST 2011
     */
    public void setOperdate(Date operdate) {
        this.operdate = operdate;
    }
}