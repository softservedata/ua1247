package com.softserve.edu18;

public class GreencityEcoNewsCommentAuthor {
    private int id;
    private String name;


    public GreencityEcoNewsCommentAuthor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "\n\t\t\t\t\t\tGreencityEcoNewsCommentAuthor{" +
                "\n\t\t\t\t\t\t\t\tid=" + id +
                "\n\t\t\t\t\t\t\t\tname='" + name + '\'' +
                "\n\t\t\t\t\t\t}";
    }
}