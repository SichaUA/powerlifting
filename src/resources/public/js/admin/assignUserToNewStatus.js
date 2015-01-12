$(document).ready(function () {
    function ModerViewModel() {
        var self = this;

        self.moderEmail = ko.observable('').extend({
            required: true,
            email: true
        });

        self.errors = ko.validation.group(self);

        self.newModer = function () {
            if(self.errors().length) {
                self.errors.showAllMessages();
                return;
            }

            $.ajax({
                url: '/admin/new-moder',
                type: 'POST',
                data: {
                    moderEmail: self.moderEmail()
                }
            }).done(function (response) {
                if(response === 'success') {
                    alert('moder success');
                }
            })
        }
    }

    function AdminViewModel() {
        var self = this;

        self.adminEmail = ko.observable('').extend({
            required: true,
            email: true
        });

        self.errors = ko.validation.group(self);

        self.newAdmin = function () {
            if(self.errors().length) {
                self.errors.showAllMessages();
                return;
            }

            $.ajax({
                url: '/admin/new-admin',
                type: 'POST',
                data: {
                    adminEmail: self.adminEmail()
                }
            }).done(function (response) {
                if(response === 'success') {
                    alert('admin success')
                }
            })
        }
    }

    var moderViewModel = new ModerViewModel();
    var adminViewModel = new AdminViewModel();
    ko.applyBindings(moderViewModel, document.getElementById('moder-form'));
    ko.applyBindings(adminViewModel, document.getElementById('admin-form'));
});
