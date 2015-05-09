<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Weighing participants</title>
    <!-- Bootstrap core CSS -->
    <link href="/libs/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="/libs/font-awesome/css/font-awesome.css" rel="stylesheet" />

    <!-- Custom styles for this template -->
    <link href="/libs/css/style.css" rel="stylesheet">
    <link href="/libs/css/style-responsive.css" rel="stylesheet">

    <link href="/libs/css/autocomplete.css" rel="stylesheet">

    <link rel="stylesheet" type="text/css" href="/libs/js/gritter/css/jquery.gritter.css" />

</head>
<body>
<#include "*/commonUser/menu.ftl">

<section id="main-content">
    <section class="wrapper">
        <div class="row mt">
            <div class="col-md-12">
                <h3><i class="fa fa-angle-right"></i> Weighing participants</h3>
            </div>

            <div class="row col-md-12">
                <div class="col-md-12">
                    <div class="col-md-2">
                        <h4>Select sequence</h4>
                    </div>

                    <div class="col-md-4">
                        <select class="form-control sequence-select">
                            <#assign x = 1>
                            <#list sequences as sequence>
                                <option value="${sequence.sequenceId}">#${x}, ${sequence.date}, ${sequence.info}</option>
                                <#assign x = x + 1>
                            </#list>
                        </select>
                    </div>
                </div>
            </div>

            <div id="sequence-table" class="col-lg-12">
                <div class="content-panel">
                    <table id="participant-table" class="table table-striped table-advance table-hover">
                        <h4><i class="fa fa-angle-right"></i> Participants:</h4>
                        <hr>
                        <thead>
                            <tr>
                                <th><i class="fa fa-sort-numeric-asc"></i> Ordinal</th>
                                <th><i class="fa fa-user"></i> Lifter</th>
                                <th><i class="fa fa-calendar"></i> Birthday</th>
                                <th><i class="fa "></i> Gender</th>
                                <th><i class="fa fa-group"></i> Age Group</th>
                                <th><i class="fa fa-group"></i> Weight Category</th>
                                <th><i class=" fa"></i> Group</th>
                                <th><i class=" fa"></i> Weight</th>
                                <th><i class=" fa"></i> Status</th>
                                <th><i class=" fa"></i> 1st Attempt SQ</th>
                                <th><i class=" fa"></i> 1st Attempt BP</th>
                                <th><i class=" fa"></i> 1st Attempt DL</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody data-bind="foreach: participants">
                            <tr>
                                <td data-bind="text: ordinalNumber"></td>
                                <td data-bind="text: name"></td>
                                <td data-bind="text: birthday"></td>
                                <td data-bind="text: gender"></td>
                                <td data-bind="text: ageGroup"></td>
                                <td data-bind="text: weightCategory"></td>
                                <td data-bind="text: group"></td>
                                <td>
                                    <input type="text" class="form-control" data-bind="value: weight, event: {change: $root.changeParticipantWeight}"/>
                                </td>
                                <td data-bind="text: status"></td>
                                <td>
                                    <input type="text" class="form-control" data-bind="value: firstSQ, event: {change: $root.setFirstSQAttempt}"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" data-bind="value: firstBP, event: {change: $root.setFirstBPAttempt}"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" data-bind="value: firstDL, event: {change: $root.setFirstDLAttempt}"/>
                                </td>
                                <td></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
</section>
<#--<#include "*/modal/modal.ftl">-->

<script type="application/javascript" src="/libs/js/bootstrap.min.js"></script>

<script type="application/javascript" src="/libs/js/knockout-3.2.0.js"></script>
<script type="application/javascript" src="/libs/js/knockout.validation.js"></script>

<#--<script type="text/javascript" src="/libs/js/gritter/js/jquery.gritter.js"></script>
<script type="text/javascript" src="/libs/js/gritter-conf.js"></script>-->

<script type="text/javascript" charset="UTF-8" src="/js/moderator/weighingParticipants.js"></script>
</body>
</html>