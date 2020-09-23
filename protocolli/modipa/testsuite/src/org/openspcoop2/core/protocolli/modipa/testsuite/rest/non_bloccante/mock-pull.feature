Feature: ModiPA Proxy test

Background:
* def url_invocazione_erogazione = govway_base_path + '/rest/in/DemoSoggettoErogatore/ApiDemoNonBlockingRestPull/v1'

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
    * karate.proceed(url_invocazione_erogazione)
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


Scenario:
    * karate.log('Scenario non matchato')
    * karate.fail('Non hai matchato!')