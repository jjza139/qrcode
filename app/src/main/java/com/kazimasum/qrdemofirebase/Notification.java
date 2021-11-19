package com.kazimasum.qrdemofirebase;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Notification {
    private final OkHttpClient client = new OkHttpClient();


    Notification(){}


    void send_notification(String title,String body,String token) {
        //[code request api ]
        // [Code create json and body]
        final String Severkey="AAAACBk1YbM:APA91bFmKDmkdzoHk6IDGLn-d1w4RzEvbBEoqD5w4BhYK2PCnb5__fgwmd3a9hnhfscrSwR_ytmQOHZp0-_WhJzb_SdTE3GEi6YPnhWB0rfmAU6vVXFBligdm3htlhokbGcSLEYxJJc5";
        final String key = "key=" + Severkey;
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject main = new JSONObject();
        JSONObject noti = new JSONObject();
        try {
            main.put("to", token);
            main.put("notification", noti);
            noti.put("title",title);
            noti.put("body",body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody Body = RequestBody.create(JSON, main.toString());
        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .post(Body)
                .header("Content-Type", "application/json")
                .addHeader("Authorization", key)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) { }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                a=response.body().string();
//                try {
//                    JSONObject json = new JSONObject(a);
//                    JSONObject data = new JSONObject(json.getString("data"));
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

//                result.setText(a);


            }
        });

    }
}
