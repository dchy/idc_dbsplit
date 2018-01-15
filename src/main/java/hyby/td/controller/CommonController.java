package hyby.td.controller;

import hyby.td.bean.ParamDataAll;
import hyby.td.service.CommonPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by 11019 on 17.12.21.
 */
@CrossOrigin        //解决ajax跨域
@Api(description = "DB层面拆分")
@RestController
public class CommonController {

    @Autowired
    private CommonPostService commonPostService;

    @RequestMapping(method = RequestMethod.GET,value = "/dbsplit/show",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "所有参数详情",httpMethod = "GET")
    public List<ParamDataAll> findParamAll(
            @ApiParam(value = "状态",required = true,name = "status")
            @RequestParam(name = "status",required = true)
                    String status){
        int i = -1;
        try{
            i = Integer.parseInt(status);
        }catch (Exception e){
            i = -1;
        }
        List<ParamDataAll> paramDataAll = commonPostService.findParamDataAll(i);
        return paramDataAll;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/dbsplit/show/one",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "参数详情",httpMethod = "GET")
    public ParamDataAll findParamOne(
            @ApiParam(value = "唯一键",required = true,name = "id")
            @RequestParam(name = "id",required = true)
                    Integer id){
        ParamDataAll paramDataOne = commonPostService.findParamDataOne(id);
        return paramDataOne;
    }

    @ApiOperation(value = "DB层面拆分-POST请求",httpMethod = "POST")
    @RequestMapping(method = RequestMethod.POST,value = "/dbsplit/post",produces = "application/json;charset=UTF-8")
    public Map<String, String> commonPOST(
            @ApiParam(value = "类型",required = true,name = "type")
            @RequestParam(name="type",required = true)
                    String type,
            @ApiParam(value = "参数字符串",required = false,name = "param")
                    @RequestParam(name="param",required = false)
            String param){
        Map<String, String> stringStringMap = commonPostService.addParam(type, param);
        return stringStringMap;
    }
}
