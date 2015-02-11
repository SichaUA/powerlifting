$(document).ready(function () {
    var viewModel = {
        logoutFunction: function () {
            $.ajax({
                url: '/logout',
                type: 'POST'
            }).done(function () {
                window.location = '/';
            });
        }
    };

    ko.applyBindings(viewModel, /*$('#logout-top')*/ document.getElementById('logout-top'));
    ko.applyBindings(viewModel, /*$('#logout')*/ document.getElementById('logout'));
});