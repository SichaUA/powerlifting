var users = [];

function deleteParticipant(participantId) {
    $.ajax({
        url: '/moder/deleteParticipantFromCompetition/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1),
        method: 'POST',
        data: {participantId: participantId}
    }).done(function (response) {
        alert(response);
        if (response === 'success') {
            location.reload();
        } else {
            alert('Error!');
        }
    });
}

function createNewUser() {
    window.location = '/moder/createNewUser/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
}

$(document).ready(function () {
    $('#participant-input').autocomplete({
        serviceUrl: ('/moder/getUsersLikeWhichNotInCompetition/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1)),
        paramName: "term",
        delimiter: ",",
        transformResult: function(response) {
            users =  $.map($.parseJSON(response), function(item) {
                return { value: (item.secondName + ' ' + item.firstName + ' ' + item.middleName + ' ' + item.birthday), data: item.userId };
            });

            return {suggestions: users};
        }
    });

    $('#add-participant-form').submit(function (e) {
        e.preventDefault();

        var participant = $('#participant-input').val();
        var userId;
        for(var i = 0; i < users.length; i++) {
            if(users[i].value == participant) {
                userId = users[i].data;
                break;
            }
        }

        $.ajax({
            url: '/moder/AddParticipantToCompetition/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1),
            method: 'POST',
            data: {
                participantId: userId
            }
        }).done(function (response) {
            window.location = response;
        });
    });
});
