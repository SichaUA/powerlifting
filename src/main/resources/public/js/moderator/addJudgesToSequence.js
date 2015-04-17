$(document).ready(function () {
    $('.judge-select').change(function (select) {
        if($(this).val() != -1) {
            addJudge($(this).parents('tr').find('.judge-id').val(), $(this).val());
        }else{
            deleteJudge($(this).parents('tr').find('.judge-id').val(), $(this).val());
        }
    });

    function addJudge(judgeId, typeId) {
        $.ajax({
            url: '/moder/addJudgeToSequence/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1),
            method: 'POST',
            data: {
                judgeId: judgeId,
                typeId: typeId
            }
        }).done(function (response) {
            if(response == 'success') {
                $('#base-modal-title').text('Judge was successfully added to sequence!');
                //$('#base-modal-body').text('');
                $('#base-modal').modal('show');
            }
        });
    }

    function deleteJudge(judgeId, typeId) {
        $.ajax({
            url: '/moder/deleteJudgeFromSequence/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1),
            method: 'POST',
            data: {
                judgeId: judgeId
            }
        }).done(function (response) {
            if(response == 'success') {
                $('#base-modal-title').text('Judge was successfully deleted from sequence!');
                //$('#base-modal-body').text('');
                $('#base-modal').modal('show');
            }
        });
    }
});