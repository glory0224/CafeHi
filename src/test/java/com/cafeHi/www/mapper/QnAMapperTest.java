package com.cafeHi.www.mapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.cafeHi.www.board.dto.QnA;
import com.cafeHi.www.common.file.FileStore;
import com.cafeHi.www.mapper.board.QnaMapper;
import com.cafeHi.www.mapper.member.MemberMapper;
import com.cafeHi.www.member.dto.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
//@Transactional
@AutoConfigureMockMvc
public class QnAMapperTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	QnaMapper qnaMapper;
	
	@Autowired
	MemberMapper memberMapper;
	
	@Autowired
	FileStore fileStore;
	
	// 파일 다운로드 관련 블로그 - https://wordbe.tistory.com/entry/Spring-Web-MVC-%ED%8C%8C%EC%9D%BC%EC%97%85%EB%A1%9C%EB%93%9C-%ED%8C%8C%EC%9D%BC%EB%8B%A4%EC%9A%B4%EB%A1%9C%EB%93%9C
	// test file 을 mock을 활용했다. (참고)
//	@Test
//	public void insertQnA() throws Exception {
//		
//		// 파일 업로드 관련
//		MockMultipartFile file = new MockMultipartFile(
//				"file",
//				"test.txt",
//				"text/plain",
//				"For test".getBytes());
//		
//		// When
//	    this.mockMvc.perform(multipart("/files").file(file))
//	      .andDo(print())
//	      .andExpect(status().is3xxRedirection())
//	      ;
//		
//		
//		//QnA qna = new QnA("test title", "없음", "test content", LocalDateTime.now(), 0, 1, "upload_path", null , "store_file_name", "upload_file_name", null);
//		
//		//int qna_num = qnaMapper.writeQnA(qna);
//		
//		//log.info("qna_num = {} " , qna_num);		
//	}
	
	@Test
	public void insertQnA() {
		
		List<QnA> qnaList = new ArrayList<>();
		
		for (int i = 1; i < 51; i++) {
			
			Member getMember = memberMapper.getMember(i); 
			
			QnA qna = new QnA("testTitle" + i, "none", "testContent" + i , LocalDateTime.now() , LocalDateTime.now() , 0, "none", "none", "없음", getMember);
			
			qnaMapper.writeQnA(qna);
			
			qnaList.add(qna);
			
		}
		
		Assertions.assertThat(qnaList.size()).isEqualTo(50);
	}
	
	
	
	
}
