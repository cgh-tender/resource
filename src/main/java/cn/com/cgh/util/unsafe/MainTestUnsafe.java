package cn.com.cgh.util.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class MainTestUnsafe {
    private Unsafe unsafe;
    private Long baseCount = 10L;
    public static void main(String[] args) {
        MainTestUnsafe mainTestUnsafe = new MainTestUnsafe();
        try {
            mainTestUnsafe.setUnsafe(mainTestUnsafe.getUnsafeInstance());
            mainTestUnsafe.aaa();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    public void aaa() throws InterruptedException {
        unsafe.putInt(this,baseCount,10);
        unsafe.compareAndSwapInt(this,baseCount,10,20);
        unsafe.compareAndSwapInt(this,baseCount,10,30);
        Thread.sleep(1000);

        System.out.println(unsafe.getIntVolatile(this,baseCount));
    }

    //使用方法
    public static Unsafe getUnsafeInstance() throws SecurityException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);
        return (Unsafe) theUnsafeInstance.get(Unsafe.class);
    }

    public Unsafe getUnsafe() {
        return unsafe;
    }

    public void setUnsafe(Unsafe unsafe) {
        this.unsafe = unsafe;
    }
}
