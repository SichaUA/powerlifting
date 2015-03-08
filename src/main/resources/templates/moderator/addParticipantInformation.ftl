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
                            <label class="col-sm-2 col-sm-2 control-label">Age Group*</label>
                            <div class="col-sm-10">
                                <select id="category-select" class="form-control" data-bind="value: ageGroup">
                                <#list ageGroups as ageGroup>
                                    <option value="${ageGroup.groupId}">${ageGroup.group} (${ageGroup.description})</option>
                                </#list>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Category*</label>
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
                            <label class="col-sm-2 col-sm-2 control-label">Region*</label>
                            <div class="col-sm-10">
                                <input id="region-input" type="text" class="form-control" placeholder="Region" data-bind="value: region"/>
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
                            <label class="col-sm-2 col-sm-2 control-label">Total</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" data-bind="value: total"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">First Coach Name</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" data-bind="value: firstCoach"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Personal Coach Name</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" data-bind="value: personalCoach"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">First Additional Coach Name</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" data-bind="value: firstAdditionalCoach"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Second Additional Coach Name</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" data-bind="value: secondAdditionalCoach"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-12">
                                <input type="submit" class="btn btn-round btn-primary" value="Add"/>
                            </div>
                        </div>
                    </form>

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