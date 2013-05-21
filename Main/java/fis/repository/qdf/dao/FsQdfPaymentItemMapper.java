package fis.repository.qdf.dao;

import fis.repository.qdf.model.FsQdfPaymentItem;
import fis.repository.qdf.model.FsQdfPaymentItemExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FsQdfPaymentItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_QDF_PAYMENT_ITEM
     *
     * @mbggenerated Tue May 21 09:36:54 CST 2013
     */
    int countByExample(FsQdfPaymentItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_QDF_PAYMENT_ITEM
     *
     * @mbggenerated Tue May 21 09:36:54 CST 2013
     */
    int deleteByExample(FsQdfPaymentItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_QDF_PAYMENT_ITEM
     *
     * @mbggenerated Tue May 21 09:36:54 CST 2013
     */
    int insert(FsQdfPaymentItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_QDF_PAYMENT_ITEM
     *
     * @mbggenerated Tue May 21 09:36:54 CST 2013
     */
    int insertSelective(FsQdfPaymentItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_QDF_PAYMENT_ITEM
     *
     * @mbggenerated Tue May 21 09:36:54 CST 2013
     */
    List<FsQdfPaymentItem> selectByExample(FsQdfPaymentItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_QDF_PAYMENT_ITEM
     *
     * @mbggenerated Tue May 21 09:36:54 CST 2013
     */
    int updateByExampleSelective(@Param("record") FsQdfPaymentItem record, @Param("example") FsQdfPaymentItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIS.FS_QDF_PAYMENT_ITEM
     *
     * @mbggenerated Tue May 21 09:36:54 CST 2013
     */
    int updateByExample(@Param("record") FsQdfPaymentItem record, @Param("example") FsQdfPaymentItemExample example);
}