package hyby.td.service;

import hyby.td.bean.ParamDataAll;

import java.util.List;
import java.util.Map;

/**
 * Created by 11019 on 17.12.21.
 */
public interface CommonPostService {
    Map<String ,String> addParam(String type, String paramDatas);
    List<ParamDataAll> findParamDataAll(int status);
    ParamDataAll findParamDataOne(int id);
}
