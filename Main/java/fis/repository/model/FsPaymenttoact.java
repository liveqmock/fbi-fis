package fis.repository.model;

import java.util.Date;

public class FsPaymenttoact {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_PAYMENTTOACT.PKID
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    private String pkid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_PAYMENTTOACT.PAYNOTESCODE
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    private String paynotescode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_PAYMENTTOACT.NOTESCODE
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    private String notescode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_PAYMENTTOACT.BANKACCTDATE
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    private String bankacctdate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_PAYMENTTOACT.RECFEEFLAG
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    private Integer recfeeflag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_PAYMENTTOACT.VERSION
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    private Integer version;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_PAYMENTTOACT.CREATED_BY
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    private String createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_PAYMENTTOACT.CREATED_DT
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    private Date createdDt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_PAYMENTTOACT.LAST_UPD_BY
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    private String lastUpdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_PAYMENTTOACT.LAST_UPD_DATE
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    private Date lastUpdDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIS.FS_PAYMENTTOACT.AREACODE
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    private String areacode;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_PAYMENTTOACT.PKID
     *
     * @return the value of FIS.FS_PAYMENTTOACT.PKID
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public String getPkid() {
        return pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_PAYMENTTOACT.PKID
     *
     * @param pkid the value for FIS.FS_PAYMENTTOACT.PKID
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public void setPkid(String pkid) {
        this.pkid = pkid == null ? null : pkid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_PAYMENTTOACT.PAYNOTESCODE
     *
     * @return the value of FIS.FS_PAYMENTTOACT.PAYNOTESCODE
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public String getPaynotescode() {
        return paynotescode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_PAYMENTTOACT.PAYNOTESCODE
     *
     * @param paynotescode the value for FIS.FS_PAYMENTTOACT.PAYNOTESCODE
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public void setPaynotescode(String paynotescode) {
        this.paynotescode = paynotescode == null ? null : paynotescode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_PAYMENTTOACT.NOTESCODE
     *
     * @return the value of FIS.FS_PAYMENTTOACT.NOTESCODE
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public String getNotescode() {
        return notescode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_PAYMENTTOACT.NOTESCODE
     *
     * @param notescode the value for FIS.FS_PAYMENTTOACT.NOTESCODE
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public void setNotescode(String notescode) {
        this.notescode = notescode == null ? null : notescode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_PAYMENTTOACT.BANKACCTDATE
     *
     * @return the value of FIS.FS_PAYMENTTOACT.BANKACCTDATE
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public String getBankacctdate() {
        return bankacctdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_PAYMENTTOACT.BANKACCTDATE
     *
     * @param bankacctdate the value for FIS.FS_PAYMENTTOACT.BANKACCTDATE
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public void setBankacctdate(String bankacctdate) {
        this.bankacctdate = bankacctdate == null ? null : bankacctdate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_PAYMENTTOACT.RECFEEFLAG
     *
     * @return the value of FIS.FS_PAYMENTTOACT.RECFEEFLAG
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public Integer getRecfeeflag() {
        return recfeeflag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_PAYMENTTOACT.RECFEEFLAG
     *
     * @param recfeeflag the value for FIS.FS_PAYMENTTOACT.RECFEEFLAG
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public void setRecfeeflag(Integer recfeeflag) {
        this.recfeeflag = recfeeflag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_PAYMENTTOACT.VERSION
     *
     * @return the value of FIS.FS_PAYMENTTOACT.VERSION
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_PAYMENTTOACT.VERSION
     *
     * @param version the value for FIS.FS_PAYMENTTOACT.VERSION
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_PAYMENTTOACT.CREATED_BY
     *
     * @return the value of FIS.FS_PAYMENTTOACT.CREATED_BY
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_PAYMENTTOACT.CREATED_BY
     *
     * @param createdBy the value for FIS.FS_PAYMENTTOACT.CREATED_BY
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_PAYMENTTOACT.CREATED_DT
     *
     * @return the value of FIS.FS_PAYMENTTOACT.CREATED_DT
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public Date getCreatedDt() {
        return createdDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_PAYMENTTOACT.CREATED_DT
     *
     * @param createdDt the value for FIS.FS_PAYMENTTOACT.CREATED_DT
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_PAYMENTTOACT.LAST_UPD_BY
     *
     * @return the value of FIS.FS_PAYMENTTOACT.LAST_UPD_BY
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public String getLastUpdBy() {
        return lastUpdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_PAYMENTTOACT.LAST_UPD_BY
     *
     * @param lastUpdBy the value for FIS.FS_PAYMENTTOACT.LAST_UPD_BY
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy == null ? null : lastUpdBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_PAYMENTTOACT.LAST_UPD_DATE
     *
     * @return the value of FIS.FS_PAYMENTTOACT.LAST_UPD_DATE
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public Date getLastUpdDate() {
        return lastUpdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_PAYMENTTOACT.LAST_UPD_DATE
     *
     * @param lastUpdDate the value for FIS.FS_PAYMENTTOACT.LAST_UPD_DATE
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public void setLastUpdDate(Date lastUpdDate) {
        this.lastUpdDate = lastUpdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIS.FS_PAYMENTTOACT.AREACODE
     *
     * @return the value of FIS.FS_PAYMENTTOACT.AREACODE
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public String getAreacode() {
        return areacode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIS.FS_PAYMENTTOACT.AREACODE
     *
     * @param areacode the value for FIS.FS_PAYMENTTOACT.AREACODE
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    public void setAreacode(String areacode) {
        this.areacode = areacode == null ? null : areacode.trim();
    }
}