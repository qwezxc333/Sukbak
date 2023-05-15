//찜버튼 로직
function cgAjaxInsertZzim(biz_id, user_id, auth, index){
	//alert("찜버튼 시작");
	//alert("index -> "+index);
	//alert("user_id-> "+user_id);
	//alert("auth-> "+auth);
	// seller나 admin 찜 못하게 
	if(auth == "[SELLER]" || auth == "[ADMIN]"){
		alert("user 회원만 이용 가능합니다.");
		return false;
	}
	// 로그인을 하지 않았거나, user권한인지 판단하기 위함.
	if(user_id == null || user_id == "" || auth != "[USER]"){
		alert("로그인이 필요한 서비스 입니다.")
		return false;
	}
	// insert 하는 ajax
	$.ajax({
		url : "/cgAjaxInsertZzim",
		dataType : "json",
		data : { 
			"biz_id" : biz_id,
			"user_id" : user_id,
			"auth" : auth
		},
		success : function(zzim){
			if(zzim == 1){
				alert("찜목록에 추가하였습니다.");
				//location.reload();
			}else{
				alert("찜목록 등록 실패");
			}
		}
	})
	var zzimBtnInsertImg = $("#zzimBtnInsert" + index);

	// 이미지 경로를 변경합니다.
	zzimBtnInsertImg.attr("src", "/img/like.png");
    
   
	
	
}
// 찜 삭제 로직
function cgAjaxDeleteZzim(biz_id, user_id, auth, index){
	//alert("찜삭제 시작");
	//alert("index -> "+index);
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
			}else{
				alert("찜삭제  등록 실패");
			}
		}
	})
	
	var zzimBtnDeleteImg = $("#zzimBtnDelete" + index);

	// 이미지 경로를 변경합니다.
	zzimBtnDeleteImg.attr("src", "/img/dislike.png");
	
}

//변수를 계속 변경해주어야 하므로 .val()을 사용하지 않는다.
// 객체 자체를 선택한거임.
const priceSlider = $("#priceSlider");
const maxPriceInput = $("#maxPrice");

// 이벤트 리스너 추가
// priceSlider가 작동될 때 input 이벤트가 발생되고, priceslider의 값이 maxPrice값에 들어가게 되는 것.
// input 이벤트는 키를 누르거나 복사/붙여넣기 등을 통해 입력한 내용이 변경될 때마다 발생한다. 
priceSlider.on("input", function() {
// 여기선 구체적인 벨류값을 가져와야하므로 .val()을 넣어야함. 
if($("#priceSlider").val() == 100){
	maxPriceInput.val(priceSlider.val()+'만원 이상');
} else{
	maxPriceInput.val(priceSlider.val()+'만원');
}
});



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

function cgAjaxProductList(accom, kind, user_id, auth){
var checkIn = $("#checkIn").val();
var checkOut = $("#checkOut").val();
var maxPrice = ($("#priceSlider").val()*10000);
// 모든 가격이 다 나와야하니까 큰 값으로  치환한다. 
if(maxPrice == 1000000){
	maxPrice = 9999999999;
}
//alert("maxPrice-> "+maxPrice);
//alert("user_id-> "+user_id);
//alert("auth-> "+auth);
// 체크아웃이 없을 때 유효성 검사
if(checkIn != '' && !checkOut){
	alert("체크아웃 날짜를 선택해주세요");
	return false;
}
var bedTypes = [];
$('input[name="bed_type"]:checked').each(function() {
    bedTypes.push($(this).val());
});
if (bedTypes.length == 0) {
    bedTypes = [310, 320, 330];
}

var addr 	 = $("#addr").val();

var capacity = $("#capacity").val();
if(!capacity){
	capacity = 2;
}

var ajAccom_type = accom;
var pool 		= null;
var parking 	= null;
var cafe    	= null;
var restaurant 	= null;
var store 		= null;
var sauna 		= null;
var laundry 	= null;
var fitness 	= null;
var bath		= null;
var wifi		= null;
var ac			= null;
var tv			= null;

if ($("#pool").prop("checked")) {
	pool = "Y";
}; 

if ($("#parking").prop("checked")) {
	parking = "Y";
}; 

if ($("#cafe").prop("checked")) {
	cafe = "Y";
}; 

if ($("#restaurant").prop("checked")) {
	restaurant = "Y";
}; 

if ($("#store").prop("checked")) {
	store = "Y";
}; 

if ($("#sauna").prop("checked")) {
	sauna = "Y";
}; 

if ($("#laundry").prop("checked")) {
	laundry = "Y";
}; 

if ($("#fitness").prop("checked")) {
	fitness = "Y";
}; 
if ($("#bath").prop("checked")) {
	bath = "Y";
}; 
if ($("#ac").prop("checked")) {
	ac = "Y";
}; 
if ($("#tv").prop("checked")) {
	tv = "Y";
}; 

var str = "";
//가격 포맷팅
function formatPrice(price){
	return price.toLocaleString('ko-KR');
}

// 버튼 클릭 시 ajax 호출
$.ajax({
	url : "/cgAjaxProductList",
	data : {"accom_type":ajAccom_type,  
		    "kind":kind,
		    "bed_type":bedTypes,
		    "check_in":checkIn,
		    "check_out":checkOut,
		    "pool":pool,
		    "parking":parking,
		    "cafe":cafe,
		    "restaurant":restaurant,
		    "store":store,
		    "sauna":sauna,
		    "laundry":laundry,
		    "fitness":fitness,
		    "addr":addr,
		    "bath":bath,
		    "wifi":wifi,
		    "ac":ac,
		    "tv":tv,
		    "r_capacity":capacity,
		    "min_price_r2":maxPrice
		    },
	dataType : 'json',
	success : function(productSort) {
		
		$(productSort).each(function(index) {
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
            // 날짜변경해서 숙박 가격이 오른상태에서도 똑같이 필터링이 되게 하기.
		    if(maxPrice >= totalPrice){
		    	var formattedPrice = formatPrice(totalPrice);
	            str += "<div class='cgProduct_list_area'>";
	            str += "<a href='/accomDetail?biz_id=" + this.biz_id + "&checkIn="+checkIn+"&checkOut="+checkOut+"'>";
	            str += "<div class='cgProduct_list_img'>";
	            str += "<ul>";
	            str += "<li><img class='thumbnail_img' src='" + this.r_img + "'></li>";
	            str += "</ul>";
	            str += "</div>";
	            str += "<div class='cgProduct_list_contents'>";
	            str += "<ul>";
	            str += "<li id='productTitle'>" + this.biz_name + "</li>";
	            str += "<li>";
	            for (var i = 0; i < this.avg_rating; i++) {
	            	str += "<i><img src='/img/cgStar.png' style='width:15px;height:15px;margin-right:5px;'></i>";
	            }
	            str += "<span id='productRating'>" + this.avg_rating + "/5</span>";
	            str += "</li>";
	            str += "<li id='productPrice'>" + formattedPrice + "원</li>";
	            str += "<li id='productAddr'>" + this.addr + "</li>";
	            str += "</ul>";
	            str += "</div>";
	            str += "</a>";
	            if (this.zzim_status == null) {
	                str += "<div class='zzimButtons'>";
	                str += "<div>";
	                str += "<img id='zzimBtnInsert" + index + "' src='/img/dislike.png' onclick='cgAjaxInsertZzim(\"" + this.biz_id + "\", \"" + user_id + "\", \"" + auth + "\", \"" + index + "\")'>";
	                str += "</div>";
	                str += "</div>";
	            } else if (this.zzim_status == 'Y') {
	                str += "<div class='zzimButtons'>";
	                str += "<div>";
	                str += "<img id='zzimBtnDelete" + index + "' src='/img/like.png' onclick='cgAjaxDeleteZzim(\"" + this.biz_id + "\", \"" + user_id + "\", \"" + auth + "\", \"" + index + "\")'>";
	                str += "</div>";
	                str += "</div>";
	            }
	            str += "</div>";
		    }
		})
		// alert('ajax str->' + str)
		// 가격 변동 후에 다시 조건을 주었을 때 없다면  str에 축척되지 않으니 str로 비교해야 함.
		if(str == ""){
			// 조건이 없다면 행귄이 보여주기
			$(".listEmpty").show();
			$(".cgProduct_lists_area").empty();
		
		}else{
			// 조건이 있다면 행귄이 나옴
			$(".listEmpty").hide();
			$(".cgProduct_lists_area").html(str);	
		}
	}
});
}

function plus(){
	var capacity = document.getElementById("capacity");
	var value = parseInt(capacity.value);
	if(value < 10){
		capacity.value = value + 1;
	}
}

function minus(){
	var capacity = document.getElementById("capacity");
	var value = parseInt(capacity.value);
	if(value > 2){
		capacity.value = value - 1;
	}
}
