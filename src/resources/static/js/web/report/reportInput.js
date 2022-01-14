$(document).ready(function() {
    // 初始化表格
    initTable();
});
//获取当前日期 接受的标本
function initTable() {
    var t = $("#reportin-table").bootstrapTable({
        url: '/report/receive/today', //请求后台方法的路径
        pagination: true,	//显示分页条
        sidePagination: 'server',//服务器端分页,这个属性一定要有，否则不能显示查询到的结果
        method: 'POST',                      //请求方式（*）
        contentType :'application/x-www-form-urlencoded',
            striped: true,                      //是否显示行间隔色
            cache: true,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: true,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
            pageSize: 15,                       //每页的记录行数（*）
            showColumns: true,                  //是否显示所有的列（选择显示的列）
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            uniqueId: "doctadviseno",             //每一行的唯一标识，一般为主键列
            clickToSelect: true,                //是否启用点击选中行
        responseHandler: function (res) { //接受后台返回的结果集
            return {
                rows: res.list,  // list中包含是要展示的对象
                total: res.total
            }
        },
            queryParams: function (params) { // 前端向后台传的参数，这里也可以自定义参数，具体实现在后面的文章中介绍-->
                return {
                    offset: params.offset, //页码-->
                    limit: params.limit, //页面大小，即每一页显示的条数-->
                }
            },
        columns: [{
            checkbox: true
            },
            {
                field: 'reportId',//该列映射的data的参数名
                title: '报告单ID'
            },
            {
                field: 'patient.patientName',
                title: '患者',
            },
            {
                field: 'createTime',
                title: '报告申请时间'
            },
            {
                field: 'status',
                title: '报告状态',
                formatter: function (value, row, index) {
                    if(row.status == 0){
                       return "<span> 未审核 </span>"
                    }else if(row.status == 1){
                       return "<span> 已审核 </span>"
                    }else if(row.status == 2){
                        return "<span> 已打印 </span>"
                    }else { return "<span> 作废 </span>" }
                }
            }
        ]
    });
}
$('#report_id').bind('keypress', function (event) {
    if(event.keyCode == "13"){
        document.getElementById("info").innerHTML=''
        var reportId = $.trim($('#report_id').val())
        $.ajax({
            url: '/reportin/list',
            type : "POST",
            dataType: "json",
            contentType :'application/x-www-form-urlencoded',
            data: {
                "reportId": reportId
            },
            success: function(res){
                if(res.code == '0'){
                     $("#patient_id").val(res.data.patientid);
                     $("#patient_name").val(res.data.patientname);
                     $("#patient_age").val(res.data.age);
                     if(res.data.sex == 1){
                        $("#patient_sex").val("男");
                     }else{
                         $("#patient_sex").val("女");
                     }
                     $("#create_time").val(res.data.requesttime);
                     $("#requester").val(res.data.requester)
                     $("#item").val(res.data.examinaim);
                     $("#result").val(res.data.result);
                     $("#checktor").val(res.data.checktor);
                     $("#check_time").val(res.data.CHECKTIME);
                     $("#sample_type").val(res.data.sampletype);
                     if(res.data.SAMPLESTATUS == 0){
                        $("#status").val("未审核");
                         //如果样本 未审核  则将结果设置为可修改
                         $("#result").removeAttr('disabled')
                     }else if(res.data.SAMPLESTATUS == 1){
                        $("#status").val("已审核");
                     }else if(res.data.SAMPLESTATUS == 2){
                        $("#status").val("已打印");
                     }else{ $("#status").val("作废"); }
                     if(res.data.result == ''||res.data.result == null || res.data.result == undefined){
                         $("#result").val("未出");
                     }else{
                         $("#result").val(res.data.result);
                     }
                }else{
                    alert(res.msg)
                    location.reload()
                }
            }
        })
    }
})
$('#save_button').click(function() {
    var result = $('#result').val();
    if(null == result || '' == result){
        alert("结果不得为空")
    }
    $.ajax({
        url: "/report/insert",
        type : "POST",
        data: $('#save_from').serialize(),
        success: function(res){
            document.getElementById("info").innerHTML=res.msg;
            location.reload()
        }
    })
})

// <!-- Excel批量录入 报告单 -->
$("#fileUpload").change(function () {
    var file = $("#fileUpload")[0].files[0];
    var reader = new FileReader();
    reader.readAsBinaryString(file)

    reader.onload = function(e){  //读取完成后触发 onload
        var fileValue = e.target.result   // 数据保存在对象的result属性中
        var data = XLSX.read(fileValue, {type: 'binary'});  //读取xlsx内容
        sheetName = data.SheetNames[0]   // 获取文档中第一个sheet页签的名字
        sheetValue = data.Sheets[sheetName]  //获取内容
        console.log(sheetValue)
        var jsonData = JSON.stringify(XLSX.utils.sheet_to_json(sheetValue))
        console.log(jsonData)
        $.ajax({
            url: '/reportin/excel',
            type: 'POST',
            datatype: 'json',
            headers : {
                'Content-Type' : 'application/json;charset=utf-8'
            },
            data: jsonData,
            success: function(res){
                alert(res.msg)
            }
        })
    }
});