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
 * Time: 下午5:33
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
            logger.error("查询预算单位信息失败." + ex.getMessage());
            MessageUtil.addError("查询预算单位信息失败." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
        }
    }

    public String onBtnAcceptClick() {
        try{
            gwkBaseInfoService.getAllInfoBdgagency(parambofcode);                   //接口获取
        } catch (Exception ex) {
            logger.error("获取预算单位信息失败." + ex.getMessage());
            String msg = ex.getMessage() == null ? "" : ex.getMessage().replaceAll("\n", "").replaceAll("\r", "");
            MessageUtil.addError("获取预算单位信息失败." + msg);
            return null;
        }
        try {
            gwkBaseBdgagencyList = gwkBaseInfoService.selectBdgagency(parambofcode);//本地查询
        } catch (Exception ex) {
            logger.error("查询预算单位信息失败." + ex.getMessage());
            MessageUtil.addError("查询预算单位信息失败." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        MessageUtil.addInfo("数据已获取.");
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
