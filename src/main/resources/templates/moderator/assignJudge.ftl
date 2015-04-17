<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Select judge from users</title>
    <!-- Bootstrap core CSS -->
    <link href="/libs/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="/libs/font-awesome/css/font-awesome.css" rel="stylesheet" />

    <!-- Custom styles for this template -->
    <link href="/libs/css/style.css" rel="stylesheet">
    <#--<link href="/libs/css/style-responsive.css" rel="stylesheet">-->

    <link href="/libs/css/autocomplete.css" rel="stylesheet">

</head>
<body>
<#include "*/commonUser/menu.ftl">


<section id="main-content">
    <section class="wrapper">
        <h3><i class="fa fa-angle-right"></i> Select Judge From Users</h3>
        <div class="row mt">
            <div class="col-lg-12">
                <div class="form-panel">

                    <form id="select-judge-form" class="form-horizontal style-form">
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">User</label>
                            <div class="col-sm-10">
                                <input id="user-input" type="text" class="form-control" placeholder="User name" autofocus/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Category</label>
                            <div class="col-sm-10">
                                <select id="category-select" class="form-control">
                                    <#list categories as category>
                                        <option value="${category.categoryId}">
                                            ${category.category}
                                        </option>
                                    </#list>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Date of category appointment</label>
                            <div class="col-sm-10">
                                <input id="datepicker" type="text" class="form-control">
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-12">
                                <label>
                                    <input id="addJudgeToCompetition" type="checkbox" checked /> Add judge to competition
                                </label>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-12">
                                <input type="submit" class="btn btn-round btn-primary" value="Create New Judge"/>
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
<script src="/libs/js/bootstrap.js"></script>
<script src="/libs/js/bootstrap-datepicker.js"></script>
<script>
    $('#datepicker').datepicker();
</script>

<script src="/js/moderator/assignJudge.js"></script>
</body>
</html>