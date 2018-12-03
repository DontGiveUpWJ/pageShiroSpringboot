function getCarCheckCode() {

		$.ajax({
			type:"POST",
			url:"/serviceBP/testLogin", 
			dataType:"json",
			success:function(resp) {
				if(resp.message == "success"){
				
					
					alert("登录超时，请重新登录！");
					window.location.href = "login.html";
				}else{
				}
			}
		});
	}
	
window.setInterval(getCarCheckCode,1000*60*20);
