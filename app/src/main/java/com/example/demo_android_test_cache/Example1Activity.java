package com.example.demo_android_test_cache;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Anson on 2016/5/24.
 */
public class Example1Activity extends AppCompatActivity {
    private final static String ENDPOINT = "http://demo-test-cache.azurewebsites.net/";
    private final static int CACHE_SIZE = 10 * 1024 * 1024; // 10 MiB
    private OkHttpClient okHttpClient;
    private Cache cache;

    @Bind(R.id.resultTextView) TextView resultTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        ButterKnife.bind(this);
        initOkHttpClient();
    }

    private void initOkHttpClient() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        cache = new Cache(Example1Activity.this.getExternalCacheDir(), CACHE_SIZE);
        okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(logInterceptor)
                .build();
    }


    @OnClick(R.id.fetchUserButton)
    public void onClickFetchUserButton() {
        final ProgressDialog dialog = new ProgressDialog(this);
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                dialog.show();
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... params) {
                Request request = new Request.Builder()
                        .get()
                        .url(ENDPOINT + "api/UserOnlyMaxAge")
                        .build();

                String log = "";
                //Request headers
                for (String name : request.headers().names()) {
                    log += "Request : " + name + " → " + request.header(name) + "\n";
                }

                try {
                    Response response = okHttpClient.newCall(request).execute();
                    //Response headers
                    log += "\n\n";
                    log += "Response Status : " + response.code() + " → " + response.message() + "\n";
                    for (String name : response.headers().names()) {
                        log += name + " → " + response.header(name) + "\n";
                    }
                    log += "\n\n\n";
                    //Response body
                    log += "Response body : " + response.body().string();
                } catch (IOException e) {
                    log += e.toString();
                    e.printStackTrace();
                }
                return log;
            }

            @Override
            protected void onPostExecute(String log) {
                resultTextView.setText(log);
                dialog.dismiss();
                super.onPostExecute(log);
            }
        };
        task.execute();
    }

    @OnClick(R.id.clearCacheButton)
    public void onClickClearCacheButton() {
        try {
            cache.evictAll();
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}