$('#register').click(function (){
    const username = $('#username').val();
    const loginname = $('#loginname').val();
    const password = $('#password').val();
    const password_r = $('#passwordR').val();
    if(username === '' || username == null || username.length >12){
        alert("昵称不合规定");
        return false;
    }else if(loginname === '' || loginname == null || loginname.length > 12){
        alert("登录账号不合规定");
        return false;
    }else if(/.*[\u4e00-\u9fa5]+.*$/.test(loginname)){
        alert("登陆账号不能包含中文");
        return false;
    }else if(password ==='' || password == null){
        alert("密码不得为空");
        return false;
    }else if(password_r ==='' || password_r == null){
        alert("确认密码不得为空");
        return false;
    }else if(password !== password_r){
        alert("两次输入密码不一致");
        return false;
    }else if($("#check").is(':checked') === false){
        alert("请勾选隐私协议")
        return false;
    } else {
        $.ajax({
            url: '/system/register/main',
            type : "POST",
            dataType: "json",
            contentType :'application/x-www-form-urlencoded',
            data: {
                "username" : username,
                "loginname" : loginname,
                "password" : password,
                "usertype": "01"
            },
            success: function (res){
                console.log(res)
                if(res.code == '0'){
                    alert("注册成功");
                    window.close();
                }else {
                    alert("注册失败：" + res.msg);
                }
            }
        })
    }
})