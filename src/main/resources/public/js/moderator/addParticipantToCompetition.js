$(document).ready(function () {
    var today = new Date();

    function ParticipantViewModel() {
        var self = this;

        self.email = ko.observable('').extend({
            required: true,
            email: true
        });
        self.firstName = ko.observable('').extend({required: true});
        self.secondName = ko.observable('').extend({required: true});
        self.middleName = ko.observable('').extend({required: true});
        self.birthday = ko.observable((today.getMonth() + 1) + "/" + today.getDate() + "/" + today.getFullYear()).extend({required: true});
        self.gender = ko.observable(true).extend({required: true});

        self.errors = ko.validation.group(self);

        self.newParticipant = function () {
            self.birthday = ko.observable($('#datepicker').val());

            if(self.errors().length) {
                self.errors.showAllMessages();
                return;
            }

            $.ajax({
                url: '/moder/add-participant',
                type: 'POST',
                data: {
                    participantJson: ko.toJSON(self)
                }
            }).done(function () {
                //window.location = '/sign-in';
            })
        }
    }

    var participantViewModel = new ParticipantViewModel();
    ko.applyBindings(participantViewModel, document.getElementById('participant-form'));
});
