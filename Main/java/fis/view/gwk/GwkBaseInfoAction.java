package fis.view.gwk;

import fis.common.BeanCopy;
import fis.repository.gwk.dao.GwkBaseBdgagencyMapper;
import fis.repository.gwk.model.GwkBaseBdgagency;
import fis.repository.gwk.model.GwkBaseBdgagencyExample;
import fis.service.gwk.GwkBaseInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import skyline.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-8
 * Time: ����5:33
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class GwkBaseInfoAction {
    private static final Logger logger = LoggerFactory.getLogger(GwkBaseInfoAction.class);
    @ManagedProperty(value = "#{gwkBaseInfoService}")
    private GwkBaseInfoService gwkBaseInfoService;
    private List<GwkBaseBdgagency> gwkBaseBdgagencyList;
    private String parambofcode;
    @PostConstruct
    public void init() {
        try{
            Map parammap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            parambofcode = parammap.get("bofcode").toString();
            gwkBaseBdgagencyList = gwkBaseInfoService.selectBdgagency(parambofcode);
        } catch (Exception ex) {
            logger.error("��ѯԤ�㵥λ��Ϣʧ��." + ex.getMessage());
            MessageUtil.addError("��ѯԤ�㵥λ��Ϣʧ��." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
        }
    }

    public String onBtnAcceptClick() {
        try{
            gwkBaseInfoService.getAllInfoBdgagency(parambofcode);                   //�ӿڻ�ȡ
        } catch (Exception ex) {
            logger.error("��ȡԤ�㵥λ��Ϣʧ��." + ex.getMessage());
            String msg = ex.getMessage() == null ? "" : ex.getMessage().replaceAll("\n", "").replaceAll("\r", "");
            MessageUtil.addError("��ȡԤ�㵥λ��Ϣʧ��." + msg);
            return null;
        }
        try {
            gwkBaseBdgagencyList = gwkBaseInfoService.selectBdgagency(parambofcode);//���ز�ѯ
        } catch (Exception ex) {
            logger.error("��ѯԤ�㵥λ��Ϣʧ��." + ex.getMessage());
            MessageUtil.addError("��ѯԤ�㵥λ��Ϣʧ��." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        MessageUtil.addInfo("�����ѻ�ȡ.");
        return null;
    }

    public GwkBaseInfoService getGwkBaseInfoService() {
        return gwkBaseInfoService;
    }

    public void setGwkBaseInfoService(GwkBaseInfoService gwkBaseInfoService) {
        this.gwkBaseInfoService = gwkBaseInfoService;
    }

    public List<GwkBaseBdgagency> getGwkBaseBdgagencyList() {
        return gwkBaseBdgagencyList;
    }

    public void setGwkBaseBdgagencyList(List<GwkBaseBdgagency> gwkBaseBdgagencyList) {
        this.gwkBaseBdgagencyList = gwkBaseBdgagencyList;
    }
}
