package com.softserve.edu11;



import java.util.Objects;

public class TesterUser1 {
    private String email;
    private String password;
    private String username;
    private String url;

    public TesterUser1(String email, String password, String username, String url) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return url;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TesterUser1 that = (TesterUser1) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(username, that.username) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, username, url);
    }

    @Override
    public String toString() {
        return "TesterUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
