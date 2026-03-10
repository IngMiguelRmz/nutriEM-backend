package com.nutriem.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageResponse<T> {

    private List<T> content;
    private int     page;
    private int     size;
    private long    totalElements;
    private int     totalPages;
    private boolean last;
    private boolean first;

    public PageResponse() {}

    public static <T> PageResponse<T> from(Page<T> page) {
        PageResponse<T> r = new PageResponse<>();
        r.content       = page.getContent();
        r.page          = page.getNumber();
        r.size          = page.getSize();
        r.totalElements = page.getTotalElements();
        r.totalPages    = page.getTotalPages();
        r.last          = page.isLast();
        r.first         = page.isFirst();
        return r;
    }

    public List<T> getContent()             { return content; }
    public void setContent(List<T> v)       { this.content = v; }
    public int getPage()                    { return page; }
    public void setPage(int v)              { this.page = v; }
    public int getSize()                    { return size; }
    public void setSize(int v)              { this.size = v; }
    public long getTotalElements()          { return totalElements; }
    public void setTotalElements(long v)    { this.totalElements = v; }
    public int getTotalPages()              { return totalPages; }
    public void setTotalPages(int v)        { this.totalPages = v; }
    public boolean isLast()                 { return last; }
    public void setLast(boolean v)          { this.last = v; }
    public boolean isFirst()                { return first; }
    public void setFirst(boolean v)         { this.first = v; }
}
