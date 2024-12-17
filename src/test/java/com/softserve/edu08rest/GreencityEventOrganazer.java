package com.softserve.edu08rest;

public class GreencityEventOrganazer {
    private int id;
    private String name;
    private String organizerRating;
    private String email;

    public GreencityEventOrganazer(int id, String name, String organizerRating, String email) {
        this.id = id;
        this.name = name;
        this.organizerRating = organizerRating;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOrganizerRating() {
        return organizerRating;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "\n\t\t\t\tGreencityEventOrganazer{" +
                "\n\t\t\t\t\tid=" + id +
                "\n\t\t\t\t\tname='" + name + '\'' +
                "\n\t\t\t\t\torganizerRating='" + organizerRating + '\'' +
                "\n\t\t\t\t\temail='" + email + '\'' +
                "\n\t\t\t\t}";
    }
}
