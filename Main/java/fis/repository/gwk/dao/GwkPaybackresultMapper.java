package fis.repository.gwk.dao;

import fis.repository.gwk.model.GwkPaybackresult;
import fis.repository.gwk.model.GwkPaybackresultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GwkPaybackresultMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_PAYBACKRESULT
     *
     * @mbggenerated Sat Jan 07 23:10:02 CST 2012
     */
    int countByExample(GwkPaybackresultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_PAYBACKRESULT
     *
     * @mbggenerated Sat Jan 07 23:10:02 CST 2012
     */
    int deleteByExample(GwkPaybackresultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_PAYBACKRESULT
     *
     * @mbggenerated Sat Jan 07 23:10:02 CST 2012
     */
    int insert(GwkPaybackresult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_PAYBACKRESULT
     *
     * @mbggenerated Sat Jan 07 23:10:02 CST 2012
     */
    int insertSelective(GwkPaybackresult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_PAYBACKRESULT
     *
     * @mbggenerated Sat Jan 07 23:10:02 CST 2012
     */
    List<GwkPaybackresult> selectByExample(GwkPaybackresultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_PAYBACKRESULT
     *
     * @mbggenerated Sat Jan 07 23:10:02 CST 2012
     */
    int updateByExampleSelective(@Param("record") GwkPaybackresult record, @Param("example") GwkPaybackresultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_PAYBACKRESULT
     *
     * @mbggenerated Sat Jan 07 23:10:02 CST 2012
     */
    int updateByExample(@Param("record") GwkPaybackresult record, @Param("example") GwkPaybackresultExample example);
}