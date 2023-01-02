package cn.com.common.util;


public class RSource<T> extends R<T>{

    public RSource(){};

    public RSource<T> data(T data){
        setData(data);
        return this;
    }

    public RSource<T> msg(String msg){
        setMsg(msg);
        return this;
    }

    public RSource<T> code(long code){
        setCode(code);
        return this;
    }

    public RSource<T> success(boolean success){
        setSuccess(success);
        return this;
    }

    public R<T> build(){
        return this;
    }
}
