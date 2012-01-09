package fis.view.gwk;

import fis.common.gwk.constant.RtnTagKey;
import fis.common.gwk.constant.CardSendFlg;
import fis.repository.gwk.model.GwkCardbaseinfo;
import fis.service.gwk.CardInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Time: ����7:30
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class CardInfoSendAction {
    private static final Logger logger = LoggerFactory.getLogger(CardInfoSendAction.class);
    @ManagedProperty(value = "#{cardInfoService}")
    private CardInfoService cardInfoService;
    private List<GwkCardbaseinfo> gwkCardbaseinfoList;
    private GwkCardbaseinfo[] gwkCardbaseinfos;
    private CardSendFlg cardSendFlg = CardSendFlg.SEND_NO;
    private int rcdcount = 0;
    private String parambofcode;

    @PostConstruct
    public void init() {
        try {
            Map parammap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            parambofcode = parammap.get("bofcode").toString();
            gwkCardbaseinfoList = cardInfoService.selectCardinfos(parambofcode, CardSendFlg.SEND_NO.getCode());
            rcdcount = gwkCardbaseinfoList.size();
        } catch (Exception ex) {
            logger.error("��ѯδ���Ϳ���Ϣʧ��." + ex.getMessage());
            MessageUtil.addError("��ѯδ���Ϳ���Ϣʧ��." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
        }
    }

    //����
    public String onBtnSendClick() {
        if (gwkCardbaseinfos.length < 1) {
            MessageUtil.addWarn("��ѡ������һ������");
            return null;
        }
        String rtnmsg = "";
        try {
            //���Ϳ���Ϣ
            rtnmsg = cardInfoService.sendCardinfos(gwkCardbaseinfos, parambofcode);
        } catch (Exception ex) {
            logger.error("���Ϳ���Ϣʧ��:" + ex.getMessage());
            String msg = ex.getMessage() == null ? "" : ex.getMessage().replaceAll("\n", "").replaceAll("\r", "");
            MessageUtil.addError("���Ϳ���Ϣʧ��:" + msg);
            return null;
        }
        try {
            gwkCardbaseinfoList = cardInfoService.selectCardinfos(parambofcode, CardSendFlg.SEND_NO.getCode());
            rcdcount = gwkCardbaseinfoList.size();
        } catch (Exception ex) {
            logger.error("��ѯδ���Ϳ���Ϣʧ��." + ex.getMessage());
            MessageUtil.addError("��ѯδ���Ϳ���Ϣʧ��." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        if (rtnmsg.equals(RtnTagKey.RESULT_SUCCESS)) {
            MessageUtil.addInfo("���ͳɹ�");
        } else {
            logger.error("���Ϳ���Ϣʧ��:" + rtnmsg);
            MessageUtil.addInfo("����ʧ�ܿ���Ϣ��" + rtnmsg);
        }
        return null;
    }

    public int getRcdcount() {
        return rcdcount;
    }

    public void setRcdcount(int rcdcount) {
        this.rcdcount = rcdcount;
    }

    public CardInfoService getCardInfoService() {
        return cardInfoService;
    }

    public void setCardInfoService(CardInfoService cardInfoService) {
        this.cardInfoService = cardInfoService;
    }

    public List<GwkCardbaseinfo> getGwkCardbaseinfoList() {
        return gwkCardbaseinfoList;
    }

    public void setGwkCardbaseinfoList(List<GwkCardbaseinfo> gwkCardbaseinfoList) {
        this.gwkCardbaseinfoList = gwkCardbaseinfoList;
    }

    public GwkCardbaseinfo[] getGwkCardbaseinfos() {
        return gwkCardbaseinfos;
    }

    public void setGwkCardbaseinfos(GwkCardbaseinfo[] gwkCardbaseinfos) {
        this.gwkCardbaseinfos = gwkCardbaseinfos;
    }

    public CardSendFlg getCardSendFlg() {
        return cardSendFlg;
    }

    public void setCardSendFlg(CardSendFlg cardSendFlg) {
        this.cardSendFlg = cardSendFlg;
    }
}
