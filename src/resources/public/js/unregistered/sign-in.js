$(document).ready(function () {
    function SignInViewModel () {
        var self = this;

        self.email = ko.observable('').extend({required: true});
        self.password = ko.observable('').extend({required: true});

        self.errors = ko.validation.group(self);

        self.signInRequest = function () {
            if(self.errors().length) {
                self.errors.showAllMessages();
                return;
            }

            $.ajax({
                url: "/sign-in-request",
                type: "POST",
                data: {
                    requestJson: ko.toJSON(self)
                }
            }).done(function (response) {
                if(response === 'success') {
                    window.location = '/';
                }else{
                    //TODO add error message
                }
            });
        }

    }

    var signInViewModel = new SignInViewModel();
    ko.applyBindings(signInViewModel, document.getElementById("request-form"));
});