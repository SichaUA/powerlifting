$(document).ready(function () {
    var userViewModel = {
        participate : function () {
            $.ajax({
                url: '/participate-competition',
                type: 'POST',
                data: {
//                  get competition id from url
                    competitionId: window.location.pathname.substring(window.location.pathname.lastIndexOf('/')+1)
                }
            }).done(function () {
                window.location = '/my-competitions';
            })
        },
        
        deleteCompetition : function () {
            $.ajax({
                url: '/moder/delete-competition',
                type: 'POST',
                data: {
//                  get competition id from url
                    competitionId: window.location.pathname.substring(window.location.pathname.lastIndexOf('/')+1)
                }
            }).done(function (response) {
                if(response == 'success') {
                    window.location = '/my-competitions';
                }
            })
        },

        addJudges : function () {
            window.location = '/moder/add-judges/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/')+1);
        },

        addParticipants : function() {
            window.location = '/moder/add-participants/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/')+1);
        }
    };

    ko.applyBindings(userViewModel, document.getElementById('controls'));
});
