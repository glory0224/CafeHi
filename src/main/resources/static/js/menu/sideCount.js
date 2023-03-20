function countSideList0(type)  {
	  // 결과를 표시할 element
	  const resultCount = document.getElementById('sideCount0');
	  const resultCartAmount = document.getElementById('sideCartAmount0');
	  const resultOrderAmount = document.getElementById('sideOrderAmount0');
	  
	  // 현재 화면에 표시된 값
	  let number = resultCount.innerText;
	  
	  // 더하기/빼기
	  if(type === 'plus') {
	    number = parseInt(number) + 1;
	  }else if(type === 'minus' && number > 0)  {
	    number = parseInt(number) - 1;
	  }
	  
	  // 결과 출력
	  resultCount.innerText = number;
	  resultCartAmount.value = resultCount.innerText
	  resultOrderAmount.value = resultCount.innerText
	}
	
function countSideList1(type)  {
	  // 결과를 표시할 element
	  const resultCount = document.getElementById('sideCount1');
	  const resultCartAmount = document.getElementById('sideCartAmount1');
	  const resultOrderAmount = document.getElementById('sideOrderAmount1');
	  
	  // 현재 화면에 표시된 값
	  let number = resultCount.innerText;
	  
	  // 더하기/빼기
	  if(type === 'plus') {
	    number = parseInt(number) + 1;
	  }else if(type === 'minus' && number > 0)  {
	    number = parseInt(number) - 1;
	  }
	  
	  // 결과 출력
	  resultCount.innerText = number;
	  resultCartAmount.value = resultCount.innerText
	  resultOrderAmount.value = resultCount.innerText
	}
	
	
function countSideList2(type)  {
	  // 결과를 표시할 element
	  const resultCount = document.getElementById('sideCount2');
	  const resultCartAmount = document.getElementById('sideCartAmount2');
	  const resultOrderAmount = document.getElementById('sideOrderAmount2');
	  
	  // 현재 화면에 표시된 값
	  let number = resultCount.innerText;
	  
	  // 더하기/빼기
	  if(type === 'plus') {
	    number = parseInt(number) + 1;
	  }else if(type === 'minus' && number > 0)  {
	    number = parseInt(number) - 1;
	  }
	  
	  // 결과 출력
	  resultCount.innerText = number;
	  resultCartAmount.value = resultCount.innerText
	  resultOrderAmount.value = resultCount.innerText
	}
	
	
function countSideList3(type)  {
	  // 결과를 표시할 element
	  const resultCount = document.getElementById('sideCount3');
	  const resultCartAmount = document.getElementById('sideCartAmount3');
	  const resultOrderAmount = document.getElementById('sideOrderAmount3');
	  
	  // 현재 화면에 표시된 값
	  let number = resultCount.innerText;
	  
	  // 더하기/빼기
	  if(type === 'plus') {
	    number = parseInt(number) + 1;
	  }else if(type === 'minus' && number > 0)  {
	    number = parseInt(number) - 1;
	  }
	  
	  // 결과 출력
	  resultCount.innerText = number;
	  resultCartAmount.value = resultCount.innerText
	  resultOrderAmount.value = resultCount.innerText
	}


