(function ( $ ) {
    $.fn.accordeon = function () {
        $(this).click(function () {
            $(this)
                .children()
                .filter(".body-block")
                .toggle("slow");
        });
    }
})(jQuery);