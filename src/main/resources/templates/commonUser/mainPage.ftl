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
                <#include "*/competitionList.ftl">
            </#list>

        </div>

    </section>
</section>
</body>
</html>