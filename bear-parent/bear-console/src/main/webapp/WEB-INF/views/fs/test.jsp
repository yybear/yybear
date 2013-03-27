<%@ page session="false" pageEncoding="UTF-8" %>
<%@ include file="../common/includes.jsp" %>
<html>
<head>
    <title>fs</title>
    <meta name="biz" content="fs"/>
    <meta name="tab" content="test"/>
    <link href="${ctx}/static/css/fs/test.css" type="text/css" media="screen" rel="stylesheet"/>
    <script src="${ctx}/static/js/beautify.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/fs/test.js" type="text/javascript"></script>
    <script type="text/javascript">
        var ctx='/fs';
    </script>
</head>
<body>
<table style="width: 100%;margin-top: 5px;" cellpadding="0" cellspacing="0">
    <tr>
        <td style="width: 180px;vertical-align: top;">
            <ul id="api-list"></ul>
        </td>
        <td style="vertical-align: top">
            <div style="padding: 10px;">
                <form id="api-form" name="api-form" method="post" target="_blank" enctype="multipart/form-data">
                    <div id="api-url">
                        <span class="name"></span>&nbsp;&nbsp;
                        <input name="_url" size="30"/>
                        方法：<select name="_method">
                        <option value="get">get</option>
                        <option value="post">post</option>
                        <option value="put">put</option>
                        <option value="delete">delete</option>
                    </select>
                    </div>
                    <ul id="api-params"></ul>
                    <p><input id="api-submit" type="button" value="提 交" onclick="doSubmit(this.form);"/></p>

                    <div style="color: #999;">注:{id}要换成对应的id</div>
                </form>
            </div>
            <div>
                <textarea id="api-result"></textarea>
            </div>
        </td>
    </tr>
</table>
</body>
</html>
