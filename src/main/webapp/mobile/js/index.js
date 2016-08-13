(function() {
    globalZJdComTask.push(function() {
        drawCircle();
        run();
    });
    // 半径
    var big_radius = 0;
    // 首页圆
    function drawCircle() {
        var dom;
        if (sn.$("canvas.big_circle").length === 0) {
            dom = createDom({tag: "canvas", className: "big_circle"});
        } else {
            dom = sn.$("canvas.big_circle")[0];
        }
        var pars = sn.body();
        var radius = Math.floor(Math.sqrt(pars.cw * pars.cw + pars.wh * pars.wh)) + 5;
        big_radius = radius;
        dom.width = radius;
        dom.height = radius;
        var c = dom;
        var ctx = c.getContext("2d");
        ctx.beginPath();
        ctx.arc(radius, 0, radius, Math.PI / 2, Math.PI , false);
        ctx.lineTo(radius, 0);
        ctx.fillStyle = "#e13045";
        ctx.fill();
        ctx.closePath();
        //定位
    }
    //canvas隐藏
    function canHide() {
        sn.css(sn.$("canvas.big_circle")[0]).remove("ant");
        sn.$("canvas.big_circle")[0].style.webkitTransform = "translate(" + big_radius + "px, -" + big_radius + "px)";
    }
    // 动画圆
    function antCircle(cb) {
        if (sn.$("canvas.big_circle").length === 0) {
            return;
        }
        var dom = sn.$("canvas.big_circle")[0];
        var pars = sn.body();
        dom.style.webkitTransform = "translate(" + big_radius + "px, -" + big_radius + "px)";
        sn.css(dom.parentNode).remove("show");
        sn.css(dom.parentNode).add("show");
        sn.css(dom).remove("show");
        sn.css(dom).add("show");
        var _clear = function() {
            clearTimeout(_clear.__timeout__);
            _clear._do();
        }
        _clear._do = function() {
            typeof cb === "function" && cb();
        }
        _clear.__timeout__ = setTimeout(function() {
            _clear();
        }, 1000);

        setTimeout(function() {
            dom.style.webkitTransform = "translate(-" + (big_radius - pars.cw) + "px, 0px)";
            sn.evt.bind(dom, "webkitTransitionEnd", function() {
                _clear();
                sn.evt.unbind(dom, "webkitTransitionEnd");
            });
        }, 0);
    }
    // 创建dom
    function createDom(conf) {
        var dom = document.createElement(conf.tag || "div");
        var root = conf.root || document.body;
        conf.className && (dom.className = conf.className);
        root.appendChild(dom);
        return dom;
    }

    /* 初始化进度条*/
    window.initProgressBars = initProgressBars;
    function initProgressBars(){
        var progress_bars = sn.$('.progress_bar');
        sn.each(progress_bars, function(el, key){
            if (sn.css(el)._contains("finished")) {
                return
            }
            sn.css(el).add("finished");
            var percent = el.getAttribute('progress');
            if (!sn.isNumeric(percent)) {
                return;
            }
            percent = percent - 0;
            percent = percent < 0 ? 0 : (percent > 100 ? 100 : percent);
            sn.animate(function(times, max) {
                el.style.width = percent * times / max + '%';
            }, 10, 40, function() {
                el.style.width = percent + '%';

            }, -1)
        })
    }
    // 两个swiper
    function swiper() {
        var swiper1 = new Swiper('.swiper1', {
            pagination: '.swiper-pagination1',
            paginationClickable: true,
            spaceBetween: 30,
            autoplay: 2500,
            parallax: true,
            speed: 600,
            loop : true,
        });
        var swiper2 = new Swiper('.swiper2', {
            pagination: '.swiper-pagination2',
            paginationClickable: true,
            spaceBetween: 30,
            scrollbarHide: true,
            slidesPerView: 'auto',
            centeredSlides: true,
            spaceBetween: 30,
            grabCursor: true,
            parallax: true,
            // effect : 'coverflow',
            loop : true,
            slidesPerView : 'auto',
            loopedSlides :8,
            rotate: 30,
            stretch: 10,
            depth: 60,
            modifier: 2,
            slideShadows : true,
        });
    }
    //加载完成运行
    function run() {
        initProgressBars();
        // swiper
        swiper();
        // 懒加载图片
        sn.snload({load_class:"snload"});
        // 是否滚动到底部了并且是向下滚动
        function isBottom() {
            var scrolled = document.body.scrollTop;
            if (typeof isBottom.lastVal === "undefined") {
                isBottom.lastVal = document.body.scrollTop;
            }
            var sh = sn.body().wh;
            var threshold = 100;
            var scrollH = document.body.scrollHeight - sh;
            var isDown = scrolled > isBottom.lastVal; //是否是向下滚动
            isBottom.lastVal = scrolled;
            return (scrolled + threshold) > scrollH && isDown;
        }

        // sn.evt.on(window, 'scroll', function() {
        //   if (isBottom()) {
        //     // 滚动到底部了
        //   }
        // });
        //其他事件
        // 添加动画锁
        var lock_ant = false;
        globalEventAgent["touchend"]["search"] = function(e) {
            clearTimeout(delay);
            if (lock_ant) {
                return;
            }
            lock_ant = true;
            var wap = sn.$(".wapper")[0];
            var el = sn.$(".search", wap)[0];
            sn.css(wap).toggle("show");
            sn.css(el).toggle("sh");
            var delay = setTimeout(function() {
                lock_ant = false;
                sn.css(el).toggle("show");
            }, 100);
        };

        globalEventAgent["touchend"]["sback"] = function(e) {
            clearTimeout(delay);
            if (lock_ant) {
                return;
            }
            lock_ant = true;
            var wap = sn.$(".wapper")[0];
            var el = sn.$(".search", wap)[0];
            var _clear = function() {
                clearTimeout(_clear.__timeout__);
                _clear._do();
            }
            _clear._do = function() {
                lock_ant = false;
                sn.css(wap).remove("show");
                sn.css(el).remove("sh");
                $("#noresult-box").hide();
                $("#load_search").hide();
                $("#searchIpt").val("");
            }
            _clear.__timeout__ = setTimeout(function() {
                _clear();
            }, 1000);
            var delay = setTimeout(function() {
                sn.evt.bind(el, "webkitTransitionEnd", function() {
                    _clear();
                    sn.evt.unbind(el, "webkitTransitionEnd");
                });
                sn.css(el).toggle("show");
            }, 100);
        };
        globalEventAgent["touchmove"]["sback"] = function(e) {
            e.preventDefault();
        };
        // 显示状态和用户中心
        globalEventAgent["touchend"]["status"] = __antStatus;
        window.antStatus = __antStatus;
        // 动画分步
        function __antStatus(show_tag) {
            if (lock_ant) {
                return;
            }
            // lock_ant = true;
            antCircle(function() {
                // lock_ant = false;
                pushcss(function() {
                    sn.css(sn.$("canvas.big_circle")[0]).add("ant");
                    sn.evt.bind(sn.$("canvas.big_circle")[0], "webkitAnimationEnd", function() {
                        sn.evt.unbind(sn.$("canvas.big_circle")[0], "webkitAnimationEnd");
                    });
                });
            })
            //执行动画 安卓响应时间延后
            if (brower().iPhone) {
                setTimeout(function() {
                    antStatus(show_tag);
                }, 100);
            } else {
                setTimeout(function() {
                    antStatus(show_tag);
                }, 500);
            }
        }
        //antStatus("user");
        function antStatus(show_tag) {
            clearTimeout(delay);
            if (lock_ant) {
                return;
            }
            lock_ant = true;
            //清除canvas动画
            var wap = sn.$(".wapper")[0];
            var el = sn.$(".status", wap)[0];
            var showStatus = function(show_tag) {
                var el_s = sn.$(".sele", el)[0];
                var el_user = sn.$(".user", el)[0];
                var el_exe = el_s;
                if (show_tag === "user") {
                    //自动显示user
                    var el_btn = sn.$(".wapper .status .btn .left img")[0]
                    sn.css(el_btn).remove("btn_user");
                    sn.css(el_btn).add("btn_2sele");
                    el_exe = el_user;
                }
                if (sn.css(el_s)._contains("show") || sn.css(el_user)._contains("show")) {
                    return;
                }
                sn.css(el_exe).add("sh");
                setTimeout(function() {sn.css(el_exe).add("show");}, 0);
            }
            sn.css(wap).toggle("show");
            var _clear = function() {
                clearTimeout(_clear.__timeout__);
                _clear._do();
            }
            _clear._do = function() {
                lock_ant = false;
                showStatus(show_tag);
            }
            _clear.__timeout__ = setTimeout(function() {
                _clear();
            }, 5000);
            var delay = setTimeout(function() {
                sn.css(el).toggle("sh");
                sn.css(el).toggle("show");
                sn.evt.bind(el, "webkitAnimationEnd", function() {
                    _clear();
                    sn.evt.unbind(el, "webkitAnimationEnd");
                });
            }, 100);
        };
        // status close
        globalEventAgent["touchend"]["status_close"] = function(e) {
            clearTimeout(delay);
            if (lock_ant) {
                return;
            }
            lock_ant = true;
            var wap = sn.$(".wapper")[0];
            var el = sn.$(".status", wap)[0];
            sn.css(el).remove("show");
            sn.css(el).add("ant");
            var _clear = function() {
                clearTimeout(_clear.__timeout__);
                _clear._do();
            }
            _clear._do = function() {
                lock_ant = false;
                sn.css(sn.$("canvas.big_circle")[0].parentNode).remove("show");
                sn.css(sn.$("canvas.big_circle")[0]).remove("show");
                sn.css(wap).remove("show");
                sn.css(el).remove("ant");
                sn.css(el).remove("sh");
                sn.css(el).remove("hide");
            }
            _clear.__timeout__ = setTimeout(function() {
                _clear();
            }, 1000);
            var delay = setTimeout(function() {
                sn.css(el).add("hide");
                //canvas 隐藏
                setTimeout(function() {
                    canHide();
                }, 200);
                sn.evt.bind(el, "webkitTransitionEnd", function() {
                    _clear();
                    sn.evt.unbind(el, "webkitTransitionEnd");
                });
            }, 300);
        };
        //显示状态和用户中心内容切换
        globalEventAgent["touchend"]["s9u"] = function(e) {
            clearTimeout(delay);
            if (lock_ant) {
                return;
            }
            lock_ant = true;
            var delay;
            // 用户中心
            var btn_user = function(el) {
                var wap = sn.$(".wapper")[0];
                var el_s = sn.$(".sele", wap)[0];
                var el_u = sn.$(".user", wap)[0];
                var _clear = function() {
                    clearTimeout(_clear.__timeout__);
                    _clear._do();
                }
                _clear._do = function() {
                    lock_ant = false;
                    sn.css(el_s).remove("sh");
                    sn.css(el_s).remove("show");
                    sn.css(el_s).remove("hide");
                }
                _clear.__timeout__ = setTimeout(function() {
                    _clear();
                }, 1000);
                sn.css(el).remove("btn_user");
                sn.css(el).add("btn_2sele");
                sn.css(el_u).add("sh");
                delay = setTimeout(function() {
                    sn.css(el_s).add("hide");
                    sn.css(el_u).add("show");
                    sn.evt.bind(el_u, "webkitTransitionEnd", function() {
                        _clear();
                        sn.evt.unbind(el_u, "webkitTransitionEnd");
                    });
                }, 200);
            };

            var btn_2sele = function(el) {
                var wap = sn.$(".wapper")[0];
                var el_s = sn.$(".sele", wap)[0];
                var el_u = sn.$(".user", wap)[0];
                sn.css(el).remove("btn_2sele");
                sn.css(el).add("btn_user");
                sn.css(el_s).add("sh");
                var _clear = function() {
                    clearTimeout(_clear.__timeout__);
                    _clear._do();
                }
                _clear._do = function() {
                    lock_ant = false;
                    sn.css(el_u).remove("sh");
                    sn.css(el_u).remove("show");
                    sn.css(el_u).remove("hide");
                }
                _clear.__timeout__ = setTimeout(function() {
                    _clear();
                }, 1000);
                delay = setTimeout(function() {
                    sn.css(el_u).add("hide");
                    sn.css(el_s).add("show");
                    sn.evt.bind(el_u, "webkitTransitionEnd", function() {
                        _clear();
                        sn.evt.unbind(el_u, "webkitTransitionEnd");
                    });
                }, 200);
            };
            if (sn.css(this)._contains("btn_user")) {
                btn_user(this);
            } else if (sn.css(this)._contains("btn_2sele")){
                btn_2sele(this);
            }
        }
        //8种状态3d切换
        var hash = {}, isMove = false;
        globalEventAgent["touchstart"]["sele_8s"] = function(e) {
            e.stopPropagation();
            e.preventDefault();
            var el = e.target;
            var touch = e.touches[0];
            hash.x = touch.clientX;
            hash.y = touch.clientY;
            hash.el = el;
            isMove = false;
        }
        globalEventAgent["touchmove"]["sele_8s"] = function(e) {
            isMove = true;
            var touch = e.touches[0];
            hash.mx = touch.clientX;
            hash.my = touch.clientY;
            hash.dx = hash.mx - hash.x;
            hash.dy = hash.my - hash.y;
        }
        globalEventAgent["touchend"]["sele_8s"] = function(e) {
            var threshold = 50;
            if (isMove === false && hash.el.parentNode.nodeName === "A" && sn.css(hash.el.parentNode.parentNode)._contains("curr")) {
                window.location.href = hash.el.parentNode.getAttribute("href");
                return;
            }
            if (hash.dx > threshold) {
                // 右
                s3d("right");
            } else if (hash.dx < -threshold) {
                // 左
                s3d("left");
            }
        }
        // pushcss
        function pushcss(cb) {
            var lastPos = "-30px";
            if (pushcss.one) {
                sn.$("canvas.big_circle")[0].style.webkitTransform = "translate(-" + (big_radius - sn.body().cw) + "px, " + lastPos + ")";
                return typeof cb === "function" && cb();
            }
            pushcss.one = true;
            var xpos = big_radius - sn.body().cw;
            var style = ''
                + '.big_circle.show.ant{\n'
                + '    transform: translateY(-30px) translateX(-'+ xpos +'px);\n'
                + '    -webkit-transform: translateY(-30px) translateX(-'+ xpos +'px);\n'
                + '    -webkit-transition: none;\n'
                + '    -webkit-animation: "big_breath" 0.6s cubic-bezier(.53,.1,.81,.38) 0s 1;\n'
                + '}\n'
                + '@-webkit-keyframes big_breath {\n'
                + '  0% {\n'
                + '    transform: translateY(0) translateX(-'+ xpos +'px);\n'
                + '    -webkit-transform: translateY(0) translateX(-'+ xpos +'px);\n'
                + '  }\n'
                + '  20% {\n'
                + '    transform: translateY(-40px) translateX(-'+ xpos +'px);\n'
                + '    -webkit-transform: translateY(-40px) translateX(-'+ xpos +'px);\n'
                + '  }\n'
                + '  40% {\n'
                + '    transform: translateY(-20px) translateX(-'+ xpos +'px);\n'
                + '    -webkit-transform: translateY(-20px) translateX(-'+ xpos +'px);\n'
                + '  }  \n'
                + '  70% {\n'
                + '    transform: translateY(-25px) translateX(-'+ xpos +'px);\n'
                + '    -webkit-transform: translateY(-25px) translateX(-'+ xpos +'px);\n'
                + '  }\n'
                + '  80% {\n'
                + '    transform: translateY(-35px) translateX(-'+ xpos +'px);\n'
                + '    -webkit-transform: translateY(-35px) translateX(-'+ xpos +'px);\n'
                + '  }\n'
                + '  100% {\n'
                + '    transform: translateY(-30px) translateX(-'+ xpos +'px);\n'
                + '    -webkit-transform: translateY(-30px) translateX(-'+ xpos +'px);\n'
                + '  }\n'
                + '}\n';
            sn.$("canvas.big_circle")[0].style.webkitTransform = "translate(-" + (big_radius - sn.body().cw) + "px, " + lastPos + ")";
            sn.pushcss(style);
            typeof cb === "function" && cb();
        }
        //状态图3d转换
        function s3d(direct) {
            var wap = sn.$(".wapper")[0];
            var el_s = sn.$(".sele_8s", wap)[0];
            var sts = sn.$(".st", el_s);
            var curr_index = 0;
            var curr;
            for (var i = 0, len = sts.length; i < len; i++) {
                var el = sts[i];
                if (sn.css(el)._contains("curr")) {
                    curr_index = i;
                    break;
                }
            }
            if (direct === "left") {
                curr = curr_index + 1;
            } else if (direct === "right") {
                curr = curr_index - 1;
            }
            if (curr < 0 || curr >= len) {
                return;
            }
            var wap = sn.$(".wapper")[0];
            var el = sn.$(".status", wap)[0];
            sn.css(el).remove("show");
            var clear = function(index) {
                sn.css(sts[index]).remove("left");
                sn.css(sts[index]).remove("curr");
                sn.css(sts[index]).remove("right");
                sn.css(sts[index]).remove("hideleft");
                sn.css(sts[index]).remove("hideright");
            }
            sn.$("#categoryId")[0].value = sts[curr].getAttribute("data-categoryid");
            setTimeout(function() {
                for (var j = 0; j < curr - 1; j++) {
                    clear(j),sn.css(sts[j]).add("hideleft");
                }
                for (var k = curr + 2; k < sts.length; k++) {
                    clear(k),sn.css(sts[k]).add("hideright");
                }
                (curr - 1) >= 0 && (clear(curr - 1),sn.css(sts[curr - 1]).add("left"));
                clear(curr),sn.css(sts[curr]).add("curr");
                (curr + 1) < len && (clear(curr + 1),sn.css(sts[curr + 1]).add("right"));
                // sn.$("#categoryId")[0].value = curr;
            }, 0);
        }
        // 弹出禁止上下滑动
        globalEventAgent["touchstart"]["wapper_stop"] = function(e) {
            // var el = e.target;
            // if (el.nodeName === "INPUT") {
            //   return;
            // }
            // e.preventDefault();
        };
        globalEventAgent["touchmove"]["wapper_stop"] = function(e) {
            e.preventDefault();
        };
        globalEventAgent["touchend"]["wapper_stop"] = function(e) {};
        sn(".sele_3c a,.sele_go a").bind("touchmove", function(e) {
            e.preventDefault();
        });


        //写入数字图片
        //do_num2img();
        // dom 格式 <div class="num" id="num2img" data-num="123,345">
        function do_num2img() {
            var el = sn.$("#num2img");
            if (el.length === 0) {
                return;
            }
            el = el[0];
            el.innerHTML = num2img(el.getAttribute("data-num"));
        }
        //数组2图片
        function num2img(str) {
            if (typeof str !== "string") {
                return;
            }
            var vstr = "0123456789,万，";
            var hash = {
                '0': '<img src="http://storage.jd.com/openicloud.q.m.z.jd.com/static/images/empty.gif" class="icon num_data0">',
                '1': '<img src="http://storage.jd.com/openicloud.q.m.z.jd.com/static/images/empty.gif" class="icon num_data1">',
                '2': '<img src="http://storage.jd.com/openicloud.q.m.z.jd.com/static/images/empty.gif" class="icon num_data2">',
                '3': '<img src="http://storage.jd.com/openicloud.q.m.z.jd.com/static/images/empty.gif" class="icon num_data3">',
                '4': '<img src="http://storage.jd.com/openicloud.q.m.z.jd.com/static/images/empty.gif" class="icon num_data4">',
                '5': '<img src="http://storage.jd.com/openicloud.q.m.z.jd.com/static/images/empty.gif" class="icon num_data5">',
                '6': '<img src="http://storage.jd.com/openicloud.q.m.z.jd.com/static/images/empty.gif" class="icon num_data6">',
                '7': '<img src="http://storage.jd.com/openicloud.q.m.z.jd.com/static/images/empty.gif" class="icon num_data7">',
                '8': '<img src="http://storage.jd.com/openicloud.q.m.z.jd.com/static/images/empty.gif" class="icon num_data8">',
                '9': '<img src="http://storage.jd.com/openicloud.q.m.z.jd.com/static/images/empty.gif" class="icon num_data9">',
                ',': '<img src="http://storage.jd.com/openicloud.q.m.z.jd.com/static/images/empty.gif" class="icon num_datadian">',
                '，': '<img src="http://storage.jd.com/openicloud.q.m.z.jd.com/static/images/empty.gif" class="icon num_datadian">',
                '万': '<img src="http://storage.jd.com/openicloud.q.m.z.jd.com/static/images/empty.gif" class="icon num_datawan">'
            }
            var cache = [];
            for (var i = 0, len = str.length; i < len; i++) {
                vstr.indexOf(str[i]) !== -1 && cache.push(hash[str[i]]);
            }
            return cache.join('');
        }
        //end
    }
    //run end
//end
})("产品众筹M站首页");