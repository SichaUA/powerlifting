$(document).ready(function () {
    function UserViewModel() {
        var self = this;

        self.name = ko.observable('').extend({required: true});
        self.city = ko.observable('').extend({required: true});
        self.startDate = ko.observable('').extend();
        self.endDate = ko.observable('').extend();
        self.gender = ko.observable(true).extend({required: true});
        self.info = ko.observable('').extend({required: true});

        self.errors = ko.validation.group(self);

        self.newCompetition = function () {
            if(self.errors().length) {
                self.errors.showAllMessages();
                return;
            }

            $.ajax({
                url: '/moder/new-competition',
                type: 'POST',
                data: {
                    competitionJson: ko.toJSON(self)
                }
            }).done(function () {
                window.location = '/user/my-competitions';
            })
        }
    }

    var userViewModel = new UserViewModel();
    ko.applyBindings(userViewModel, document.getElementById('competition-form'));
});
