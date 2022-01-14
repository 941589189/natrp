$('#submit').click(function(){
    var password = $('#password').val()
    var password_a = $('#password-a').val()
    if( null == password  || '' == password){
         alert("新密码不得为空")
    }else if(null == password_a || '' == password_a){
         alert("确认新密码不得为空")
    }else if(password != password_a){
        alert("两次密码不相同")
    }else{
        $.ajax({
            url: "/system/user/password",
            type: "POST",
            dataType: "json",
            contentType: 'application/x-www-form-urlencoded',
            async: false,
            data: {
               "password":password
            },
            success: function (res){
                console.log(res)
                if(res.code == '0'){
                    alert(res.msg);
                    location.href = ("/login")
                }else{
                    alert(res.msg);
                }
            }
        })
    }
})