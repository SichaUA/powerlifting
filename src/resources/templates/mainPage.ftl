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
        <h3><i class="fa fa-angle-right"></i> Home</h3>
        <div class="row">
            <#list competitions as competition>
                <div class="col-lg-4 col-md-4 col-sm-4 mb">
                    <!-- WHITE PANEL -->
                    <div class="green-panel pn">
                        <div class="green-header">
                            <h5>${competition.name}</h5>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-md-6">
                                <p><b>City:</b> ${competition.city}</p>
                                <p>
                                    <b>Gender:</b>
                                    <#if competition.gender == 1>
                                        чоловіки
                                    <#elseif competition.gender == 2>
                                        жінки
                                    <#else>
                                        всі
                                    </#if>
                                </p>
                            </div>
                            <div class="col-md-6">
                                <p><b>Start date:</b> ${competition.startDate}</p>
                                <p><b>End date:</b> ${competition.endDate}</p>
                            </div>
                        </div>
                        <br>
                        <p>${competition.info}</p>
                    </div>
                </div>
            </#list>

        </div>

    </section>
</section>
</body>
</html>