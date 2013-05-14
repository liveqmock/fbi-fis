package fis.batch.autosend;

import fis.common.gwk.constant.RtnTagKey;
import fis.service.gwk.CardInfoService;
import fis.service.gwk.ConsumeInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.advance.utils.PropertyManager;

import javax.annotation.Resource;

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
        //遍历财政局代码，发送相关信息 添加三个辅助变量 2012-12-06
        String[] strArrCode = null;
        String bofCode = "";
        String strFinanceName = "";
        strArrCode = PropertyManager.getProperty("gwk.finance.codeset").split(",");
        for (int i =0;i<strArrCode.length;i++){
            bofCode = strArrCode[i];
            strFinanceName = PropertyManager.getProperty("gwk.finance.name." + bofCode);
            try {
                //发送卡信息
                rtnmsg = cardInfoService.sendAllCardinfos(bofCode);
    //            rtnmsg = cardInfoService.sendAllCardinfos(PropertyManager.getProperty("gwk.areacode"));
            } catch (Exception ex) {
                logger.error("自动发送"+strFinanceName+"卡信息失败:" + ex.getMessage());
            }
            if (rtnmsg.equals(RtnTagKey.RESULT_SUCCESS)) {
                logger.info("发送成功");
            } else {
                logger.error("自动发送"+strFinanceName+"卡信息返回失败信息:" + rtnmsg);
            }
            String rtnmsg1 = "";
            try {
                //发送消费信息
                rtnmsg1 = consumeInfoService.sendAllConsumeinfos(bofCode);
//                rtnmsg1 = consumeInfoService.sendAllConsumeinfos(PropertyManager.getProperty("gwk.areacode"));
            } catch (Exception ex) {
                logger.error("自动发送"+strFinanceName+"消费信息失败:" + ex.getMessage());
            }
            if (rtnmsg1.equals(RtnTagKey.RESULT_SUCCESS)) {
                logger.info("发送成功");
            } else {
                logger.error("自动发送"+strFinanceName+"消费信息返回失败信息:" + rtnmsg1);
            }
        }
    }
}
