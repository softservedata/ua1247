package com.softserve.edu18;

import java.util.List;

public class GreencityAllEcoNewsComments {
    private List<GreencityEcoNewsCommentPage> page;
    private int totalElements;
    private int currentPage;
    private int totalPages;


    public GreencityAllEcoNewsComments(List<GreencityEcoNewsCommentPage> page, int totalElements, int currentPage, int totalPages, int number, boolean hasPrevious, boolean hasNext, boolean first, boolean last) {
        this.page = page;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.totalPages = totalPages;

    }

    public List<GreencityEcoNewsCommentPage> getPage() {
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


    @Override
    public String toString() {
        return "\n\tGreencityAllEcoNewsComment{" +
                "\n\t\tpage=" + page +
                "\n\t\ttotalElements=" + totalElements +
                "\n\t\tcurrentPage=" + currentPage +
                "\n\t\ttotalPages=" + totalPages +
                "\n\t}";
    }
}