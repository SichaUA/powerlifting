<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head lang="en">
    <meta charset="UTF-8">
    <title>Competition creating</title>
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
        <h3><i class="fa fa-angle-right"></i> Create Competition</h3>

        <div class="row mt">
            <div class="col-lg-12">
                <div class="form-panel">
                    <form class="form-horizontal style-form" id="competition-form" data-bind="submit: newCompetition">
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Name</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" data-bind="value: name">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">City</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" data-bind="value: city">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Start date</label>
                            <div class="col-sm-10">
                                <input id="start-date" type="text" class="form-control" data-bind="value: startDate">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">End date</label>
                            <div class="col-sm-10">
                                <input id="end-date" type="text" class="form-control" data-bind="value: endDate">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Gender</label>
                            <div class="col-sm-10">
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="genderRadios" id="gender1" value="1" data-bind="checked: gender">
                                        Male
                                    </label>
                                </div>
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="genderRadios" id="gender2" value="0" data-bind="checked: gender">
                                        Female
                                    </label>
                                </div>
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="genderRadios" id="gender3" value="2" data-bind="checked: gender">
                                        Both
                                    </label>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Type</label>
                            <div class="col-sm-10">
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="typeRadios" id="type1" value="0" data-bind="checked: type">
                                        Eventing
                                    </label>
                                </div>
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="typeRadios" id="type2" value="1" data-bind="checked: type">
                                        Bench Press
                                    </label>
                                </div>
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="typeRadios" id="type2" value="2" data-bind="checked: type">
                                        Deadlift
                                    </label>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Age Group</label>
                            <div class="col-sm-10">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" id="ageGroup1" value="1" data-bind="checked: ageGroup1">
                                        1 вікова група (чоловіки/жінки)
                                    </label>
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" id="ageGroup2" value="2" data-bind="checked: ageGroup2">
                                        2 вікова група (юніори/юніорки)
                                    </label>
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" id="ageGroup3" value="3" data-bind="checked: ageGroup3">
                                        3 вікова група (юнаки/дівчата)
                                    </label>
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" id="ageGroup4" value="4" data-bind="checked: ageGroup4">
                                        4 вікова група (ветерани)
                                    </label>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Aditional information</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" rows="5" style="resize: vertical" data-bind="value: info"></textarea>
                            </div>
                        </div>

                        <input type="submit" value="Create" class="btn btn-theme">
                    </form>
                </div>
            </div>
        </div>

    </section>
</section>

<script type="application/javascript" src="/libs/js/knockout-3.2.0.js"></script>
<script type="application/javascript" src="/libs/js/knockout.validation.js"></script>
<script src="/js/moderator/competitionCreateForm.js"></script>

<#--<script src="libs/js/jquery.js"></script>-->
<script src="/libs/js/bootstrap.js"></script>
<script src="/libs/js/bootstrap-datepicker.js"></script>
<script>
    $('#start-date').datepicker();
    $('#end-date').datepicker();
</script>


</body>
</html>