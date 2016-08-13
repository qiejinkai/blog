/*! JS, uglified
 Date: 2015-09-28 */
!function(){function a(a){if(0===k.$(".loading").length)return h();var c=5e3,d=!1;window.__snTimeOut__=setTimeout(function(){d=!0,k.evt.unbind(window,"load"),b(a)},c),k.evt.bind(document,l,function(a){a.stopPropagation(),a.preventDefault()}),k.evt.bind(window,"load",function(){clearTimeout(window.__snTimeOut__),d||b(a)})}function b(a){k("body").css("add","loaded");var b=k(".loading");b.css("add","hiding"),k.evt.unbind(document,l),b.css("add","hide"),c(),g(),f(),a()}function c(){if(!k.css(k.$("body")[0])._contains("nogo2top")){if(0===k.$(".go2top").length){var a=document.createElement("div");a.className="go2top",a.innerHTML='<img src="http://storage.jd.com/openicloud.q.m.z.jd.com/static/images/empty.gif" class="icon rock" data-event-agent="touchend|rock">',document.body.appendChild(a)}k.evt.on(window,"scroll",function(){var a=0;document.documentElement&&(a=document.documentElement.scrollTop),0===a&&document.body&&(a=document.body.scrollTop),d(a)})}}function d(a){var b=k.body().wh;if(b>a||m)return void k.css(k.$(".go2top")[0]).remove("show");{var c=1.5*b;document.body.scrollHeight-b}a>c?k.css(k.$(".go2top")[0]).add("show"):k.css(k.$(".go2top")[0]).remove("show")}function e(){var a=(k.body().wh,document.body.scrollTop),b=function(){m=!0;var b=10,c=Math.floor(a/b);k.animate(function(b){document.documentElement&&(document.documentElement.scrollTop=a-c*b),document.body&&(document.body.scrollTop=a-c*b)},b,40,function(){document.documentElement&&(document.documentElement.scrollTop=0),document.body&&(document.body.scrollTop=0),m=!1},-1)};b()}function f(){k.evt.bind(document,l,function(a){globalDomEvtSwt&&(a.stopPropagation(),a.preventDefault());var b=a.target,c=a.type,d=b.getAttribute("data-event-agent"),e=5;if(!d)for(var f=0;(b=b.parentNode)&&(!(f>=e)&&"getAttribute"in b)&&!(d=b.getAttribute("data-event-agent"));f++);if(d){var g=d.split("|"),h=g[0].split(","),i=g[1];-1!==k.indexOf(h,c)&&globalEventAgent[c][i].call(b,a)}})}function g(){globalEventAgent.touchend.rock=function(){var a=this.parentNode;setTimeout(function(){e(),k.css(a.parentNode).remove("show")},400)}}function h(){c(),g(),f(),window.globalZJdComTask={push:function(a){a()}}}function i(){{var a=navigator.userAgent;navigator.appVersion}return{trident:a.indexOf("Trident")>-1,presto:a.indexOf("Presto")>-1,webKit:a.indexOf("AppleWebKit")>-1,gecko:a.indexOf("Gecko")>-1&&-1==a.indexOf("KHTML"),mobile:!!a.match(/AppleWebKit.*Mobile.*/),ios:!!a.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),android:a.indexOf("Android")>-1||a.indexOf("Linux")>-1,iPhone:a.indexOf("iPhone")>-1,iPad:a.indexOf("iPad")>-1,webApp:-1==a.indexOf("Safari"),weixin:a.indexOf("MicroMessenger")>-1,qq:" qq"==a.match(/\sQQ/i)}}var j=util();window.jstools=j;var k=j.s;window.sn=k,window.globalDomEvtSwt=!1;var l="touchstart,touchmove,touchend";window.globalEventAgent={};var m=!1;k.each(l.split(","),function(a){window.globalEventAgent[a]={}}),window.loading=a,window.globalZJdComTask=[],a(function(){for(var a;a=globalZJdComTask.shift();)a()}),window.brower=i,k.ext({snload:function(a){function b(a){c.each(a,function(a){if(a.nodeName&&a.getAttribute(d.attr)){var b=c.getPos(a).y,f=b-d.threshold<=e+document.body.scrollTop;f&&("IMG"===a.nodeName?a.src=a.getAttribute(d.attr):a.style.backgroundImage="url("+d.attr+")",c.css(a).remove(d.load_class),c.css(a).add(d.loaded_class))}})}var c=this;a=a||{};var d={threshold:30,attr:"data-original",load_class:"load",loaded_class:"loaded"},e=c.body().wh,f=function(){var a=c.$("."+d.load_class);a.length&&b(a)};c.evt.on(window,"scroll",f),c.copy(d,a),f(),c.sn2load=f},ajax:function(a){$.ajax({url:a.url,type:a.method,scriptCharset:a.chrset||"utf-8",data:a.data||{},dataType:a.type||"json",success:function(a){a.success(a)},error:function(b){a.error(b)}})}})}("common"),window.validate_middleware=function(){function a(a,d){var e=c(d);for(var f in e){var d=e[f];a[f]=b(f,d)}return a}function b(a,b){return function(c){return"function"==typeof b?b(c):d(a,c,b)}}function c(a){var b={};for(var c in a){var d=a[c];b[c]=e(d)?{min:d[2]?d[2][0]:!1,max:d[2]?d[2][1]:!1,reg:d[3]||null,exist:d[1],name:d[0]||"",reg_msg:d[4]||""}:d}return b}function d(a,b,c){var d="",e=!0;if("string"!=typeof b&&"number"!=typeof b)return!1;var f="";f=c.name,b=b.toString();var g=b.length;return c.exist&&""===b?(d=f+"不能为空",e=!1):c.min&&g<c.min?(d=f+"不能小于"+c.min+"位,"+f+"长度为"+c.min+" ~ "+c.max+"位",e=!1):c.max&&g>c.max?(d=f+"不能大于"+c.max+"位,"+f+"长度为"+c.min+" ~ "+c.max+"位",e=!1):c.reg&&!c.reg.test(b)&&(d=c.reg_msg,e=!1),{status:e,msg:d,key:a}}function e(a){return"[object Array]"===Object.prototype.toString.call(a)}function f(a,b){for(var c in b)a[c]=b[c];return a}var g={},h={},i={email:["邮箱",!0,[1,100],/^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$/i,"邮箱地址无效"],captcha:["验证码",!0,void 0,/^\d{4,10}$/,"验证码不正确"],mobile:["移动电话",!0,null,/^0?(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[57])[0-9]{8}$/,"手机号格式不正确"],username:["用户名",!0,[4,16],/^[a-zA-Z][\w\-]+$/,"用户名只允许字母、数字、下划线、横线组成，首位只能为字母, 且至少需要 4 个字符"],password:["密码",!0,[6,26],/^.{5,25}$/,"密码只能为 6 - 26 位数字，字母及常用符号组成"],code:["短信验证码",!0,null,/^\d{6}$/,"请填写6位数字验证码"],ID_card:["身份证号码",!0,null,/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z])$/,"请输入正确的身份证号码"],time:["时间",!0,null,/^([01]\d|2[0-3])(:[0-5]\d){1,2}$/,"请输入正确的时间,例:14:30或14:30:00"],url:["网址",!0,null,/^(https?|ftp):\/\/[^\s]+$/i,"网址格式不正确"],postcode:["邮政编码",!0,null,/^(https?|ftp):\/\/[^\s]+$/i,"邮政编码格式不正确"],chinese:["中文",!0,null,/^[\u0391-\uFFE5]+$/,"请输入中文"],chineseName:["中文名",!0,null,/^[\u0391-\uFFE5]{2,6}$/,"请输入2-6个汉字中文"],address:["地址",!0,null,/^[\u0391-\uFFE5][\u0391-\uFFE5\d]+$/,"填写正确的地址"],agreement:["注册协议",!0,null,/^.{1,10}$/,"填阅读注册协议"],date:["日期",!0,null,/^[\u0391-\uFFE5][\u0391-\uFFE5\d]+$/,"日期格式:yyyy-mm-dd"]};return g.REG=i,g.validate=function(b){return b=b||{},a(h,f(i,b))},g}();
(function () {
    /*share*/
    if (!window.dataForWeixin)
    {
        var shareurl=encodeURI(window.location.href);
        window.dataForWeixin = {
            appId: "",
            img: "http://img30.360buyimg.com/cf/jfs/t298/111/215985347/175849/d2488f13/5405408aN51a7d223.png",
            url: "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8ac0ef33f8df7e4f&redirect_uri=http%3A//"+window.location.host+"/wxjdissue/jdissueWX/get_wxid.action%3Fdispatch%3D"+shareurl+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect",
            title: "京东众筹-凑份子就是有FUN...",
            friendTitle: "凑份子就是有FUN",
            desc: "是有多久没回到新奇看世界了呢？快点来FUN家族寻宝吧~"
        };
    }

    var onBridgeReady = function () {
        // 发送给好友;
        WeixinJSBridge.on('menu:share:appmessage', function (argv) {
            WeixinJSBridge.invoke('sendAppMessage', {
                "appid": dataForWeixin.appId,
                "img_url": dataForWeixin.img,
                "img_width": "240",
                "img_height": "240",
                "link": dataForWeixin.url,
                "desc": dataForWeixin.desc,
                "title": dataForWeixin.title
            }, function (res) {
            });
        });
        // 分享到朋友圈;
        WeixinJSBridge.on('menu:share:timeline', function (argv) {
            WeixinJSBridge.invoke('shareTimeline', {
                "appid": dataForWeixin.appId,
                "img_url": dataForWeixin.img,
                "img_width": "240",
                "img_height": "240",
                "link": dataForWeixin.url,
                "desc": dataForWeixin.desc,
                "title": dataForWeixin.title
            }, function (res) {
            });
        });
        // 分享到微博;
        WeixinJSBridge.on('menu:share:weibo', function (argv) {
            WeixinJSBridge.invoke('shareWeibo', {
                "content": dataForWeixin.title + ' ' + dataForWeixin.url,
                "url": dataForWeixin.url
            }, function (res) {
            });
        });
    };
    if (document.addEventListener) {
        document.removeEventListener('WeixinJSBridgeReady', onBridgeReady);
        document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
    } else if (document.attachEvent) {
        document.detachEvent('WeixinJSBridgeReady', onBridgeReady);
        document.detachEvent('onWeixinJSBridgeReady', onBridgeReady);
        document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
    }
})();