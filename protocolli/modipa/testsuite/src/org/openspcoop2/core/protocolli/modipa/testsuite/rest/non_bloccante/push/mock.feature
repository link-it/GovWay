Feature: Feature di mock per i test non bloccante push

Background:

    * def isTest = function(id) { return headerContains('GovWay-TestSuite-Test-Id', id) } 

# GIRO OK
#
#

Scenario: isTest('test-ok-richiesta-client')

    # Verifico che l'erogazione del server abbia aggiornato lo header X-Reply-To con la url invocazione della fruizione del server
    # verso l'erogazione del client.

    * match requestHeaders['X-ReplyTo'][0] == govway_base_path + "/rest/out/DemoSoggettoErogatore/DemoSoggettoFruitore/RestNonBlockingPushClient/v1"
    * def responseHeaders = { 'X-Correlation-ID': "fb382380-cf98-4f75-95eb-2a65ba45309e" }
    * def responseStatus = 202
    * def response = { 'outcome': 'accepted' }

Scenario: isTest('test-ok-risposta-server')

    * def responseStatus = 200
    * def response = read('classpath:src/test/rest/non-bloccante/push/server-response-response.json')
    

# CorrelationID Aggiunto dall'erogazione server
#
# Invio solo il messaggio di ack senza il correlation ID che verrà aggiunto dall'erogazione server

Scenario: isTest('correlation-id-added-by-server')

    * def responseStatus = 202
    * def response = { 'outcome': 'accepted' }