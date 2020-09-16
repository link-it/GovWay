Feature: ModiPA Proxy test

Background:
* def pid = 0

* def url_invocazione_erogazione = govway_base_path + '/rest/in/DemoSoggettoErogatore/ApiDemoBlockingRest/v1'

Scenario: methodIs('post')
    * karate.proceed(url_invocazione_erogazione)
    #* set response.cacca = 4
    #* def response = { aga: "mennone"}