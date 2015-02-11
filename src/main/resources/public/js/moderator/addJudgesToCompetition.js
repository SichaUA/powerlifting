function deleteJudge(judgeId) {
    $.ajax({
        url: '/moder/deleteJudgeFromCompetition/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1),
        method: 'POST',
        data: {judgeId: judgeId}
    }).done(function (response) {
        alert(response);
        if (response === 'success') {
            location.reload();
        } else {
            alert('Error!');
        }
    });
}

$(document).ready(function () {

    $('#judge-input').autocomplete({
        serviceUrl: ('/moder/getJudgesLike/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1)),
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

    $('#add-judge-form').submit(function () {
        var judge = $('#judge-input').val().split(' ');
        var email = judge[judge.length-1];
        $.ajax({
            url: '/moder/AddJudgeToCompetition/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1),
            method: 'POST',
            data: {
                judgeEmail: email
            }
        }).done(function (response) {
            alert(response);
            if (response === 'success') {
                location.reload();
            } else {
                alert('Error!');
            }
        });
    });
});