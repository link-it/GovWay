Feature: Server mock per il testing della sicurezza messaggio

Background:
    * def isTest = function(id) { return headerContains('GovWay-TestSuite-Test-ID', id) } 

    * def confHeaders = 
    """
    function() { 
        return {
            'Content-type': "application/soap+xml",
            'GovWay-TestSuite-GovWay-Transaction-ID': karate.get("requestHeaders['GovWay-Transaction-ID'][0]")
        }
    }
    """

    * configure responseHeaders = confHeaders


Scenario: isTest('connettivita-base') || isTest('connettivita-base-default-trustore')
    * def responseStatus = 200
    * def response = read('classpath:test/soap/sicurezza-messaggio/response.xml')


# catch all
#
#

Scenario:
    karate.fail("Nessuno scenario matchato")