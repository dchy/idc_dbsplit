package hyby.td.service.impl;

import hyby.td.bean.ParamData;
import hyby.td.bean.ParamURI;
import hyby.td.dao.idcDataSource.ParamDataDao;
import hyby.td.utils.HttpRequestUtils;
import hyby.td.utils.MailUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 推送参数数据到应用方
 * Created by 11019 on 17.12.21.
 */
public class PushParamServiceImpl implements Runnable {
    private JSONObject paramData;
    private ParamURI paramURI;
    private String createTime;
    private ParamDataDao paramDataDao;
//    private ParamDataTestDao paramDataDao;
    private int id;

    public PushParamServiceImpl(JSONObject paramData, ParamURI paramURI, String createTime, ParamDataDao paramDataDao, int id) {
        this.paramData = paramData;
        this.paramURI = paramURI;
        this.createTime = createTime;
        this.paramDataDao = paramDataDao;
        this.id = id;
    }

    public PushParamServiceImpl() {
    }

    private String toMail =  "dingcy@rxjy.com";

    static Logger logger = LoggerFactory.getLogger(PushParamServiceImpl.class);

    @Override
    public void run() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String urlAddress = paramURI.getReceiverUrl();

        //非标准post
//        String paramName = paramURI.getParamName();
//        if(null != paramName || !paramName.equals("")){
//            String[] paramNameSplit = paramName.split(",");
//            if(null != paramData){
//                for (int i = 0; i < paramNameSplit.length; i++) {
//                    String s = paramNameSplit[i];
//                    if(i==0){
//                        urlAddress = urlAddress + "?" + s +"=" +paramData.getString(paramNameSplit[i]);
//                    }else {
//                        urlAddress = urlAddress + "&" + s +"=" +paramData.getString(paramNameSplit[i]);
//                    }
//                }
//            }
//        }


        int retryCount = 0;
        JSONObject jsonObject = null;

        while (retryCount<5){
            try {
                //调用参数接收方接口
//                String s = HttpRequestUtils.httpPostStr(urlAddress, null, false);
                jsonObject = HttpRequestUtils.httpPost(urlAddress, paramData, false);
                if(!jsonObject.getString("statusCode").equals("1")){
                    throw new Exception("更新项目底层数据失败");
                }
                //更新项目底层中参数状态信息
                try {
//                    int updateParam = paramDataDao.updateParam(retryCount, 1,id,"",dateFormat.format(new Date()));
                    int updateParam = paramDataDao.updateParam(retryCount, 1,id,jsonObject.getString("statusMsg"),dateFormat.format(new Date()));
                    if(updateParam<=0){
                        throw new Exception("更新项目底层数据失败");
                    }
                }catch (Exception e){
                    ParamData paramData = paramDataDao.selectParamData(id);
                    MailUtils.sendMail("数据拆分失败","数据推送成功，但更新项目底层数据状态失败,id:"+paramData.getReceiverID()+"，参数为："+paramData.getParam()+"创建时间："+paramData.getCreateTime()+"---------------------",toMail);
                    logger.error(e.getMessage()+",数据推送成功，但更新项目底层数据状态失败,id:"+paramData.getReceiverID()+"，参数为："+paramData.getParam()+"创建时间："+paramData.getCreateTime());
                }
                break;

            }catch (Exception e){
                retryCount++;
                logger.error(e.getMessage());
            }
        }

        if (retryCount>4){
            ParamData paramData = new ParamData();
            try {
//                int updateParam = paramDataDao.updateParam(retryCount, 0, id,"",dateFormat.format(new Date()));
                int updateParam = paramDataDao.updateParam(retryCount, 0, id,jsonObject.getString("statusMsg"),dateFormat.format(new Date()));
                if(updateParam<=0){
                    throw new Exception("更新项目底层失败");
                }
                paramData = paramDataDao.selectParamData(id);
            }catch (Exception e){
                paramData = paramDataDao.selectParamData(id);
                MailUtils.sendMail("数据拆分失败","数据推送失败，且更新项目底层数据状态失败,id:"+paramData.getReceiverID()+"，参数为："+paramData.getParam()+"创建时间："+paramData.getCreateTime()+"---------------------",toMail);
                logger.error(e.getMessage()+",数据推送失败，且更新项目底层数据状态失败,id:"+paramData.getReceiverID()+"，参数为："+paramData.getParam()+"创建时间："+paramData.getCreateTime());
            }
            MailUtils.sendMail("数据拆分失败","数据推送失败,id:"+paramData.getReceiverID()+"，参数为："+paramData.getParam()+"创建时间："+paramData.getCreateTime()+"---------------------",toMail);
            logger.error("数据推送失败,id:"+paramData.getReceiverID()+"，参数为："+paramData.getParam()+"创建时间："+paramData.getCreateTime());
        }
    }
}
