package com.example.androidtest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = "MyResult";
    private static final String FRAGMENT_TAG = "FragmentTag";
    private static final String KEY = "Key";

    private ScrollView mScroll;
    private TextView mLog;
    private ProgressBar mProgressBar;

    private ExecutorService mExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mExecutor = Executors.newFixedThreadPool(5);
    }

    public void runCode(View v) {


        Bundle bundle = new Bundle();
        bundle.putString(KEY, "Some Data") ;

        //LoaderManager.getInstance(this).initLoader(100, bundle, this).forceLoad(); // fetch result from cache
        // . reloads exisiting loader that is available against ID
        LoaderManager.getInstance(this).restartLoader(100, bundle, this).forceLoad(); //Reloads results and
        // calls loadInBackground() method

    }



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

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        List<String> songList = Arrays.asList(PlayList.songs);
        return new MyTaskLoader(this, args, songList);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        log(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }


    private static class MyTaskLoader extends AsyncTaskLoader<String>{

        private List<String> mSongList;
        private Bundle args;
        public MyTaskLoader(@NonNull Context context, Bundle args, List<String> songList) {
            super(context);
            this.args = args;
            this.mSongList = songList;

        }

        @Nullable
        @Override
        public String loadInBackground() {

            String data = args.getString(KEY);
            Log.d(TAG, data);

            
            Log.d(TAG, "loadInBackground: "+Thread.currentThread().getName());
            for (String song:mSongList) {
                Log.d(TAG, "Name: "+song);
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d(TAG, "Termined: ");
            return "Result From Loader";
        }


        @Override
        public void deliverResult(@Nullable String data) {
            data += ": Modified ";
            super.deliverResult(data);

        }
    }


}