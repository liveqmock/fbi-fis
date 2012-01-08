package fis.view.gwk;

import fis.common.gwk.constant.ConsumeInfoSts;
import fis.repository.gwk.model.GwkConsumeinfo;
import fis.service.gwk.ConsumeInfoService;
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
 * Time: 下午10:02
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
    private String parambofcode;

    @PostConstruct
    public void init() {
        try {
            Map parammap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            parambofcode = parammap.get("bofcode").toString();
            gwkConsumeinfoList = consumeInfoService.selectConsumeSendNo(parambofcode);
        } catch (Exception ex) {
            logger.error("查询未发送消费信息失败." + ex.getMessage());
            String msg = ex.getMessage() == null ? "" : ex.getMessage().replaceAll("\n", "").replaceAll("\r", "");
            MessageUtil.addError("查询未发送消费信息失败:" + msg);
        }
    }

    public String onBtnSendClick() {
        if (gwkConsumeinfos.length < 1) {
            MessageUtil.addInfo("请选择至少一条数据");
            return null;
        }
        try {
            consumeInfoService.sendConsumeinfo(gwkConsumeinfos,parambofcode);
        } catch (Exception ex) {
            logger.error("发送消费信息失败:" + ex.getMessage());
            String msg = ex.getMessage() == null ? "" : ex.getMessage().replaceAll("\n", "").replaceAll("\r", "");
            MessageUtil.addError("发送消费信息失败:" + msg);
            return null;
        }

        return null;
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
