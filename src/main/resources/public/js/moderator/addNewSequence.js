$(document).ready(function () {
    var ageGroupsAndWeightCategories = [];
    //var weightCategories = [];

    function getAgeGroups() {
        $.ajax({
            url: '/moder/getAgeGroupsAndWeightCategories/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1),
            method: 'POST'
        }).done(function (response) {
            ageGroupsAndWeightCategories = response;
            sequenceViewModel.ageGroupsAndWeightCategories([]);
            number = 0;
            for(var i = 0; i < response.length; i++) {
                sequenceViewModel.ageGroupsAndWeightCategories.push(new AgeGroupAndWeightCategory(response[i]));
            }
        });
    }

    var AgeGroupAndWeightCategory = function(initialAgeGroup) {
        var self = this;

        self.ageGroup = initialAgeGroup.ageGroup;
        self.weightCategory = initialAgeGroup.weightCategory;
        self.participantCount = initialAgeGroup.participantCount;
        var gender = (initialAgeGroup.weightCategory.gender == 0)? 'female':'male';
        self.name = initialAgeGroup.ageGroup.group + ' (' + initialAgeGroup.ageGroup.description + '), ' +
                    initialAgeGroup.weightCategory.name + ', gender - ' + gender +
                    ', participants - ' + initialAgeGroup.participantCount;
        self.id = initialAgeGroup.id;
    };

    var SequenceViewModel = function() {
        var self = this;

        self.ageGroupsAndWeightCategories = ko.observableArray();
        self.selectedAgeGroupsAndWeightCategories = ko.observableArray();

        var today = new Date();
        self.date = ko.observable((today.getMonth() + 1) + "/" + today.getDate() + "/" + today.getFullYear()).extend({required: true});
        self.info = ko.observable();
        self.errors = ko.validation.group(self);
        self.addSequence = function () {
            self.date = ko.observable($('#datepicker').val());

            if(self.errors().length) {
                self.errors.showAllMessages();
                return;
            }

            $.ajax({
                url: '/moder/addNewSequence/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1),
                method: 'POST',
                data: {
                    newSequenceJSON: ko.toJSON(self)
                }
            }).done(function (response) {
                if(response == 'success') {
                    window.location = '/moder/splitParticipantsIntoSequences/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
                }else{
                    alert('error');
                }
            });
        }
    };

    var sequenceViewModel = new SequenceViewModel();
    ko.applyBindings(sequenceViewModel, document.getElementById('sequence-add-form'));
    getAgeGroups();
});