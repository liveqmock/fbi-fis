package fis.view.fs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.repository.model.Ptoper;
import skyline.service.PlatformService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-12-31
 * Time: 下午2:41
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@RequestScoped
public class ValidateAction {
    private static final Logger logger = LoggerFactory.getLogger(ValidateAction.class);

    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;

    private String struser;
    private String strpwd;
    private String rtnmsg;

    public String onBtnCheckClick() {
        try {
            List<Ptoper> ptoperList = platformService.selectOperForValidate(struser, strpwd);
            if (ptoperList.size() < 1) {
                rtnmsg = "<script language='javascript'>rtnScript('false','用户名或密码错误，请重新输入。');</script>";
            } else {
                rtnmsg = "<script language='javascript'>rtnScript('true','');</script>";
            }
        } catch (Exception ex) {
            String msg = ex.getMessage().replaceAll("\n","").replaceAll("\r","");
            rtnmsg = "<script language='javascript'>rtnScript('false','" + msg + "');</script>";
            ex.printStackTrace();
            return null;
        }

        return null;
    }

    public PlatformService getPlatformService() {
        return platformService;
    }

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    public String getStruser() {
        return struser;
    }

    public void setStruser(String struser) {
        this.struser = struser;
    }

    public String getStrpwd() {
        return strpwd;
    }

    public void setStrpwd(String strpwd) {
        this.strpwd = strpwd;
    }

    public String getRtnmsg() {
        return rtnmsg;
    }

    public void setRtnmsg(String rtnmsg) {
        this.rtnmsg = rtnmsg;
    }
}
