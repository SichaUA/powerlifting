$(document).ready(function () {
    var userViewModel = {
        participate : function () {
            $.ajax({
                url: '/user/participate-competition',
                type: 'POST',
                data: {
//                  get competition id from url
                    competitionId: window.location.pathname.substring(window.location.pathname.lastIndexOf('/')+1)
                }
            }).done(function () {
                window.location = '/user/my-competitions';
            })
        }
    };

    ko.applyBindings(userViewModel, /*$('#controls')*/document.getElementById('controls'));
});
