package cn.com.common.util;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p> 返回类型实体
 * @author Haidar
 * @date 2020/12/7 20:07
 **/
@Getter
@Setter
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1000L;
    private static long sCode;
    private static String sMsg;
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
        this.sCode = code;
        this.code = code;
    }

    public R(String msg){
        this.sMsg = msg;
        this.msg = msg;
    }

    public R(long code,String msg){
        this.sCode = code;
        this.code = code;
        this.sMsg = msg;
        this.msg = msg;
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
        return restResult(data, !"".equalsIgnoreCase(sCode+"") ? sCode : errorCode.getCode() , !"".equalsIgnoreCase(sMsg) ? sMsg : errorCode.getMsg());
    }

    private static <T> R<T> restResult(T data, long code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        apiResult.setSuccess(code == 1);
        return apiResult;
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
