package com.example.schoolpartner.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by q on 2017/4/21.
 */
public class HttpUtil {
    public static String location = "192.168.0.105";
    public static void sendHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
