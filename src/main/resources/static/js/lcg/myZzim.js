//찜 삭제 로직
function cgAjaxDeleteZzim(biz_id, user_id, auth){
	//alert("찜삭제 시작");
	//alert("biz_id -> "+biz_id);
	//alert("user_id-> "+user_id);
	//alert("auth-> "+auth);
	
	// insert 하는 ajax
	$.ajax({
		url : "/cgAjaxDeletetZzim",
		dataType : "json",
		data : { 
			"biz_id" : biz_id,
			"user_id" : user_id,
			"auth" : auth
		},
		success : function(zzim){
			if(zzim == 1){
				alert("찜목록에서 삭제되었습니다.");
				location.reload();
			}else{
				alert("찜삭제  등록 실패");
			}
		}
	})
	
}


const checkIn = document.getElementById("checkIn");
const checkOut = document.getElementById("checkOut");
let checkInDate = null;
let checkOutDate = null;

flatpickr(checkIn, {
  locale: "ko",
  enableTime: false,
  dateFormat: "Y-m-d",
  minDate: new Date(),
  maxDate: new Date().fp_incr(30),
  onClose: function(selectedDates, dateStr, instance) {
	// checkInDate 에 내가 고른 날짜가 들어감.
    checkInDate = selectedDates[0] || null;
    // 선택한 날짜를 입력창에 표시할 수 있도록 설정
    checkIn.value = dateStr;
  },
  // 고른 체크 아웃보다 더 뒤에 날짜를 못고르게 체크아웃 날짜가 들어있으면  고를 수 있는 최대 날짜가 체크아웃 전날로 설정함.
  onOpen: function(selectedDates, dateStr, instance){
	/*    if(checkOutDate != null){
		  instance.set("maxDate", checkOutDate.fp_incr(-1));
	  }  */
	  
	  resetCheckout();
  }
});

flatpickr(checkOut, {
  locale: "ko",
  enableTime: false,
  dateFormat: "Y-m-d",
  minDate: new Date().fp_incr(1),
  maxDate: new Date().fp_incr(30),
  onOpen: function(selectedDates, dateStr, instance) {
    // 이전에 선택한 checkInDate가 있으면 checkOut 달력의 minDate로 지정
    if (checkInDate != null) {
      instance.set("minDate", checkInDate.fp_incr(1));
    }
  }
});

function resetCheckout() {
	  checkOutDate = null;
	  checkOut.value = '';
	}

function cgAjaxZzimListsByUser_id(user_id, auth){
var checkIn = $("#checkIn").val();
var checkOut = $("#checkOut").val();
/* alert("checkIn ->>"+checkIn);
alert("checkOut ->>"+checkOut);
alert("user_id-> "+user_id);
alert("auth-> "+auth); */
// 체크아웃이 없을 때 유효성 검사
if(checkIn != '' && !checkOut){
	alert("체크아웃 날짜를 선택해주세요");
	return false;
}

var str = "";

//가격 포맷팅
function formatPrice(price){
	return price.toLocaleString('ko-KR');
}

// 버튼 클릭 시 ajax 호출
$.ajax({
	url : "/cgAjaxZzimListsByUser_id",
	data : {
			"user_id":user_id
		    },
	dataType : 'json',
	success : function(productSort) {
		str += "<div class='cgProduct_lists_area'>";
		str += "<div class='cgProduct_list_grid'>";
		$(productSort).each(function() {
	     if (!checkIn || !checkOut) {
                var diffDays = 1;
            } else {
                var checkInDate = new Date(checkIn);
                var checkOutDate = new Date(checkOut);
                // 숙박일의 절대일수를 구하고 
                var diffTime = Math.abs(checkOutDate - checkInDate);
                // 진짜 올림으로 소수점 방지를 해준다.  (1000 * 60 * 60 * 24) 하루를 밀리초로 표현한것 
                var diffDays = Math.ceil(diffTime /  (1000 * 60 * 60 * 24));
            }
            var totalPrice = this.min_price_r2 * diffDays;
        	var formattedPrice = formatPrice(totalPrice);
	    	str += "<div class='cgProduct_list_area'>";
            str += "    <div class='cgProduct_list_wrapper'>";
            str += "      <a href='/accomDetail?biz_id=" + this.biz_id + "&checkIn=" + checkIn + "&checkOut=" + checkOut + "'>";
            str += "        <div class='cgProduct_list_img'>";
            str += "          <ul>";
            str += "            <li><img class='thumbnail_img' src='" + this.r_img + "'></li>";
            str += "          </ul>";
            str += "        </div>";
            str += "        <div class='cgProduct_list_contents'>";
            str += "          <ul>";
            str += "            <li id='productTitle'>" + this.biz_name + "</li>";
            str += "            <li>";
            for (var i = 0; i < this.avg_rating; i++) {
              str += "              <i><img src='/img/cgStar.png' style='width:15px;height:15px;margin-right:5px;'></i>";
            }
            str += "              <span id='productRating'>" + this.avg_rating + "/5</span>";
            str += "            </li>";
            str += "            <li id='productPrice'>" + formattedPrice + "원</li>";
            str += "            <li id='productAddr'>" + this.addr + "</li>";
            str += "          </ul>";
            str += "        </div>";
            str += "      </a>";
            if (this.zzim_status == 'Y') {
              str += "      <div class='zzimButtons'>";
              str += "        <div>";
              str += "          <img id='zzimBtn' src='/img/like.png' onclick='cgAjaxDeleteZzim(\"" + this.biz_id + "\", \"" + user_id + "\", \"" + auth + "\")'>";
              str += "        </div>";
              str += "      </div>";
            }
            str += "    </div>";
 	         str += "  </div>";
          });

          str += "</div>";
          str += "</div>";
          
		//alert('ajax str->' + str)
		if(productSort == ""){
			// 조건이 없다면 행귄이 보여주기
			$(".listEmpty").show();
		
		}else{
			$(".cgProduct_lists_area").html(str);	
		}
	}
});
}
