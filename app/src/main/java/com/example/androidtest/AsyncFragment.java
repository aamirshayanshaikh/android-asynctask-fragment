package com.example.androidtest;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AsyncFragment extends Fragment {
    private static final String TAG = "MyResult";

    private MyTaskHandler myTaskHandler;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    public interface MyTaskHandler{
        void handleTask(String message);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: Fragment");
        if (context instanceof MyTaskHandler){
            myTaskHandler = (MyTaskHandler) (context);
        }
    }


    public void runTask(String...params){
        AsyncDownload myTask = new AsyncDownload();
        myTask.execute(params);

    }

    class AsyncDownload extends AsyncTask<String, String, String> {




        @Override
        protected String doInBackground(String... strings) {

            for(String val : strings){
                publishProgress(val);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(isCancelled()){
                    publishProgress("Task Cancelled");
                    break;
                }
            }




            return null;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            myTaskHandler.handleTask(values[0]);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }
    }
}
