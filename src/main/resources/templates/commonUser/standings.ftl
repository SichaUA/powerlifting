<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Standings ${competition.name}</title>
    <!-- Bootstrap core CSS -->
    <link href="/libs/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="/libs/font-awesome/css/font-awesome.css" rel="stylesheet" />

    <!-- Custom styles for this template -->
    <link href="/libs/css/style.css" rel="stylesheet">
    <link href="/libs/css/style-responsive.css" rel="stylesheet">

    <link rel="stylesheet" type="text/css" href="/libs/lineicons/style.css">

    <link href="/css/commonUser/standings.css" rel="stylesheet">

<#--<link rel="stylesheet" type="text/css" href="/libs/js/gritter/css/jquery.gritter.css" />-->

</head>
<body>
<#include "*/commonUser/menu.ftl">

<section id="main-content">
    <section class="wrapper">
        <div class="row mt">
            <div class="row col-md-12">
                <div class="col-md-4">
                    <#--<h3><i class="fa fa-angle-right"></i> Standings</h3>-->
                </div>
                <input type="hidden" class="competition-id" value="${competition.id}"/>
            </div>

            <div id="participants-all-info" class="row col-md-12">

                <div class="participants-competition tab-content">
                    <div id="SQ" class="SQ">
                        <div id="sequence-table" class="col-lg-12">
                            <div class="content-panel">
                                <table id="participant-SQ-table" class="table table-striped table-advance table-hover">
                                    <h4><i class="fa fa-angle-right"></i> Standings:</h4>
                                    <hr>
                                    <thead>
                                    <tr>
                                        <th><i class="fa fa-sort-numeric-asc"></i></th>
                                        <th><i class="fa fa-user"></i> Lifter</th>
                                        <th><i class="fa fa-calendar"></i> Birthday</th>
                                        <th><i class="fa "></i> Gender</th>
                                    <#--<th><i class="fa fa-group"></i> Age Group</th>-->
                                        <th>Weight</th>
                                        <th>SQ 1st</th>
                                        <th>SQ 2nd</th>
                                        <th>SQ 3rd</th>
                                        <th>BP 1st</th>
                                        <th>BP 2nd</th>
                                        <th>BP 3rd</th>
                                        <th>DL 1st</th>
                                        <th>DL 2nd</th>
                                        <th>DL 3rd</th>
                                        <th>Total</th>
                                        <#--<th></th>-->
                                    </tr>
                                    </thead>
                                    <tbody data-bind="foreach: participants">
                                    <tr>
                                        <td data-bind="text: ordinalNumber"></td>
                                        <td data-bind="text: name"></td>
                                        <td data-bind="text: birthday"></td>
                                        <td data-bind="text: gender"></td>
                                    <#--<td data-bind="text: ageGroup"></td>-->
                                        <td data-bind="text: weight"></td>
                                        <td data-bind="css: SQFirstCss, text: SQFirst"></td>
                                        <td data-bind="css: SQSecondCss, text: SQSecond"></td>
                                        <td data-bind="css: SQThirdCss, text: SQThird"></td>
                                        <td data-bind="css: BPFirstCss, text: BPFirst"></td>
                                        <td data-bind="css: BPSecondCss, text: BPSecond"></td>
                                        <td data-bind="css: BPThirdCss, text: BPThird"></td>
                                        <td data-bind="css: DLFirstCss, text: DLFirst"></td>
                                        <td data-bind="css: DLSecondCss, text: DLSecond"></td>
                                        <td data-bind="css: DLThirdCss, text: DLThird"></td>
                                        <td data-bind="text: currentTotal"></td>
                                        <#--<td></td>-->
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row mt col-md-12">
            <div class="col-md-3 col-md-offset-9">
                <div class="content-panel">
                    <table class="table table-striped table-advance table-hover">
                        <h4><i class="fa fa-angle-right"></i> Colors:</h4>
                        <hr>
                        <thead>
                        <tr>
                            <th>Color</th>
                            <th>Meaning</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="declared-weight"></td>
                            <td>Declared weight</td>
                        </tr>
                        <tr>
                            <td class="good-lift-weight"></td>
                            <td>Good lift</td>
                        </tr>
                        <tr>
                            <td class="bad-lift-weight"></td>
                            <td>Bad lift</td>
                        </tr>
                        <tr>
                            <td class="refuse-weight"></td>
                            <td>Refuse</td>
                        </tr>
                        <tr>
                            <td class="judge-mistake"></td>
                            <td>Judge mistake</td>
                        </tr>
                        <tr>
                            <td class="doctor-weight"></td>
                            <td>Removed by doctor</td>
                        </tr>
                        <tr>
                            <td class="disq-participant"></td>
                            <td>Disqualified</td>
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

<#--<script type="application/javascript" src="/libs/js/knockout-3.2.0.js"></script>-->
<#--<script type="application/javascript" src="/libs/js/knockout.validation.js"></script>-->

<#--<script type="text/javascript" src="/libs/js/gritter/js/jquery.gritter.js"></script>-->
<#--<script type="text/javascript" src="/libs/js/gritter-conf.js"></script>-->

<#--<script type="text/javascript" charset="UTF-8" src="/js/moderator/startCompetitionPageSort.js"></script>-->
<script type="text/javascript" charset="UTF-8" src="/js/commonUser/standings.js"></script>
</body>
</html>