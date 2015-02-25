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

        self.category = ko.observable();
        self.region = ko.observable('');
        self.ownParticipation = ko.observable();
        self.SQ = ko.observable('').extend({required: true});
        self.BP = ko.observable('').extend({required: true});
        self.DL = ko.observable('').extend({required: true});

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

            $.ajax({
                url: '/moder/insertParticipantToCompetition/' + participant + '/' + competition,
                type: 'POST',
                data: {
                    category: self.category(),
                    region: self.region(),
                    ownParticipation: (self.ownParticipation()? self.ownParticipation() : false),
                    sq: self.SQ(),
                    bp: self.BP(),
                    dl: self.DL()
                }
            }).done(function (response) {
                window.location = '/moder/add-participants/' + competition;
            })
        }
    }

    var participantViewModel = new ParticipantViewModel();
    ko.applyBindings(participantViewModel, document.getElementById('add-participant-form'));
});
