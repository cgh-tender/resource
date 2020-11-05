package cn.com.cgh.jvm.oom;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * 方法区导致内存溢出
 * -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m -XX:+PrintGCDetails
 * java.lang.OutOfMemoryError: Metaspace
 */
public class MethodAreaOutOfMemory {
    public static void main(String[] args) {
        while (true){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(TestObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> methodProxy.invokeSuper(0,objects));
            enhancer.create();
        }
    }

    public static class TestObject{
        private double a = 34.53;
        private Integer b = 9999999;
    }
}
