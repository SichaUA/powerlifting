$(document).ready(function () {
    function UserViewModel() {
        var self = this;

        self.email = ko.observable('').extend({
            required: true,
            email: true
        });
        self.firstName = ko.observable('').extend({required: true});
        self.secondName = ko.observable('').extend({required: true});
        self.middleName = ko.observable('').extend({required: true});
        self.birthday = ko.observable('').extend({required: true});
        self.gender = ko.observable(true).extend({required: true});
        self.password = ko.observable('').extend({required: true});
        self.confirmPassword = ko.observable('').extend({
            required: true,
            validation: {
                validator: function (value) {
                    return value === self.password();
                },
                message: 'Passwords most be equals!'
            }
        });

        self.errors = ko.validation.group(self);

        self.newUser = function () {
            if(self.errors().length) {
                self.errors.showAllMessages();
                return;
            }

            $.ajax({
                url: '/new-user',
                type: 'POST',
                data: {
                    studentJson: ko.toJSON(self)
//                    birthday: new Date(self.birthdayVal()).getTime()
                }
            }).done(function () {
                window.location = '/sign-in';
            })
        }
    }

    var userViewModel = new UserViewModel();
    ko.applyBindings(userViewModel, document.getElementById('user-form'));
});
