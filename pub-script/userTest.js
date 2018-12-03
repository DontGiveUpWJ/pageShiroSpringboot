 //根据用户查询用户的角色
            function findUserRole() {
                var id = $(".checkchild:checked").val();
                alert(id);
                var p = {
                    id: id
                };
                if ($(".checkchild:checked").length < 1) {
                    alert("请选择一条数据");
                    return;
                }
                if ($(".checkchild:checked").length > 1) {
                    alert("一次只能修改一条数据");
                    return;
                }

                $.ajax({
                    async: false,
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(p),
                    url: '/serviceBP/roles/rolesWithSelected',
                    dataType: 'json',
                    success: function(data) {
                        $("#boxRoleForm").empty();
                        var htm = "<input type='hidden'  id = 'userId' name='userid' value='" + id + "'>";
                        for (var i = 0; i < data.data.length; i++) {
                            htm += "<div class='checkbox'><label><input type='checkbox' id = 'roleId' name='roleid' value='" + data.data[i].id + "'";
                            if (data.data[i].selected == 1) {
                                htm += " checked='checked'";
                            }
                            htm += "/>" + data.data[i].roledesc + "</label></div>";
                        }
                        $("#boxRoleForm").append(htm);
                    }
                });

                $('#selectRole').modal();

            }
            //添加用户弹窗
    function addUserModal() {
        $('#addUser').modal();
    }
    //添加用户
    function addUser() {
        var username = $("#usernameId").val();
        var password = $("#pId").val();
        var enable = $("#eId").val();
        var addParam = {
            "username": username,
            "password": password,
            "enable": enable
        }
        if (username == "" || username == undefined || username == null) {
            alert("用户名不能为空！");
            return;
        }
        if (password.length < 6 || password.length >= 16) {
            alert("密码长度为6-16");
            return;
        }
        $.ajax({
            cache: true,
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: '/serviceBP/users/add',
            /* data:$('#userForm').serialize(),// 你的formid */
            data: JSON.stringify(addParam),
            // 你的formid
            async: false,
            success: function(data) {
                if (data.message == "success") {
                    alert("保存成功");
                    $('#addUser').modal('hide');
                    $("#submit").trigger("click");
                } else if (data.message = "fail") {
                    alert("该用户已存在");
                    $('#addUser').modal('hide');
                } else {
                    alert("保存失败");
                    $('#addUser').modal('hide');
                }
            }
        });
    }
            //删除
            function delByID() {
                var id = $(".checkchild:checked").val();
                alert(id);
                if ($(".checkchild:checked").length < 1) {
                    alert("请选择一条数据");
                    return;
                }
                if ($(".checkchild:checked").length > 1) {
                    alert("一次只能修改一条数据");
                    return;
                }
                if (confirm("确定删除吗")) {
                    alert("ok");
                    $.ajax({
                        cache: true,
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        url: '/serviceBP/users/delete',
                        data: JSON.stringify({
                            id: id
                        }),
                        async: false,
                        success: function(data) {
                            if (data.message == "success") {
                                alert('删除成功');
                                $("#submit").trigger("click")
                            } else {
                                alert('删除失败');
                            }
                        }
                    });
                } else {
                    alert("false");
                }

            }
            //删除
            //修改回显
            function findUserById() {

                $("#upusername").val("1");
                $("#passwordId").val("2");
                var id = $(".checkchild:checked").val();
                if ($(".checkchild:checked").length < 1) {
                    $('#updateUser').modal('hide');
                    alert("请选择一条数据");
                    return;
                }
                if ($(".checkchild:checked").length > 1) {
                    alert("一次只能修改一条数据");
                    return;
                }
                $('#updateUser').modal('show');
                ////
                var findUserByUid = {
                    id: id
                };
                
                $.ajax({
                    async: false,
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(findUserByUid),
                    url: '/serviceBP/users/findUserByUid',
                    dataType: 'json',
                    success: function(data) {
                        /* alert("11" + data.username); */
                        $("#uuId").val(data.data.id);
                        $("#upusername").val(data.data.username);
                        $("#passwordId").val(data.data.password);
                        $("#enableIdd").val(data.data.enable);

                    }
                });
            }

            //保存角色功能
            function saveUserRoles() {
                var roleids = document.getElementsByName("roleid");
                var userId = $("#userId").val();
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
                    url: '/serviceBP/users/saveUserRoles',
                    data: JSON.stringify({
                        roleid: roleIds.join(","),
                        userid: userId
                    }),
                    // 你的formid
                    async: false,
                    success: function(data) {
                        if (data.message == "success") {
                            alert("保存成功");
                            $('#selectRole').modal('hide');
                        } else {
                            alert("保存失败");
                            $('#selectRole').modal('hide');
                        }
                    }
                })
            }

            //修改用户2222222
            function updateUser() {
                alert("11");
                var username = $("#upusername").val();
                var password = $("#passwordId").val();
                var enable = $("#enableIdd").val();
                var id = $("#uuId").val();
                var upParam = {
                    "id": id,
                    "username": username,
                    "password": password,
                    "enable": enable
                }
                if (username == "" || username == undefined || username == null) {
                    alert("用户名不能为空");
                    return;

                }
                if (password.length < 6 || password.length >= 16) {
                    alert("密码长度为6-16");
                    return;
                }

                if (enable == "" || enable == undefined || enable == null) {
                    alert("用户状态不能为空");
                    return;
                }

                $.ajax({
                    cache: true,
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    url: '/serviceBP/users/update',
                    data: JSON.stringify(upParam),
                    // 你的formid
                    async: false,
                    success: function(data) {
                        if (data.message == "success") {
                            alert("保存成功");
                            $("#submit").trigger("click");
                            $('#updateUser').modal('hide');
                        } else if (data.message == "error") {
                            alert("该用户已存在");
                            $('#updateUser').modal('hide');
                        } else {
                            alert("保存失败");
                            $('#updateUser').modal('hide');
                        }
                    }
                });
            }
            //222222222
            