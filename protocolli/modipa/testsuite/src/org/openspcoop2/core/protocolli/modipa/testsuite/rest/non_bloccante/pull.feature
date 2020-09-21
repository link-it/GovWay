Feature: Test per il profilo di interazione non bloccante rest di tipo PULL

Scenario: Giro OK

* def url_invocazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoNonBlockingRestPull/v1"
* def body_req = read('classpath:bodies/nonblocking-rest-request.json')

Given url url_invocazione
And path 'tasks', 'queue'
And request body_req
And params ({ returnCode: 202, returnHttpHeader:'Location: /tasks/queue/32bb4c13-e898-4c12-94db-4ddb18de7919'})
When method post
Then status 202
