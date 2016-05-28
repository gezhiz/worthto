$(function () {
    var seconds = 5;
    $('#waitTime').text(seconds);
    var intervalId = window.setInterval(function () {
        seconds--;
        if (seconds <= 0) {
            window.clearInterval(intervalId);
            window.location.replace(basePath + '/');
            return;
        }
        $('#waitTime').text(seconds);
    }, 1000);
    $('#jump').click(function () {
        window.clearInterval(intervalId);
        window.location.replace(basePath + '/');
    });
});
