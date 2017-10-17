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
/**
 * 重写window.alert方法，用layer控件替换
 * @param msg
 * @param callback
 * @param data
 */
window.alert = function (msg, callback, data) {
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
        handler: null
    };
    if (url.indexOf('?') !== -1) {
        url += '&ajaxTimeFresh=' + Math.random();
    } else {
        url += '?ajaxTimeFresh=' + Math.random();
    }
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
        beforeSend: function () {
            if (plugin.settings.waiting) {
                layer.load(2, {
                    shade: [0.8, '#393D49']
                });
            }
        },
        success: function (data, textStatus, request) {
            if (plugin.settings.waiting) {
                layer.closeAll('loading');
            }
            if (data.attachs) {
                $('#_csrf').val(data.attachs._csrf.token).attr('name', data.attachs._csrf.parameterName);
            }
            if (plugin.settings.handler !== null && plugin.settings.handler !== undefined) {
                invoke(plugin.settings.handler, data);
            } else {
                alert(data.messages.join('<br/>'));
            }
        },
        error: function (XMLHttpRequest, ajaxOptions, thrownError) {
            if (plugin.settings.waiting) {
                layer.closeAll('loading');
            }
            var json = JSON.parse(XMLHttpRequest.responseText);
            if (json.attachs.messages_details) {
                $.each(json.attachs.messages_details.messages, function (key, value) {
                    layer.tips(value, '#' + key, {
                        tipsMore: true, time: 10000
                    });
                })
            }
            alert(json.messages.join('<br/>'), function () {
                to_url(json.attachs.url);
            });
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
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded; charset=utf-8',
        dataType: 'html',
        waiting: true,
        cache: false,
        async: true
    };
    if (url.indexOf('?') !== -1) {
        url += '&ajaxTimeFresh=' + Math.random();
    } else {
        url += '?ajaxTimeFresh=' + Math.random();
    }
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
        beforeSend: function () {
            if (plugin.settings.waiting) {
                layer.load(2, {
                    shade: [0.8, '#393D49']
                });
            }
        },
        success: function (data) {
            if (plugin.settings.waiting) {
                layer.closeAll();
            }
            container.css({
                opacity: '0.0'
            }).html(data).delay(50).animate({
                opacity: '1.0'
            }, 300);
            if (data.attachs) {
                $('#_csrf').val(data.attachs._csrf.token).attr('name', data.attachs._csrf.parameterName);
            }
        },
        error: function (XMLHttpRequest, ajaxOptions, thrownError) {
            if (plugin.settings.waiting) {
                layer.closeAll();
            }
            var json = JSON.parse(XMLHttpRequest.responseText);
            alert(json.messages, function () {
                to_url(json.attachs.url);
            });
        }
    });
}

/**
 * dataTables加载
 * @param url 请求路径
 * @param container: 渲染的容器jQuery.ID
 * @param columns: 列字段处理
 * @param params: 请求参数
 * @param destorys: 是否销毁
 */
function matchTable(url, container, columns, params, destorys) {
    if (destorys) {
        if ($.fn.dataTable.isDataTable(container)) {
            $(container).DataTable().destroy();
        }
    }
    //初始化表格
    return $(container).DataTable(
        {
            scrollX: true,
            bServerSide: true,
            ordering: false,
            searching: false, //禁用原生搜索
            bLengthChange: true,
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
                // 请求参数封装
                data.condition = params;
                // 请求设置
                var opt = {
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
                };
                //ajax请求数据
                ajax_action(url, JSON.stringify(data), opt);
            },
            columns: columns
        });
}

function to_url(url) {
    if (self !== top) {
        parent.window.location.href = url;
    } else {
        window.location.href = url;
    }
}