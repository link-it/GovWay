Feature: Test di Proxy Mock per profilo soap bloccante

Background:

* def soap_url = govway_base_path + '/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoBlockingSoap/v1'

* url soap_url

Scenario: Test Demo con mock proxy

    * def body = read("classpath:bodies/modipa-blocking-sample-request-soap.xml")
    * def resp = read("classpath:test/risposte-default/soap/bloccante/response.xml")

    Given request body
    And header Content-Type = 'application/soap+xml'
    And header action = soap_url
    When method post
    Then status 200
    And match response == resp
    