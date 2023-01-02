package cn.com.common.util;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1000L;
    /**
     * 返回编码
     */
    private long code;
    /**
     * 返回是否成功
     */
    private boolean success = true;
    /**
     * 返回描述信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    public R(){}

    public R(long code){
        this.code = code;
    }

    public R(String msg){
        this.msg = msg;
    }

    public R(long code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public static <T> RSource<T> failed(){
        return (RSource<T>) restResult().msg(ErrorCode.FAILED.getMsg()).code(ErrorCode.FAILED.getCode());
    }

    public static <T> RSource<T> success(T data){
        return (RSource<T>) restResult().code(ErrorCode.SUCCESS.getCode()).msg(ErrorCode.SUCCESS.getMsg()).data(data);
    }

    protected void setCode(long code) {
        this.code = code;
    }

    protected void setSuccess(boolean success) {
        this.success = success;
    }

    protected void setMsg(String msg) {
        this.msg = msg;
    }

    protected void setData(T data) {
        this.data = data;
    }

    private static <T> RSource<T> restResult() {
        return new RSource<T>();
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", success=" + success +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}