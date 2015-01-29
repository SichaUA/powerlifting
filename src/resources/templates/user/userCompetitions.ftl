<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Competitions</title>
    <!-- Bootstrap core CSS -->
    <link href="/libs/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="/libs/font-awesome/css/font-awesome.css" rel="stylesheet"/>

    <!-- Custom styles for this template -->
    <link href="/libs/css/style.css" rel="stylesheet">
    <link href="/libs/css/style-responsive.css" rel="stylesheet">

</head>
<body>
<#include "*/commonUser/menu.ftl">


<section id="main-content">
    <section class="wrapper">
        <div class="col-md-12">
            <div class="row mt">
                <div class="col-md-10">
                    <h3><i class="fa fa-angle-right"></i> Competitions</h3>
                </div>
                <#if user.role == 2>
                    <div class="col-md-2">
                        <button id="new-competition" type="button" class="btn btn-round btn-info pull-right">Create new</button>
                        <script>
                            $('#new-competition').click(function () {
                                window.location.href = '/moder/createCompetition';
                            })
                        </script>
                    </div>
                </#if>
            </div>

            <div class="content row mt">
                <div role="tabpanel">
                    <ul class="nav nav-tabs">
                        <li role="presentation" class="active">
                            <a href="#participated-competitions" role="tab" data-toggle="tab" aria-controls="participated-competitions">Participated</a>
                        </li>
                        <li role="presentation">
                            <a href="#impending-competitions" role="tab" data-toggle="tab"
                                aria-controls="impending-competitions">Impending</a>
                        </li>
                        <#if user.role == 2>
                            <li role="presentation">
                                <a href="#created-competitions" role="tab" data-toggle="tab" aria-controls="created-competitions">Created</a>
                            </li>
                        </#if>
                    </ul>
                </div>
            </div>

            <div class="tab-content row mt">
                <div id="participated-competitions" class="participated-competitions tab-pane active" role="tabpanel">
                    <#list pCompetitions as competition>
                        <#include "*/commonUser/competitionList.ftl">
                    </#list>
                </div>

                <div id="impending-competitions" class="impending-competitions tab-pane" role="tabpanel">
                    <#list iCompetitions as competition>
                        <#include "*/commonUser/competitionList.ftl">
                    </#list>
                </div>

                <#if user.role == 2>
                    <div id="created-competitions" class="created-competitions tab-pane" role="tabpanel">
                        <#list createdCompetitions as competition>
                            <#include "*/commonUser/competitionList.ftl">
                        </#list>
                    </div>
                </#if>
            </div>
        </div>

    </section>
</section>
<script src="/libs/js/bootstrap.js"></script>
</body>
</html>