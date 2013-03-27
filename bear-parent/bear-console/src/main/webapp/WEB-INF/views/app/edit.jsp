<%@ page session="false" pageEncoding="UTF-8" %>
<%@ include file="../common/includes.jsp" %>
<html>
<head>
    <title>app</title>
    <meta name="biz" content="app"/>
</head>
<body>
<form:form commandName="app" method="post">
    <table class="form-table" style="margin: 10px 5px;">
        <tr>
            <th>Key</th>
            <td><form:input path="key" size="60" cssClass="validate[required]"/></td>
        </tr>
        <tr>
            <th>名称</th>
            <td><form:input path="name" size="60" cssClass="validate[required]"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" class="a-btn" value="保存">
                <input type="button" class="a-btn" value="返回" onclick="location.href='${ctx}/app';">
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>
