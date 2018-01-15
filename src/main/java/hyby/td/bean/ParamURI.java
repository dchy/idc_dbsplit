package hyby.td.bean;

/**
 * 业务相关
 * Created by 11019 on 17.12.21.
 */
public class ParamURI {
    private String paramProvider;   //参数提供方
    private String paramReceiver;   //参数接收方
    private String receiverUrl;     //应用方提供的业务接口
    private int receiverID;         //接口提供者地址编号
    private String updateTime;      //数据更新时间
    private String paramName;       //参数名

    @Override
    public String toString() {
        return "ParamURI{" +
                "paramProvider='" + paramProvider + '\'' +
                ", paramReceiver='" + paramReceiver + '\'' +
                ", receiverUrl='" + receiverUrl + '\'' +
                ", receiverID=" + receiverID +
                ", updateTime='" + updateTime + '\'' +
                ", paramName='" + paramName + '\'' +
                '}';
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
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

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
