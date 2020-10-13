Feature: Server mock per il testing della sicurezza messaggio

Background:
    * def isTest = function(id) { return headerContains('GovWay-TestSuite-Test-ID', id) } 

    * def confHeaders = 
    """
    function() { 
        return {
            'GovWay-TestSuite-GovWay-Transaction-ID': karate.get("requestHeaders['GovWay-Transaction-ID'][0]")
        }
    }
    """

    * configure responseHeaders = confHeaders


Scenario: isTest('connettivita-base') || isTest('connettivita-base-default-trustore')
    # Controllo che al server non siano arrivate le informazioni di sicurezza
    * match requestHeaders['Authorization'] == '#notpresent'
    * def responseStatus = 200
    * def response = read('classpath:test/rest/sicurezza-messaggio/response.json')


# catch all
#
#

Scenario:
    karate.fail("Nessuno scenario matchato")