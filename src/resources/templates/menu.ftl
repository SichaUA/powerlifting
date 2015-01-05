<header class="header black-bg">
    <div class="sidebar-toggle-box">
        <div class="fa fa-bars tooltips" data-placement="right" data-original-title="Navigation"></div>
    </div>
    <a href="/" class="logo"><b>POWERLIFTING</b></a>

    <#if user??>
        <div class="top-menu">
            <ul class="nav pull-right top-menu">
                <li><a class="logout" href="" data-bind="click: logoutFunction.bind()">Logout</a></li>
            </ul>
        </div>
    <#else>
        <div class="top-menu">
            <ul class="nav pull-right top-menu">
                <li><a class="logout" href="/sign-in">Sign in</a></li>
            </ul>
        </div>
    </#if>
</header>

<aside>
    <div id="sidebar"  class="nav-collapse ">
        <ul class="sidebar-menu" id="nav-accordion">

            <#if user??>
                <p class="centered"><a href="#"><img src="img/ui-zac.jpg" class="img-circle" width="60"></a></p>
                <h5 class="centered">${user.firstName} ${user.secondName}</h5>

                <li class="sub-menu">
                    <a href="#">
                        <i class="fa fa-male"></i>
                        <span>Profile</span>
                    </a>
                </li>

                <li class="sub-menu">
                    <a href="#">
                        <i class="fa fa-graduation-cap"></i>
                        <span>My Competitions</span>
                    </a>
                </li>

                <li class="sub-menu">
                    <a href="/" data-bind="click: logoutFunction.bind()">
                        <i class="fa fa-sign-out"></i>
                        <span>Logout</span>
                    </a>
                </li>

                <hr>
            <#else>
                <h5 class="centered">Welcome!</h5>
            </#if>

            <li class="mt">
                <a href="/">
                    <i class="fa fa-home"></i>
                    <span>Home</span>
                </a>
            </li>

            <li class="sub-menu">
                <a href="javascript:;" >
                    <i class="fa fa-empire"></i>
                    <span>Competitions</span>
                </a>
                <ul class="sub">
                    <li><a  href="#">All</a></li>
                    <li><a  href="#">Current</a></li>
                    <li><a  href="#">Past</a></li>
                </ul>
            </li>

            <#if !user??>
                <li class="sub-menu">
                    <a href="javascript:;" >
                        <i class="fa fa-sign-in"></i>
                        <span>Enter</span>
                    </a>
                    <ul class="sub">
                        <li><a  href="/sign-in">Sign in</a></li>
                        <li><a  href="/sign-up">Sign up</a></li>
                    </ul>
                </li>
            </#if>

        </ul>
    </div>
</aside>

<!-- js placed at the end of the document so the pages load faster -->
<script src="libs/js/jquery.js"></script>
<script class="include" type="text/javascript" src="libs/js/jquery.dcjqaccordion.2.7.js"></script>
<script src="libs/js/jquery.scrollTo.min.js"></script>
<script src="libs/js/jquery.nicescroll.js" type="text/javascript"></script>

<script type="application/javascript" src="libs/js/knockout-3.2.0.js"></script>
<script type="application/javascript" src="libs/js/knockout.validation.js"></script>
<#if user??>
<script type="application/javascript" src="/js/menu.js"></script>
</#if>




<!--common script for all pages-->
<script src="libs/js/common-scripts.js"></script>