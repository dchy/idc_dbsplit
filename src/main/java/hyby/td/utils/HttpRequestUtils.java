package hyby.td.utils;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;


/**
 * httpClient调取接口方式一
 */
public class HttpRequestUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);    //日志记录
 
    /**
     * httpPost
     * @param url  路径
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam){
        return httpPost(url, jsonParam, false);
    }



    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam, boolean noNeedResponse){
        //post请求返回结果
    	HttpClient httpClient = HttpClientBuilder.create().build();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "UTF-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            logger.info(new Date().toString());
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /**把json字符串转换成json对象**/
                    jsonResult = JSONObject.fromObject(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        logger.info(new Date().toString());
        return jsonResult;
    }

    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static String httpPostStr(String url, JSONObject jsonParam, boolean noNeedResponse){
        logger.info("-----------------begin-----------------");
        logger.info(new Date().toString());

        //post请求返回结果
        HttpClient httpClient = HttpClientBuilder.create().build();
        String res = "";
        HttpPost method = new HttpPost(url);
        method.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE,false);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            logger.info(new Date().toString());
            HttpResponse result = httpClient.execute(method);

            logger.info(new Date().toString());
            url = URLDecoder.decode(url, "UTF-8");
            logger.info(new Date().toString());
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return res;
                    }
                    res = str;
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        logger.info(new Date().toString());
        logger.info("-----------------end-----------------");
        return res;
    }
 
 
    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static JSONObject httpGet(String url){

        //get请求返回结果
        JSONObject jsonResult = null;
        try {
        	HttpClient httpClient = HttpClientBuilder.create().build();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);
 
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
                jsonResult = JSONObject.fromObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        }

        return jsonResult;
    }


    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
 public static String httpGetStr(String url){
        String strResult = null;
        //get请求返回结果
        JSONObject jsonResult = null;
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
               /**读取服务器返回过来的json字符串数据**/
                strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
//                jsonResult = JSONObject.fromObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                logger.error("get请求提交失败1:" + url);
                strResult =  "Status Code 500,请求的数据不存在或已弃用";
            }
        } catch (IOException e) {
            logger.error("get请求提交失败2:" + url, e);
           strResult =  "Status Code 500,请求的数据不存在或已弃用";
        }

        return strResult;
    }

    public static void main(String[] args) {
        JSONObject object = new JSONObject();
        String urlAddress = "http://api.swb.cs/api/ZA/Yw_KeHu_FenDan?OrderNumber=1111111&SheJiSheKaHao=1212";
        object.put("OrderNumber",1);
        object.put("SheJiSheKaHao",1);

        String s = HttpRequestUtils.httpPostStr(urlAddress, null, false);
        System.out.println(s);
    }

}