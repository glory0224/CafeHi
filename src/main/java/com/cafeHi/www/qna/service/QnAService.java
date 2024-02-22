package com.cafeHi.www.qna.service;

import com.cafeHi.www.common.exception.EntityNotFoundException;
import com.cafeHi.www.common.file.FileStore;
import com.cafeHi.www.common.file.dto.UploadFile;
import com.cafeHi.www.common.page.SearchCriteria;
import com.cafeHi.www.member.dto.MemberInfo;
import com.cafeHi.www.member.repository.MemberRepository;
import com.cafeHi.www.qna.dto.QnADTO;
import com.cafeHi.www.qna.form.QnAFileForm;
import com.cafeHi.www.qna.form.QnAForm;
import com.cafeHi.www.qna.entity.QnA;
import com.cafeHi.www.qna.entity.QnAFile;
import com.cafeHi.www.qna.repository.QnAFileJpaRepository;
import com.cafeHi.www.qna.repository.QnAJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class QnAService {

    private final QnAJpaRepository qnAJpaRepository;
    private final QnAFileJpaRepository qnAFileJpaRepository;
    private final MemberRepository memberRepository;


    private final FileStore fileStore;

    @Transactional
    public void WriteQnA(QnAForm qnAForm, MultipartFile uploadFile){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberInfo memberInfo = (MemberInfo) principal;

        QnA qna = new QnA();

        qna.WriteFormSetQnA(qnAForm);

        qna.MemberInfoSetQnA(memberInfo);

        QnA findQnA = qnAJpaRepository.save(qna);

        QnAFile qnAFile = new QnAFile();

        try {
            AttachUploadFile(uploadFile, qnAFile);
        }catch (IOException e) {
            e.printStackTrace();
        }

        qnAFile.QnASetQnAFile(findQnA);

        qnAFileJpaRepository.save(qnAFile);

    }

    // AWS S3 파일 변경
    @Transactional
    public void UpdateQnA(QnAForm qnAForm, MultipartFile uploadFile){

        Optional<QnA> findQnA = qnAJpaRepository.findById(qnAForm.getQnaNum());
        QnA qnA = findQnA.orElseThrow(() -> new EntityNotFoundException("Not Found QnA Info"));
        QnAFile qnAFile = qnAFileJpaRepository.findQnAFileByQnA(qnA);
        // 기존 파일 존재하는 경우
        if(!uploadFile.isEmpty() && !qnAFile.getUploadPath().equals("none")) {

            // 기존의 S3새로운 파일 삭제
            fileStore.deleteFile(qnAFile.getStoreFileName());

            // 새로운 파일 등록
            try {
                AttachUploadFile(uploadFile, qnAFile);
            }catch (IOException e) {
                e.printStackTrace();
            }
            qnA.updateFormSetQnA(qnAForm);
            // 기존에 파일이 없던 경우
        } else if(uploadFile != null && !uploadFile.isEmpty() && qnAFile.getUploadPath().equals("none")) {
            // 새로운 파일 처리
            try {
                AttachUploadFile(uploadFile, qnAFile);
            }catch (IOException e) {
                e.printStackTrace();
            }
            qnA.updateFormSetQnA(qnAForm);
        } else {
            qnA.updateFormSetQnA(qnAForm);
        }

    }

    @Transactional
    public void deleteQnA(QnAForm qnAForm) throws IOException {
        Optional<QnA> findQnA = qnAJpaRepository.findById(qnAForm.getQnaNum());
        QnA qnA = findQnA.orElseThrow(() -> new EntityNotFoundException("Not Found QnA Info"));
        QnAFile qnAFile = qnAFileJpaRepository.findQnAFileByQnA(qnA);

        if(!qnAFile.getUploadPath().equals("none")) {
            fileStore.deleteFile(qnAFile.getStoreFileName());
        }

        qnAFileJpaRepository.deleteById(qnAFile.getId());
        qnAJpaRepository.deleteById(qnA.getQnaNum());
    }

    public List<QnADTO> findQnAList(int limit, int offset, SearchCriteria searchCriteria) {

        // 날짜 형식 포맷
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<QnA> qnAList = new ArrayList<>();
        // 검색조건이 있는경우
        if(searchCriteria.getSearchStartDate() != "" && searchCriteria.getSearchEndDate() != "") {
            LocalDate searchStartDate = LocalDate.parse(searchCriteria.getSearchStartDate(), formatter);
            LocalDate searchEndDate = LocalDate.parse(searchCriteria.getSearchEndDate(), formatter);
            searchCriteria.setStartDate(searchStartDate);
            searchCriteria.setEndDate(searchEndDate);
            qnAList = qnAJpaRepository.findPagingList(limit, offset, searchCriteria);
        } else {
            // 특정 날짜가 아닌경우 오늘날짜로 조회하도록
            searchCriteria.setStartDate(LocalDate.now());
            searchCriteria.setEndDate(LocalDate.now());
            qnAList = qnAJpaRepository.findPagingList(limit, offset, searchCriteria);
        }

        List<QnADTO> qnADTOList = new ArrayList<>();

        for (QnA qnA : qnAList) {
            QnADTO qnADTO = qnA.convertQnADTO();
            qnADTOList.add(qnADTO);
        }
        return qnADTOList;

    }

    public List<QnADTO> findQnAListByMemberCode(int limit, int offset, SearchCriteria searchCriteria, Long memberCode) {

        List<QnA> qnAList = qnAJpaRepository.findQnAListByMemberCode(limit, offset, searchCriteria, memberCode);
        List<QnADTO> qnADTOList = qnAList.stream().map(qnA -> qnA.convertQnADTO()).collect(Collectors.toList());

        return qnADTOList;
    }

    public QnADTO findQnA(Long QnAId) {

        Optional<QnA> findQnA = qnAJpaRepository.findById(QnAId);
        QnA qnA = findQnA.orElseThrow(() -> new EntityNotFoundException("Not Found QnA Info"));

        QnADTO qnADTO = new QnADTO(qnA);

        return qnADTO;
    }



    public long getCountAll() {
        return qnAJpaRepository.count();
    }

    public int getPagingCount(SearchCriteria searchCriteria) {

        return qnAJpaRepository.getPagingCount(searchCriteria);
    }


    public QnAFileForm findQnAFile(Long qnaId) {

        Optional<QnA> findQnA = qnAJpaRepository.findById(qnaId);
        QnA qnA = findQnA.orElseThrow(() -> new EntityNotFoundException("Not Found QnA Info"));
        QnAFile qnAFile = qnAFileJpaRepository.findQnAFileByQnA(qnA);


        // ModelMapper 라이브러리를 이용해 간편하게 Form 객체로 변경가능

        QnAFileForm qnAFileForm = new QnAFileForm();

        qnAFileForm.setId(qnAFile.getId());
        qnAFileForm.setQna(qnAFile.getQna());
        qnAFileForm.setUploadPath(qnAFile.getUploadPath());
        qnAFileForm.setStoreFileName(qnAFile.getStoreFileName());
        qnAFileForm.setUploadFileName(qnAFile.getUploadFileName());

        return qnAFileForm;
    }

    /**
     * if (!uploadFile.isEmpty())로 비교했더니 사이즈가 0인 파일이 들어오면 파일이 아예 없다고 게시글에 보여주기 때문에 파일은 있는데 내용이 없는 경우를 체크 못함
     * 이건 어떻게 해결하지? 의문..
     */
    private void AttachUploadFile(MultipartFile uploadFile, QnAFile qnAFile) throws IOException {


        if (!uploadFile.isEmpty()) {
            UploadFile attachFile = fileStore.storeFile(uploadFile);

            // 서버용 파일명
            String storeFileName = attachFile.getStoreFileName();


            // 클라이언트 파일명
            String uploadFileName = attachFile.getUploadFileName();

            // 전체 경로
            String fullPath = fileStore.getFullPath(storeFileName);

            qnAFile.AttachUploadFile(fullPath, storeFileName, uploadFileName);

        }else {
                qnAFile.AttachNoneFile();
        }
    }

    @Transactional
    public void increaseHit(HttpServletRequest request, HttpServletResponse response, Long qnaNum) {


        // 쿠키 생성으로 방문 했던 게시글 새로고침 할 때 계속 조회수 증가하는 현상 방지
        Cookie oldCookie = null;

        Cookie[] cookies = request.getCookies();

        // postView 라고 이름 지어진 쿠키가 있으면 새로운 쿠키 값을 넣는다.
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if(!oldCookie.getValue().contains("[" + qnaNum +"]")) {
                Optional<QnA> findQnA = qnAJpaRepository.findById(qnaNum);
                QnA qnA = findQnA.orElseThrow(() -> new EntityNotFoundException("Not Found QnA Info"));
                oldCookie.setValue(oldCookie.getValue() + "[" + qnaNum + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60*60*24); // 쿠키 설정 시간 하루
                response.addCookie(oldCookie);

                qnA.increaseHit();
                qnAJpaRepository.save(qnA);
            }

        }else {

            Optional<QnA> findQnA = qnAJpaRepository.findById(qnaNum);
            QnA qnA = findQnA.orElseThrow(() -> new EntityNotFoundException("Not Found QnA Info"));
            Cookie newCookie = new Cookie("postView", "[" + qnaNum + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);

            qnA.increaseHit();
            qnAJpaRepository.save(qnA);
        }
    }


}
