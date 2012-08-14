package fis.repository.pay.dao;

import fis.repository.pay.model.PayPayvoucher;
import fis.repository.pay.model.PayPayvoucherExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PayPayvoucherMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_PAYVOUCHER
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int countByExample(PayPayvoucherExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_PAYVOUCHER
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int deleteByExample(PayPayvoucherExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_PAYVOUCHER
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int insert(PayPayvoucher record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_PAYVOUCHER
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int insertSelective(PayPayvoucher record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_PAYVOUCHER
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    List<PayPayvoucher> selectByExample(PayPayvoucherExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_PAYVOUCHER
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int updateByExampleSelective(@Param("record") PayPayvoucher record, @Param("example") PayPayvoucherExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_PAYVOUCHER
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int updateByExample(@Param("record") PayPayvoucher record, @Param("example") PayPayvoucherExample example);
}