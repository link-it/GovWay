Feature: Server proxy per il testing sicurezza messaggio

Background: 

    # TODO: usa Gov...Test-ID anzichè Gov...Test-Id anche negli altri test
    * def isTest = function(id) { return headerContains('GovWay-TestSuite-Test-ID', id) } 


Scenario: isTest('connettivita-base')
    * def url_invocazione_erogazione = govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01/v1'
    
    * karate.proceed (url_invocazione_erogazione)


# catch all
#
#

Scenario:
    karate.fail("Nessuno scenario matchato")
