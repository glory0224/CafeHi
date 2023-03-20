package com.cafeHi.www.mapper.board;

import java.util.List;
import java.util.Map;

import com.cafeHi.www.common.page.SearchCriteria;
import org.apache.ibatis.annotations.Mapper;

import com.cafeHi.www.board.dto.QnA;
import com.cafeHi.www.common.page.Criteria;

@Mapper
public interface QnaMapper {
	
	// QnA 게시글 CRUD
	public int writeQnA(QnA qna);
	public List<QnA> getQnAList(SearchCriteria scri);
	public int modifyQnA(QnA qna);
	public void modifyHit(QnA qna);
	public int deleteQnA(QnA qna);
	public int getQnASearchNum(SearchCriteria scri);
	public QnA getQnA(QnA qna);
	
	// 멤버 QnA 게시글 조회
	public int getMyQnANum(Map<String , Object> myPageQnAMap);
	public List<QnA> getMyQnAList(Map<String , Object> myPageQnAMap);
	
	
}
