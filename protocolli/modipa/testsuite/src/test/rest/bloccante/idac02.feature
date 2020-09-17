Feature: Testing feature idac02 REST

Scenario: IDAC02 Autenticazione Client

* def body = read("classpath:bodies/modipa-blocking-sample-request.json")
* def resp = read("classpath:test/risposte-default/rest/bloccante/response.json")

Given url govway_base_path + '/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoBlockingRestIDAC02/v1'
And path 'resources', 1, 'M'
And request body
When method post
Then status 200
And match response == resp


Scenario: IDAC02 Autenticazione Client Assente

* def body = read("classpath:bodies/modipa-blocking-sample-request.json")

Given url govway_base_path + '/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoBlockingRestIDAC02BadAuth/v1'

And path 'resources', 1, 'M'
And request body
When method post
Then status 401