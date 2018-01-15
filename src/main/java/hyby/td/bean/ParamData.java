package hyby.td.bean;

/**
 *
 * 参数数据相关
 * Created by 11019 on 17.12.21.
 */
public class ParamData {
    private int id;             //唯一键
    private int receiverID;     //接口提供者地址编号
    private String param;       //参数值
    private String createTime;  //参数创建时间
    private int retryCount;     //重试次数
    private int status;         //推送状态（0失败，1成功）
    private String receiverResponse = "";//参数接收者返回的信息
    private String updateTime;  //更新时间

    @Override
    public String toString() {
        return "ParamData{" +
                "id=" + id +
                ", receiverID=" + receiverID +
                ", param='" + param + '\'' +
                ", createTime='" + createTime + '\'' +
                ", retryCount=" + retryCount +
                ", status=" + status +
                ", receiverResponse='" + receiverResponse + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
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

    public String getReceiverResponse() {
        return receiverResponse;
    }

    public void setReceiverResponse(String receiverResponse) {
        this.receiverResponse = receiverResponse;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
