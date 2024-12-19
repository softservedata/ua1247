package com.softserve.edu18;

public class GreencityEcoNewsCommentPage {
    private int id;
    private String createdDate;
    private String modifiedDate;
    private GreencityEcoNewsCommentAuthor author;
    private String text;


    public GreencityEcoNewsCommentPage(int id, String createdDate, String modifiedDate,
                                       GreencityEcoNewsCommentAuthor author, String text) {
        this.id = id;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.author = author;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getText() {
        return text;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public GreencityEcoNewsCommentAuthor getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "\n\t\t\tGreencityEcoNewsCommentPage{" +
                "\n\t\t\t\tid=" + id +
                "\n\t\t\t\tcreatedDate='" + createdDate + '\'' +
                "\n\t\t\t\tmodifiedDate='" + modifiedDate + '\'' +
                "\n\t\t\t\ttext='" + text + '\'' +
                "\n\t\t\t\t\tauthor=" + author +
                "\n\t\t\t}";
    }
}