<%@ page session="false" pageEncoding="UTF-8" %>
<%@ include file="../common/includes.jsp" %>
<html>
<head>
    <title>${param.configKey}配置</title>
    <meta name="biz" content="${param.configKey}"/>
    <meta name="tab" content="cfg"/>
</head>
<body>
<table class="list-table" style="width: 100%;">
    <caption>
        <a href="${ctx}/biz/cfg?configKey=${param.configKey}" class="a-btn">新建业务配置</a>
    </caption>
    <tr>
        <th>业务</th>
        <th>业务名称</th>
        <th style="width:180px;">操作</th>
    </tr>
    <c:forEach items="${cfgs}" var="cfg">
        <tr>
            <td><c:out value="${cfg.bizKey}"/></td>
            <td><c:out value="${cfg.bizName}"/></td>
            <td>
                <a href="${ctx}/biz/cfg?bizKey=${cfg.bizKey}&configKey=${cfg.configKey}">编辑</a>&nbsp;
                <a href="${ctx}/biz/cfg/delete?bizKey=${cfg.bizKey}&configKey=${cfg.configKey}" onclick="return confirm('确定要删除吗?')">删除</a>&nbsp;
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
