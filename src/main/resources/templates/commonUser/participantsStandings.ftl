<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head lang="en">
    <meta charset="UTF-8">
    <title>Standing of participants</title>
    <!-- Bootstrap core CSS -->
    <link href="/libs/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="/libs/font-awesome/css/font-awesome.css" rel="stylesheet" />

    <!-- Custom styles for this template -->
    <link href="/libs/css/style.css" rel="stylesheet">
    <link href="/libs/css/style-responsive.css" rel="stylesheet">
</head>
<body>
<#include "*/menu.ftl">

<section id="main-content">
    <section class="wrapper">
        <h3><i class="fa fa-angle-right"></i> Standings</h3>

        <div class="row mt">
            <div class="col-md-12">
                <div class="content-panel">
                    <table class="table table-striped table-advance table-hover">
                        <h4><i class="fa fa-angle-right"></i> Female</h4>
                        <hr>
                        <thead>
                            <tr>
                                <th><i class="fa fa-sort-numeric-asc"></i></th>
                                <th><i class="fa fa-female"></i> Lifter</th>
                                <th><i class="fa fa-calendar"></i> Birthday</th>
                                <th><i class="fa fa-home"></i> From</th>
                                <th>SQ</th>
                                <th>BP</th>
                                <th>DL</th>
                                <th><i class=" fa fa-edit"></i> Total</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <#list categories as category>
                                <#if category.gender == 0>
                                    <tr>
                                        <td colspan="9" style="color: #0044cc"><h4>${category.name}</h4></td>
                                    </tr>

                                    <#assign x = 1>
                                    <#list participants as participant>
                                        <#if participant.gender == 0 && participant.category == category.categoryId>
                                            <tr>
                                                <td>${x}</td>
                                                <td>${participant.secondName} ${participant.firstName} ${participant.middleName}</td>
                                                <td>${participant.birthday}</td>
                                                <td>${participant.fromName}</td>
                                                <td>${participant.squat}</td>
                                                <td>${participant.benchPress}</td>
                                                <td>${participant.deadLift}</td>
                                                <td>${participant.total}</td>
                                                <td></td>
                                            </tr>
                                            <#assign x = x+1>
                                        </#if>
                                    </#list>
                                </#if>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="row mt">
            <div class="col-md-12">
                <div class="content-panel">
                    <table class="table table-striped table-advance table-hover">
                        <h4><i class="fa fa-angle-right"></i> Male</h4>
                        <hr>
                        <thead>
                        <tr>
                            <th><i class="fa fa-sort-numeric-asc"></i></th>
                            <th><i class="fa fa-male"></i> Lifter</th>
                            <th><i class="fa fa-calendar"></i> Birthday</th>
                            <th><i class="fa fa-home"></i> From</th>
                            <th>SQ</th>
                            <th>BP</th>
                            <th>DL</th>
                            <th><i class=" fa fa-edit"></i> Total</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list categories as category>
                            <#if category.gender == 1>
                            <tr>
                                <td colspan="9" style="color: #0044cc"><h4>${category.name}</h4></td>
                            </tr>

                                <#assign x = 1>
                                <#list participants as participant>
                                    <#if participant.gender == 1 && participant.category == category.categoryId>
                                    <tr>
                                        <td>${x}</td>
                                        <td>${participant.secondName} ${participant.firstName} ${participant.middleName}</td>
                                        <td>${participant.birthday}</td>
                                        <td>${participant.fromName}</td>
                                        <td>${participant.squat}</td>
                                        <td>${participant.benchPress}</td>
                                        <td>${participant.deadLift}</td>
                                        <td>${participant.total}</td>
                                        <td></td>
                                    </tr>
                                        <#assign x = x+1>
                                    </#if>
                                </#list>
                            </#if>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </section>
</section>

<script src="/libs/js/bootstrap.js"></script>


</body>
</html>