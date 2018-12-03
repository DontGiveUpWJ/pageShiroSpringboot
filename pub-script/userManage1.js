$(function(){
	var htmladd = $('#tpl-user-add').html();
    //添加用户的js
    $('#user-add').click(function(){
    	dialog({
            title: '添加用户',
            content: htmladd,
            okValue: '确定',  
            ok: function () {
			var uid = $("#uId").val();	
			var uName = $("#pId").val();
			var uEnable = $('input[name="enAbleName"]:checked').val(); 
			var param = {username:uid,password:uName,enable:uEnable};
		$.ajax({
            async: false,
            type: "POST",
            contentType: "application/json; charset=utf-8",
			url: 'http://127.0.0.1:8089/serviceBP/users/add',
			data: JSON.stringify(param),
            dataType: "json",
            success: function (result) {
				if(result.message == "success"){
				alert("添加成功");
				dialog('close');
			}else if(result.message == "fail"){
				alert("用户已存在，请重新输入！");
				
			}else{
				alert("输入错误，请重新输入！");
				dialog('close');
			}
				
				
            },
            error: function () {
            }
        })
			
				
			},	//确定回调
            cancelValue: '取消', 
            cancel: function () {}	//取消回调
    	}).show();
    });
    
    //修改用户的js
	var editTpl = $('#tpl-edit').html();
    $('#table').on('click','.js_edit',function(event){
        event.preventDefault();
    	dialog({
            title: '修改',
            content: editTpl,
            okValue: '确定',
            ok: function () {
                var editForm = $('#edit-form');
            	 var username = editForm.find('.js_userName').val();
                alert(username);
            },	//确定回调
            cancelValue: '取消', 
            cancel: function () {},	//取消回调
            onshow: function(){		//弹窗显示回调
            	var editForm = $('#edit-form');
                editForm.find('.js_userName').val("wj1");
            	editForm.find('.js_pwd').val('1234');
                var username = editForm.find('.js_userName').val();
                if(username == "wj"){
                    editForm.find('[name="radio-box_3"][value="'+ 1 +'"]')[0].checked = true;
                }else{
                    editForm.find('[name="radio-box_3"][value="'+ 0 +'"]')[0].checked = true;
                }
            	
            }
    	}).show();
    });
    
    //查看用户的js
  var lookTpl = $('#tpl-look').html();
    $('#table').on('click','.js_look',function(event){
        event.preventDefault();
    	dialog({
            title: '查看',
            content: lookTpl,
            okValue: '确定',
            ok: function () {
                var editForm = $('#edit-form');
            	 var username = editForm.find('.js_userName').val();
                alert(username);
            },	//确定回调
            cancelValue: '取消', 
            cancel: function () {},	//取消回调
            onshow: function(){		//弹窗显示回调
            	var editForm = $('#edit-form');
                editForm.find('.js_userName').val("董开旺");
            	editForm.find('.js_pwd').val('董开旺是笨蛋');
                var username = editForm.find('.js_userName').val();
                if(username == "wj"){
                    editForm.find('[name="radio-box_3"][value="'+ 1 +'"]')[0].checked = true;
                }else{
                    editForm.find('[name="radio-box_3"][value="'+ 0 +'"]')[0].checked = true;
                }
            	
            }
    	}).show();
    });
    
    //查看用户角色信息
	var html = $('#tpl-fenpei').html();
    $('#user-fenpei').click(function(){
		
	var id = $(".checkchild:checked").val();
	var p = {
		id: id
	};
	if ($(".checkchild:checked").length < 1) {
		alert("请选择一条数据3");
		return;
	}
	if ($(".checkchild:checked").length > 1) {
		alert("一次只能修改一条数据");
		return;
	}

	
	dialog({
            title: '角色信息',
            content: html,
            okValue: '确定',
            ok: function () {
             
				var e = $('#findUserRole');
				var roleids = $('input[name="roleid"]'); 
				var userId =  e.find('#userId').val();
				/**var roleids = e.find('[name="roleid"]').val();**/
                var roleIds = new Array();
                for (var i = 0; i < roleids.length; i++) {
                    if (roleids[i].checked) {
                        roleIds.push(roleids[i].value);
                    }

                }
                $.ajax({
                    cache: true,
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    url: 'http://127.0.0.1:8089/serviceBP/users/saveUserRoles',
                    data: JSON.stringify({
                        roleid: roleIds.join(","),
                        userid: userId
                    }),
                    // 你的formid
                    async: false,
                    success: function(data) {
                        if (data.message == "success") {
                            alert("保存成功");
							window.location.reload();
                           
                        } else {
                            alert("保存失败");
                            window.location.reload();
                        }
                    }
                })
            
			  
			  
			  
			  
			  
			  
            },	//确定回调
            cancelValue: '取消', 
            cancel: function () {},	//取消回调
            onshow: function(){		//弹窗显示回调
            $.ajax({
				async: false,
				type: "POST",
				contentType: "application/json; charset=utf-8",
				data: JSON.stringify(p),
				url: 'http://127.0.0.1:8089/serviceBP/roles/rolesWithSelected',
				dataType: 'json',
				success: function(data) {
					$("#findUserRole").empty();
					var htm = "<input type='hidden'  id = 'userId' name='userid' value='" + id + "'>";
					for (var i = 0; i < data.data.length; i++) {
						htm += "<div class='checkbox'><label><input type='checkbox' id = 'roleId' name='roleid' value='" + data.data[i].id + "'";
						if (data.data[i].selected == 1) {
							htm += " checked='checked'";
						}
						htm += "/>" + data.data[i].roledesc + "</label></div>";
					}
					$("#findUserRole").append(htm);
				}
			});	
            	
            }
    	}).show();

    });
    
	
	
	
	
    
    
   
});


