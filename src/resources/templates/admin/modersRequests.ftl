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
<#include "*/menu.ftl">


<section id="main-content">
    <section class="wrapper">
        <h3><i class="fa fa-angle-right"></i> Requests</h3>
        <div class="row">

            <div class="col-md-12">
                <div class="content-panel">
                    <h4><i class="fa fa-angle-right"></i> Open moderator requests</h4>
                    <hr>
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Email</th>
                            <th>First Name</th>
                            <th>Second Name</th>
                            <th>Middle Name</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list moders as moder>
                            <tr>
                                <td>${moder.email}</td>
                                <td>${moder.firstName}</td>
                                <td>${moder.secondName}</td>
                                <td>${moder.middleName}</td>
                                <td>
                                    <input type="button" class="btn btn-primary" value="Assign as moderator">
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>

    </section>
</section>
</body>
</html>