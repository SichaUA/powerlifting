<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Requests</title>
    <!-- Bootstrap core CSS -->
    <link href="/libs/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="/libs/font-awesome/css/font-awesome.css" rel="stylesheet" />

    <!-- Custom styles for this template -->
    <link href="/libs/css/style.css" rel="stylesheet">
    <link href="/libs/css/style-responsive.css" rel="stylesheet">

</head>
<body>
<#include "*/commonUser/menu.ftl">


    <section id="main-content">
        <section class="wrapper">
            <h3><i class="fa fa-angle-right"></i> Assign user to another status</h3>
            <div class="row mt">
                <div class="col-md-12">
                    <div class="form-panel">
                        <h4><i class="fa fa-angle-right"></i> ASSIGN USER TO MODERATOR STATUS</h4>
                        <hr>
                        <form class="form-horizontal style-form" id="moder-form" data-bind="submit: newModer">

                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Email</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" placeholder="User email" data-bind="value: moderEmail">
                                </div>
                            </div>

                            <input type="submit" value="Assign" class="btn btn-theme">
                        </form>
                    </div>
                </div>
            </div>

            <div class="row mt">
                <div class="col-md-12">
                    <div class="form-panel">
                        <h4><i class="fa fa-angle-right"></i> ASSIGN USER AS ADMINISTRATOR</h4>
                        <hr>
                        <form class="form-horizontal style-form" id="admin-form" data-bind="submit: newAdmin">

                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Email</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" placeholder="User email" data-bind="value: adminEmail">
                                </div>
                            </div>

                            <input type="submit" value="Assign" class="btn btn-theme">
                        </form>
                    </div>
                </div>

            </div>

        </section>
    </section>

    <script type="application/javascript" src="/libs/js/knockout-3.2.0.js"></script>
    <script type="application/javascript" src="/libs/js/knockout.validation.js"></script>
    <script type="application/javascript" src="/js/admin/assignUserToNewStatus.js"></script>
</body>
</html>