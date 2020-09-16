Feature: Feature test connettività base https

Background:

* url govway_base_path + '/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoBlockingRest/v1'

Scenario: Test di Echo REST

    * def body = read("classpath:bodies/modipa-blocking-sample-request.json")
    * def resp = read("classpath:test/risposte-default/rest/bloccante/response.json")

    Given path 'resources', 1, 'M'
    And request body
    When method post
    Then status 200
    And match response == resp
