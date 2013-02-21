<%@ page session="false" pageEncoding="UTF-8" %>
<%@ include file="common/includes.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>全文检索平台</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <link href="css/main.css" type="text/css" media="screen" rel="stylesheet"/>
    <script src="js/jquery-min.js" type="text/javascript"></script>
    <script src="js/jquery.autocomplete.js" type="text/javascript"></script>
    <script src="js/main.js" type="text/javascript"></script>
</head>
<body>
<div><a href='<%=request.getContextPath() %>/solr.do'>solr控制台</a></div>
<form action="" method="get" id="s-form">
    <c:choose>
        <c:when test="${empty keyword}">
            <div id="s-input1">
                <div>
                    <h1>全文检索平台</h1><br/><br/><br/>
                </div>
                <input type="hidden" name="aid" value="${param.aid}"/>
                <input type="text" name="keyword" id="keyword" value="${keyword}" class="text"/>&nbsp;
                <input type="submit" value="搜 索" class="btn"/>
            </div>
        </c:when>
        <c:otherwise>
            <input type="hidden" name="index"/>
            <div id="s-input">
                <a href="${ctx}" title="首页">首页</a>&nbsp;&nbsp;
                <input type="text" name="keyword" id="keyword" value="${keyword}" class="text"/>&nbsp;
                <input type="submit" value="搜 索" class="btn"/>
            </div>
            <c:if test="${!empty page}"><div id="s-count">
                找到 ${page.total} 条结果 （用时 ${page.qTime/1000} 秒）
            </div></c:if>
            <div id="sidebar">
                <c:forEach items="${page.facets}" var="entry">
                    <ul>
                        <c:forEach items="${entry.value}" var="facet">
                            <c:choose>
                                <c:when test="${entry.key=='app'}">
                                    <li<c:if test="${param.aid==facet.id || empty param.aid && empty facet.id}"> class="active"</c:if>>
                                        <a href="?keyword=${keyword}&aid=${facet.id}&mt=${param.mt}&date=${param.date}&sort=${param.sort}">${facet.name} (${facet.count})</a>
                                    </li>
                                </c:when>
                                <c:when test="${entry.key=='category'}">
                                    <li<c:if test="${param.cid==facet.id || empty param.cid && empty facet.id}"> class="active"</c:if>>
                                        <a href="?keyword=${keyword}&aid=${param.aid}&cid=${facet.id}&mt=${param.mt}&date=${param.date}&sort=${param.sort}">${facet.name} (${facet.count})</a>
                                    </li>
                                </c:when>
                                <c:when test="${entry.key=='mimeType'}">
                                    <li<c:if test="${param.mt==facet.id || empty param.mt && empty facet.id}"> class="active"</c:if>>
                                        <a href="?keyword=${keyword}&aid=${param.aid}&cid=${param.cid}&mt=${facet.id}&date=${param.date}&sort=${param.sort}">${facet.name} (${facet.count})</a>
                                    </li>
                                </c:when>
                                <c:when test="${entry.key=='date'}">
                                    <li<c:if test="${param.date==facet.id || empty param.date && empty facet.id}"> class="active"</c:if>>
                                        <a href="?keyword=${keyword}&aid=${param.aid}&cid=${param.cid}&mt=${param.mt}&date=${facet.id}&sort=${param.sort}">${facet.name}</a>
                                    </li>
                                </c:when>
                                <c:when test="${entry.key=='sort'}">
                                    <li<c:if test="${param.sort==facet.id && !empty param.sort || empty param.sort && facet.id=='score'}"> class="active"</c:if>>
                                        <a href="?keyword=${keyword}&aid=${param.aid}&cid=${param.cid}&mt=${param.mt}&date=${param.date}&sort=${facet.id}">${facet.name}</a>
                                    </li>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                        <li class="sep"></li>
                    </ul>
                </c:forEach>
            </div>
            <div id="tbody">
                <c:if test="${!empty page.scs}">
                    <div class="search-sc">您是不是要找：
                        <c:forEach items="${page.scs}" var="mlt">
                            <a href="?keyword=${mlt}">${mlt}</a>&nbsp;&nbsp;
                        </c:forEach>
                    </div>
                </c:if>
                <c:forEach items="${page.items}" var="result">
                    <div class="search-result">
                        <h3 class="result-title">
                            <a href="${result.url}" target="_blank"><c:if test="${!empty result.icon}"><img src="img/icon/${result.icon}" alt="icon"/></c:if>${result.title}</a>
                        </h3>
                        <div class="result-info">
                            <c:if test="${empty param.aid && !empty result.appName}"><a href="?keyword=${keyword}&aid=${result.appId}&mt=${param.mt}&date=${param.date}&sort=${param.sort}">${result.appName}</a>&nbsp;<c:set var="hasBn" value="true"/></c:if><c:if test="${empty param.cid && !empty result.categoryName}"><c:if test="${!empty hasBn}">›&nbsp;</c:if><a href="?keyword=${keyword}&aid=${result.appId}&cid=${result.categoryId}&mt=${param.mt}&date=${param.date}&sort=${param.sort}">${result.categoryName}</a>&nbsp;</c:if><span class="info"><fmt:formatDate value="${result.date}" pattern="yyyy-MM-dd HH:mm"/></span>
                        </div>
                        <div class="result-body clearfix">${result.body}</div>
                    </div>
                </c:forEach>
                <c:if test="${!empty page.mlts}">
                    <div class="search-mlt"><h3><em>${keyword}</em>的相关搜索</h3>
                        <c:forEach items="${page.mlts}" var="mlt" varStatus="vs">
                            <a href="?keyword=${mlt}">${mlt}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${vs.index % 4==3}"><br/></c:if>
                        </c:forEach>
                    </div>
                </c:if>
                <c:import url="common/pagination.jsp">
                    <c:param name="url" value="?keyword=${keyword}&aid=${param.aid}&cid=${param.cid}&mt=${param.mt}&date=${param.date}&sort=${param.sort}"/>
                </c:import>
            </div>
            <div style="clear: both;height: 50px;">&nbsp;</div>
        </c:otherwise>
    </c:choose>
</form>
<script type="text/javascript">
    $(function () {
        $('#keyword').autocomplete({
            autoSubmit:true,
            deferRequestBy:200,
            serviceUrl:'auto.do'
        });
    });
</script>
</body>
</html>