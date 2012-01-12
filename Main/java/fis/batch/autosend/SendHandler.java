package fis.batch.autosend;

import fis.common.gwk.constant.CardSendFlg;
import fis.common.gwk.constant.RtnTagKey;
import fis.service.gwk.CardInfoService;
import fis.service.gwk.ConsumeInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;

import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: altiris-qdo
 * Date: 12-1-11
 * Time: 下午4:40
 * To change this template use File | Settings | File Templates.
 */
public class SendHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private CardInfoService cardInfoService;
    @Resource
    private ConsumeInfoService consumeInfoService;

    public void execute() {
        String rtnmsg = "";
        try {
            //发送卡信息
            rtnmsg = cardInfoService.sendAllCardinfos("266001");
        } catch (Exception ex) {
            logger.error("发送卡信息失败:" + ex.getMessage());
        }
        if (rtnmsg.equals(RtnTagKey.RESULT_SUCCESS)) {
            logger.info("发送成功");
        } else {
            logger.error("发送卡信息返回失败信息:" + rtnmsg);
        }
        String rtnmsg1 = "";
        try {
            rtnmsg1 = consumeInfoService.sendAllConsumeinfos("266001");
        } catch (Exception ex) {
            logger.error("发送消费信息失败:" + ex.getMessage());
        }
        if (rtnmsg1.equals(RtnTagKey.RESULT_SUCCESS)) {
            logger.info("发送成功");
        } else {
            logger.error("发送消费信息返回失败信息:" + rtnmsg1);
        }
    }
}
