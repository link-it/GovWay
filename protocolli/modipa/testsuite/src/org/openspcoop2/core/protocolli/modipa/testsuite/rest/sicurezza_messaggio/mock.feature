Feature: Server mock per il testing della sicurezza messaggio

Background:
    * def isTest = function(id) { return headerContains('GovWay-TestSuite-Test-ID', id) } 
    * def checkToken = read('check-token.feature')

    * def confHeaders = 
    """
    function() { 
        return {
            'GovWay-TestSuite-GovWay-Transaction-ID': karate.get("requestHeaders['GovWay-Transaction-ID'][0]")
        }
    }
    """

    * configure responseHeaders = confHeaders


Scenario: isTest('connettivita-base') || isTest('connettivita-base-default-trustore') || isTest('connettivita-base-truststore-ca')
    
    # Controllo che al server non siano arrivate le informazioni di sicurezza
    * match requestHeaders['Authorization'] == '#notpresent'
    * def responseStatus = 200
    * def response = read('classpath:test/rest/sicurezza-messaggio/response.json')



Scenario: isTest('connettivita-base-no-sbustamento')
    # Controllo che al backend siano arrivate le informazioni di sicurezza

    * def client_token_match = 
    """
    ({
        header: { kid: 'ExampleClient1' },
        payload: { 
            aud: 'testsuite',
            client_id: 'DemoSoggettoFruitore/ApplicativoBlockingIDA01NoSbustamento',
            iss: 'DemoSoggettoFruitore',
            sub: 'ApplicativoBlockingIDA01NoSbustamento'
        }
    })
    """
    * call checkToken ({token: requestHeaders.Authorization[0], match_to: client_token_match })

    * def responseStatus = 200
    * def response = read('classpath:test/rest/sicurezza-messaggio/response.json')


# catch all
#
#

Scenario:
    karate.fail("Nessuno scenario matchato")