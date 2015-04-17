$(document).ready(function () {
    var today = new Date();

    function UserViewModel() {
        var self = this;

        self.email = ko.observable('').extend({
            email: true
        });
        self.firstName = ko.observable('').extend({required: true});
        self.secondName = ko.observable('').extend({required: true});
        self.middleName = ko.observable('').extend({required: true});
        self.birthday = ko.observable((today.getMonth() + 1) + "/" + today.getDate() + "/" + today.getFullYear()).extend({required: true});
        self.gender = ko.observable(true).extend({required: true});
        self.addJudgeToCompetition = ko.observable(true);

        self.errors = ko.validation.group(self);

        self.newParticipant = function () {
            self.birthday = ko.observable($('#datepicker').val());

            if(self.errors().length) {
                self.errors.showAllMessages();
                return;
            }

            $.ajax({
                url: '/moder/new-user/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1),
                type: 'POST',
                data: {
                    participantJson: ko.toJSON(self),
                    addParticipantToCompetition: self.addJudgeToCompetition()
                }
            }).done(function (response) {
                if(response == 'success') {
                    window.location = '/moder/add-participants/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
                }else{
                    window.location = response;
                }
            })
        }
    }

    var userViewModel = new UserViewModel();
    ko.applyBindings(userViewModel, document.getElementById('participant-form'));
});
