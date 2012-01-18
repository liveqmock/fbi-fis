package fis.repository.pay.dao;

import fis.repository.pay.model.PayPayvoucherhis;
import fis.repository.pay.model.PayPayvoucherhisExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PayPayvoucherhisMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_PAYVOUCHERHIS
     *
     * @mbggenerated Wed Jan 18 10:56:33 CST 2012
     */
    int countByExample(PayPayvoucherhisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_PAYVOUCHERHIS
     *
     * @mbggenerated Wed Jan 18 10:56:33 CST 2012
     */
    int deleteByExample(PayPayvoucherhisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_PAYVOUCHERHIS
     *
     * @mbggenerated Wed Jan 18 10:56:33 CST 2012
     */
    int insert(PayPayvoucherhis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_PAYVOUCHERHIS
     *
     * @mbggenerated Wed Jan 18 10:56:33 CST 2012
     */
    int insertSelective(PayPayvoucherhis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_PAYVOUCHERHIS
     *
     * @mbggenerated Wed Jan 18 10:56:33 CST 2012
     */
    List<PayPayvoucherhis> selectByExample(PayPayvoucherhisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_PAYVOUCHERHIS
     *
     * @mbggenerated Wed Jan 18 10:56:33 CST 2012
     */
    int updateByExampleSelective(@Param("record") PayPayvoucherhis record, @Param("example") PayPayvoucherhisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_PAYVOUCHERHIS
     *
     * @mbggenerated Wed Jan 18 10:56:33 CST 2012
     */
    int updateByExample(@Param("record") PayPayvoucherhis record, @Param("example") PayPayvoucherhisExample example);
}