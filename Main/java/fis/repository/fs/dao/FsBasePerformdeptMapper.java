package fis.repository.fs.dao;

import fis.repository.fs.model.FsBasePerformdept;
import fis.repository.fs.model.FsBasePerformdeptExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FsBasePerformdeptMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_BASE_PERFORMDEPT
     *
     * @mbggenerated Thu Dec 22 10:27:18 CST 2011
     */
    int countByExample(FsBasePerformdeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_BASE_PERFORMDEPT
     *
     * @mbggenerated Thu Dec 22 10:27:18 CST 2011
     */
    int deleteByExample(FsBasePerformdeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_BASE_PERFORMDEPT
     *
     * @mbggenerated Thu Dec 22 10:27:18 CST 2011
     */
    int insert(FsBasePerformdept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_BASE_PERFORMDEPT
     *
     * @mbggenerated Thu Dec 22 10:27:18 CST 2011
     */
    int insertSelective(FsBasePerformdept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_BASE_PERFORMDEPT
     *
     * @mbggenerated Thu Dec 22 10:27:18 CST 2011
     */
    List<FsBasePerformdept> selectByExample(FsBasePerformdeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_BASE_PERFORMDEPT
     *
     * @mbggenerated Thu Dec 22 10:27:18 CST 2011
     */
    int updateByExampleSelective(@Param("record") FsBasePerformdept record, @Param("example") FsBasePerformdeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_BASE_PERFORMDEPT
     *
     * @mbggenerated Thu Dec 22 10:27:18 CST 2011
     */
    int updateByExample(@Param("record") FsBasePerformdept record, @Param("example") FsBasePerformdeptExample example);
}