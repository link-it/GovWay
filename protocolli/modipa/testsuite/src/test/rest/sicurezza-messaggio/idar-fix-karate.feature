Feature: Testing Sicurezza Messaggio ModiPA IDAR payload vuoti

Background:
    * def basic = read('classpath:utils/basic-auth.js')
    * def check_traccia = read('check-tracce/check-traccia.feature')
    * def decode_token = read('classpath:utils/decode-token.js')

    * def result = callonce read('classpath:utils/jmx-enable-error-disclosure.feature')
    * configure afterFeature = function(){ karate.call('classpath:utils/jmx-disable-error-disclosure.feature'); }

@request-without-payload
Scenario: Test di un endpoint che non ha il payload nella richiesta

* def url_invocazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestBlockingIDAR01CRUD/v1"

Given url url_invocazione
And path 'resources', 'object', 1
And header GovWay-TestSuite-Test-ID = 'request-without-payload'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method get
Then status 200
And match header Authorization == '#notpresent'
And match response == read('request.json')

* def client_token = decode_token(responseHeaders['GovWay-TestSuite-GovWay-Client-Token'][0])
* def server_token = decode_token(responseHeaders['GovWay-TestSuite-GovWay-Server-Token'][0])

* def tid = responseHeaders['GovWay-Transaction-ID'][0]
* call check_traccia ({ tid: tid, tipo: 'Richiesta', token: client_token, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT' })
* call check_traccia ({ tid: tid, tipo: 'Risposta', token: server_token, x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT' })

* def tid = responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0]
* call check_traccia ({ tid: tid, tipo: 'Richiesta', token: client_token, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT' })
* call check_traccia ({ tid: tid, tipo: 'Risposta', token: server_token, x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT' })


@request-response-without-payload
Scenario: Test di un endpoint che non ha il payload ne nella richiesta ne nella risposta

* def url_invocazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestBlockingIDAR01CRUD/v1"

Given url url_invocazione
And path 'resources', 'object', 1
And header GovWay-TestSuite-Test-ID = 'request-response-without-payload'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method delete
Then status 204
And match header Authorization == '#notpresent'

* def client_token = decode_token(responseHeaders['GovWay-TestSuite-GovWay-Client-Token'][0])
* def server_token = decode_token(responseHeaders['GovWay-TestSuite-GovWay-Server-Token'][0])

* def tid = responseHeaders['GovWay-Transaction-ID'][0]
* call check_traccia ({ tid: tid, tipo: 'Richiesta', token: client_token, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT' })
* call check_traccia ({ tid: tid, tipo: 'Risposta', token: server_token, x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT' })

* def tid = responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0]
* call check_traccia ({ tid: tid, tipo: 'Richiesta', token: client_token, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT' })
* call check_traccia ({ tid: tid, tipo: 'Risposta', token: server_token, x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT' })


@request-without-payload
Scenario: Test di un endpoint che non ha il payload nella richiesta

* def url_invocazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestBlockingIDAR03CRUD/v1"

Given url url_invocazione
And path 'resources', 'object', 1
And header GovWay-TestSuite-Test-ID = 'request-without-payload-idar03'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method get
Then status 200
And match header Authorization == '#notpresent'
And match response == read('request.json')

* def client_token = decode_token(responseHeaders['GovWay-TestSuite-GovWay-Client-Token'][0])
* def server_token = decode_token(responseHeaders['GovWay-TestSuite-GovWay-Server-Token'][0])

* def response_digest = get server_token $.payload.signed_headers..digest

* def other_checks_risposta = 
"""
([
    { name: 'ProfiloSicurezzaMessaggio-Digest', value: response_digest[0] },
    { name: 'ProfiloSicurezzaMessaggioSignedHeader-digest', value: response_digest[0] },
    { name: 'ProfiloSicurezzaMessaggioSignedHeader-content-type', value: 'application/json' }
])
"""

* def tid = responseHeaders['GovWay-Transaction-ID'][0]
* call check_traccia ({ tid: tid, tipo: 'Richiesta', token: client_token, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT', profilo_sicurezza: "IDAR0301", other_checks: [] })
* call check_traccia ({ tid: tid, tipo: 'Risposta', token: server_token, x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT', profilo_sicurezza: "IDAR0301", other_checks: other_checks_risposta })

* def tid = responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0]
* call check_traccia ({ tid: tid, tipo: 'Richiesta', token: client_token, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT', profilo_sicurezza: "IDAR0301", other_checks: [] })
* call check_traccia ({ tid: tid, tipo: 'Risposta', token: server_token, x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT', profilo_sicurezza: "IDAR0301", other_checks: other_checks_risposta })


@request-without-payload-tampered-header
Scenario: Manomissione header firmato nel test di un endpoint che non ha il payload nella richiesta

* def url_invocazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestBlockingIDAR01CRUD/v1"

Given url url_invocazione
And path 'resources', 'object', 1
And header GovWay-TestSuite-Test-ID = 'request-without-payload-idar03-tampered-header'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
And header IDAR03TestHeader = "TestHeaderRequest"
When method get
Then status 200
And match response == read('request.json')




@request-response-without-payload
Scenario: Test di un endpoint che non ha il payload ne nella richiesta ne nella risposta

* def url_invocazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestBlockingIDAR03CRUD/v1"

Given url url_invocazione
And path 'resources', 'object', 1
And header GovWay-TestSuite-Test-ID = 'request-response-without-payload-idar03'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method delete
Then status 204
And match header Authorization == '#notpresent'

* def client_token = decode_token(responseHeaders['GovWay-TestSuite-GovWay-Client-Token'][0])
* def server_token = decode_token(responseHeaders['GovWay-TestSuite-GovWay-Server-Token'][0])


* def tid = responseHeaders['GovWay-Transaction-ID'][0]
* call check_traccia ({ tid: tid, tipo: 'Richiesta', token: client_token, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT', profilo_sicurezza: 'IDAR0301'})
* call check_traccia ({ tid: tid, tipo: 'Risposta', token: server_token, x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT', profilo_sicurezza: 'IDAR0301' })

* def tid = responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0]
* call check_traccia ({ tid: tid, tipo: 'Richiesta', token: client_token, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT', profilo_sicurezza: 'IDAR0301' })
* call check_traccia ({ tid: tid, tipo: 'Risposta', token: server_token, x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT', profilo_sicurezza: 'IDAR0301'})


@request-without-payload-digest-richiesta
Scenario: Test di un endpoint che non ha il payload nella richiesta

* def url_invocazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestBlockingIDAR03CRUDDigestRichiesta/v1"

Given url url_invocazione
And path 'resources', 'object', 1
And header GovWay-TestSuite-Test-ID = 'request-without-payload-idar03-digest-richiesta'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method get
Then status 200
And match header Authorization == '#notpresent'
And match response == read('request.json')

* def client_token = decode_token(responseHeaders['GovWay-TestSuite-GovWay-Client-Token'][0])
* def server_token = decode_token(responseHeaders['GovWay-TestSuite-GovWay-Server-Token'][0])

* def response_digest = get server_token $.payload.signed_headers..digest

* def other_checks_risposta = 
"""
([
    { name: 'ProfiloSicurezzaMessaggio-Digest', value: response_digest[0] },
    { name: 'ProfiloSicurezzaMessaggioSignedHeader-digest', value: response_digest[0] },
    { name: 'ProfiloSicurezzaMessaggioSignedHeader-content-type', value: 'application/json' }
])
"""

* def tid = responseHeaders['GovWay-Transaction-ID'][0]
* call check_traccia ({ tid: tid, tipo: 'Richiesta', token: client_token, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT', profilo_sicurezza: "IDAR0301", other_checks: [] })
* call check_traccia ({ tid: tid, tipo: 'Risposta', token: server_token, x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT', profilo_sicurezza: "IDAR0301", other_checks: other_checks_risposta })

* def tid = responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0]
* call check_traccia ({ tid: tid, tipo: 'Richiesta', token: client_token, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT', profilo_sicurezza: "IDAR0301", other_checks: [] })
* call check_traccia ({ tid: tid, tipo: 'Risposta', token: server_token, x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT', profilo_sicurezza: "IDAR0301", other_checks: other_checks_risposta })



@request-response-without-payload-digest-richiesta
Scenario: Test di un endpoint che non ha il payload ne nella richiesta ne nella risposta

* def url_invocazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestBlockingIDAR03CRUDDigestRichiesta/v1"

Given url url_invocazione
And path 'resources', 'object', 1
And header GovWay-TestSuite-Test-ID = 'request-response-without-payload-idar03-digest-richiesta'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method delete
Then status 204
And match header Authorization == '#notpresent'

* def client_token = decode_token(responseHeaders['GovWay-TestSuite-GovWay-Client-Token'][0])
* def server_token = decode_token(responseHeaders['GovWay-TestSuite-GovWay-Server-Token'][0])

* def tid = responseHeaders['GovWay-Transaction-ID'][0]
* call check_traccia ({ tid: tid, tipo: 'Richiesta', token: client_token, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT', profilo_sicurezza: 'IDAR0301' })
* call check_traccia ({ tid: tid, tipo: 'Risposta', token: server_token, x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT', profilo_sicurezza: 'IDAR0301' })

* def tid = responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0]
* call check_traccia ({ tid: tid, tipo: 'Richiesta', token: client_token, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT', profilo_sicurezza: 'IDAR0301' })
* call check_traccia ({ tid: tid, tipo: 'Risposta', token: server_token, x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT', profilo_sicurezza: 'IDAR0301' })