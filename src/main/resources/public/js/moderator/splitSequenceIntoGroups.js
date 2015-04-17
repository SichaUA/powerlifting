$(document).ready(function () {
    $('.group-count-select').change(function () {
        var groupCount = + $('.group-count-select').val();

        changeGroupCount(groupCount);

        switch (groupCount) {
            case 1:
                checkSelected(groupCount);
                removeHidden('judge-option-1');
                addHidden('judge-option-2');
                addHidden('judge-option-3');
                addHidden('judge-option-4');
                break;
            case 2:
                checkSelected(groupCount);
                removeHidden('judge-option-1');
                removeHidden('judge-option-2');
                addHidden('judge-option-3');
                addHidden('judge-option-4');
                break;
            case 3:
                checkSelected(groupCount);
                removeHidden('judge-option-1');
                removeHidden('judge-option-2');
                removeHidden('judge-option-3');
                addHidden('judge-option-4');
                break;
            case 4:
                checkSelected(groupCount);
                removeHidden('judge-option-1');
                removeHidden('judge-option-2');
                removeHidden('judge-option-3');
                removeHidden('judge-option-4');
                break;
        }
    });

    function addHidden(groupClass) {
        $('.' + groupClass).each(function () {
            $(this).prop('disabled', true);
            $(this).addClass('hidden')
        });
    }

    function removeHidden(groupClass) {
        $('.' + groupClass).each(function () {
            $(this).prop('disabled', false);
            $(this).removeClass('hidden');
        });
    }

    function checkSelected(groupCount) {
        $('.group-count-select').each(function () {
            if($(this).val() > groupCount) {
                $(this).val(0);
            }
        });
    }

    function changeGroupCount(groupCount) {
        $.ajax({
            url: '/moder/changeSequenceGroupCount/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1),
            method: 'POST',
            data: {
                groupCount: groupCount
            }
        }).done(function (response) {

        });
    }

    $('.group-count-select').change(function () {
        var participantId = $(this).parents('tr').find('.participant-id').val();
        var groupNum = $(this).val();

        changeParticipantGroup(participantId, groupNum);
    });

    function changeParticipantGroup (participantId, groupNum) {
        $.ajax({
            url: '/moder/changeParticipantGroup/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1),
            method: 'POST',
            data: {
                participantId: participantId,
                groupNum: groupNum
            }
        }).done(function (response) {
            if(response === 'success') {
                var unique_id = $.gritter.add({
                    // (string | mandatory) the heading of the notification
                    title: 'Group adding',
                    // (string | mandatory) the text inside the notification
                    text: 'Participant was successfully added to group!',
                    // (string | optional) the image to display on the left
                    //image: '',
                    // (bool | optional) if you want it to fade out on its own or just sit there
                    sticky: false,
                    // (int | optional) the time you want it to be alive for before fading out
                    time: 300,
                    // (string | optional) the class name you want to apply to that specific message
                    class_name: 'my-sticky-class'
                });

                //return false;
            }
        });
    }
});