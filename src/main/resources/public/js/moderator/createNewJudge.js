$(document).ready(function () {
    var today = new Date();

    function JudgeViewModel() {
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
        self.category = ko.observable().extend({required: true});
        self.assignmentDate = ko.observable((today.getMonth() + 1) + "/" + today.getDate() + "/" + today.getFullYear()).extend({required: true});
        self.addJudgeToCompetition = ko.observable(true);

        self.errors = ko.validation.group(self);

        self.newJudge = function () {
            self.birthday = ko.observable($('#birthday-datepicker').val());
            self.appointmentDate = ko.observable($('#appointment-datepicker').val());

            if(self.errors().length) {
                self.errors.showAllMessages();
                return;
            }

            $.ajax({
                url: '/moder/new-judge/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1),
                type: 'POST',
                data: {
                    judgeJson: ko.toJSON(self),
                    addJudgeToCompetition: self.addJudgeToCompetition()
                }
            }).done(function () {
                window.location = '/moder/add-judges/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
            })
        }
    }

    var judgeViewModel = new JudgeViewModel();
    ko.applyBindings(judgeViewModel, document.getElementById('judge-form'));
});
