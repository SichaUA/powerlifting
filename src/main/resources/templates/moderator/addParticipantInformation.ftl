<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Adding participant information</title>
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
        <h3><i class="fa fa-angle-right"></i> Add Participant Information</h3>
        <div class="row mt">
            <div class="col-lg-12">
                <div class="form-panel">

                    <form id="add-participant-form" class="form-horizontal style-form" data-bind="submit: newParticipant">
                        <div class="form-group">
                            <div class="col-md-12">
                                <h3>Add <b>${participant.secondName} ${participant.firstName} ${participant.middleName}</b> to <b>${competition.name}</b></h3>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Category</label>
                            <div class="col-sm-10">
                                <select id="category-select" class="form-control" data-bind="value: category">
                                <#list categories as category>
                                    <#if category.gender == participant.gender>
                                        <option value="${category.categoryId}">${category.name}</option>
                                    </#if>
                                </#list>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Region</label>
                            <div class="col-sm-8">
                                <input id="region-input" type="text" class="form-control" placeholder="Region" data-bind="value: region"/>
                            </div>
                            <div class="col-sm-2 centered">
                                <input type="button" class="btn btn-round btn-toolbar" value="New Region" onclick="$('#region-modal').modal('show')"/>
                            </div>

                            <div class="col-md-12">
                                <div class="checkbox">
                                    <label>
                                        <input id="own-participation" type="checkbox" data-bind="checked: ownParticipation"/> Own Participation
                                    </label>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">SQ</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" data-bind="value: SQ"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">BP</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" data-bind="value: BP"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">DL</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" data-bind="value: DL"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-12">
                                <input type="submit" class="btn btn-round btn-primary" value="Add"/>
                            </div>
                        </div>
                    </form>

                    <!-- New Region Modal -->
                    <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="region-modal" class="modal fade">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    <h4 id="modal-title" class="modal-title">Enter New Region</h4>
                                </div>
                                <div class="modal-body">
                                    <form id="new-region-form" class="form-horizontal style-form">
                                        <input id="new-region-input" type="text" class="form-control"/>
                                        <div class="modal-footer">
                                            <input data-dismiss="modal" class="btn btn-default" type="submit" value="Ok" onclick="$('#new-region-form').submit()"/>
                                            <input data-dismiss="modal" class="btn btn-danger" type="button" value="Cancel">
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- New Region Modal -->

                </div>
            </div>
        </div>
    </section>
</section>
<script type="application/javascript" src="/libs/js/knockout-3.2.0.js"></script>
<script type="application/javascript" src="/libs/js/knockout.validation.js"></script>
<script type="application/javascript" src="/libs/js/bootstrap.min.js"></script>
<script type="application/javascript" src="/libs/js/jquery.autocomplete.js"></script>

<script src="/js/moderator/addParticipantInformation.js"></script>
</body>
</html>