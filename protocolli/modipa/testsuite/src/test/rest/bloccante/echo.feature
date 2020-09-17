Feature: Feature test connettività base https

Background:

* url govway_base_path + '/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoBlockingRest/v1'
* def get_traccia = read('classpath:utils/get_traccia.js')

Scenario: Test di Echo REST

* def body = read("classpath:bodies/modipa-blocking-sample-request.json")
* def resp = read("classpath:test/risposte-default/rest/bloccante/response.json")

Given path 'resources', 1, 'M'
And request body
When method post
Then status 200
And match response == resp

Scenario: Test di Echo REST e verifica traccia sul DB

* def body = read("classpath:bodies/modipa-blocking-sample-request.json")
* def resp = read("classpath:test/risposte-default/rest/bloccante/response.json")

Given path 'resources', 1, 'M'
And request body
When method post
Then status 200
And match response == resp

* def to_match = 
"""
[
    { name: 'ProfiloInterazione', value: 'bloccante' },
    { name: 'ProfiloSicurezzaCanale', value: 'IDAC01' }
]
"""

* def result = get_traccia(responseHeaders['GovWay-Transaction-ID'][0]) 
* match result contains deep to_match
