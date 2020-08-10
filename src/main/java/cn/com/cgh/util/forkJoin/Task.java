package cn.com.cgh.util.forkJoin;

import java.util.concurrent.RecursiveTask;

/**
 * <p> 有返回值的 ForkJoin
 * @author Haidar
 * @date 2020/8/10 15:18
 **/
public class Task extends RecursiveTask<Integer> {
    private int[] arr;
    private int startIndex;
    private int endIndex;
    public Task(int[] arr, int startIndex, int endIndex) {
        this.arr = arr;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    protected Integer compute() {
        if (startIndex == endIndex){
            return arr[endIndex];
        }else
        if (endIndex - startIndex <= 1){
            return arr[startIndex] + arr[endIndex];
        }else {
            int item = (startIndex + endIndex) / 2;
            Task task1 = new Task(arr, startIndex, item);
            task1.fork();
            Task task2 = new Task(arr, item+1, endIndex);
            task2.fork();
            return task1.join() + task2.join();
        }
    }
}
