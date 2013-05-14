package fis.view.gwk;

import fis.common.gwk.constant.CardSendFlg;
import fis.repository.gwk.model.GwkCardbaseinfo;
import fis.service.gwk.CardInfoService;
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
 * User: altiris-qdo
 * Date: 12-1-10
 * Time: ÏÂÎç5:19
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class CardInfoQryAction {
    private static final Logger logger = LoggerFactory.getLogger(CardInfoQryAction.class);
    @ManagedProperty(value = "#{cardInfoService}")
    private CardInfoService cardInfoService;
    private List<GwkCardbaseinfo> gwkCardbaseinfoList;
    private CardSendFlg cardSendFlg = CardSendFlg.SEND_NO;
    private String bdgagency;
    private String acct;
    private String parambofcode;
    private String strFinanceName;

    @PostConstruct
    public void init() {
        try {
            Map parammap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            parambofcode = parammap.get("bofcode").toString();
            strFinanceName = PropertyManager.getProperty("gwk.finance.name." + parambofcode);
            query(bdgagency, acct, parambofcode);
        } catch (Exception ex) {
            logger.error("²éÑ¯"+strFinanceName+"¿¨ÐÅÏ¢Ê§°Ü." + ex.getMessage());
            MessageUtil.addError("²éÑ¯¿¨ÐÅÏ¢Ê§°Ü." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
        }
    }

    public String onBtnQueryClick() {
        try {
            query(bdgagency, acct, parambofcode);
        } catch (Exception ex) {
            logger.error("²éÑ¯"+strFinanceName+"¿¨ÐÅÏ¢Ê§°Ü." + ex.getMessage());
            MessageUtil.addError("²éÑ¯¿¨ÐÅÏ¢Ê§°Ü." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
        }
        return null;
    }


    private void query(String bdgagencyname, String account, String bofcode) {
        gwkCardbaseinfoList = cardInfoService.selectCardinfoForQry(bdgagencyname, account, bofcode);
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

    public CardSendFlg getCardSendFlg() {
        return cardSendFlg;
    }

    public void setCardSendFlg(CardSendFlg cardSendFlg) {
        this.cardSendFlg = cardSendFlg;
    }

    public String getBdgagency() {
        return bdgagency;
    }

    public void setBdgagency(String bdgagency) {
        this.bdgagency = bdgagency;
    }

    public String getAcct() {
        return acct;
    }

    public void setAcct(String acct) {
        this.acct = acct;
    }
}
