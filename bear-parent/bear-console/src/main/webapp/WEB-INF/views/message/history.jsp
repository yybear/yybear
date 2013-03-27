<%@ page session="false" pageEncoding="UTF-8" %>
<%@ include file="../common/includes.jsp" %>
<html>
<head>
    <title>biz</title>
    <meta name="biz" content="message"/>
    <meta name="tab" content="history"/>
    <script language="javascript" type="text/javascript" src="${ctx}/static/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<form action="" method="get">
    <table class="list-table" style="width: 100%;">
        <caption>
            BizKey
            <select name="bizKey" onchange="this.form.submit();"><option value="">---</option>
                <c:forEach items="${cfgs}" var="cfg">
                    <option value="${cfg.bizKey}"<c:if test="${cfg.bizKey==param.bizKey}"> selected="selected"</c:if>>${cfg.bizName}</option>
                </c:forEach>
            </select>
            类型
            <select name="type" id="l-type" onchange="this.form.submit();">
                <option value="">--</option>
                <option value="EMAIL">邮件</option>
                <option value="SMS">短信</option>
            </select>
            成功
            <select name="succeed" id="l-succeed" onchange="this.form.submit();">
                <option value="">--</option>
                <option>true</option>
                <option>false</option>
            </select>
                开始时间 <input type="text" id="startDate" name="startDate" value="${param.startDate}" size="18" class="Wdate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
                结束时间 <input type="text" id="endDate" name="endDate" value="${param.endDate}" size="18" class="Wdate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
                <select name="size" id="l-size">
                    <option>10</option>
                    <option>20</option>
                    <option>50</option>
                    <option>100</option>
                    <option>200</option>
                    <option>500</option>
                    <option>1000</option>
                </select>
                &nbsp;&nbsp;
                <input type="submit" class="a-btn" value="检 索"/>
        </caption>
        <tr>
            <th>id</th>
            <th>接受者</th>
            <th>接受者id</th>
            <th>发送时间</th>
            <th>是否成功</th>
            <th>msg</th>
        </tr>
        <c:forEach items="${page.items}" var="history">
            <tr>
                <td>${history.id}</td>
                <td>${history.data['receiverContact']}</td>
                <td>${history.data['receiverId']}</td>
                <td>${history.data['sendAt']}</td>
                <td>${history.data['succeeded']}</td>
                <td>${history.data['feedback']}</td>
            </tr>
        </c:forEach>
    </table>
</form>
<c:if test="${!empty size}">
    <script type="text/javascript">
        $(function () {
            $('#l-size').val('${size}');
            $('#l-type').val('${param.type}');
            $('#l-succeed').val('${param.succeed}');
        });
    </script>
</c:if>
<div style="padding: 10px;" class="clearfix">
<c:import url="../common/pagination.jsp">
    <c:param name="url" value="?size=${size}"/>
</c:import>
</div>
</body>
</html>
