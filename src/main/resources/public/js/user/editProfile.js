$(document).ready(function () {
    function ChangePassword() {
        var self = this;

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

        self.changePassword = function () {
            if(self.errors().length) {
                self.errors.showAllMessages();
                return;
            }

            $.ajax({
                url: '/change-password',
                type: 'POST',
                data: {
                    newPassword: self.password()
                }
            }).done(function () {
                $('#password-modal').modal('show');
                $('#password').val('');
                $('#confirmPassword').val('');
            })
        }
    }
    ko.applyBindings(new ChangePassword(), document.getElementById('password-tab'));
    
    function ChangeTitle() {
        var self = this;
        
        self.formSending = function () {
            $('#title-modal').modal('show');
        }
        self.changeTitle = function () {
            $.ajax({
                url: 'change-title',
                type: 'POST',
                data: {
                    newTitle: $('#title-select').val()
                }
            });
        }
    }

    ko.applyBindings(new ChangeTitle(), document.getElementById('title-tab'));

});
