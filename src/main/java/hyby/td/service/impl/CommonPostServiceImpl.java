package hyby.td.service.impl;

import hyby.td.bean.ParamData;
import hyby.td.bean.ParamDataAll;
import hyby.td.bean.ParamURI;
import hyby.td.dao.idcDataSource.ParamDataDao;
import hyby.td.service.CommonPostService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接收参数返回结果，并异步推送参数给应用方
 * Created by 11019 on 17.12.21.
 */
@Service
public class CommonPostServiceImpl implements CommonPostService {

    @Autowired
    private ParamDataDao paramDataDao;
//    private ParamDataTestDao paramDataDao;
    @Override
    public Map<String ,String> addParam(String type, String paramDatas) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<String, String> map = new HashMap<>();
        //转换传入的type
        int i = 0;
        try{
            i = Integer.parseInt(type);
        }catch (Exception e){
            map.put("statusCode","0");
            map.put("statusMsg","传入的type错误");
            return map;
        }
        //根据类型type获取调用的url
        ParamURI paramURI = paramDataDao.selectParamName(i);
        JSONObject jsonObject = null;
        if(null != paramDatas && !paramDatas.equals("")){
            jsonObject = JSONObject.fromObject(paramDatas);
        }
        String createTime = dateFormat.format(new Date());

        //将参数数据存入数据库
        int id = 0;
        try {
            ParamData paramData = new ParamData();
            paramData.setParam(paramDatas);
            paramData.setCreateTime(createTime);
            paramData.setUpdateTime(createTime);
            paramData.setRetryCount(0);
            paramData.setReceiverID(i);
            paramData.setStatus(0);
            int insertCount = paramDataDao.insertParamData(paramData);
            id = paramDataDao.selectParamID(createTime);
            if(insertCount<=0){
                map.put("statusCode","0");
                map.put("statusMsg","信息获取失败");
                return map;
            }
        }catch (Exception e){
            map.put("statusCode","0");
            map.put("statusMsg","信息获取失败");
            return map;
        }
        //异步推送参数，推送给应用方
        PushParamServiceImpl pushParamService = new PushParamServiceImpl(jsonObject, paramURI,createTime,paramDataDao,id);
        Thread thread = new Thread(pushParamService);
        thread.start();
        map.put("statusCode","1");
        map.put("statusMsg","成功");
        return map;
    }

    /**
     * 获取参数详情
     * @param status
     * @return
     */
    @Override
    public List<ParamDataAll> findParamDataAll(int status) {
        List<ParamDataAll> paramDataAlls = paramDataDao.selectParamDataAll(status);
        return paramDataAlls;
    }

    @Override
    public ParamDataAll findParamDataOne(int id) {
        ParamDataAll paramDataAll = paramDataDao.selectParamDataOne(id);
        return paramDataAll;
    }


}
