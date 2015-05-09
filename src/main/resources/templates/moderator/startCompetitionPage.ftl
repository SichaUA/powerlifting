<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Start Competition</title>
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
                <div class="col-md-4">
                    <h3><i class="fa fa-angle-right"></i> Start competition</h3>
                </div>
                <input type="hidden" class="competition-id" value="${competition.id}"/>
                <div class="col-md-3 col-md-offset-4">
                    <input type="button" class="btn btn-round <#if competition.broadcasting == 0>btn-danger<#else>btn-success</#if> btn-broadcasting" value="Competition broadcasting" onclick="broadcastingBtnClick()"/>
                    <button class="btn btn-round btn-danger btn-broadcasting-info" <#if competition.broadcasting == 0>disabled</#if> onclick="updateBroadcastingInfo()"><i class="fa fa-refresh"></i></button>
                </div>
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

                <div class="col-md-12">
                    <div class="col-md-2">
                        <h4>Select group</h4>
                    </div>

                    <div id="group-select" class="col-md-4">
                        <select class="form-control group-select" data-bind="foreach: groups">
                            <option data-bind="value: groupId, text: groupNum"></option>
                        </select>
                    </div>
                </div>
            </div>

            <div id="participants-all-info" class="row col-md-12">
                <div class="col-md-12">
                    <ul class="nav nav-tabs">
                        <li role="presentation" class="SQ-tab active"><a href="#SQ" role="tab" data-toggle="tab" aria-controls="SQ">SQ</a></li>
                        <li role="presentation" class="BP-tab"><a href="#BP" role="tab" data-toggle="tab" aria-controls="BP">BP</a></li>
                        <li role="presentation" class="DL-tab"><a href="#DL" role="tab" data-toggle="tab" aria-controls="DL">DL</a></li>
                    </ul>
                </div>

                <div class="participants-competition tab-content">
                    <div id="SQ" class="SQ tab-pane active" role="tabpanel">
                        <div id="sequence-table" class="col-lg-12">
                            <div class="content-panel">
                                <table id="participant-SQ-table" class="table table-striped table-advance table-hover">
                                    <h4><i class="fa fa-angle-right"></i> Participants:</h4>
                                    <hr>
                                    <thead>
                                    <tr>
                                        <th><i class="fa fa-sort-numeric-asc"></i></th>
                                        <th><i class="fa fa-user"></i> Lifter</th>
                                        <th><i class="fa fa-calendar"></i> Birthday</th>
                                        <th><i class="fa "></i> Gender</th>
                                        <#--<th><i class="fa fa-group"></i> Age Group</th>-->
                                        <th>Weight</th>
                                        <th>SQ 1st Approach</th>
                                        <th></th>
                                        <th>SQ 2nd Approach</th>
                                        <th></th>
                                        <th>SQ 3rd Approach</th>
                                        <th></th>
                                        <th>Total</th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody data-bind="foreach: participantsSQ<#--.sort(function (l, r) { return (l.SQFirstDeclared >= r.SQFirstDeclared)? 1 : -1 })-->">
                                    <tr>
                                        <td data-bind="text: ordinalNumber"></td>
                                        <td data-bind="text: name"></td>
                                        <td data-bind="text: birthday"></td>
                                        <td data-bind="text: gender"></td>
                                        <#--<td data-bind="text: ageGroup"></td>-->
                                        <td data-bind="text: weight"></td>
                                        <td data-bind="css: SQFirstCss">
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
                                        <td data-bind="text: currentTotal"></td>
                                        <td></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <div id="BP" class="BP tab-pane" role="tabpanel">
                        <div id="sequence-table" class="col-lg-12">
                            <div class="content-panel">
                                <table id="participant-BP-table" class="table table-striped table-advance table-hover">
                                    <h4><i class="fa fa-angle-right"></i> Participants:</h4>
                                    <hr>
                                    <thead>
                                    <tr>
                                        <th><i class="fa fa-sort-numeric-asc"></i></th>
                                        <th><i class="fa fa-user"></i> Lifter</th>
                                        <th><i class="fa fa-calendar"></i> Birthday</th>
                                        <th><i class="fa "></i> Gender</th>
                                        <#--<th><i class="fa fa-group"></i> Age Group</th>-->
                                        <th>Weight</th>
                                        <th>BP 1st Approach</th>
                                        <th></th>
                                        <th>BP 2nd Approach</th>
                                        <th></th>
                                        <th>BP 3rd Approach</th>
                                        <th></th>
                                        <th>Total</th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody data-bind="foreach: participantsBP">
                                    <tr>
                                        <td data-bind="text: ordinalNumber"></td>
                                        <td data-bind="text: name"></td>
                                        <td data-bind="text: birthday"></td>
                                        <td data-bind="text: gender"></td>
                                        <#--<td data-bind="text: ageGroup"></td>-->
                                        <td data-bind="text: weight"></td>
                                        <td data-bind="css: BPFirstCss">
                                            <div class="col-md-12">
                                                <input type="text" class="form-control" data-bind="value: BPFirst, event: {change: $root.changeFirstBPAttemptWeight}"/>
                                            </div>

                                            <div class="col-md-12 btn-group">
                                                <button class="btn btn-success btn-xs" data-toggle="popover" title="Good lift" data-trigger="hover" data-bind="click: $root.changeFirstBPAttemptStatusGoodLift"><i class=" fa fa-thumbs-o-up"></i></button>
                                                <button class="btn btn-danger btn-xs" data-toggle="popover" title="Bad lift" data-trigger="hover" data-bind="click: $root.changeFirstBPAttemptStatusBadLift"><i class=" fa fa-thumbs-o-down"></i></button>
                                                <button class="btn btn-theme02 btn-xs" data-toggle="popover" title="Refuse" data-trigger="hover" data-bind="click: $root.refuseFirstBPAttempt"><i class=" fa fa-flag"></i></button>
                                                <button class="btn btn-info btn-xs" data-toggle="popover" title="Removed by doctor" data-trigger="hover" data-bind="click: $root.changeFirstBPAttemptStatusDoctor"><i class=" fa fa-medkit"></i></button>
                                                <button class="btn btn-theme-disq btn-xs" data-toggle="popover" title="Disqualification" data-trigger="hover" data-bind="click: $root.participantDisqualification"><i class=" fa fa-times-circle"></i></button>
                                            </div>
                                        </td>
                                        <td>
                                            <button class="btn btn-theme btn-xs" data-toggle="popover" title="Judge mistake" data-trigger="hover" data-bind="click: $root.changeFirstBPAttemptStatusJudgeMistake"><i class=" fa fa-exclamation-circle"></i></button>
                                            <button class="btn btn-default btn-xs" data-toggle="popover" title="Cancel" data-trigger="hover" data-bind="click: $root.changeFirstBPAttemptStatusCancel"><i class=" fa fa-undo"></i></button>
                                        </td>
                                        <td data-bind="css: BPSecondCss">
                                            <div class="col-md-12">
                                                <input type="text" class="form-control" data-bind="value: BPSecond, event: {change: $root.changeSecondBPAttemptWeight}"/>
                                            </div>

                                            <div class="col-md-12 btn-group">
                                                <button class="btn btn-success btn-xs" data-toggle="popover" title="Good lift" data-trigger="hover" data-bind="click: $root.changeSecondBPAttemptStatusGoodLift"><i class=" fa fa-thumbs-o-up"></i></button>
                                                <button class="btn btn-danger btn-xs" data-toggle="popover" title="Bad lift" data-trigger="hover" data-bind="click: $root.changeSecondBPAttemptStatusBadLift"><i class=" fa fa-thumbs-o-down"></i></button>
                                                <button class="btn btn-theme02 btn-xs" data-toggle="popover" title="Refuse" data-trigger="hover" data-bind="click: $root.refuseSecomdBPAttempt"><i class=" fa fa-flag"></i></button>
                                                <button class="btn btn-info btn-xs" data-toggle="popover" title="Removed by doctor" data-trigger="hover"><i class=" fa fa-medkit"></i></button>
                                                <button class="btn btn-theme-disq btn-xs" data-toggle="popover" title="Disqualification" data-trigger="hover" data-bind="click: $root.participantDisqualification"><i class=" fa fa-times-circle"></i></button>
                                            </div>
                                        </td>
                                        <td>
                                            <button class="btn btn-theme btn-xs" data-toggle="popover" title="Judge mistake" data-trigger="hover" data-bind="click: $root.changeSecondBPAttemptStatusJudgeMistake"><i class=" fa fa-exclamation-circle"></i></button>
                                            <button class="btn btn-default btn-xs" data-toggle="popover" title="Cancel" data-trigger="hover" data-bind="click: $root.changeSecondBPAttemptStatusCancel"><i class=" fa fa-undo"></i></button>
                                        </td>
                                        <td data-bind="css: BPThirdCss">
                                            <div class="col-md-12">
                                                <input type="text" class="form-control" data-bind="value: BPThird, event: {change: $root.changeThirdBPAttemptWeight}"/>
                                            </div>

                                            <div class="col-md-12 btn-group">
                                                <button class="btn btn-success btn-xs" data-toggle="popover" title="Good lift" data-trigger="hover" data-bind="click: $root.changeThirdBPAttemptStatusGoodLift"><i class=" fa fa-thumbs-o-up"></i></button>
                                                <button class="btn btn-danger btn-xs" data-toggle="popover" title="Bad lift" data-trigger="hover" data-bind="click: $root.changeSecondBPAttemptStatusBadLift"><i class=" fa fa-thumbs-o-down"></i></button>
                                                <button class="btn btn-theme02 btn-xs" data-toggle="popover" title="Refuse" data-trigger="hover" data-bind="click: $root.refuseSecondBPAttempt"><i class=" fa fa-flag"></i></button>
                                                <button class="btn btn-info btn-xs" data-toggle="popover" title="Removed by doctor" data-trigger="hover"><i class=" fa fa-medkit"></i></button>
                                                <button class="btn btn-theme-disq btn-xs" data-toggle="popover" title="Disqualification" data-trigger="hover" data-bind="click: $root.participantDisqualification"><i class=" fa fa-times-circle"></i></button>
                                            </div>
                                        </td>
                                        <td>
                                            <button class="btn btn-theme btn-xs" data-toggle="popover" title="Judge mistake" data-trigger="hover" data-bind="click: $root.changeThirdBPAttemptStatusJudgeMistake"><i class=" fa fa-exclamation-circle"></i></button>
                                            <button class="btn btn-default btn-xs" data-toggle="popover" title="Cancel" data-trigger="hover" data-bind="click: $root.changeThirdBPAttemptStatusCancel"><i class=" fa fa-undo"></i></button>
                                        </td>
                                        <td data-bind="text: currentTotal"></td>
                                        <td></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <div id="DL" class="DL tab-pane" role="tabpanel">
                        <div id="sequence-table" class="col-lg-12">
                            <div class="content-panel">
                                <table id="participant-DL-table" class="table table-striped table-advance table-hover">
                                    <h4><i class="fa fa-angle-right"></i> Participants:</h4>
                                    <hr>
                                    <thead>
                                    <tr>
                                        <th><i class="fa fa-sort-numeric-asc"></i></th>
                                        <th><i class="fa fa-user"></i> Lifter</th>
                                        <th><i class="fa fa-calendar"></i> Birthday</th>
                                        <th><i class="fa "></i> Gender</th>
                                        <#--<th><i class="fa fa-group"></i> Age Group</th>-->
                                        <th>Weight</th>
                                        <th>DL 1st Approach</th>
                                        <th></th>
                                        <th>DL 2nd Approach</th>
                                        <th></th>
                                        <th>DL 3rd Approach</th>
                                        <th></th>
                                        <th>Total</th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody data-bind="foreach: participantsDL">
                                    <tr>
                                        <td data-bind="text: ordinalNumber"></td>
                                        <td data-bind="text: name"></td>
                                        <td data-bind="text: birthday"></td>
                                        <td data-bind="text: gender"></td>
                                        <#--<td data-bind="text: ageGroup"></td>-->
                                        <td data-bind="text: weight"></td>
                                        <td data-bind="css: DLFirstCss">
                                            <div class="col-md-12">
                                                <input type="text" class="form-control" data-bind="value: DLFirst, event: {change: $root.changeFirstDLAttemptWeight}"/>
                                            </div>

                                            <div class="col-md-12 btn-group">
                                                <button class="btn btn-success btn-xs" data-toggle="popover" title="Good lift" data-trigger="hover"><i class=" fa fa-thumbs-o-up"></i></button>
                                                <button class="btn btn-danger btn-xs" data-toggle="popover" title="Bad lift" data-trigger="hover"><i class=" fa fa-thumbs-o-down"></i></button>
                                                <button class="btn btn-theme02 btn-xs" data-toggle="popover" title="Refuse" data-trigger="hover" <#--data-bind="click: $root.changeFirstSQAttemptStatusDoctor"-->><i class=" fa fa-flag"></i></button>
                                                <button class="btn btn-info btn-xs" data-toggle="popover" title="Removed by doctor" data-trigger="hover"><i class=" fa fa-medkit"></i></button>
                                                <button class="btn btn-theme-disq btn-xs" data-toggle="popover" title="Disqualification" data-trigger="hover" <#--data-bind="click: $root.changeFirstSQAttemptStatusDoctor"-->><i class=" fa fa-times-circle"></i></button>
                                            </div>
                                        </td>
                                        <td>
                                            <button class="btn btn-theme btn-xs" data-toggle="popover" title="Judge mistake" data-trigger="hover" <#--data-bind="click: $root.changeFirstSQAttemptStatusDoctor"-->><i class=" fa fa-exclamation-circle"></i></button>
                                            <button class="btn btn-default btn-xs" data-toggle="popover" title="Cancel" data-trigger="hover" <#--data-bind="click: $root.changeFirstSQAttemptStatusDoctor"-->><i class=" fa fa-undo"></i></button>
                                        </td>
                                        <td data-bind="css: DLSecondCss">
                                            <div class="col-md-12">
                                                <input type="text" class="form-control" data-bind="value: DLSecond, event: {change: $root.changeSecondDLAttemptWeight}"/>
                                            </div>

                                            <div class="col-md-12 btn-group">
                                                <button class="btn btn-success btn-xs" data-toggle="popover" title="Good lift" data-trigger="hover"><i class=" fa fa-thumbs-o-up"></i></button>
                                                <button class="btn btn-danger btn-xs" data-toggle="popover" title="Bad lift" data-trigger="hover"><i class=" fa fa-thumbs-o-down"></i></button>
                                                <button class="btn btn-theme02 btn-xs" data-toggle="popover" title="Refuse" data-trigger="hover" <#--data-bind="click: $root.changeFirstSQAttemptStatusDoctor"-->><i class=" fa fa-flag"></i></button>
                                                <button class="btn btn-info btn-xs" data-toggle="popover" title="Removed by doctor" data-trigger="hover"><i class=" fa fa-medkit"></i></button>
                                                <button class="btn btn-theme-disq btn-xs" data-toggle="popover" title="Disqualification" data-trigger="hover" <#--data-bind="click: $root.changeFirstSQAttemptStatusDoctor"-->><i class=" fa fa-times-circle"></i></button>
                                            </div>
                                        </td>
                                        <td>
                                            <button class="btn btn-theme btn-xs" data-toggle="popover" title="Judge mistake" data-trigger="hover" <#--data-bind="click: $root.changeFirstSQAttemptStatusDoctor"-->><i class=" fa fa-exclamation-circle"></i></button>
                                            <button class="btn btn-default btn-xs" data-toggle="popover" title="Cancel" data-trigger="hover" <#--data-bind="click: $root.changeFirstSQAttemptStatusDoctor"-->><i class=" fa fa-undo"></i></button>
                                        </td>
                                        <td data-bind="css: DLThirdCss">
                                            <div class="col-md-12">
                                                <input type="text" class="form-control" data-bind="value: DLThird, event: {change: $root.changeThirdDLAttemptWeight}"/>
                                            </div>

                                            <div class="col-md-12 btn-group">
                                                <button class="btn btn-success btn-xs" data-toggle="popover" title="Good lift" data-trigger="hover"><i class=" fa fa-thumbs-o-up"></i></button>
                                                <button class="btn btn-danger btn-xs" data-toggle="popover" title="Bad lift" data-trigger="hover"><i class=" fa fa-thumbs-o-down"></i></button>
                                                <button class="btn btn-theme02 btn-xs" data-toggle="popover" title="Refuse" data-trigger="hover" <#--data-bind="click: $root.changeFirstSQAttemptStatusDoctor"-->><i class=" fa fa-flag"></i></button>
                                                <button class="btn btn-info btn-xs" data-toggle="popover" title="Removed by doctor" data-trigger="hover"><i class=" fa fa-medkit"></i></button>
                                                <button class="btn btn-theme-disq btn-xs" data-toggle="popover" title="Disqualification" data-trigger="hover" <#--data-bind="click: $root.changeFirstSQAttemptStatusDoctor"-->><i class=" fa fa-times-circle"></i></button>
                                            </div>
                                        </td>
                                        <td>
                                            <button class="btn btn-theme btn-xs" data-toggle="popover" title="Judge mistake" data-trigger="hover" <#--data-bind="click: $root.changeFirstSQAttemptStatusDoctor"-->><i class=" fa fa-exclamation-circle"></i></button>
                                            <button class="btn btn-default btn-xs" data-toggle="popover" title="Cancel" data-trigger="hover" <#--data-bind="click: $root.changeFirstSQAttemptStatusDoctor"-->><i class=" fa fa-undo"></i></button>
                                        </td>
                                        <td data-bind="text: currentTotal"></td>
                                        <td></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


        </div>
    </section>
</section>
<#--<#include "*/modal/modal.ftl">-->

<script type="application/javascript" src="/libs/js/bootstrap.min.js"></script>

<#--<script type="application/javascript" src="/libs/js/knockout-3.2.0.js"></script>-->
<#--<script type="application/javascript" src="/libs/js/knockout.validation.js"></script>-->

<#--<script type="text/javascript" src="/libs/js/gritter/js/jquery.gritter.js"></script>-->
<#--<script type="text/javascript" src="/libs/js/gritter-conf.js"></script>-->

<script type="text/javascript" charset="UTF-8" src="/js/moderator/startCompetitionPageSort.js"></script>
<script type="text/javascript" charset="UTF-8" src="/js/moderator/startCompetitionPage.js"></script>
</body>
</html>