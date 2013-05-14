package fis.view.gwk;

import fis.common.gwk.constant.ConsumeInfoSts;
import fis.common.gwk.constant.RtnTagKey;
import fis.repository.gwk.model.GwkConsumeinfo;
import fis.service.gwk.ConsumeInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.advance.utils.PropertyManager;
import skyline.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-8
 * Time: ����10:02
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ConsumeInfoSendAction {
    private static final Logger logger = LoggerFactory.getLogger(ConsumeInfoSendAction.class);
    @ManagedProperty(value = "#{consumeInfoService}")
    private ConsumeInfoService consumeInfoService;
    private List<GwkConsumeinfo> gwkConsumeinfoList;
    private GwkConsumeinfo[] gwkConsumeinfos;
    private ConsumeInfoSts consumeInfoSts = ConsumeInfoSts.SEND_INIT;
    private int rcdcount = 0;
    private String parambofcode;
    private String strFinanceName;

    @PostConstruct
    public void init() {
        try {
            Map parammap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            parambofcode = parammap.get("bofcode").toString();
            strFinanceName = PropertyManager.getProperty("gwk.finance.name." + parambofcode);
            gwkConsumeinfoList = consumeInfoService.selectConsumeSendNo(parambofcode);
            rcdcount = gwkConsumeinfoList.size();
        } catch (Exception ex) {
            logger.error("��ѯ"+strFinanceName+"δ����������Ϣʧ��." + ex.getMessage());
            String msg = ex.getMessage() == null ? "" : ex.getMessage().replaceAll("\n", "").replaceAll("\r", "");
            MessageUtil.addError("��ѯδ����������Ϣʧ��:" + msg);
        }
    }

    public String onBtnSendClick() {
        if (gwkConsumeinfos.length < 1) {
            MessageUtil.addInfo("��ѡ������һ������");
            return null;
        }
        String rtnmsg = "";
        try {
            rtnmsg = consumeInfoService.sendConsumeinfo(gwkConsumeinfos,parambofcode);
        } catch (Exception ex) {
            logger.error("����"+strFinanceName+"������Ϣʧ��:" + ex.getMessage());
            String msg = ex.getMessage() == null ? "" : ex.getMessage().replaceAll("\n", "").replaceAll("\r", "");
            MessageUtil.addError("����������Ϣʧ��:" + msg);
            return null;
        }
        try {
            Map parammap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            parambofcode = parammap.get("bofcode").toString();
            gwkConsumeinfoList = consumeInfoService.selectConsumeSendNo(parambofcode);
            rcdcount = gwkConsumeinfoList.size();
        } catch (Exception ex) {
            logger.error("��ѯ"+strFinanceName+"δ����������Ϣʧ��." + ex.getMessage());
            String msg = ex.getMessage() == null ? "" : ex.getMessage().replaceAll("\n", "").replaceAll("\r", "");
            MessageUtil.addError("��ѯδ����������Ϣʧ��:" + msg);
            return null;
        }
        if (rtnmsg.equals(RtnTagKey.RESULT_SUCCESS)) {
            MessageUtil.addInfo("���ͳɹ�");
        } else {
            logger.error("����"+strFinanceName+"������Ϣ����ʧ����Ϣ:" + rtnmsg);
            MessageUtil.addInfo("����������Ϣ����ʧ����Ϣ:" + rtnmsg);
        }

        return null;
    }

    public int getRcdcount() {
        return rcdcount;
    }

    public void setRcdcount(int rcdcount) {
        this.rcdcount = rcdcount;
    }

    public ConsumeInfoService getConsumeInfoService() {
        return consumeInfoService;
    }

    public void setConsumeInfoService(ConsumeInfoService consumeInfoService) {
        this.consumeInfoService = consumeInfoService;
    }

    public List<GwkConsumeinfo> getGwkConsumeinfoList() {
        return gwkConsumeinfoList;
    }

    public void setGwkConsumeinfoList(List<GwkConsumeinfo> gwkConsumeinfoList) {
        this.gwkConsumeinfoList = gwkConsumeinfoList;
    }

    public GwkConsumeinfo[] getGwkConsumeinfos() {
        return gwkConsumeinfos;
    }

    public void setGwkConsumeinfos(GwkConsumeinfo[] gwkConsumeinfos) {
        this.gwkConsumeinfos = gwkConsumeinfos;
    }

    public ConsumeInfoSts getConsumeInfoSts() {
        return consumeInfoSts;
    }

    public void setConsumeInfoSts(ConsumeInfoSts consumeInfoSts) {
        this.consumeInfoSts = consumeInfoSts;
    }
}
