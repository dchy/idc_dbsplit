package hyby.td.bean;

/**
 * 所有参数信息
 * Created by 11019 on 17.12.22.
 */
public class ParamDataAll {
    private int id;             //唯一键
    private String param;   //参数值
    private String paramName;//参数名
    private String createTime;  //参数创建时间
    private int retryCount;     //重试次数
    private int status;         //推送状态（0失败，1成功）
    private String paramProvider;      //参数提供方
    private String paramReceiver; //参数接收方
    private String receiverUrl;  //应用方提供的业务接口
    private String receiverResponse;    //参数接收者返回的信息

    @Override
    public String toString() {
        return "ParamDataAll{" +
                "id=" + id +
                ", param='" + param + '\'' +
                ", paramName='" + paramName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", retryCount=" + retryCount +
                ", status=" + status +
                ", paramProvider='" + paramProvider + '\'' +
                ", paramReceiver='" + paramReceiver + '\'' +
                ", receiverUrl='" + receiverUrl + '\'' +
                ", receiverResponse='" + receiverResponse + '\'' +
                '}';
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getParamProvider() {
        return paramProvider;
    }

    public void setParamProvider(String paramProvider) {
        this.paramProvider = paramProvider;
    }

    public String getParamReceiver() {
        return paramReceiver;
    }

    public void setParamReceiver(String paramReceiver) {
        this.paramReceiver = paramReceiver;
    }

    public String getReceiverUrl() {
        return receiverUrl;
    }

    public void setReceiverUrl(String receiverUrl) {
        this.receiverUrl = receiverUrl;
    }

    public String getReceiverResponse() {
        return receiverResponse;
    }

    public void setReceiverResponse(String receiverResponse) {
        this.receiverResponse = receiverResponse;
    }
}
