package fis.repository.pay.dao;

import fis.repository.pay.model.PayBasicExpfunc;
import fis.repository.pay.model.PayBasicExpfuncExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PayBasicExpfuncMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_EXPFUNC
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int countByExample(PayBasicExpfuncExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_EXPFUNC
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int deleteByExample(PayBasicExpfuncExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_EXPFUNC
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int insert(PayBasicExpfunc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_EXPFUNC
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int insertSelective(PayBasicExpfunc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_EXPFUNC
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    List<PayBasicExpfunc> selectByExample(PayBasicExpfuncExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_EXPFUNC
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int updateByExampleSelective(@Param("record") PayBasicExpfunc record, @Param("example") PayBasicExpfuncExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_EXPFUNC
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int updateByExample(@Param("record") PayBasicExpfunc record, @Param("example") PayBasicExpfuncExample example);
}