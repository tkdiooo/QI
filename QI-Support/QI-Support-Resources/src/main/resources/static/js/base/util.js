/**
 * 工具类
 */

/**
 * 创建css/js文件
 */
function createjscssfile(filename, filetype) {
    var file;
    if (filetype === "js") {
        file = document.createElement('script');
        file.setAttribute("type", "text/javascript");
        file.setAttribute("src", filename);
    } else if (filetype === "css") {
        file = document.createElement("link");
        file.setAttribute("rel", "stylesheet");
        file.setAttribute("type", "text/css");
        file.setAttribute("href", filename);
    }
    return file;
}

/**
 * 动态加载一个js/css文件
 *
 * @param filename
 * @param filetype
 */
function loadjscssfile(filename, filetype) {
    var file = createjscssfile(filename, filetype);
    if (typeof file !== "undefined") document.getElementsByTagName("head")[0].appendChild(file);
}

/**
 * 替换已经加载的js/css文件
 *
 * @param oldfilename
 * @param newfilename
 * @param filetype
 */
function replacejscssfile(oldfilename, newfilename, filetype) {
    var targetelement = (filetype === "js") ? "script" : (filetype === "css") ? "link" : "none";
    var targetattr = (filetype === "js") ? "src" : (filetype === "css") ? "href" : "none";
    // 获取所有的script/link标签
    var allsuspects = document.getElementsByTagName(targetelement);
    for (var i = allsuspects.length; i >= 0; i--) {
        // 如果不等于空，并且匹配
        if (allsuspects[i] && allsuspects[i].getAttribute(targetattr)
            && allsuspects[i].getAttribute(targetattr).indexOf(oldfilename) !== -1) {
            // 创建新的文件
            var newelement = createjscssfile(newfilename, filetype);
            // 替换
            allsuspects[i].parentNode.replaceChild(newelement, allsuspects[i]);
        }
    }
}

/**
 * 删除已经加载过的js/css文件
 *
 * @param filename
 * @param filetype
 */
function removejscssfile(filename, filetype) {
    var targetelement = (filetype === "js") ? "script" : (filetype === "css") ? "link" : "none";
    var targetattr = (filetype === "js") ? "src" : (filetype === "css") ? "href" : "none";
    var allsuspects = document.getElementsByTagName(targetelement);
    for (var i = allsuspects.length; i >= 0; i--) {
        if (allsuspects[i] && allsuspects[i].getAttribute(targetattr)
            && allsuspects[i].getAttribute(targetattr).indexOf(filename) !== -1)
            allsuspects[i].parentNode.removeChild(allsuspects[i]);
    }
}

/**
 * 替换域，跨域调用方法使用
 */
function domain() {
    var _surl = String(document.domain);
    if (_surl.lastIndexOf(".") !== -1) {
        var _surl1 = _surl.substring(0, _surl.lastIndexOf("."));
        if (_surl1.lastIndexOf(".") !== -1) {
            _surl1 = _surl1.substring(_surl1.lastIndexOf(".") + 1, _surl1.length);
        }
        _surl = _surl1 + _surl.substring(_surl.lastIndexOf("."), _surl.length);
    }
    document.domain = _surl;
}

/*-------------------------------------------------Date对象自定义方法-------------------------------------------------*/
/**
 * Date对象添加Format方法
 * @param fmt
 * @returns {*}
 * @constructor
 */
Date.prototype.Format = function (fmt) {
    var o =
        {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
Date.prototype.addDays = function (d) {
    this.setDate(this.getDate() + d);
};
Date.prototype.addWeeks = function (w) {
    this.addDays(w * 7);
};
Date.prototype.addMonths = function (m) {
    var d = this.getDate();
    this.setMonth(this.getMonth() + m);

    if (this.getDate() < d)
        this.setDate(0);
};
Date.prototype.addYears = function (y) {
    var m = this.getMonth();
    this.setFullYear(this.getFullYear() + y);

    if (m < this.getMonth()) {
        this.setDate(0);
    }
};

//毫秒转换成置顶日期格式
var format = function (time, format) {
    var t = new Date(time);
    var tf = function (i) {
        return (i < 10 ? '0' : '') + i
    };
    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function (a) {
        switch (a) {
            case 'yyyy':
                return tf(t.getFullYear());
                break;
            case 'MM':
                return tf(t.getMonth() + 1);
                break;
            case 'mm':
                return tf(t.getMinutes());
                break;
            case 'dd':
                return tf(t.getDate());
                break;
            case 'HH':
                return tf(t.getHours());
                break;
            case 'ss':
                return tf(t.getSeconds());
                break;
        }
    })
}
/*-------------------------------------------------Date对象自定义方法-------------------------------------------------*/
/*------------------------------------------------Number对象自定义方法------------------------------------------------*/
Number.prototype.add = function (arg) {
    var r1, r2, m;
    try {
        r1 = arg.toString().split(".")[1].length;
    } catch (e) {
        r1 = 0;
    }
    try {
        r2 = this.toString().split(".")[1].length;
    } catch (e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2));
    return (arg * m + this * m) / m;
};
Number.prototype.sub = function (arg) {
    var r1, r2, m, n;
    try {
        r1 = arg.toString().split(".")[1].length;
    } catch (e) {
        r1 = 0;
    }
    try {
        r2 = this.toString().split(".")[1].length;
    } catch (e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2));
    n = (r1 >= r2) ? r1 : r2;
    return ((arg * m - this * m) / m).toFixed(n);
};
Number.prototype.mul = function (arg) {
    var m = 0, s1 = arg.toString(), s2 = this.toString();
    try {
        m += s1.split(".")[1].length;
    } catch (e) {
    }
    try {
        m += s2.split(".")[1].length;
    } catch (e) {
    }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
};
Number.prototype.div = function (arg) {
    var t1 = 0, t2 = 0, r1, r2;
    try {
        t1 = this.toString().split(".")[1].length;
    } catch (e) {
    }
    try {
        t2 = arg.toString().split(".")[1].length;
    } catch (e) {
    }
    with (Math) {
        r1 = Number(this.toString().replace(".", ""));
        r2 = Number(arg.toString().replace(".", ""));
        return (r1 / r2) * pow(10, t2 - t1);
    }
};

/*------------------------------------------------Number对象自定义方法------------------------------------------------*/

/**
 * 处理数字
 * @param num 要四舍五入的数
 * @param v 表示要保留的小数位数
 * @returns {number}
 */
function decimal(num, v) {
    var vv = Math.pow(10, v);
    return Math.round(num * vv) / vv;
}