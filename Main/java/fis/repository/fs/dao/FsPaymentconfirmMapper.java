package fis.repository.fs.dao;

import fis.repository.fs.model.FsPaymentconfirm;
import fis.repository.fs.model.FsPaymentconfirmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FsPaymentconfirmMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTCONFIRM
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    int countByExample(FsPaymentconfirmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTCONFIRM
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    int deleteByExample(FsPaymentconfirmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTCONFIRM
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    int insert(FsPaymentconfirm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTCONFIRM
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    int insertSelective(FsPaymentconfirm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTCONFIRM
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    List<FsPaymentconfirm> selectByExample(FsPaymentconfirmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTCONFIRM
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    int updateByExampleSelective(@Param("record") FsPaymentconfirm record, @Param("example") FsPaymentconfirmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTCONFIRM
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    int updateByExample(@Param("record") FsPaymentconfirm record, @Param("example") FsPaymentconfirmExample example);
}