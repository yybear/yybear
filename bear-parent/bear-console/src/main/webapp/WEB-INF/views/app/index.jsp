<%@ page session="false" pageEncoding="UTF-8" %>
<%@ include file="../common/includes.jsp" %>
<html>
<head>
    <title>app</title>
    <meta name="biz" content="app"/>
</head>
<body>
<table class="list-table" style="width: 100%;">
    <caption>
        <a href="${ctx}/app/edit" class="a-btn">新建应用</a>
    </caption>
    <tr>
        <th>key</th>
        <th>名称</th>
        <th>状态</th>
        <th style="width:180px;">操作</th>
    </tr>
    <c:forEach items="${apps}" var="app">
        <tr>
            <td><c:out value="${app.key}"/></td>
            <td><c:out value="${app.name}"/></td>
            <td><c:out value="${app.status}"/></td>
            <td>
                <a href="${ctx}/app/edit?id=${app.id}">编辑</a>&nbsp;
                <%--<a href="${ctx}/app/delete?id=${app.id}" onclick="return confirm('确定要删除吗?')">删除</a>&nbsp;--%>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
