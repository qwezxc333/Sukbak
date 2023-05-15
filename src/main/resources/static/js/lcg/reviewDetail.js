
function checkLength(index) {
	  // 텍스트에리어의 값을 읽음
	  const textarea = $("#del_reason"+index).val();
	  // <span id = "wordCount">0</span>의 값을 읽음.
	  // 모달 별로 값이 달라야하기 때문에 인덱스를 부여해줘야 함.
	  const wordCount = $("#wordCount"+index);
	  // wordCount.text에 textarea.length값을 덮어 씌워줌.
	  wordCount.text(textarea.length+'/150');
	  // 길이를 150으로 하면 151까지 써져가지고 149로 했음.
	  if (textarea.length > 149) {
		    alert("글자 제한이 넘어갔습니다.");
		  }
	}

	function chk(index){
		// 띄어쓰기까지 없애기 trim()으로 스페이스바 만 눌러도 통과되는걸 방지.
		var del_reason = $("#del_reason"+index).val().trim();
		if(del_reason == "" || del_reason == null){
			alert("내용을 입력해주세요");
			return false;
		}
		alert("접수되었습니다.");
		return true;
	} 
	
	
	function cgGetAjaxSortingReviewLists(biz_id, kind){
		var kind = kind;
		var biz_id = biz_id;
		
		$.ajax({
			url : "/cgGetAjaxSortingReviewLists",
			data : {
				"kind" 	 : kind,
				"biz_id" : biz_id
			},
			dataType : "json",
			success : function(reviewLists){
				var str = "";
				$(reviewLists).each(function(index){
					var date = new Date(this.review_date);
					var year = date.getFullYear();
					// +1 안하면 저번달이 출력됨 이번달로 하고싶으면 +1로 할것.
					// 문자 0을 더하지 않으면 08일이 아니라 8 이라고만 나와서 좀 헷갈림.
					var month = ("0"+(date.getMonth()+1));
					var day = ("0"+date.getDate());
					var formatDate = year+"-"+month+"-"+day;
					
			
					
					str += "<div class = 'accomReview'>"
					str += "<div class = 'accomReviewNicknameAndDate'>";
					str += "<span id='reviewBizName'>" + this.biz_name + "</span><p>";
					str += "<span>" +this.nickname + "님 | </span>";
					str += "<span>" +this.del_request_count + "님 | </span>";
					str += "<span>" +formatDate+"</span>";
					str += "</div>";
					str += "<div class = 'accomReviewRoomName'>";
					str += "<p>" + this.r_name +" 객실</p>";
					str += "</div>";
					str += "<div class = 'reviewContentline'>";
					str += "<hr id ='ReviewContentline'>";
					str += "</div>";
					str += "<div class='accomReviewRating'>";
					str += "<span>";
					for (var i = 1; i <= this.rating; i++){
						str += "<img src = /img/star.png>";
					}
					str += "</span>";
					str += "</div>"
					str += "<div class = 'accomReviewRoomContent'>";
					str += "<p>" + this.review_content +"</p>";
					str += "</div>";
					str += "<div class = 'accomReviewRoomImg'>";
					$(this.reviewImages).each(function(){
					    if (this.review_img) { // 이미지가 있을 경우에만 img 태그 추가
					        str += "<img src = '"+this.review_img + "'class='img-thumbnail' onerror='this.style.display='none';'>";
					    }
					});
					str += "<div class='ReviewDeleteBtn'>";
					str += "<div>";
					if (this.del_request == 'N'){
						str += "<button type='button' id='modalOpenBtn' class='btn btn-primary' data-bs-toggle='modal' data-bs-target='#staticBackdrop" + index + "'>삭제요청</button>";
					}
					str += "</div>";
					str += "<div>";
					if (this.del_request == 'Y'){
					str += "<button type='button' id='modalDoneBtn' class='btn btn-secondary' disabled>삭제접수 완료</button>";
					}
					str += "</div>";
					if (this.del_request == 'R'){
					str += "<button type='button' id='deleteRefuseBtn' class='btn btn-danger' disabled>삭제요청이 거부되었습니다. 자세한 문의는 고객센터에 연락주세요</button>";
					}
					str += "</div>";
					
					str += "<div class='modal fade' id='staticBackdrop" + index + "' data-bs-backdrop='static' data-bs-keyboard='false' tabindex='-1' aria-labelledby='staticBackdropLabel' aria-hidden='true'>";
					str += "<div class='modal-dialog'>";
					str += "<div class='modal-content'>";
					str += "<form action='/biz/reviewDeleteRequest' method='post' name='frm' onsubmit='return chk(" + index + ")'>";
					str += "<div class='modal-header'>";
					str += "<h1 class='modal-title fs-5' id='staticBackdropLabel'>리뷰삭제요청</h1>";
					str += "<span id='modal-notice'>(리뷰삭제는 관리자가 판단하고 삭제하므로 5일 이상 소요됩니다.)</span>";
					str += "<button type='button' class='btn-close' data-bs-dismiss='modal' aria-label='Close'></button>";
					str += "</div>";
					str += "<div class='modal-body'>";
					 
					str += "<textarea name='del_reason' onkeyup='checkLength("+index+")' id='del_reason" + index + "' rows='10' cols='100' maxlength='200' placeholder='150자까지 작성 가능합니다.' style='resize: none;'></textarea><p>";
					str += "<div class = 'modalWordCount'>";
					str += "<span id = 'wordCount"+index+"'>0/150</span>";
					str += "</div>";
					str += "<input type='hidden' name='pay_id' id= 'pay_id' value='" + this.pay_id + "'>";
					str += "<input type='hidden' name='biz_id' id= 'biz_id' value='" + this.biz_id + "'>";
					str += "<input type='hidden' name='del_request' id= 'del_request' value='" + this.del_request + "'>";
					str += "<input type='hidden' name='kind' id= 'kind' value='" + kind + "'>";
					str += "</div>";
					str += "<div class='modal-footer'>";
					str += "<button type='button' class='btn btn-secondary' data-bs-dismiss='modal'>닫기</button>";
					str += "<button type='submit' class='btn btn-primary'>삭제요청</button>";
					str += "</div>";
					str += "</form>";
					str += "</div>";
					str += "</div>";
					str += "</div>";
					str += "</div>";
					str += "</div>";
					str += "</div>";
					str += "</div>";
				});
				//alert("ajax str-> "+str);
				$(".accomReviewContainer").html(str);
			}
		}) 
	}
	
	