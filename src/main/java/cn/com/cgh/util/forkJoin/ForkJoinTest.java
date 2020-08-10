package cn.com.cgh.util.forkJoin;

import java.util.concurrent.ForkJoinPool;

/**
 * <p> 分而置之
 * @author Haidar
 * @date 2020/8/10 18:12
 **/
public class ForkJoinTest {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int[] integer = new int[]{10,20,30};
        System.out.println(pool.invoke(new Task(integer,0,integer.length - 1)));

    }
}
