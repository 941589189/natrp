function login(){
    var uname = $('#inputEmail').val()
    var password = $.trim($('#inputPassword').val())
    $.ajax({
        url: '/key',
        type: "GET",
        success: function (res) {
            var encrypt = new JSEncrypt();//创建加密实例
            if(res.code == '0'){
                var publicKey = res.msg;//后端传回来的公钥
                encrypt.setPublicKey(publicKey) //初始化公钥
                pwd = encrypt.encrypt(password)   //数据加密
                if (uname == null || uname == '') {
                    alert("账号不得为空")
                } else if (pwd == null || pwd == '') {
                    alert("密码不得为空")
                } else {
                    $.ajax({
                        url: '/login',
                        type : "POST",
                        dataType: "json",
                        //@requestparam 使用json,以及这个contentType
                        contentType :'application/x-www-form-urlencoded',
                        data: {
                            "loginName" : uname,
                            "password" : pwd
                        },
                        success: function (res) {
                            console.log(res)
                            if(res.code == '0'){
                                location.href = ('/system/notice/open')
                            }else {
                                alert(res.msg)
                            }
                        }
                    })
                }
            }
        }
    })
}
//点击按钮登录
$('#btn')[0].addEventListener("click",login);
//输入密码后 点击回车 触发 登录按钮click事件
$('#inputPassword').bind('keypress',function (event){
    if(event.keyCode == "13"){
        document.getElementById("btn").click();
    }
})

//register按钮响应
$('#register').click(function () {
    window.open("/system/register/main")
})