Feature: Server mock per il testing della sicurezza messaggio

Background:
    * def isTest = function(id) { return karate.get("requestHeaders['GovWay-TestSuite-Test-ID'][0]") == id } 
    * def checkToken = read('check-token.feature')

    * def confHeaders = 
    """
    function() { 
        return {
            'GovWay-TestSuite-GovWay-Transaction-ID': karate.get("requestHeaders['GovWay-Transaction-ID'][0]"),
            'Connection': 'close'
        }
    }
    """

    * configure responseHeaders = confHeaders


Scenario: isTest('connettivita-base') || isTest('connettivita-base-default-trustore') || isTest('connettivita-base-truststore-ca') || isTest('disabled-security-on-action') || isTest('enabled-security-on-action') || isTest('riferimento-x509-x5u-x5t') || isTest('riferimento-x509-x5t-x5u') || isTest('riferimento-x509-x5cx5t-x5cx5t') || isTest('manomissione-payload-risposta') || isTest('low-ttl-erogazione') || isTest('manomissione-token-risposta') || isTest('connettivita-base-idar02') || isTest('riutilizzo-token')
    
    # Controllo che al server non siano arrivate le informazioni di sicurezza
    * match requestHeaders['Authorization'] == '#notpresent'
    * def responseStatus = 200
    * def response = read('classpath:test/rest/sicurezza-messaggio/response.json')


Scenario: isTest('applicativo-non-autorizzato')

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


Scenario: isTest('response-without-payload')
    * match requestHeaders['Authorization'] == '#notpresent'
    * def responseStatus = 201
    * def response = {}


Scenario: isTest('request-without-payload')
    * match requestHeaders['Authorization'] == '#notpresent'
    * def responseStatus = 200
    * def response = read('classpath:test/rest/sicurezza-messaggio/request.json')


Scenario: isTest('request-response-without-payload')
    * match requestHeaders['Authorization'] == '#notpresent'
    * def responseStatus = 204
    * def response = {}

# catch all
#
#

Scenario:
    karate.fail("Nessuno scenario matchato")