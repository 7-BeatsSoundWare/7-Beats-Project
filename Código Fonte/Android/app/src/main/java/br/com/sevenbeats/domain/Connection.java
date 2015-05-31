package br.com.sevenbeats.domain;


import android.content.ContentValues;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class Connection {

    public static String getStreamContent(String url, ContentValues params){
        if (hasParams(params)) {
            //mount url with params @ContentValues
        }

        Request request = new Request.Builder().url(url).build();
        Response response;

        try {
            OkHttpClient client = new OkHttpClient();
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String addParams(String url, ContentValues values){
        return "url with parameters";
    }

    private static boolean hasParams(ContentValues values){
        return values != null;
    }
}

