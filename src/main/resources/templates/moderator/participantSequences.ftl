<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Participants sequences</title>
    <!-- Bootstrap core CSS -->
    <link href="/libs/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="/libs/font-awesome/css/font-awesome.css" rel="stylesheet" />

    <!-- Custom styles for this template -->
    <link href="/libs/css/style.css" rel="stylesheet">
    <link href="/libs/css/style-responsive.css" rel="stylesheet">

    <link href="/libs/css/autocomplete.css" rel="stylesheet">

</head>
<body>
<#include "*/commonUser/menu.ftl">

<section id="main-content">
    <section class="wrapper">
        <div class="row mt">
            <div class="col-md-10">
                <h3><i class="fa fa-angle-right"></i> Participants Sequences</h3>
            </div>
            <div class="col-md-2">
                <input type="button" class="btn btn-round btn-info" value="Add sequence" onclick="addSequence()"/>
            </div>
            <div id="sequences-table" class="col-lg-12" data-bind="foreach: sequences">
                <div class="content-panel">
                    <table class="table table-striped table-advance table-hover">
                        <h4><i class="fa fa-angle-right"></i> Sequence #<span data-bind="text: sequenceNumber"></span></h4>
                        <p>Date: <span data-bind="text: date"></span></p>
                        <p data-bind="text: info"></p>
                        <hr>
                        <thead>
                            <tr>
                                <th><i class="fa fa-sort-numeric-asc"></i></th>
                                <th><i class="fa "></i> Gender</th>
                                <th><i class="fa "></i> Age Group</th>
                                <th><i class="fa "></i> Category</th>
                                <th><i class="fa "></i> Participant Count</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody data-bind="foreach: categories">
                            <tr>
                                <td data-bind="text: categoryNumber"></td>
                                <td data-bind="text: gender"></td>
                                <td data-bind="text: groupName"></td>
                                <td data-bind="text: name"></td>
                                <td data-bind="text: participantCount"></td>
                                <td></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
</section>
<script type="application/javascript" src="/libs/js/bootstrap.min.js"></script>

<script type="application/javascript" src="/libs/js/knockout-3.2.0.js"></script>
<script type="application/javascript" src="/libs/js/knockout.validation.js"></script>

<script type="text/javascript" charset="UTF-8" src="/js/moderator/participantSequences.js"></script>
</body>
</html>