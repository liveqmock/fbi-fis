package fis.repository.fs.dao;

import fis.repository.fs.model.FsPaymentinfohis;
import fis.repository.fs.model.FsPaymentinfohisExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FsPaymentinfohisMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFOHIS
     *
     * @mbggenerated Sat Jan 07 17:41:25 CST 2012
     */
    int countByExample(FsPaymentinfohisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFOHIS
     *
     * @mbggenerated Sat Jan 07 17:41:25 CST 2012
     */
    int deleteByExample(FsPaymentinfohisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFOHIS
     *
     * @mbggenerated Sat Jan 07 17:41:25 CST 2012
     */
    int insert(FsPaymentinfohis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFOHIS
     *
     * @mbggenerated Sat Jan 07 17:41:25 CST 2012
     */
    int insertSelective(FsPaymentinfohis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFOHIS
     *
     * @mbggenerated Sat Jan 07 17:41:25 CST 2012
     */
    List<FsPaymentinfohis> selectByExample(FsPaymentinfohisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFOHIS
     *
     * @mbggenerated Sat Jan 07 17:41:25 CST 2012
     */
    int updateByExampleSelective(@Param("record") FsPaymentinfohis record, @Param("example") FsPaymentinfohisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_PAYMENTINFOHIS
     *
     * @mbggenerated Sat Jan 07 17:41:25 CST 2012
     */
    int updateByExample(@Param("record") FsPaymentinfohis record, @Param("example") FsPaymentinfohisExample example);
}