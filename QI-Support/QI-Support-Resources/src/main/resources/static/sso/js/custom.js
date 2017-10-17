(function ($) {

    new WOW().init();

    jQuery(window).load(function () {
        jQuery("#preloader").delay(100).fadeOut("slow");
        jQuery("#load").delay(100).fadeOut("slow");
        navbar();
    });


    //jQuery to collapse the navbar on scroll
    $(window).scroll(function () {
        navbar();
    });

    function navbar() {
        if ($(".navbar").offset().top > 50) {
            $(".navbar-fixed-top").addClass("top-nav-collapse");
        } else {
            $(".navbar-fixed-top").removeClass("top-nav-collapse");
        }
    }

    //jQuery for page scrolling feature - requires jQuery Easing plugin
    $(function () {
        $('.navbar-nav li a').bind('click', function (event) {
            var $anchor = $(this);
            if ($($anchor.attr('href')).offset()) {
                $('html, body').animate({
                    scrollTop: $($anchor.attr('href')).offset().top
                }, 1000, 'easeInOutExpo');
                event.preventDefault();
            }
        });
        $('.page-scroll a').bind('click', function (event) {
            var $anchor = $(this);
            $('html, body').animate({
                scrollTop: $($anchor.attr('href')).offset().top
            }, 1000, 'easeInOutExpo');
            event.preventDefault();
        });
    });


    //nivo lightbox
    $('.gallery-item a').nivoLightbox({
        effect: 'fadeScale',                             // The effect to use when showing the lightbox
        theme: 'default',                           // The lightbox theme to use
        keyboardNav: true,                          // Enable/Disable keyboard navigation (left/right/escape)
        clickOverlayToClose: true,                  // If false clicking the "close" button will be the only way to close the lightbox
        onInit: function () {
        },                       // Callback when lightbox has loaded
        beforeShowLightbox: function () {
        },           // Callback before the lightbox is shown
        afterShowLightbox: function (lightbox) {
        },    // Callback after the lightbox is shown
        beforeHideLightbox: function () {
        },           // Callback before the lightbox is hidden
        afterHideLightbox: function () {
        },            // Callback after the lightbox is hidden
        onPrev: function (element) {
        },                // Callback when the lightbox gallery goes to previous item
        onNext: function (element) {
        },                // Callback when the lightbox gallery goes to next item
        errorMessage: 'The requested content cannot be loaded. Please try again later.' // Error message when content can't be loaded
    });

})(jQuery);
