<%@ page session="false" pageEncoding="UTF-8" %>
<%@ include file="../common/includes.jsp" %>
<html>
<head>
    <title>fs</title>
    <meta name="biz" content="fs"/>
    <meta name="tab" content="video"/>
    <script src="${ctx}/static/js/fs/jwplayer/jwplayer.js" type="text/javascript"></script>
</head>
<body>
<div id="mediaplayer">JW Player goes here</div>
<script type="text/javascript">
    jwplayer("mediaplayer").setup({
        flashplayer: "${ctx}/static/js/fs/jwplayer/player.swf",
        file: "/fs/video/ai/${param.id}",
        image: "/fs/video/ai-c1/${param.id}",
        provider: 'http',
        streamer: 'start',
        controlbar: 'bottom',
        width: '800',
        height: '500'
    });
</script>
</body>
</html>
