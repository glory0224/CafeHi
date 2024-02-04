package com.cafeHi.www.qna.repository;

import com.cafeHi.www.common.page.SearchCriteria;
import com.cafeHi.www.qna.entity.QnA;

import java.util.List;

public interface QnADsl {
    List<QnA> findPagingList(int limit, int offset, SearchCriteria searchCriteria);

    List<QnA> findQnAListByMemberCode(int limit, int offset, SearchCriteria searchCriteria, Long MemberCode);

    int getPagingCount(SearchCriteria searchCriteria);
}
