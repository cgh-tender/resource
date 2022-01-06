package cn.com.cgh.common.util;

import lombok.Data;

import java.io.Serializable;

/**
 * <p> 返回类型实体
 * @author Haidar
 * @date 2020/12/7 20:07
 **/
@Data
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

    public R(T data){
        this.data = data;
    }

    public R(long code,T data){
        this.code = code;
        this.data = data;
    }

    public static <T> R<T> failed(T data){
        return restResult(data,ErrorCode.FAILED);
    }

    public static <T> R<T> failed(String msg){
        return (R<T>) restResult(msg,ErrorCode.FAILED.setMsg(msg));
    }

    public static <T> R<T> failed(String msg,T data){
        return restResult(data,ErrorCode.FAILED.setMsg(msg));
    }

    public static <T> R<T> success(T data){
        return restResult(data,ErrorCode.SUCCESS);
    }

    public static <T> R<T> success(String msg){
        return (R<T>) restResult(msg,ErrorCode.SUCCESS.setMsg(msg));
    }

    public static <T> R<T> success(String msg,T data){
        return restResult(data,ErrorCode.SUCCESS.setMsg(msg));
    }

    public static <T> R<T> restResult(T data, ErrorCode errorCode) {
        return restResult(data, errorCode.getCode(),errorCode.getMsg());
    }

    private static <T> R<T> restResult(T data, long code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        apiResult.setSuccess(code == 1);
        return apiResult;
    }
}