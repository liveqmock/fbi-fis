package fis.view.qdf;

import fis.repository.qdf.model.FsQdfPaymentDetail;
import fis.repository.qdf.model.FsQdfPaymentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Created with IntelliJ IDEA.
 * User: vincent
 * Date: 13-5-15
 * Time: ����9:46
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class PBStandardAction {
    private static final Logger logger = LoggerFactory.getLogger(PBStandardAction.class);

    private FsQdfPaymentInfo fsQdfPaymentInfo;
    private FsQdfPaymentDetail fsQdfPaymentDetail;

    private String pjzl; //Ʊ������
    private String jksbh;//�ɿ�����

    @PostConstruct
    public void init() {
        fsQdfPaymentInfo = new FsQdfPaymentInfo();
        fsQdfPaymentDetail = new FsQdfPaymentDetail();
    }

    public String onBtnAccept() {
        try {

        } catch (Exception ex) {
            logger.error("��ȡ�ɿ�����Ϣʧ��:���=" + jksbh + ";" + ex.getMessage());
            logger.error("��ȡ�ɿ�����Ϣʧ��:���=" + jksbh + ";" + ex.getMessage());
            MessageUtil.addError("��ȡ�ɿ�����Ϣʧ��:���=" + jksbh + ";" + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    //============================================================================

    public FsQdfPaymentInfo getFsQdfPaymentInfo() {
        return fsQdfPaymentInfo;
    }

    public void setFsQdfPaymentInfo(FsQdfPaymentInfo fsQdfPaymentInfo) {
        this.fsQdfPaymentInfo = fsQdfPaymentInfo;
    }

    public FsQdfPaymentDetail getFsQdfPaymentDetail() {
        return fsQdfPaymentDetail;
    }

    public void setFsQdfPaymentDetail(FsQdfPaymentDetail fsQdfPaymentDetail) {
        this.fsQdfPaymentDetail = fsQdfPaymentDetail;
    }

    public String getPjzl() {
        return pjzl;
    }

    public void setPjzl(String pjzl) {
        this.pjzl = pjzl;
    }

    public String getJksbh() {
        return jksbh;
    }

    public void setJksbh(String jksbh) {
        this.jksbh = jksbh;
    }
}
