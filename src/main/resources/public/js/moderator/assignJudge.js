$(document).ready(function () {
    var users = [];

    $('#user-input').autocomplete({
        serviceUrl: ('/moder/getUsersLike/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1)),
        paramName: "term",
        delimiter: ",",
        transformResult: function(response) {
            users =  $.map($.parseJSON(response), function(item) {
                return { value: (item.secondName + ' ' + item.firstName + ' ' + item.middleName + ' ' + item.birthday), data: item.userId };
            });

            return {suggestions: users};
        }
    });

    $('#select-judge-form').submit(function (e) {
        e.preventDefault();

        var user = $('#user-input').val();
        var userId;
        for(var i = 0; i < users.length; i++) {
            if(users[i].value == user) {
                userId = users[i].data;
            }
        }

        $.ajax({
            url: '/moder/assignUserToJudge/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1),
            method: 'POST',
            data: {
                userId: userId,
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