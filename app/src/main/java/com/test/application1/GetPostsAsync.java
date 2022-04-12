package com.test.application1;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetPostsAsync extends AsyncTask<Object, List<Post>, Void> {

    static {
        System.loadLibrary("application1");
    }

    public native String getSiteFromJNI();

    ArrayAdapter<String> arrayAdapter;

    public GetPostsAsync(ArrayAdapter<String> arrayAdapter) {
        this.arrayAdapter = arrayAdapter;
    }


    @Override
    protected Void doInBackground(Object... params) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getSiteFromJNI())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderService service = retrofit.create(jsonPlaceHolderService.class);

        Call<List<Post>> posts = service.getPosts();

        posts.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    arrayAdapter.add("Response not successful, please restart the app.");
                    return;
                }

                List<Post> posts = response.body();
                publishProgress(posts);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                arrayAdapter.add("Failure, please restart the app.");
            }

        });
        
        Log.i("Check logs for async", "Should finish first");
        return null;
    }

    @Override
    protected void onProgressUpdate(List<Post>... listPosts) {
        Log.i("Check logs for async", "Should finish in second");
        List<Post> posts = Arrays.stream(listPosts).iterator().next();

        for (Post post1 : posts) {
            String content = "";
            content += "ID: " + post1.getId() + "\n";
            content += "Body: " + post1.getText();

            arrayAdapter.add(content);
        }

        super.onProgressUpdate(listPosts);
    }
}
