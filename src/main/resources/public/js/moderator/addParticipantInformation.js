$(document).ready(function () {
    $('#region-input').autocomplete({
        serviceUrl: '/moder/getRegionLike',
        paramName: 'term',
        delimiter: ',',
        transformResult: function(response) {
            return {
                suggestions: $.map($.parseJSON(response), function(item) {
                    return { value: item.name, data: item.regionId };
                })
            };
        }
    });

    $('#new-region-form').submit(function (e) {
        e.preventDefault();

        $.ajax({
            url: '/moder/newRegion',
            method: 'POST',
            data: {
                regionName: $('#new-region-input').val()
            }
        }).done(function (response) {
            if(response !== 'success') {
                alert('This Region is already exist!');
            }else{
                $('#region-input').val($('#new-region-input').val());
            }
        });
    });

    function ParticipantViewModel() {
        var self = this;

        self.ageGroup = ko.observable();
        self.category = ko.observable();
        self.region = ko.observable('');
        self.ownParticipation = ko.observable(false);
        self.SQ = ko.observable('').extend();
        self.BP = ko.observable('').extend();
        self.DL = ko.observable('').extend();
        self.total = ko.observable('').extend();
        self.firstCoach = ko.observable('');
        self.personalCoach = ko.observable('');
        self.firstAdditionalCoach = ko.observable('');
        self.secondAdditionalCoach = ko.observable('');

        self.errors = ko.validation.group(self);

        self.newParticipant = function () {
            self.region = ko.observable($('#region-input').val());

            if(self.errors().length) {
                self.errors.showAllMessages();
                return;
            }

            var competition = window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
            var loc = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/'));
            var participant = loc.substring(loc.lastIndexOf('/') + 1);

            if(self.SQ() == '') {
                self.SQ = ko.observable(0);
            }
            if(self.BP() == '') {
                self.BP = ko.observable(0);
            }
            if(self.DL() == '') {
                self.DL = ko.observable(0);
            }
            if(self.total() == '') {
                self.DL = ko.observable(0);
            }

            $.ajax({
                url: '/moder/insertParticipantToCompetition/' + participant + '/' + competition,
                type: 'POST',
                data: {
                    participantAddingInfoJson: ko.toJSON(self)
                }
            }).done(function (response) {
                window.location = '/moder/add-participants/' + competition;
            })
        }
    }

    var participantViewModel = new ParticipantViewModel();
    ko.applyBindings(participantViewModel, document.getElementById('add-participant-form'));
});
