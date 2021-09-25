package cn.com.cgh.invock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class prov implements InvocationHandler {
    private Object object;
    public prov(Object o){
        this.object = o;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("前");
        Object invoke = method.invoke(object, args);
        System.out.println("后");
        return invoke;
    }
}
