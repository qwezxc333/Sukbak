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
		
	function cgAjaxProductHotelList(addr, kind){
		var checkIn = $("#checkIn").val();
		var checkOut = $("#checkOut").val();
		var bedTypes = [];
		$('input[name="bed_type"]:checked').each(function() {
		    bedTypes.push($(this).val());
		});
		if (bedTypes.length == 0) {
		    bedTypes = [310, 320, 330];
		}
		
		// 호텔 종류 배열로 받기
		var accom_types = [];
		$('input[name="accom_types"]:checked').each(function() {
			accom_types.push($(this).val());
		});
		if (accom_types.length == 0) {
			accom_types = [511, 513, 514, 515];
		}
		
		
		
		var addr 	 = addr;
		
		var capacity = $("#capacity").val();
		if(!capacity){
			capacity = 2;
		}
		
	
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
		
		// 버튼 클릭 시 ajax 호출
		$.ajax({
			url : "/cgAjaxProductList",
			data : {
					"accom_type":accom_types,
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
				    "r_capacity":capacity
				    },
			dataType : 'json',
			success : function(productSort) {
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
		            
		            str += "<div class='cgProduct_list_area'>";
		            str += "<a href='/accomDetail?biz_id=" + this.biz_id + "&checkIn="+checkIn+"&checkOut="+checkOut+"'>";
		            str += "<div class='cgProduct_list_img'>";
		            str += "<ul>";
		            str += "<li><img class='thumbnail_img' src='/img/" + this.r_img + "'></li>";
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
		            str += "<li id='productPrice'>" + totalPrice + "원</li>";
		            str += "<li id='productAddr'>" + this.addr + "</li>";
		            str += "</ul>";
		            str += "</div>";
		            str += "</a>";
		            str += "</div>";					
				})
				alert('ajax str->' + str)
				$(".cgProduct_lists_area").html(str);
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