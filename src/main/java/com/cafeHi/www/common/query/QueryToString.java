package com.cafeHi.www.common.query;

import org.springframework.stereotype.Component;

@Component
public class QueryToString {

    public String getQueryString(Long qnaNum) {
        StringBuffer sb = new StringBuffer();
        sb.append("?qnaNum=");
        sb.append(qnaNum);


        return sb.toString();
    }
}
