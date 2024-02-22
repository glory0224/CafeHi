package com.cafeHi.www.common.page;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;

public class SearchCriteria extends Criteria{

    private String searchType ="";
    private String keyword = "";
    private String searchStartDate = "";
    private String searchEndDate = "";
    private LocalDate StartDate = LocalDate.now();
    private LocalDate EndDate = LocalDate.now();



    public String getSearchType() {
        return searchType;
    }
    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSearchStartDate() {
        return searchStartDate;
    }

    public void setSearchStartDate(String searchStartDate) {
        this.searchStartDate = searchStartDate;
    }

    public String getSearchEndDate() {
        return searchEndDate;
    }

    public void setSearchEndDate(String searchEndDate) {
        this.searchEndDate = searchEndDate;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public void setStartDate(LocalDate startDate) {
        StartDate = startDate;
    }

    public LocalDate getEndDate() {
        return EndDate;
    }

    public void setEndDate(LocalDate endDate) {
        EndDate = endDate;
    }

    @Override
    public String toString() {
        return "SearchCriteria [searchType=" + searchType + ", keyword=" + keyword + ", searchStartDate=" + searchStartDate + ", searchEndDate=" + searchEndDate + "]";
    }

    public String getQueryString(Long qnaNum, int page, int perPageNum, String searchType, String keyword, String searchStartDate, String searchEndDate) {
        StringBuilder sb = new StringBuilder();
        sb.append("?qnaNum=");
        sb.append(qnaNum);
        sb.append("&page=");
        sb.append(page);
        sb.append("&perPageNum=");
        sb.append(perPageNum);
        sb.append("&searchType=");
        sb.append(searchType);
        sb.append("&keyword=");
        sb.append(keyword);
        sb.append("&searchStartDate=");
        sb.append(searchStartDate);
        sb.append("&searchStartDate=");
        sb.append(searchEndDate);

        try {
            sb.append(URLEncoder.encode(keyword, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String getQueryString(int page, int perPageNum, String searchType, String keyword, String searchStartDate, String searchEndDate) {
        StringBuilder sb = new StringBuilder();

        sb.append("?page=");
        sb.append(page);
        sb.append("&perPageNum=");
        sb.append(perPageNum);
        sb.append("&searchType=");
        sb.append(searchType);
        sb.append("&keyword=");
        sb.append(keyword);
        sb.append("&searchStartDate=");
        sb.append(searchStartDate);
        sb.append("&searchStartDate=");
        sb.append(searchEndDate);

        try {
            sb.append(URLEncoder.encode(keyword, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}