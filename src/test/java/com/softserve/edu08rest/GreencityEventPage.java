package com.softserve.edu08rest;

public class GreencityEventPage {
    private int id;
    private String title;
    private GreencityEventOrganazer organizer;

    public GreencityEventPage(int id, String title, GreencityEventOrganazer organizer) {
        this.id = id;
        this.title = title;
        this.organizer = organizer;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public GreencityEventOrganazer getOrganizer() {
        return organizer;
    }

    @Override
    public String toString() {
        return "\n\t\t\tGreencityEventPage{" +
                "\n\t\t\t\tid=" + id +
                "\n\t\t\t\ttitle='" + title +
                "\n\t\t\t\torganizer=" + organizer +
                "\n\t\t\t}";
    }
}
