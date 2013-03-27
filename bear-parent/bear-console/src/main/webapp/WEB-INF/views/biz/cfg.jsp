<%@ page session="false" pageEncoding="UTF-8" %>
<%@ include file="../common/includes.jsp" %>
<html>
<head>
    <title>${param.configKey}配置</title>
    <meta name="biz" content="${param.configKey}"/>
    <meta name="tab" content="cfg"/>
    <c:choose>
    	<c:when test="${'message' eq param.configKey}">
    		<script src="${ctx}/static/js/xheditor/xheditor-1.2.1.min.js"></script>
			<script src="${ctx}/static/js/xheditor/xheditor_lang/zh-cn.js"></script>
			<script>
				$(function(){
					var editor = $('#body').xheditor();
					$("form").on("submit",function(e){
						$("#body").val(editor.getSource());
					}); 
				});
			</script>
    	</c:when>
    	<c:otherwise>
    		<link rel="stylesheet" href="${ctx}/static/js/codemirror/lib/codemirror.css">
		    <script src="${ctx}/static/js/codemirror/lib/codemirror.js"></script>
		    <script src="${ctx}/static/js/codemirror/mode/xml/xml.js"></script>
		    <script src="${ctx}/static/js/codemirror/mode/javascript/javascript.js"></script>
		    <script src="${ctx}/static/js/codemirror/mode/css/css.js"></script>
		    <script src="${ctx}/static/js/codemirror/mode/htmlmixed/htmlmixed.js"></script>
		    <style type="text/css">
		        .CodeMirror {
		            border: 1px solid #999;
		            font-family: Consolas, 'Lucida Console', 'DejaVu Sans Mono', monospace;
		        }
		
		        .CodeMirror-scroll {
		            min-height: 400px;
		            height: auto;
		            overflow-y: hidden;
		            overflow-x: auto;
		        }
		    </style>
		    <script>
			    $(function () {
			         var editor = CodeMirror.fromTextArea($('#configValue')[0], {
			            mode:"javascript",
			            tabMode:"indent",
			            lineNumbers:true,
			            lineWrapping:true
			        });
			        if (!editor.getValue()) {
			            $.ajax({
			                url:'${ctx}/static/tpl/${param.configKey}.json',
			                dataType:'text',
			                success:function (data) {
			                    editor.setValue(data);
			                    editor.focus();
			                }
			            });
			        }
			    });
		    </script>
    	</c:otherwise>
    </c:choose>	
</head>
<body>
<form:form action="?configKey=${param.configKey}" commandName="config" method="post">
    <table class="form-table" style="margin: 10px 5px;width: 100%;">
        <tr>
            <th style="width: 50px;">业务</th>
            <td>
            	<form:select path="bizKey" items="${bizs}" cssStyle="width: 150px;"/>
            	<input type="hidden" name="t" value="${'message' eq param.configKey}">
            </td>
        </tr>
        <tr>
            <th style="vertical-align: top;">配置值</th>
            <c:choose>
            	<c:when test="${'message' eq param.configKey}">
		            <td>
		            	<table class="list-table">
		            		<tr><td>模板标题</td><td><input style="width: 99%" id="subject" name="subject" type="text" value="${configJson['tpls']['subject']}"/></td></tr>
		            		<tr><td>模板内容</td><td><textarea id="body" name="body" rows="20" cols="120">${configJson['tpls']['body']}</textarea></td></tr>
		            		<tr><td>模板内置参数</td><td><textarea id="vars" name="vars" rows="" cols="120">${configJson['vars']}</textarea></td></tr>
		            	</table>
		            </td>
            	</c:when>
            	<c:otherwise>
            		<td><form:textarea path="configValue" cssStyle="width: 100%;"/></td>
            	</c:otherwise>
            </c:choose>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" class="a-btn" value="保存">
                <input type="button" class="a-btn" value="返回" onclick="location.href='${ctx}/biz/cfgs?configKey=${param.configKey}';">
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>
