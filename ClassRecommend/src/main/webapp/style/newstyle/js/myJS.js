$(function () {
    $(".animate-bg").polygonizr();

    $("#to-register").click(function () {

        $(".form-blur").flip({
            direction: "lr",
            speed: 500,
            color: $(this).css("background-color"),
        });
        $(".loginForm-wrapper").addClass("hidden");
        $(".registerForm-wrapper").removeClass("hidden");

        // $(".form-blur").hide();
    });
    $("#to-login").click(function () {
        $(".form-blur").flip({
            direction: "lr",
            speed: 500,
            color: $(this).css("background-color"),
        });
        $(".registerForm-wrapper").addClass("hidden");
        $(".loginForm-wrapper").removeClass("hidden");
    });
});