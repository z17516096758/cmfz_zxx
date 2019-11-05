<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function(){
        $("#srtable").jqGrid({
            url : '${path}/star/showAll',
            editurl:'${path}/star/edit',
            datatype : "json",
            autowidth:true,
            styleUI:'Bootstrap',
            height : "auto",
            colNames : [ '编号', '艺名', '真实姓名', '照片', '性别','生日'],
            colModel : [
                {name : 'id',width : 55},
                {name : 'nickname',editable:true,width : 90},
                {name : 'realname',editable:true,width : 100},
                {name : 'photo',width : 80,editable:true,edittype: 'file',align : "right",
                    formatter:function(cellvalue){
                        return "<img src='${path}/star/image/"+cellvalue+"' style='width:180px;height:80px' />";
                    }
                },
                {name : 'sex',width : 80,align : "right",editable:true,edittype: 'select', editoptions:{value:'男:男;女:女'}},
                {name : 'bir',width : 80,align : "right" ,editable:true, edittype:'date'},
            ],
            rowNum : 3,
            rowList : [ 3, 5, 10, 20 ],
            pager : '#srpager',
            viewrecords : true,
            multiselect : false,
            subGrid : true,
            caption : "明星管理",
            subGridRowExpanded : function(subgrid_id, row_id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id
                    + "' class='scroll'></table><div id='"
                    + pager_id + "' class='scroll'></div>");
                jQuery("#" + subgrid_table_id).jqGrid(
                    {
                        url : "${path}/user/showAll?q=2&starId=" + row_id,
                        styleUI:"Bootstrap",
                        datatype : "json",
                        colNames : [ 'id', '用户名', '昵称', '头像','电话','地址','签名' ],
                        colModel : [
                            {name : "id",width : 80,key : true},
                            {name : "username",width : 130},
                            {name : "nickname",width : 70,align : "right"},
                            {name : "photo",width : 70,align : "right",
                                formatter:function(cellvalue){
                                    return "<img src='${path}/user/image/"+cellvalue+"' style='width:180px;height:80px' />"
                                }
                            },
                            {name : "phone",width : 70,align : "right"},
                            {name : "province",width : 70,align : "right",
                                formatter:function (cellvalue,options,rows) {
                                    return cellvalue+"-"+rows.city;
                                }

                            },
                            {name : "sign",width : 70,align : "right"}
                        ],
                        rowNum : 20,
                        pager : pager_id,
                        autowidth: true,
                        height : '100%'
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : false,
                        add : false,
                        del : false
                    });
            },
            subGridRowColapsed : function(subgrid_id, row_id) {
            }
        })
        $("#srtable").jqGrid('navGrid', '#srpager',
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
                        url:"${path}/star/upload",
                        type:"post",
                        dataType:"JSON",
                        fileElementId:"photo",
                        data:{id:data.responseText},
                        success:function(){
                            //刷新表单
                            $("#srtable").trigger("reloadGrid");
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
</script>
<div class="panel panel-info">
    <table id="srtable">

    </table>
    <div id="srpager">
    </div>
</div>

