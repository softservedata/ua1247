package com.softserve.edu18;

import okhttp3.*;

import java.io.IOException;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GET200AllCommentsEcoNews {
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    @Test
    public void checkGET200() {
        try {
            HttpUrl.Builder urlBuilder = HttpUrl
                    .parse("https://greencity.greencity.cx.ua/eco-news/1916/comments/active")
                    .newBuilder();
            urlBuilder.addQueryParameter("page", "0");
            urlBuilder.addQueryParameter("size", "5");
            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Accept", "*/*")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.err.println("Request failed with code: " + response.code());
                    return;
                }

                Assertions.assertTrue(response.isSuccessful());
                Assertions.assertEquals(response.code(), 200);

                String json = response.body() != null ? response.body().string() : "";
                GreencityAllEcoNewsComments greencityAllComments =
                        gson.fromJson(json, GreencityAllEcoNewsComments.class);

                System.out.println("resultJson: " + json);
                System.out.println("greencityAllEvents: " + greencityAllComments);
            }
        } catch (IOException e) {
            System.err.println("Error during API call: " + e.getMessage());
        }
    }
}