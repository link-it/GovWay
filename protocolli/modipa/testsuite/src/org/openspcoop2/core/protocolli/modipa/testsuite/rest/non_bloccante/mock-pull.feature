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

Scenario: methodIs('post') && pathMatches('/tasks/queue') && karate.get('requestParams.returnHttpHeader') == null
    # Qui viene testato lo stato 202 senza lo header location
    
    * def problem = read('classpath:test/rest/non-bloccante/error-bodies/request-task-no-location-erogazione.json')

    * karate.proceed(url_invocazione_erogazione)
    * match responseStatus == 502
    * match response == problem


Scenario: methodIs('post') && pathMatches('/tasks/queue') && match_task('Test-Erogazione-Status-Not-202')
    
    * def problem = read('classpath:test/rest/non-bloccante/error-bodies/request-task-not-202-erogazione.json')    
    
    * karate.proceed(url_invocazione_erogazione)
    * match responseStatus == 502
    * match response == problem

Scenario: methodIs('get') && pathMatches('/tasks/queue/{tid}') && karate.get('pathParams.tid') == 'Test-Erogazione-Invalid-Status-Request'
    
    * def problem = read('classpath:test/rest/non-bloccante/error-bodies/invalid-status-from-request-erogazione.json')

    * karate.proceed(url_invocazione_erogazione)
    * match responseStatus == 502
    * match response == problem

Scenario: methodIs('get') && pathMatches('/tasks/queue/{tid}') && karate.get('pathParams.tid') == 'Test-Erogazione-Location-Removed-From-Status'
    * def problem = read('classpath:test/rest/non-bloccante/error-bodies/no-location-from-status-erogazione.json')

    * karate.proceed(url_invocazione_erogazione)
    * match responseStatus == 502
    * match response == problem

Scenario: methodIs('get') && pathMatches('/tasks/result/{tid}') && karate.get('pathParams.tid') == 'Test-Erogazione-Response-Not-200'
    * def problem = read('classpath:test/rest/non-bloccante/error-bodies/task-response-not-200-erogazione.json')

    * karate.proceed(url_invocazione_erogazione)
    * match responseStatus == 502
    * match response == problem


Scenario:
    * karate.log('Scenario non matchato')
    * karate.fail('Non hai matchato!')