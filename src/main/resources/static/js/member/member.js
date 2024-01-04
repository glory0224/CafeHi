
// duam 주소 찾기 - 사용시 <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script> 태그가 있는지 확인하기
function find_address(setType) {
    new daum.Postcode({
        oncomplete: function(data) {
            // // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
            //
            // // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            // var roadAddr = data.roadAddress; // 도로명 주소 변수
            // var extraRoadAddr = ''; // 참고 항목 변수
            //
            // // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            // if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
            //     extraRoadAddr += data.bname;
            // }
            // // 건물명이 있고, 공동주택일 경우 추가한다.
            // if(data.buildingName !== '' || data.apartment === 'Y'){
            //    extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            // }

            let userSelectedType = data.userSelectedType; // 선택한 주소타입( R : 도로명 주소, J : 지번 주소)
            let postcode1 = data.postcode1;
            let postcode2 = data.postcode2;
            let jibunAddress = data.jibunAddress;

            let zonecode = data.zonecode;
            let roadAddress = data.roadAddress;
            let buildingName = data.buildingName;

            let searchZipcode = "";
            let searchAddress1 = "";
            let searchAddress2 = "";

            if(userSelectedType == "J") {
                searchZipcode = postcode1+postcode2;
                searchAddress1 = jibunAddress;
                searchAddress2 = "";
            } else if(userSelectedType == "R") {
                searchZipcode = zonecode;
                searchAddress1 = roadAddress;

                if(buildingName == "") {
                    searchAddress2 = "";
                } else {
                    searchAddress2 = " (" + buildingName + ")";
                }
            }

            if(setType == "signup") {
                document.getElementById("memberZipCode").value = searchZipcode;
                document.getElementById("memberAddress").value = searchAddress1 + searchAddress2;
            } else if(setType == "memberUpdate") {
                document.getElementById("memberUpdateZipCode").value = searchZipcode;
                document.getElementById("memberUpdateAddress").value = searchAddress1 + searchAddress2;
            }

            // // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
            // if(roadAddr !== '' && extraRoadAddr != ''){
            // 	extraRoadAddr = extraRoadAddr;
            //      roadAddr + ' ' + extraRoadAddr;
            //     document.getElementById("memberJibunAddress").value = data.jibunAddress;
            //
            // } else if (roadAddr !== ''){
            // 	document.getElementById("memberRoadAddress").value = roadAddr
            // 	//document.getElementById("memberJibunAddress").value = data.jibunAddress;
            // } else if (extraRoadAddr != '') {
            // 	document.getElementById("memberJibunAddress").value = data.jibunAddress;
            // }
            //
            // return;
        }
    }).open();
}
