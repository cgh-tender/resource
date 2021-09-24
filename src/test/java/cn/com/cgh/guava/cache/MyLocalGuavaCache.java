package cn.com.cgh.guava.cache;

import com.google.common.cache.*;
import com.google.common.util.concurrent.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class MyLocalGuavaCache {
    static {
        CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder();

        RemovalListener<String,Object> removalListener = RemovalListeners.asynchronous(removalNotification -> {
            Object value = removalNotification.getValue();
            System.out.println(value);
            System.out.println(removalNotification.toString());
        }, Executors.newSingleThreadExecutor());

        LoadingCache<String, Object> build = builder.removalListener(removalListener)
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String key) throws Exception {
                        return null;
                    }
                });

    }
    private static ThreadPoolTaskExecutor executor;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

        ListenableFuture future1 = service.submit(new Callable<Integer>() {
            public Integer call() throws InterruptedException {
                Thread.sleep(1000);
                System.out.println("call future 1.");
                return 1;
            }
        });

        ListenableFuture future2 = service.submit(new Callable<Integer>() {
            public Integer call() throws InterruptedException {
                Thread.sleep(1000);
                System.out.println("call future 2.");
                //       throw new RuntimeException("----call future 2.");
                return 2;
            }
        });

        final ListenableFuture allFutures = Futures.allAsList(future1, future2);

    }
}
