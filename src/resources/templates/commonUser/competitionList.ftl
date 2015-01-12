<#list competitions as competition>
    <div class="col-lg-4 col-md-4 col-sm-4 mb">
        <a href="/competition/${competition.id}">
            <div class="green-panel pn">
                <div class="green-header">
                    <h5>${competition.name}</h5>
                </div>
                <br>

                <div class="row">
                    <div class="col-md-6">
                        <p><b>City:</b> ${competition.city}</p>

                        <p>
                            <b>Gender:</b>
                            <#if competition.gender == 1>
                                чоловіки
                            <#elseif competition.gender == 2>
                                жінки
                            <#else>
                                всі
                            </#if>
                        </p>
                    </div>
                    <div class="col-md-6">
                        <p><b>Start date:</b> ${competition.startDate}</p>

                        <p><b>End date:</b> ${competition.endDate}</p>
                    </div>
                </div>
                <br>

                <p>${competition.info}</p>
            </div>
        </a>
    </div>
</#list>