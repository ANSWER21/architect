package com.topstack.hiconcurrent.concurrent;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * @author Administrator
 * AsyncTask 生命周期与宿主不同步，有可能发生内存泄露，默认情况下所有任务串行执行
 * HandlerThread 适用于主线程需要和子线程通信的场景，应用于持续性任务，比如轮询
 */
public class ConcurrentTest {

    private static final String TAG = "ConcurrentTest";
    private static final int MSG_WHT_1 = 1;
    private static volatile boolean hasNotify = false;

    public static void concurrentTest() throws InterruptedException {
        //asyncTaskTest();
        //asyncTaskTest1();
        //handlerThreadTest();
        //waitAndNotifyTest();
        //joinTest();
        //sleepTest();
        threadCommunication();
    }

    private static void asyncTaskTest() {
        //适用于需要知道任务执行进度并且更新UI的场景
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("execute myasynctask");
    }

    private static void asyncTaskTest1() {
        for (int i = 0; i < 10; i++) {
            //串行执行，其中一条任务休眠或阻塞，后面则不能执行
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "run: AsyncTask execute" + System.currentTimeMillis());
                }
            });
        }
    }

    private static void asyncTaskTest2() {
        //线程池的方式使线程异步
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run:THREAD_POOL_EXECUTOR AsyncTask execute");
            }
        });
    }

    private static void handlerThreadTest() {
        /**
         * 新建一个名为“handle-thread”的线程
         * 将此线程开启
         * 将此线程的looper绑定到myHandler上，此线程中所接受到的消息将被轮询出并交由MyHandler执行
         */
        HandlerThread handlerThread = new HandlerThread("handle-thread");
        handlerThread.start();
        MyHandler myHandler = new MyHandler(handlerThread.getLooper());
        myHandler.sendEmptyMessage(MSG_WHT_1);
    }

    private static void waitAndNotifyTest() {
        /**
         * notify 适用于一个线程需要等待另一个线程执行结果或者部分结果
         * 需要保证wait、notify的执行顺序
         */
        final Object object = new Object();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run:thread1 start");
                synchronized (object) {
                    try {
                        if (!hasNotify) {
                            object.wait(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.e(TAG, "run:thread1 end");
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run:thread2 start");
                synchronized (object) {
                    object.notify();
                    hasNotify = true;
                }
                Log.e(TAG, "run:thread2 end");
            }
        });

        thread1.start();
        thread2.start();
    }

    private static void joinTest() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run1:" + System.currentTimeMillis());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "run2:" + System.currentTimeMillis());
            }
        });

        thread.start();
        thread.join();
        //使用join方法主线程的此条log将在thread执行完后才会去执行
        //join方法相当于向当前进程(主线程)插入一条方法
        Log.e(TAG, "test3:" + System.currentTimeMillis());
    }

    private static void yieldTest() {
        /**
         * 暂停当前正在执行的线程对象，不会释放资源锁，使同优先级或优先级更高的线程有执行机会
         */
    }

    private static void sleepTest() {
        /**
         * 使调用线程进入休眠状态，但在一个synchronized块中执行sleep，线程虽然会休眠，但是不会释放资源对象
         */
        final Object object = new Object();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run:thread1 start");
                synchronized (object) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.e(TAG, "run:thread1 end");
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    Log.e(TAG, "run:thread2 start");
                    Log.e(TAG, "run:thread2 end");
                }
            }
        });

        thread1.start();
        thread2.start();
    }

    private static void threadCommunication() {
        LooperThread looperThread = new LooperThread("looper-thread");
        looperThread.start();
        Handler handler = new Handler(looperThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.e(TAG, "handleMessage:" + msg.what);
                Log.e(TAG, "handleMessage:" + Thread.currentThread().getName());
            }
        };
        //主线程向子线程通信，将 MSG_WHT_1 传递过去
        handler.sendEmptyMessage(MSG_WHT_1);
    }


    static class MyAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            for (int i = 0; i < 10; i++) {
                publishProgress(i * 10);
            }
            return params[0];
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.e(TAG, "onProgressUpdate:" + values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(TAG, "onPostExecute:" + result);
        }
    }

    static class MyHandler extends Handler {

        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.e(TAG, "handleMessage" + msg.what);
            Log.e(TAG, "handleMessage" + Thread.currentThread().getName());
        }
    }

    static class LooperThread extends Thread {
        private Looper looper;

        public LooperThread(String name) {
            super(name);
        }

        public Looper getLooper() {
            synchronized (this) {
                if (looper == null && isAlive()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return looper;
        }

        @Override
        public void run() {
            Looper.prepare();
            synchronized (this) {
                looper = Looper.myLooper();
                notify();
            }
            Looper.loop();
        }
    }

}
