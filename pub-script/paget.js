//function getSendMessageInfo() { 
$(document).ready(function () {   //初始化首页显示                                                                         
var paramObj = {
        "pageNum":"1",
        "pageSize":"2"
    };
    //法律信息
    $.ajax({
        url:  'http://127.0.0.1:8089/serviceBP/users',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json",
        async: false,
        data: JSON.stringify(paramObj),
        success: function (result) {
 
            if (result.message=='success'){
                 if (result.data.list.length>0){
                     addLawInfo(result.data.list);//添加数据
                     changepagelaw(result.data.total,result.data.pages)//分页显示（总条数，总页数）
                 }
            }else {
                alert("出现未知错误")
            }
        },
        error: function (XMLHttpRequest, textStatus) {
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
        }
    })
 
});
/*分页*/
function changepagelaw(total,page) {
  
    $("#totallawNum").html(total)
    Page({
        num:page,               //页码数
        startnum:1,            //指定页码
        elem:$('#pagelaw'),     //指定的元素
        callback:function(n){  //回调函数 n为number类型所以需要转为String类型
            var pageNum = n.toString()
            getSendMessageInfo(pageNum);
        }
    });
}
