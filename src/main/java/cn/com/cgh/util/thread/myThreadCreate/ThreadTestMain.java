package cn.com.cgh.util.thread.myThreadCreate;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.core.NamedThreadLocal;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import java.util.concurrent.*;

public class ThreadTestMain {
    public static void main(String[] args) {
        ThreadPoolExecutorFactoryBean factoryBean = new ThreadPoolExecutorFactoryBean();
        factoryBean.setThreadNamePrefix("  aaa ");
        RejectedExecutionHandler abortPolicy = new ThreadPoolExecutor.AbortPolicy();

        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(1);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1,
                1000,TimeUnit.MINUTES, queue,
                factoryBean, abortPolicy);
        threadPoolExecutor.execute(()->{
            throw new RuntimeException("new Test");
        });
        threadPoolExecutor.execute(()->{
            throw new RuntimeException("new Test");
        });
        threadPoolExecutor.shutdown();

    }
}
