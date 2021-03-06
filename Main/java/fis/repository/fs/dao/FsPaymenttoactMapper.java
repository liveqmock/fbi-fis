package fis.repository.fs.dao;

import fis.repository.fs.model.FsPaymenttoact;
import fis.repository.fs.model.FsPaymenttoactExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FsPaymenttoactMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTTOACT
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    int countByExample(FsPaymenttoactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTTOACT
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    int deleteByExample(FsPaymenttoactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTTOACT
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    int insert(FsPaymenttoact record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTTOACT
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    int insertSelective(FsPaymenttoact record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTTOACT
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    List<FsPaymenttoact> selectByExample(FsPaymenttoactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTTOACT
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    int updateByExampleSelective(@Param("record") FsPaymenttoact record, @Param("example") FsPaymenttoactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTTOACT
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    int updateByExample(@Param("record") FsPaymenttoact record, @Param("example") FsPaymenttoactExample example);
}