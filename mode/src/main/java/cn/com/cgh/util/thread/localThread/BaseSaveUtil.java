package cn.com.cgh.util.thread.localThread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p> save question_base 表记录 id
 * <p> 记录当前线程保存的id 数据
 * @author
 * @date 2020/9/4 17:39
 **/
public class BaseSaveUtil {
    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("pool-%d").build();

    /**
     * 获取线程池
     * @return
     */
    public ThreadPoolExecutor getThreadPool(){
        return new ThreadPoolExecutor(2, 4,
                1000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(100), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    /** 保证原子操作 有/无 不影响 */
    private static final AtomicInteger ID = new AtomicInteger(0);

    /** 线程唯一初始化值为 0 */
    private static final ThreadLocal<Integer> THREAD_LOCAL_ID = ThreadLocal.withInitial(ID::getAndIncrement);

    /**设置初始化值为 "" */
    private static final ThreadLocal<String> THREAD_LOCAL_NAME = ThreadLocal.withInitial(()-> "");

    private static final String COMMENT_START = "comment_";

    /** 保存 solr 中评论 id */
    private static final ThreadLocal<List<String>>  THREAD_LOCAL_COMMENT_ID = ThreadLocal.withInitial(()->new ArrayList<>());
    /**
     * <p> 在当前线程中保存 数据
     * @param
     * @return
     * @author Haidar
     * @date 2020/9/7 14:33
     **/
    public static void setId(int data){
        THREAD_LOCAL_ID.set(data);
    }

    /**
     * <p> 在当前线程中获取 id
     * @param
     * @return
     * @author Haidar
     * @date 2020/9/7 14:34
     **/
    public static int getId() {
        return THREAD_LOCAL_ID.get();
    }

    /**
     * 当前线程设置 name
     * @param name
     */
    public static void setName(String name){
        THREAD_LOCAL_NAME.set(name);
    }
    /**
     * 当前线程获取 name
     */
    public static String getName(){
        return THREAD_LOCAL_NAME.get();
    }

    /** data 属于 list<String> */
    public static void setCommentId(List<String> data){
        if (data instanceof List){
            THREAD_LOCAL_COMMENT_ID.set(data);
        }else {
            throw new RuntimeException("BaseSaveUtil.setCommentId() data < List<String> ");
        }
    }

    /** 保证为原子操作 进行方法级别加锁 */
    public synchronized static void addCommonId(Integer id){
        List<String> list = THREAD_LOCAL_COMMENT_ID.get();
        list.add(COMMENT_START + id);
        THREAD_LOCAL_COMMENT_ID.set(list);
    }


    /** 获取当前线程保存的所有的 question_comment 的 id */
    public static List<String> getCommonId(){
        return THREAD_LOCAL_COMMENT_ID.get();
    }


    /**
     * <p> 测试多线程 main 方法
     * @param
     * @return
     * @author Haidar
     * @date 2020/9/7 14:37
     **/
    public static void main(String[] args) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(3, 3,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 3; i++) {
            int finalI = i;
            singleThreadPool.execute(()-> {
                BaseSaveUtil.setId(finalI);
                BaseSaveUtil.setName(Thread.currentThread().getName());
                System.out.println(String.format(" %s - num : %d ", Thread.currentThread().getName(),finalI));
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                System.out.println(String.format("%s - %s - num : %d ", BaseSaveUtil.getName(),Thread.currentThread().getName(),BaseSaveUtil.getId()));
            });
        }
        singleThreadPool.shutdown();
    }
}
