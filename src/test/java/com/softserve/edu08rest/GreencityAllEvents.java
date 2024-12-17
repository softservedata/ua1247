package com.softserve.edu08rest;

import java.util.List;

public class GreencityAllEvents {
    private List<GreencityEventPage> page;
    private int totalElements;
    private int currentPage;
    private int totalPages;
    private int number;
    private boolean hasPrevious;
    private boolean hasNext;
    private boolean first;
    private boolean last;

    public GreencityAllEvents(List<GreencityEventPage> page, int totalElements, int currentPage, int totalPages, int number, boolean hasPrevious, boolean hasNext, boolean first, boolean last) {
        this.page = page;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.number = number;
        this.hasPrevious = hasPrevious;
        this.hasNext = hasNext;
        this.first = first;
        this.last = last;
    }

    public List<GreencityEventPage> getPage() {
        return page;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getNumber() {
        return number;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isLast() {
        return last;
    }

    @Override
    public String toString() {
        return "\n\tGreencityAllEvents{" +
                "\n\t\tpage=" + page +
                "\n\t\ttotalElements=" + totalElements +
                "\n\t\tcurrentPage=" + currentPage +
                "\n\t\ttotalPages=" + totalPages +
                "\n\t\tnumber=" + number +
                "\n\t\thasPrevious=" + hasPrevious +
                "\n\t\thasNext=" + hasNext +
                "\n\t\tfirst=" + first +
                "\n\t\tlast=" + last +
                "\n\t}";
    }
}
