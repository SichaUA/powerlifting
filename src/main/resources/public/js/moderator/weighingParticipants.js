$(document).ready(function () {
    var participants = [];

    $('.sequence-select').change(function () {
        getParticipants();
    });

    function getParticipants() {
        $.ajax({
            url: '/moder/getSequenceParticipants/' + $('.sequence-select').val(),
            method: 'POST'
        }).done(function (response) {
            participants = response;
            participantsViewModel.participants([]);
            for(var i = 0; i < participants.length; i++) {
                participantsViewModel.participants.push(new Participant(participants[i]));
            }
        });
    }

    var Participant = function (initialParticipant) {
        var self = this;

        self.groupParticipantId = initialParticipant.groupParticipantId;
        self.name = initialParticipant.user.secondName + ' ' + initialParticipant.user.firstName + ' ' +
                initialParticipant.user.middleName;
        self.birthday = initialParticipant.user.birthday;
        self.gender = (initialParticipant.user.gender == 0) ? 'Female' : 'Male';
        self.ageGroup = initialParticipant.ageGroup.group + ' (' + initialParticipant.ageGroup.description + ')';
        self.weightCategory = initialParticipant.weightCategory.name;
        self.group = initialParticipant.group.groupNum;
        self.ordinalNumber = initialParticipant.ordinalNumber;

        self.weight = ko.observable(initialParticipant.participantWeight).extend({
            numeric: 2
        });

        self.firstSQ = ko.observable(initialParticipant.firstSQAttempt).extend({
            numeric: 2
        });

        self.firstBP = ko.observable(initialParticipant.firstBPAttempt).extend({
            numeric: 2
        });

        self.firstDL = ko.observable(initialParticipant.firstDLAttempt).extend({
            numeric: 2
        });

        self.status = ko.observable((initialParticipant.statusId == 2)? 'Disqualified' : '');
    };


    function ParticipantsViewModel() {
        var self = this;

        self.participants = ko.observableArray(participants);

        self.changeParticipantWeight = function (participant) {
            $.ajax({
                url: '/moder/changeParticipantWeight',
                method: 'POST',
                data: {
                    groupParticipantId: participant.groupParticipantId,
                    weight: participant.weight
                }
            }).done(function (response) {
                if(response === 'Not a Number') {
                    //participant.weight(0);
                    //self.changeParticipantWeight(participant);
                }else{
                    participant.status(response);
                }
            });
        };

        self.setFirstSQAttempt = function (participant) {
            $.ajax({
                url: '/moder/setFirstAttemptWeight',
                method: 'POST',
                data: {
                    groupParticipantId: participant.groupParticipantId,
                    type: 'SQ',
                    weight: participant.firstSQ,
                    attemptNum: 1
                }
            }).done(function (response) {

            });
        };

        self.setFirstBPAttempt = function (participant) {
            $.ajax({
                url: '/moder/setFirstAttemptWeight',
                method: 'POST',
                data: {
                    groupParticipantId: participant.groupParticipantId,
                    type: 'BP',
                    weight: participant.firstBP,
                    attemptNum: 1
                }
            }).done(function (response) {

            });
        };

        self.setFirstDLAttempt = function (participant) {
            $.ajax({
                url: '/moder/setFirstAttemptWeight',
                method: 'POST',
                data: {
                    groupParticipantId: participant.groupParticipantId,
                    type: 'DL',
                    weight: participant.firstDL,
                    attemptNum: 1
                }
            }).done(function (response) {

            });
        };
    }

    var participantsViewModel = new ParticipantsViewModel();
    ko.applyBindings(participantsViewModel, document.getElementById('participant-table'));
    getParticipants();



    ko.extenders.numeric = function(target, precision) {
        //create a writeable computed property to intercept writes to our observable
        var result = ko.computed({
            read: target,
            write: function(newValue) {
                var current = target(),
                    valueToWrite = Math.round(isNaN(newValue) ? 0 : parseFloat(newValue) * Math.pow(10, precision)) / Math.pow(10, precision);

                if (valueToWrite !== current) {
                    target(valueToWrite);
                } else {
                    //if the rounded value is the same, but a different value was written, force a notification for the current field to pick it up
                    if (newValue != current) {
                        target.notifySubscribers(valueToWrite);
                    }
                }
            }
        }).extend({ notify: "always" });

        //initialize with current value to make sure it is rounded appropriately
        result(target());

        //return the new computed property
        return result;
    };
});