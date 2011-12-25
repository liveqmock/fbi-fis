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
 * Time: ����10:48
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
            //todo �ӿڲ�ѯ
        }
        return fsPaymentinfoList;
    }

    //�����տ�ȷ��
    @Transactional
    public void sendPayinfoConfirm(List<FsPaymentinfo> fsPaymentinfoList, String processsts) throws InvocationTargetException, IllegalAccessException {
        //todo ���� ������ͳɹ�
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
        //todo ���͵�����Ϣ
        //���   �ɹ�:����״̬=2 ʧ��=4 ; ���� ���˱�־=1
        String recfeeflag = RecfeeFlag.RECFEE_TOACT.getCode();
        if (1 == 1) {
            for (FsPaymentinfo record : fsPaymentinfos) {
                updatePaymentinfo(record, recfeeflag, processsts);
            }
        } else {
            //���´���״̬Ϊ���˷���ʧ��
            for (FsPaymentinfo record : fsPaymentinfos) {
                updatePaymentinfo(record, recfeeflag, ProcessStatus.PROCESS_TOACTFAIL.getCode());
            }
        }

    }

    //
    public void updatePaymentinfo(FsPaymentinfo record, String recfeeflag, String processsts) throws InvocationTargetException, IllegalAccessException {
        //���µ�����Ϣ������״̬ ������ʷ��¼
        OperatorManager operatorManager = SystemService.getOperatorManager();
        Date dt = new Date();
        record.setRecfeeflag(recfeeflag);
        record.setProcessstatus(processsts);
        if (processsts.equals(ProcessStatus.PROCESS_CONFIRMSUC.getCode())) {
            record.setBankrecdate(sdf.format(dt));              //�����տ�����
        } else if (processsts.equals(ProcessStatus.PROCESS_TOACTSUC.getCode())) {
            record.setBankacctdate(sdf.format(dt));             //���ü�������
        }
        record.setLastUpdBy(operatorManager.getOperatorId());
        record.setLastUpdDate(dt);
        FsPaymentinfohis fsPaymentinfohis = new FsPaymentinfohis();
        BeanUtils.copyProperties(fsPaymentinfohis, record);
        fsPaymentinfohis.setCreatedBy(operatorManager.getOperatorId());
        fsPaymentinfohis.setCreatedDt(dt);
        //��־�����
        SysJoblog sysJoblog = new SysJoblog();
        sysJoblog.setTablename("fs_paymentinfo");
        sysJoblog.setRowpkid(record.getPaynotescode());
        sysJoblog.setJobname("����");
        sysJoblog.setJobdesc("���ͺ����:����״̬=" + processsts);
        sysJoblog.setJobtime(dt);
        sysJoblog.setJobuserid(operatorManager.getOperatorId());
        sysJoblog.setJobusername(operatorManager.getOperatorName());
        FsPaymentinfoExample payExample = new FsPaymentinfoExample();
        payExample.clear();
        payExample.createCriteria().andPaynotescodeEqualTo(record.getPaynotescode());
        fsPaymentinfoMapper.updateByExampleSelective(record, payExample);
        //������ʷ
        fsPaymentinfohisMapper.insertSelective(fsPaymentinfohis);
        //������־
        sysJoblogMapper.insert(sysJoblog);
    }

    /**
     * ��ѯδ���ˡ����˷���ʧ������
     */
    public List<FsPaymentinfo> selectPayinfoNoToact() {
        String[] processstsAry = new String[2];
        processstsAry[0] = ProcessStatus.PROCESS_CONFIRMSUC.getCode();
        processstsAry[1] = ProcessStatus.PROCESS_TOACTFAIL.getCode();
        return fsPaymentinfoMapper.selectPayinfoForToact(processstsAry);
    }

    /**
     * ��ѯ�ѷ��͵�����Ϣ
     */
    public List<FsPaymentinfo> selectPayinfoToact() {
        String[] processstsAry = new String[1];
        processstsAry[0] = ProcessStatus.PROCESS_TOACTSUC.getCode();
        return fsPaymentinfoMapper.selectPayinfoForToact(processstsAry);
    }

    /**
     * ��ѯ�˸���Ϣ*/
    public List<FsRefundinfo> selectRefundinfoByAppcd(String applcode) {
        List<FsRefundinfo> fsRefundinfoList = new ArrayList<FsRefundinfo>();
        fsRefundinfoList = fsRefundinfoMapper.selectByRefundAppCode(applcode);
        if (fsRefundinfoList == null || fsRefundinfoList.size() < 1) {
            //todo �ӿ�
        }
        return fsRefundinfoList;
    }

    @Transactional
    public void sendRefundConfirm(List<FsRefundinfo> fsRefundinfoList,String refundProcessSts) {
        //todo ���� ������ͳɹ�
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
        //��־�����
        SysJoblog sysJoblog = new SysJoblog();
        sysJoblog.setTablename("fs_refundinfo");
        sysJoblog.setRowpkid(record.getPaynotescode());
        sysJoblog.setJobname("����");
        sysJoblog.setJobdesc("���ͺ����:����״̬=" + refundProcessSts);
        sysJoblog.setJobtime(dt);
        sysJoblog.setJobuserid(operatorManager.getOperatorId());
        sysJoblog.setJobusername(operatorManager.getOperatorName());

        FsRefundinfoExample rfExample = new FsRefundinfoExample();
        rfExample.clear();
        rfExample.createCriteria().andRefundapplycodeEqualTo(record.getRefundapplycode());
        //�����˸���
        fsRefundinfoMapper.updateByExampleSelective(record,rfExample);
        //������ʷ��
        fsRefundinfohisMapper.insertSelective(fsRefundinfohis);
        //������־
        sysJoblogMapper.insert(sysJoblog);
    }
 }
