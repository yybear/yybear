<%@ page session="false" pageEncoding="UTF-8" %>
<html>
<head>
    <title>spam</title>
    <meta name="biz" content="spam"/>
</head>
<body>
<iframe src="/cs/spam/index" style="width: 100%;" frameborder="0" id="c-frame" scrolling="auto;"></iframe>
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
