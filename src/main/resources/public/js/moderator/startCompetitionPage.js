$(document).ready(function () {
    var participants = [];

    $('.sequence-select').change(function () {
        getParticipants();
    });

    function getParticipants() {
        $.ajax({
            url: '/moder/getCompetitionSequenceParticipants/' + $('.sequence-select').val(),
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

        self.ordinalNumber = initialParticipant.ordinalNumber;
        self.groupParticipantId = initialParticipant.groupParticipantId;
        self.name = initialParticipant.user.secondName + ' ' + initialParticipant.user.firstName + ' ' +
        initialParticipant.user.middleName;
        self.birthday = initialParticipant.user.birthday;
        self.gender = (initialParticipant.user.gender == 0) ? 'Female' : 'Male';
        self.ageGroup = initialParticipant.ageGroup.group + ' (' + initialParticipant.ageGroup.description + ')';
        self.weight = initialParticipant.weight;
        self.group = initialParticipant.group.groupNum;
        self.SQ = initialParticipant.declaredSQWeight;
        self.BP = initialParticipant.declaredBPWeight;
        self.DL = initialParticipant.declaredDLWeight;
        self.currentTotal = initialParticipant.currentTotal;

        self.SQFirst = ko.observable(initialParticipant.declaredSQWeight).extend({
            numeric: 2
        });

        self.SQSecond = ko.observable(0).extend({
            numeric: 2
        });

        self.SQThird = ko.observable(0).extend({
            numeric: 2
        });

        self.BPFirst = ko.observable(initialParticipant.declaredBPWeight).extend({
            numeric: 2
        });

        self.BPSecond = ko.observable(0).extend({
            numeric: 2
        });

        self.BPThird = ko.observable(0).extend({
            numeric: 2
        });

        self.DLFirst = ko.observable(initialParticipant.declaredDLWeight).extend({
            numeric: 2
        });

        self.DLSecond = ko.observable(0).extend({
            numeric: 2
        });

        self.DLThird = ko.observable(0).extend({
            numeric: 2
        });

        var participantTotal = self.SQFirst() + self.SQSecond() + self.SQThird() + self.BPFirst() + self.BPSecond() +
            self.BPThird() + self.DLFirst() + self.DLSecond() + self.DLThird();

        self.currentTotal = ko.computed(function () {
            return Math.max(self.SQFirst(), self.SQSecond(), self.SQThird()) + Math.max(self.BPFirst(), self.BPSecond(), self.BPThird()) +
                Math.max(self.DLFirst(), self.DLSecond(), self.DLThird());
        }, self).extend({
            numeric: 2
        });
    };


    function ParticipantsViewModel() {
        var self = this;

        self.participants = ko.observableArray(participants);

    }

    var participantsViewModel = new ParticipantsViewModel();
    ko.applyBindings(participantsViewModel, document.getElementById('participants-all-info'));
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