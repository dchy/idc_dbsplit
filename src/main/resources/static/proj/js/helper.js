//格式化时间
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

var VM = {
    //转换器
    convertor: {
        //日期转换
        date: function (cur) {
            var value = typeof (cur) == "function" ? cur() : cur;

            var reg = /^\/Date\((\d+)\)\/$/;
            if (reg.test(value)) {
                value = parseInt(reg.exec(value)[1]);
            }
            date = new Date(value);
            return date.Format("yyyy-MM-dd");
        }
    },

    //状态
    status: [
        { text: "禁用", value: "0" },
        { text: "启用", value: "1" },
    ],
    ///字体集
    fonts:[
        { key: "fa-envelope", value: [] },//email
        { key: "fa-mobile-phone", value: [] },//tel
        { key: " fa-phone", value: [] },//phone
        { key: "fa-calendar", value: [] },//date
        { key: "fa-key", value: ["appid", "appkey"] },//key
        { key: "fa-link", value: ["redirecturi"] },//link
        { key: "fa-exclamation",value:["common"] }//common
    ],
    target: [{ text: "当前窗口", value: "1" }, { text: "新窗口", value: "2" }],
    model: ko.observable({}),

};
//判断空字符正则
var regempty = /\w+/;//字符为空
//查找字体
function findfont(input) {
    var result = VM.fonts[VM.fonts.length - 1].key;
    if (regempty.test(input)) {
        input = input.trim().toLocaleLowerCase();
        $.each(VM.fonts, function () {
            for (var i = 0; i < this.value.length;i++){
                if (this.value[i] == input) {
                    result = this.key;
                    return false;
                }
            }
        });
    }
    return result;
}

//递归查找
function recursivesearch(json, property,arrayproperty,value) {
    if (json.hasOwnProperty(property)) {
        if (json[property] == value) {
            return json;
        } else {
            if (json.hasOwnProperty(arrayproperty)) {
                var temp = null
                for (var i = 0; i < json[arrayproperty].length; i++) {
                    temp = recursivesearch(json[arrayproperty][i],property,arrayproperty,value);
                    if (temp != null) {
                        return temp;
                    }
                }
            }
        }
    }
    return null;
}
//分页
//分页计算
function pagingCompute(data, minuend) {
    if (minuend == null || minuend <= 0) {
        minuend = 10;
    }
    var cur = data.pageNumber;
    var max = data.totalPages;
    var divisor = minuend / 2;
    var a = 1;
    var b = max;
    var result;
    if ((cur - divisor) > 0) {
        a = cur - divisor;
    }

    if ((cur + divisor) > max) {
        a -= ((cur + divisor) - max);
    }

    var result = [];

    for (var i = a; i <= a + minuend; i++) {
        if (i > 0 && i <= max) {
            result.push(i);
        }
    }
    return result;
}

//改变地址栏
function changeUrl(data) {
    if (typeof (history).hasOwnProperty("replaceState")) {
        var href = location.href;
        var index = href.indexOf("?");
        var curUrl = href;
        if (index > 0) {
            curUrl = href.substr(0, index) + "?";
        } else {
            curUrl += "?";
        }
        history.replaceState(null, null, curUrl + data);
    }
}


function postData(
    ///请求url
    url,
    ///数据
    data,
    ///成功回调
    callback,
    ///错误回调
    error) {
    if (url == null || url == "") {
        url = window.location.href;
    }
    if (callback == null || typeof (callback) != "function") {
        callback = function (e) {
            debugger;
        };
    }
    if (error == null || typeof (error) != "function") {
        error = function (e) {
            debugger;
        };
    }
    $.ajax({
        type: "post",
        data: data,
        url: url,
        success: callback,
        error: error
    });
}

//数据转json
function arrayToJson(array) {
    var json = {};
    for (var i = 0; i < array.length; i++) {
        var temp = array[i];
        json[temp["name"]] = temp["value"];
    }
    return json;
}
//绝对url匹配模式
//var absoluteUrlRegex = /([^\?]+)/;
///跳转
function skip(
    ///当前索引
    index,
    ///父级
    parent,
    //事件对象
    event) {
    if (index <= 0 || index == parent.pageNumber || index > parent.totalPages) {
        return;
    }

    var form = $(event.target).parents("form").first();
    form.find("input[name=pageNumber]").val(index);
    changeUrl(form.serialize());
    var json = arrayToJson(form.serializeArray());
    postData(form.attr("action"), json, parent["success"]);

}

//默认的初始化函数
function defaultSuccess(obj, key) {
    if (obj == null) {
        obj = VM;
    }
    if (key == null) {
        key = "model";
    }
    $(function () {
        var success = function (data) {
            data.success = success;
            //data.absoluteUrl = true;
            obj[key](data);
        }
        ///初始化Body
        postData(null, null, success);
    });
}


function search(obj) {
    var form = $(obj).parents("form").first();
    form.find("input[name=pageNumber]").val(1);
    form.submit();
}


var message = {
    show:function(input) {
        alert(input);
    }
}

$(function () {
    VM.area=[{text:"南京",value:"15"},
        {text:"R6",value:"11"},
        {text:"北京",value:"12"},
        {text:"合肥",value:"16"},
        {text:"武汉",value:"18"},
        {text:"天津",value:"19"},
        {text:"郑州",value:"20"},
        {text:"重庆",value:"21"},
        {text:"成都",value:"22"},
        {text:"西安",value:"25"},
        {text:"杭州",value:"26"},
        {text:"济南",value:"30"},
        {text:"石家庄",value:"31"},
        {text:"宁波",value:"36"},
        {text:"上海",value:"38"},
        {text:"望京",value:"49"},
        {text:"亦庄",value:"50"},
        {text:"CBD",value:"55"},
        {text:"鹰计划",value:"59"}
    ];

    VM.result=[{text:"数据一致",value:"true"},{text:"数据不一致",value:"false"}];

    //通用转换
    var commonConvert = function (json, val, propertyKey, propertyValue) {
        var value = val;
        if (propertyKey == undefined)
            propertyKey = "value";
        if (propertyValue == undefined)
            propertyValue = "text";
        $(json).each(function () {
            if (this[propertyKey] == val) {
                value = this[propertyValue];
                return false;
            }
        });
        return value;
    }

    VM.convert = function (func, data) {
        var result;
        if (VM.convertor.hasOwnProperty(func)) {
            result = VM.convertor[func](data);
        } else {
            if (VM.hasOwnProperty(func)) {
                result = commonConvert(VM[func], data);
            } else {
                result = "N/A";
            }
        }

        return result;
    }
    ///数据绑定
    ko.applyBindings(VM);

});

