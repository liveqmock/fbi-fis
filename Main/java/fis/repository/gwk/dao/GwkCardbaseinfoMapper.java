package fis.repository.gwk.dao;

import fis.repository.gwk.model.GwkCardbaseinfo;
import fis.repository.gwk.model.GwkCardbaseinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GwkCardbaseinfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_CARDBASEINFO
     *
     * @mbggenerated Wed Jan 11 12:20:58 CST 2012
     */
    int countByExample(GwkCardbaseinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_CARDBASEINFO
     *
     * @mbggenerated Wed Jan 11 12:20:58 CST 2012
     */
    int deleteByExample(GwkCardbaseinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_CARDBASEINFO
     *
     * @mbggenerated Wed Jan 11 12:20:58 CST 2012
     */
    int insert(GwkCardbaseinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_CARDBASEINFO
     *
     * @mbggenerated Wed Jan 11 12:20:58 CST 2012
     */
    int insertSelective(GwkCardbaseinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_CARDBASEINFO
     *
     * @mbggenerated Wed Jan 11 12:20:58 CST 2012
     */
    List<GwkCardbaseinfo> selectByExample(GwkCardbaseinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_CARDBASEINFO
     *
     * @mbggenerated Wed Jan 11 12:20:58 CST 2012
     */
    int updateByExampleSelective(@Param("record") GwkCardbaseinfo record, @Param("example") GwkCardbaseinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.GWK_CARDBASEINFO
     *
     * @mbggenerated Wed Jan 11 12:20:58 CST 2012
     */
    int updateByExample(@Param("record") GwkCardbaseinfo record, @Param("example") GwkCardbaseinfoExample example);
}