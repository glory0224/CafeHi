package com.cafeHi.www.common.page;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchCriteria extends Criteria{

    private String searchType ="";
    private String keyword = "";

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


    @Override
    public String toString() {
        return "SearchCriteria [searchType=" + searchType + ", keyword=" + keyword + "]";
    }

    public String getQueryString(Long qnaNum, int page, int perPageNum, String searchType, String keyword) {
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

        try {
            sb.append(URLEncoder.encode(keyword, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String getQueryString(int page, int perPageNum, String searchType, String keyword) {
        StringBuilder sb = new StringBuilder();

        sb.append("?page=");
        sb.append(page);
        sb.append("&perPageNum=");
        sb.append(perPageNum);
        sb.append("&searchType=");
        sb.append(searchType);
        sb.append("&keyword=");
        sb.append(keyword);

        try {
            sb.append(URLEncoder.encode(keyword, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }




}