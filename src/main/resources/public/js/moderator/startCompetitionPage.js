$(document).ready(function () {
    var groups = [];
    var participants = [];

    $('.sequence-select').change(function () {
        getGroups();
    });

    function getGroups() {
        $.ajax({
            url: '/moder/getCompetitionSequenceGroups/' + $('.sequence-select').val(),
            method: 'POST'
        }).done(function (response) {
            groups = response;
            groupsViewModel.groups([]);
            for(var i = 0; i < groups.length; i++) {
                groupsViewModel.groups.push(groups[i]);
            }

            getParticipants();
        });
    }

    function GroupsViewModel() {
        var self = this;

        self.groups = ko.observableArray(groups);

    }

    var groupsViewModel = new GroupsViewModel();
    ko.applyBindings(groupsViewModel, document.getElementById('group-select'));
    getGroups();

    $('.group-select').change(function () {
        getParticipants();
    });

    function getParticipants() {
        $.ajax({
            url: '/moder/getCompetitionGroupParticipants/' + $('.group-select').val(),
            method: 'POST'
        }).done(function (response) {
            participants = response;
            participantsViewModel.participants([]);
            for(var i = 0; i < participants.length; i++) {
                participantsViewModel.participants.push(new Participant(participants[i]));
            }

            participantsViewModel.participantsSQ(participantsViewModel.participants.sort(function (l, r) {return (l.ordinalNumber >= r.ordinalNumber)? 1 : -1;}))
            participantsViewModel.participantsBP(participantsViewModel.participants.sort(function (l, r) {return (l.ordinalNumber >= r.ordinalNumber)? -1 : 1;}))
            participantsViewModel.participantsDL(participantsViewModel.participants.sort(function (l, r) {return (l.name >= r.name)? 1 : -1;}))
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
        self.currentTotal = initialParticipant.currentTotal;

        self.SQFirst = ko.observable(initialParticipant.firstSQAttempt).extend({
            numeric: 2
        });

        self.SQFirstStatus = ko.observable(initialParticipant.firstSQAttemptStatus);

        self.SQSecond = ko.observable(initialParticipant.secondSQAttempt).extend({
            numeric: 2
        });

        self.SQSecondStatus = ko.observable(initialParticipant.secondSQAttemptStatus);

        self.SQThird = ko.observable(initialParticipant.thirdSQAttempt).extend({
            numeric: 2
        });

        self.SQThirdStatus = ko.observable(initialParticipant.thirdSQAttemptStatus);

        self.BPFirst = ko.observable(initialParticipant.firstBPAttempt).extend({
            numeric: 2
        });

        self.BPFirstStatus = ko.observable(initialParticipant.firstBPAttemptStatus);

        self.BPSecond = ko.observable(initialParticipant.secondBPAttempt).extend({
            numeric: 2
        });

        self.BPSecondStatus = ko.observable(initialParticipant.secondBPAttemptStatus);

        self.BPThird = ko.observable(initialParticipant.thirdBPAttempt).extend({
            numeric: 2
        });

        self.BPThirdStatus = ko.observable(initialParticipant.thirdBPAttemptStatus);

        self.DLFirst = ko.observable(initialParticipant.firstBPAttempt).extend({
            numeric: 2
        });

        self.DLFirstStatus = ko.observable(initialParticipant.firstDLAttemptStatus);

        self.DLSecond = ko.observable(initialParticipant.secondBPAttempt).extend({
            numeric: 2
        });

        self.DLSecondStatus = ko.observable(initialParticipant.secondDLAttemptStatus);

        self.DLThird = ko.observable(initialParticipant.thirdBPAttempt).extend({
            numeric: 2
        });

        self.DLThirdStatus = ko.observable(initialParticipant.thirdDLAttemptStatus);

        var participantTotal = self.SQFirst() + self.SQSecond() + self.SQThird() + self.BPFirst() + self.BPSecond() +
            self.BPThird() + self.DLFirst() + self.DLSecond() + self.DLThird();

        self.currentTotal = ko.computed(function () {
            return Math.max(
                    (self.SQFirstStatus == 2)? self.SQFirst() : 0,
                    (self.SQSecondStatus == 2)? self.SQSecond() : 0,
                    (self.SQThirdStatus == 2)? self.SQThird() : 0) +
                Math.max(
                    (self.BPFirstStatus == 2)? self.BPFirst() : 0,
                    (self.BPSecondStatus == 2)? self.BPSecond() : 0,
                    (self.BPThirdStatus == 2)? self.BPThird() : 0) +
                Math.max(
                    (self.DLFirstStatus == 2)? self.DLFirst() : 0,
                    (self.DLSecondStatus == 2)? self.DLSecond() : 0,
                    (self.DLThirdStatus == 2)? self.DLThird() : 0);

        }, self).extend({
            numeric: 2
        });

        //css SQ bind
        self.SQFirstCss = ko.computed(function () {
            return (self.SQFirstStatus() == 4)? 'doctor-weight' : (self.SQFirstStatus() == 2)? 'ok-weight' : (self.SQFirstStatus() == 3)? 'cancel-weight' : 'declared-weight';
        }, self);

        self.SQSecondCss = ko.computed(function () {
            return (self.SQSecondStatus() == 4)? 'doctor-weight' : (self.SQSecondStatus() == 2)? 'ok-weight' : (self.SQSecondStatus() == 3)? 'cancel-weight' : 'declared-weight';
        }, self);

        self.SQThirdCss = ko.computed(function () {
            return (self.SQThirdStatus() == 4)? 'doctor-weight' : (self.SQThirdStatus() == 2)? 'ok-weight' : (self.SQThirdStatus() == 3)? 'cancel-weight' : 'declared-weight';
        }, self);

        //css BP bind
        self.BPFirstCss = ko.computed(function () {
            return (self.BPFirstStatus() == 4)? 'doctor-weight' : (self.BPFirstStatus() == 2)? 'ok-weight' : (self.BPFirstStatus() == 3)? 'cancel-weight' : 'declared-weight';
        }, self);

        self.BPSecondCss = ko.computed(function () {
            return (self.BPSecondStatus() == 4)? 'doctor-weight' : (self.BPSecondStatus() == 2)? 'ok-weight' : (self.BPSecondStatus() == 3)? 'cancel-weight' : 'declared-weight';
        }, self);

        self.BPThirdCss = ko.computed(function () {
            return (self.BPThirdStatus() == 4)? 'doctor-weight' : (self.BPThirdStatus() == 2)? 'ok-weight' : (self.BPThirdStatus() == 3)? 'cancel-weight' : 'declared-weight';
        }, self);

        //css DL bind
        self.DLFirstCss = ko.computed(function () {
            return (self.DLFirstStatus() == 4)? 'doctor-weight' : (self.DLFirstStatus() == 2)? 'ok-weight' : (self.DLFirstStatus() == 3)? 'cancel-weight' : 'declared-weight';
        }, self);

        self.DLSecondCss = ko.computed(function () {
            return (self.DLSecondStatus() == 4)? 'doctor-weight' : (self.DLSecondStatus() == 2)? 'ok-weight' : (self.DLSecondStatus() == 3)? 'cancel-weight' : 'declared-weight';
        }, self);

        self.DLThirdCss = ko.computed(function () {
            return (self.DLThirdStatus() == 4)? 'doctor-weight' : (self.DLThirdStatus() == 2)? 'ok-weight' : (self.DLThirdStatus() == 3)? 'cancel-weight' : 'declared-weight';
        }, self);
    };


    function ParticipantsViewModel() {
        var self = this;

        self.participants = ko.observableArray(participants);

        self.participantsSQ = ko.observableArray(self.participants());
        self.participantsBP = ko.observableArray(self.participants());
        self.participantsDL = ko.observableArray(self.participants());

        //SQ edits
        self.changeFirstSQAttemptWeight = function (participant) {
            changeFirstAttemptWeight('SQ', 1, participant);
        };

        self.changeSecondSQAttemptWeight = function (participant) {
            changeFirstAttemptWeight('SQ', 2, participant);
        };

        self.changeThirdSQAttemptWeight = function (participant) {
            changeFirstAttemptWeight('SQ', 3, participant);
        };

        //BP edits
        self.changeFirstBPAttemptWeight = function (participant) {
            changeFirstAttemptWeight('BP', 1, participant);
        };

        self.changeSecondBPAttemptWeight = function (participant) {
            changeFirstAttemptWeight('BP', 2, participant);
        };

        self.changeThirdBPAttemptWeight = function (participant) {
            changeFirstAttemptWeight('BP', 3, participant);
        };

        //DL edits
        self.changeFirstDLAttemptWeight = function (participant) {
            changeFirstAttemptWeight('DL', 1, participant);
        };

        self.changeSecondDLAttemptWeight = function (participant) {
            changeFirstAttemptWeight('DL', 2, participant);
        };

        self.changeThirdDLAttemptWeight = function (participant) {
            changeFirstAttemptWeight('DL', 3, participant);
        };

        function changeFirstAttemptWeight(type, attemptNum, participant) {
            var weight;
            if(type === 'SQ') {
                if(attemptNum === 1)
                    weight = participant.SQFirst;
                else if(attemptNum === 2)
                    weight = participant.SQSecond;
                else if(attemptNum === 3)
                    weight = participant.SQThird;
            }else if(type === 'BP') {
                if(attemptNum === 1)
                    weight = participant.BPFirst;
                else if(attemptNum === 2)
                    weight = participant.BPSecond;
                else if(attemptNum === 3)
                    weight = participant.BPThird;
            }else{
                if(attemptNum === 1)
                    weight = participant.DLFirst;
                else if(attemptNum === 2)
                    weight = participant.DLSecond;
                else if(attemptNum === 3)
                    weight = participant.DLThird;
            }

            $.ajax({
                url: '/moder/setFirstAttemptWeight/',
                method: 'POST',
                data: {
                    groupParticipantId: participant.groupParticipantId,
                    type: type,
                    weight: weight,
                    attemptNum: attemptNum
                }
            }).done(function (response) {
                if(type === 'SQ') {
                    if(attemptNum === 1)
                        participant.SQFirstStatus(1);
                    else if(attemptNum === 2)
                        participant.SQSecondStatus(1);
                    else if(attemptNum === 3)
                        participant.SQThirdStatus(1);
                }else if(type === 'BP') {
                    if(attemptNum === 1)
                        participant.BPFirstStatus(1);
                    else if(attemptNum === 2)
                        participant.BPSecondStatus(1);
                    else if(attemptNum === 3)
                        participant.BPThirdStatus(1);
                }else{
                    if(attemptNum === 1)
                        participant.DLFirstStatus(1);
                    else if(attemptNum === 2)
                        participant.DLSecondStatus(1);
                    else if(attemptNum === 3)
                        participant.DLThirdStatus(1);
                }
            });
        }

        //SQ Buttons
        self.changeFirstSQAttemptStatusOK = function (participant) {
            changeAttemptStatus('SQ', 1, 2, participant);
        };

        self.changeSecondSQAttemptStatusOK = function (participant) {
            changeAttemptStatus('SQ', 2, 2, participant);
        };

        self.changeThirdSQAttemptStatusOK = function (participant) {
            changeAttemptStatus('SQ', 3, 2, participant);
        };

        self.changeFirstSQAttemptStatusCancel = function (participant) {
            changeAttemptStatus('SQ', 1, 3, participant);
        };

        self.changeSecondSQAttemptStatusCancel = function (participant) {
            changeAttemptStatus('SQ', 2, 3, participant);
        };

        self.changeThirdSQAttemptStatusCancel = function (participant) {
            changeAttemptStatus('SQ', 3, 3, participant);
        };

        self.changeFirstSQAttemptStatusDoctor = function (participant) {
            changeAttemptStatus('SQ', 1, 4, participant);
        };

        self.changeSecondSQAttemptStatusDoctor = function (participant) {
            changeAttemptStatus('SQ', 2, 4, participant);
        };

        self.changeThirdSQAttemptStatusDoctor = function (participant) {
            changeAttemptStatus('SQ', 3, 4, participant);
        };

        function changeAttemptStatus(type, attemptNum, status, participant) {
            var weight;
            if(type === 'SQ') {
                if(attemptNum === 1)
                    weight = participant.SQFirst;
                else if(attemptNum === 2)
                    weight = participant.SQSecond;
                else if(attemptNum === 3)
                    weight = participant.SQThird;
            }else if(type === 'BP') {
                if(attemptNum === 1)
                    weight = participant.BPFirst;
                else if(attemptNum === 2)
                    weight = participant.BPSecond;
                else if(attemptNum === 3)
                    weight = participant.BPThird;
            }else{
                if(attemptNum === 1)
                    weight = participant.DLFirst;
                else if(attemptNum === 2)
                    weight = participant.DLSecond;
                else if(attemptNum === 3)
                    weight = participant.DLThird;
            }

            $.ajax({
                url: '/moder/changeAttemptStatus/',
                method: 'POST',
                data: {
                    groupParticipantId: participant.groupParticipantId,
                    type: type,
                    weight: weight,
                    attemptNum: attemptNum,
                    status: status
                }
            }).done(function (response) {
                if(type === 'SQ') {
                    if(attemptNum === 1)
                        participant.SQFirstStatus(status);
                    else if(attemptNum === 2)
                        participant.SQSecondStatus(status);
                    else if(attemptNum === 3)
                        participant.SQThirdStatus(status);
                }else if(type === 'BP') {
                    if(attemptNum === 1)
                        participant.BPFirstStatus(status);
                    else if(attemptNum === 2)
                        participant.BPSecondStatus(status);
                    else if(attemptNum === 3)
                        participant.BPThirdStatus(status);
                }else{
                    if(attemptNum === 1)
                        participant.DLFirstStatus(status);
                    else if(attemptNum === 2)
                        participant.DLSecondStatus(status);
                    else if(attemptNum === 3)
                        participant.DLthirdStatus(status);
                }
            });
        }
    }

    var participantsViewModel = new ParticipantsViewModel();
    /*participantsViewModel.SQFirstCss = ko.pureComputed(function () {
        return (participantsViewModel.SQFirstStatus == 1)? 'declared-weight' : (participantsViewModel.SQFirstStatus == 2)? 'ok-weight' : (participantsViewModel.SQFirstStatus == 3)? 'cancel-weight' :'doctor-weight';
    }, participantsViewModel);*/
    ko.applyBindings(participantsViewModel, document.getElementById('participants-all-info'));



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