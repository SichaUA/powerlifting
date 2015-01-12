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
        <div class="row">
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
                                    <#--TODO finish this to every user-->
                                    <#if user??>
                                        <input type="button" class="btn btn-primary btn-round btn-lg" value="Participate" />
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-md-6">
                                    <h4><b>City:</b> ${competition.city}</h4>
                                </div>
                                <div class="col-md-6">
                                    <h4>
                                        <b>Gender:</b>
                                        <#if competition.gender == 1>
                                            male
                                        <#elseif competition.gender == 2>
                                            female
                                        <#else>
                                            all
                                        </#if>
                                    </h4>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-md-6">
                                    <h4><b>Start date:</b> ${competition.startDate}</h4>
                                </div>
                                <div class="col-md-6">
                                    <h4><b>End date:</b> ${competition.endDate}</h4>
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

        </div>

    </section>
</section>
</body>
</html>