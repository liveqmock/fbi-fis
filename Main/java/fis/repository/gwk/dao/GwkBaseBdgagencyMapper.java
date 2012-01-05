package fis.repository.gwk.dao;

import fis.repository.gwk.model.GwkBaseBdgagency;
import fis.repository.gwk.model.GwkBaseBdgagencyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GwkBaseBdgagencyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_BASE_BDGAGENCY
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    int countByExample(GwkBaseBdgagencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_BASE_BDGAGENCY
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    int deleteByExample(GwkBaseBdgagencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_BASE_BDGAGENCY
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    int insert(GwkBaseBdgagency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_BASE_BDGAGENCY
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    int insertSelective(GwkBaseBdgagency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_BASE_BDGAGENCY
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    List<GwkBaseBdgagency> selectByExample(GwkBaseBdgagencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_BASE_BDGAGENCY
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    int updateByExampleSelective(@Param("record") GwkBaseBdgagency record, @Param("example") GwkBaseBdgagencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_BASE_BDGAGENCY
     *
     * @mbggenerated Fri Jan 06 07:43:12 CST 2012
     */
    int updateByExample(@Param("record") GwkBaseBdgagency record, @Param("example") GwkBaseBdgagencyExample example);
}