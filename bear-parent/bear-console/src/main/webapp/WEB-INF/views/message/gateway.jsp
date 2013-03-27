<%@ page session="false" pageEncoding="UTF-8" %>
<%@ include file="../common/includes.jsp" %>
<html>
<head>
    <title>biz</title>
    <meta name="biz" content="message"/>
    <meta name="tab" content="gateway"/>
    <style>
    	#testResult{
    		margin-left: 10px;
    	}
    </style>
</head>
<body>
<iframe src="/cs/gateway" style="width: 100%;" frameborder="0" id="c-frame" scrolling="auto;"></iframe>
<script type="text/javascript">
    function doResize() {
        $('#c-frame').height($(window).height() - 70);
    }
    $(function () {
        doResize();
        $(window).resize(doResize);
    });
</script>
</body>
</html>
