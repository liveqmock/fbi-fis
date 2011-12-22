package fis.repository.dao;

import fis.repository.model.FsRefundconfirm;
import fis.repository.model.FsRefundconfirmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FsRefundconfirmMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_REFUNDCONFIRM
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    int countByExample(FsRefundconfirmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_REFUNDCONFIRM
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    int deleteByExample(FsRefundconfirmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_REFUNDCONFIRM
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    int insert(FsRefundconfirm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_REFUNDCONFIRM
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    int insertSelective(FsRefundconfirm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_REFUNDCONFIRM
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    List<FsRefundconfirm> selectByExample(FsRefundconfirmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_REFUNDCONFIRM
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    int updateByExampleSelective(@Param("record") FsRefundconfirm record, @Param("example") FsRefundconfirmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_REFUNDCONFIRM
     *
     * @mbggenerated Wed Dec 21 10:11:04 CST 2011
     */
    int updateByExample(@Param("record") FsRefundconfirm record, @Param("example") FsRefundconfirmExample example);
}