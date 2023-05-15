// may public using

	function goBack() {
			var a = confirm("작성한 내용은 저장되지 않습니다. 사업자 메인 페이지로 이동합니다.")
			if (a) window.location.href = "/biz/bizMain";
			else return false;
		}
		
	function actAlert() {
			alert("수정이 완료되었습니다")
		}
	
	
	function goBackNonAlert() {
		window.location.href = "/biz/bizMain";
		}
		
	function goBackRooms() {
		window.location.href = "/biz/rooms";
		}

//bizMain Only

	function chkHidden() {
			var a = confirm("업체가 숨김 처리되어 고객에게 노출되지 않습니다. 진행하시겠습니까?")
			if (a) return true;
			else return false;
		}
		
	function chkOpen() {
			var a = confirm("업체가 공개되어 고객에게 노출됩니다. 진행하시겠습니까?")
			if (a) return true;
			else return false;
		}
		
	function chkDelete() {
			var a = confirm("업체가 삭제되어 마이페이지와 고객에게 노출되지 않습니다. 진행하시겠습니까?")
			if (a) return true;
			else return false;
		}
	
// imgInsert

	function actAlertImgInsert() {
		var biz_id = document.getElementById("accombiz_id").value
		document.getElementById("biz_id").value = biz_id;
		var a = confirm("객실 정보 등록을 완료하고 이미지등록을 하시겠습니까?")
		if (a) return true;
		else return false;
	}

// roomSelectForm

	function chkDelete() {
		var a = confirm("객실이 삭제되어 마이페이지와 고객에게 노출되지 않습니다. 진행하시겠습니까?")
		if (a) return true;
		else return false;
	}	
	
	function chkUpdateRoom() {
		var a = confirm("객실 수정 페이지로 이동합니다.")
		if (a) return true;
		else return false;
	}

// rooms Only

	function chkHiddenRoom() {
		var a = confirm("객실이 숨김 처리되어 고객에게 노출되지 않습니다. 진행하시겠습니까?")
		if (a) return true;
		else return false;
	}
	
	function chkOpenRoom() {
		var a = confirm("객실이 공개되어 고객에게 노출됩니다. 진행하시겠습니까?")
		if (a) return true;
		else return false;
	}
	
	function chkDeleteRoom() {
		var a = confirm("객실이 삭제되어 마이페이지와 고객에게 노출되지 않습니다. 진행하시겠습니까?")
		if (a) return true;
		else return false;
	}
	
// addr Finds

	function accomAddrGetForm() {
		window.open("/biz/accomAddrGetForm", "_blank", "menubar=no, toolbar=no");
	}
	
	window.addEventListener('message', function(event) {
		  // 자식뷰에서 전달한 데이터를 가져와 화면에 출력
		  document.getElementById('addr').value = event.data.addr;
		  document.getElementById('latitude').value = event.data.latitude;
		  document.getElementById('longitude').value = event.data.longitude;
		});

// 사업자등록번호 중복체크 accomInsert

	function biz_idChk() {
    var biz_id = document.getElementById("biz_id").value
    if (biz_id == "") {
        alert("사업자등록번호를 입력해 주세요")
        return false;
    } else {
        fetch('/biz/checkBizId?biz_id=' + biz_id)
            .then(response => response.text())
            .then(result => {
                if (result == "duplicate") {
                    alert("이미 등록된 사업자등록번호입니다.")
                    document.getElementById("biz_id").value = "";
                    document.getElementById("biz_idChecked").value = 0;
                } else if (result == "nonMatched") {
                    alert(" -(하이픈) 를 포함한 10자리 사업자 등록번호를 입력해 주세요")
                    document.getElementById("biz_id").value = "";
                    document.getElementById("biz_idChecked").value = 0;
                } else {
                    alert("사용 가능한 사업자등록번호입니다.")
                    document.getElementById("biz_idChecked").value = 1;
                }
            })
            .catch(error => console.log('error', error));
    }
}
	
	function accomImgInsert() {
			var biz_id = document.getElementById("accombiz_id").value
			document.getElementById("biz_id").value = biz_id;
			var a = confirm("객실 정보 등록을 완료하고 이미지등록을 하시겠습니까?")
			if (a) return true;
			else return false;
		}
	function accomValueChk() {
		var biz_idChecked = document.getElementById("biz_idChecked").value
		if (biz_idChecked == 0 ) {
			alert('사업자등록번호 중복확인이 필요합니다.')
			focus("biz_id")
			return false;
		}
		return true;
		}