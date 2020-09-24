Feature: ModiPA Proxy test

Background:
* def url_invocazione_erogazione = govway_base_path + '/rest/in/DemoSoggettoErogatore/ApiDemoNonBlockingRestPullNoValidazione/v1'

* def match_task =
"""
function(task_id) {
    return karate.get('requestParams.returnHttpHeader[0]') == 'Location: /tasks/queue/' + task_id
}
"""

* configure followRedirects = false

* def invalid_implementation_response =
"""
{
    title: "InvalidResponse",
    status :502,
    detail: "Invalid response received from the API Implementation"
}
"""


Scenario: methodIs('post') && pathMatches('/tasks/queue') && match_task('Test-Location-Removed-From-Ack')
    * karate.proceed(url_invocazione_erogazione)
    * remove responseHeaders.Location


Scenario: methodIs('post') && pathMatches('/tasks/queue') && match_task('Test-Status-Not-202')
    * karate.proceed(url_invocazione_erogazione)
    * def responseStatus = 201


Scenario: methodIs('get') && pathMatches('/tasks/queue/{tid}') && karate.get('pathParams.tid') == 'Test-Location-Not-An-URI'
    * def url_invocazione_erogazione_validazione = govway_base_path + '/rest/in/DemoSoggettoErogatore/ApiDemoNonBlockingRestPull/v1'
    * karate.proceed(url_invocazione_erogazione_validazione)
    * set responseHeaders.Location = '/Not/An/Uri'


Scenario: methodIs('get') && pathMatches('/tasks/queue/{tid}') && karate.get('pathParams.tid') == 'Test-Invalid-Status-Request'
    * karate.proceed(url_invocazione_erogazione)
    * def responseStatus = 201

Scenario: methodIs('get') && pathMatches('/tasks/queue/{tid}') && karate.get('pathParams.tid') == 'Test-Location-Removed-From-Status'
    * karate.proceed(url_invocazione_erogazione)
    * remove responseHeaders.Location

Scenario: methodIs('get') && pathMatches('/tasks/result/{tid}') && karate.get('pathParams.tid') == 'Test-Response-Not-200'
    * karate.proceed(url_invocazione_erogazione)
    * def responseStatus = 201


# INIZIO TEST LATO EROGAZIONE

Scenario: methodIs('post') && pathMatches('/tasks/queue') && match_task('Test-Erogazione-Status-Not-202')
    * karate.proceed(url_invocazione_erogazione)
    # Qui c'è i problema che l'erogazione mi dice 201 created invece dovrebbe arrabbiarsi
    * match responseStatus == 502
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
    * match response == problem

Scenario: methodIs('post') && pathMatches('/tasks/queue') && karate.get('requestParams.returnHttpHeader') == null
    # Qui viene testato lo stato 202 senza lo header location
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

    * karate.proceed(url_invocazione_erogazione)
    * match responseStatus == 502
    * match response == problem

Scenario: methodIs('get') && pathMatches('/tasks/queue/{tid}') && karate.get('pathParams.tid') == 'Test-Erogazione-Invalid-Status-Request'
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
    * karate.proceed(url_invocazione_erogazione)
    * match responseStatus == 502
    * match response == problem

Scenario: methodIs('get') && pathMatches('/tasks/queue/{tid}') && karate.get('pathParams.tid') == 'Test-Erogazione-Location-Removed-From-Status'
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
    * karate.proceed(url_invocazione_erogazione)
    * match responseStatus == 502
    * match response == problem

Scenario: methodIs('get') && pathMatches('/tasks/result/{tid}') && karate.get('pathParams.tid') == 'Test-Erogazione-Response-Not-200'
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
    * karate.proceed(url_invocazione_erogazione)
    * match responseStatus == 502
    * match response == problem


Scenario:
    * karate.log('Scenario non matchato')
    * karate.fail('Non hai matchato!')