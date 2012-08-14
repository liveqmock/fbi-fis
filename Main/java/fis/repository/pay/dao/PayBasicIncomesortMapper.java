package fis.repository.pay.dao;

import fis.repository.pay.model.PayBasicIncomesort;
import fis.repository.pay.model.PayBasicIncomesortExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PayBasicIncomesortMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_INCOMESORT
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int countByExample(PayBasicIncomesortExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_INCOMESORT
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int deleteByExample(PayBasicIncomesortExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_INCOMESORT
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int insert(PayBasicIncomesort record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_INCOMESORT
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int insertSelective(PayBasicIncomesort record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_INCOMESORT
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    List<PayBasicIncomesort> selectByExample(PayBasicIncomesortExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_INCOMESORT
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int updateByExampleSelective(@Param("record") PayBasicIncomesort record, @Param("example") PayBasicIncomesortExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_INCOMESORT
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int updateByExample(@Param("record") PayBasicIncomesort record, @Param("example") PayBasicIncomesortExample example);
}