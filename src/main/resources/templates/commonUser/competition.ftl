<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
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

                            <#--TODO finish this to every type of user-->
                            <#if user??>
                                <div class="form-group">
                                    <div id="controls" class="col-md-12">
                                        <#if competition.author != user.userId>
                                            <button class="btn btn-primary btn-round btn-lg" data-bind="click: participate.bind()">Participate</button>
                                        </#if>
                                        <#if competition.author == user.userId>
                                            <#--<button class="btn btn-info btn-round btn-lg">Edit</button>-->
                                            <a data-toggle="modal" href="${competition.id}#delete-modal"><button class="btn btn-info btn-danger btn-lg">Delete</button></a>
                                            <button class="btn btn-info btn-round btn-lg" data-bind="click: addJudges.bind()">Add Judges</button>
                                            <button class="btn btn-info btn-round btn-lg" data-bind="click: addParticipants.bind()">Add Participants</button>
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
                                    <h4><b>Age Group:</b> ${ageGroup}</h4>
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