package com.softserve.utils;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class GreencityPost {

    public LoginDto login(String email, String password) {
        PropertiesUtil propertiesUtil = new PropertiesUtil();
        String captchaToken = propertiesUtil.readCaptchaToken();
        OwnSignInDto ownSignInDto = new OwnSignInDto(email, password, captchaToken);
        LoginDto LoginDto = null;
        try {
            LoginDto = login(ownSignInDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return LoginDto;
    }

    public LoginDto login(OwnSignInDto ownSignInDto) throws IOException {
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody;
        Request request;
        Response response;
        String resultJson;
        LoginDto loginDto;
        //
        // Login
        String jsonBody =  new StringBuilder()
                .append("{")
                .append("\"email\":\"" + ownSignInDto.getEmail() + "\",")
                .append("\"password\":\"" + ownSignInDto.getPassword() + "\",")
                .append("\"captchaToken\":\"" + ownSignInDto.getCaptchaToken() + "\"")
                .append("}").toString();
        requestBody = RequestBody.create(jsonBody,
                MediaType.parse("application/json; charset=utf-8"));
        request = new Request.Builder()
                .url("https://greencity-user.greencity.cx.ua/ownSecurity/signIn")
                //.addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build();
        response = client.newCall(request).execute();
        resultJson = response.body().string();
        System.out.println("resultJson = " + resultJson);
        // check
        if (resultJson.contains("accessToken")) {
            loginDto = gson.fromJson(resultJson, LoginDto.class);
        } else {
            throw new RuntimeException(resultJson);
        }
        return loginDto;
    }
}
