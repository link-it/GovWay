Feature: Feature di proxy per i test non bloccante push

Background:

    * def url_erogazione_server_validazione = govway_base_path + "/rest/in/DemoSoggettoErogatore/RestNonBlockingPushServer/v1"
    * def url_erogazione_client_validazione = govway_base_path + "/rest/in/DemoSoggettoFruitore/RestNonBlockingPushClient/v1"

    * def url_erogazione_server = "/rest/in/DemoSoggettoErogatore/RestNonBlockingPushServerNoValidazione/v1"
    * def url_erogazione_client = "/rest/in/DemoSoggettoFruitore/RestNonBlockingPushClientNoValidazione/v1"

    * def isTest = function(id) { return headerContains('GovWay-TestSuite-Test-Id', id) } 

Scenario: isTest('test-ok-richiesta-client')

* karate.proceed(url_erogazione_server_validazione)

Scenario: isTest('test-ok-risposta-server')

* karate.proceed(url_erogazione_client_validazione)


# CATCH ALL
#
#
Scenario:

    karate.fail("Nessuno scenario matchato sul proxy")
