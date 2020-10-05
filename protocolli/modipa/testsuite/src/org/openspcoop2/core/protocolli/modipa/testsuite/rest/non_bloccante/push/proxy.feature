Feature: Feature di proxy per i test non bloccante push

Background:

    * def url_erogazione_server_validazione = govway_base_path + "/rest/in/DemoSoggettoErogatore/RestNonBlockingPushServer/v1"
    * def url_erogazione_client_validazione = govway_base_path + "/rest/in/DemoSoggettoFruitore/RestNonBlockingPushClient/v1"

    * def url_erogazione_server = "/rest/in/DemoSoggettoErogatore/RestNonBlockingPushServerNoValidazione/v1"
    * def url_erogazione_client = "/rest/in/DemoSoggettoFruitore/RestNonBlockingPushClientNoValidazione/v1"

    * def isTest = function(id) { return headerContains('GovWay-TestSuite-Test-Id', id) } 

# GIRO OK
#
#

Scenario: isTest('test-ok-richiesta-client')

# Verifico che la fruizione abbia aggiornato lo header X-Reply-To con la url invocazione dell'erogazione lato client
* match requestHeaders['X-ReplyTo'][0] == govway_base_path + "/rest/in/DemoSoggettoFruitore/RestNonBlockingPushClient/v1"
* karate.proceed(url_erogazione_server_validazione)

Scenario: isTest('test-ok-risposta-server')

* karate.proceed(url_erogazione_client_validazione)

# CorrelationID Aggiunto dall'erogazione server
#
#

Scenario: isTest('correlation-id-added-by-server')

* karate.proceed(url_erogazione_server_validazione)
* match responseHeaders['X-Correlation-ID'][0] == responseHeaders['GovWay-Transaction-ID'][0]


# CATCH ALL
#
#
Scenario:

    karate.fail("Nessuno scenario matchato sul proxy")