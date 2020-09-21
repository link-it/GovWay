Feature: Test per il profilo di interazione non bloccante rest di tipo PULL

Scenario: Giro OK

* def url_invocazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoNonBlockingRestPull/v1"
* def body_req = read('classpath:bodies/nonblocking-rest-request.json')
* def task_uid = "32bb4c13-e898-4c12-94db-4ddb18de7919"

* def pending_response = read('classpath:test/risposte-default/rest/non-bloccante/pending.json')
* def completed_response = read('classpath:test/risposte-default/rest/non-bloccante/completed.json')
* def result_response = read("classpath:test/risposte-default/rest/bloccante/response.json")


Given url url_invocazione
And path 'tasks', 'queue'
And request body_req
And params ({ returnCode: 202, returnHttpHeader:'Location: /tasks/queue/' + task_uid})
When method post
Then status 202

Given url url_invocazione
And path 'tasks', 'queue', 'not_ready_uid'
And params ({ returnCode: 200, destFile: '/etc/govway/test/protocolli/modipa/rest/non-bloccante/pending.json', destFileContentType: 'application/json' })
When method get
Then status 200

Given url url_invocazione
And path 'tasks', 'queue', task_uid
And params ({ returnCode: 303, returnHttpHeader: 'Location: /tasks/result/' + task_uid, destFile: '/etc/govway/test/protocolli/modipa/rest/non-bloccante/completed.json', destFileContentType: 'application/json' })
When method get
Then status 303

Given url url_invocazione
And path 'tasks', 'result', task_uid
And params ({ returnCode: 200, returnHttpHeader: 'Location: /tasks/result/' + task_uid})
When method get
Then status 200
And match response == result_response
