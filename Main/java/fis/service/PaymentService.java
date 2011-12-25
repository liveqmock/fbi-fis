package fis.service;

import com.sun.org.apache.regexp.internal.RE;
import fis.common.constant.ProcessStatus;
import fis.common.constant.RecfeeFlag;
import fis.common.constant.RefundProcessSts;
import fis.repository.dao.*;
import fis.repository.model.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.security.OperatorManager;
import skyline.service.SystemService;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-12-22
 * Time: 上午10:48
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PaymentService {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    @Resource
    private FsPaymentinfoMapper fsPaymentinfoMapper;
    @Resource
    private FsPaymentinfohisMapper fsPaymentinfohisMapper;
    @Resource
    private FsRefundinfoMapper fsRefundinfoMapper;
    @Resource
    private FsRefundinfohisMapper fsRefundinfohisMapper;
    @Resource
    private SysJoblogMapper sysJoblogMapper;

    public List<FsPaymentinfo> selectPayinfoByPaynotescd(String paynotescd) {
        List<FsPaymentinfo> fsPaymentinfoList = fsPaymentinfoMapper.selectPayinfoByPaynotescd(paynotescd);
        if (fsPaymentinfoList == null || fsPaymentinfoList.size() < 1) {
            //todo 接口查询
        }
        return fsPaymentinfoList;
    }

    //发送收款确认
    @Transactional
    public void sendPayinfoConfirm(List<FsPaymentinfo> fsPaymentinfoList, String processsts) throws InvocationTargetException, IllegalAccessException {
        //todo 发送 如果发送成功
        String recfeeflag = RecfeeFlag.RECFEE_TOACT.getCode();
        if (1 == 1) {
            for (FsPaymentinfo record : fsPaymentinfoList) {
                updatePaymentinfo(record, recfeeflag, processsts);
            }
        } else {
            for (FsPaymentinfo record : fsPaymentinfoList) {
                updatePaymentinfo(record, recfeeflag, ProcessStatus.PROCESS_CONFIRMFAIL.getCode());
            }
        }
    }

    @Transactional
    public void sendPayinfoToact(FsPaymentinfo[] fsPaymentinfos, String processsts) throws InvocationTargetException, IllegalAccessException {
        //todo 发送到账信息
        //如果   成功:处理状态=2 失败=4 ; 更新 到账标志=1
        String recfeeflag = RecfeeFlag.RECFEE_TOACT.getCode();
        if (1 == 1) {
            for (FsPaymentinfo record : fsPaymentinfos) {
                updatePaymentinfo(record, recfeeflag, processsts);
            }
        } else {
            //更新处理状态为到账发送失败
            for (FsPaymentinfo record : fsPaymentinfos) {
                updatePaymentinfo(record, recfeeflag, ProcessStatus.PROCESS_TOACTFAIL.getCode());
            }
        }

    }

    //
    public void updatePaymentinfo(FsPaymentinfo record, String recfeeflag, String processsts) throws InvocationTargetException, IllegalAccessException {
        //更新到账信息、处理状态 插入历史记录
        OperatorManager operatorManager = SystemService.getOperatorManager();
        Date dt = new Date();
        record.setRecfeeflag(recfeeflag);
        record.setProcessstatus(processsts);
        if (processsts.equals(ProcessStatus.PROCESS_CONFIRMSUC.getCode())) {
            record.setBankrecdate(sdf.format(dt));              //设置收款日期
        } else if (processsts.equals(ProcessStatus.PROCESS_TOACTSUC.getCode())) {
            record.setBankacctdate(sdf.format(dt));             //设置记账日期
        }
        record.setLastUpdBy(operatorManager.getOperatorId());
        record.setLastUpdDate(dt);
        FsPaymentinfohis fsPaymentinfohis = new FsPaymentinfohis();
        BeanUtils.copyProperties(fsPaymentinfohis, record);
        fsPaymentinfohis.setCreatedBy(operatorManager.getOperatorId());
        fsPaymentinfohis.setCreatedDt(dt);
        //日志表插入
        SysJoblog sysJoblog = new SysJoblog();
        sysJoblog.setTablename("fs_paymentinfo");
        sysJoblog.setRowpkid(record.getPaynotescode());
        sysJoblog.setJobname("更新");
        sysJoblog.setJobdesc("发送后更新:处理状态=" + processsts);
        sysJoblog.setJobtime(dt);
        sysJoblog.setJobuserid(operatorManager.getOperatorId());
        sysJoblog.setJobusername(operatorManager.getOperatorName());
        FsPaymentinfoExample payExample = new FsPaymentinfoExample();
        payExample.clear();
        payExample.createCriteria().andPaynotescodeEqualTo(record.getPaynotescode());
        fsPaymentinfoMapper.updateByExampleSelective(record, payExample);
        //插入历史
        fsPaymentinfohisMapper.insertSelective(fsPaymentinfohis);
        //插入日志
        sysJoblogMapper.insert(sysJoblog);
    }

    /**
     * 查询未到账、到账发送失败数据
     */
    public List<FsPaymentinfo> selectPayinfoNoToact() {
        String[] processstsAry = new String[2];
        processstsAry[0] = ProcessStatus.PROCESS_CONFIRMSUC.getCode();
        processstsAry[1] = ProcessStatus.PROCESS_TOACTFAIL.getCode();
        return fsPaymentinfoMapper.selectPayinfoForToact(processstsAry);
    }

    /**
     * 查询已发送到账信息
     */
    public List<FsPaymentinfo> selectPayinfoToact() {
        String[] processstsAry = new String[1];
        processstsAry[0] = ProcessStatus.PROCESS_TOACTSUC.getCode();
        return fsPaymentinfoMapper.selectPayinfoForToact(processstsAry);
    }

    /**
     * 查询退付信息*/
    public List<FsRefundinfo> selectRefundinfoByAppcd(String applcode) {
        List<FsRefundinfo> fsRefundinfoList = new ArrayList<FsRefundinfo>();
        fsRefundinfoList = fsRefundinfoMapper.selectByRefundAppCode(applcode);
        if (fsRefundinfoList == null || fsRefundinfoList.size() < 1) {
            //todo 接口
        }
        return fsRefundinfoList;
    }

    @Transactional
    public void sendRefundConfirm(List<FsRefundinfo> fsRefundinfoList,String refundProcessSts) {
        //todo 发送 如果发送成功
        if (1 == 1) {
            for (FsRefundinfo record : fsRefundinfoList) {
                updateRefundinfo(record,refundProcessSts);
            }
        } else {
            for (FsRefundinfo record : fsRefundinfoList) {
                updateRefundinfo(record, RefundProcessSts.PROCESS_CONFIRMFAIL.getCode());
            }
        }
    }

    private void updateRefundinfo(FsRefundinfo record,String refundProcessSts) {
        OperatorManager operatorManager = SystemService.getOperatorManager();
        Date dt = new Date();
        record.setLastUpdBy(operatorManager.getOperatorId());
        record.setLastUpdDate(dt);
        record.setProcessstatus(refundProcessSts);
        FsRefundinfohis fsRefundinfohis = new FsRefundinfohis();
        fsRefundinfohis.setRefundapplycode(record.getRefundapplycode());
        fsRefundinfohis.setPaynotescode(record.getPaynotescode());
        fsRefundinfohis.setProcessstatus(refundProcessSts);
        fsRefundinfohis.setCreatedBy(operatorManager.getOperatorId());
        fsRefundinfohis.setCreatedDt(dt);
        fsRefundinfohis.setAreacode(record.getAreacode());
        //日志表插入
        SysJoblog sysJoblog = new SysJoblog();
        sysJoblog.setTablename("fs_refundinfo");
        sysJoblog.setRowpkid(record.getPaynotescode());
        sysJoblog.setJobname("更新");
        sysJoblog.setJobdesc("发送后更新:处理状态=" + refundProcessSts);
        sysJoblog.setJobtime(dt);
        sysJoblog.setJobuserid(operatorManager.getOperatorId());
        sysJoblog.setJobusername(operatorManager.getOperatorName());

        FsRefundinfoExample rfExample = new FsRefundinfoExample();
        rfExample.clear();
        rfExample.createCriteria().andRefundapplycodeEqualTo(record.getRefundapplycode());
        //插入退付表
        fsRefundinfoMapper.updateByExampleSelective(record,rfExample);
        //插入历史表
        fsRefundinfohisMapper.insertSelective(fsRefundinfohis);
        //插入日志
        sysJoblogMapper.insert(sysJoblog);
    }
 }
