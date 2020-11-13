package cn.com.cgh.util.thread.callback;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFutureTask;

import javax.annotation.Nullable;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class FutureCallBackShut {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1, 1, 0,
                TimeUnit.MINUTES, new LinkedBlockingQueue<>(1),
                Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        AtomicBoolean aBoolean = new AtomicBoolean(false);
        ListenableFutureTask<String> futureTask = ListenableFutureTask.create(() -> {
            while (!aBoolean.get()){
                aBoolean.set(Thread.currentThread().isInterrupted());
                if (aBoolean.get()){
                    throw new Exception("被终止的");
                }
            }
            return "success";
        });

        threadPoolExecutor.submit(futureTask);

        Futures.addCallback(futureTask, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                System.out.println("成功 " + result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());
                t.printStackTrace();
            }
        },threadPoolExecutor);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        futureTask.cancel(true);
        threadPoolExecutor.shutdown();


    }
}
