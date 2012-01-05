package fis.service;

import com.sun.org.apache.regexp.internal.RE;
import fis.common.BeanCopy;
import fis.common.constant.ProcessStatus;
import fis.common.constant.RecfeeFlag;
import fis.common.constant.RefundProcessSts;
import fis.repository.dao.*;
import fis.repository.model.*;
import gateway.service.BizInterService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.security.OperatorManager;
import skyline.service.SystemService;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Resource
    private BizInterService bizInterService;

    @Transactional
    public List<FsPaymentinfo> selectPayinfoByPaynotescd(String bofcode, String paynotescd, String checkcode) {
        List<FsPaymentinfo> fsPaymentinfoList = fsPaymentinfoMapper.selectPayinfoByPaynotescd(paynotescd, checkcode, bofcode);
        if (fsPaymentinfoList == null || fsPaymentinfoList.size() < 1) {
            //接口查询
            List<String> paramList = new ArrayList<String>();
            paramList.add(paynotescd);
            paramList.add(checkcode);
            List<Map<String, String>> mapList = null;
            try {
                mapList = bizInterService.getBizDatas("FS", bofcode, "queryNonTaxNotes", paramList);
            } catch (Exception e) {
                throw new RuntimeException("返回数据异常:" + e.getMessage());
            }
            Date dt = new Date();
            for (Map<String, String> m : mapList) {
                FsPaymentinfo fsPaymentinfo = (FsPaymentinfo) BeanCopy.copyObject("fis.repository.model.FsPaymentinfo", m);
                fsPaymentinfo.setVersion(1);
                fsPaymentinfo.setAreacode(bofcode);
                fsPaymentinfo.setCreatedDt(dt);
                fsPaymentinfo.setCreatedBy(SystemService.getOperatorManager().getOperatorId());
                fsPaymentinfo.setProcessstatus(ProcessStatus.PROCESS_INIT.getCode());
                fsPaymentinfo.setAgentbank("8129");
                fsPaymentinfoMapper.insertSelective(fsPaymentinfo);
            }
            fsPaymentinfoList = fsPaymentinfoMapper.selectPayinfoByPaynotescd(paynotescd, checkcode, bofcode);
        }
        return fsPaymentinfoList;
    }

    //发送收款确认
    @Transactional
    public void sendPayinfoConfirm(List<FsPaymentinfo> fsPaymentinfoList, String processsts, String bofcode) throws InvocationTargetException, IllegalAccessException {
        // 发送 如果发送成功
        String recfeeflag = RecfeeFlag.RECFEE_TOACT.getCode();
        for (FsPaymentinfo fsPaymentinfo : fsPaymentinfoList) {
            List<String> paramList = new ArrayList<String>();
            //PAYNOTESCODE，AGENTBANK，RECMETHOD，NOTESCODE，BANKRECDATE，NOTESKIND
            paramList.add(fsPaymentinfo.getPaynotescode());
            paramList.add(fsPaymentinfo.getAgentbank());
            paramList.add(fsPaymentinfo.getRecmethod().toString());
            paramList.add(fsPaymentinfo.getNotescode());
            Date dt = new Date();
            paramList.add(sdf.format(dt));
            paramList.add((fsPaymentinfo.getNoteskind() == null ? "" : fsPaymentinfo.getNoteskind().toString()));
            try {
                List<Map<String, String>> mapList = bizInterService.getBizDatas("FS", bofcode, "receiveNonTaxNotes", paramList);
                if (mapList != null && mapList.size() > 0 && !StringUtils.isEmpty(mapList.get(0).get("NonTaxNote"))) {
                    updatePaymentinfo(fsPaymentinfo, recfeeflag, processsts, bofcode);
                } else {
//                    updatePaymentinfo(fsPaymentinfo, recfeeflag, processsts, bofcode);
                    updatePaymentinfo(fsPaymentinfo, recfeeflag, ProcessStatus.PROCESS_CONFIRMFAIL.getCode(), bofcode);
                    throw new RuntimeException("收款确认发送返回报文失败:缴款书编号=" + fsPaymentinfo.getPaynotescode());
                }
            } catch (Exception e) {
                updatePaymentinfo(fsPaymentinfo, recfeeflag, ProcessStatus.PROCESS_CONFIRMFAIL.getCode(), bofcode);
                throw new RuntimeException("收款确认发送失败:缴款书编号=" + fsPaymentinfo.getPaynotescode() + "," + e.getMessage());
            }

        }

    }

    @Transactional
    public void sendPayinfoToact(FsPaymentinfo[] fsPaymentinfos, String processsts, String bofcode) throws InvocationTargetException, IllegalAccessException {
        //发送到账信息
        //如果   成功:处理状态=2 失败=4 ; 更新 到账标志=1
        String recfeeflag = RecfeeFlag.RECFEE_TOACT.getCode();
        for (FsPaymentinfo record : fsPaymentinfos) {
            List<String> paramList = new ArrayList<String>();
            // PAYNOTESCODE,NOTESCODE,BANKACCTDATE,RECFEEFLAG
            paramList.add(record.getPaynotescode());
            paramList.add(record.getNotescode());
            Date dt = new Date();
            paramList.add(sdf.format(dt));
            paramList.add(recfeeflag);
            boolean sendSuc = true;
            try {
                List<Map<String, String>> mapList = new BizInterService().getBizDatas("FS", bofcode, "confirmNonTaxNotes", paramList);
                if (mapList != null && mapList.size() > 0 && !StringUtils.isEmpty(mapList.get(0).get("NonTaxNote"))) {
                    updatePaymentinfo(record, recfeeflag, processsts, bofcode);
                } else {
//                    updatePaymentinfo(record, recfeeflag, processsts, bofcode);
                    updatePaymentinfo(record, recfeeflag, ProcessStatus.PROCESS_TOACTFAIL.getCode(), bofcode);
                    throw new RuntimeException("倒账确认发送返回报文失败:缴款书编号=" + record.getPaynotescode());
                }
            } catch (Exception e) {
                updatePaymentinfo(record, recfeeflag, ProcessStatus.PROCESS_TOACTFAIL.getCode(), bofcode);
                throw new RuntimeException("倒账确认发送失败:缴款书编号=" + record.getPaynotescode() + "," + e.getMessage());
            }
        }
    }

    //
    public void updatePaymentinfo(FsPaymentinfo record, String recfeeflag, String processsts,
                                  String bofcode) throws InvocationTargetException, IllegalAccessException {
        //更新到账信息、处理状态 插入历史记录
        OperatorManager operatorManager = SystemService.getOperatorManager();
        Date dt = new Date();
        FsPaymentinfo rcd = new FsPaymentinfo();
        rcd.setRecfeeflag(recfeeflag);
        rcd.setProcessstatus(processsts);
        if (processsts.equals(ProcessStatus.PROCESS_CONFIRMSUC.getCode())) {
            rcd.setBankrecdate(sdf.format(dt));              //设置收款日期
        } else if (processsts.equals(ProcessStatus.PROCESS_TOACTSUC.getCode())) {
            rcd.setBankacctdate(sdf.format(dt));             //设置记账日期
        }
        rcd.setLastUpdBy(operatorManager.getOperatorId());
        rcd.setLastUpdDate(dt);
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
        payExample.createCriteria().andPaynotescodeEqualTo(record.getPaynotescode()).andAreacodeEqualTo(bofcode);
        fsPaymentinfoMapper.updateByExampleSelective(rcd, payExample);
        //插入历史
        fsPaymentinfohisMapper.insertSelective(fsPaymentinfohis);
        //插入日志
        sysJoblogMapper.insert(sysJoblog);
    }

    /**
     * 查询未到账、到账发送失败数据
     */
    public List<FsPaymentinfo> selectPayinfoNoToact(String bofcode) {
        String[] processstsAry = new String[2];
        processstsAry[0] = ProcessStatus.PROCESS_CONFIRMSUC.getCode();
        processstsAry[1] = ProcessStatus.PROCESS_TOACTFAIL.getCode();
        String[] bofcodeAry = new String[1];
        bofcodeAry[0] = bofcode;
        Map<String, String[]> prostsMap = new HashMap<String, String[]>(2);
        prostsMap.put("bofcode", bofcodeAry);
        prostsMap.put("processstsAry", processstsAry);
        return fsPaymentinfoMapper.selectPayinfoForToact(prostsMap);
    }

    /**
     * 查询已发送到账信息
     */
    public List<FsPaymentinfo> selectPayinfoToact(String bofcode) {
        String[] processstsAry = new String[1];
        processstsAry[0] = ProcessStatus.PROCESS_TOACTSUC.getCode();
        String[] bofcodeAry = new String[1];
        bofcodeAry[0] = bofcode;
        Map<String, String[]> prostsMap = new HashMap<String, String[]>(2);
        prostsMap.put("bofcode", bofcodeAry);
        prostsMap.put("processstsAry", processstsAry);
        return fsPaymentinfoMapper.selectPayinfoForToact(prostsMap);
    }

    /**
     * */
    public List<FsPaymentinfo> selectPayinfo(String processstatus, String bofcode, String notescode,
                                             String recStartd, String recEndd, String acctStartd,
                                             String acctEndd) {
        return fsPaymentinfoMapper.selectPayinfoForQry(processstatus, bofcode
                , notescode, recStartd, recEndd, acctStartd, acctEndd);
    }

    /**
     * 查询退付信息
     */
    @Transactional
    public List<FsRefundinfo> selectRefundinfoByAppcd(String applcode, String bofcode, String performdept) throws Exception {
        List<FsRefundinfo> fsRefundinfoList = new ArrayList<FsRefundinfo>();
        fsRefundinfoList = fsRefundinfoMapper.selectByRefundAppCode(applcode, bofcode);
        if (fsRefundinfoList == null || fsRefundinfoList.size() < 1) {
            //接口
            List<String> paramList = new ArrayList<String>();
            // 申请书编号1+，+执收单位编码1
            paramList.add(applcode);
            paramList.add(performdept);
            List<Map<String, String>> mapList = new BizInterService().getBizDatas("FS", bofcode, "getreturnNonTaxNotes", paramList);
            for (Map<String, String> m : mapList) {
                FsRefundinfo fsRefundinfo = (FsRefundinfo) BeanCopy.copyObject("fis.repository.model.FsRefundinfo", m);
                fsRefundinfo.setVersion(1);
                fsRefundinfo.setAreacode(bofcode);
                fsRefundinfo.setCreatedBy(SystemService.getOperatorManager().getOperatorId());
                fsRefundinfo.setCreatedDt(new Date());
                fsRefundinfo.setProcessstatus(RefundProcessSts.PROCESS_INIT.getCode());
                fsRefundinfoMapper.insertSelective(fsRefundinfo);
            }
            fsRefundinfoList = fsRefundinfoMapper.selectByRefundAppCode(applcode, bofcode);
        }
        return fsRefundinfoList;
    }

    @Transactional
    public void sendRefundConfirm(List<FsRefundinfo> fsRefundinfoList, String refundProcessSts, String bofcode) {
        //发送 如果发送成功
        for (FsRefundinfo record : fsRefundinfoList) {
            //发送
            List<String> paramList = new ArrayList<String>();
            // REFUNDAPPLYCODE, PAYNOTESCODE
            paramList.add(record.getRefundapplycode());
            paramList.add(record.getPaynotescode());
            try {
                List<Map<String, String>> mapList = new BizInterService().getBizDatas("FS", bofcode, "confirmReturnNonTaxNotes", paramList);
                if (mapList != null && mapList.size() > 0 && !StringUtils.isEmpty(mapList.get(0).get("REFUNDAPPLYCODE"))
                        && !StringUtils.isEmpty(mapList.get(0).get("PAYNOTESCODE"))) {
                    updateRefundinfo(record, refundProcessSts);
                } else {
//                    updateRefundinfo(record, refundProcessSts);
                    updateRefundinfo(record, RefundProcessSts.PROCESS_CONFIRMFAIL.getCode());
                    throw new RuntimeException("退付确认发送返回报文失败:申请书编号=" + record.getRefundapplycode());
                }
            } catch (Exception e) {
                updateRefundinfo(record, RefundProcessSts.PROCESS_CONFIRMFAIL.getCode());
                throw new RuntimeException("退付确认发送失败:申请书编号=" + record.getRefundapplycode() + "," + e.getMessage());
            }

        }
    }

    private void updateRefundinfo(FsRefundinfo record, String refundProcessSts) {
        OperatorManager operatorManager = SystemService.getOperatorManager();
        Date dt = new Date();
        FsRefundinfo rcd = new FsRefundinfo();
        rcd.setLastUpdBy(operatorManager.getOperatorId());
        rcd.setLastUpdDate(dt);
        rcd.setProcessstatus(refundProcessSts);
        FsRefundinfohis fsRefundinfohis = new FsRefundinfohis();
        fsRefundinfohis.setRefundapplycode(record.getRefundapplycode());
        fsRefundinfohis.setPaynotescode(record.getPaynotescode());
        fsRefundinfohis.setProcessstatus(record.getProcessstatus());
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
        fsRefundinfoMapper.updateByExampleSelective(rcd, rfExample);
        //插入历史表
        fsRefundinfohisMapper.insertSelective(fsRefundinfohis);
        //插入日志
        sysJoblogMapper.insert(sysJoblog);
    }

    public List<FsRefundinfo> selectRefundinfo(String bofcode,String refundappcode,String refundProcessSts,
                                               String startd,String endd) throws ParseException {
        FsRefundinfoExample example = new FsRefundinfoExample();
        FsRefundinfoExample.Criteria criteria = example.createCriteria();
        criteria.andAreacodeEqualTo(bofcode);
        if (refundappcode != null && !StringUtils.isEmpty(refundappcode)) {
            criteria.andRefundapplycodeEqualTo(refundappcode);
        }
        if (refundProcessSts != null && !StringUtils.isEmpty(refundProcessSts)) {
            criteria.andProcessstatusEqualTo(refundProcessSts);
        }
        if (startd != null && !StringUtils.isEmpty(startd)) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            String stdt = startd + " 00:00:00";
            Date startDate = df.parse(stdt);
//            String enddt = df.format(updatedt) + " 23:59:59";
            criteria.andLastUpdDateGreaterThanOrEqualTo(startDate);
        }
        if (endd != null && !StringUtils.isEmpty(endd)) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            String enddt = endd + " 23:59:59";
            Date endDate = df.parse(endd);
            criteria.andLastUpdDateLessThanOrEqualTo(endDate);
        }
        return fsRefundinfoMapper.selectByExample(example);
    }
}
