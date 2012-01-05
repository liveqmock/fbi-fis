package fis.repository.dao;

import fis.repository.model.FsPaymentinfo;
import fis.repository.model.FsPaymentinfoExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface FsPaymentinfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Fri Dec 23 15:43:29 CST 2011
     */
    int countByExample(FsPaymentinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Fri Dec 23 15:43:29 CST 2011
     */
    int deleteByExample(FsPaymentinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Fri Dec 23 15:43:29 CST 2011
     */
    int insert(FsPaymentinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Fri Dec 23 15:43:29 CST 2011
     */
    int insertSelective(FsPaymentinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Fri Dec 23 15:43:29 CST 2011
     */
    List<FsPaymentinfo> selectByExample(FsPaymentinfoExample example);

    List<FsPaymentinfo> selectPayinfoByPaynotescd(@Param("paynotescd") String paynotescd, @Param("checkcode") String checkcode,
                                                  @Param("bofcode") String bofcode);

    List<FsPaymentinfo> selectPayinfoForToact(Map m);

    List<FsPaymentinfo> selectPayinfoForQry(@Param("processstatus") String processstatus,@Param("bofcode") String bofcode,
                                            @Param("notescode") String notescode,@Param("recStartd") String recStartd,
                                            @Param("recEndd") String recEndd,@Param("acctStartd") String acctStratd,
                                            @Param("acctEndd") String acctEndd );
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Fri Dec 23 15:43:29 CST 2011
     */
    int updateByExampleSelective(@Param("record") FsPaymentinfo record, @Param("example") FsPaymentinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFO
     *
     * @mbggenerated Fri Dec 23 15:43:29 CST 2011
     */
    int updateByExample(@Param("record") FsPaymentinfo record, @Param("example") FsPaymentinfoExample example);
}