package hyby.td.job;


import hyby.td.bean.ParamData;
import hyby.td.bean.ParamURI;
import hyby.td.dao.idcDataSource.ParamDataDao;
import hyby.td.utils.HttpRequestUtils;
import hyby.td.utils.MailUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 再次推送
 * Created by 11019 on 17.12.21.
 */
//以下四个注解用于定时任务
@SuppressWarnings("all")
@Component
@Configurable
@EnableScheduling
public class PushAgain {
    private final Logger logger = LoggerFactory.getLogger(PushAgain.class);
    private String toMail =  "dingcy@rxjy.com";

    @Autowired
//    private ParamDataTestDao paramDataDao;
    private ParamDataDao paramDataDao;

    @Scheduled(cron = "0 */30 * * * ? ")
    public  void campareDateSendMail(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<ParamData> paramData = paramDataDao.selectParamDataNo(0);
        for (int i = 0; i < paramData.size(); i++) {
            ParamURI paramURI = paramDataDao.selectParamName(paramData.get(i).getReceiverID());
            String urlAddress= paramURI.getReceiverUrl();
            String params = paramData.get(i).getParam();
            JSONObject jsonObject = null;
            if(null!=params|| !params.equals("")){
                jsonObject = JSONObject.fromObject(params);
            }

            //非标准post
//            if(paramURI.getParamName()!=null || !paramURI.getParamName().equals("")){
//                String[] paramNameSplit = paramURI.getParamName().split(",");
//                for (int j = 0; j < paramNameSplit.length; j++) {
//                    String s = paramNameSplit[j];
//                    if(j==0){
//                        urlAddress = urlAddress + "?" + s +"=" +jsonObject.getString(paramNameSplit[j]);
//                    }else {
//                        urlAddress = urlAddress + "&" + s +"=" +jsonObject.getString(paramNameSplit[j]);
//                    }
//                }
//            }


            JSONObject object = null;
            int retryCount = 0;
            while (retryCount<5){
                try {
//                    String s = HttpRequestUtils.httpPostStr(urlAddress, null, false);
                    object = HttpRequestUtils.httpPost(urlAddress, jsonObject, false);
                    if(!object.getString("statusCode").equals("1")){
                        throw new Exception("更新项目底层数据失败");
                    }
                    try {
//                        int updateParam = paramDataDao.updateParam(retryCount, 1, paramData.get(i).getReceiverID(),"",dateFormat.format(new Date()));
                        int updateParam = paramDataDao.updateParam(retryCount, 1, paramData.get(i).getReceiverID(),object.getString("statusMsg"),dateFormat.format(new Date()));
                        if(updateParam<=0){
                            throw new Exception("更新项目底层数据失败");
                        }
                    }catch (Exception e){
                        ParamData paramDatas = paramDataDao.selectParamData(paramData.get(i).getReceiverID() );
                        MailUtils.sendMail("数据拆分失败","数据推送成功，但更新项目底层数据状态失败,id:"+paramDatas.getReceiverID()+"，参数为："+paramDatas.getParam()+"创建时间："+paramDatas.getCreateTime()+"---------------------",toMail);
                        logger.error(e.getMessage()+",数据推送成功，但更新项目底层数据状态失败,id:"+paramDatas.getReceiverID()+"，参数为："+paramDatas.getParam()+"创建时间："+paramDatas.getCreateTime());
                    }
                    break;
                }catch (Exception e){
                    retryCount++;
                    logger.error(e.getMessage());
                }
            }
            if (retryCount>4){
                ParamData paramDatas = new ParamData();
                try {
//                    int updateParam = paramDataDao.updateParam(retryCount, 0, paramData.get(i).getReceiverID(),"",dateFormat.format(new Date()));
                    int updateParam = paramDataDao.updateParam(retryCount, 0, paramData.get(i).getReceiverID(),object.getString("statusMsg"),dateFormat.format(new Date()));
                    if(updateParam<=0){
                        throw new Exception("更新项目底层失败");
                    }
                    paramDatas = paramDataDao.selectParamData(paramData.get(i).getReceiverID());
                }catch (Exception e){
                    paramDatas = paramDataDao.selectParamData( paramData.get(i).getReceiverID());
                    MailUtils.sendMail("数据拆分失败","数据推送失败，且更新项目底层数据状态失败,id:"+paramDatas.getReceiverID()+"，参数为："+paramDatas.getParam()+"创建时间："+paramDatas.getCreateTime()+"---------------------",toMail);
                    logger.error(e.getMessage()+",数据推送失败，且更新项目底层数据状态失败,id:"+paramDatas.getReceiverID()+"，参数为："+paramDatas.getParam()+"创建时间："+paramDatas.getCreateTime());
                }
                MailUtils.sendMail("数据拆分失败","数据推送失败,id:"+paramDatas.getReceiverID()+"，参数为："+paramDatas.getParam()+"创建时间："+paramDatas.getCreateTime()+"---------------------",toMail);
                logger.error("数据推送失败,id:"+paramDatas.getReceiverID()+"，参数为："+paramDatas.getParam()+"创建时间："+paramDatas.getCreateTime());
            }
        }
    }
}
