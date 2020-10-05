Feature: Test del profilo di interazione push non bloccante ModiPA


Background:

* def url_reply_to = "url_erogazione_lato_client"
* def task_id = "fb382380-cf98-4f75-95eb-2a65ba45309e"

* def result = callonce read('classpath:utils/jmx-enable-error-disclosure.feature')
* configure afterFeature = function(){ karate.call('classpath:utils/jmx-disable-error-disclosure.feature'); }

* def url_fruizione_client_validazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestNonBlockingPushServer/v1"
* def url_fruizione_client_no_validazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestNonBlockingPushServerNoValidazione/v1"


@test-ok
Scenario: Giro completo e senza errori

* def url_fruizione_server_validazione = govway_base_path + "/rest/out/DemoSoggettoErogatore/DemoSoggettoFruitore/RestNonBlockingPushClient/v1"

Given url url_fruizione_client_validazione
And path 'resources', 1, 'M'
And header X-ReplyTo = 'url_che_la_fruizione_sostituisce'
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
And match header GovWay-Conversation-ID == task_id


@correlation-id-added-by-server
Scenario: L'erogazione del server deve aggiungere lo header X-Correlation-ID se non inserito dal backend

Given url url_fruizione_client_validazione
And path 'resources', 1, 'M'
And header X-ReplyTo = 'url_che_la_fruizione_sostituisce'
And header GovWay-TestSuite-Test-Id = 'correlation-id-added-by-server'
And request read('client-request.json')
When method post
Then status 202


@iniezione-header-id-collaborazione
Scenario: Aggiunta dello header X-Correlation-ID a partire dall'id collaborazione

# TODO: Considera di abilitare la validazione sulla api
* def url_fruizione_server_helper_collaborazione = govway_base_path + "/rest/out/DemoSoggettoErogatore/DemoSoggettoFruitore/RestNonBlockingPushClientHelperCollaborazioneNoValidazione/v1"

Given url url_fruizione_server_helper_collaborazione
And path 'MResponse'
And header GovWay-Conversation-ID = task_id
And header GovWay-TestSuite-Test-Id = 'iniezione-header-id-collaborazione'
And request read('server-response.json')
When method post
Then status 200
And match response == read('server-response-response.json')


Given url url_fruizione_server_helper_collaborazione
And path 'MResponse'
And header GovWay-TestSuite-Test-Id = 'iniezione-header-id-collaborazione-query'
And request read('server-response.json')
And param govway_conversation_id = task_id
When method post
Then status 200
And match response == read('server-response-response.json')


@iniezione-header-riferimento-id-richiesta
Scenario: Aggiunta dello header X-Correlation-ID a partire dal riferimento id richiesta

# TODO: Considera di abilitare la validazione sulla api
* def url_fruizione_server_helper_riferimento = govway_base_path + "/rest/out/DemoSoggettoErogatore/DemoSoggettoFruitore/RestNonBlockingPushClientHelperRiferimentoNoValidazione/v1"

Given url url_fruizione_server_helper_riferimento
And path 'MResponse'
And header GovWay-Relates-To = task_id
And header GovWay-TestSuite-Test-Id = 'iniezione-header-riferimento-id-richiesta'
And request read('server-response.json')
When method post
Then status 200
And match response == read('server-response-response.json')


Given url url_fruizione_server_helper_riferimento
And path 'MResponse'
And header GovWay-TestSuite-Test-Id = 'iniezione-header-riferimento-id-richiesta-query'
And request read('server-response.json')
And param govway_relates_to = task_id
When method post
Then status 200
And match response == read('server-response-response.json')



@no-correlation-id-in-client-request-response
Scenario: La fruizione del client solleva errore se non trova lo header x-correlation-id

Given url url_fruizione_client_no_validazione
And path 'resources', 1, 'M'
And header X-ReplyTo = 'url_che_la_fruizione_sostituisce'
And header GovWay-TestSuite-Test-Id = 'no-correlation-id-in-client-request-response'
And request read('client-request.json')
When method post
Then status 200



@no-correlation-id-in-server-response-request
Scenario: L'erogazione del client verifica la presenza dello header x-correlation-id
# TODO: matchare anche GovWay-Conversation-ID