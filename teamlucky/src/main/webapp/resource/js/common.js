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