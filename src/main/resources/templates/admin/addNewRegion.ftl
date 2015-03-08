<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Region</title>
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
        <h3><i class="fa fa-angle-right"></i> Add New Region</h3>
        <div class="row mt">
            <div class="col-md-12">
                <div class="form-panel">
                    <form class="form-horizontal style-form" id="region-form" data-bind="submit: addRegion">

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Region</label>
                            <div class="col-sm-10">
                                <input id="region-name" type="text" class="form-control" placeholder="California" data-bind="value: regionName">
                            </div>
                        </div>

                        <input type="submit" class="btn btn-round btn-success" value="Add"/>

                    </form>
                </div>
            </div>
        </div>
    </section>
</section>

<#include "*/modal/modal.ftl">

<script type="application/javascript" src="/libs/js/bootstrap.min.js"></script>

<script type="application/javascript" src="/libs/js/knockout-3.2.0.js"></script>
<script type="application/javascript" src="/libs/js/knockout.validation.js"></script>
<script type="application/javascript" src="/js/admin/addNewRegion.js"></script>
</body>
</html>