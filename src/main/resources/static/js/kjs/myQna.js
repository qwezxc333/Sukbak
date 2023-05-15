	const qnaBtn = document.querySelector('.qna');
	const complaintBtn = document.querySelector('.complaint');
	let defaultQnaType = qnaBtn.dataset.qnaType; // 기본 qna_type
	
	const qnaUpdateBtn = document.querySelector('.qnaUpdateBtn');
	const qnaDeleteBtn = document.querySelector('.qnaDeleteBtn');
	
	function formatDate(nowDate) {
		const date = new Date(nowDate);
		const year = date.getFullYear();
		const month = String(date.getMonth() + 1).padStart(2, '0');
		const day = String(date.getDate()).padStart(2, '0');
		
		return `${year}-${month}-${day}`;
	}
	
	//1:1문의 , 불만접수 list
	function loadData(qnaType) {

		$.ajax({
			url: '/commonUser/myQnaAjax',
			type: 'GET',
			data: {
				qna_type : qnaType
			},
		    success: data => {
		    
		        let html = "";
		        
				// 1:1문의사항 or 불만접수가 없다면, 
		        if (data.length === 0) {
		        	html += `<div class="no-content">`;
		        	html += `<img src="/img/penguin_oops.png" alt="no-content">`;
		        	html += `<p>현재 문의사항이 없습니다.
		        				  	<br>문의를 남기시려면 더보기의 1:1문의/불만접수 이동 
		        				  	<br>또는, <a href="/noti?note_id=1">여기<a/>를 클릭하세요.
		        				  </p>`;
		        	html += `</div>`;
		        } else {
	        		data.forEach(item => {
			        	
			            html += `<div class="qnaList" data-qna-id="${item.qna_id}">`;
			            html += `<div class="qnaListTitle"><h2> Q. ${item.qna_title}</h2></div>`;
			            html += `<div class="qnaListDate"><span>${formatDate(item.qna_date)}</span></div>`;

			            if (item.reply) {
		                    html += `<div class="qnaReReply"><span>답변완료</span></div>`;
		                    html += `<div class="qnaContent" style="display:none;">`;
		                    html += `<div class="qnaListContent"><p>${item.qna_content}</p></div>`;
		                    html += `<hr>
		                        <div class="qnaReListContent">
		                            <p>
		                                <span> A. </span>
		                                ${item.reply}
		                            </p>
		                        </div>`;
		                    html += `<div class="qnaReListDate"><span>${formatDate(item.qna_re_date)}</span></div>`;
		                } else {
		                    html += `<div class="qnaReNoReply"><span>미답변</span></div>`;
		                    html += `<div class="qnaContent" style="display:none;">`;
		                    html += `<div class="qnaListContent"><p>${item.qna_content}</p></div>`;
		                }
						
						html += `<div class="qnaBtn-container">`;
						html += `<button class="qnaUpdateBtn">수정</button>`;
	                    html += `<button class="qnaDeleteBtn">삭제</button>`;
	                    html += `</div>`;
						
			            html += `</div>`;
			            html += `</div>`;
			        });
		        }
		        
		        $('#myQna').html(html);

		        // .qnaList 요소에 click 이벤트 추가
		        $('.qnaList').on('click', handleSlideToggle);
		    }
		});
	}
	
	// 슬라이드 토글
	function handleSlideToggle(){
        const title = $(this).find('.qnaListTitle h2');
		const content = $(this).find('.qnaContent');

        content.slideToggle(); // -> jQuery method
        
        if (title.css('text-overflow') === 'ellipsis') {
        	title.css({
        		'white-space': 'normal',
        		'text-overflow': 'clip'
        	});
        } else{
        	title.css({
            	'white-space': 'nowrap',
        		'text-overflow': 'ellipsis'
        	});
  		}
	}
	
	// 1:1문의 <-> 불만접수
	function handleClick(event) {
		const qnaType = event.target.dataset.qnaType;
		loadData(qnaType);
		
		//defaultQnaType 업데이트
		defaultQnaType = qnaType; 
	}
	
	// qna 수정
	$(document).on('click', '.qnaUpdateBtn', function(event) {
		
		let qnaList = $(this).closest('.qnaList');
	    let qnaId = qnaList.data('qna-id');
	    let qnaReReply = qnaList.find('.qnaReReply');
	    
	    let qnaTitleElement = qnaList.find('.qnaListTitle h2');
	    let qnaContentElement = qnaList.find('.qnaListContent p');
	    
	    let qnaTitle = qnaTitleElement.text();
	    let qnaContent = qnaContentElement.text();
	    
	    let content = qnaList.find('.qnaContent');
	    
	    // "답변완료" 요소 없을때만 수정가능
	    if(!qnaReReply.length) {
	    	// 텍스트 필드 -> 입력 필드 변경
			qnaTitleElement.html(`<input type="text" id="updateTitle" value="${qnaTitle.substring(4)}"/>`);
			qnaContentElement.html(`<textarea id="updateContent">${qnaContent}</textarea>`);
			
			//수정 -> 저장버튼 변경
			let saveBtn = $(`<button class="qnaSaveBtn">저장</button>`);
	    	$(this).replaceWith(saveBtn);
	    	
	    	// 토글 Off
	    	qnaList.off('click', handleSlideToggle);
	    	
	    	// 내용 접혀있는 경우 펼치기
	    	content.slideDown();
	    	
	    	// 저장버튼 눌렀을 때 
	    	$('.qnaSaveBtn').on('click', function(event) {
	    		
	    		// 수정된 input값의 제목과 내용 가져오기
	    		let updatedTitle = $('#updateTitle').val();
	        	let updatedContent = $('#updateContent').val();
	    		
	    		$.ajax({
	    			url: '/commonUser/myQnaUpdate',
	    			type: 'GET',
	    			data: {
	    				qna_id : qnaId,
	    				qna_title: updatedTitle,
	    				qna_content: updatedContent
	    			},
	    			success: data => {
	    				alert("문의글 수정이 완료되었습니다.");
	    				//수정 버튼 변경
	    				$('.qnaSaveBtn').replaceWith(qnaUpdateBtn);
	    			
	    				// 입력필드 -> 텍스트 필드 변경
	    				qnaTitleElement.html(`<h2>${updatedTitle}<h2>`);
	    				qnaContentElement.html(`<p>${updatedContent}</p>`);
	    				
	    				// 수정된 데이터 다시 가져오기
	           		 	loadData(defaultQnaType);
	           		 	
	           		 	// 토글 On
	           		 	qnaList.on('click', handleSlideToggle);
	    			}
	    		});
			});
	    } else{
	    	alert('답변이 등록된 글은 수정이 제한됩니다.');
	    }
	});
	
	// qna 삭제
	$(document).on('click', '.qnaDeleteBtn', function() {
		//const loginUserPw = document.querySelector('loginUserPw');
		let qnaList = $(this).closest('.qnaList');
		let qnaId = qnaList.data('qna-id');
		let userInput = prompt('삭제하시려면 DELETE 를 정확히 입력하세요');
		
		if(userInput === 'DELETE'){
			$.ajax({
				url: '/commonUser/myQnaDelete',
				type: 'GET',
				data: {
					qna_id : qnaId
				},
		    	success: data => {
		    		//화면에서 바로삭제
		    		qnaList.remove();
		    		
		    		// qnaList 없을때 oops표시(loadDate 재호출)
		    		if($('qnaList').length === 0 ) {
		    			loadData(defaultQnaType);
		    		}
		    	}
			});
		} else if(userInput == null){
			//prompt 취소 시 이벤트발생X
		} else{
			alert('삭제에 실패했습니다. DELETE를 정확히 입력해주세요.');
		}
		
	});
	
	qnaBtn.addEventListener('click', handleClick);
	complaintBtn.addEventListener('click', handleClick);
	
	$(document).ready(function() {
		loadData(defaultQnaType);
	});