package dto;

/**
 * @title: Message
 * @description: 用来发送返回请求
 * @author: Minghua Zhou 周明华
 * @updateTime: 2019/12/1 11:38
 */
public class MessageDto {
    private String msg;
    private boolean isSuccess;

    public MessageDto() {
        super();
    }

    public MessageDto(String msg, boolean isSuccess) {
        this.msg = msg;
        this.isSuccess = isSuccess;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
