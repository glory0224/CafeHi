// JS 유효성 검사

// 유효성 검사가 적용이 안되는 경우 : 대부분 id나 name의 오타일 가능성이 높고, 그게 아닐 경우에는 인터넷 어플리케이션을 바꿔가며 적용해볼것(ex chrome, microsoftEdge ...)

// 회원가입 유효성 검사
function signupcheck() {
	
	var memberId = document.getElementById('member_id');
	var memberPassword = document.getElementById('memberPassword');
	var contact = document.getElementById('memberContact');
	var memberEmail = document.getElementById('memberEmail');
	/*mailAuthCheck : 인증번호 일치 여부 확인*/
	var mailAuthCheck = document.getElementById('email_check_boolean').checked;
	/*mailInputCheck : 인증번호 입력 여부 확인*/
	var mailInputCheck = document.getElementById('email_chk_boolean').checked;
	var mailDuplicateCheck = document.getElementById('email_dup_boolean').checked;
	var idDuplicateCheck = document.getElementById('id_check_boolean').checked;
	var idCheck = document.getElementById('id_boolean').checked;
	

	
	if(!mailInputCheck){
		alert('인증번호를 입력하셔야합니다.');
		return;
	}
	
	if(!mailAuthCheck){
		alert('인증번호가 일치 하지 않습니다. 메일 인증 절차를 다시 진행해주세요.');
		return;
	}
	
	if(!idCheck){
		alert('아이디 중복확인를 진행해주세요.');
		return;
	}
	
	if(!idDuplicateCheck){
		alert('중복된 아이디가 존재합니다. 아이디를 확인해주세요.');
		return;
	}
	
	if(!mailDuplicateCheck){
		alert('중복된 이메일이 존재합니다. 이메일을 확인해주세요.');
		return;
	}

	document.getElementById('memberForm').submit();


}

/*회원 삭제 버튼*/

function button_event(){
		var inputId = document.getElementById('deleteId');
		var inputPw = document.getElementById('deletePw');
		
		if(inputId.value == ""){
			alert('아이디를 입력해주세요.');
			return;
		}else if(inputPw.value == ""){
			alert('비밀번호를 입력해주세요.');
			return;
		}
		
		  if (confirm("정말 삭제하시겠습니까??") == true){    //확인
			  document.getElementById('memberDeleteForm').submit();
		  }else{   //취소
		      return;
		  }
	}

/* 회원 비밀번호 찾기 버튼 이벤트 */



