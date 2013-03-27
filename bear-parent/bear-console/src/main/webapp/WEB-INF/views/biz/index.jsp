<%@ page session="false" pageEncoding="UTF-8" %>
<%@ include file="../common/includes.jsp" %>
<html>
<head>
    <title>biz</title>
    <meta name="biz" content="biz"/>
</head>
<body>
<table class="list-table" style="width: 100%;">
    <caption>
        所属应用
        <select onchange="location.href='?appId='+this.value;"><option value="">---</option>
            <c:forEach items="${apps}" var="entry">
                <option value="${entry.key}"<c:if test="${entry.key==param.appId}"> selected="selected"</c:if>>${entry.value}</option>
            </c:forEach>
        </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="${ctx}/biz/edit" class="a-btn">新建业务</a>
    </caption>
    <tr>
        <th>key</th>
        <th>名称</th>
        <th>所属应用</th>
        <th>状态</th>
        <th style="width:180px;">操作</th>
    </tr>
    <c:forEach items="${bizs}" var="biz">
        <tr>
            <td><c:out value="${biz.key}"/></td>
            <td><c:out value="${biz.name}"/></td>
            <td><c:out value="${apps[biz.appId]}"/></td>
            <td><c:out value="${biz.status}"/></td>
            <td>
                <a href="${ctx}/biz/edit?id=${biz.id}">编辑</a>&nbsp;
                <%--<a href="${ctx}/biz/delete?id=${biz.id}" onclick="return confirm('确定要删除吗?')">删除</a>&nbsp;--%>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
