package com.cafeHi.www.qna.service;

import com.cafeHi.www.common.file.FileStore;
import com.cafeHi.www.common.file.dto.UploadFile;
import com.cafeHi.www.common.page.SearchCriteria;
import com.cafeHi.www.member.dto.CustomMember;
import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.member.repository.MemberRepository;
import com.cafeHi.www.qna.dto.QnAFileForm;
import com.cafeHi.www.qna.dto.QnAForm;
import com.cafeHi.www.qna.entity.QnA;
import com.cafeHi.www.qna.entity.QnAFile;
import com.cafeHi.www.qna.repository.QnAFileRepository;
import com.cafeHi.www.qna.repository.QnARepository;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class QnAService {

    private final QnARepository qnARepository;

    private final QnAFileRepository qnAFileRepository;

    private final MemberRepository memberRepository;


    private final FileStore fileStore;

    @Transactional
    public void WriteQnA(QnAForm qnAForm, MultipartFile uploadFile){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomMember memberInfo = (CustomMember) principal;

        Member findMember = memberRepository.findMember(memberInfo.getMemberInfo().getMemberCode());

        QnA qna = new QnA();

        qna.WriteFormSetQnA(qnAForm);

        qna.MemberSetQnA(findMember);

        long findQnANum = qnARepository.save(qna);

        QnA findQnA = qnARepository.findQnA(findQnANum);

        QnAFile qnAFile = new QnAFile();

        try {
            AttachUploadFile(uploadFile, qnAFile);
        }catch (IOException e) {
            e.printStackTrace();
        }

        qnAFile.QnASetQnAFile(findQnA);

        qnAFileRepository.save(qnAFile);

    }

    // AWS S3 파일 변경
    @Transactional
    public void UpdateQnA(QnAForm qnAForm, MultipartFile uploadFile){


        QnA findQnA = qnARepository.findQnA(qnAForm.getQnaNum());
        QnAFile qnAFileByQnA = qnAFileRepository.findQnAFileByQnA(findQnA);

        // 기존 파일 존재하는 경우
        if(uploadFile != null && !uploadFile.isEmpty() && !qnAFileByQnA.getUploadPath().equals("none")) {

            // 기존의 S3새로운 파일 삭제
            fileStore.deleteFile(qnAFileByQnA.getStoreFileName());

            // 새로운 파일 등록
            try {
                AttachUploadFile(uploadFile, qnAFileByQnA);
            }catch (IOException e) {
                e.printStackTrace();
            }
            findQnA.updateFormSetQnA(qnAForm);
            // 기존에 파일이 없던 경우
        } else if(uploadFile != null && !uploadFile.isEmpty() && qnAFileByQnA.getUploadPath().equals("none")) {
            // 새로운 파일 처리
            try {
                AttachUploadFile(uploadFile, qnAFileByQnA);
            }catch (IOException e) {
                e.printStackTrace();
            }
            findQnA.updateFormSetQnA(qnAForm);
        } else {
            findQnA.updateFormSetQnA(qnAForm);
        }

    }

    @Transactional
    public void deleteQnA(QnAForm qnAForm) throws IOException {
        QnA findQnA = qnARepository.findQnA(qnAForm.getQnaNum());
        QnAFile qnAFileByQnA = qnAFileRepository.findQnAFileByQnA(findQnA);
        if(!qnAFileByQnA.getUploadPath().equals("none")) {
            fileStore.deleteFile(qnAFileByQnA.getStoreFileName());
        }

        qnAFileRepository.deleteQnAFile(qnAFileByQnA.getId());
        qnARepository.deleteQnA(findQnA.getQnaNum());
    }

    public List<QnAForm> findQnAList(int limit, int offset, SearchCriteria searchCriteria) {

        List<QnA> qnAList = qnARepository.findPagingList(limit, offset, searchCriteria);

        List<QnAForm> qnAFormList = qnAList.stream().map(qna -> {
            QnAForm qnAForm = new QnAForm();
            qnAForm.setQnaNum(qna.getQnaNum());
            qnAForm.setQnaTitle(qna.getQnaTitle());
            qnAForm.setQnaTitleClassification(qna.getQnaTitleClassification());
            qnAForm.setQnaContent(qna.getQnaContent());
            qnAForm.setQnaHit(qna.getQnaHit());
            qnAForm.setMember(qna.getMember());
            qnAForm.setQnaWriteDate(qna.getQnaWriteDate());
            qnAForm.setQnaUpdateDate(qna.getQnaUpdateDate());
            return qnAForm;
        }).collect(Collectors.toList());


        return qnAFormList;

    }

    public QnAForm findQnA(Long QnAId) {

        QnA qnA = qnARepository.findQnA(QnAId);

        QnAForm qnaForm = new QnAForm();

        qnaForm.setQnaNum(qnA.getQnaNum());
        qnaForm.setQnaTitle(qnA.getQnaTitle());
        qnaForm.setQnaContent(qnA.getQnaContent());
        qnaForm.setQnaTitleClassification(qnA.getQnaTitleClassification());
        qnaForm.setQnaHit(qnA.getQnaHit());
        qnaForm.setMember(qnA.getMember());
        qnaForm.setQnaWriteDate(qnA.getQnaWriteDate());
        qnaForm.setQnaUpdateDate(qnA.getQnaUpdateDate());

        return qnaForm;
    }



    public int getCountAll() {
        return qnARepository.getCountAll();
    }

    public int getPagingCount(SearchCriteria searchCriteria) {
        return qnARepository.getPagingCount(searchCriteria);
    }



    public QnAFileForm findQnAFile(Long qnaId) {

        QnA qnA = qnARepository.findQnA(qnaId);

        QnAFile qnAFileByQnA = qnAFileRepository.findQnAFileByQnA(qnA);

        // ModelMapper 라이브러리를 이용해 간편하게 Form 객체로 변경가능

        QnAFileForm qnAFileForm = new QnAFileForm();

        qnAFileForm.setId(qnAFileByQnA.getId());
        qnAFileForm.setQna(qnAFileByQnA.getQna());
        qnAFileForm.setUploadPath(qnAFileByQnA.getUploadPath());
        qnAFileForm.setStoreFileName(qnAFileByQnA.getStoreFileName());
        qnAFileForm.setUploadFileName(qnAFileByQnA.getUploadFileName());

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
                QnA qnA = qnARepository.findQnA(qnaNum);
                oldCookie.setValue(oldCookie.getValue() + "[" + qnaNum + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60*60*24); // 쿠키 설정 시간 하루
                response.addCookie(oldCookie);

                qnA.increaseHit();
                qnARepository.save(qnA);
            }

        }else {

            QnA qnA = qnARepository.findQnA(qnaNum);
            Cookie newCookie = new Cookie("postView", "[" + qnaNum + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);

            qnA.increaseHit();
            qnARepository.save(qnA);
        }
    }


}
