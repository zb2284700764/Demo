package com.demo.thread;

public class ThreadCreationCmp {


    public static void main(String[] args) {
        Thread t;
        CountingTask countingTask = new CountingTask();

        // 获取处理器个数
        final int numberOfProceesors = Runtime.getRuntime().availableProcessors();
        System.out.println("处理器个数:" + numberOfProceesors);

        for (int i = 0; i < 2 * numberOfProceesors; i++) {
            // 直接创建线程
            t = new Thread(countingTask);
            t.start();
        }

        for (int i = 0; i < 2 * numberOfProceesors; i++) {
            // 通过子类方式创建线程
            t = new CountingThread();
            t.start();
        }

    }

    static class Counter {
        private int count = 0;

        public void increment() {
            count++;
        }

        public int value() {
            return count;
        }

    }

    static class CountingTask implements Runnable {
        private Counter counter = new Counter();

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                doSomething();
                counter.increment();
            }
            System.out.println("CountingTask:"+counter.value());
        }

        private void doSomething(){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    static class CountingThread extends Thread {
        private Counter counter = new Counter();

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                doSomething();
                counter.increment();
            }
            System.out.println("CountingThread:"+counter.value());
        }

        private void doSomething(){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
