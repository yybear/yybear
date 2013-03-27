var apis = [
    '文件',
    {
        name:'文件列表',
        url:'files',
        method:'post',
        params:[
            {name:'业务名', code:'bizKey', def:'image'},
            {name:'所有者', code:'owner', def:'1'}
        ]
    },
    {
        name:'指定用户文件列表',
        url:'user/files',
        method:'post',
        params:[
            {name:'用户id', code:'userId', def:'1'},
            {name:'业务名', code:'bizKey', def:'image'},
            {name:'所有者', code:'owner', def:'1'},
            {name:'开始位置', code:'start',def:'0'},
            {name:'个数', code:'limit', def:20}
        ]
    },
    {
        name:'批量删除文件',
        url:'files/remove',
        method:'post',
        params:[
            {name:'业务名', code:'bizKey', def:'image'},
            {name:'所有者', code:'owner', def:'1'}
        ]
    },
    {
        name:'获取文件信息',
        url:'file',
        method:'post',
        params:[
            {name:'文件id', code:'id', def:'1'}
        ]
    },
    {
        name:'更新文件信息',
        url:'file/update',
        method:'post',
        params:[
            {name:'文件id', code:'id', def:'1'},
            {name:'名称', code:'name', def:'image'},
            {name:'访问权限级别', code:'scope'},
            {name:'自定义属性,(必须为合法json map)', code:'attr'},
            {name:'文件', code:'file', type:'file'}
        ]
    },
    {
        name:'上传文件',
        url:'upload',
        method:'post',
        params:[
            {name:'用户id', code:'userId', def:'1'},
            {name:'业务名', code:'bizKey', def:'image'},
            {name:'所有者', code:'owner', def:'1'},
            {name:'访问权限级别', code:'scope'},
            {name:'自定义属性,(必须为合法json map)', code:'attr'},
            {name:'文件', code:'file', type:'file'},
            {name:'文件', code:'file1', type:'file'}
        ]
    },
    {
        name:'图片变换',
        url:'image/transform',
        method:'post',
        params:[
            {name:'文件id', code:'id', def:'1'},
            {name:'操作', code:'action', def:'{op:\'crop\',left:10,top:20,width:100,height:200}'}
        ]
    },
    {
        name:'根据id删除文件',
        url:'file/remove',
        method:'post',
        params:[
            {name:'文件id,可多个', code:'id', def:'1'}
        ]
    }
];
function getType(object) {
    var _t;
    return ((_t = typeof(object)) == "object" ? object == null && "null" || Object.prototype.toString.call(object).slice(8, -1) : _t).toLowerCase();
}
function choiceApi(index) {
    var api = apis[index];
    var apiform = document.getElementById('api-form');
    $('#api-url span.name').html(api.name);
    apiForm._url.value = ctx + (api.url.indexOf('/') == 0 ? api.url : '/api/' + api.url);
    apiForm._method.value = api.method;
    var arr = [];
    if (api.params) {
        for (var i = 0; i < api.params.length; i++) {
            var param = api.params[i];
            arr.push('<li>');
            if ('file' == api.params[i].type) {
                arr.push(param.name + '-' + param.code + '&nbsp;&nbsp;<input type="file" class="f" name="' + param.code + '" value="' + (param.def != undefined ? param.def : '') + '" size="50"/>');
            } else if ('checkbox' == api.params[i].type) {
                var arr1 = "";
                for (var j = 0; j < param.values.length; j++) {
                    var chkapi = param.values[j];
                    arr1 = arr1 + '<input type="checkbox" name="' + param.code + '" value="' + chkapi.value + '" />' + chkapi.name;
                }
                arr.push(param.name + '-' + param.code + '&nbsp;&nbsp;' + arr1);
            }
            else if ('select' == api.params[i].type) {
                var arr1 = "";
                for (var j = 0; j < param.values.length; j++) {
                    var chkapi = param.values[j];
                    arr1 = arr1 + '<option  value="' + chkapi.value + '">' + chkapi.name + '</option>';
                }
                arr.push(param.name + '-' + param.code + '&nbsp;&nbsp; <select name="' + param.code + '" class="ipt validate[required]">' + arr1 + '</select>');
            }
            else {
                arr.push(param.name + '-' + param.code + '&nbsp;&nbsp;<input type="text" name="' + param.code + '" value="' + (param.def != undefined ? param.def : '') + '" size="50"/>');
            }
            if (param.doc) {
                arr.push(param.doc);
            }
            arr.push('</li>');
        }
    }
    $('#api-params').html(arr.join(''));
    $("#api-result").val('');
}
function appendSid(a, url) {
    var sid = $('#api-ticket').html();
    window.open(a.href + '&url=' + encodeURIComponent(url + '?sid=' + sid));
    return false;
}
function doSubmit(form) {
    var url = form._url.value;
    var file = $('#api-params input.f');
    if (file.val()) {
        form.action = url;
        form.submit();
        return;
    }
    $.ajax({
        type:"POST",
        dataType:'text',
        url:url,
        data:$("#api-form").serialize(),
        success:function (data) {
            $("#api-result").val(js_beautify(data));
        }
    });
}
var api, apiForm, url = location.href;
url = url.substring(0, url.indexOf('/', 8));
$(function () {
    var arr = [];
    for (var i = 0; i < apis.length; i++) {
        var _api = apis[i];
        if (getType(_api) == 'string') {
            arr.push('<li class="title"><h3>' + _api + '</h3></li>');
        } else {
            arr.push('<li><a href="javascript:choiceApi(' + i + ')">' + _api.name + '</a></li>');
        }
    }
    $('#api-list').append(arr.join(''));
    apiForm = document.getElementById('api-form');
    choiceApi(1);
});