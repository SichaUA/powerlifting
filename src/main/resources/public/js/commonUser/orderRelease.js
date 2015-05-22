$(document).ready(function () {
    var singleton = 0;
    var competition;
    var participants = [{
        ordinalNumber : 0, groupParticipantId: 0, birthday: '', gender: '', ageGroup: '', weight: 0,
        group: '', currentTotal: 0, participantStatus: 0,
        SQFirst: 0, SQFirstStatus: 0, SQSecond: 0, SQSecondStatus: 0, SQThird: 0, SQThirdStatus: 0,
        BPFirst: 0, BPFirstStatus: 0, BPSecond: 0, BPSecondStatus: 0, BPThird: 0, BPThirdStatus: 0,
        DLFirst: 0, DLFirstStatus: 0, DLSecond: 0, DLSecondStatus: 0, DLThird: 0, DLThirdStatus: 0,
        name: '', photo: '', type: '', attempt: '', attemptWeight: ''}];

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

            getParticipants();
        });
    }

    //Participants
    function getParticipants() {
        $.ajax({
            url: '/getNotDisqualifiedCompetitionGroupParticipants/' + competition.broadcastingGroup,
            method: 'POST'
        }).done(function (response) {
            //participants = response;
            //participantsViewModel.participants([]);
            var newParticipants = [];
            for(var i = 0; i < response.length; i++) {
                newParticipants.push(new Participant(response[i]));
                //participantsViewModel.participants.push(new Participant(response[i]));
            }

            participantsViewModel.participants(newParticipants);
            if(competition.broadcastingType == 1){
                participantsViewModel.participants.sort(SQSort);
            }else if(competition.broadcastingType == 2) {
                participantsViewModel.participants.sort(BPSort);
            }else{
                participantsViewModel.participants.sort(DLSort);
            }

            if(singleton === 0) {
                ko.applyBindings(participantsViewModel, document.getElementById('viewModelBind'));
                singleton++;
            }

            //participantsViewModel.firstParticipant(new CurrParticipant(participantsViewModel.participants()[0]));
            //currParticipantViewModel.currParticipant(participantsViewModel.participants()[0]);

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

        self.photo = ko.observable('/img/avatars/' + initialParticipant.user.photo);
        self.type = ko.observable((competition.broadcastingType === 1)? 'SQ' : (competition.broadcastingType === 2)? 'BP' : 'DL');

        var attempt;
        var attemptWeight;

        if(self.type() == 'SQ') {
            if(self.SQFirstStatus() == 1 || self.SQFirstStatus() == 6) {
                attempt = '1st Attempt';
                attemptWeight = self.SQFirst();
            }
            else if(self.SQSecondStatus() == 1 || self.SQSecondStatus() == 6) {
                attempt = '2nd Attempt';
                attemptWeight = self.SQSecond();
            }
            else {
                attempt = '3rd Attempt';
                attemptWeight = self.SQThird();
            }
        }else if(self.type() == 'BP') {
            if(self.BPFirstStatus() == 1 || self.BPFirstStatus() == 6) {
                attempt = '1st Attempt';
                attemptWeight = self.BPFirst();
            }
            else if(self.BPSecondStatus() == 1 || self.BPSecondStatus() == 6) {
                attempt = '2nd Attempt';
                attemptWeight = self.BPSecond();
            }
            else {
                attempt = '3rd Attempt';
                attemptWeight = self.BPThird();
            }
        }else{
            if(self.DLFirstStatus() == 1 || self.DLFirstStatus() == 6) {
                attempt = '1st Attempt';
                attemptWeight = self.DLFirst();
            }
            else if(self.DLSecondStatus() == 1 || self.DLSecondStatus() == 6) {
                attempt = '2nd Attempt';
                attemptWeight = self.DLSecond();
            }
            else {
                attempt = '3rd Attempt';
                attemptWeight = self.DLThird();
            }
        }

        self.attempt = ko.observable(attempt);
        self.attemptWeight = ko.observable(attemptWeight + ' kg');
    };

    /*var CurrParticipant = function (initialParticipant) {
        var self = this;

        self.name = initialParticipant.name;
        self.photo = initialParticipant.photo();
        self.type = initialParticipant.type();
        self.attempt = initialParticipant.attempt();
        self.attemptWeight = initialParticipant.attemptWeight();

        return self;
    };
*/

    function ParticipantsViewModel() {
        var self = this;

        self.participants = ko.observableArray();

        self.participantsSQ = ko.observableArray(self.participants());
        self.participantsBP = ko.observableArray(self.participants());
        self.participantsDL = ko.observableArray(self.participants());
    }

    var participantsViewModel = new ParticipantsViewModel();


    window.setInterval(function () {
        getCompetition();
    }, 1000);

    /*function CurrParticipantViewModel() {
        var self = this;

        self.currParticipant = ko.observable(participants[0]);
    }

    var currParticipantViewModel = new CurrParticipantViewModel();
    ko.applyBindings(currParticipantViewModel, document.getElementById('curr-participant-form'));*/

});
