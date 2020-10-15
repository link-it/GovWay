Feature: Testing Sicurezza Messaggio ModiPA IDAR01

Background:
    * def basic = read('classpath:utils/basic-auth.js')

    * def result = callonce read('classpath:utils/jmx-enable-error-disclosure.feature')
    * configure afterFeature = function(){ karate.call('classpath:utils/jmx-disable-error-disclosure.feature'); }
    
    * def check_traccia = read('check-tracce/check-traccia.feature')
    * def check_signature = read('classpath:org/openspcoop2/core/protocolli/modipa/testsuite/soap/sicurezza_messaggio/check-signature.feature')

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
Scenario: Test connettività base con trustore default

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


@connettivita-base-no-sbustamento
Scenario: Test connettività base senza sbustamento modipa

* def body = read("request.xml")
* def resp = read("response.xml")
* def soap_url = govway_base_path + '/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/SoapBlockingIDAS01DefaultTrustore/v1'

Given url soap_url
And request body
And header Content-Type = 'application/soap+xml'
And header action = soap_url
And header GovWay-TestSuite-Test-ID = 'connettivita-base-no-sbustamento'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01NoSbustamento', password: 'ApplicativoBlockingIDA01NoSbustamento' })
When method post
Then status 200

* match /Envelope/Header/Security/Signature == "#present"
* match /Envelope/Header/Security/Timestamp/Created == "#string"
* match /Envelope/Header/Security/Timestamp/Expires == "#string"
* match /Envelope/Header/To == "DemoSoggettoFruitore/ApplicativoBlockingIDA01NoSbustamento"
* match /Envelope/Header/From/Address == "SoapBlockingIDAS01DefaultTrustoreNoSbustamento/v1"
* match /Envelope/Header/MessageID == "#uuid"

* def body = response
* call check_signature [ {element: 'To'}, {element: 'From'}, {element: 'MessageID'}, {element: 'ReplyTo'}, {element: 'RelatesTo'} ]

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



@connettivita-base-truststore-ca
Scenario: Test connettività base con erogazione e fruizione che usano il trustore delle CA

* def body = read("request.xml")
* def resp = read("response.xml")
* def soap_url = govway_base_path + '/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/SoapBlockingIDAS01TrustStoreCA/v1'

Given url soap_url
And request body
And header Content-Type = 'application/soap+xml'
And header action = soap_url
And header GovWay-TestSuite-Test-ID = 'connettivita-base-truststore-ca'
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


@response-without-payload
Scenario: Test di una azione che non ha il payload nella risposta

* def body = read("only-request.xml")
* def soap_url = govway_base_path + '/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/SoapBlockingIDAS01MultipleOP/v1'

Given url soap_url
And request body
And header Content-Type = 'application/soap+xml'
And header action = soap_url
And header GovWay-TestSuite-Test-ID = 'response-without-payload'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method post
Then status 200
And match response == ''

* def karateCache = Java.type('org.openspcoop2.core.protocolli.modipa.testsuite.KarateCache')
* xml client_request = karateCache.get("Client-Request")

* def client_token_to_match = 
"""
({
    x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT',
    wsa_to: karate.xmlPath(client_request, '/Envelope/Header/To'),
    wsa_from: karate.xmlPath(client_request, '/Envelope/Header/From/Address'),
    message_id: karate.xmlPath(client_request, '/Envelope/Header/MessageID')
})
"""

* def tid = responseHeaders['GovWay-Transaction-ID'][0]
* call check_traccia ({tid: tid, tipo: 'Richiesta', token: client_token_to_match })

    # TODO: Riabilita il test sul tid dell'erogazione qui se andrea fa viaggiare lo header nel profilo soap oneway
    #   Altrimenti fai aggiungre lo heder al proxy e fai comunque il test qui sotto
    #* def tid = responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0]
    #* call check_traccia ({tid: tid, tipo: 'Richiesta', token: client_token_to_match })



@disabled-security-on-action
Scenario: Test risorsa non protetta in una API con IDAS01 abilitato di default

* def body = read("MRequestResponse.xml")
* def soap_url = govway_base_path + '/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/SoapBlockingIDAS01MultipleOP/v1'

Given url soap_url
And request body
And header Content-Type = 'application/soap+xml'
And header action = soap_url
And header GovWay-TestSuite-Test-ID = 'disabled-security-on-action'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method post
Then status 200
And match response == read('response.xml')