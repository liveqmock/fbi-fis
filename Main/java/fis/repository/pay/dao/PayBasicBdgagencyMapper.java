package fis.repository.pay.dao;

import fis.repository.pay.model.PayBasicBdgagency;
import fis.repository.pay.model.PayBasicBdgagencyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PayBasicBdgagencyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_BDGAGENCY
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int countByExample(PayBasicBdgagencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_BDGAGENCY
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int deleteByExample(PayBasicBdgagencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_BDGAGENCY
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int insert(PayBasicBdgagency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_BDGAGENCY
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int insertSelective(PayBasicBdgagency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_BDGAGENCY
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    List<PayBasicBdgagency> selectByExample(PayBasicBdgagencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_BDGAGENCY
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int updateByExampleSelective(@Param("record") PayBasicBdgagency record, @Param("example") PayBasicBdgagencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_BDGAGENCY
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int updateByExample(@Param("record") PayBasicBdgagency record, @Param("example") PayBasicBdgagencyExample example);
}