$.tplInit=function(baseParam){
    var nameEl= $('#tpl-name');
    var editor = CodeMirror.fromTextArea($('#tpl-value')[0], {
        mode: "text/html",
        tabMode: "indent",
        lineNumbers: true,
        lineWrapping: true
    });
    var loadTpl = function () {
        var tplName = nameEl.val();
        if (tplName) {
            $.post('loadTpl.do?' + baseParam, {tplName:tplName}, function (data) {
                editor.setValue(data);
                editor.focus();
            });
        }
    };
    loadTpl();
    $('#tpl-name').change(loadTpl);
    $('#add-tpl').click(function(){
        var name = window.prompt("请输入模板名", "");
        if (name) {
            nameEl.prepend('<option>' + name + '</option>');
            nameEl.val(name);
            editor.setValue('');
            editor.focus();
        }
    });
    $('#remote-tpl').click(function () {
        var tplName = nameEl.val();
        if (tplName && confirm("确定要删除吗?")) {
            $.post('remoteTpl.do?' + baseParam, {tplName:tplName}, function () {
                nameEl.find("option:selected").remove();
                loadTpl();
            });
        }
    });
    $('#save-tpl').click(function () {
        var tplName = nameEl.val();
        if(!tplName){
            alert('请先创建模板');
            return;
        }
        var value = editor.getValue();
        $.post('saveTpl.do?' + baseParam, {tplName:tplName,tplValue:value}, function () {
            $('#save-info').html('<span style="color:red;">保存成功</span>').find('span').fadeOut("slow");
        });
    });
}