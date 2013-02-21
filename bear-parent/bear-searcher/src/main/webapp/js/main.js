function gotoPage(f, v) {
    $('#s-form input[name=index]').val(v).parent().submit();
    return false;
}

function doSearchSubmit(solrUrl,defWord,keyword) {
    var s = keyword.value;
    if (s != defWord && !/^ *$/.test(s)) {
        window.open(solrUrl + '/?keyword=' + encodeURIComponent(s));
        keyword.value = defWord;
        keyword.blur();
    }
    return false;
}

function initSearch(solrUrl,defWord,id) {
    $('#' + id).on({
        'focus':function () {
            if (this.value.replace(/^\s+|\s+$/g, "") == defWord)
                this.value = '';
            $(this).removeClass('search-tip');
        },
        'blur':function () {
            if (this.value.replace(/^\s+|\s+$/g, "") == '') {
                this.value = defWord;
                $(this).addClass('search-tip');
            }
        }
    }).autocomplete({
        deferRequestBy:200,
        maxHeight:500,
        width:280,
        fnFormatResult:function (value) {
            return ['<div class="ac-item"><img src="', solrUrl, '/img/', value.icon, '" alt=""/><span>', value.title, '</span><strong>', value.bn, (value.cn ? '&nbsp;›&nbsp;' + value.cn : ''), value.body,'</strong></div>'].join('');
        },
        onSelect:function (value, data, keyword) {
            var win = window.open(value.url);
            if (!win) {
                window.alert("请禁用弹出式窗口拦截");
                window.open(value.url);
            }
            keyword.val(defWord);
            keyword.blur();
        },
        serviceUrl:solrUrl + '/suggest.do'
    });
    $('#pf-tools').hover(function () {
        $(this).addClass('active');
    }, function () {
        $(this).removeClass('active');
    });
}