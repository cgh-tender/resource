package cn.com.cgh.jvm.oom;

import java.nio.ByteBuffer;

/**
 * 直接内存溢出  java.lang.OutOfMemoryError: Direct buffer memory
 * -XX:MaxDirectMemorySize=100m
 */
public class DirecOom {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(128 * 1024 * 1024);

    }
}
