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


# catch all
#
#

#Scenario:
#    karate.fail("Nessuno scenario matchato")
