package com.blog.rest.dto;

import java.util.List;

public class PaginationDetails {
    private List<PostDTO> content;
    private int pageNumber;
    private int pageLength;
    private long totalItems;
    private int totalPage;
    private boolean last;

    public PaginationDetails() {
    }

    public PaginationDetails(List<PostDTO> content, int pageNumber, int pageLength, long totalItems, int totalPage, boolean last) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageLength = pageLength;
        this.totalItems = totalItems;
        this.totalPage = totalPage;
        this.last = last;
    }

    public List<PostDTO> getContent() {
        return content;
    }

    public void setContent(List<PostDTO> content) {
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageLength() {
        return pageLength;
    }

    public void setPageLength(int pageLength) {
        this.pageLength = pageLength;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}
