@parallel=false
Feature: Testing Sicurezza Messaggio ModiPA IDAR01

Background:
    * def basic = read('classpath:utils/basic-auth.js')
    * def check_traccia = read('check-tracce/check-traccia.feature')
    * def decode_token = read('classpath:utils/decode-token.js')
    * def checkToken = read('classpath:org/openspcoop2/core/protocolli/modipa/testsuite/rest/sicurezza_messaggio/check-token.feature')


@karate
@karate-proxy-post
Scenario: 

* def url_invocazione = "http://localhost:8090"

Given url url_invocazione
And path 'resources', 1, 'M'
And request read('request.json')
And header GovWay-TestSuite-Test-ID = 'karate-proxy-post'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method post
Then status 200
And match response == read('response.json')

@karate
@karate-proxy-get
Scenario:

* def url_invocazione = "http://localhost:8090"

Given url url_invocazione
And path 'resources', 'object', 1
And header GovWay-TestSuite-Test-ID = 'karate-proxy-get'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method get
Then status 200
And match response == read('request.json')

@karate
@karate-proxy-delete
Scenario:

* def url_invocazione = "http://localhost:8090"

Given url url_invocazione
And path 'resources', 'object', 1
And header GovWay-TestSuite-Test-ID = 'karate-proxy-delete'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method delete
Then status 204


@connettivita-base
Scenario: Test connettività base

* def url_invocazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestBlockingIDAR01/v1"

Given url url_invocazione
And path 'resources', 1, 'M'
And request read('request.json')
And header GovWay-TestSuite-Test-ID = 'connettivita-base'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method post
Then status 200
And match response == read('response.json')
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


@connettivita-base-default-trustore
Scenario: Test connettività base con trustore e keystore di default

* def url_invocazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestBlockingIDAR01DefaultTrustore/v1"

Given url url_invocazione
And path 'resources', 1, 'M'
And request read('request.json')
And header GovWay-TestSuite-Test-ID = 'connettivita-base-default-trustore'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method post
Then status 200
And match response == read('response.json')
And match header Authorization == '#notpresent'

* def client_token = decode_token(responseHeaders['GovWay-TestSuite-GovWay-Client-Token'][0])
* def server_token = decode_token(responseHeaders['GovWay-TestSuite-GovWay-Server-Token'][0])

* def tid = responseHeaders['GovWay-Transaction-ID'][0]
* call check_traccia ({ tid: tid, tipo: 'Richiesta', token: client_token, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT' })
* call check_traccia ({ tid: tid, tipo: 'Risposta', token: server_token, x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT' })

* def tid = responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0]
* call check_traccia ({ tid: tid, tipo: 'Richiesta', token: client_token, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT' })
* call check_traccia ({ tid: tid, tipo: 'Risposta', token: server_token, x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT' })


@connettivita-base-no-sbustamento
Scenario: Test connettività base senza sbustamento modipa

* def url_invocazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestBlockingIDAR01DefaultTrustore/v1"

Given url url_invocazione
And path 'resources', 1, 'M'
And request read('request.json')
And header GovWay-TestSuite-Test-ID = 'connettivita-base-no-sbustamento'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01NoSbustamento', password: 'ApplicativoBlockingIDA01NoSbustamento' })
When method post
Then status 200
And match response == read('response.json')
And match header Authorization == '#notnull'

* def server_token_match =
"""
({
    header: { kid: 'ExampleServer'},
    payload: {
        aud: 'DemoSoggettoFruitore/ApplicativoBlockingIDA01NoSbustamento',
        client_id: 'RestBlockingIDAR01DefaultTrustoreNoSbustamento/v1',
        iss: 'DemoSoggettoErogatore',
        sub: 'RestBlockingIDAR01DefaultTrustoreNoSbustamento/v1'
    }
})
"""

* call checkToken ({token: responseHeaders.Authorization[0], match_to: server_token_match  })

* def client_token = decode_token(responseHeaders['GovWay-TestSuite-GovWay-Client-Token'][0])
* def server_token = decode_token(responseHeaders['GovWay-TestSuite-GovWay-Server-Token'][0])

* def tid = responseHeaders['GovWay-Transaction-ID'][0]
* call check_traccia ({ tid: tid, tipo: 'Richiesta', token: client_token, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT' })
* call check_traccia ({ tid: tid, tipo: 'Risposta', token: server_token, x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT' })

* def tid = responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0]
* call check_traccia ({ tid: tid, tipo: 'Richiesta', token: client_token, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT' })
* call check_traccia ({ tid: tid, tipo: 'Risposta', token: server_token, x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT' })



@connettivita-base-truststore-ca
Scenario: Test connettività base con erogazione e fruizione che usano il trustore delle CA

* def url_invocazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestBlockingIDAR01TrustStoreCA/v1"

Given url url_invocazione
And path 'resources', 1, 'M'
And request read('request.json')
And header GovWay-TestSuite-Test-ID = 'connettivita-base-truststore-ca'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method post
Then status 200
And match response == read('response.json')
And match header Authorization == '#notpresent'


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


@response-without-payload
Scenario: Test di un endpoint che non ha il payload nella risposta

* def url_invocazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestBlockingIDAR01CRUD/v1"

Given url url_invocazione
And path 'resources', 'object'
And request read('request.json')
And header GovWay-TestSuite-Test-ID = 'response-without-payload'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method post
Then status 201
And match header Authorization == '#notpresent'

* def client_token = decode_token(responseHeaders['GovWay-TestSuite-GovWay-Client-Token'][0])
* def server_token = decode_token(responseHeaders['GovWay-TestSuite-GovWay-Server-Token'][0])

* def tid = responseHeaders['GovWay-Transaction-ID'][0]
* call check_traccia ({ tid: tid, tipo: 'Richiesta', token: client_token, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT' })
* call check_traccia ({ tid: tid, tipo: 'Risposta', token: server_token, x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT' })

* def tid = responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0]
* call check_traccia ({ tid: tid, tipo: 'Richiesta', token: client_token, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT' })
* call check_traccia ({ tid: tid, tipo: 'Risposta', token: server_token, x509sub: 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT' })
