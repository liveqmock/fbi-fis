package fis.view.gwk;

import fis.common.gwk.constant.ConfirmPayFlg;
import fis.common.gwk.constant.PayStatus;
import fis.repository.gwk.model.GwkPaybackinfo;
import fis.service.gwk.PaybackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-8
 * Time: ����11:13
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class PaybackAction {
    private static final Logger logger = LoggerFactory.getLogger(PaybackAction.class);
    @ManagedProperty(value = "#{paybackService}")
    private PaybackService paybackService;
    private List<GwkPaybackinfo> gwkPaybackinfoList;
    private PayStatus payStatus = PayStatus.SPDB_INIT;
    private ConfirmPayFlg confirmPayFlg = ConfirmPayFlg.CONFIRMPAY_INIT;
    private String vchid;
    private String parambofcode;

    @PostConstruct
    public void init() {
        try {
            Map parammap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            parambofcode = parammap.get("bofcode").toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //����֧��ƾ֤��ȡ����
    public String onBtnAcceptClick() {
        try {
            gwkPaybackinfoList = paybackService.getPaybackinfoByVch(parambofcode,vchid);
            if (gwkPaybackinfoList == null || gwkPaybackinfoList.size() < 1) {
                MessageUtil.addWarn("û�����ݣ�����ƾ֤���Ƿ���ȷ��");
                return null;
            }
            GwkPaybackinfo record = gwkPaybackinfoList.get(0);
            if (record.getConfirmpayflag().equals(ConfirmPayFlg.CONFIRMPAY_VALID.getCode())) {
                String confirmtitle = ConfirmPayFlg.CONFIRMPAY_VALID.getTitle();
                MessageUtil.addInfo("��ƾ֤״̬Ϊ" + confirmtitle);
                setButtonDisabled(true);
                return null;
            }
            setButtonDisabled(false);
        } catch (Exception ex) {
            logger.error("��ȡ֧��ƾ֤��Ϣʧ��:ƾ֤��=" + vchid + ";" + ex.getMessage());
            String msg = ex.getMessage() == null ? "" : ex.getMessage().replaceAll("\n", "").replaceAll("\r", "");
            MessageUtil.addError("��ȡ֧��ƾ֤��Ϣʧ��:" + msg);
            return null;
        }
        return null;
    }

    //�տ�ȷ��
    public String onBtnConfirmClick() {
        try {
            paybackService.updatePaybackinfoByVch(parambofcode,vchid);
            setButtonDisabled(true);
        } catch (Exception ex) {
            logger.error("����ȷ���տ�ʧ��:ƾ֤��=" + vchid + ";" + ex.getMessage());
            String msg = ex.getMessage() == null ? "" : ex.getMessage().replaceAll("\n", "").replaceAll("\r", "");
            MessageUtil.addError("����ȷ���տ�ʧ��:" + msg);
            return null;
        }
        try {
            gwkPaybackinfoList = paybackService.getPaybackinfoByVch(parambofcode,vchid);
        } catch (Exception ex) {
            logger.error("ȷ�ϳɹ����ѯʧ�ܡ�");
            return null;
        }
        MessageUtil.addInfo("�տ�ȷ�ϳɹ���");
        return null;
    }

    /*����button״̬*/
    private void setButtonDisabled(boolean disabled) {
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        HtmlCommandButton btnPay = (HtmlCommandButton) viewRoot.findComponent("editForm:btnConfirm");
        btnPay.setDisabled(disabled);
    }

    public PaybackService getPaybackService() {
        return paybackService;
    }

    public void setPaybackService(PaybackService paybackService) {
        this.paybackService = paybackService;
    }

    public List<GwkPaybackinfo> getGwkPaybackinfoList() {
        return gwkPaybackinfoList;
    }

    public void setGwkPaybackinfoList(List<GwkPaybackinfo> gwkPaybackinfoList) {
        this.gwkPaybackinfoList = gwkPaybackinfoList;
    }

    public PayStatus getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(PayStatus payStatus) {
        this.payStatus = payStatus;
    }

    public String getVchid() {
        return vchid;
    }

    public void setVchid(String vchid) {
        this.vchid = vchid;
    }

    public ConfirmPayFlg getConfirmPayFlg() {
        return confirmPayFlg;
    }

    public void setConfirmPayFlg(ConfirmPayFlg confirmPayFlg) {
        this.confirmPayFlg = confirmPayFlg;
    }
}
