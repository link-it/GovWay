Feature: ModiPA Proxy test

Background:
* def url_invocazione_erogazione = govway_base_path + '/rest/in/DemoSoggettoErogatore/ApiDemoNonBlockingRestPull/v1'

Scenario: methodIs('post') && pathMatches('/tasks/queue') && karate.get('requestParams.returnHttpHeader[0]') == 'Location: /tasks/queue/32bb4c13-e898-4c12-94db-4ddb18de7919_Location_Removed'
    * karate.proceed(url_invocazione_erogazione)
    * remove responseHeaders.Location

Scenario:
    * karate.log('Request params: ' + requestParams)

    * karate.fail('Non hai matchato!')