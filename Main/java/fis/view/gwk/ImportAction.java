package fis.view.gwk;

import gateway.ftp.pfbank.IMPBankFileThread;
import org.apache.commons.lang.StringUtils;
import skyline.common.utils.MessageUtil;
import sun.misc.MessageUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-12
 * Time: 下午3:14
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@RequestScoped
public class ImportAction {

    @ManagedProperty(value = "#{IMPBankFileThread}")
    private IMPBankFileThread impBankFileThread;

    private String filepath;

    public String onBtnImpClick() {
        if (StringUtils.isEmpty(filepath)) {
            MessageUtil.addError("请选择文件.");
            return null;
        }
        try{
            impBankFileThread.impFileForsingle(filepath);
        } catch (Exception ex) {
            MessageUtil.addError("导入失败.");
        }
        return null;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public IMPBankFileThread getImpBankFileThread() {
        return impBankFileThread;
    }

    public void setImpBankFileThread(IMPBankFileThread impBankFileThread) {
        this.impBankFileThread = impBankFileThread;
    }
}
