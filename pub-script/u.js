$(document).ready(function () {
	alert("1111");

var addParam = {id:1};
	 $.ajax({
                    cache: true,
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    url: 'http://127.0.0.1:8089/serviceBP/users',
                    /* data:$('#userForm').serialize(),// 你的formid */
                    data: JSON.stringify(addParam),
                    // 你的formid
                    async: false,
                    success: function(data) {
                       alert("2222");
                    }
                });
	
	
});

