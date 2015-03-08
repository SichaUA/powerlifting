$(document).ready(function () {

    function RegionViewModel () {
        var self = this;

        self.regionName = ko.observable('');

        self.addRegion = function (region) {
            $.ajax({
                url: '/admin/addRegion',
                method: 'POST',
                data: {
                    regionName: self.regionName()
                }
            }).done(function (response) {
                if(response == 'success'){
                    window.location = '/admin/editRegions';
                }else{
                    $('#danger-modal-title').text('Add ERROR!');
                    $('#danger-modal-body').text('This region is already exist');
                    $('#danger-modal').modal('show');
                }
            });
        };

        self.errors = ko.validation.group(self);
    }

    var regionViewModel = new RegionViewModel();
    ko.applyBindings(regionViewModel, document.getElementById('region-form'));
});