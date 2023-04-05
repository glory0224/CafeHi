package com.cafeHi.www.mapper.board;

import java.util.List;
import java.util.Map;

import com.cafeHi.www.common.page.SearchCriteria;
import org.apache.ibatis.annotations.Mapper;

import com.cafeHi.www.board.dto.QnADTO;
import com.cafeHi.www.common.page.Criteria;

@Mapper
public interface QnaMapper {
	
	// QnAEntity 게시글 CRUD
	int writeQnA(QnADTO qna);
	List<QnADTO> getQnAList(SearchCriteria scri);
	int modifyQnA(QnADTO qna);
	void modifyHit(QnADTO qna);
	int deleteQnA(QnADTO qna);
	int getQnASearchNum(SearchCriteria scri);
	QnADTO getQnA(QnADTO qna);
	
	// 멤버 QnAEntity 게시글 조회
	int getMyQnANum(Map<String , Object> myPageQnAMap);
	List<QnADTO> getMyQnAList(Map<String , Object> myPageQnAMap);
	
	
}
