package com.example.myproject;


import androidx.annotation.NonNull;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;


public class loginapi {

    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public interface LoginCallback {
        void onResult(boolean success, String response);
    }

    public static void login(String email, String password, LoginCallback callback) {
        String url = constantspage.API_URL + "/api/v1/auth/login";

        String json = "{"
                + "\"email\":\"" + email + "\","
                + "\"password\":\"" + password + "\""
                + "}";

        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                callback.onResult(false, e.getMessage()); // ❌ API failed
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseBody = response.body().string();

                if (response.isSuccessful()) {
                    callback.onResult(true, responseBody); // ✅ success
                    System.out.println("Success: " + responseBody);
                } else {
                    callback.onResult(false, responseBody); // ❌ failed
                    System.out.println("Failed: " + response.code());
                }
            }
        });
    }

    public static void getAllCars(LoginCallback callback) {
        String url = constantspage.API_URL + "/api/v1/cars/search";

//                if(!name.isEmpty()){
//                  url =  constantspage.API_URL + "/api/v1/cars/search";
//                }else {
//                    url =   constantspage.API_URL + "/api/v1/cars/search?carName=" + name;
//                }





        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                callback.onResult(false, e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseBody = response.body().string();
                if (response.isSuccessful()) {
                   callback.onResult(true, responseBody);
                } else {
                   callback.onResult(false, responseBody);
                }
            }
        });
    }

    // Utility method to run on Main thread
//    private static void runOnMain(Runnable runnable) {
//        new Handler(Looper.getMainLooper()).post(runnable);
//    }

}
