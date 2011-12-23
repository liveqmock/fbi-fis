package fis.service;

import com.sun.org.apache.regexp.internal.RE;
import fis.common.constant.ProcessStatus;
import fis.common.constant.RecfeeFlag;
import fis.repository.dao.FsPaymentinfoMapper;
import fis.repository.dao.FsPaymentinfohisMapper;
import fis.repository.dao.SysJoblogMapper;
import fis.repository.model.FsPaymentinfo;
import fis.repository.model.FsPaymentinfoExample;
import fis.repository.model.FsPaymentinfohis;
import fis.repository.model.SysJoblog;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.security.OperatorManager;
import skyline.service.SystemService;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
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
    private SysJoblogMapper sysJoblogMapper;

    public List<FsPaymentinfo> selectPayinfoByPaynotescd(String paynotescd) {
        List<FsPaymentinfo> fsPaymentinfoList = fsPaymentinfoMapper.selectPayinfoByPaynotescd(paynotescd);
        if (fsPaymentinfoList.size() < 1) {
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
        sysJoblog.setJobdesc("���ͳɹ������:����״̬=" + processsts);
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
}
