//文档加载事件
$(document).ready(function() {
    // 初始化表格
    initTable();
});
var profix = "/report/list"
function initTable() {
    var t = $("#report-table").bootstrapTable({
        url: '/report/list', //请求后台方法的路径
        pagination: true,	//显示分页条
        sidePagination: 'server',//服务器端分页,这个属性一定要有，否则不能显示查询到的结果
        method: 'POST',                      //请求方式（*）
        contentType :'application/x-www-form-urlencoded',
            //toolbar: '#toolbar',              //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: true,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
            pageSize: 15,                     //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: false,                      //是否显示表格搜索
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列（选择显示的列）
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            uniqueId: "reportId",             //每一行的唯一标识，一般为主键列
            showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                  //是否显示父子表搜索
            clickToSelect: true,                //是否启用点击选中行
            searchOnEnterKey: true,            //在搜索框内输入内容并且按下回车键才开始搜索
        responseHandler: function (res) { //接受后台返回的结果集
            return {
                rows: res.list,  // list中包含是要展示的对象
                total: res.total
            }
        },
        queryParams: function (params) { // 前端向后台传的参数，这里也可以自定义参数，具体实现在后面的文章中介绍
            return {
                offset: params.offset, //页码
                limit: params.limit, //页面大小，即每一页显示的条数
                reportId : $.trim($('#search-id').val()),
                patientId : $.trim($('#patient-id').val()),
                startTime: $('#starttime').val(),
                endTime: $('#endtime').val(),
                status: function (){
                    if($("#check_status").is(':checked')){
                        return "1";
                    }else {
                        return '0'
                    }
                }
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
                field: 'patientId',
                title: '病历号',
                 visible: false
            },
            {
                field: 'patient.patientName',
                title: '姓名',
            },
            {
                field: 'patient.sex',
                title: '性别',
                formatter: function (value, row, index) {
                    if(row.patient.sex == 1){
                       return "<span> 男 </span>"
                    }else if(row.patient.sex == 2){
                       return "<span> 女 </span>"
                    }else{
                        return "<span> 未知 </span>"
                    }
                }
            },
            {
                field: 'patient.age',
                title: '年龄',
                visible: false
            },
            {
                field: 'createTime',
                title: '报告申请时间',
                visible: false
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
            },
             {
                field : 'item',
                title : '医嘱项目'
            },
            {
                field : 'result',
                title : '报告结果'
            },
            {
                field : 'checkTime',
                title : '审核时间'
            },
            {
                field : 'checktor',
                title : '审核者'
            },
            {
                field : 'sampleType',
                title : '样本类型',
                formatter: function (value, row, index) {
                    if(row.sampleType == 0){
                       return "<span> 咽拭子 </span>"
                    }else if(row.patient.sex == 1){
                       return "<span> 肛拭子 </span>"
                    }else{
                        return "<span> 其他 </span>"
                    }
                }
            },
            {
                field : 'remark',
                title : '备注',
                cellStyle: formatTableUnit,
                formatter: paramsMatter
            }
        ]
    });
}
//查询按钮响应事件
$(function() {
    $("#query").click(function() {
        $('#report-table').bootstrapTable('refreshOptions', { pageNumber:1,pageSize:30 }); // 很重要的一步，刷新url！
    });
});
/*修改备注那一列的属性  文本过长隐藏多余的部分，鼠标悬停显示全部的内容*/
function paramsMatter(value, row, index) {
    var span = document.createElement('span');
    span.setAttribute('title', value);
    span.innerHTML = value;
    return span.outerHTML;
}
function formatTableUnit(value, row, index) {
    return {
        css: {
            "white-space": 'nowrap',
            "text-overflow": 'ellipsis',/*当文本溢出时，溢出的部分隐藏，显示省略号*/
            "overflow": 'hidden',
            "max-width": "100px"
        }
    }
}

 //导出PDF响应事件
$('#exportPDF').click(function(){
    var checkValue = $('#report-table').bootstrapTable('getSelections');
    var status = checkValue[0].status;
    if(checkValue.length<=0 || checkValue.length>=2){
            alert("请选择一行");
    }else if(status == '0'){
        alert("此报告单未审核，不可打印");
    }else{
        console.log(checkValue[0].reportId)
        window.location.href=("/report/pdf/download?reportId=" + checkValue[0].reportId)
    }
});
   //导出报告单Excel按钮响应事件
$('#export').click(function(){
    var startTime = $('#starttime').val()
    var endTime = $('#endtime').val()
    window.location.href=("/report/export?startTime=" + startTime +"&endTime=" + endTime )
});

