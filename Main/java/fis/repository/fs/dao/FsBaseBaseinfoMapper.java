package fis.repository.fs.dao;

import fis.repository.fs.model.FsBaseBaseinfo;
import fis.repository.fs.model.FsBaseBaseinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FsBaseBaseinfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_BASE_BASEINFO
     *
     * @mbggenerated Tue Dec 20 16:48:18 CST 2011
     */
    int countByExample(FsBaseBaseinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_BASE_BASEINFO
     *
     * @mbggenerated Tue Dec 20 16:48:18 CST 2011
     */
    int deleteByExample(FsBaseBaseinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_BASE_BASEINFO
     *
     * @mbggenerated Tue Dec 20 16:48:18 CST 2011
     */
    int insert(FsBaseBaseinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_BASE_BASEINFO
     *
     * @mbggenerated Tue Dec 20 16:48:18 CST 2011
     */
    int insertSelective(FsBaseBaseinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_BASE_BASEINFO
     *
     * @mbggenerated Tue Dec 20 16:48:18 CST 2011
     */
    List<FsBaseBaseinfo> selectByExample(FsBaseBaseinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_BASE_BASEINFO
     *
     * @mbggenerated Tue Dec 20 16:48:18 CST 2011
     */
    int updateByExampleSelective(@Param("record") FsBaseBaseinfo record, @Param("example") FsBaseBaseinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_BASE_BASEINFO
     *
     * @mbggenerated Tue Dec 20 16:48:18 CST 2011
     */
    int updateByExample(@Param("record") FsBaseBaseinfo record, @Param("example") FsBaseBaseinfoExample example);
}