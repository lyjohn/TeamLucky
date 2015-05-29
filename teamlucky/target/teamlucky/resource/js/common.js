/**
 * Created by laiguoqiang on 15/5/17.
 * 全局的JS函数
 */

(function ($) {

    function scrollToPosition(top) {
        $("html,body").animate({
            scrollTop: top
        }, 500);
    }
})(jQuery);



$(function(){
    $(document).on("click",".undo",function(e){
        layer.msg("程序猿还在开发这个页面",{icon:5});

        e.preventDefault();
        return false;
    });

    //是否是邮箱
    String.prototype.isEmail = function(){
        var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
        return reg.test(this);
    }

    //是否是手机
    String.prototype.isMobile = function(){
        var reg =/^([\+0-9]{2,4}[\-]{0,1}1\d{10}|1\d{10})$/;
        return reg.test(this);
    }

    //是否是座机
    String.prototype.isPhone = function(){
        var reg = /^([0-9]{7,8})(-([0-9]{3,}))?$/;
        return reg.test(this);
    }

    //是否是座机区号
    String.prototype.isPhoneZone = function(){
        var reg = /^(0[0-9]{2,3})$/;
        return reg.test(this);
    }

    //测试是否是整数
    String.prototype.isInt = function() {
        if (this == "NaN")
            return false;
        return this == parseInt(this).toString();
    }

    String.prototype.isIDcard = function() {
        var code = this;
        var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
        var tip = "";
        var pass= true;

        if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
            tip = "身份证号格式错误";
            pass = false;
        }

        else if(!city[code.substr(0,2)]){
            tip = "地址编码错误";
            pass = false;
        }
        else{
            //18位身份证需要验证最后一位校验位
            if(code.length == 18){
                code = code.split('');
                //∑(ai×Wi)(mod 11)
                //加权因子
                var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
                //校验位
                var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
                var sum = 0;
                var ai = 0;
                var wi = 0;
                for (var i = 0; i < 17; i++)
                {
                    ai = code[i];
                    wi = factor[i];
                    sum += ai * wi;
                }
                var last = parity[sum % 11];
                if(parity[sum % 11] != code[17]){
                    tip = "校验位错误";
                    pass =false;
                }
            }
        }
        //if(!pass) alert(tip);
        return pass;
    }
});
