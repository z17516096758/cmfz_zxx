<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<script>

    $(function(){
        $("#abtable").jqGrid({
            url : '${path}/album/showAll',
            editurl:'${path}/album/edit',
            styleUI:"Bootstrap",
            datatype : "json",
            autowidth:true,
            height : "auto",
            colNames : [ 'Id', '名称', '封面', '得分', '作者','播音员', '集数','简述','上传时间' ],
            colModel : [
                {name : 'id',width : 55,key:true},
                {name : 'name',width : 90,editable:true},
                {name : 'cover',width : 100,editable:true,edittype:'file',
                    formatter:function (cellvalue) {
                        return "<img src='${path}/album/image/"+cellvalue+"' style='width:180px;height:80px' />";
                    }
                },
                {name : 'score',width : 80,align : "right",editable: true},
                {name : 'author',width : 80,align : "right",editable:true,edittype:'select',editoptions:{
                    dataUrl:'${path}/star/show'
                    }},
                {name : 'announcer',width : 80,align : "right",editable:true},
                {name : 'count',width : 150,editable: true},
                {name : 'resume',width : 80,align : "right" ,editable:true},
                {name : 'createDate',width : 80,align : "right"}
            ],
            rowNum : 3,
            rowList : [ 3,5, 10, 20 ],
            pager : '#abpager',
            viewrecords : true,
            multiselect : false,
            subGrid : true,
            caption : "专辑管理",
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
                        url : "${path}/chapter/showAll?albumId=" + row_id,
                        editurl: '${path}/chapter/edit?albumId='+row_id,
                        styleUI:"Bootstrap",
                        datatype : "json",
                        colNames : [ 'id', '名称', '文件大小', '时长','创建日期','操作'],
                        colModel : [
                            {name : "id",width : 80,key : true},
                            {name : "name",width : 130 , editable:true, edittype:'file'},
                            {name : "size",width : 70,align : "right"},
                            {name : "time",width : 70,align : "right"},
                            {name : "createDate",width : 70,align : "right",},
                            {name:"name",width:100,
                                formatter:function (cellvalue) {
                                    return "<a href= '#' onclick='playerChapter(\""+cellvalue+"\")'><span class='glyphicon glyphicon-play-circle'/>&nbsp;&nbsp;"
                                        +"<a href= '#' onclick='downloadChapter(\""+cellvalue+"\")'><span class='glyphicon glyphicon-download'/>"
                                }
                            }
                        ],
                        rowNum : 20,
                        pager : pager_id,
                        autowidth: true,
                        height : '100%'
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : false, add : true, del : true},
                    {},
                    {
                        closeAfterAdd: true,
                        afterSubmit: function(data){
                            $.ajaxFileUpload({
                                url:'${path}/chapter/upload',
                                dataType: 'JSON',
                                type: 'post',
                                fileElementId:'name',
                                data: {id:data.responseText},
                                success:function () {
                                    $("#abtable").trigger("reloadGrid")
                                }
                            });
                            return "hello";
                        }
                    },
                    {}
                );
            },
        });
        jQuery("#abtable").jqGrid('navGrid', '#abpager',
            {add : true, edit : true, del : true ,addtext:"添加",edittext:"修改"},
            {
                closeAfterEdit:true
            },
            {
                closeAfterAdd:true,
                afterSubmit:function(data){
                    $.ajaxFileUpload({
                        url:"${path}/ablum/upload",
                        dataType:"JSON",
                        type:"post",
                        fileElementId: "cover",
                        data:{id:data.responseText},
                        success:function () {
                            $("#abtable").trigger("reloadGrid");
                        }
                    });
                    return "hello";
                }
            }
            );
    });
    function playerChapter(fileName) {
        $("#audioModal").modal("show");
        $("#playAudio").attr("src","${path}/album/chapter/music/"+fileName);
    }
    function downloadChapter(fileName) {
        location.href="${path}/chapter/download?fileName="+fileName;
    }
</script>
<div class="panel panel-info">
    <%--定义表格模板--%>
    <table id="abtable"></table>
    <%--定义页面模板--%>
    <div id="abpager"></div>
    <div id="audioModal" class="modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <audio id="playAudio" src="" controls></audio>
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</div>