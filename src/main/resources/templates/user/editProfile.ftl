<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head lang="en">
    <meta charset="UTF-8">
    <title>Edit Profile</title>
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
                    <h3><i class="fa fa-angle-right"></i> Edit Profile</h3>
                </div>
            </div>

            <div class="content row mt">
                <div role="tabpanel">
                    <ul class="nav nav-tabs">
                        <li role="presentation" class="active">
                            <a href="#image-tab" role="tab" data-toggle="tab" aria-controls="image-tab">Change Image</a>
                        </li>
                        <li role="presentation">
                            <a href="#password-tab" role="tab" data-toggle="tab"
                               aria-controls="password-tab">Change Password</a>
                        </li>
                        <li role="presentation">
                            <a href="#title-tab" role="tab" data-toggle="tab" aria-controls="title-tab">Change Title</a>
                        </li>
                    </ul>
                </div>
            </div>

            <div class="tab-content row mt">
                <div id="image-tab" class="form-panel image-tab tab-pane active" role="tabpanel">
                    <form class="form-horizontal style-form" id="upload-form" method="POST" action="/upload-avatar"
                          enctype="multipart/form-data">
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Select New Avatar Image</label>

                            <div class="col-sm-10">
                                <input class="btn btn-round" type="file" name="file"/>
                            </div>
                        </div>

                        <input class="btn btn-theme" type="submit" value="Change"/>
                    </form>
                </div>

                <div id="password-tab" class="form-panel password-tab tab-pane" role="tabpanel">
                    <form class="form-horizontal style-form" id="upload-form" method="POST" data-bind="submit: changePassword.bind()">
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Password</label>
                            <div class="col-sm-10">
                                <input id="password" type="password" class="form-control" placeholder="" data-bind="value: password">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Confirm Password</label>
                            <div class="col-sm-10">
                                <input id="confirmPassword" type="password" class="form-control" placeholder=""
                                       data-bind="value: confirmPassword">
                            </div>
                        </div>

                        <input type="submit" value="Change" class="btn btn-theme">
                    </form>
                    <!-- Password Modal -->
                    <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="password-modal" class="modal fade">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    <h4 class="modal-title">Password was successfully changed</h4>
                                </div>
                                <div class="modal-footer">
                                    <button data-dismiss="modal" class="btn btn-default" type="button">Ok</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Password Modal -->
                </div>

                <div id="title-tab" class="form-panel title-tab tab-pane" role="tabpanel">
                    <form class="form-horizontal style-form" id="upload-form" method="POST" action="/?"
                          enctype="multipart/form-data" data-bind="submit: formSending.bind()">
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Title</label>

                            <div class="col-sm-10">
                                <select id="title-select" class="form-control">
                                    <#list titles as title>
                                        <option value="${title.titleId}"
                                                <#if user.title == title.titleId>selected</#if> >
                                            ${title.name}
                                        </option>
                                    </#list>
                                </select>
                            </div>
                        </div>

                        <input type="submit" value="Change" class="btn btn-theme">
                    </form>

                    <!-- Title Modal -->
                    <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="title-modal" class="modal fade">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    <h4 class="modal-title">Title changing</h4>
                                </div>
                                <div class="modal-body">
                                    <p>You can change your title, but if you will participate in competition you need a document which confirm this title.</p>
                                </div>
                                <div class="modal-footer">
                                    <button data-dismiss="modal" class="btn btn-default" type="button" data-bind="click: changeTitle.bind()">Ok</button>
                                    <button data-dismiss="modal" class="btn btn-danger" type="button">Cancel</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Title Modal -->
                </div>
            </div>
        </div>
    </section>
</section>

<script type="application/javascript" src="/libs/js/knockout-3.2.0.js"></script>
<script type="application/javascript" src="/libs/js/knockout.validation.js"></script>
<script type="application/javascript" src="/js/user/editProfile.js" charset="utf-8"></script>

<#--<script src="libs/js/jquery.js"></script>-->
<script src="/libs/js/bootstrap.js"></script>

</body>
</html>