
$("#subLogin").on('click',function(){
	var username = $("#uName").val();
	var password = $("#uPassword").val();
		$.ajax({
			cache: true,
			type: "POST",
			contentType: "application/json; charset=utf-8",
			url: '/serviceBP/login',
			data: JSON.stringify({
				username: username,
				password:password
			}),
			async: false,
			success: function(data) {
				if (data.message == "success") {
					$("#userName").html(data.data.username);
					var aa = data.data.username;
					window.location.href = "index.html?uuu=" + aa;
					
				} else if(data.message == "error"){
					$("#msg").html('用户名或密码错误');
				}else if(data.message == "fail"){
					$("#msg").html('用户被锁定，请联系管理员');
				}
			}
		});
	
	
 });
 
 
 

