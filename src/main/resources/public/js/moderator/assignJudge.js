$(document).ready(function () {
    $('#user-input').autocomplete({
        serviceUrl: ('/moder/getUsersLike/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1)),
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

    $('#select-judge-form').submit(function (e) {
        e.preventDefault();

        var user = $('#user-input').val().split(' ');
        var email = user[user.length-1];

        $.ajax({
            url: '/moder/assignUserToJudge/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1),
            method: 'POST',
            data: {
                userEmail: email,
                categoryId: $('#category-select').val(),
                categoryDate: $('#datepicker').val(),
                addJudgeToCompetition: $('#addJudgeToCompetition').is(':checked')
            }
        }).done(function (response) {
            if (response === 'success') {
                window.location = "/moder/add-judges/" + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
            } else {
                alert('Error!');
            }
        });
    });

});