<%--
  Created by IntelliJ IDEA.
  User: 朱欣鑫
  Date: 2019/10/31
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<script>
    $(function(){
        $("#ustable").jqGrid({
            url : "${path}/user/showAllByPages",
            editurl:"${path}/user/edit",
            datatype : "json",
            rowNum : 5,
            autowidth:true,
            height:"auto",
            styleUI:"Bootstrap",
            rowList : [ 5,10, 20, 30 ],
            pager : '#uspager',
            viewrecords : true,  //是否展示总条数
            colNames : [ 'Id', '用户名','密码' ,'盐值', '昵称', '电话','省份','城市','标签','头像','性别','创建时间' ],
            colModel : [
                {name : 'id',width : 55,hidden:true},
                {name : 'username',editable:true,width : 90},
                {name : 'password',editable:true,width : 90},
                {name : 'salt',editable:true,width : 80,align : "right"},
                {name : 'nickname',width : 80,editable:true,align : "center"},
                {name : 'phone',width : 80,editable:true,align : "right"},
                {name : "province",width : 70,align : "right",editable:true
                //     ,formatter:function (cellvalue,options,rows) {
                //         return cellvalue+"-"+rows.city;
                //     }

                },
                {name : 'city',width : 55,editable:true},
                {name : 'sign',editable:true,width : 80,align : "center"},
                {name : 'photo', id:"photo",width : 100,editable:true,edittype:"file",align : "center",
                    formatter:function(cellvalue){
                        return "<img src='${path}/user/image/"+cellvalue+"' style='width:180px;height:80px' />";
                    },// 返回图片。
                },
                {name : 'sex',editable:true,edittype:'select',editoptions:{value:'男:男;女:女'},width : 80,align : "right"},
                {name : 'createDate',width : 80,align : "center"},

            ]

        });

        /*增删改查操作*/
        $("#ustable").jqGrid('navGrid', '#uspager',
            {edit : true,add : true,del : true,search:false,addtext:"添加",edittext:"编辑"},
            {
                closeAfterEdit:true,
                beforeShowForm:function(data){
                    data.find("#img_path").attr("disabled",true)//禁用input框
                }
            },
            {
                afterSubmit:function(data){
                    //文件上传
                    $.ajaxFileUpload({
                        url:"${path}/user/upload",
                        type:"post",
                        dataType:"JSON",
                        fileElementId:"photo",
                        data:{id:data.responseText},
                        success:function(){
                            //刷新表单
                            $("#ustable").trigger("reloadGrid");
                        }
                    })
                    //一定要有返回值，返回什么都行
                    return "hello";
                },
                closeAfterAdd:true
            },
            {}
        );
    });
    function exportUser() {
        alert(1);
        location.href=("${path}/user/exportUser");
        alert("Excel表导出成功");
    }

</script>

<%--初始化面板--%>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>轮播图信息</h2>
    </div>

    <ul class="nav nav-tabs">
        <li class="active"><a href="#">轮播图信息</a></li>
    </ul>

    <div class="panel panel-body">
        <button type="button" class="btn btn-info" onclick="exportUser()">导出所有用户信息</button>
    </div>

    <div id="show" class="alert alert-warning alert-dismissible" role="alert"  style="width:200px;display: none">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <strong id="showMsg"></strong>
    </div>

    <%--初始化表单--%>
    <table id="ustable" />

    <%--定义分页工具栏--%>
    <div id="uspager"></div>

</div>

