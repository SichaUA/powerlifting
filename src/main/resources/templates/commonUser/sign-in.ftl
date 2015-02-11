<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>Sign in</title>
    <link href="/libs/css/bootstrap.css" rel="stylesheet">
    <link href="/libs/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="/libs/css/style.css" rel="stylesheet">
    <link href="/libs/css/style-responsive.css" rel="stylesheet">

</head>
<body>
<div id="login-page">
    <div class="container">

        <form id="request-form" class="logining form-login" data-bind="submit: signInRequest">
            <h2 class="form-login-heading">sign in now</h2>
            <div class="login-wrap">
                <div id="error-message" class="alert alert-danger centered hidden">Error! Wrong email or password!</div>
                <input type="text" class="form-control" placeholder="Email" data-bind="value: email" autofocus>
                <br>

                <input type="password" class="form-control" placeholder="Password" data-bind="value: password">
                <br>

                <button class="btn btn-theme btn-block" href="index.html" type="submit"><i class="fa fa-lock"></i> SIGN IN</button>

                <hr>
                <div class="registration">
                    Don't have an account yet?<br/>
                    <a class="" href="/sign-up">
                        Create an account
                    </a>
                </div>
            </div>
        </form>

    </div>
</div>

<!-- js placed at the end of the document so the pages load faster -->
<script src="/libs/js/jquery.js"></script>
<#--<script src="js/libs/bootstrap.min.js"></script>-->

<!--BACKSTRETCH-->
<!-- You can use an image of whatever size. This script will stretch to fit in any screen size.-->
<script type="text/javascript" src="/libs/js/jquery.backstretch.min.js"></script>
<script>
    $.backstretch("img/backgrounds/${background}", {speed: 500});
</script>
<script type="application/javascript" src="/libs/js/knockout-3.2.0.js"></script>
<script type="application/javascript" src="/libs/js/knockout.validation.js"></script>
<script type="application/javascript" src="/js/commonUser/sign-in.js"></script>
</body>
</html>