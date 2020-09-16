Feature: Test di connettività base https

Scenario: Test di Echo SOAP

    * def body = read("classpath:bodies/modipa-blocking-sample-request-soap.xml")
    * def resp = read("classpath:test/risposte-default/soap/bloccante/response.xml")

    Given url "http://localhost:8080/govway/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoBlockingSoap/v1"
    And request body
    And header Content-Type = 'application/soap+xml'
    And header action = "http://localhost:8080/govway/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoBlockingSoap/v1"
    When method post
    Then status 200
    And match response == resp
    