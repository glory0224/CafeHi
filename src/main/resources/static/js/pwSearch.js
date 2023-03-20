function pwSearch() {
	
	authId = window.document.getElementById( 'authId' );
	
	
	if(authId.value == "") {
		alert('아이디를 입력해주세요');
		authId.focus();
		return;
	}
	
	
	document.getElementById('pwSearchForm').submit();


}

function pwSearchAuth(){
	authEmail =  window.document.getElementById( 'authPwEmail' );
	if(authEmail.value == ""){
		alert('이메일을 입력해주세요');
		authEmail.focus();
		return;
	}
	
	// 이메일 형식 체크 
	// RFC 5322 이메일 정규 표현식
	emailReg = new RegExp("([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\"\(\[\]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\[[\t -Z^-~]*])");
	
	if(!emailReg.test(authEmail.value)){
		alert('올바른 이메일 형식을 입력하세요. \n ex) ID@naver.com or ID@yale.edu.com');
		return false;
	}
	
	document.getElementById('pwSearchAuthForm').submit();
	
}

