Feature: Server proxy per il testing sicurezza messaggio

Background: 

    # TODO: usa Gov...Test-ID anzichè Gov...Test-Id anche negli altri test
    * def isTest = function(id) { return headerContains('GovWay-TestSuite-Test-ID', id) } 
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


# catch all
#
#

Scenario:
    karate.fail("Nessuno scenario matchato")
