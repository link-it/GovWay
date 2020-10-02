Feature: Test del profilo di interazione push non bloccante ModiPA


Background:

* def url_reply_to = "url_erogazione_lato_client"
* def task_id = "fb382380-cf98-4f75-95eb-2a65ba45309e"

* def result = callonce read('classpath:utils/jmx-enable-error-disclosure.feature')
* configure afterFeature = function(){ karate.call('classpath:utils/jmx-disable-error-disclosure.feature'); }


@test-ok
Scenario: Giro completo e senza errori

* def url_fruizione_client_validazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestNonBlockingPushServer/v1"
* def url_fruizione_server_validazione = govway_base_path + "/rest/out/DemoSoggettoErogatore/DemoSoggettoFruitore/RestNonBlockingPushClient/v1"

Given url url_fruizione_client_validazione
And path 'resources', 1, 'M'
And header X-ReplyTo = url_fruizione_server_validazione
And header GovWay-TestSuite-Test-Id = 'test-ok-richiesta-client'
And request read('client-request.json')
When method post
Then status 202
And match header X-Correlation-ID == task_id
And match header GovWay-Conversation-ID == task_id

Given url url_fruizione_server_validazione
And path 'MResponse'
And header X-Correlation-ID = task_id
And header GovWay-TestSuite-Test-Id = 'test-ok-risposta-server'
And request read('server-response.json')
When method post
Then status 200
And match response == read('server-response-response.json')
# Devo matchare anche qui lo header GovWay-Conversation-ID?


@correllation-id-server
Scenario: L'erogazione del server deve aggiungere lo header X-Correlation-ID se non inserito dal backend


@iniezione-header-id-collaborazione
Scenario: Aggiunta dello header X-Correlation-ID a partire dall'id collaborazione


@iniezione-header-riferimento-id-richiesta
Scenario: Aggiunta dello header X-Correlation-ID a partire dal riferimento id richiesta


@modifica-header-x-reply-to
Scenario: Verifica che la fruizione lato client modifichi lo x-reply-to


@no-correlation-id-in-client-request-response
Scenario: La fruizione del client solleva errore se non trova lo header x-correlation-id


@no-correlation-id-in-server-response-request
Scenario: L'erogazione del client verifica la presenza dello header x-correlation-id
# TODO: matchare anche GovWay-Conversation-ID