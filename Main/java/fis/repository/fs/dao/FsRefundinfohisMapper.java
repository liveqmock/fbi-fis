package fis.repository.fs.dao;

import fis.repository.fs.model.FsRefundinfohis;
import fis.repository.fs.model.FsRefundinfohisExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FsRefundinfohisMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_REFUNDINFOHIS
     *
     * @mbggenerated Sun Dec 25 20:31:35 CST 2011
     */
    int countByExample(FsRefundinfohisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_REFUNDINFOHIS
     *
     * @mbggenerated Sun Dec 25 20:31:35 CST 2011
     */
    int deleteByExample(FsRefundinfohisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_REFUNDINFOHIS
     *
     * @mbggenerated Sun Dec 25 20:31:35 CST 2011
     */
    int insert(FsRefundinfohis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_REFUNDINFOHIS
     *
     * @mbggenerated Sun Dec 25 20:31:35 CST 2011
     */
    int insertSelective(FsRefundinfohis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_REFUNDINFOHIS
     *
     * @mbggenerated Sun Dec 25 20:31:35 CST 2011
     */
    List<FsRefundinfohis> selectByExample(FsRefundinfohisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_REFUNDINFOHIS
     *
     * @mbggenerated Sun Dec 25 20:31:35 CST 2011
     */
    int updateByExampleSelective(@Param("record") FsRefundinfohis record, @Param("example") FsRefundinfohisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_REFUNDINFOHIS
     *
     * @mbggenerated Sun Dec 25 20:31:35 CST 2011
     */
    int updateByExample(@Param("record") FsRefundinfohis record, @Param("example") FsRefundinfohisExample example);
}