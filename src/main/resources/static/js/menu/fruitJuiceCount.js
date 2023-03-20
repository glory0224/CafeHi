function countFruitJuiceList0(type)  {
	  // 결과를 표시할 element
	  const resultCount = document.getElementById('fruitCount0');
	  const resultCartAmount = document.getElementById('fruitCartAmount0');
	  const resultOrderAmount = document.getElementById('fruitOrderAmount0');
	  
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
	
function countFruitJuiceList1(type)  {
	  // 결과를 표시할 element
	  const resultCount = document.getElementById('fruitCount1');
	  const resultCartAmount = document.getElementById('fruitCartAmount1');
	  const resultOrderAmount = document.getElementById('fruitOrderAmount1');
	  
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
	
function countFruitJuiceList2(type)  {
	  // 결과를 표시할 element
	  const resultCount = document.getElementById('fruitCount2');
	  const resultCartAmount = document.getElementById('fruitCartAmount2');
	  const resultOrderAmount = document.getElementById('fruitOrderAmount2');
	  
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

