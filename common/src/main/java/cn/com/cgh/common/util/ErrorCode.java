package cn.com.cgh.common.util;

public enum ErrorCode {
    FAILED(-1, "失败"),
    SUCCESS(0, "成功");
    private long code;
    private String msg;
    ErrorCode(final long code, final String msg) {
        this.code = code;
        this.msg = msg;
    }
    public long getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public ErrorCode setMsg(String msg){
        this.msg = msg;
        return this;
    }
}
