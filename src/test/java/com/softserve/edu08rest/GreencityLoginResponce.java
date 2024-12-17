package com.softserve.edu08rest;

public class GreencityLoginResponce {
    private int userId;
    private String accessToken;
    private String refreshToken;
    private String name;
    private boolean ownRegistrations;

    public GreencityLoginResponce(int userId, String accessToken, String refreshToken, String name, boolean ownRegistrations) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.name = name;
        this.ownRegistrations = ownRegistrations;
    }

    public int getUserId() {
        return userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getName() {
        return name;
    }

    public boolean isOwnRegistrations() {
        return ownRegistrations;
    }

    @Override
    public String toString() {
        return "\n\tGreencityLoginResponce{" +
                "\n\t\tuserId=" + userId +
                "\n\t\taccessToken='" + accessToken + '\'' +
                "\n\t\trefreshToken='" + refreshToken + '\'' +
                "\n\t\tname='" + name + '\'' +
                "\n\t\townRegistrations=" + ownRegistrations +
                "\n\t}";
    }
}
