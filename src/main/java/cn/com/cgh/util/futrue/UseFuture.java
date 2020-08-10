package cn.com.cgh.util.futrue;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class UseFuture {
    static class MyFuture implements Callable<Integer>{
        @Override
        public Integer call() throws Exception {
            int num = 0;
            try {
                Thread.sleep(1000);
                for (int i = 0;i <= 200 ; i++){
                    if (Thread.currentThread().isInterrupted()){
                        return null;
                    }
                    num += i;
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return num;
        }
    }

    public static void main(String[] args) {
        MyFuture myFuture = new MyFuture();
        FutureTask<Integer> task = new FutureTask<Integer>(myFuture);
        new Thread(task).start();

        try {
            Thread.sleep(100);
            Random r = new Random();
            int i = r.nextInt(100);
            System.out.println(i);
            if (i > 50){
                System.out.println("get MyFuture value = " + task.get());
            }else {
                System.out.println("cancel .... ");
                task.cancel(true);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println();

    }
}
