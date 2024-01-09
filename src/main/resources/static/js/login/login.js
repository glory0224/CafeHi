// 비밀번호 입력 후 엔터키로 로그인 시도
document.addEventListener("DOMContentLoaded", function (){
    let password = document.getElementById('floatingPassword');
    password.addEventListener("keydown", function (event) {
        if (event.key === 'Enter') {
            // 양식 필드(예: type="text" 또는 type="password" 입력) 내에서 Enter 키를 누르면 브라우저의 기본 동작은 양식을 제출한다.
            event.preventDefault(); // 기본 양식 제출 방식을 loginCheck 함수를 호출하는 방식으로 변경한다.
            loginCheck();
        }
    });
});

// 로그인 체크 후 로그인
function loginCheck(){

    memberId = document.getElementById('floatingInput');
    memberPassword = document.getElementById('floatingPassword');

    if (memberId.value == "") {
        alert('아이디를 입력해주세요.');
        floatingInput.focus();
        return;
    } else if (memberPassword.value == "") {
        alert('비밀번호를 입력해주세요.');
        floatingPassword.focus();
        return;
    }

    document.getElementById('loginForm').submit();
}

