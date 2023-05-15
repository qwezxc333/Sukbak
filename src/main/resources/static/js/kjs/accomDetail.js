	//초기 셋팅
	const dateRange = document.getElementById("dateRange");
	
	// URL에서 checkIn과 checkOut 값 추출
	const urlParams = new URLSearchParams(window.location.search);
	let checkIn = urlParams.get("checkIn") ? new Date(urlParams.get("checkIn")) : new Date();
	let checkOut = urlParams.get("checkOut") ? new Date(urlParams.get("checkOut")) : new Date(Date.now() + 24 * 60 * 60 * 1000);
	
	
	// 플랫피커 달력
	let fp = flatpickr(dateRange, {
		mode : "range",
		minDate : new Date(),
		maxDate : new Date().fp_incr(30),
		dateFormat : "m.d",
		defaultDate: (checkIn >= new Date() && checkOut > checkIn) ? [checkIn, checkOut] : [new Date(), checkOut],
		clickOpens : false,
		inline : true,
		locale : "ko",
		onChange : function(selectedDates, dateStr) {
	
			// 날짜 범위 선택되면 input에 01.01(월) ~ 01.02(화) 1박 형식 포맷팅
			if (selectedDates.length === 2) {
				let nights = (selectedDates[1] - selectedDates[0]) / (1000 * 60 * 60 * 24);
				dateRange.value = dateStr + " · " + nights + "박";
				
				checkIn = selectedDates[0];
				checkOut = selectedDates[1];
				loadData();
			}
		}
	});
	
	// 구글지도
	const locationDiv = document.getElementById("location");
	const latitude = parseFloat(locationDiv.dataset.latitude);
	const longitude = parseFloat(locationDiv.dataset.longitude);
	
	// 반응형 동작을 위한 이벤트 핸들러 등록
	const mediaQuery = window.matchMedia("(max-width: 992px)");
	
	// mediaQuery에 handleMediaQuery 등록, 992px되면 isMobile true
	function handleMediaQuery(mq) {
	
		const isMobile = mq.matches;
		
    	fp.destroy(); // 기존 인스턴스를 파괴
    	fp = flatpickr(dateRange, {
    		mode : "range",
    		minDate : new Date(),
    		maxDate : new Date().fp_incr(30),
    		dateFormat : "m.d",
    		defaultDate: (checkIn >= new Date() && checkOut > checkIn) ? [checkIn, checkOut] : [new Date(), checkOut],
    		clickOpens : isMobile,
    		inline : !isMobile,
    		locale : "ko",
    		onReady: function(){
    			defaultInputValue([ checkIn, checkOut ]);
    		},
    		onChange : function(selectedDates, dateStr) {
    	
    			// 날짜 범위 선택되면 input에 01.01(월) ~ 01.02(화) 1박 형식 표시
    			if (selectedDates.length === 2) {
    				let nights = (selectedDates[1] - selectedDates[0]) / (1000 * 60 * 60 * 24);
    				dateRange.value = dateStr + " · " + nights + "박";
    				
    				checkIn = selectedDates[0];
    				checkOut = selectedDates[1];
    				loadData();
    			}
    		}
    	});
	}
	
	// 플랫피커에 사용하기위해 Date타입으로 변환했으나, 서버에선 String 비교.. formatDate 포맷팅
	function formatDate(date) {
	    const year = date.getFullYear();
	    const month = (date.getMonth() + 1).toString().padStart(2, '0');
	    const day = date.getDate().toString().padStart(2, '0');
	    return `${year}-${month}-${day}`;
	}
	
	// 가격 포맷팅
	function formatPrice(price){
		return price.toLocaleString('ko-KR') + '원' ;
	}
	
	// ajax 출력
	function loadData() {
		let today = new Date();
		today.setHours(0, 0, 0, 0); // 시간, 분, 초, 밀리초를 0으로 설정
		
		//URL값에서 준거 변수에 저장
		const urlParams = new URLSearchParams(window.location.search);
		let vBiz_id = urlParams.get("biz_id");
		let vCheckIn = checkIn;
		let vCheckOut = checkOut;
		
		//URL 체크인,체크아웃이 오늘날짜 이전으로 들어오면 Date형변환 하고 비교해야함
		let vCheckInDate = new Date(vCheckIn);
		let vCheckOutDate = new Date(vCheckOut);
		
		// setHours (시분초 제거 후 자정기준 비교)
		vCheckInDate.setHours(0, 0, 0, 0);
		vCheckOutDate.setHours(0, 0, 0, 0);
		
		//URL 체크인,체크아웃이 오늘날짜 이전으로 들어오면 Date형변환 후 비교
		if(vCheckInDate < today || vCheckOutDate <= today){
			vCheckIn = new Date();
			vCheckOut = new Date(Date.now() + 24 * 60 * 60 * 1000);
		    dateRange._flatpickr.setDate([vCheckIn, vCheckOut]); // 달력 업데이트
		}
		
		//체크인 체크아웃이 URL에 없다면, 기본값 설정
		if(!vCheckIn || !vCheckOut){
			vCheckIn = new Date();
			vCheckOut = new Date(Date.now() + 24 * 60 * 60 * 1000);
			dateRange._flatpickr.setDate([vCheckIn, vCheckOut]); // 달력 업데이트
		}
		
		let nights = (vCheckOut - vCheckIn) / (1000 * 60 * 60 * 24);
		
		//체크인,체크아웃이 둘다 있다면
	    if (checkIn && checkOut) {
	        $.ajax({
	            url: "/accomDetailRoomList",
	            type: "GET",
	            dataType:'json',
	            data: {biz_id : vBiz_id, 
	            		  checkIn : formatDate(vCheckIn), 
	            		  checkOut : formatDate(vCheckOut)},
	            success: data => {
	            	let html = "";
	            	
	            	data.forEach( item => {
	            		html += `<div class="roomList">`
	            		html += `<div class="roomListImg"><img src=${item.r_img} alt = "roomImage" onerror="this.onerror=null; this.src='/img/imageLoading.jpg';"></div>`;
	            		html += `<div class="roomListContent">`;
	            		html += `<h2>${item.r_name}</h2>`;
	            		html += `<p>가격  <br>${formatPrice(item.r_price)}  / 1박</p>`;
						html += `<button class="roomListReserveBtn" data-is-reserved="${item.is_reserved}" onclick="location.href='/payment?biz_id=${item.biz_id}&r_id=${item.r_id}&checkIn=${formatDate(vCheckIn)}&checkOut=${formatDate(vCheckOut)}'">`;
						
						if(item.is_reserved == 1){
							html += '예약불가' ;
						} else{
							html += `예약  ${formatPrice(item.totalRoomPrice)}/ ${nights}박`;
						}
						html += '</button>';
						html += '</div>';
						html += '</div>';
	            	});
	            	
	            	$('#roomReservedList').html(html);
	            	updateReserveButtons();
	            }
	        });
	    } else {
	    }
	  
	}
	
	// 페이지 로드시 플랫피커 onChange가 바로시작안되서 (박) 표시가 안보임, 직접 함수 생성 후 페이지 로드시에 호출
	function defaultInputValue(selectedDates) {
		if (selectedDates.length === 2) {
			let nights = (selectedDates[1] - selectedDates[0]) / (1000 * 60 * 60 * 24);
			let dateStr = flatpickr.formatDate(selectedDates[0], "m.d") + " ~ " + flatpickr.formatDate(selectedDates[1], "m.d");
			dateRange.value = dateStr + " · " + nights + "박";
		}
	}
	
	// 예약버튼에 data-is-reserved로 저장해놨는데, 저장되있는 item.is_reserved 값으로 버튼 변경
	function updateReserveButtons(){
		$('.roomListReserveBtn').each(function(){
			var is_reserved = $(this).data('is-reserved');
			if (is_reserved == 1){
				$(this).css('background-color', 'darkgray');
				$(this).prop('disabled', true); //porp은 jquery요소의 속성값을 가져옴, disabled 속성을 true로 하면 버튼 비활성화
				
			}else {
		        $(this).prop('disabled', false);
	        }
		});
	}
	
	// 구글지도 초기화
	function initMap() {
		
		const center = { lat: latitude, lng: longitude };
		const map = new google.maps.Map(document.getElementById("map"), {
			zoom: 15,
			center: center,
		});
		
		const marker = new google.maps.Marker({
			position: center,
			map: map,
		});
	}
	
	
	// 페이지로딩이 끝났을 때 실행
	$(function() {
		//객실리스트 ajax로 출력
	    loadData();
	    
	 	// 페이지로드시 onChange 발동안되서 박표시 안되니 직접 만들어서 강제 호출
		defaultInputValue([ checkIn, checkOut ]);
	 	
		// 페이지 로딩 시 이벤트 핸들러 호출
		mediaQuery.addListener(handleMediaQuery);
		handleMediaQuery(mediaQuery);
	}); 
