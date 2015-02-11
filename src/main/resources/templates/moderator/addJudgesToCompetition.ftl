<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Adding judges to competition</title>
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
        <h3><i class="fa fa-angle-right"></i> Add Judge To Competition</h3>
        <div class="row mt">
            <div class="col-lg-12">
                <div class="form-panel">

                    <form id="add-judge-form" class="form-horizontal style-form centered">
                        <div class="form-group">
                            <div class="col-md-12">
                                <h3>${competition.name}</h3>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-11">
                                <input id="judge-input" type="text" class="form-control" placeholder="Judge second name" autofocus/>
                            </div>

                            <div class="col-md-1">
                                <input type="submit" class="btn btn-round btn-primary" value="Add"/>
                            </div>
                        </div>
                    </form>

                    <form class="form-horizontal style-form">
                        <div class="form-group">
                            <div class="col-md-12">
                                <h4>Current Judges</h4>
                            </div>

                            <div class="col-md-12">
                                <#list judges as judge>
                                        <div class="col-md-4 col-sm-4 mb">
                                            <div class="darkblue-panel pn">
                                                <div class="darkblue-header">
                                                    <h5>${judge.secondName} ${judge.firstName} ${judge.middleName}</h5>
                                                </div>
                                                <img src="/img/avatars/${judge.photo}" class="img-circle" height="120">
                                                <p>${judge.email}</p>
                                                <footer>
                                                    <div class="centered">
                                                        <button class="btn btn-danger btn-round" value="${judge.userId}" onclick="deleteJudge(value)">
                                                            Delete
                                                        </button>
                                                    </div>
                                                </footer>
                                            </div>
                                        </div>
                                </#list>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </section>
</section>
<script type="application/javascript" src="/libs/js/bootstrap.min.js"></script>
<script type="application/javascript" src="/libs/js/jquery.autocomplete.js"></script>

<script src="/js/moderator/addJudgesToCompetition.js"></script>
</body>
</html>