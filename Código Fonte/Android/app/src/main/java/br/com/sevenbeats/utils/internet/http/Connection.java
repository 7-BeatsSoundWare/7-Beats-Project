package br.com.sevenbeats.utils.internet.http;


import android.content.ContentValues;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class Connection {

    public Object getStreamContent(String url, ContentValues params){
        if (hasParams(params)) {
            //mount url with params @ContentValues
        }

        Request request = new Request.Builder().url(url).build();
        Response response;
        Object result;

        try {
            OkHttpClient client = new OkHttpClient();
            response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            result = new ErrorResponse(CONNECTION_ERROR, errorMessage);
        }

        return result;
    }

    private static boolean hasParams(ContentValues values){
        return values != null;
    }

    private  String errorMessage = "";
    private static final int CONNECTION_ERROR = 1001;
}

