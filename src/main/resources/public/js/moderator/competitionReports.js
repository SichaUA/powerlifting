function generateMainProtocol() {
    $.ajax({
        url: '/moder/createMainProtocol/' + $('.competition-id').val(),
        method: 'POST'
    }).done(function (response) {
        alert(response);
    });
}