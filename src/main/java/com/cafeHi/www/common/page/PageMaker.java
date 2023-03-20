package com.cafeHi.www.common.page;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
public class PageMaker {
	
	 private int totalCount;
	 private int startPage;
	 private int endPage;
	 private boolean prev;
	 private boolean next;

	 private int displayPageNum = 10;

	 private Criteria cri;
	 
	 public void setCri(Criteria cri) {
	  this.cri = cri;
	 }

	 public void setTotalCount(int totalCount) {
	  this.totalCount = totalCount;
	  calcData();
	 }

	 public int getTotalCount() {
	  return totalCount;
	 }

	 public int getStartPage() {
	  return startPage;
	 }

	 public int getEndPage() {
	  return endPage;
	 }

	 public boolean isPrev() {
	  return prev;
	 }

	 public boolean isNext() {
	  return next;
	 }

	 public int getDisplayPageNum() {
	  return displayPageNum;
	 }

	 public Criteria getCri() {
	  return cri;
	 }
	 
	 
	 
	 private void calcData() {
	  endPage = (int) (Math.ceil(cri.getPage() / (double)displayPageNum) * displayPageNum);
	  startPage = (endPage - displayPageNum) + 1;
	  
	  int tempEndPage = (int) (Math.ceil(totalCount / (double)cri.getPerPageNum()));
	  if (endPage > tempEndPage)
	  {
	   endPage = tempEndPage;
	  }

	  prev = startPage == 1 ? false : true;
	  next = endPage * cri.getPerPageNum() >= totalCount ? false : true;


	 }

	public String makeQuery(int page)
	{
		UriComponents uriComponents =
				UriComponentsBuilder.newInstance()
						.queryParam("page", page)
						.queryParam("perPageNum", cri.getPerPageNum())
						.queryParam("searchType", ((SearchCriteria)cri).getSearchType())	// 검색 조건
						.queryParam("keyword", encoding(((SearchCriteria)cri).getKeyword())) // 검색 키워드
						.build();

		return uriComponents.toUriString();
	}

	 
	 // keyword 는 인코딩에 따라 의도한것과 다른 결과가 나올 수 있기 때문에 인코딩 기능을 추가합니다.

	private String encoding(String keyword) {
		 if(keyword == null || keyword.trim().length() == 0)
		 { return ""; }
		 
		 try {
		  return URLEncoder.encode(keyword, "UTF-8");
		 } catch(UnsupportedEncodingException e)
		 { return ""; }
		}
}
