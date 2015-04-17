<#setting locale="uk_UA">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Judges To Sequence</title>
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
        <div class="row mt">
            <div class="col-md-12">
                <div class="col-md-9">
                    <h3><i class="fa fa-angle-right"></i> Add judges to sequence</h3>
                    <h5>
                        <b>Sequence:</b><br>
                        Date: ${sequence.date}
                        <br>
                        Info: ${sequence.info}
                        <br>
                        Categories:<br>
                    <#list sequence.categories as category>
                    ${category.key.name}; ${category.value.group}(${category.value.description})<br>
                    </#list>
                    </h5>
                </div>

                <div class="col-md-3">
                    <input type="button" class="btn btn-round btn-primary" value="Add judges to competition" onclick="window.location = '/moder/add-judges/' + window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);"/>
                </div>
            </div>

            <div id="judge-table" class="col-lg-12" data-bind="foreach: sequences">
                <div class="content-panel">
                    <table class="table table-striped table-advance table-hover">
                        <h4><i class="fa fa-angle-right"></i> Judges:</h4>

                        <hr>
                        <thead>
                        <tr>
                            <th><i class="fa "></i> Photo</th>
                            <th><i class="fa "></i> Name</th>
                            <th><i class="fa "></i> Email</th>
                            <th><i class="fa "></i> Gender</th>
                            <th><i class="fa "></i> Birthday</th>
                            <th><i class="fa "></i> Judge Category</th>
                            <th><i class="fa "></i> Assignment Date</th>
                            <th><i class="fa "></i> </th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                            <#list alreadyJudges as judge>
                                <tr>
                                    <td><img src="/img/avatars/${judge.judge.photo}" width="60"></td>
                                    <td>${judge.judge.firstName} ${judge.judge.middleName} ${judge.judge.secondName}</td>
                                    <td>
                                        <#if judge.judge.email??>
                                            ${judge.judge.email}
                                        <#else>
                                            -
                                        </#if>
                                    </td>
                                    <td>
                                        <#if judge.judge.gender == 1>
                                            Male
                                        <#else>
                                            Female
                                        </#if>
                                    </td>
                                    <td>${judge.judge.birthday}</td>
                                    <td>
                                        <#list judgeCategories as category>
                                            <#if category.categoryId == judge.judge.category>
                                                ${category.category}
                                                <#break>
                                            </#if>
                                        </#list>
                                    </td>
                                    <td>${judge.judge.assignmentDate}</td>
                                    <td>
                                        <select class="form-control judge-select">
                                            <option value="-1" selected>Not used</option>
                                            <#list judgeTypes as type>
                                                <option value="${type.typeId}" <#if judge.typeId == type.typeId>selected</#if> >
                                                ${type.name}
                                                </option>
                                            </#list>
                                        </select>
                                    </td>

                                    <td></td>
                                    <input class="judge-id" type="hidden" value="${judge.judge.userId}"/>
                                </tr>
                            </#list>
                            <#list competitionJudge as judge>
                                <tr>
                                    <td><img src="/img/avatars/${judge.photo}" width="60"></td>
                                    <td>${judge.secondName} ${judge.firstName} ${judge.middleName}</td>
                                    <td>
                                        <#if judge.email??>
                                        ${judge.email}
                                        <#else>
                                            -
                                        </#if>
                                    </td>
                                    <td>
                                        <#if judge.gender == 1>
                                            Male
                                        <#else>
                                            Female
                                        </#if>
                                    </td>
                                    <td>${judge.birthday}</td>
                                    <td>
                                        <#list judgeCategories as category>
                                            <#if category.categoryId == judge.category>
                                                ${category.category}
                                                <#break>
                                            </#if>
                                        </#list>
                                    </td>
                                    <td>${judge.assignmentDate}</td>
                                    <td>
                                        <select class="form-control judge-select">
                                            <option value="-1" selected>Not used</option>
                                            <#list judgeTypes as type>
                                                <option value="${type.typeId}">
                                                ${type.name}
                                                </option>
                                            </#list>
                                        </select>
                                    </td>

                                    <td></td>
                                    <input class="judge-id" type="hidden" value="${judge.userId}"/>
                                </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
</section>
<#include "*/modal/modal.ftl">

<script type="application/javascript" src="/libs/js/bootstrap.min.js"></script>

<script type="application/javascript" src="/libs/js/knockout-3.2.0.js"></script>
<script type="application/javascript" src="/libs/js/knockout.validation.js"></script>

<script type="text/javascript" charset="UTF-8" src="/js/moderator/addJudgesToSequence.js"></script>
</body>
</html>

<#--
старший суддя - 1
боковий суддя - 2
секретар - 1
помічник секретаря - 1
суддя при учасниках - 1
комп'ютерний секретар - 1
журі(голова і члени)

загальні - головний суддя і головний секретар-->
