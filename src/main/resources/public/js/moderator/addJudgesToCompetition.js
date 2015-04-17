var judges = [];

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

function assignJudge() {
    window.location = '/moder/assignJudge/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
}

function createNewJudge() {
    window.location = '/moder/createNewJudge/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
}

$(document).ready(function () {

    $('#judge-input').autocomplete({
        serviceUrl: ('/moder/getJudgesLike/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1)),
        paramName: "term",
        delimiter: ",",
        transformResult: function(response) {
            judges = $.map($.parseJSON(response), function(item) {
                    return { value: (item.secondName + ' ' + item.firstName + ' ' + item.middleName + '; ' + item.birthday), data: item.userId };
                });
            return {suggestions: judges};
        },
        select: function( event, ui ) {
            alert(ui.item.userId);
        }
    });

    $('#judge-input').on('autocompleteselect', function ( event, ui ) {
        alert(ui.item.userId);
    });

    $('#add-judge-form').submit(function (e) {
        e.preventDefault();

        var judge = $('#judge-input').val();
        var judgeId = 0;
        for(var i = 0; i < judges.length; i++) {
            if(judges[i].value == judge) {
                judgeId = judges[i].data;
                break;
            }
        }
        if(judgeId == 0) {
            $('#danger-modal-title').text('ERROR!');
            $('#danger-modal-body').text('Select judge from drop-down');
            $('#danger-modal').modal('show');
        }else{
            $.ajax({
                url: '/moder/AddJudgeToCompetition/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1),
                method: 'POST',
                data: {
                    judgeId: judgeId
                }
            }).done(function (response) {
                if (response === 'success') {
                    location.reload();
                } else {
                    $('#danger-modal-title').text('ERROR!');
                    $('#danger-modal-body').text('Something gone wrong! Reload page and try again');
                    $('#danger-modal').modal('show');
                }
            });
        }
    });
});