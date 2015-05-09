$(document).ready(function () {
    var competition;
    var participants = [];

    //Competition
    function getCompetition() {
        $.ajax({
            url: '/getCompetition',
            method: 'POST',
            data:{
                competitionId: $('.competition-id').val()
            }
        }).done(function (response) {
            competition = response;
        });
    }

    window.setInterval(function () {
        getCompetition();
        getParticipants();
    }, 1000);

    //Participants
    function getParticipants() {
        $.ajax({
            url: '/moder/getCompetitionGroupParticipants/' + competition.broadcastingGroup,
            method: 'POST'
        }).done(function (response) {
            participants = response;
            participantsViewModel.participants([]);
            for(var i = 0; i < participants.length; i++) {
                participantsViewModel.participants.push(new Participant(participants[i]));
            }

            if(competition.broadcastingType == 1){
                participantsViewModel.participants.sort(SQSort);
            }else if(competition.broadcastingType == 2) {
                participantsViewModel.participants.sort(BPSort);
            }else{
                participantsViewModel.participants.sort(DLSort);
            }

            /*participantsViewModel.participantsSQ(participantsViewModel.participants.sort(SQSort));
            participantsViewModel.participantsBP(participantsViewModel.participants.sort(BPSort));
            participantsViewModel.participantsDL(participantsViewModel.participants.sort(DLSort));*/
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
    }

    var participantsViewModel = new ParticipantsViewModel();
    ko.applyBindings(participantsViewModel, document.getElementById('participants-all-info'));

});
