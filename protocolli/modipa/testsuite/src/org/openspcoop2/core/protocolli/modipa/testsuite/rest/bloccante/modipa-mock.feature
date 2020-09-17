Feature: ModiPA Proxy test

Background:
* def pid = 0

* def url_invocazione_erogazione = govway_base_path + '/rest/in/DemoSoggettoErogatore/ApiDemoBlockingRest/v1'

* def get_traccia = read('classpath:utils/get_traccia.js')
* def traccia_to_match = 
"""
[
    { name: 'ProfiloInterazione', value: 'bloccante' },
    { name: 'ProfiloSicurezzaCanale', value: 'IDAC01' }
]
"""

Scenario: pathMatches('/resources/{id}/M') && methodIs('post')
    * karate.proceed(url_invocazione_erogazione)
    
    * def result = get_traccia(responseHeaders['GovWay-Transaction-ID'][0]) 
    * match result contains deep traccia_to_match
    #* set response.cacca = 4
    #* def response = { aga: "mennone"}