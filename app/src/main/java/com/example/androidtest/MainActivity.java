package com.example.androidtest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.androidtest.thread.BackgroundThread;
import com.example.androidtest.thread.Playlist;


public class MainActivity extends AppCompatActivity implements AsyncFragment.MyTaskHandler {

    private static final String TAG = "MyResult";
    private static final String FRAGMENT_TAG = "FragmentTag";

    private ScrollView mScroll;
    private TextView mLog;
    private ProgressBar mProgressBar;

private AsyncFragment asyncFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();


        FragmentManager manager = getSupportFragmentManager();
        asyncFragment = (AsyncFragment) manager.findFragmentByTag(FRAGMENT_TAG);
        if(asyncFragment == null){
            asyncFragment = new AsyncFragment();
            manager.beginTransaction().add(asyncFragment, FRAGMENT_TAG).commit();
        }

    }



    public void runCode(View v) {

        asyncFragment.runTask("Red", "Green", "Blue", "Yello");

    };



    private void initViews() {
        mScroll = (ScrollView) findViewById(R.id.scrollLog);
        mLog = (TextView) findViewById(R.id.tvLog);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    public void clearOutput(View v) {
        mLog.setText("");
        scrollTextToEnd();
    }

    public void log(String message) {
        Log.i(TAG, message);
        mLog.append(message + "\n");
        scrollTextToEnd();
    }

    private void scrollTextToEnd() {
        mScroll.post(new Runnable() {
            @Override
            public void run() {
                mScroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public void displayProgressBar(boolean display) {
        if (display) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void handleTask(String message) {
        log(message);
    }




}