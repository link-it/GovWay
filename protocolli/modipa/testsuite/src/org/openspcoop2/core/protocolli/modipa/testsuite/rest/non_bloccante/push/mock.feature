Feature: Feature di mock per i test non bloccante push

Background:

    * def isTest = function(id) { return headerContains('GovWay-TestSuite-Test-Id', id) } 

# GIRO OK
#
#

Scenario: isTest('test-ok-richiesta-client')

    * def responseHeaders = { 'X-Correlation-ID': "fb382380-cf98-4f75-95eb-2a65ba45309e" }
    * def responseStatus = 202
    * def response = { 'outcome': 'accepted' }

Scenario: isTest('test-ok-risposta-server')

    * def responseStatus = 200
    * def response = read('classpath:src/test/rest/non-bloccante/push/server-response-response.json')