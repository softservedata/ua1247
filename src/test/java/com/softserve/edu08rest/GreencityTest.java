package com.softserve.edu08rest;

import com.google.gson.Gson;
import okhttp3.*;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GreencityTest {
    private Gson gson;
    private OkHttpClient client;
    //
    private RequestBody requestBody;
    private Request request;
    private Response response;
    //
    private String json;
    private String token;

    @BeforeAll
    public void setup() {
        gson = new Gson();
        client = new OkHttpClient();
    }

    @AfterAll
    public void tear() {
        response.close();
    }

    @Test
    public void checkSigninTesters() throws Exception {
        GreencityLoginRequest greencityLoginRequest = new GreencityLoginRequest("tyv09754@zslsz.com",
                "Qwerty_1",
                "UD~3tDW<$K.rEk$IELFTVQwWU$-tN%IX~q>`NuMpxhUMb$D");
        json = gson.toJson(greencityLoginRequest);
        //
        // Login
        requestBody = RequestBody.create(json,
                MediaType.parse("application/json; charset=utf-8"));
        request = new Request.Builder()
                .url("https://greencity-user.greencity.cx.ua/api/testers/sign-in")
                //.addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build();
        response = client.newCall(request).execute();
        //
        // Check
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertEquals(response.code(), 200);
        //
        json = response.body().string();
        GreencityLoginResponce greencityLoginResponce = gson.fromJson(json, GreencityLoginResponce.class);
        token = greencityLoginResponce.getAccessToken();
        //
        System.out.println("resultJson = " + json);
        System.out.println("greencityLoginResponce = " + greencityLoginResponce);
        //
        // Check greencityLoginResponce
    }

    @Test
    public void checkEvents() throws Exception {
        // Get all Events
        HttpUrl.Builder urlBuilder = HttpUrl
                .parse("https://greencity.greencity.cx.ua/events")
                .newBuilder();
        urlBuilder.addQueryParameter("page", "0");
        urlBuilder.addQueryParameter("size", "5");
        String url = urlBuilder.build().toString();
        //
        request = new Request
                .Builder()
                .url(url)
                .addHeader("Accept", "*/*")
                //.addHeader("Authorization", "Bearer " + token)
                .get()
                .build();
        response = client.newCall(request).execute();
        //
        // Check
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertEquals(response.code(), 200);
        //
        json = response.body().string();
        GreencityAllEvents greencityAllEvents = gson.fromJson(json, GreencityAllEvents.class);
        //
        System.out.println("resultJson: " + json);
        System.out.println("greencityAllEvents: " + greencityAllEvents);
        //
        // Check greencityAllEvents
    }
}
