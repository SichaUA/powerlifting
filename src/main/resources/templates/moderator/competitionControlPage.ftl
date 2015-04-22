<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Competition control</title>
    <!-- Bootstrap core CSS -->
    <link href="/libs/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="/libs/font-awesome/css/font-awesome.css" rel="stylesheet" />

    <!-- Custom styles for this template -->
    <link href="/libs/css/style.css" rel="stylesheet">
    <link href="/libs/css/style-responsive.css" rel="stylesheet">

    <link rel="stylesheet" type="text/css" href="/libs/lineicons/style.css">

    <#--<link href="/libs/css/autocomplete.css" rel="stylesheet">-->

    <#--<link rel="stylesheet" type="text/css" href="/libs/js/gritter/css/jquery.gritter.css" />-->

</head>
<body>
<#include "*/commonUser/menu.ftl">

<section id="main-content">
    <section class="wrapper">
        <div class="row mt">
            <div class="col-md-12">
                <h3><i class="fa fa-angle-right"></i> Competition control</h3>
            </div>

            <div class="row mtbox">
                <a href="/moder/weighingParticipants/${competitionId}">
                    <div class="col-md-2 col-sm-2 col-md-offset-1 box0">
                        <div class="box1">
                            <span class="li_heart"></span>
                            <h3>Participant weight</h3>
                        </div>
                        <p>Set participants weight.</p>
                    </div>
                </a>
            </div>
        </div>
    </section>
</section>
<#--<#include "*/modal/modal.ftl">-->

<script type="application/javascript" src="/libs/js/bootstrap.min.js"></script>

<#--<script type="application/javascript" src="/libs/js/knockout-3.2.0.js"></script>-->
<#--<script type="application/javascript" src="/libs/js/knockout.validation.js"></script>-->

<#--<script type="text/javascript" src="/libs/js/gritter/js/jquery.gritter.js"></script>-->
<#--<script type="text/javascript" src="/libs/js/gritter-conf.js"></script>-->

<#--<script type="text/javascript" charset="UTF-8" src="/js/moderator/splitSequenceIntoGroups.js"></script>-->
</body>
</html>