package fis.service.gwk;

import fis.common.gwk.constant.ConfirmPayFlg;
import fis.common.gwk.constant.PayStatus;
import fis.repository.fs.dao.SysJoblogMapper;
import fis.repository.fs.model.SysJoblog;
import fis.repository.gwk.dao.GwkCardbaseinfoMapper;
import fis.repository.gwk.dao.GwkConsumeinfoMapper;
import fis.repository.gwk.dao.GwkPaybackinfoMapper;
import fis.repository.gwk.dao.GwkPaybackresultMapper;
import fis.repository.gwk.model.*;
import gateway.ftp.pfbank.Config;
import org.apache.ecs.html.Big;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.security.OperatorManager;
import skyline.service.SystemService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-6
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ImpExpService {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");
    private static DecimalFormat df = new DecimalFormat("#.##");
    @Resource
    private GwkCardbaseinfoMapper gwkCardbaseinfoMapper;
    @Resource
    private GwkConsumeinfoMapper gwkConsumeinfoMapper;
    @Resource
    private GwkPaybackresultMapper gwkPaybackresultMapper;
    @Resource
    private GwkPaybackinfoMapper gwkPaybackinfoMapper;
    @Resource
    private SysJoblogMapper sysJoblogMapper;

    /**
     * 查询还款数据 status=01初始;filesendflag=0初始;confirmpayflag=1确认还款
     */
    public ArrayList<ArrayList> selectPaybackinfos() throws Exception {
        Date dt = new Date();
        ArrayList<ArrayList> dataList = new ArrayList<ArrayList>();
        List<GwkPaybackinfo> paybackinfoList = gwkPaybackinfoMapper.selectPaybackinfo4File();
        for (GwkPaybackinfo record : paybackinfoList) {
            ArrayList<String> subDataList = new ArrayList<String>();
            subDataList.add("0310");    //银行代号
            subDataList.add(Config.getString("Bank_Code"));  //分行代号
            subDataList.add(sdf.format(dt));                 //记录产生日期
            subDataList.add("");                             //扣账帐户号码
            String stramt = record.getAmt().toString();
            subDataList.add(stramt);
            subDataList.add("156");                          //币种
            subDataList.add("");                             //还款日期 空
            subDataList.add(record.getAccount().toString()); //账号
            subDataList.add("00000");                        //强制校验标志
            subDataList.add(record.getIdtype() == null ? "" : record.getIdtype());
            subDataList.add(record.getIdnumber() == null ? "" : record.getIdnumber());
            subDataList.add("1");                            //扣款或者存款标志
            subDataList.add("0");                            //使用溢缴款或者透支额度标志
            subDataList.add("0");                            //余额不足时，全额扣款标志
            subDataList.add("20");                           //扣款/存款类型
            subDataList.add("公务卡批量还款");                //交易描述
            subDataList.add(record.getVoucherid());          //流水号(支付令号)
            subDataList.add("备用");                             //保留
            dataList.add(subDataList);
        }
        return dataList;
    }

    /*生成文件后更新filesendflag=yyyyMMdd*/
    @Transactional
    public void updatePaybackinfos() {
        Date dt = new Date();
        GwkPaybackinfoExample example = new GwkPaybackinfoExample();
        example.createCriteria().andStatusEqualTo(PayStatus.SPDB_INIT.getCode()).andFilesendflagEqualTo("0")
                .andConfirmpayflagEqualTo(ConfirmPayFlg.CONFIRMPAY_VALID.getCode());
        GwkPaybackinfo record = new GwkPaybackinfo();
        record.setFilesendflag(sdf.format(dt));
        gwkPaybackinfoMapper.updateByExampleSelective(record, example);
    }

    /**
     * 扣款结果导入
     */
    @Transactional
    public int importPaybackresult(ArrayList<ArrayList> impVar, String filename) throws Exception {
        int count = 0;
        if (impVar != null && impVar.size() > 0) {
            Date dt = new Date();
            //删除同文件数据
            GwkPaybackresultExample example = new GwkPaybackresultExample();
            example.createCriteria().andFilenameEqualTo(filename);
            gwkPaybackresultMapper.deleteByExample(example);
            for (ArrayList record : impVar) {
                GwkPaybackresult gwkPaybackresult = new GwkPaybackresult();
                gwkPaybackresult.setBankcode(record.get(0).toString());
                gwkPaybackresult.setRecoroccurdate(record.get(1).toString());
                gwkPaybackresult.setGatheringbankacctcode(record.get(2).toString());
                //金额 金额单位分->元
                double amt = Double.parseDouble(record.get(3).toString()) / 100;
                BigDecimal dcmAmt = BigDecimal.valueOf(Double.parseDouble(df.format(amt)));
                gwkPaybackresult.setAmt(dcmAmt);
                gwkPaybackresult.setCurcde(record.get(4).toString());
                gwkPaybackresult.setInacDate(record.get(5).toString());
                //金额 金额单位分->元
                double inamt = Double.parseDouble(record.get(6).toString()) / 100;
                BigDecimal dcmInamt = BigDecimal.valueOf(Double.parseDouble(df.format(inamt)));
                gwkPaybackresult.setInacAmt(dcmInamt);
                gwkPaybackresult.setAccount(record.get(7).toString());
                gwkPaybackresult.setDirectiontype(record.get(8).toString());
                gwkPaybackresult.setDklsh(record.get(9).toString());           //支付令号
                gwkPaybackresult.setResponsecode(record.get(10).toString());   //处理结果状态
                gwkPaybackresult.setExpand(record.get(11).toString());
                gwkPaybackresult.setFilename(filename);
                gwkPaybackresult.setAreacode(PropertyManager.getProperty("gwk.areacode"));    //市南区
                gwkPaybackresultMapper.insertSelective(gwkPaybackresult);
                //更新还款状态 还款日期
                GwkPaybackinfo gwkPaybackinfo = new GwkPaybackinfo();
                gwkPaybackinfo.setStatus(record.get(10).toString());
                gwkPaybackinfo.setPaybackdate(record.get(5).toString());
                //如果还款失败 更新filesendflag=0 重新发送
                if (!record.get(10).toString().equals(PayStatus.SPDB_PAYSUC.getCode())) {
                    gwkPaybackinfo.setFilesendflag("0");
                }
                gwkPaybackinfo.setOperdate(dt);
                GwkPaybackinfoExample paybackinfoExample = new GwkPaybackinfoExample();
                paybackinfoExample.clear();
                // 金额条件 问题 金额单位
                paybackinfoExample.createCriteria().andVoucheridEqualTo(record.get(9).toString())
                        .andAccountEqualTo(record.get(7).toString())
                        .andAmtEqualTo(dcmAmt);
                gwkPaybackinfoMapper.updateByExampleSelective(gwkPaybackinfo, paybackinfoExample);
                count++;
            }
            //日志表插入
            SysJoblog sysJoblog = new SysJoblog();
            sysJoblog.setTablename("gwk_paybackresult");
            sysJoblog.setRowpkid("0");
            sysJoblog.setJobname("插入");
            sysJoblog.setJobdesc("插入条数" + String.valueOf(count));
            sysJoblog.setJobtime(dt);
            sysJoblogMapper.insert(sysJoblog);
        }
        return count;
    }

    /**
     * 消费信息导入
     */
    @Transactional
    public int importConsumeinfo(ArrayList<ArrayList> impVar, String filename) throws Exception {
        int count = 0;
        if (impVar != null && impVar.size() > 0) {
            SimpleDateFormat sdf6 = new SimpleDateFormat("yyyyMM");
            Date dt = new Date();
            GwkConsumeinfoExample example = new GwkConsumeinfoExample();
            example.createCriteria().andFilenameEqualTo(filename);
            gwkConsumeinfoMapper.deleteByExample(example);
            for (ArrayList record : impVar) {
                GwkConsumeinfo gwkConsumeinfo = new GwkConsumeinfo();
                gwkConsumeinfo.setBusidate(record.get(0).toString());
                gwkConsumeinfo.setCorelsh(record.get(1).toString());
                gwkConsumeinfo.setTxType(record.get(2).toString());
                gwkConsumeinfo.setMsginfosign(record.get(3).toString());
                gwkConsumeinfo.setTxCd(record.get(4).toString());
                gwkConsumeinfo.setServertermcode(record.get(5).toString());
                gwkConsumeinfo.setCleardate(record.get(6).toString());
                gwkConsumeinfo.setMngdeptcode(record.get(7).toString());
                gwkConsumeinfo.setTransmitdeptcode(record.get(8).toString());
                gwkConsumeinfo.setReceivedeptcode(record.get(9).toString());
                gwkConsumeinfo.setLsh(record.get(10).toString());
                gwkConsumeinfo.setAccount(record.get(11).toString().substring(2));      //删除前两位
                gwkConsumeinfo.setCardname(queryCardname(record.get(11).toString().substring(2)));//插入卡人
                //金额 分转换成元为单位
                double money = Double.parseDouble(record.get(12).toString() + record.get(13).toString()) / 100;
                BigDecimal busimoney = BigDecimal.valueOf(Double.parseDouble(df.format(money)));
                gwkConsumeinfo.setBusimoney(busimoney);                    //消费金额
                gwkConsumeinfo.setUniontxdate(record.get(14).toString());
                gwkConsumeinfo.setBusitype(record.get(15).toString());
                gwkConsumeinfo.setTerminalsign(record.get(16).toString());
                gwkConsumeinfo.setBusicode(record.get(17).toString());
                gwkConsumeinfo.setBusiname(record.get(18).toString());
                gwkConsumeinfo.setCurcde(record.get(19).toString());
                gwkConsumeinfo.setSearchrefercode(record.get(20).toString());
                gwkConsumeinfo.setAuthorizecode(record.get(21).toString());
                gwkConsumeinfo.setBackcode(record.get(22).toString());
                gwkConsumeinfo.setDaycanceltype(record.get(23).toString());
                gwkConsumeinfo.setRemark(record.get(24).toString());
                gwkConsumeinfo.setExpand(record.get(25).toString());
                gwkConsumeinfo.setBranchcode(record.get(26).toString());
                gwkConsumeinfo.setOperdate(dt);
                gwkConsumeinfo.setFilename(filename);
                gwkConsumeinfo.setAreacode(PropertyManager.getProperty("gwk.areacode"));              //市南
                //设置最迟还款日期 busidate的下月20日
                Calendar c = Calendar.getInstance();
                c.clear();
                c.setTime(sdf.parse(record.get(0).toString()));
                c.add(Calendar.MONTH, 1);
                Date endTime = c.getTime();
                String limitdate = sdf6.format(endTime) + "20";
                gwkConsumeinfo.setLimitdate(limitdate);
                gwkConsumeinfoMapper.insertSelective(gwkConsumeinfo);
                count++;
            }
            //日志表插入
            SysJoblog sysJoblog = new SysJoblog();
            sysJoblog.setTablename("gwk_consumeinfo");
            sysJoblog.setRowpkid("0");
            sysJoblog.setJobname("插入");
            sysJoblog.setJobdesc("插入条数" + String.valueOf(count));
            sysJoblog.setJobtime(dt);
            sysJoblogMapper.insert(sysJoblog);
        }
        return count;
    }

    //根据卡号查找持卡人 导入消费信息时用
    private String queryCardname(String account) {
        GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
        example.clear();
        example.createCriteria().andAccountEqualTo(account);
        List<GwkCardbaseinfo> gwkCardbaseinfoList = gwkCardbaseinfoMapper.selectByExample(example);
        if (gwkCardbaseinfoList.size() < 1) {
            return "";
        } else {
            return gwkCardbaseinfoList.get(0).getCardname();
        }
    }

    /**
     * 开卡信息导入
     */
    @Transactional
    public int importCardOpen(ArrayList<ArrayList> impVar) throws Exception {
        int count = 0;
        if (impVar != null && impVar.size() > 0) {
            Date dt = new Date();
            GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
            for (ArrayList record : impVar) {
                GwkCardbaseinfo gwkCardbaseinfo = new GwkCardbaseinfo();
                gwkCardbaseinfo.setCardname(record.get(0).toString());
                gwkCardbaseinfo.setCardengname(record.get(1).toString());
                gwkCardbaseinfo.setIdtype(record.get(2).toString());
                gwkCardbaseinfo.setIdnumber(record.get(3).toString());
                gwkCardbaseinfo.setBdgagencyname(record.get(4).toString());
                gwkCardbaseinfo.setBdgagency(record.get(5).toString());
                gwkCardbaseinfo.setAccount(record.get(6).toString());
                gwkCardbaseinfo.setNewaccount(record.get(7).toString());
                gwkCardbaseinfo.setStartdate(sdf10.format(dt));                            //导入日期       yyyy-mm-dd
                String enddate10 = getformatdt10(record.get(8).toString());
                gwkCardbaseinfo.setEnddate(enddate10);        //yymm --> yyyy-mm-dd
                String createdate8 = record.get(9).toString();
                String createdate10 = createdate8.substring(0, 4) + "-" + createdate8.substring(4, 6) + "-" + createdate8.substring(6, 8);
                gwkCardbaseinfo.setCreatedate(createdate10);     //yyyymmdd 转换 yyyy-mm-dd
                double acdtAmt = Double.parseDouble(df.format(Double.parseDouble(record.get(10).toString())));
                gwkCardbaseinfo.setAccreditamt(BigDecimal.valueOf(acdtAmt));
                gwkCardbaseinfo.setTitle(record.get(11).toString());
                gwkCardbaseinfo.setExpand(record.get(12).toString());
                gwkCardbaseinfo.setBranchbankcode(record.get(13).toString());
                gwkCardbaseinfo.setAction("0");                 //新增
                gwkCardbaseinfo.setOperdate(dt);
                gwkCardbaseinfo.setAreacode(PropertyManager.getProperty("gwk.areacode"));          //浦发市南
                gwkCardbaseinfo.setBank(Config.getString("CARDBANK"));
                gwkCardbaseinfo.setGatheringbankacctname(Config.getString("GATHERINGBANKACCTNAME"));
                gwkCardbaseinfo.setGatheringbankacctcode(Config.getString("GATHERINGBANKACCTCODE"));
                gwkCardbaseinfo.setGatheringbankname(Config.getString("GATHERINGBANKNAME"));
                //删除存在数据
                example.clear();
                example.createCriteria().andAccountEqualTo(record.get(6).toString());
                gwkCardbaseinfoMapper.deleteByExample(example);
                gwkCardbaseinfoMapper.insertSelective(gwkCardbaseinfo);
                count++;
            }
            //日志表插入
            SysJoblog sysJoblog = new SysJoblog();
            sysJoblog.setTablename("gwk_cardbaseinfo");
            sysJoblog.setRowpkid("0");
            sysJoblog.setJobname("插入");
            sysJoblog.setJobdesc("插入条数" + String.valueOf(count));
            sysJoblog.setJobtime(dt);
            sysJoblogMapper.insert(sysJoblog);
        }
        return count;
    }

    private String getformatdt10(String yymm) {
        SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");
        String yyyymmdd = "20" + yymm + "01";
        Date tempdt = null;
        try {
            tempdt = sdf.parse(yyyymmdd);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(tempdt);
        c.set(Calendar.DATE, 1);
        c.roll(Calendar.DATE, -1);
        Date endTime = c.getTime();
        return sdf10.format(endTime);
    }

    /**
     * 换卡信息更新响应卡号
     */
    @Transactional
    public int importCardRep(ArrayList<ArrayList> impVar) throws Exception {
        int count = 0;
        if (impVar != null && impVar.size() > 0) {
            Date dt = new Date();
            GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
            for (ArrayList record : impVar) {
                GwkCardbaseinfo gwkCardbaseinfo = new GwkCardbaseinfo();
                gwkCardbaseinfo.setAccount(record.get(7).toString());
                gwkCardbaseinfo.setNewaccount(record.get(7).toString());
                gwkCardbaseinfo.setCreatedate(record.get(9).toString());
                gwkCardbaseinfo.setAction("1");
                gwkCardbaseinfo.setSentflag("0"); //为发送
                example.clear();
                example.createCriteria().andAccountEqualTo(record.get(6).toString());
                gwkCardbaseinfoMapper.updateByExampleSelective(gwkCardbaseinfo, example);
                count++;
            }
            //日志表插入
            SysJoblog sysJoblog = new SysJoblog();
            sysJoblog.setTablename("gwk_cardbaseinfo");
            sysJoblog.setRowpkid("0");
            sysJoblog.setJobname("更新");
            sysJoblog.setJobdesc("更新条数" + String.valueOf(count));
            sysJoblog.setJobtime(dt);
            sysJoblogMapper.insert(sysJoblog);
        }
        return count;
    }
}
