	// 메세지 div 생성
    function addMessage(text, position) {
        const messageDiv = document.createElement('div');
	        messageDiv.classList.add('message');
	        messageDiv.classList.add(position);
	        messageDiv.textContent = text;
        
        if(position === 'left'){
        	const img = document.createElement('img');
        	img.src = '/img/penguin2.png';
        	img.classList.add('left-penguin-img');
        	messageDiv.appendChild(img);
        	
        	const nameSpan = document.createElement('span');
        	nameSpan.textContent = '헹귄이';
        	nameSpan.classList.add('top-penguin-name');
        	messageDiv.appendChild(nameSpan);
        }
        
        document.getElementById('chat').appendChild(messageDiv);
     
        scrollToBottom();
    }
	
	// text:텍스트, messageType:대분류, notice_id:제목, reset:대분류 다시호출 , redirectpage:공지사항페이지 이동
    function addButtonMessage(text, messageType, notice_id, reset, redirectPage) {
		
        const messageDiv = document.createElement('div');
        messageDiv.classList.add('message');
        messageDiv.classList.add('right');

        const button = document.createElement('button');
        button.classList.add('button-message');
        button.textContent = text;
        
        if(messageType){
        	button.onclick = () => {
        		if(messageType === 'qna'){
        			getQnaForm(messageType);
        			buttonClicked(button);
        		}else{
	        		getNoticeFaqTitle(messageType);
	        		buttonClicked(button);
        		}
        	}
        }else if(notice_id){
        	button.onclick = () => {
        		getNoticeFaqContent(notice_id);
        		buttonClicked(button);
        	}
        }else if(reset){
        	button.onclick = () => {
        		resetToInitialState(false);
        		buttonClicked(button);
        	}
        }else if(redirectPage){
        		button.onclick = () => {
        		location.href = '/noti?note_id=0';
        	}
        }
        
        messageDiv.appendChild(button);
        document.getElementById('chat').appendChild(messageDiv);
        
        scrollToBottom();
    }
	
    // notice 제목 출력
    function getNoticeFaqTitle(messageType) {
        let value;

        if (messageType === 'reservation') {
            addMessage('예약 문의', 'left');
            value = 411;
        } else if (messageType === 'payment') {
            addMessage('결제 문의', 'left');
            value = 412;
        } else if (messageType === 'users') {
            addMessage('회원 문의', 'left');
            value = 413;
        }else if (messageType === 'review') {
            addMessage('리뷰 문의', 'left');
            value = 414;
        }else if (messageType === 'qna') {
        	addMessage('1대1 문의', 'left');
        }
        
        $.ajax({
        	url: '/noticeFaqTitle',
        	type: 'POST',
        	data: {
        		notice_type: value
        	},
        	success: data => {
        		console.log("getNoticeFaqTitle data->", data);
        		
        		data.forEach( item => {
	        		addButtonMessage("Q. " + item.notice_title, undefined, item.notice_id);
        			console.log("item.notice_title->", item.notice_title);
        		});
        		/* addButtonMessage('이전 페이지로!', undefined, undefined, true); */
        	}
        });
    }
	
 	// notice content 출력
    function getNoticeFaqContent(notice_id) {
        $.ajax({
            url: '/noticeFaqContent',
            type: 'POST',
            data: {
                notice_id: notice_id
            },
            success: data => {
                console.log("getNoticeFaqContent data->", data);
                
                data.forEach( item => {
	                addMessage(item.notice_content, "left");
	                console.log("item.notice_content->",item.notice_content)
                });
                
                // 처음페이지 이동 버튼
                addButtonMessage('처음 메뉴로!', undefined, undefined, true);
                // 공지사항 페이지 이동
                addButtonMessage('자세항 사항은 여기!', undefined, undefined, undefined, true);
            }
        });
    }
 
 	// 1대1문의 Form 출력
 	function getQnaForm() {
 		//아이디 출력
 		const loginUserId = document.getElementById('loginUserId').textContent;
 		
 		const form = document.createElement('form');
 		
 		const labelTitle = document.createElement('label');  // 제목 레이블 생성
 		const inputTitle = document.createElement('input');
 		
 		const labelContent = document.createElement('label');  // 내용 레이블 생성
 		const inputContent = document.createElement('textarea');
 		
 		const requiredMark = document.createElement('span');
 		
 		const qnaBtn = document.createElement('button');
 		
 		form.classList.add('qnaForm');
 		requiredMark.classList.add('requiredMark');
 		requiredMark.textContent = '*';
 		qnaBtn.classList.add('qnaBtn');
 		
 		inputTitle.type = 'text';
 		inputTitle.id = 'titleInput';  // 제목 id 설정
 		inputTitle.placeholder = '제목을 입력해주세요.';
 		labelTitle.setAttribute('for', 'titleInput');  // 제목 레이블과 제목input id 연결
 		labelTitle.textContent += '제목';
 		
 		inputContent.type = 'text';
 		inputContent.id = 'contentInput';  // 내용 id 설정
 		inputContent.placeholder = '문의 내용을 입력해주세요.';
 		labelContent.setAttribute('for', 'contentInput');  // 내용 레이블과 내용input id 연결
 		labelContent.textContent += '문의 내용';
 		
 		qnaBtn.textContent = '저장';  //버튼

 		form.appendChild(labelTitle);  // 제목 레이블 추가
 		form.appendChild(inputTitle);
 		form.appendChild(labelContent);  // 내용 레이블 추가
 		form.appendChild(inputContent);
 		form.appendChild(qnaBtn);
 		
 		//label 뒤에 * 추가, cloneNode는 appenChild하면 요소 이동 시에 처음꺼 삭제되서 1개 요소로 쓸거면 cloneNode해줘야 함
 		labelTitle.appendChild(requiredMark.cloneNode(true));
 		labelContent.appendChild(requiredMark.cloneNode(true));
		
 		//#chat에 폼 추가
 		document.getElementById('chat').appendChild(form);
 		
 		scrollToBottom();
 		
 		//입력 안할 시 inInput 추가,제거
 		inputTitle.addEventListener('input', updateBtn);
		inputContent.addEventListener('input', updateBtn);
 		
 		function updateBtn() {
 			if (inputTitle.value && inputContent.value) {
 				qnaBtn.classList.remove('isInput');
 				qnaBtn.classList.remove('qnaBtnIsInput');
 				qnaBtn.classList.add('qnaBtn');
 				qnaBtn.disabled = false;
 			} else{
 				qnaBtn.classList.add('isInput');
 				qnaBtn.classList.add('qnaBtnIsInput');
 				qnaBtn.classList.remove('qnaBtn');
 				qnaBtn.disabled = true;
 			}
 		}
 		
 		//버튼 클릭 시 insert요청
 		qnaBtn.addEventListener('click', (e) => {
 			//새로고침 X
 			e.preventDefault();
 			
 			const inputTitleValue = inputTitle.value;
 			const inputContentValue = inputContent.value;
 			
 			//로그인 했을때
 			if(loginUserId && loginUserId != "anonymousUser"){
 				//제목, 내용 둘다 입력 했을때
 				if(inputTitleValue && inputContentValue){
	 				qnaBtn.textContent  = '제출 완료';
		 			qnaBtn.classList.add('submitted');
		 			inputTitle.disabled = true;
		 			inputContent.disabled = true;
		 			
		 			//insertQna 호출
		 			insertQna(inputTitleValue, inputContentValue);
 				}
 			}else{
 				alert('로그인 후 이용해 주세요.');
 			}
 			
 		});
 		
 		//페이지 로드시에 Form호출될 때 실행되게 하려고 추가
 		updateBtn();
 	}
 	
 	// 1대1문의 입력
 	function insertQna(inputTitleValue, inputContentValue){
 		$.ajax({
 			url: '/qnaInsert',
 			type: 'POST',
 			data: {
 				qna_title : inputTitleValue,
 				qna_content : inputContentValue
 			},
 			success: data => {
 				
 				addMessage('문의 작성 완료!','left');
 				// 처음페이지 이동 버튼
                addButtonMessage('처음 메뉴로!', undefined, undefined, true);
                // 공지사항 페이지 이동
                addButtonMessage('자세항 사항은 여기!', undefined, undefined, undefined, true);
 			}
 		});
 	}
 
    //스크롤처리
    function scrollToBottom(){
    	const chatDiv = document.getElementById('chat');
	    chatDiv.scrollTop = chatDiv.scrollHeight;
    }
    
    //css처리 , 클릭한 버튼 clicked class추가
    function buttonClicked(button){
    	//모든 버튼 clicked 클래스 제거
    	const buttons = document.querySelectorAll('.button-message');
    	buttons.forEach(btn => {
    		btn.classList.remove('clicked');
    	});
    	
    	//클릭하면 clicked 클래스 추가
    	button.classList.add('clicked');
    }
    
	// 초기 메시지 설정
    function resetToInitialState(showInitialMessage = true){
    	/* const chatDiv = document.getElementById('chat');
        chatDiv.innerHTML = ''; */
        
        if(showInitialMessage){
		    addMessage('안녕하세요. 헹귄이에요. 무엇을 도와드릴까요?', 'left');
        }
	    
        if(!showInitialMessage){
	    	addMessage('처음 메뉴로 이동!', 'left');
	    }
        
	    addButtonMessage('예약 문의', 'reservation');
	    addButtonMessage('결제 문의', 'payment');
	    addButtonMessage('회원 문의', 'users');
	    addButtonMessage('리뷰 문의', 'review');
	    addButtonMessage('1대1문의 작성', 'qna')
	    
    }
    
	//현재시간
	function currentTime(){
		const currentTimeDiv = document.querySelector('.currentTime');
		const now = new Date();
		const hours = now.getHours();
		const minutes = now.getMinutes().toString().padStart(2, '0');
		const ampm = hours >=12 ? '오후' : '오전';
		currentTimeDiv.textContent = `${ampm} ${hours}:${minutes}`;
	}
	setInterval(currentTime, 1000);
	
	//페이지 로딩 시
    $(document).ready(function() {
    	
    	const chatDiv = document.querySelector('#chat');
    	const chatBtn = document.querySelector('.penguinBot');
    	const chatClose = document.querySelector('.btn-close');
    	
    	//채팅 초기값 셋팅
    	resetToInitialState();
    	
    	//페이지로딩 시 채팅 숨김
    	chatDiv.style.visibility = 'hidden';
    	
    	//시간표시
    	currentTime();
    	
    	
    	
    	//헹귄봇 클릭 시
    	chatBtn.addEventListener('click', () => {
    		if(chatDiv.style.visibility === 'hidden'){
    			chatDiv.style.visibility = 'visible';
    			chatDiv.style.opacity = '1';
    			chatBtn.style.opacity = '0';
    		}
    	});
    	
    	//X표시 누를 시
    	chatClose.addEventListener('click', () => {
    		if(chatDiv.style.visibility === 'visible'){
    			chatDiv.style.visibility = 'hidden';
    			chatDiv.style.opacity = '0';
    			chatBtn.style.opacity = '1';
    		}
    	});
    });