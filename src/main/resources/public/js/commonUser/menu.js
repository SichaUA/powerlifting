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

    ko.applyBindings(viewModel, document.getElementById('logout-top'));
    ko.applyBindings(viewModel, document.getElementById('logout'));
});