$(document).ready(function () {

    var Region = function(initialAgeGroup) {
        var self = this;

        self.regionId = initialAgeGroup.regionId;
        self.name = initialAgeGroup.name;
    };

    function RegionViewModel () {
        var self = this;

        self.regions = ko.observableArray();

        self.regionName = ko.observable('');
        self.regionName.subscribe(function () {
            self.updateRegions();
        });

        self.updateRegions = function () {
            $.ajax({
                url: '/admin/getRegionsLike',
                method: 'POST',
                data: {
                    term: self.regionName()
                }
            }).done(function (response) {
                self.regions(response);
            });
        };

        self.getRegions = function () {
            self.updateRegions();
        };

        self.deleteRegion = function (region) {
            $.ajax({
                url: '/admin/deleteRegion',
                method: 'POST',
                data: {
                    regionJson: ko.toJSON(region)
                }
            }).done(function (response) {
                if(response == 'success'){
                    self.updateRegions();
                }else{
                    $('#danger-modal-title').text('Delete ERROR!');
                    $('#danger-modal-body').text('This region is already used');
                    $('#danger-modal').modal('show');
                }
            });
        };

        self.errors = ko.validation.group(self);
    }

    var regionViewModel = new RegionViewModel();
    ko.applyBindings(regionViewModel, document.getElementById('region-binding'));

/*    $('#region-name').on('input', function (e) {
        regionViewModel.updateRegions()
    });*/
});

function addRegion () {
    window.location = '/admin/addNewRegion';
};