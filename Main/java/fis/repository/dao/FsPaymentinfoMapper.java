package fis.repository.dao;

import fis.repository.model.FsPaymentinfo;
import fis.repository.model.FsPaymentinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FsPaymentinfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    int countByExample(FsPaymentinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    int deleteByExample(FsPaymentinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    int insert(FsPaymentinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    int insertSelective(FsPaymentinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    List<FsPaymentinfo> selectByExample(FsPaymentinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    int updateByExampleSelective(@Param("record") FsPaymentinfo record, @Param("example") FsPaymentinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Wed Dec 21 08:08:41 CST 2011
     */
    int updateByExample(@Param("record") FsPaymentinfo record, @Param("example") FsPaymentinfoExample example);
}