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
            return {
                suggestions: $.map($.parseJSON(response), function(item) {
                    return { value: (item.secondName + ' ' + item.firstName + ' ' + item.middleName + ' ' + item.email), data: item.userId };
                })
            };
        }
    });

    $('#add-participant-form').submit(function (e) {
        e.preventDefault();

        var participant = $('#participant-input').val().split(' ');
        var email = participant[participant.length-1];

        $.ajax({
            url: '/moder/AddParticipantToCompetition/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1),
            method: 'POST',
            data: {
                participantEmail: email
            }
        }).done(function (response) {
            if (response === 'success') {
                location.reload();
            } else {
                alert('Error!');
            }
        });
    });
});
