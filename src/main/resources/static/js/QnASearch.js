$(".search_area button").on("click", function(e){
        e.preventDefault();
        
        let type = $(".search_area select").val();
        let keyword = $(".search_area input[name='keyword']").val();
        
        if(!type){
            alert("검색 종류를 선택하세요.");
            return false;
        }
        
        if(!keyword){
            alert("키워드를 입력하세요.");
            return false;
        }        
        
        QnAForm.find("input[name='type']").val(type);
        QnAForm.find("input[name='keyword']").val(keyword);
        QnAForm.find("input[name='pageNum']").val(1);
        QnAForm.submit();
    });