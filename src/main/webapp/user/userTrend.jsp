<%--
  Created by IntelliJ IDEA.
  User: 朱欣鑫
  Date: 2019/11/1
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<script src="${path}/js/echarts.js"></script>
<script src="${path}/js/china.js"></script>
<script>
    $.getJSON("${path}/trend/show",function (data) {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '用户注册趋势表'
            },
            tooltip: {},
            legend: {
                data:['男','女']
            },
            xAxis: {
                data: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"]
            },
            yAxis: {},
            series: [{
                name: '男',
                type: 'line',
                data: data.boys
            },
            {
                name: '女',
                type: 'line',
                data: data.grils
            }
                ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    })

</script>

<div id="main" style="width: 600px;height:400px;"></div>


