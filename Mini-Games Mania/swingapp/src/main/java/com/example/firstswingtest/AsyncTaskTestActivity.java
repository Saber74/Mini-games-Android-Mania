package com.example.firstswingtest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

public class AsyncTaskTestActivity extends Activity {
    public ClientIP x;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        x= new ClientIP();
        super.onCreate(savedInstanceState);

        //Starting the task. Pass an url as the parameter.
        new PostTask().execute("http://feeds.pcworld.com/pcworld/latestnews");
    }

    // The definition of our task class
    private class PostTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return "All Done!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}