// qna 댓글 등록
document.addEventListener("DOMContentLoaded", function (){
    let comment = document.getElementById('qnaComment');
    comment.addEventListener("keydown", function (event) {
        if (event.key === 'Enter') {
            // 양식 필드(예: type="text" 또는 type="password" 입력) 내에서 Enter 키를 누르면 브라우저의 기본 동작은 양식을 제출한다.
            event.preventDefault(); // 기본 양식 제출 방식을 loginCheck 함수를 호출하는 방식으로 변경한다.
            commentCheck();
        }
    });
});

function commentCheck() {
    let commentForm = document.getElementById('commentForm');

    let writeDate = commentForm.querySelector('#qnaCommentWriteDate').value;
    let UpdateDate = commentForm.querySelector('#qnaCommentUpdateDate').value;
    let qnaNum = commentForm.querySelector('#qnaNum').value;
    commentForm.querySelector('#memberId')


    if(comment.value == "") {
        alert('댓글을 입력해주세요.');
        qnaComment.focus();
    }


}


