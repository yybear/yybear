<%@ page pageEncoding="UTF-8" %>
<%@ include file="includes.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>检索平台管理</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <link href="css/main.css" type="text/css" media="screen" rel="stylesheet"/>
    <link href="css/console.css" type="text/css" media="screen" rel="stylesheet"/>
    <link href="css/jquery.validationEngine.css" type="text/css" media="screen" rel="stylesheet"/>
    <script src="js/jquery.js" type="text/javascript"></script>
    <script src="js/jquery.validationEngine.js" type="text/javascript"></script>
    <script src="js/console.js" type="text/javascript"></script>
</head>
<body>
<div id="container">
    <div class="tab">
        <ul class="clearfix">
            <li<c:if test="${tab=='console'}"> class="active"</c:if>><a href="console.do">索引管理</a></li>
            <li<c:if test="${tab=='queue'}"> class="active"</c:if>><a href="queue.do">索引队列</a></li>
            <li<c:if test="${tab=='analysis'}"> class="active"</c:if>><a href="analysis.do">字段分析</a></li>
            <li<c:if test="${tab=='solr'}"> class="active"</c:if>><a href="solr.do">Solr</a></li>
            <li><a href="${ctx}">首页</a></li>
        </ul>
    </div>
    <div id="tbody">