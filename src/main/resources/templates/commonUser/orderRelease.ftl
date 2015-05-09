<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order release</title>
    <!-- Bootstrap core CSS -->
    <link href="/libs/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="/libs/font-awesome/css/font-awesome.css" rel="stylesheet" />

    <!-- Custom styles for this template -->
    <link href="/libs/css/style.css" rel="stylesheet">
    <link href="/libs/css/style-responsive.css" rel="stylesheet">

    <link rel="stylesheet" type="text/css" href="/libs/lineicons/style.css">

    <link href="/css/startCompetition/startCompetitionPage.css" rel="stylesheet">

<#--<link rel="stylesheet" type="text/css" href="/libs/js/gritter/css/jquery.gritter.css" />-->

</head>
<body>
<#include "*/commonUser/menu.ftl">

<section id="main-content">
    <section class="wrapper">
        <div class="row mt">
            <div class="row col-md-12">
                <h3><i class="fa fa-angle-right"></i> Order release</h3>
            </div>

            <div id="participants-all-info" class="row col-md-12">
                <input type="hidden" class="competition-id" value="${competition.id}"/>

                <div class="participants-competition">
                    <div id="sequence-table" class="col-lg-12">
                        <div class="content-panel">
                            <table id="participant-table" class="table table-striped table-advance table-hover">
                                <h3><i class="fa fa-angle-right"></i> Type: <#if competition.broadcastingType == 1>SQ<#elseif competition.broadcastingType == 2>BP<#else>DL</#if></h3>
                                <h4><i class="fa fa-angle-right"></i> Participants:</h4>
                                <hr>
                                <thead>
                                <tr>
                                    <th><i class="fa fa-sort-numeric-asc"></i> Ordinal</th>
                                    <th><i class="fa fa-user"></i> Lifter</th>
                                    <th><i class="fa fa-calendar"></i> Birthday</th>
                                    <th><i class="fa "></i> Gender</th>
                                    <th><i class="fa fa-group"></i> Age Group</th>
                                    <th>Weight</th>
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
                                    <td data-bind="text: weight"></td>
                                    <#--<td data-bind="css: SQFirstCss">
                                        <div class="col-md-12">
                                            <input type="text" class="form-control" data-bind="value: SQFirst, event: {change: $root.changeFirstSQAttemptWeight}"/>
                                        </div>
                                        <div class="col-md-12 btn-group">
                                            <button class="btn btn-success btn-xs" data-toggle="popover" title="Good lift" data-trigger="hover" data-bind="click: $root.changeFirstSQAttemptStatusGoodLift"><i class=" fa fa-thumbs-o-up"></i></button>
                                            <button class="btn btn-danger btn-xs" data-toggle="popover" title="Bad lift" data-trigger="hover" data-bind="click: $root.changeFirstSQAttemptStatusBadLift"><i class=" fa fa-thumbs-o-down"></i></button>
                                            <button class="btn btn-theme02 btn-xs" data-toggle="popover" title="Refuse" data-trigger="hover" data-bind="click: $root.refuseFirstSQAttempt"><i class=" fa fa-flag"></i></button>
                                            <button class="btn btn-info btn-xs" data-toggle="popover" title="Removed by doctor" data-trigger="hover" data-bind="click: $root.changeFirstSQAttemptStatusDoctor"><i class=" fa fa-medkit"></i></button>
                                            <button class="btn btn-theme-disq btn-xs" data-toggle="popover" title="Disqualification" data-trigger="hover" data-bind="click: $root.participantDisqualification"><i class=" fa fa-times-circle"></i></button>
                                        </div>
                                    </td>
                                    <td>
                                        <button class="btn btn-theme btn-xs" data-toggle="popover" title="Judge mistake" data-trigger="hover" data-bind="click: $root.changeFirstSQAttemptStatusJudgeMistake"><i class=" fa fa-exclamation-circle"></i></button>
                                        <button class="btn btn-default btn-xs" data-toggle="popover" title="Cancel" data-trigger="hover" data-bind="click: $root.changeFirstSQAttemptStatusCancel"><i class=" fa fa-undo"></i></button>
                                    </td>
                                    <td data-bind="css: SQSecondCss">
                                        <div class="col-md-12">
                                            <input type="text" class="form-control" data-bind="value: SQSecond, event: {change: $root.changeSecondSQAttemptWeight}"/>
                                        </div>

                                        <div class="col-md-12 btn-group">
                                            <button class="btn btn-success btn-xs" data-toggle="popover" title="Good lift" data-trigger="hover" data-bind="click: $root.changeSecondSQAttemptStatusGoodLift"><i class=" fa fa-thumbs-o-up"></i></button>
                                            <button class="btn btn-danger btn-xs" data-toggle="popover" title="Bad lift" data-trigger="hover" data-bind="click: $root.changeSecondSQAttemptStatusBadLift"><i class=" fa fa-thumbs-o-down"></i></button>
                                            <button class="btn btn-theme02 btn-xs" data-toggle="popover" title="Refuse" data-trigger="hover" data-bind="click: $root.refuseSecondSQAttempt"><i class=" fa fa-flag"></i></button>
                                            <button class="btn btn-info btn-xs" data-toggle="popover" title="Removed by doctor" data-trigger="hover" data-bind="click: $root.changeSecondSQAttemptStatusDoctor"><i class=" fa fa-medkit"></i></button>
                                            <button class="btn btn-theme-disq btn-xs" data-toggle="popover" title="Disqualification" data-trigger="hover" data-bind="click: $root.participantDisqualification"><i class=" fa fa-times-circle"></i></button>
                                        </div>
                                    </td>
                                    <td>
                                        <button class="btn btn-theme btn-xs" data-toggle="popover" title="Judge mistake" data-trigger="hover" data-bind="click: $root.changeSecondSQAttemptStatusJudgeMistake"><i class=" fa fa-exclamation-circle"></i></button>
                                        <button class="btn btn-default btn-xs" data-toggle="popover" title="Cancel" data-trigger="hover" data-bind="click: $root.changeSecondSQAttemptStatusCancel"><i class=" fa fa-undo"></i></button>
                                    </td>
                                    <td data-bind="css: SQThirdCss">
                                        <div class="col-md-12">
                                            <input type="text" class="form-control" data-bind="value: SQThird, event: {change: $root.changeThirdSQAttemptWeight}"/>
                                        </div>

                                        <div class="col-md-12 btn-group">
                                            <button class="btn btn-success btn-xs" data-toggle="popover" title="Good lift" data-trigger="hover" data-bind="click: $root.changeThirdSQAttemptStatusGoodLift"><i class=" fa fa-thumbs-o-up"></i></button>
                                            <button class="btn btn-danger btn-xs" data-toggle="popover" title="Bad lift" data-trigger="hover" data-bind="click: $root.changeThirdSQAttemptStatusBadLift"><i class=" fa fa-thumbs-o-down"></i></button>
                                            <button class="btn btn-theme02 btn-xs" data-toggle="popover" title="Refuse" data-trigger="hover" data-bind="click: $root.refuseThirdSQAttempt"><i class=" fa fa-flag"></i></button>
                                            <button class="btn btn-info btn-xs" data-toggle="popover" title="Removed by doctor" data-trigger="hover" data-bind="click: $root.changeThirdSQAttemptStatusDoctor"><i class=" fa fa-medkit"></i></button>
                                            <button class="btn btn-theme-disq btn-xs" data-toggle="popover" title="Disqualification" data-trigger="hover" data-bind="click: $root.participantDisqualification"><i class=" fa fa-times-circle"></i></button>
                                        </div>
                                    </td>
                                    <td>
                                        <button class="btn btn-theme btn-xs" data-toggle="popover" title="Judge mistake" data-trigger="hover" data-bind="click: $root.changeThirdSQAttemptStatusJudgeMistake"><i class=" fa fa-exclamation-circle"></i></button>
                                        <button class="btn btn-default btn-xs" data-toggle="popover" title="Cancel" data-trigger="hover" data-bind="click: $root.changeThirdSQAttemptStatusCancel"><i class=" fa fa-undo"></i></button>
                                    </td>
                                    <td data-bind="text: currentTotal"></td>-->
                                    <td></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                </div>
            </div>


        </div>
    </section>
</section>

<script type="application/javascript" src="/libs/js/bootstrap.min.js"></script>

<script type="text/javascript" charset="UTF-8" src="/js/moderator/startCompetitionPageSort.js"></script>
<script type="text/javascript" charset="UTF-8" src="/js/commonUser/orderRelease.js"></script>
</body>
</html>