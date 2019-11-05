<%--
  Created by IntelliJ IDEA.
  User: 朱欣鑫
  Date: 2019/10/31
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:set var="path" value="${pageContext.request.contextPath}" />
    <script charset="utf-8" src="${path}/kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" src="${path}/kindeditor/lang/zh-CN.js"></script>
    <script>
        KindEditor.ready(function(K) {
            window.editor = K.create('#editor_id',
                {
                    uploadJson:"${path}/article/upload",
                    filePostName:"photo",
                    allowFileManger:true ,//是否开启图片空间
                    fileManagerJson:"${path}/editor/queryAllphoto",
                    afterBlur:function(){ //编辑器失去焦点(blur)时执行回调函数
                        this.sync();//将编辑器的内容同步到表单
                    }
                });
        });
    </script>
</head>
<body>
<textarea id="editor_id" name="content" style="width:700px;height:300px;">

                        </textarea>
</body>
</html>
