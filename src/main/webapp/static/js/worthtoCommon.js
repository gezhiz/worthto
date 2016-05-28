/**
 * Created by doublez on 16/4/12.
 */
Date.prototype.format = function(format){
    var o = {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(), //day
        "h+" : this.getHours(), //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3), //quarter
        "S" : this.getMilliseconds() //millisecond
    }

    if(/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }

    for(var k in o) {
        if(new RegExp("("+ k +")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
        }
    }
    return format;
}
var openImg = function($imgTag){
    var src = $imgTag.attr('src');
    if(!_.isEmpty(src)) {
        window.open(src, '_blank');
    }
}

var previewImg = function(self){
    var src = $(self).attr('src');
    if(!_.isEmpty(src)) {
        window.open(src, '_blank');
    }
}


$(function(){
    $('.small-img').click(function(){
        openImg($(this));
    });
    $('.middle-img').click(function(){
        openImg($(this));
    });

    $( ".datetimepicker" ).datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        language: "zh-CN",
        autoclose: true,
        startDate:new Date().format("yyyy-MM-dd hh:ii"), //开始时间，在这时间之前都不可选
    });

    $('.viewDetail').each(function(){
        $(this).find('input[name]').attr('disabled','disabled');
        $(this).find('select[name]').attr('disabled','disabled');
        $(this).find('textarea[name]').attr('disabled','disabled');
        $(this).find('[type=submit]').hide();
        $(this).find('[submit]').hide();
    });

    initClipboard();
});

var initClipboard = function(){
    var clipIdBtns = document.querySelectorAll('[copyId]');
    var clipboard = new Clipboard(clipIdBtns);

    clipboard.on('success', function(e) {
        YoYoTip.tip(e.text+'已经复制到剪切板');
    });

    clipboard.on('error', function(e) {
        YoYoTip.alert('',JSON.stringify(e));
    });
    $("[data-toggle='tooltip']").tooltip();
}


var getHost = function(url) {
    var host = undefined;
    if(url.indexOf('://') < 0) {
        url = "https://"+url;
    }
    if(typeof url == "undefined"
        || null == url)
        url = window.location.href;
    var regex = /.*\:\/\/([^\/]*).*/;
    var match = url.match(regex);
    if(typeof match != "undefined"
        && null != match)
        host = match[1];
    return host;
}

var trimSourceLinks = function($xerSourceLinks) {
    $.each($xerSourceLinks,function(index){
        var xerSourceLink = $xerSourceLinks[index];
        var url = $(xerSourceLink).attr('href');
        var name = getHost(url);
        if(name == undefined) {
            name = '源链接';
        } else {
            name = name.replace('www.','');
            var dotIndex = name.indexOf('.');
            if(dotIndex > 0) {
                name = name.substr(0,dotIndex);
            }
            if(name.length > 6) {
                name = name.substr(0,6);
            }
        }
        $(xerSourceLink).html(name);
    });
}

$(function(){
    //获取域名的前六位座位链接的名称
    var $xerSourceLinks = $('[xerSourceLink]');
    trimSourceLinks($xerSourceLinks);
})
