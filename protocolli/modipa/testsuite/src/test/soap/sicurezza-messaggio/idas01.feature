Feature: Testing Sicurezza Messaggio ModiPA IDAR01

Background:
    * def basic = read('classpath:utils/basic-auth.js')

    * def result = callonce read('classpath:utils/jmx-enable-error-disclosure.feature')
    * configure afterFeature = function(){ karate.call('classpath:utils/jmx-disable-error-disclosure.feature'); }
    
    * def check_traccia = read('check-tracce/check-traccia.feature')

@connettivita-base
Scenario: Test connettività base

* def body = read("request.xml")
* def resp = read("response.xml")
* def soap_url = govway_base_path + '/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/SoapBlockingIDAS01/v1'

Given url soap_url
And request body
And header Content-Type = 'application/soap+xml'
And header action = soap_url
And header GovWay-TestSuite-Test-ID = 'connettivita-base'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method post
Then status 200
And match response == resp

* def karateCache = Java.type('org.openspcoop2.core.protocolli.modipa.testsuite.KarateCache')
* xml client_request = karateCache.get("Client-Request")
* xml server_response = karateCache.get("Server-Response")

* def client_token_to_match = 
"""
({
    x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT',
    wsa_to: karate.xmlPath(client_request, '/Envelope/Header/To'),
    wsa_from: karate.xmlPath(client_request, '/Envelope/Header/From/Address'),
    message_id: karate.xmlPath(client_request, '/Envelope/Header/MessageID')
})
"""

* def server_token_to_match = 
"""
({
    x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT',
    wsa_to: karate.xmlPath(server_response, '/Envelope/Header/To'),
    wsa_from: karate.xmlPath(server_response, '/Envelope/Header/From/Address'),
    message_id: karate.xmlPath(server_response, '/Envelope/Header/MessageID')
})
"""

* def tid = responseHeaders['GovWay-Transaction-ID'][0]
* call check_traccia ({tid: tid, tipo: 'Richiesta', token: client_token_to_match })
* call check_traccia ({tid: tid, tipo: 'Risposta', token: server_token_to_match })

* def tid = responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0]
* call check_traccia ({tid: tid, tipo: 'Richiesta', token: client_token_to_match })
* call check_traccia ({tid: tid, tipo: 'Risposta', token: server_token_to_match })


@connettivita-base-default-trustore
Scenario: Test connettività base

* def body = read("request.xml")
* def resp = read("response.xml")
* def soap_url = govway_base_path + '/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/SoapBlockingIDAS01DefaultTrustore/v1'

Given url soap_url
And request body
And header Content-Type = 'application/soap+xml'
And header action = soap_url
And header GovWay-TestSuite-Test-ID = 'connettivita-base-default-trustore'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method post
Then status 200
And match response == resp

* def karateCache = Java.type('org.openspcoop2.core.protocolli.modipa.testsuite.KarateCache')
* xml client_request = karateCache.get("Client-Request")
* xml server_response = karateCache.get("Server-Response")

* def client_token_to_match = 
"""
({
    x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT',
    wsa_to: karate.xmlPath(client_request, '/Envelope/Header/To'),
    wsa_from: karate.xmlPath(client_request, '/Envelope/Header/From/Address'),
    message_id: karate.xmlPath(client_request, '/Envelope/Header/MessageID')
})
"""

* def server_token_to_match = 
"""
({
    x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT',
    wsa_to: karate.xmlPath(server_response, '/Envelope/Header/To'),
    wsa_from: karate.xmlPath(server_response, '/Envelope/Header/From/Address'),
    message_id: karate.xmlPath(server_response, '/Envelope/Header/MessageID')
})
"""

* def tid = responseHeaders['GovWay-Transaction-ID'][0]
* call check_traccia ({tid: tid, tipo: 'Richiesta', token: client_token_to_match })
* call check_traccia ({tid: tid, tipo: 'Risposta', token: server_token_to_match })

* def tid = responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0]
* call check_traccia ({tid: tid, tipo: 'Richiesta', token: client_token_to_match })
* call check_traccia ({tid: tid, tipo: 'Risposta', token: server_token_to_match })

