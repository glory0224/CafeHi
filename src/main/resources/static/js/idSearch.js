
function idSearch() {
	//window 객체를 사용해서 창을 열기 때문에 id값을 가져올 때도 window.document로 범위를 설정해줘야 id가 넘어온다.
	authEmail =  window.document.getElementById( 'authIdEmail' );
	
	// 빈칸 체크
	if(authEmail.value == "") {
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
	
	
	document.getElementById('idSearchForm').submit();


}


