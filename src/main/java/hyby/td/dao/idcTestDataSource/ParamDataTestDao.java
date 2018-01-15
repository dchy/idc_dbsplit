package hyby.td.dao.idcTestDataSource;

import hyby.td.bean.ParamData;
import hyby.td.bean.ParamDataAll;
import hyby.td.bean.ParamURI;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 11019 on 17.12.12.
 */
@Mapper
public interface ParamDataTestDao {
    //根据类型获取参数名
    ParamURI selectParamName(@Param("receiverID") int receiverID);
    //将参数数据存入数据库,并返回主键
    int insertParamData(@Param("paramData") ParamData paramData);
    //修改参数重试次数，推送状态
    int updateParam(@Param("retryCount") int retryCount, @Param("status") int status, @Param("id") int id, @Param("receiverResponse") String receiverResponse, @Param("updateTime") String updateTime);
    //查出参数相关内容
    ParamData selectParamData(@Param("id") int id);
    //查出参数ID
    int selectParamID(@Param("createTime") String createTime);
    //获取推送失败的参数
    List<ParamData> selectParamDataNo(@Param("status") int status);
    //查询所有的参数信息
    List<ParamDataAll> selectParamDataAll(@Param("status") int status);
    //查询单条参数信息
    ParamDataAll selectParamDataOne(@Param("id") int id);

}
