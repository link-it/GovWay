Feature: payment service mock

Background:
* def pid = 0

* def url_invocazione_erogazione = govway_base_path + '/rest/in/DemoSoggettoErogatore/ApiDemoBlockingRest/v1'

Scenario: pathMatches('/resources/{id}/M') && methodIs('post')
    * karate.proceed(url_invocazione_erogazionee)
    #* set response.cacca = 4
    #* def response = { aga: "mennone"}