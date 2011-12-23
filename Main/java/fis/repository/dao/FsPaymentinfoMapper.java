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
     * @mbggenerated Thu Dec 22 13:06:40 CST 2011
     */
    int countByExample(FsPaymentinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Thu Dec 22 13:06:40 CST 2011
     */
    int deleteByExample(FsPaymentinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Thu Dec 22 13:06:40 CST 2011
     */
    int insert(FsPaymentinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Thu Dec 22 13:06:40 CST 2011
     */
    int insertSelective(FsPaymentinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Thu Dec 22 13:06:40 CST 2011
     */
    List<FsPaymentinfo> selectByExample(FsPaymentinfoExample example);

    /**
     * 按缴款书号查询*/
    List<FsPaymentinfo> selectPayinfoByPaynotescd(@Param("paynotescd") String paynotescd);

    /**
     * 到账信息查询 除去项目信息*/
    List<FsPaymentinfo> selectPayinfoForToact(String[] processstsAry);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Thu Dec 22 13:06:40 CST 2011
     */
    int updateByExampleSelective(@Param("record") FsPaymentinfo record, @Param("example") FsPaymentinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Thu Dec 22 13:06:40 CST 2011
     */
    int updateByExample(@Param("record") FsPaymentinfo record, @Param("example") FsPaymentinfoExample example);
}