<%@ page session="false" pageEncoding="UTF-8" %>
<html>
<head>
    <title>search</title>
    <meta name="biz" content="search"/>
</head>
<body>
<iframe src="/searcher/console.do" style="width: 100%;" frameborder="0" id="c-frame" scrolling="auto;"></iframe>
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
