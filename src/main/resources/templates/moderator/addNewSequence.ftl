<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add new sequence</title>
    <!-- Bootstrap core CSS -->
    <link href="/libs/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="/libs/font-awesome/css/font-awesome.css" rel="stylesheet" />

    <!-- Custom styles for this template -->
    <link href="/libs/css/style.css" rel="stylesheet">
    <link href="/libs/css/style-responsive.css" rel="stylesheet">

    <link href="/libs/css/datepicker.css" rel="stylesheet">

</head>
<body>
<#include "*/commonUser/menu.ftl">

<section id="main-content">
    <section class="wrapper">
        <h3><i class="fa fa-angle-right"></i> Add New Sequences</h3>
        <div class="row mt">
            <div class="col-lg-12">
                <div class="form-panel">
                    <form id="sequence-add-form" class="form-horizontal style-form" data-bind="submit: addSequence">
                        <div class="form-group">
                            <div class="col-md-12">
                                <h3>Adding new sequence to ${competition.name}</h3>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-12">
                                <label class="col-sm-2 col-sm-2 control-label">Age groups and weight categories</label>
                                <div class="col-sm-10" data-bind="foreach: ageGroupsAndWeightCategories">
                                    <div class="checkbox">
                                        <input type="checkbox" data-bind="attr: { value: id }, checked: $root.selectedAgeGroupsAndWeightCategories">
                                        <span data-bind="text: name"></span>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-12">
                                <label class="col-sm-2 col-sm-2 control-label">Sequence date</label>
                                <div class="col-sm-10">
                                    <input id="datepicker" type="text" class="form-control" data-bind="value: date">
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-12">
                                <label class="col-sm-2 col-sm-2 control-label">Sequence info</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" data-bind="value: info">
                                </div>
                            </div>
                        </div>

                        <input type="submit" class="btn btn-round btn-success" value="Add"/>
                    </form>
                </div>
            </div>
        </div>
    </section>
</section>
<script type="application/javascript" src="/libs/js/bootstrap.min.js"></script>

<script src="/libs/js/bootstrap-datepicker.js"></script>
<script>
    $('#datepicker').datepicker();
</script>

<script type="text/javascript" charset="UTF-8" src="/js/moderator/addNewSequence.js"></script>
</body>
</html>