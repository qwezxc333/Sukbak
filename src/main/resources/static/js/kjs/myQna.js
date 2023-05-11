	const qnaBtn = document.querySelector('.qna');
	const complaintBtn = document.querySelector('.complaint');
	const defaultQnaType = qnaBtn.dataset.qnaType; // 기본 qna_type
	
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
			url: '/commonUser/myQna',
			type: 'POST',
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
			        	
			            html += `<div class="qnaList">`;
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

			            html += `</div>`;
			            html += `</div>`;
			        });
		        }
		        
		        $('#myQna').html(html);

		        // .qnaList 요소에 click 이벤트 추가
		        $('.qnaList').on('click', function () {
		        	const content = $(this).find('.qnaContent');
		            const title = $(this).find('.qnaListTitle h2');
	
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
		            
		        });
		    }
		});
	}
	
	// 1:1문의 <-> 불만접수
	function handleClick(event) {
		const qnaType = event.target.dataset.qnaType;
		loadData(qnaType);
	}
	
	qnaBtn.addEventListener('click', handleClick);
	complaintBtn.addEventListener('click', handleClick);
	
	$(document).ready(function() {
		loadData(defaultQnaType);
	});