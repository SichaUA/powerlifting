<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${competition.name}</title>
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
            <div class="row mt">
                <div class="col-lg-12">
                    <div class="form-panel">
                        <form class="form-horizontal style-form centered">
                            <div class="form-group">
                                <div class="col-md-12">
                                    <h3>${competition.name}</h3>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-md-12">
                                    <a href="/participantsNomination/${competition.id}"><input type="button" class="btn btn-round btn-info" value="Nomination" onclick=""/></a>
                                </div>
                            </div>

                            <#if competition.broadcasting == 1>
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <a href="/competitionOrderRelease/${competition.id}"><input type="button" class="btn btn-round btn-info" value="Order release" /></a>
                                        <a href="/competitionStandings/${competition.id}"><input type="button" class="btn btn-round btn-info" value="Standings" /></a>
                                        <#--<a href="/competitionInformationBoard/${competition.id}"><input type="button" class="btn btn-round btn-info" value="Information board" /></a>-->
                                    </div>
                                </div>
                            </#if>

                            <#--TODO finish this to every type of user-->
                            <#if user?? && competition.author == user.userId>
                                <div class="form-group">
                                    <div id="controls" class="col-md-12">
                                        <#if competition.author != user.userId>
                                            <#--<button class="btn btn-primary btn-round btn-lg" data-bind="click: participate.bind()">Participate</button>-->
                                        </#if>
                                        <#if competition.author == user.userId>
                                        <#--<button class="btn btn-info btn-round btn-lg">Edit</button>-->
                                            <a data-toggle="modal" href="${competition.id}#delete-modal"><button class="btn btn-info btn-danger btn-lg">Delete</button></a>
                                            <a href="/moder/add-judges/${competition.id}"><button class="btn btn-info btn-round btn-lg" data-bind="click: addJudges.bind()">Add Judges</button></a>
                                            <a href="/moder/add-participants/${competition.id}"><button class="btn btn-info btn-round btn-lg" data-bind="click: addParticipants.bind()">Add Participants</button></a>
                                            <a href="/moder/splitParticipantsIntoSequences/${competition.id}"><button class="btn btn-info btn-round btn-lg" data-bind="click: splitIntoSequences.bind()">Split Into Sequences</button></a>
                                            <a href="/moder/competitionControlPage/${competition.id}"><button class="btn btn-info btn-round btn-lg" data-bind="click: startCompetition.bind()">Competition control</button></a>
                                            <!-- Delete Modal -->
                                            <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="delete-modal" class="modal fade">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header modal-danger-header">
                                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                            <h4 class="modal-title">Are you sure you want delete this competition?</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            <p>If you delete this competition all participants, judges and results will be deleted.</p>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button data-dismiss="modal" class="btn btn-danger" type="button" data-bind="click: deleteCompetition.bind()">Delete</button>
                                                            <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- Delete Modal -->
                                        </#if>
                                    </div>
                                </div>
                            </#if>


                            <div class="form-group">
                                <div class="col-md-6">
                                    <h4><b>City:</b> ${competition.city}</h4>
                                </div>
                                <div class="col-md-6">
                                    <h4><b>Start date:</b> ${competition.startDate}</h4>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-md-6">
                                    <h4>
                                        <b>Gender:</b>
                                    <#if competition.gender == 1>
                                        male
                                    <#elseif competition.gender == 0>
                                        female
                                    <#else>
                                        all
                                    </#if>
                                    </h4>
                                </div>
                                <div class="col-md-6">
                                    <h4><b>End date:</b> ${competition.endDate}</h4>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-md-6">
                                    <h4>
                                        <b>Type:</b>
                                        <#if competition.type == 0>
                                            Eventing
                                        <#elseif competition.type == 1>
                                            Bench press
                                        <#else>
                                            Deadlift
                                        </#if>
                                    </h4>
                                </div>
                                <div class="col-md-6">
                                    <h4>
                                        <b>Age Groups:</b>
                                        <#list ageGroups as ageGroup>
                                            ${ageGroup}
                                        </#list>
                                    </h4>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-md-12">
                                    <h4><b>Additional information:</b></h4>
                                </div>
                                <div class="col-md-12">
                                    <h4>${competition.info}</h4>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
    </section>
</section>
<script type="application/javascript" src="/libs/js/bootstrap.min.js"></script>

<script src="/js/commonUser/competition.js"></script>
</body>
</html>