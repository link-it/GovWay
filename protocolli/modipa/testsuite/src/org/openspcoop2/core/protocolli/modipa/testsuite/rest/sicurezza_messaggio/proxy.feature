Feature: Server proxy per il testing sicurezza messaggio

Background: 

    * def isTest = function(id) { return karate.get("requestHeaders['GovWay-TestSuite-Test-ID'][0]") == id } 
    * def checkToken = read('check-token.feature')

    * def client_token_match = 
    """
    ({
        header: { kid: 'ExampleClient1' },
        payload: { 
            aud: 'testsuite',
            client_id: 'DemoSoggettoFruitore/ApplicativoBlockingIDA01',
            iss: 'DemoSoggettoFruitore',
            sub: 'ApplicativoBlockingIDA01'
        }
    })
    """

    * def tamper_token = 
    """
    function(tok) {
        var prefix = "Bearer"
        tok = tok.slice(prefix.length).trim()
        var components = tok.split('.')
        components[1] = components[1] + 'tamper'
        return 'Bearer ' + components[0] + '.' + components[1] + '.' + components[2]
    }
    """
   
Scenario: isTest('connettivita-base')

    * call checkToken ({token: requestHeaders.Authorization[0], match_to: client_token_match })

    * def url_invocazione_erogazione = govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01/v1'
    * karate.proceed (url_invocazione_erogazione)
    

    * def server_token_match =
    """
    ({
        header: { kid: 'ExampleServer'},
        payload: {
            aud: 'DemoSoggettoFruitore/ApplicativoBlockingIDA01',
            client_id: 'RestBlockingIDAR01/v1',
            iss: 'DemoSoggettoErogatore',
            sub: 'RestBlockingIDAR01/v1'
        }
    })
    """
    * call checkToken ({token: responseHeaders.Authorization[0], match_to: server_token_match  })

    * def newHeaders = 
    """
    ({
        'GovWay-TestSuite-GovWay-Client-Token': requestHeaders.Authorization[0],
        'GovWay-TestSuite-GovWay-Server-Token': responseHeaders.Authorization[0],
    })
    """
    * def responseHeaders = karate.merge(responseHeaders,newHeaders)


Scenario: isTest('connettivita-base-default-trustore')

    * call checkToken ({token: requestHeaders.Authorization[0], match_to: client_token_match })

    * def url_invocazione_erogazione = govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01DefaultTrustore/v1'
    * karate.proceed (url_invocazione_erogazione)

    * def server_token_match =
    """
    ({
        header: { kid: 'ExampleServer'},
        payload: {
            aud: 'DemoSoggettoFruitore/ApplicativoBlockingIDA01',
            client_id: 'RestBlockingIDAR01DefaultTrustore/v1',
            iss: 'DemoSoggettoErogatore',
            sub: 'RestBlockingIDAR01DefaultTrustore/v1'
        }
    })
    """

    * call checkToken ({token: responseHeaders.Authorization[0], match_to: server_token_match  })

    * def newHeaders = 
    """
    ({
        'GovWay-TestSuite-GovWay-Client-Token': requestHeaders.Authorization[0],
        'GovWay-TestSuite-GovWay-Server-Token': responseHeaders.Authorization[0],
    })
    """
    * def responseHeaders = karate.merge(responseHeaders,newHeaders)


Scenario: isTest('connettivita-base-no-sbustamento')
    

    # Testo il token del client sulla feature di mock e il token del server sulla feature client (idar01.feature)
    
    * def url_invocazione_erogazione = govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01DefaultTrustoreNoSbustamento/v1'
    * karate.proceed (url_invocazione_erogazione)

    * def newHeaders = 
    """
    ({
        'GovWay-TestSuite-GovWay-Client-Token': requestHeaders.Authorization[0],
        'GovWay-TestSuite-GovWay-Server-Token': responseHeaders.Authorization[0],
    })
    """
    * def responseHeaders = karate.merge(responseHeaders,newHeaders)


Scenario: isTest('connettivita-base-truststore-ca')

    * call checkToken ({token: requestHeaders.Authorization[0], match_to: client_token_match })

    * def url_invocazione_erogazione = govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01TrustStoreCA/v1'
    * karate.proceed (url_invocazione_erogazione)
    

    * def server_token_match =
    """
    ({
        header: { kid: 'ExampleServer'},
        payload: {
            aud: 'DemoSoggettoFruitore/ApplicativoBlockingIDA01',
            client_id: 'RestBlockingIDAR01TrustStoreCA/v1',
            iss: 'DemoSoggettoErogatore',
            sub: 'RestBlockingIDAR01TrustStoreCA/v1'
        }
    })
    """
    * call checkToken ({token: responseHeaders.Authorization[0], match_to: server_token_match  })

    * def newHeaders = 
    """
    ({
        'GovWay-TestSuite-GovWay-Client-Token': requestHeaders.Authorization[0],
        'GovWay-TestSuite-GovWay-Server-Token': responseHeaders.Authorization[0],
    })
    """
    * def responseHeaders = karate.merge(responseHeaders,newHeaders)


Scenario: isTest('response-without-payload') || isTest('request-without-payload')

    * call checkToken ({token: requestHeaders.Authorization[0], match_to: client_token_match })

    * def url_invocazione_erogazione = govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01CRUD/v1'
    * karate.proceed(url_invocazione_erogazione)

    * def server_token_match =
    """
    ({
        header: { kid: 'ExampleServer'},
        payload: {
            aud: 'DemoSoggettoFruitore/ApplicativoBlockingIDA01',
            client_id: 'RestBlockingIDAR01CRUD/v1',
            iss: 'DemoSoggettoErogatore',
            sub: 'RestBlockingIDAR01CRUD/v1'
        }
    })
    """
    * call checkToken ({token: responseHeaders.Authorization[0], match_to: server_token_match  })

    * def newHeaders = 
    """
    ({
        'GovWay-TestSuite-GovWay-Client-Token': requestHeaders.Authorization[0],
        'GovWay-TestSuite-GovWay-Server-Token': responseHeaders.Authorization[0],
    })
    """
    * def responseHeaders = karate.merge(responseHeaders,newHeaders)

Scenario: isTest('request-response-without-payload')
    
    * call checkToken ({token: requestHeaders.Authorization[0], match_to: client_token_match })

    * def url_invocazione_erogazione = govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01CRUD/v1'
    * karate.proceed(url_invocazione_erogazione)

    * def server_token_match =
    """
    ({
        header: { kid: 'ExampleServer'},
        payload: {
            aud: 'DemoSoggettoFruitore/ApplicativoBlockingIDA01',
            client_id: 'RestBlockingIDAR01CRUD/v1',
            iss: 'DemoSoggettoErogatore',
            sub: 'RestBlockingIDAR01CRUD/v1'
        }
    })
    """
    * call checkToken ({token: responseHeaders.Authorization[0], match_to: server_token_match  })

    * def newHeaders = 
    """
    ({
        'GovWay-TestSuite-GovWay-Client-Token': requestHeaders.Authorization[0],
        'GovWay-TestSuite-GovWay-Server-Token': responseHeaders.Authorization[0],
    })
    """
    * def responseHeaders = karate.merge(responseHeaders,newHeaders)


Scenario: isTest('disabled-security-on-action')

    * match requestHeaders.Authorization == "#notpresent"

    * def url_invocazione_erogazione = govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01CRUD/v1'
    * karate.proceed(url_invocazione_erogazione)

    * match responseHeaders.Authorization == "#notpresent"


Scenario: isTest('enabled-security-on-action')

    * call checkToken ({token: requestHeaders.Authorization[0], match_to: client_token_match })

    # Cambia questo
    * def url_invocazione_erogazione = govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01CRUDNoDefaultSecurity/v1' 

    * karate.proceed (url_invocazione_erogazione)
    
    # Cambia il sub
    * def server_token_match =
    """
    ({
        header: { kid: 'ExampleServer'},
        payload: {
            aud: 'DemoSoggettoFruitore/ApplicativoBlockingIDA01',
            client_id: 'RestBlockingIDAR01CRUDNoDefaultSecurity/v1',
            iss: 'DemoSoggettoErogatore',
            sub: 'RestBlockingIDAR01CRUDNoDefaultSecurity/v1'
        }
    })
    """
    
    * call checkToken ({token: responseHeaders.Authorization[0], match_to: server_token_match  })

    * def newHeaders = 
    """
    ({
        'GovWay-TestSuite-GovWay-Client-Token': requestHeaders.Authorization[0],
        'GovWay-TestSuite-GovWay-Server-Token': responseHeaders.Authorization[0],
    })
    """
    * def responseHeaders = karate.merge(responseHeaders,newHeaders)


Scenario: isTest('riferimento-x509-x5u-x5t')

    * def client_token_match = 
    """
    ({
        header: { 
            kid: 'ExampleClient1',
            x5c: '#notpresent',
            x5u: 'http://localhost:8080/ExampleClient1.crt'
        },
        payload: { 
            aud: 'testsuite',
            client_id: 'DemoSoggettoFruitore/ApplicativoBlockingIDA01',
            iss: 'DemoSoggettoFruitore',
            sub: 'ApplicativoBlockingIDA01'
        }
    })
    """

    * call checkToken ({token: requestHeaders.Authorization[0], match_to: client_token_match })

    * karate.proceed (govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01X5T/v1')
    * match responseStatus == 200
    
    * def server_token_match =
    """
    ({
        header: { 
            kid: 'ExampleServer',
            x5c: '#notpresent',
            x5u: '#notpresent',
            'x5t#S256': '#present'
        },
        payload: {
            aud: 'DemoSoggettoFruitore/ApplicativoBlockingIDA01',
            client_id: 'RestBlockingIDAR01X5T/v1',
            iss: 'DemoSoggettoErogatore',
            sub: 'RestBlockingIDAR01X5T/v1'
        }
    })
    """
    
    * call checkToken ({token: responseHeaders.Authorization[0], match_to: server_token_match  })

    * def newHeaders = 
    """
    ({
        'GovWay-TestSuite-GovWay-Client-Token': requestHeaders.Authorization[0],
        'GovWay-TestSuite-GovWay-Server-Token': responseHeaders.Authorization[0],
    })
    """
    * def responseHeaders = karate.merge(responseHeaders,newHeaders)


Scenario: isTest('riferimento-x509-x5t-x5u')

    * def client_token_match = 
    """
    ({
        header: { 
            kid: 'ExampleClient1',
            x5c: '#notpresent',
            x5u: '#notpresent',
            'x5t#S256': '#present'
        },
        payload: { 
            aud: 'testsuite',
            client_id: 'DemoSoggettoFruitore/ApplicativoBlockingIDA01',
            iss: 'DemoSoggettoFruitore',
            sub: 'ApplicativoBlockingIDA01'
        }
    })
    """

    * call checkToken ({token: requestHeaders.Authorization[0], match_to: client_token_match })

    * karate.proceed (govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01X5T-X5U/v1')
    * match responseStatus == 200
    
    * def server_token_match =
    """
    ({
        header: { 
            kid: 'ExampleServer',
            x5c: '#notpresent',
            x5u: 'http://localhost:8080/ExampleServer.crt'
        },
        payload: {
            aud: 'DemoSoggettoFruitore/ApplicativoBlockingIDA01',
            client_id: 'RestBlockingIDAR01X5T-X5U/v1',
            iss: 'DemoSoggettoErogatore',
            sub: 'RestBlockingIDAR01X5T-X5U/v1'
        }
    })
    """
    
    * call checkToken ({token: responseHeaders.Authorization[0], match_to: server_token_match  })

    * def newHeaders = 
    """
    ({
        'GovWay-TestSuite-GovWay-Client-Token': requestHeaders.Authorization[0],
        'GovWay-TestSuite-GovWay-Server-Token': responseHeaders.Authorization[0],
    })
    """
    * def responseHeaders = karate.merge(responseHeaders,newHeaders)

Scenario: isTest('riferimento-x509-x5cx5t-x5cx5t')

    * def client_token_match = 
    """
    ({
        header: { 
            kid: 'ExampleClient1',
            x5c: '#present',
            x5u: '#notpresent',
            'x5t#S256': '#present'
        },
        payload: { 
            aud: 'testsuite',
            client_id: 'DemoSoggettoFruitore/ApplicativoBlockingIDA01',
            iss: 'DemoSoggettoFruitore',
            sub: 'ApplicativoBlockingIDA01'
        }
    })
    """

    * call checkToken ({token: requestHeaders.Authorization[0], match_to: client_token_match })

    * karate.proceed (govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01X5CX5T-X5CX5T/v1')
    * match responseStatus == 200
    
    * def server_token_match =
    """
    ({
        header: { 
            kid: 'ExampleServer',
            x5c: '#present',
            x5u: '#notpresent',
            'x5t#S256': '#present'
        },
        payload: {
            aud: 'DemoSoggettoFruitore/ApplicativoBlockingIDA01',
            client_id: 'RestBlockingIDAR01X5CX5T-X5CX5T/v1',
            iss: 'DemoSoggettoErogatore',
            sub: 'RestBlockingIDAR01X5CX5T-X5CX5T/v1'
        }
    })
    """
    
    * call checkToken ({token: responseHeaders.Authorization[0], match_to: server_token_match  })

    * def newHeaders = 
    """
    ({
        'GovWay-TestSuite-GovWay-Client-Token': requestHeaders.Authorization[0],
        'GovWay-TestSuite-GovWay-Server-Token': responseHeaders.Authorization[0],
    })
    """
    * def responseHeaders = karate.merge(responseHeaders,newHeaders)

Scenario: isTest('no-token-fruizione')

    # Rispondo direttamente senza passare dall'erogazione, non generando nessun token
    * def responseStatus = 200
    * def response = read('classpath:test/rest/sicurezza-messaggio/response.json')


Scenario: isTest('manomissione-payload-risposta')

    * karate.proceed (govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01/v1')
    * match responseStatus == 200

    * set response.nuovo_campo = "pippa"


Scenario: isTest('manomissione-payload-richiesta')

    * def c = request
    * set c.nuovo_campo = "pippa"

    * karate.proceed(govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01/v1')
    * match responseStatus == 400

Scenario: isTest('low-ttl-fruizione')

    * java.lang.Thread.sleep(2000)

    * karate.proceed(govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01/v1')
    * match responseStatus == 400
    * match response == read('classpath:test/rest/sicurezza-messaggio/error-bodies/ttl-scaduto-in-request.json')
    * match header GovWay-Transaction-ErrorType == 'InteroperabilityInvalidRequest'


Scenario: isTest('low-ttl-fruizione-no-disclosure')

    * java.lang.Thread.sleep(2000)

    * karate.proceed(govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01/v1')
    * match responseStatus == 400
    * match response == read('classpath:test/rest/sicurezza-messaggio/error-bodies/interoperability-invalid-request.json')
    * match header GovWay-Transaction-ErrorType == 'InteroperabilityInvalidRequest'


Scenario: isTest('low-ttl-erogazione')
    
    * karate.proceed(govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01LowTTL/v1')
    * match responseStatus == 200
    * java.lang.Thread.sleep(2000)


Scenario: isTest('manomissione-token-richiesta')

    * set requestHeaders['Authorization'][0] = tamper_token(requestHeaders['Authorization'][0])
    * karate.proceed(govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01NoValidazione/v1')
    * match responseStatus == 400
    * match response == read('classpath:test/rest/sicurezza-messaggio/error-bodies/invalid-token-signature-in-request.json')
    * match header GovWay-Transaction-ErrorType == 'InteroperabilityInvalidRequest'


Scenario: isTest('manomissione-token-richiesta-no-disclosure')

    * set requestHeaders['Authorization'][0] = tamper_token(requestHeaders['Authorization'][0])
    * karate.proceed(govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01NoValidazione/v1')
    * match responseStatus == 400
    * match response == read('classpath:test/rest/sicurezza-messaggio/error-bodies/interoperability-invalid-request.json')
    * match header GovWay-Transaction-ErrorType == 'InteroperabilityInvalidRequest'


Scenario: isTest('manomissione-token-risposta')

    * karate.proceed(govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01NoValidazione/v1')
    
    * set responseHeaders['Authorization'][0] = tamper_token(responseHeaders['Authorization'][0])

# TODO: Cambiare il nome dell'erogazione da autenticazionepuntuale a autorizzazionepuntuale
Scenario: isTest('applicativo-non-autorizzato')

    * karate.proceed(govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01AutenticazionePuntuale/v1')


##############################
#           IDAR02
##############################

Scenario: isTest('connettivita-base-idar02')

    * def client_token_match = 
    """
    ({
        header: { kid: 'ExampleClient1' },
        payload: { 
            aud: 'testsuite',
            client_id: 'DemoSoggettoFruitore/ApplicativoBlockingIDA01',
            iss: 'DemoSoggettoFruitore',
            sub: 'ApplicativoBlockingIDA01',
            jti: '#uuid'
        }
    })
    """

    * call checkToken ({token: requestHeaders.Authorization[0], match_to: client_token_match })

    * karate.proceed (govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR02/v1')    

    * def server_token_match =
    """
    ({
        header: { kid: 'ExampleServer'},
        payload: {
            aud: 'DemoSoggettoFruitore/ApplicativoBlockingIDA01',
            client_id: 'RestBlockingIDAR02/v1',
            iss: 'DemoSoggettoErogatore',
            sub: 'RestBlockingIDAR02/v1',
            jti: '#uuid'
        }
    })
    """
    * call checkToken ({token: responseHeaders.Authorization[0], match_to: server_token_match  })

    * def newHeaders = 
    """
    ({
        'GovWay-TestSuite-GovWay-Client-Token': requestHeaders.Authorization[0],
        'GovWay-TestSuite-GovWay-Server-Token': responseHeaders.Authorization[0],
    })
    """
    * def responseHeaders = karate.merge(responseHeaders,newHeaders)


Scenario: isTest('riutilizzo-token')

    * karate.proceed (govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR02/v1')    
    
    * def newHeaders = 
    """
    ({
        'GovWay-TestSuite-GovWay-Client-Token': requestHeaders.Authorization[0],
        'GovWay-TestSuite-GovWay-Server-Token': responseHeaders.Authorization[0],
    })
    """
    * def responseHeaders = karate.merge(responseHeaders,newHeaders)

Scenario: isTest('riutilizzo-token-risposta')

    * def responseHeaders =  ({ 'Authorization': requestHeaders['GovWay-TestSuite-Server-Token'][0] })
    * def responseStatus = 200
    * def response = read('classpath:test/rest/sicurezza-messaggio/response.json')

# catch all
#
#

#Scenario:
#    karate.fail("Nessuno scenario matchato")
