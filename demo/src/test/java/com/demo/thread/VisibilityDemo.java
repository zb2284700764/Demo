package com.demo.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程可见性 demo
 */
public class VisibilityDemo {

    public  int a = 0;

    public void increment(){
            a++;
            System.out.println(a);
    }

    public static void main(String[] args) {



        final VisibilityDemo visibilityDemo = new VisibilityDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        visibilityDemo.increment();
                    }
                }
            }.start();
       }



//        TimeCousumingTask timeCousumingTask = new TimeCousumingTask();
//        Thread thread = new Thread(timeCousumingTask);
//        thread.setName("线程1");
//        thread.start();
//        Thread thread1 = new Thread(timeCousumingTask);
//        thread1.setName("线程--2");
//        thread1.start();
//
//        // 指定时间内任务没有完成，就将其取消
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        timeCousumingTask.cancel();
//
//        System.out.println("main 方法执行完成");

    }

}

//class TimeCousumingTask implements Runnable {
//
//    private boolean toCancel = false;
//
//    @Override
//    public void run() {
//        while (!toCancel) {
//            if (doExecute()) {
//                break;
//            }
//        }
//        if (toCancel) {
//            System.out.println(Thread.currentThread().getName() + " <-> Task was canceled.");
//        } else {
//            System.out.println(Thread.currentThread().getName() + " <-> Task done!");
//        }
//    }
//
//    private boolean doExecute() {
//        boolean isDone = false;
//        System.out.println(Thread.currentThread().getName() + " <-> Executeint .... toCancel - " + toCancel);
//
//        // 模拟现实操作耗时
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        return isDone;
//    }
//
//    public void cancel() {
//        toCancel = true;
//        System.out.println(Thread.currentThread().getName() + " <-> canceled - " + toCancel);
//    }
//}


