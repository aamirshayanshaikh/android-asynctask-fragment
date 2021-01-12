package com.example.androidtest;

import android.util.Log;

class MultipleTasks implements Runnable {

    private static final String TAG = "MyResult";
    private  int mNoOgThread;
    private  MainActivity mActivity;

    public MultipleTasks(int noOfThreads, MainActivity activity){
        mNoOgThread = noOfThreads;
        mActivity = activity;
    }

    @Override
    public void run() {

        Log.d(TAG, "run: "+ Thread.currentThread().getName()+" Start thread no "+mNoOgThread);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "run: "+ Thread.currentThread().getName()+" End thread no "+mNoOgThread);

    }
}
