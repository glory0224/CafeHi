package com.cafeHi.www.common.page;

public class WithoutKeywordCriteria extends Criteria{
    private String searchType = "";

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    @Override
    public String toString() {
        return "SearchCriteria [searchType=" + searchType + "]";
    }

    public String getQueryString(Long qnaNum, int page, int perPageNum, String searchType) {
        StringBuilder sb = new StringBuilder();
        sb.append("?qnaNum=");
        sb.append(qnaNum);
        sb.append("&page=");
        sb.append(page);
        sb.append("&perPageNum=");
        sb.append(perPageNum);
        sb.append("&searchType=");
        sb.append(searchType);

        return sb.toString();
    }

    public String getQueryString(int page, int perPageNum, String searchType) {
        StringBuilder sb = new StringBuilder();

        sb.append("?page=");
        sb.append(page);
        sb.append("&perPageNum=");
        sb.append(perPageNum);
        sb.append("&searchType=");
        sb.append(searchType);

        return sb.toString();
    }
}
