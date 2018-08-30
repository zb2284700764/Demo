package com.demo.thread;

/**
 * 多线程 模拟下载文件
 */
public class DownloadThreadApp {

    public static void main(String[] args) {

        int[] arr = {1, 3, 4, 5, 5, 6, 10, 3, 4, 2, 1, 2, 3, 2, 4, 5};

        Thread download;
        for (int anArr : arr) {
            download = new Thread(new DownloadThread(anArr));
            download.start();
        }
    }


    static class DownloadThread implements Runnable {
        private final int time;

        DownloadThread(int time) {
            this.time = time;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(time * 1000);
            } catch (InterruptedException e) {
            }

            System.out.printf("模拟下载完成，线程[%s], 状态[%s], 用时[%s]秒 %n", Thread.currentThread().getName(), Thread.currentThread().getState(), time);
        }
    }

}
