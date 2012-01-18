package fis.repository.pay.dao;

import fis.repository.pay.model.PayBasicProgram;
import fis.repository.pay.model.PayBasicProgramExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PayBasicProgramMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_PROGRAM
     *
     * @mbggenerated Wed Jan 18 10:56:33 CST 2012
     */
    int countByExample(PayBasicProgramExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_PROGRAM
     *
     * @mbggenerated Wed Jan 18 10:56:33 CST 2012
     */
    int deleteByExample(PayBasicProgramExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_PROGRAM
     *
     * @mbggenerated Wed Jan 18 10:56:33 CST 2012
     */
    int insert(PayBasicProgram record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_PROGRAM
     *
     * @mbggenerated Wed Jan 18 10:56:33 CST 2012
     */
    int insertSelective(PayBasicProgram record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_PROGRAM
     *
     * @mbggenerated Wed Jan 18 10:56:33 CST 2012
     */
    List<PayBasicProgram> selectByExample(PayBasicProgramExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_PROGRAM
     *
     * @mbggenerated Wed Jan 18 10:56:33 CST 2012
     */
    int updateByExampleSelective(@Param("record") PayBasicProgram record, @Param("example") PayBasicProgramExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_PROGRAM
     *
     * @mbggenerated Wed Jan 18 10:56:33 CST 2012
     */
    int updateByExample(@Param("record") PayBasicProgram record, @Param("example") PayBasicProgramExample example);
}