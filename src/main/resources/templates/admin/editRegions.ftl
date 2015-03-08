<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Regions</title>
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
        <div class="row mt">

            <div class="col-md-10">
                <h3><i class="fa fa-angle-right"></i> Edit Regions</h3>
            </div>
            <div class="col-md-2">
                <input type="button" class="btn btn-round btn-info" value="Add region" onclick="addRegion()"/>
            </div>

            <div class="col-md-12" id="region-binding">
                <div class="form-panel">
                    <form class="form-horizontal style-form" id="moder-form" data-bind="submit: getRegions">

                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Region</label>
                            <div class="col-sm-10">
                                <input id="region-name" type="text" class="form-control" placeholder="California" data-bind="value: regionName">
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-12">
                                <input type="submit" class="btn btn-round btn-success" value="Find"/>
                            </div>
                        </div>
                    </form>
                    <div class="content-panel">
                        <table class="table table-advance table-hover">
                            <thead>
                            <tr>
                                <th><i class="fa fa-globe"></i> Region</th>
                                <th><i class="fa fa-cog"></i> Action</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody data-bind="foreach: regions">
                            <tr>
                                <td data-bind="text: name"></td>
                                <td>
                                    <input type="button" class="btn btn-round btn-danger" value="Delete" data-bind="click: $root.deleteRegion"/>
                                </td>
                                <td></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </section>
</section>

<#include "*/modal/modal.ftl">

<script type="application/javascript" src="/libs/js/bootstrap.min.js"></script>

<script type="application/javascript" src="/libs/js/knockout-3.2.0.js"></script>
<script type="application/javascript" src="/libs/js/knockout.validation.js"></script>
<script type="application/javascript" src="/js/admin/editRegions.js"></script>
</body>
</html>