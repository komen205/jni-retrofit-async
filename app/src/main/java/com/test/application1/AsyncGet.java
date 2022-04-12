package com.test.application1;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AsyncGet extends AsyncTask<Object, Void, Void> {

    static {
        System.loadLibrary("application1");
    }

    public native String siteFromJNI();

    @Override
    protected Void doInBackground(Object... params) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(siteFromJNI())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderService service = retrofit.create(jsonPlaceHolderService.class);

        Call<List<Post>> posts = service.getPosts();

        posts.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()) {
                    return;
                }

                MainActivity mainActivity = (MainActivity) params[0];
                ListView listview3 = (ListView) mainActivity.findViewById(R.id.listview3);
                ArrayList<String> myStringArray1 = new ArrayList<String>();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mainActivity, R.layout.list_view, myStringArray1);
                List<Post> posts = response.body();

                for(Post post : posts)
                {

                    String content = "";
                    content += post.getId();
                    content += post.getText();

                    String finalContent = content;

                    new Handler(Looper.getMainLooper()).post(new Runnable(){
                        @Override
                        public void run() {

                            myStringArray1.add(finalContent);
                            listview3.setAdapter(adapter);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });

        return null;
    }

}
