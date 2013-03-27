<%@ page import="org.bear.framework.support.sitemesh.SitemeshHelper" %>
<%--@elvariable id="_meta" type="java.util.map"--%>
<%@ page session="false" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% SitemeshHelper.extractMeta(pageContext); %>
<c:set var="ctx" value="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>Any Console - ${_title}</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <link href="${ctx}/static/css/console.css" type="text/css" media="screen" rel="stylesheet"/>
    <link href="${ctx}/static/css/jquery.validationEngine.css" type="text/css" media="screen" rel="stylesheet"/>
    <script src="${ctx}/static/js/jquery.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/jquery.validationEngine.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/console.js" type="text/javascript"></script>
    ${_head}
</head>
<body>
<div id="container">
    <div id="header">
        <h1 style="float: left;">Any Console</h1>
        <div style="float: right;padding: 10px 20px 0 0;"><a href="#" onclick="location.href='http://i.any123.com/login?callback='+encodeURIComponent(location.href);">登陆</a></div>
        <ul class="clearfix">
            <li${_meta.biz=='app'?' class="active"':''}><a href="${ctx}/app">应用管理</a></li>
            <li${_meta.biz=='biz'?' class="active"':''}><a href="${ctx}/biz">业务管理</a></li>
            <li${_meta.biz=='fs'?' class="active"':''}><a href="${ctx}/biz/cfgs?configKey=fs">文件服务</a></li>
            <li${_meta.biz=='token'?' class="active"':''}><a href="${ctx}/biz/cfgs?configKey=token">token管理</a></li>
            <li${_meta.biz=='message'?' class="active"':''}><a href="${ctx}/biz/cfgs?configKey=message">消息服务</a></li>
            <li${_meta.biz=='search'?' class="active"':''}><a href="${ctx}/search">全文检索</a></li>
            <li${_meta.biz=='event'?' class="active"':''}><a href="${ctx}/event">事件服务</a></li>
            <li${_meta.biz=='cache'?' class="active"':''}><a href="${ctx}/cache">缓存服务</a></li>
            <li${_meta.biz=='spam'?' class="active"':''}><a href="${ctx}/spam">敏感词</a></li>
        </ul>
    </div>
    <div id="tbody">
        <c:choose>
            <c:when test="${_meta.biz=='fs'}">
                <div class="tab">
                    <ul class="clearfix">
                        <li${_meta.tab=='cfg'?' class="active"':''}><a href="${ctx}/biz/cfgs?configKey=fs">配置</a></li>
                        <li${_meta.tab=='test'?' class="active"':''}><a href="${ctx}/fs/test">测试</a></li>
                    </ul>
                </div>
            </c:when>
            <c:when test="${_meta.biz=='message'}">
                <div class="tab">
                    <ul class="clearfix">
                        <li${_meta.tab=='cfg'?' class="active"':''}><a href="${ctx}/biz/cfgs?configKey=message">配置</a></li>
                        <li${_meta.tab=='history'?' class="active"':''}><a href="${ctx}/message/history">发送历史</a></li>
                        <li${_meta.tab=='gateway'?' class="active"':''}><a href="${ctx}/message/gateway">短信网关</a></li>
                    </ul>
                </div>
            </c:when>
        </c:choose>
        <c:choose>
            <c:when test="${empty ret}"/>
            <c:when test="${ret}">
                <div class="ok">保存成功!</div>
            </c:when>
            <c:otherwise>
                <div class="error">${msg}</div>
            </c:otherwise>
        </c:choose>
        ${_body}
    </div>
</div>
</body>
</html>