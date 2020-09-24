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

@request-task-no-location
Scenario: Richiesta processamento con stato 202 e senza Header Location
    * def problem =
    """
    {
        type: "https://govway.org/handling-errors/502/InteroperabilityResponseManagementFailed.html",
        title: "InteroperabilityResponseManagementFailed",
        status: 502,
        detail: "Header http 'Location', richiesto dal profilo non bloccante PULL, non trovato",
        govway_id: "#string"
    }
    """

    Given url url_invocazione
    And path 'tasks', 'queue'
    And request body_req
    And params ({ returnCode: 202 })
    When method post
    Then status 502
    And match response == problem

@request-task-not-202
Scenario: Richiesta processamento con stato diverso da 202

    * def task_id = "Test-Erogazione-Status-Not-202"
    * def problem = 
    """
    {
        type: "https://govway.org/handling-errors/502/InteroperabilityResponseManagementFailed.html",
        title: "InteroperabilityResponseManagementFailed",
        status: 502,
        detail: "HTTP Status '201' differente da quello atteso per il profilo non bloccante 'PULL' con ruolo 'Richiesta' (atteso: 202)",
        govway_id: "#string"
    }
    """
    

    Given path 'tasks', 'queue'
    And request body_req
    And params ({ returnCode: 201, returnHttpHeader:'Location: /tasks/queue/' + task_id})
    When method post
    Then status 502
    And match response == problem


@invalid-status-from-request
Scenario: Richiesta stato operazione con stato http diverso da 200 e 303

    * def task_id = "Test-Erogazione-Invalid-Status-Request"
    * def problem = 
    """
    {
        type: "https://govway.org/handling-errors/502/InteroperabilityResponseManagementFailed.html",
        title: "InteroperabilityResponseManagementFailed",
        status: 502,
        detail: "HTTP Status '201' differente da quello atteso per il profilo non bloccante 'PULL' con ruolo 'RichiestaStato' (atteso: 200,303)",
        govway_id: "#string"
    }
    """

    Given url url_invocazione
    And path 'tasks', 'queue', task_id
    And params ({ returnCode: 201, destFile: '/etc/govway/test/protocolli/modipa/rest/non-bloccante/pending.json', destFileContentType: 'application/json' })
    When method get
    Then status 502
    And match response == problem


@no-location-from-status
Scenario: Richiesta stato operazione completata senza header location

    * def task_id = "Test-Erogazione-Location-Removed-From-Status"
    * def problem =
    """
    {
        type: "https://govway.org/handling-errors/502/InteroperabilityResponseManagementFailed.html",
        title: "InteroperabilityResponseManagementFailed",
        status: 502,
        detail: "Header http 'Location', richiesto dal profilo non bloccante PULL, non trovato",
        govway_id: "#string"
    }
    """

    Given url url_invocazione
    And path 'tasks', 'queue', task_id
    And params ({ returnCode: 303, destFile: '/etc/govway/test/protocolli/modipa/rest/non-bloccante/completed.json', destFileContentType: 'application/json' })
    When method get
    Then status 502
    And match response == problem


@task-response-not-200
Scenario: Ottenimento risorsa processata con stato diverso da 200 OK    

    * def task_id = "Test-Erogazione-Response-Not-200"
    * def problem =
    """
    {
        type: "https://govway.org/handling-errors/502/InteroperabilityInvalidResponse.html",
        title: "InteroperabilityInvalidResponse",
        status: 502,
        detail: "HTTP Status '202' differente da quello atteso per il profilo non bloccante 'PULL' con ruolo 'Risposta' (atteso: 200)",
        govway_id: "#string"
    }
    """

    Given url url_invocazione
    And path 'tasks', 'result', task_id
    And params ({ returnCode: 202 })
    When method get
    Then status 502
    And match response == problem

