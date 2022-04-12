package com.test.application1;

import com.google.gson.annotations.SerializedName;

public class Post {

    private String id;

    @SerializedName("body")
    String text;

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

}
