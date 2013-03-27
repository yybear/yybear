<%@ page session="false" pageEncoding="UTF-8" %>
<%@ include file="../common/includes.jsp" %>
<html>
<head>
    <title>token</title>
    <meta name="biz" content="token"/>
</head>
<body>
<table class="list-table" style="width: 100%;">
    <caption>
        <a href="${ctx}/token/edit" class="a-btn">新建业务配置</a>
    </caption>
    <tr>
        <th>业务Key</th>
        <th>业务名</th>
        <th style="width:180px;">操作</th>
    </tr>
    <c:forEach items="${configs}" var="config">
        <tr>
            <td><c:out value="${token.key}"/></td>
            <td><c:out value="${token.name}"/></td>
            <td><c:out value="${apps[token.appId]}"/></td>
            <td><c:out value="${token.status}"/></td>
            <td>
                <a href="${ctx}/token/edit?id=${token.id}">编辑</a>&nbsp;
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
