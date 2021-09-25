package cn.com.cgh.util.thread.hash;

/**
 * hash 一般译做散列也有直接译为哈希的, 通过散列算法变换成固定他要和的输出该输入可能会散列成相同的输出
 * 所以不能把散列值确定唯一的输入值简单的说就是一种将任意他要和的消息压缩成一定固定长度消息摘要的函数
 * 常用HASH函数直接取余法,乘法取整法,平方取中法, MD5, SHA-1 加密算法(摘要算法)
 *
 * 解决HASH同一位置的方法
 * 1. 开放寻址
 * 2. 在散列 ThreadLocalMap
 * 3. 连地址法 ConcurrentHashMap
 *
 */
public class testMain {
    public static void main(String[] args) {
        System.out.println(63%4);
        System.out.println(63&3);
        System.out.println(Integer.toBinaryString(4));
    }
}
