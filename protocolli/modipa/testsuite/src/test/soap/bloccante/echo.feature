Feature: Test di connettività base https

Background:

* def get_traccia = read('classpath:utils/get_traccia.js')
* def traccia_to_match = 
"""
[
    { name: 'ProfiloInterazione', value: 'bloccante' },
    { name: 'ProfiloSicurezzaCanale', value: 'IDAC01' }
]
"""

Scenario: Test di Echo SOAP

* def body = read("classpath:bodies/modipa-blocking-sample-request-soap.xml")
* def resp = read("classpath:test/risposte-default/soap/bloccante/response.xml")
* def soap_url = govway_base_path + '/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoBlockingSoap/v1'

Given url soap_url
And request body
And header Content-Type = 'application/soap+xml'
And header action = soap_url
When method post
Then status 200
And match response == resp

* def result = get_traccia(responseHeaders['GovWay-Transaction-ID'][0]) 
* match result contains deep traccia_to_match
    