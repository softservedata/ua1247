package com.softserve.edu11_12_13.data;


public final class TesterUserRepository {

    private TesterUserRepository() {
    }

    public static TesterUser1 getValidUser() {

        return new TesterUser1("Roman.tsvyk.pb.2018@lpnu.ua", "090198_Ts",
                "Oleksandr", "https://greencity-user.greencity.cx.ua/api/testers/sign-in");
    }




}
