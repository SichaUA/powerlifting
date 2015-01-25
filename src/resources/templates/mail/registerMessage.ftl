<#--<#setting locale="uk_UA">-->
<#--<!DOCTYPE html>-->
<#--<html>-->
<#--<head>-->
    <#--<meta charset="UTF-8">-->
    <#--<title>Home</title>-->
    <#--<!-- Bootstrap core CSS &ndash;&gt;-->
    <#--<link href="/libs/css/bootstrap.css" rel="stylesheet">-->
    <#--<!--external css&ndash;&gt;-->
    <#--<link href="/libs/font-awesome/css/font-awesome.css" rel="stylesheet" />-->

    <#--<!-- Custom styles for this template &ndash;&gt;-->
    <#--<link href="/libs/css/style.css" rel="stylesheet">-->
    <#--<link href="/libs/css/style-responsive.css" rel="stylesheet">-->

<#--</head>-->
<#--<body>-->
    <link href="/libs/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="/libs/font-awesome/css/font-awesome.css" rel="stylesheet" />

    <!-- Custom styles for this template -->
    <link href="/libs/css/style.css" rel="stylesheet">
    <link href="/libs/css/style-responsive.css" rel="stylesheet">

    <section id="main-content">
        <section class="wrapper">
            <h3><i class="fa fa-angle-right"></i> WELCOME IN POWERLIFTING!</h3>
            <div class="row mt">
                <div class="col-lg-12">
                    <div class="form-panel">
                        <form class="form-horizontal style-form">
                            <div class="form-group">
                                <div class="col-md-10">
                                    <h3>${user.secondName} ${user.firstName} ${user.middleName}</h3>
                                </div>
                                <div class="col-md-2">
                                    <a href="/edit-profile">
                                        <input type="button" class="btn btn-round btn-info" value="Edit your profile now">
                                    </a>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-md-4">
                                    <img src="/img/${user.photo}" width="200">
                                </div>
                                <div class="col-md-4">
                                    <h5><b>Email</b>: ${user.email}</h5>
                                    <br>
                                <#if user.gender == 0>
                                    <h5><b>Gender:</b> female</h5>
                                <#else>
                                    <h5><b>Gender:</b> male</h5>
                                </#if>
                                </div>
                                <div class="col-md-4">
                                    <h5><b>Birthday:</b> ${user.birthday}</h5>
                                    <br>
                                <#if user.title??>
                                    <h5><b>Title:</b> ${user.title}</h5>
                                </#if>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </section>
    </section>
<#--</body>-->
<#--</html>-->