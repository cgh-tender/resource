package cn.com.cgh.util.thread.callback;


import com.google.common.util.concurrent.*;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.*;

import static com.google.common.util.concurrent.Futures.addCallback;

/**
 * <p> guava FutureTaslCallBack
 * @author Haidar
 * @date 2020/10/19 13:53
 **/
public class FutureTaskCallBack {
    public static void main(String[] args) {
//        ListenableFutureTask<String> futureTask = new ListenableFutureTask<>(() -> {
//            Thread.sleep(5000);
//            System.out.println("futureTask success");
//            return "success";
//        });
//
//        FailureCallback callback = new ListenableFutureCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                System.out.println("callback onSuccess success");
//            }
//
//            @Override
//            public void onFailure(Throwable ex) {
//                System.out.println("callback onFailure error " + ex.getMessage());
//            }
//        };
//        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(1));
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MINUTES, new LinkedBlockingQueue<>(1), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        ListenableFutureTask<String> task = ListenableFutureTask.create(() -> {
            System.out.println("task create");
            throw new NullPointerException();
//            return  "我是任务返回信息";
        });
        FutureCallback<String> callback = new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                    System.out.println("有返回结果 thread name = " + Thread.currentThread().getName());
                    System.out.println(result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("onFailure = "+t.getMessage());
            }
        };
        threadPoolExecutor.submit(task);
        task.addListener(()->{
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println("addListener");
        },threadPoolExecutor);
        addCallback(task, callback,threadPoolExecutor);
        threadPoolExecutor.shutdown();
    }
}
