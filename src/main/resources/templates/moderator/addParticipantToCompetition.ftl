<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head lang="en">
    <meta charset="UTF-8">
    <title>Add Participants</title>
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
        <h3><i class="fa fa-angle-right"></i> Add Participant</h3>

        <div class="row mt">
            <div class="col-lg-12">
                <div class="form-panel">
                    <form class="form-horizontal style-form" id="participant-form" data-bind="submit: newParticipant">
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Email</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" placeholder="example@email.com" data-bind="value: email">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">First Name</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" placeholder="Vasya" data-bind="value: firstName">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Second Name</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" placeholder="Pupkin" data-bind="value: secondName">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Middle Name</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" placeholder="Vasylyovych" data-bind="value: middleName">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Birthday</label>
                            <div class="col-sm-10">
                                <input id="datepicker" type="text" class="form-control" data-bind="value: birthday">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Gender</label>
                            <div class="col-sm-10">
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="optionsRadios" id="optionsRadios1" value="1" data-bind="checked: gender">
                                        Male
                                    </label>
                                </div>
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="optionsRadios" id="optionsRadios2" value="0" data-bind="checked: gender">
                                        Female
                                    </label>
                                </div>
                            </div>
                        </div>

                        <input type="submit" value="Add" class="btn btn-theme">
                    </form>
                </div>
            </div>
        </div>

    </section>
</section>

<script type="application/javascript" src="/libs/js/knockout-3.2.0.js"></script>
<script type="application/javascript" src="/libs/js/knockout.validation.js"></script>
<script type="application/javascript" src="/js/unregistered/sign-up.js"></script>

<script src="/libs/js/bootstrap.js"></script>
<script src="/libs/js/bootstrap-datepicker.js"></script>
<script>
    $('#datepicker').datepicker();
</script>


</body>
</html>