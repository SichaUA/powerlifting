$(document).ready(function () {
    var sequences = [
        /*{
            streamId: 1,
            competition: 1,
            date: '12/12/2012',
            info: 'dfgh hgfds sdfg',
            categories: [{categoryId: 1}]
        },
        {
            streamId: 2,
            competition: 1,
            date: '12/13/2012',
            info: 'dt gyh jk',
            categories: [{categoryId: 2}]
        }*/
    ];
    var categoryIndex = 1;

    function getSequences() {
        $.ajax({
            url: '/moder/getSequences/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1),
            method: 'POST'
        }).done(function (response) {
            sequences = response;
            sequenceViewModel.sequences([]);
            for(var i = 0; i < sequences.length; i++) {
                sequenceViewModel.sequences.push(new Sequence(sequences[i]));
            }
        });
    }

    var Sequence = function (initialSequence) {
        var self = this;

        self.sequenceNumber = ko.observable(sequences.indexOf(initialSequence) + 1);
        self.sequenceId = initialSequence.sequenceId;
        self.competition = initialSequence.competition;
        self.date = initialSequence.date;
        self.info = initialSequence.info;
        self.categories = ko.observableArray();
        var totalParticipant = 0;
        for(var i = 0; i < initialSequence.categories.length; i++) {
            self.categories.push(new Category(initialSequence.categories[i]));
            totalParticipant += initialSequence.categories[i].participantCount;
        }
        self.total = ko.observable(totalParticipant);

        categoryIndex = 1;
    };

    var Category = function (initialCategory) {
        var self = this;

        self.categoryNumber = ko.observable(categoryIndex++);
        self.categoryId = initialCategory.key.categoryId;
        //self.gender = ko.observable(initialCategory.gender);
        self.gender = (initialCategory.key.gender == 0)? 'Female' : 'Male';//ko.pureComputed(function () {
            /*return (initialCategory.gender == 0)? 'Female' : 'Male';
        }, self);*/
        self.minWeight = initialCategory.key.minWeight;
        self.maxWeight = initialCategory.key.maxWeight;
        self.name = initialCategory.key.name;
        self.participantCount = initialCategory.key.participantCount;

        self.ageGroupId = initialCategory.value.groupId;
        self.groupName = initialCategory.value.group + ' (' + initialCategory.value.description + ")";
    };

    function SequenceViewModel() {
        var self = this;

        self.sequences = ko.observableArray();
        for(var i = 0; i < sequences.length; i++) {
            self.sequences.push(new Sequence(sequences[i]));
        }
    }

    var sequenceViewModel = new SequenceViewModel();
    ko.applyBindings(sequenceViewModel, document.getElementById('sequences-table'));
    getSequences();
});

function addSequence() {
    window.location = '/moder/addNewSequence/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
}