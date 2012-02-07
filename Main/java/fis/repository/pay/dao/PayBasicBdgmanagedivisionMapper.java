package fis.repository.pay.dao;

import fis.repository.pay.model.PayBasicBdgmanagedivision;
import fis.repository.pay.model.PayBasicBdgmanagedivisionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PayBasicBdgmanagedivisionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_BDGMANAGEDIVISION
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int countByExample(PayBasicBdgmanagedivisionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_BDGMANAGEDIVISION
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int deleteByExample(PayBasicBdgmanagedivisionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_BDGMANAGEDIVISION
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int insert(PayBasicBdgmanagedivision record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_BDGMANAGEDIVISION
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int insertSelective(PayBasicBdgmanagedivision record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_BDGMANAGEDIVISION
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    List<PayBasicBdgmanagedivision> selectByExample(PayBasicBdgmanagedivisionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_BDGMANAGEDIVISION
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int updateByExampleSelective(@Param("record") PayBasicBdgmanagedivision record, @Param("example") PayBasicBdgmanagedivisionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.PAY_BASIC_BDGMANAGEDIVISION
     *
     * @mbggenerated Mon Feb 06 14:16:38 CST 2012
     */
    int updateByExample(@Param("record") PayBasicBdgmanagedivision record, @Param("example") PayBasicBdgmanagedivisionExample example);
}