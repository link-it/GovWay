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
    * match response contains invalid_implementation_response

Scenario: methodIs('post') && pathMatches('/tasks/queue') && karate.get('requestParams.returnHttpHeader') == null
    * karate.proceed(url_invocazione_erogazione)
    * match responseStatus == 502
    * match response contains invalid_implementation_response

Scenario: methodIs('get') && pathMatches('/tasks/queue/{tid}') && karate.get('pathParams.tid') == 'Test-Erogazione-Invalid-Status-Request'
    * karate.proceed(url_invocazione_erogazione)
    * match responseStatus == 502
    * match response contains invalid_implementation_response

Scenario: methodIs('get') && pathMatches('/tasks/queue/{tid}') && karate.get('pathParams.tid') == 'Test-Erogazione-Location-Removed-From-Status'
    * karate.proceed(url_invocazione_erogazione)
    * match responseStatus == 502
    * match response contains invalid_implementation_response

Scenario: methodIs('get') && pathMatches('/tasks/result/{tid}') && karate.get('pathParams.tid') == 'Test-Erogazione-Response-Not-200'
    * karate.proceed(url_invocazione_erogazione)
    * match responseStatus == 502
    * match response contains invalid_implementation_response


Scenario:
    * karate.log('Scenario non matchato')
    * karate.fail('Non hai matchato!')