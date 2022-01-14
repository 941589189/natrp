$('#save_button').click(function(){
    var data = $('#data').val();
    if(data == '' || data == null){
        alert("内容为空，无法保存");
    }else{
        $.ajax({
            url: '/system/notice/innotice',
            type: 'POST',
            dataType: 'JSON',
            contentType :'application/x-www-form-urlencoded',
            data:{ "data": data },
            success: function(res){
                console.log(res);
                if(res.code == '0'){
                   alert(res.msg);
                   location.href = ('/report/main')
                }else{
                   alert(res.msg);
                }
            }
        })
    }
})
 $("#fileUpload").change(function () {
        var file = $("#fileUpload")[0].files[0];
        var reader = new FileReader();
        reader.readAsText(file,'UTF-8')
        reader.onload = function(){  //读取完成后触发 onload
           var fileValue = this.result  // 数据保存在对象的result属性中
           var textarea = document.getElementById('data');
           textarea.innerText = fileValue;
        }
    });