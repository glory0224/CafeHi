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
@AutoConfigureMockMvc
public class QnAMapperTest {

	@Autowired
	QnaMapper qnaMapper;
	
	@Autowired
	MemberMapper memberMapper;
	
	@Autowired
	FileStore fileStore;
	
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
