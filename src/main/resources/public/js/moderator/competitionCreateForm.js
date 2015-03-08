$(document).ready(function () {
    var today = new Date();
    var todayFormatted = (today.getMonth() + 1) + "/" + today.getDate() + "/" + today.getFullYear();

    function UserViewModel() {
        var self = this;

        self.name = ko.observable('').extend({required: true});
        self.city = ko.observable('').extend({required: true});
        self.startDate = ko.observable((today.getMonth() + 1) + "/" + today.getDate() + "/" + today.getFullYear()).extend({required: true});
        self.endDate = ko.observable((today.getMonth() + 1) + "/" + today.getDate() + "/" + today.getFullYear()).extend({required: true});
        self.gender = ko.observable(true).extend({required: true});
        self.type = ko.observable(true).extend({required: true});
        self.ageGroup1 = ko.observable(true).extend();
        self.ageGroup2 = ko.observable(true).extend();
        self.ageGroup3 = ko.observable(true).extend();
        self.ageGroup4 = ko.observable(true).extend();
        self.info = ko.observable('').extend({required: true});

        self.errors = ko.validation.group(self);

        self.newCompetition = function () {
            self.startDate = ko.observable($('#start-date').val());
            self.endDate = ko.observable($('#end-date').val());

            if(self.errors().length) {
                self.errors.showAllMessages();
                return;
            }

            $.ajax({
                url: '/moder/new-competition',
                type: 'POST',
                data: {
                    competitionJson: ko.toJSON(self),
                    ageGroups: ko.toJSON([self.ageGroup1(), self.ageGroup2(), self.ageGroup3(), self.ageGroup4()])
                }
            }).done(function () {
                window.location = '/my-competitions';
            })
        }
    }

    var userViewModel = new UserViewModel();
    ko.applyBindings(userViewModel, document.getElementById('competition-form'));
});
