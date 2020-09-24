Feature: Test rilevamento errori lato erogazione

Background:

* configure followRedirects = false

* def url_invocazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoNonBlockingRestPullProxyNoValidazione/v1"
* url url_invocazione

* def body_req = read('classpath:bodies/nonblocking-rest-request.json')
* def invalid_implementation_response =
"""
{
    title: "InvalidResponse",
    status :502,
    detail: "Invalid response received from the API Implementation"
}
"""

@request-task-not-202
Scenario: Richiesta processamento con stato diverso da 202

    * def task_id = "Test-Erogazione-Status-Not-202"

    Given path 'tasks', 'queue'
    And request body_req
    And params ({ returnCode: 201, returnHttpHeader:'Location: /tasks/queue/' + task_id})
    When method post
    Then status 502
    And match response contains invalid_implementation_response

@request-task-no-location
Scenario: Richiesta processamento con stato 202 e senza Header Location

    Given url url_invocazione
    And path 'tasks', 'queue'
    And request body_req
    And params ({ returnCode: 202 })
    When method post
    Then status 502
    And match response contains invalid_implementation_response

@invalid-status-from-request
Scenario: Richiesta stato operazione con stato http diverso da 200 e 303

    * def task_id = "Test-Erogazione-Invalid-Status-Request"

    Given url url_invocazione
    And path 'tasks', 'queue', task_id
    And params ({ returnCode: 201, destFile: '/etc/govway/test/protocolli/modipa/rest/non-bloccante/pending.json', destFileContentType: 'application/json' })
    When method get
    Then status 502
    And match response contains invalid_implementation_response


@no-location-from-status
Scenario: Richiesta stato operazione completata senza header location

    * def task_id = "Test-Erogazione-Location-Removed-From-Status"

    Given url url_invocazione
    And path 'tasks', 'queue', task_id
    And params ({ returnCode: 303, destFile: '/etc/govway/test/protocolli/modipa/rest/non-bloccante/completed.json', destFileContentType: 'application/json' })
    When method get
    Then status 502
    And match response contains invalid_implementation_response


@task-response-not-200
Scenario: Ottenimento risorsa processata con stato diverso da 200 OK    

    * def task_id = "Test-Erogazione-Response-Not-200"

    Given url url_invocazione
    And path 'tasks', 'result', task_id
    And params ({ returnCode: 202 })
    When method get
    Then status 502
    And match response contains invalid_implementation_response

