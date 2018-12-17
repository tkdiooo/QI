/**
 * <br>
 *
 * @author 张麒
 */
$.fn.serializeJson = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

$.fn.serializeString = function () {
    var serializeObj = "";
    this.attr('name', function (index, text) {
        if (index === 0) serializeObj += text;
        else serializeObj += "," + text;
    });
    return serializeObj;
};

$.fn.clearForm = function () {
    $(':input', this)
        .not(':button, :submit, :reset, :hidden')
        .val('')
        .removeAttr('checked')
        .removeAttr('selected');
};

/**
 * 重写window.alert方法，用layer控件替换
 * @param msg
 * @param callback
 * @param data
 */
window.alert = function (msg, callback, data) {
    if (self !== top)
        parent.layer.msg(msg, {
            time: 0,
            shade: [0.8, '#393D49'],
            area: '300px',
            btnAlign: 'c',
            btn: ['确认']
            , yes: function (index) {
                parent.layer.close(index);
                invoke(callback, data);
            }
        });
    else
        layer.msg(msg, {
            time: 0,
            shade: [0.8, '#393D49'],
            area: '300px',
            btnAlign: 'c',
            btn: ['确认']
            , yes: function (index) {
                layer.close(index);
                invoke(callback, data);
            }
        });
};
/**
 * 重写window.confirm方法，用layer控件替换
 * @param msg
 * @param callback
 */
window.confirm = function (msg, callback) {
    layer.msg(msg, {
        time: 0,
        shade: [0.8, '#393D49'],
        area: '300px',
        btnAlign: 'c',
        btn: ['确认', '取消']
        , yes: function (index) {
            invoke(callback);
            layer.close(index);
        }
    });
};
/**
 * 重写window.open方法，用layer控件替换
 * @param opt
 */
window.open = function (opt) {
    var index = layer.load();
    openDialog(opt);
    layer.close(index);
};

/**
 * 执行方法
 */
function invoke(callback, data) {
    if (callback !== null && callback !== undefined) {
        if (typeof callback === 'function') {
            callback(data);
        } else {
            var method = eval(callback);
            new method(data);
        }
    }
}

/**
 * jQuery.hotkeys 键盘热键绑定方法
 * @param keys
 * @param callback
 */
function bindHotKey(keys, callback) {
    jQuery.hotkeys.add(keys, function () {
        invoke(callback);
    });
}

/**
 * 回车键事件绑定
 * @param elements
 * @param callback
 */
function bindEnterHotKey(elements, callback) {
    $('#' + elements).bind('keydown', function (evt) {
        if (evt.keyCode === 13) invoke(callback);
    });
}

/**
 * dialog窗口
 * @param opt
 */
function openDialog(opt) {
    var defaults = {
        type: 1,
        title: '弹出框',
        shadeClose: true,
        shade: [0.8, '#393D49'],
        move: false
    };
    var plugin = this;
    plugin.settings = $.extend({}, defaults, opt);
    layer.open(plugin.settings);
}

var win = {};
var browser = {};

/**
 * 初始化窗口高度、宽度
 */
function initSize() {
    //获取当前浏览器宽度和高度
    //win.width = typeof window.innerWidth == 'undefined' ? document.documentElement.clientWidth : window.innerWidth;
    //win.height = typeof window.innerHeight == 'undefined' ? document.body.scrollHeight : window.innerHeight;
    win.width = $(window).width();
    win.height = $(document).height();
    // 高度最小600
    if (win.height < 600) {
        win.height = 600;
        $(document.body).height(win.height);
    }
    // 宽度最小960
    if (win.width < 960) {
        win.width = 960;
        $(document.body).width(win.width);
    }
}

/**
 * 初始化浏览器判断
 */
function initBrowser() {
    var userAgent = navigator.userAgent, rMsie = /(msie\s|trident.*rv:)([\w.]+)/, rFirefox = /(firefox)\/([\w.]+)/,
        rOpera = /(opera).+version\/([\w.]+)/, rChrome = /(chrome)\/([\w.]+)/, rSafari = /version\/([\w.]+).*(safari)/;
    var ua = userAgent.toLowerCase();
    // 判断是否是IE
    var match = rMsie.exec(ua);
    if (match !== null) {
        browser.type = 'IE';
        browser.version = match[2];
    } else if ((match = rFirefox.exec(ua)) !== null) {
        browser.type = match[1];
        browser.version = match[2];
    } else if ((match = rOpera.exec(ua)) !== null) {
        browser.type = match[1];
        browser.version = match[2];
    } else if ((match = rChrome.exec(ua)) !== null) {
        browser.type = match[1];
        browser.version = match[2];
    } else if ((match = rSafari.exec(ua)) !== null) {
        browser.type = match[1];
        browser.version = match[2];
    } else if ((match = rSafari.exec(ua)) !== null) {
        browser.type = match[1];
        browser.version = match[2];
    } else {
        browser.type = '';
        browser.version = '';
    }
}

/**
 * 禁用鼠标右键
 */
$(document).on('contextmenu', function () {
    return false;
});

$(function () {
    initBrowser();
    initSize();
});


/**
 * ajax上传
 * @param url 请求路径
 * @param data 数据
 * @param opt 设置
 */
function ajax_upload(url, data, opt) {
    var defaults = {
        type: 'POST',
        dataType: 'json',
        waiting: true,
        handler: null,
        callback: null
    };
    if (url.indexOf('?') !== -1) {
        url += '&ajaxTimeFresh=' + Math.random();
    } else {
        url += '?ajaxTimeFresh=' + Math.random();
    }
    data = setCSRF(data);
    var plugin = this;
    plugin.settings = $.extend({}, defaults, opt);


    $.ajaxFileUpload({
        url: url,    //需要链接到服务器地址
        data: data,
        secureuri: false,
        type: plugin.settings.type,
        fileElementId: plugin.settings.fileElementId, //文件选择框的id属性
        dataType: plugin.settings.dataType,
        beforeSend: function () {
            console.info('beforeSend')
            if (plugin.settings.waiting) {
                // showWaiting();
            }
        },
        success: function (data, textStatus, request) {
            console.info(data)
            if (plugin.settings.waiting) {
                closeWaiting();
            }
            if (data.attachs) {
                $('#_csrf').val(data.attachs._csrf.token).attr('name', data.attachs._csrf.parameterName);
            }
            if (plugin.settings.handler !== null && plugin.settings.handler !== undefined) {
                invoke(plugin.settings.handler, data);
            }
            else if (plugin.settings.callback !== null && plugin.settings.callback !== undefined) {
                alert(data.messages.join('<br/>'), function () {
                    invoke(plugin.settings.callback, data);
                });
            }
            else {
                alert(data.messages.join('<br/>'));
            }
        },
        error: function (XMLHttpRequest, ajaxOptions, thrownError) {
            console.info(XMLHttpRequest.responseText)
            if (plugin.settings.waiting) {
                closeWaiting();
            }
            if (null != XMLHttpRequest.responseText && '' !== XMLHttpRequest.responseText) {
                if (isJson(XMLHttpRequest.responseText)) {
                    var json = JSON.parse(XMLHttpRequest.responseText);
                    if (json.attachs.messages_details) {
                        alert(json.messages.join('<br/>'), function () {
                            $.each(json.attachs.messages_details.messages, function (key, value) {
                                layer.tips(value, '#' + key, {
                                    tipsMore: true, time: 10000
                                });
                            })
                        });
                    } else {
                        alert(json.messages.join('<br/>'), function () {
                            if (json.attachs.url) {
                                window.location.href = json.attachs.url;
                            }
                        });
                    }
                } else {
                    $('body').html(XMLHttpRequest.responseText);
                }
            } else {
                alert('网络出现错误，请稍后尝试');
            }
        }
    });
}

/**
 * 发送ajax请求
 * @param url 请求路径
 * @param data 数据
 * @param opt 设置
 */
function ajax_action(url, data, opt) {
    var defaults = {
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded; charset=utf-8',
        dataType: 'json',
        waiting: true,
        cache: false,
        async: true,
        handler: null,
        callback: null
    };
    if (url.indexOf('?') !== -1) {
        url += '&ajaxTimeFresh=' + Math.random();
    } else {
        url += '?ajaxTimeFresh=' + Math.random();
    }
    data = setCSRF(data);
    var plugin = this;
    plugin.settings = $.extend({}, defaults, opt);
    $.ajax({
        url: url,
        data: data,
        type: plugin.settings.type,
        dataType: plugin.settings.dataType,
        contentType: plugin.settings.contentType,
        cache: plugin.settings.cache,
        async: plugin.settings.async,
        // crossDomain: true,
        // xhrFields: {
        //     withCredentials: true
        // },
        beforeSend: function () {
            if (plugin.settings.waiting) {
                showWaiting();
            }
        },
        success: function (data, textStatus, request) {
            if (plugin.settings.waiting) {
                closeWaiting();
            }
            if (data.attachs) {
                $('#_csrf').val(data.attachs._csrf.token).attr('name', data.attachs._csrf.parameterName);
            }
            if (plugin.settings.handler !== null && plugin.settings.handler !== undefined) {
                invoke(plugin.settings.handler, data);
            }
            else if (plugin.settings.callback !== null && plugin.settings.callback !== undefined) {
                alert(data.messages.join('<br/>'), function () {
                    invoke(plugin.settings.callback, data);
                });
            }
            else {
                alert(data.messages.join('<br/>'));
            }
        },
        error: function (XMLHttpRequest, ajaxOptions, thrownError) {
            if (plugin.settings.waiting) {
                closeWaiting();
            }
            if (null != XMLHttpRequest.responseText && '' !== XMLHttpRequest.responseText) {
                if (isJson(XMLHttpRequest.responseText)) {
                    var json = JSON.parse(XMLHttpRequest.responseText);
                    if (json.attachs.messages_details) {
                        alert(json.messages.join('<br/>'), function () {
                            $.each(json.attachs.messages_details.messages, function (key, value) {
                                layer.tips(value, '#' + key, {
                                    tipsMore: true, time: 10000
                                });
                            })
                        });
                    } else {
                        alert(json.messages.join('<br/>'), function () {
                            if (json.attachs.url) {
                                window.location.href = json.attachs.url;
                            }
                        });
                    }
                } else {
                    $('body').html(XMLHttpRequest.responseText);
                }
            } else {
                alert('网络出现错误，请稍后尝试');
            }
        }
    });
}

/**
 * ajax加载页面
 * @param url 请求路径
 * @param container 渲染的容器对象
 * @param data 提交的数据
 * @param opt 设置
 */
function load_url(url, container, data, opt) {
    var defaults = {
        type: 'GET',
        contentType: 'application/x-www-form-urlencoded; charset=utf-8',
        dataType: 'html',
        waiting: true,
        effect: true,
        cache: false,
        async: true
    };
    if (url.indexOf('?') !== -1) {
        url += '&ajaxTimeFresh=' + Math.random();
    } else {
        url += '?ajaxTimeFresh=' + Math.random();
    }
    data = setCSRF(data);
    var plugin = this;
    plugin.settings = $.extend({}, defaults, opt);
    $.ajax({
        url: url,
        data: data,
        type: plugin.settings.type,
        dataType: plugin.settings.dataType,
        contentType: plugin.settings.contentType,
        cache: plugin.settings.cache,
        async: plugin.settings.async,
        // crossDomain: true,
        // xhrFields: {
        //     withCredentials: true
        // },
        beforeSend: function () {
            if (plugin.settings.waiting) {
                showWaiting();
            }
        },
        success: function (data, textStatus, request) {
            var _csrf = request.getResponseHeader('_csrf');
            if (_csrf) {
                _csrf = JSON.parse(_csrf);
                $('#_csrf').val(_csrf.token).attr('name', _csrf.parameterName);
            }
            if (plugin.settings.waiting) {
                closeWaiting();
            }
            if (plugin.settings.effect) {
                container.css({
                    opacity: '0.5'
                }).html(data).delay(50).animate({
                    opacity: '1.0'
                }, 300);
            } else {
                container.html(data);
            }
        },
        error: function (XMLHttpRequest, ajaxOptions, thrownError) {
            if (plugin.settings.waiting) {
                closeWaiting();
            }
            if (null != XMLHttpRequest.responseText && '' !== XMLHttpRequest.responseText) {
                if (isJson(XMLHttpRequest.responseText)) {
                    var json = JSON.parse(XMLHttpRequest.responseText);
                    alert(json.messages.join('<br/>'), function () {
                        if (json.attachs.url) {
                            window.location.href = json.attachs.url;
                        }
                    });
                } else {
                    container.html(XMLHttpRequest.responseText);
                }
            } else {
                alert('网络异常，请稍后尝试');
            }
        }
    });
}

/**
 * dataTables加载
 * @param opt: 设置
 * @param opt.container: 渲染的容器jQuery.ID
 * @param opt.columns: 列字段处理
 * @param opt.buttons: 按钮
 * @param destorys: 是否销毁
 */
function matchDomTable(opt, destorys) {
    if (destorys) {
        if ($.fn.dataTable.isDataTable(opt.container)) {
            $(opt.container).DataTable().destroy();
        }
    }
    var defaults = {
        scrollX: true,
        bSort: true,
        searching: true, //原生搜索
        bLengthChange: false, //禁用数据量选择
        autoColumnSizing: true, // 表头宽度自适应
        renderer: 'bootstrap', //渲染样式：Bootstrap和jquery-ui
        pagingType: 'full_numbers', //分页样式：simple,simple_numbers,full,full_numbers
        // dom: "it<'row'p>",
        // lengthChange: !1,
        language: {
            'sProcessing': '处理中...',
            'sLengthMenu': '显示 _MENU_ 项结果',
            'sZeroRecords': '没有匹配结果',
            'bPaginate': true,
            'bFilter': true,
            'sInfo': '显示第  _START_ 至  _END_ 项结果，共  _TOTAL_ 项，当前位置  第_PAGE_页',
            'sInfoEmpty': '显示第 0 至 0 项结果，共 0 项',
            'sInfoFiltered': '(由  _MAX_ 项结果过滤)',
            'sInfoPostFix': '',
            'sSearch': '搜索:',
            'sUrl': '',
            'sEmptyTable': '表中数据为空',
            'sLoadingRecords': '载入中...',
            'sInfoThousands': ',',
            'oPaginate': {
                'sFirst': '首页',
                'sPrevious': '上页',
                'sNext': '下页',
                'sLast': '末页'
            }
        },
        bDeferRender: false,
        retrieve: true,
        processing: false,
        columns: opt.columns,
        dom: "<'row'<'col-md-6'l<'#toolbar'>><'col-md-6'f>r>t<'row'<'col-md-5 sm-center'i><'col-md-7 text-right sm-center'p>>",
        initComplete: function () {
            // 表格加载完毕，手动添加按钮到表格上
            if (settings.buttons) {
                matchTableButtons(settings);
            }
            // 表格加载完毕，表头宽度自适应
            if (settings.autoColumnSizing) {
                $(opt.container + '_wrapper').find('.dataTables_scrollHeadInner').addClass('fullWidth').find('.table-bordered').addClass('fullWidth');
            }
        }
        // drawCallback: function (settings) {
        //     $('#data_table_wrapper').find('.row').attr('style', 'margin-left:0 !important;margin-right:0 !important;');
        //     $('#data_table_paginate').find('li').find('a').attr('style', 'padding:4px 10px !important;');
        // }
    };
    var settings = $.extend({}, defaults, opt);
    //初始化表格
    return $(opt.container).DataTable(settings);
}

/**
 * dataTables加载
 * @param opt: 设置
 * @param opt.url 请求路径
 * @param opt.container: 渲染的容器jQuery.ID
 * @param opt.columns: 列字段处理
 * @param opt.columnsButtons: 列按钮处理
 * @param opt.params: 请求参数
 * @param opt.buttons: 按钮
 * @param opt.form: 查询条件
 * @param destorys: 是否销毁
 */
function matchAjaxTable(opt, destorys) {
    if (destorys) {
        if ($.fn.dataTable.isDataTable(opt.container)) {
            $(opt.container).DataTable().destroy();
        }
    }
    var defaults = {
        scrollX: true,
        bServerSide: true,
        bSort: true,
        searching: false, //禁用原生搜索
        bLengthChange: false, //禁用数据量选择
        autoColumnSizing: true, // 表头宽度自适应
        renderer: 'bootstrap', //渲染样式：Bootstrap和jquery-ui
        pagingType: 'full_numbers', //分页样式：simple,simple_numbers,full,full_numbers
        language: {
            'sProcessing': '处理中...',
            'sLengthMenu': '显示 _MENU_ 项结果',
            'sZeroRecords': '没有匹配结果',
            'bPaginate': true,
            'bFilter': true,
            'sInfo': '显示第  _START_ 至  _END_ 项结果，共  _TOTAL_ 项，当前位置  第_PAGE_页',
            'sInfoEmpty': '显示第 0 至 0 项结果，共 0 项',
            'sInfoFiltered': '(由  _MAX_ 项结果过滤)',
            'sInfoPostFix': '',
            'sSearch': '搜索:',
            'sUrl': '',
            'sEmptyTable': '表中数据为空',
            'sLoadingRecords': '载入中...',
            'sInfoThousands': ',',
            'oPaginate': {
                'sFirst': '首页',
                'sPrevious': '上页',
                'sNext': '下页',
                'sLast': '末页'
            }
        },
        bDeferRender: false,
        retrieve: true,
        processing: false,
        ajax: function (data, callback, settings) {
            if (opt.form) {
                // 动态参数
                data.condition = eval('(' + (JSON.stringify(opt.params) + JSON.stringify($("#dataTables_form").serializeJson())).replace(/}{/, ',') + ')');
            } else {
                // 请求参数封装
                data.condition = opt.params;
            }
            console.info(data)
            //ajax请求数据
            ajax_action(opt.url, JSON.stringify(data), {
                contentType: 'application/json; charset=utf-8',
                handler: function (result) {
                    setTimeout(function () {
                        //封装返回数据
                        result.result.draw = data.draw;
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(result.result);
                    }, 200);
                }
            });
        },
        columns: opt.columns,
        //行被创建回调
        createdRow: function (row, data, dataIndex) {
            // 构建列字段上操作按钮
                if (settings.columnsButtons) {
                $.each(settings.columnsButtons, function (i, value) {
                    $(row).find('td:last').append('<button type="button" class="' + value.class + '" onclick="' + value.action + '(\'' + data.id + '\');" data-original-title="' + value.text + '" data-placement="right">' + value.icon + value.text + '</button>');
                });
                $("[data-original-title]").tooltip();
            }
        },
        dom: "<'row'<'col-md-6'l<'#toolbar'>><'col-md-6' l<'#formbar'>f>r>t",
        initComplete: function () {
            // 表格加载完毕，手动添加按钮到表格上
            if (settings.buttons) {
                matchTableButtons(settings);
            }
            // 表格加载完毕，手动添加查询条件到表格上
            if (settings.form) {
                matchTableForm(settings);
            }
            // 表格加载完毕，表头宽度自适应
            if (settings.autoColumnSizing) {
                $(opt.container + '_wrapper').find('.dataTables_scrollHeadInner').addClass('fullWidth').find('.table-bordered').addClass('fullWidth');
            }
            $("[data-original-title]").tooltip();
        }
    };
    var settings = $.extend({}, defaults, opt);
    //初始化表格
    return $(opt.container).DataTable(settings);
}

function matchTableButtons(opt) {
    $.each(opt.buttons, function (i, value) {
        var div = $('<div class="btn-group" data-autorun="' + (i + 1) + '" data-original-title="' + value.title + '"></div>');
        // 按钮
        if (value.type === 'button') {
            var button = $('<button type="button" id="' + value.id + '" class="' + value.class + '"><span class="glyphicon">' + value.text + '</span></button>');
            button.click(value.action);
            div.append(button);
        }
        // 选择
        else if (value.type === 'select') {
            var select = $('<button type="button" id="' + value.id + '" class="' + value.class + '" val="">' + value.text + '</button>' +
                '<button type="button" class="' + value.class + ' dropdown-toggle" data-toggle="dropdown">' +
                '<span class="caret"></span><span class="sr-only">' + value.text + '</span></button>');
            var options = $('<ul class="dropdown-menu" role="menu"></ul>');
            $.each(value.options, function (j, opt) {
                var li = $('<li cls="' + opt.class + '" val="' + opt.value + '"><a href="javascript:void(0);">' + opt.text + '</a></li>');
                li.click(function () {
                    selectChange(this, value);
                });
                options.append(li);
            });
            div.append(select);
            div.append(options);
        }
        $("#toolbar").append(div);
    });
}

function matchTableForm(opt) {
    var form = $('<form id="dataTables_form"></form>');
    $.each(opt.form, function (i, value) {
        // 文本框
        if (value.type === 'text') {
            form.append($('<input type="' + value.type + '" name="' + value.id + '" class="form-control input-sm" placeholder="' + value.text + '"/>'));
        } else if (value.type === 'button') {
            var button = $('<button type="button" class="btn btn-primary left-margin-5"><span class="glyphicon">查询</span></button>').click(value.action);
            form.append(button);
        }
    });
    $('#formbar').addClass('dataTables_filter').append(form);
}

function selectChange(obj, opt) {
    var btn = $('#' + opt.id);
    // 获取节点属性
    var text = $(obj).children().text();
    var cls = $(obj).attr('cls');
    var val = $(obj).attr('val');
    // 替换节点属性
    if (undefined === cls) {
        cls = btn.attr('class');
    }
    $(obj).children().text(btn.text());
    $(obj).attr('cls', btn.attr('class'));
    $(obj).attr('val', btn.attr('val'));
    // 设置btn属性
    btn.text(text).removeClass().addClass(cls).attr('val', val);
    btn.next().removeClass().addClass('dropdown-toggle ' + cls);
    invoke(opt.action, val);
}

function to_url(url) {
    if (self !== top) {
        parent.window.location.href = url;
    } else {
        window.location.href = url;
    }
}

function setCSRF(data) {
    var csrf = $('#_csrf');
    if (csrf.length > 0) {
        if (data === undefined) {
            data = {};
        }
        data[csrf.attr('name')] = csrf.val();
    }
    return data;
}

function showWaiting() {
    if (self !== top) parent.layer.load(2);
    else layer.load(2);
}

function closeWaiting() {
    if (self !== top) parent.layer.closeAll('loading');
    else layer.closeAll('loading');
}
