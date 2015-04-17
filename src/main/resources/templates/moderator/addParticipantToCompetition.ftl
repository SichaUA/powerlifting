<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Adding participants to competition</title>
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
        <h3><i class="fa fa-angle-right"></i> Add Participants To Competition</h3>
        <div class="row mt">
            <div class="col-lg-12">
                <div class="form-panel">

                    <form id="add-participant-form" class="form-horizontal style-form">
                        <div class="form-group">
                            <div class="col-md-12">
                                <h3>${competition.name}</h3>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-11">
                                <input id="participant-input" type="text" class="form-control" placeholder="User name" autofocus/>
                            </div>
                            <div class="col-md-1">
                                <input type="submit" class="btn btn-round btn-primary" value="Add"/>
                            </div>

                            <br>
                            <br>
                            <br>

                            <div class="btn-group btn-group-justified">
                                <div class="col-md-2 col-md-offset-10">
                                    <input type="button" class="btn btn-round btn-info btn-sm" value="Register New User" onclick="createNewUser()"/>
                                </div>
                            </div>
                        </div>
                    </form>

                    <form id="delete-form" class="form-horizontal style-form">
                        <div class="form-group">
                            <div class="col-md-12">
                                <h4>Current Participants</h4>
                            </div>

                            <div class="col-md-12">
                                <#list participants as participant>
                                    <div class="col-md-4 col-sm-4 mb">
                                        <div class="darkblue-panel pn">
                                            <div class="darkblue-header">
                                                <h5>${participant.secondName} ${participant.firstName} ${participant.middleName}</h5>
                                            </div>
                                            <img src="/img/avatars/${participant.photo}" class="img-circle" height="120">
                                            <p>${participant.email}</p>
                                            <footer>
                                                <div class="centered">
                                                    <button class="btn btn-danger btn-round" value="${participant.userId}" onclick="deleteParticipant(value)">
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

<script src="/js/moderator/addParticipantToCompetition.js"></script>
</body>
</html>