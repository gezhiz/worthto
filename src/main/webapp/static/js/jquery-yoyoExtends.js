jQuery.prototype.serializeObject=function(){
    var a,o,h,i,e;
    a=this.serializeArray();
    o={};
    h=o.hasOwnProperty;

    var h1 = function(p1, p2, p3){
        var i = p2.indexOf(".");
        if (i >= 0) {
            var i1 = p2.substring(0, i).trim();
            if (i1.length > 0) {
                if (!h.call(p1, i1)) {
                    p1[i1] = {};
                }

                if (i + 1 < p2.length) {
                    h1(p1[i1], p2.substring(i + 1), p3);
                }
            }
        } else {
            if (!h.call(p1, p2)) {
                if (p2.substring(p2.length - 2) == '[]') {
                    p2 = p2.substring(0, p2.length - 2).trim();
                    if (!h.call(p1, p2)) {
                        p1[p2] = [];
                    }
                    p1[p2].push(p3);
                } else {
                    p1[p2] = p3;
                }
            } else {
                if (!(p1[p2] instanceof Array)) {
                    var aa = [];
                    aa.push(p1[p2]);
                    p1[p2] = aa;
                }

                p1[p2].push(p3);
            }
        }
    };

    for(i=0;i<a.length;i++){
        e=a[i];
        e.name = e.name.trim();
        e.value = e.value.trim();
        h1(o, e.name, e.value);
    }

    var h2 = function (o, n, v) {
        var i = n.indexOf("[");
        if (i >= 0) {
            delete o[n];
            var i1 = n.substring(0, i).trim();
            if (!h.call(o, i1)) {
                o[i1] = [];
            }
            o[i1].push(v);
        }
    }

    var h3 = function(o){
        if (o instanceof Array) {
            $.each(o, function (i) {
                h3(o[i]);
            });
        } else if (o instanceof Object) {
            $.each(o, function (n, v) {
                if (v instanceof Array) {
                    h3(v[i]);
                } else if (v instanceof Object) {
                    h3(v);
                }
                h2(o, n, v);

            });
        }
    };

    h3(o);

    return o;
};


jQuery.extend({
    postJSON: function(url, data, callback) {
        return jQuery.ajax(url, {
            type: 'post',
            data: data,
            dataType: "json",
            success: callback
        });
    }
});