$(document).ready(function () {
    var groups = [];
    var participants = [];

    //Groups
    $('.sequence-select').change(function () {
        getGroups();

        broadcastInfoBtnReset();
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


    //Participants
    $('.group-select').change(function () {
        getParticipants();

        broadcastInfoBtnReset();
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

            participantsViewModel.participantsSQ(participantsViewModel.participants.sort(SQSort));
            participantsViewModel.participantsBP(participantsViewModel.participants.sort(BPSort));
            participantsViewModel.participantsDL(participantsViewModel.participants.sort(DLSort));
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
        self.participantStatus = ko.observable(initialParticipant.statusId);

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

        self.DLFirst = ko.observable(initialParticipant.firstDLAttempt).extend({
            numeric: 2
        });

        self.DLFirstStatus = ko.observable(initialParticipant.firstDLAttemptStatus);

        self.DLSecond = ko.observable(initialParticipant.secondDLAttempt).extend({
            numeric: 2
        });

        self.DLSecondStatus = ko.observable(initialParticipant.secondDLAttemptStatus);

        self.DLThird = ko.observable(initialParticipant.thirdDLAttempt).extend({
            numeric: 2
        });

        self.DLThirdStatus = ko.observable(initialParticipant.thirdDLAttemptStatus);

        var participantTotal = self.SQFirst() + self.SQSecond() + self.SQThird() + self.BPFirst() + self.BPSecond() +
            self.BPThird() + self.DLFirst() + self.DLSecond() + self.DLThird();

        self.currentTotal = ko.computed(function () {
            return Math.max(
                    (self.SQFirstStatus() == 2)? self.SQFirst() : 0,
                    (self.SQSecondStatus() == 2)? self.SQSecond() : 0,
                    (self.SQThirdStatus() == 2)? self.SQThird() : 0) +
                Math.max(
                    (self.BPFirstStatus() == 2)? self.BPFirst() : 0,
                    (self.BPSecondStatus() == 2)? self.BPSecond() : 0,
                    (self.BPThirdStatus() == 2)? self.BPThird() : 0) +
                Math.max(
                    (self.DLFirstStatus() == 2)? self.DLFirst() : 0,
                    (self.DLSecondStatus() == 2)? self.DLSecond() : 0,
                    (self.DLThirdStatus() == 2)? self.DLThird() : 0);

        }, self).extend({
            numeric: 2
        });

        //css SQ bind
        self.SQFirstCss = ko.computed(function () {
            return (self.participantStatus() == 2)? 'disq-participant' : (self.SQFirstStatus() == 5)? 'refuse-weight' : (self.SQFirstStatus() == 6)? 'judge-mistake' :
                (self.SQFirstStatus() == 4 || self.participantStatus() == 3)? 'doctor-weight' : (self.SQFirstStatus() == 2)? 'good-lift-weight' : (self.SQFirstStatus() == 3)? 'bad-lift-weight' : 'declared-weight';
        }, self);

        self.SQSecondCss = ko.computed(function () {
            return (self.participantStatus() == 2)? 'disq-participant' : (self.SQSecondStatus() == 5)? 'refuse-weight' : (self.SQSecondStatus() == 6)? 'judge-mistake' :
                (self.SQSecondStatus() == 4 || self.participantStatus() == 3)? 'doctor-weight' : (self.SQSecondStatus() == 2)? 'good-lift-weight' : (self.SQSecondStatus() == 3)? 'bad-lift-weight' : 'declared-weight';
        }, self);

        self.SQThirdCss = ko.computed(function () {
            return (self.participantStatus() == 2)? 'disq-participant' : (self.SQThirdStatus() == 5)? 'refuse-weight' : (self.SQThirdStatus() == 6)? 'judge-mistake' :
                (self.SQThirdStatus() == 4 || self.participantStatus() == 3)? 'doctor-weight' : (self.SQThirdStatus() == 2)? 'good-lift-weight' : (self.SQThirdStatus() == 3)? 'bad-lift-weight' : 'declared-weight';
        }, self);

        //css BP bind
        self.BPFirstCss = ko.computed(function () {
            return (self.participantStatus() == 2)? 'disq-participant' : (self.BPFirstStatus() == 5)? 'refuse-weight' : (self.BPFirstStatus() == 6)? 'judge-mistake' :
                (self.BPFirstStatus() == 4 || self.participantStatus() == 3)? 'doctor-weight' : (self.BPFirstStatus() == 2)? 'good-lift-weight' : (self.BPFirstStatus() == 3)? 'bad-lift-weight' : 'declared-weight';
        }, self);

        self.BPSecondCss = ko.computed(function () {
            return (self.participantStatus() == 2)? 'disq-participant' : (self.BPSecondStatus() == 5)? 'refuse-weight' : (self.BPSecondStatus() == 6)? 'judge-mistake' :
                (self.BPSecondStatus() == 4 || self.participantStatus() == 3)? 'doctor-weight' : (self.BPSecondStatus() == 2)? 'good-lift-weight' : (self.BPSecondStatus() == 3)? 'bad-lift-weight' : 'declared-weight';
        }, self);

        self.BPThirdCss = ko.computed(function () {
            return (self.participantStatus() == 2)? 'disq-participant' : (self.BPThirdStatus() == 5)? 'refuse-weight' : (self.BPThirdStatus() == 6)? 'judge-mistake' :
                (self.BPThirdStatus() == 4 || self.participantStatus() == 3)? 'doctor-weight' : (self.BPThirdStatus() == 2)? 'good-lift-weight' : (self.BPThirdStatus() == 3)? 'bad-lift-weight' : 'declared-weight';
        }, self);

        //css DL bind
        self.DLFirstCss = ko.computed(function () {
            return (self.participantStatus() == 2)? 'disq-participant' : (self.DLFirstStatus() == 5)? 'refuse-weight' : (self.DLFirstStatus() == 6)? 'judge-mistake' :
                (self.DLFirstStatus() == 4 || self.participantStatus() == 3)? 'doctor-weight' : (self.DLFirstStatus() == 2)? 'good-lift-weight' : (self.DLFirstStatus() == 3)? 'bad-lift-weight' : 'declared-weight';
        }, self);

        self.DLSecondCss = ko.computed(function () {
            return (self.participantStatus() == 2)? 'disq-participant' : (self.DLSecondStatus() == 5)? 'refuse-weight' : (self.DLSecondStatus() == 6)? 'judge-mistake' :
                (self.DLSecondStatus() == 4 || self.participantStatus() == 3)? 'doctor-weight' : (self.DLSecondStatus() == 2)? 'good-lift-weight' : (self.DLSecondStatus() == 3)? 'bad-lift-weight' : 'declared-weight';
        }, self);

        self.DLThirdCss = ko.computed(function () {
            return (self.participantStatus() == 2)? 'disq-participant' : (self.DLThirdStatus() == 5)? 'refuse-weight' : (self.DLThirdStatus() == 6)? 'judge-mistake' :
                (self.DLThirdStatus() == 4 || self.participantStatus() == 3)? 'doctor-weight' : (self.DLThirdStatus() == 2)? 'good-lift-weight' : (self.DLThirdStatus() == 3)? 'bad-lift-weight' : 'declared-weight';
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
        //SQ editsend

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
        //BP edits end

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
        //DL edits end

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

                    participantsViewModel.participantsSQ(participantsViewModel.participantsSQ.sort(SQSort));
                }else if(type === 'BP') {
                    if(attemptNum === 1)
                        participant.BPFirstStatus(1);
                    else if(attemptNum === 2)
                        participant.BPSecondStatus(1);
                    else if(attemptNum === 3)
                        participant.BPThirdStatus(1);

                    participantsViewModel.participantsBP(participantsViewModel.participantsBP.sort(BPSort));
                }else{
                    if(attemptNum === 1)
                        participant.DLFirstStatus(1);
                    else if(attemptNum === 2)
                        participant.DLSecondStatus(1);
                    else if(attemptNum === 3)
                        participant.DLThirdStatus(1);

                    participantsViewModel.participantsDL(participantsViewModel.participantsDL.sort(DLSort));
                }
            });
        }

        //SQ Buttons
        //Good lift
        self.changeFirstSQAttemptStatusGoodLift = function (participant) {
            changeAttemptStatus('SQ', 1, 2, participant);
        };

        self.changeSecondSQAttemptStatusGoodLift = function (participant) {
            changeAttemptStatus('SQ', 2, 2, participant);
        };

        self.changeThirdSQAttemptStatusGoodLift = function (participant) {
            changeAttemptStatus('SQ', 3, 2, participant);
        };

        self.changeFirstBPAttemptStatusGoodLift = function (participant) {
            changeAttemptStatus('BP', 1, 2, participant);
        };

        self.changeSecondBPAttemptStatusGoodLift = function (participant) {
            changeAttemptStatus('BP', 2, 2, participant);
        };

        self.changeThirdBPAttemptStatusGoodLift = function (participant) {
            changeAttemptStatus('BP', 3, 2, participant);
        };

        self.changeFirstDLAttemptStatusGoodLift = function (participant) {
            changeAttemptStatus('DL', 1, 2, participant);
        };

        self.changeSecondDLAttemptStatusGoodLift = function (participant) {
            changeAttemptStatus('DL', 2, 2, participant);
        };

        self.changeThirdDLAttemptStatusGoodLift = function (participant) {
            changeAttemptStatus('DL', 3, 2, participant);
        };
        //Good lift end

        //Bad lift
        self.changeFirstSQAttemptStatusBadLift = function (participant) {
            changeAttemptStatus('SQ', 1, 3, participant);
        };

        self.changeSecondSQAttemptStatusBadLift = function (participant) {
            changeAttemptStatus('SQ', 2, 3, participant);
        };

        self.changeThirdSQAttemptStatusBadLift = function (participant) {
            changeAttemptStatus('SQ', 3, 3, participant);
        };

        self.changeFirstBPAttemptStatusBadLift = function (participant) {
            changeAttemptStatus('BP', 1, 3, participant);
        };

        self.changeSecondBPAttemptStatusBadLift = function (participant) {
            changeAttemptStatus('BP', 2, 3, participant);
        };

        self.changeThirdBPAttemptStatusBadLift = function (participant) {
            changeAttemptStatus('BP', 3, 3, participant);
        };

        self.changeFirstDLAttemptStatusBadLift = function (participant) {
            changeAttemptStatus('DL', 1, 3, participant);
        };

        self.changeSecondDLAttemptStatusBadLift = function (participant) {
            changeAttemptStatus('DL', 2, 3, participant);
        };

        self.changeThirdDLAttemptStatusBadLift = function (participant) {
            changeAttemptStatus('DL', 3, 3, participant);
        };
        //Bad lift end

        //Doctor
        self.changeFirstSQAttemptStatusDoctor = function (participant) {
            changeAttemptStatus('SQ', 1, 4, participant);
        };

        self.changeSecondSQAttemptStatusDoctor = function (participant) {
            changeAttemptStatus('SQ', 2, 4, participant);
        };

        self.changeThirdSQAttemptStatusDoctor = function (participant) {
            changeAttemptStatus('SQ', 3, 4, participant);
        };

        self.changeFirstBPAttemptStatusDoctor = function (participant) {
            changeAttemptStatus('BP', 1, 4, participant);
        };

        self.changeSecondBPAttemptStatusDoctor = function (participant) {
            changeAttemptStatus('BP', 2, 4, participant);
        };

        self.changeThirdBPAttemptStatusDoctor = function (participant) {
            changeAttemptStatus('BP', 3, 4, participant);
        };

        self.changeFirstDLAttemptStatusDoctor = function (participant) {
            changeAttemptStatus('DL', 1, 4, participant);
        };

        self.changeSecondDLAttemptStatusDoctor = function (participant) {
            changeAttemptStatus('DL', 2, 4, participant);
        };

        self.changeThirdDLAttemptStatusDoctor = function (participant) {
            changeAttemptStatus('DL', 3, 4, participant);
        };
        //Doctor end

        //Cancel
        self.changeFirstSQAttemptStatusCancel = function (participant) {
            changeAttemptStatus('SQ', 1, -1, participant);
        };

        self.changeSecondSQAttemptStatusCancel = function (participant) {
            changeAttemptStatus('SQ', 2, -1, participant);
        };

        self.changeThirdSQAttemptStatusCancel = function (participant) {
            changeAttemptStatus('SQ', 3, -1, participant);
        };

        self.changeFirstBPAttemptStatusCancel = function (participant) {
            changeAttemptStatus('BP', 1, -1, participant);
        };

        self.changeSecondBPAttemptStatusCancel = function (participant) {
            changeAttemptStatus('BP', 2, -1, participant);
        };

        self.changeThirdBPAttemptStatusCancel = function (participant) {
            changeAttemptStatus('BP', 3, -1, participant);
        };

        self.changeFirstDLAttemptStatusCancel = function (participant) {
            changeAttemptStatus('DL', 1, -1, participant);
        };

        self.changeSecondDLAttemptStatusCancel = function (participant) {
            changeAttemptStatus('DL', 2, -1, participant);
        };

        self.changeThirdDLAttemptStatusCancel = function (participant) {
            changeAttemptStatus('DL', 3, -1, participant);
        };
        //Cancel end

        //Judge mistake
        self.changeFirstSQAttemptStatusJudgeMistake = function (participant) {
            changeAttemptStatus('SQ', 1, 6, participant);
        };

        self.changeSecondSQAttemptStatusJudgeMistake = function (participant) {
            changeAttemptStatus('SQ', 2, 6, participant);
        };

        self.changeThirdSQAttemptStatusJudgeMistake = function (participant) {
            changeAttemptStatus('SQ', 3, 6, participant);
        };

        self.changeFirstBPAttemptStatusJudgeMistake = function (participant) {
            changeAttemptStatus('BP', 1, 6, participant);
        };

        self.changeSecondBPAttemptStatusJudgeMistake = function (participant) {
            changeAttemptStatus('BP', 2, 6, participant);
        };

        self.changeThirdBPAttemptStatusJudgeMistake = function (participant) {
            changeAttemptStatus('BP', 3, 6, participant);
        };

        self.changeFirstDLAttemptStatusJudgeMistake = function (participant) {
            changeAttemptStatus('DL', 1, 6, participant);
        };

        self.changeSecondDLAttemptStatusJudgeMistake = function (participant) {
            changeAttemptStatus('DL', 2, 6, participant);
        };

        self.changeThirdDLAttemptStatusJudgeMistake = function (participant) {
            changeAttemptStatus('DL', 3, 6, participant);
        };
        //Judge mistake end

        //Refuse
        self.refuseFirstSQAttempt = function (participant) {
            changeAttemptStatus('SQ', 1, 5, participant);
        };

        self.refuseSecondSQAttempt = function (participant) {
            changeAttemptStatus('SQ', 2, 5, participant);
        };

        self.refuseThirdSQAttempt = function (participant) {
            changeAttemptStatus('SQ', 3, 5, participant);
        };

        self.refuseFirstBPAttempt = function (participant) {
            changeAttemptStatus('BP', 1, 5, participant);
        };

        self.refuseSecondBPAttempt = function (participant) {
            changeAttemptStatus('BP', 2, 5, participant);
        };

        self.refuseThirdBPAttempt = function (participant) {
            changeAttemptStatus('BP', 3, 5, participant);
        };

        self.refuseFirstDLAttempt = function (participant) {
            changeAttemptStatus('DL', 1, 5, participant);
        };

        self.refuseSecondDLAttempt = function (participant) {
            changeAttemptStatus('DL', 2, 5, participant);
        };

        self.refuseThirdDLAttempt = function (participant) {
            changeAttemptStatus('DL', 3, 5, participant);
        };
        //Refuse end

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
                if(status == -1){
                    status = 1;
                    participant.participantStatus(1);
                }

                if(type === 'SQ') {
                    if(attemptNum === 1)
                        participant.SQFirstStatus(status);
                    else if(attemptNum === 2)
                        participant.SQSecondStatus(status);
                    else if(attemptNum === 3)
                        participant.SQThirdStatus(status);

                    participantsViewModel.participantsSQ(participantsViewModel.participantsSQ.sort(SQSort));
                }else if(type === 'BP') {
                    if(attemptNum === 1)
                        participant.BPFirstStatus(status);
                    else if(attemptNum === 2)
                        participant.BPSecondStatus(status);
                    else if(attemptNum === 3)
                        participant.BPThirdStatus(status);

                    participantsViewModel.participantsBP(participantsViewModel.participantsBP.sort(BPSort));
                }else{
                    if(attemptNum === 1)
                        participant.DLFirstStatus(status);
                    else if(attemptNum === 2)
                        participant.DLSecondStatus(status);
                    else if(attemptNum === 3)
                        participant.DLThirdStatus(status);

                    participantsViewModel.participantsDL(participantsViewModel.participantsDL.sort(DLSort));
                }

                if(status == 4) {
                    changeGroupParticipantStatus(participant, 3);
                }
            });

            self.participantDisqualification = function (participant) {
                changeGroupParticipantStatus(participant, 2);
            };

            function changeGroupParticipantStatus(participant, status) {
                $.ajax({
                    url: '/moder/changeGroupParticipantStatus/',
                    method: 'POST',
                    data: {
                        groupParticipantId: participant.groupParticipantId,
                        status: status
                    }
                }).done(function (response) {
                    participant.participantStatus(status);

                    participantsViewModel.participantsSQ(participantsViewModel.participantsSQ.sort(SQSort));
                    participantsViewModel.participantsBP(participantsViewModel.participantsBP.sort(BPSort));
                    participantsViewModel.participantsDL(participantsViewModel.participantsDL.sort(DLSort));
                });
            }

        }
    }

    var participantsViewModel = new ParticipantsViewModel();
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

//Broadcasting
function broadcastingBtnClick() {
    if($('.btn-broadcasting').hasClass('btn-success'))
        stopBroadcasting();
    else
        startBroadcasting();
}

function startBroadcasting() {
    $.ajax({
        url: '/moder/startBroadcasting',
        method: 'POST',
        data: {
            competitionId: $('.competition-id').val()
        }
    }).done(function (response) {
        $('.btn-broadcasting').removeClass('btn-danger');
        $('.btn-broadcasting').addClass('btn-success');

        $('.btn-broadcasting-info').attr('disabled', false);
    });
}

function stopBroadcasting() {
    $.ajax({
        url: '/moder/stopBroadcasting',
        method: 'POST',
        data:{
            competitionId: $('.competition-id').val()
        }
    }).done(function (response) {
        $('.btn-broadcasting').removeClass('btn-success');
        $('.btn-broadcasting').addClass('btn-danger');

        $('.btn-broadcasting-info').attr('disabled', true);
    });
}

function updateBroadcastingInfo() {
    var type = $('.SQ-tab').hasClass('active')? 'SQ' : $('.BP-tab').hasClass('active')? 'BP' : 'DL';

    $.ajax({
        url: '/moder/updateBroadcastingInfo',
        method: 'POST',
        data:{
            competitionId: $('.competition-id').val(),
            sequenceId: $('.sequence-select').val(),
            groupId: $('.group-select').val(),
            type: type
        }
    }).done(function (response) {
        $('.btn-broadcasting-info').removeClass('btn-danger');
        $('.btn-broadcasting-info').addClass('btn-success');
    });
}

function broadcastInfoBtnReset() {
    $('.btn-broadcasting-info').removeClass('btn-success');
    $('.btn-broadcasting-info').addClass('btn-danger');
}

$('.SQ-tab').click(function (e) {
    broadcastInfoBtnReset();
});
$('.BP-tab').click(function (e) {
    broadcastInfoBtnReset();
});
$('.DL-tab').click(function (e) {
    broadcastInfoBtnReset();
});
//Broadcasting end