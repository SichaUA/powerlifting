<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Split sequence into groups</title>
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
                <h3><i class="fa fa-angle-right"></i> Split sequence into groups</h3>
            </div>

            <div class="col-md-12">
                <div class="col-md-2">
                    <h4>Select group count</h4>
                </div>

                <div class="col-md-2">
                    <select class="form-control group-count-select" data-bind="value: groupCount, event{ change: groupCountChange}">
                        <option value="1" <#if groupCount == 1>selected</#if>>1</option>
                        <option value="2" <#if groupCount == 2>selected</#if>>2</option>
                        <option value="3" <#if groupCount == 3>selected</#if>>3</option>
                        <option value="4" <#if groupCount == 4>selected</#if>>4</option>
                    </select>
                </div>
            </div>

            <div id="participant-table" class="col-lg-12">
                <div class="content-panel">
                    <table class="table table-striped table-advance table-hover">
                        <h4><i class="fa fa-angle-right"></i> Participants:</h4>
                        <hr>
                        <thead>
                        <tr>
                            <th><i class="fa fa-sort-numeric-asc"></i></th>
                            <th><i class="fa fa-user"></i> Lifter</th>
                            <th><i class="fa fa-calendar"></i> Birthday</th>
                            <th><i class="fa "></i> Gender</th>
                            <th><i class="fa fa-group"></i> Age Group</th>
                            <th><i class="fa fa-group"></i> Weight Category</th>
                            <th>SQ</th>
                            <th>BP</th>
                            <th>DL</th>
                            <th><i class=" fa fa-edit"></i> Total</th>
                            <th><i class=" fa"></i> Group</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                            <#assign x = 1>
                            <#list participants as participant>
                                <tr>
                                    <td>${x}</td>
                                    <td>${participant.user.secondName} ${participant.user.firstName} ${participant.user.middleName}</td>
                                    <td>${participant.user.birthday}</td>
                                    <td>
                                        <#if participant.user.gender == 0>
                                            Female
                                        <#else>
                                            Male
                                        </#if>
                                    </td>
                                    <td>${participant.ageGroup.group} (${participant.ageGroup.description})</td>
                                    <td>
                                        ${participant.weightCategory.name}
                                    </td>
                                    <td>
                                        <#if participant.squat != 0>
                                        ${participant.squat}
                                        <#else>
                                            <i class="fa fa-minus"></i>
                                        </#if>
                                    </td>
                                    <td>
                                        <#if participant.benchPress != 0>
                                        ${participant.benchPress}
                                        <#else>
                                            <i class="fa fa-minus"></i>
                                        </#if>
                                    </td>
                                    <td>
                                        <#if participant.deadLift != 0>
                                        ${participant.deadLift}
                                        <#else>
                                            <i class="fa fa-minus"></i>
                                        </#if>
                                    </td>
                                    <td>
                                        <#if participant.total != 0>
                                        ${participant.total}
                                        <#else>
                                            <i class="fa fa-minus"></i>
                                        </#if>
                                    </td>
                                    <td>
                                        <select size="5" class="form-control group-count-select" data-bind="options: availableGroups, value: selectedGroup">
                                            <option class="judge-option-0" value="0" <#if participant.selectedGroup??>selected</#if>>No group</option>
                                            <option class="judge-option-1" value="1" <#if participant.selectedGroup == 1>selected</#if>>1</option>
                                            <option class="judge-option-2 <#if groupCount lt 2>hidden</#if>" value="2" <#if participant.selectedGroup == 2>selected</#if> <#if groupCount lt 0>disabled</#if>>2</option>
                                            <option class="judge-option-3 <#if groupCount lt 3>hidden</#if>" value="3" <#if participant.selectedGroup == 3>selected</#if> <#if groupCount lt 0>disabled</#if>>3</option>
                                            <option class="judge-option-4 <#if groupCount lt 4>hidden</#if>" value="4" <#if participant.selectedGroup == 4>selected</#if> <#if groupCount lt 0>disabled</#if>>4</option>
                                        </select>
                                        <input type="hidden" class="participant-id" value="${participant.participantId}"/>
                                    </td>
                                    <td></td>
                                </tr>
                                <#assign x = x+1>
                            </#list>
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

<script type="text/javascript" src="/libs/js/gritter/js/jquery.gritter.js"></script>
<script type="text/javascript" src="/libs/js/gritter-conf.js"></script>

<script type="text/javascript" charset="UTF-8" src="/js/moderator/splitSequenceIntoGroups.js"></script>
</body>
</html>