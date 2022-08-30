function CheckForm(){
	
	let id = document.getElementById("txtuserid").value;
	let pw = document.getElementById("txtpassword").value;
	
	let idPwCheck = document.getElementById("IdPwCheck");
	let idCheck = document.getElementById("IdCheck");
	let pwCheck = document.getElementById("PwCheck");
	
	idPwCheck.innerHTML = "";
	idCheck.innerHTML = "";
	pwCheck.innerHTML = "";
	
	if(id == "" && pw ==""){
		idPwCheck.innerHTML = "아이디와 비밀번호를 입력하세요.";
		return false;
	}
	else if(id == ""){
		idCheck.innerHTML = "아이디를 입력하세요.";
		return false;
	} 
	else if (pw == ""){
		pwCheck.innerHTML = "비밀번호를 입력하세요.";
		return false;
	}

}