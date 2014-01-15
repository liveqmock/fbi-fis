package fnt.repository.dao;

import fnt.repository.model.FsQdfChkVch;
import fnt.repository.model.FsQdfChkVchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FsQdfChkVchMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_QDF_CHK_VCH
     *
     * @mbggenerated Mon Jun 24 11:41:37 CST 2013
     */
    int countByExample(FsQdfChkVchExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_QDF_CHK_VCH
     *
     * @mbggenerated Mon Jun 24 11:41:37 CST 2013
     */
    int deleteByExample(FsQdfChkVchExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_QDF_CHK_VCH
     *
     * @mbggenerated Mon Jun 24 11:41:37 CST 2013
     */
    int insert(FsQdfChkVch record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_QDF_CHK_VCH
     *
     * @mbggenerated Mon Jun 24 11:41:37 CST 2013
     */
    int insertSelective(FsQdfChkVch record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_QDF_CHK_VCH
     *
     * @mbggenerated Mon Jun 24 11:41:37 CST 2013
     */
    List<FsQdfChkVch> selectByExample(FsQdfChkVchExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_QDF_CHK_VCH
     *
     * @mbggenerated Mon Jun 24 11:41:37 CST 2013
     */
    int updateByExampleSelective(@Param("record") FsQdfChkVch record, @Param("example") FsQdfChkVchExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_QDF_CHK_VCH
     *
     * @mbggenerated Mon Jun 24 11:41:37 CST 2013
     */
    int updateByExample(@Param("record") FsQdfChkVch record, @Param("example") FsQdfChkVchExample example);
}