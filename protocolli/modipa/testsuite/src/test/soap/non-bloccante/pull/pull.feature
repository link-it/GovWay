Feature: Test Profilo Non Bloccante Pull

Background:

* def url_validazione = govway_base_path + "/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/NonBlockingSoapPullProxy/v1"
* def url_no_validazione = govway_base_path + "/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/NonBlockingSoapPullNoValidazioneProxy/v1"
* def url_helper_headers = govway_base_path + "/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/NonBlockingSoapPullHelperHeadersNoValidazioneProxy/v1"

* configure headers = { 'Content-Type': 'application/soap+xml', 'action': url_validazione }
* def invalid_response = read("invalid-response.xml")

* def result = callonce read('classpath:utils/jmx-enable-error-disclosure.feature')
* configure afterFeature = function(){ karate.call('classpath:utils/jmx-disable-error-disclosure.feature'); }

* def check_traccia_richiesta = read('./check-tracce/richiesta.feature')
* def check_traccia_richiesta_stato = read('./check-tracce/richiesta-stato.feature')
* def check_traccia_risposta = read('./check-tracce/risposta.feature')
* def check_id_collaborazione = read('./check-tracce/id-collaborazione.feature')

@test-ok
Scenario: Giro OK

    * def task_id = "d2f49459-1624-4710-b80c-15e33d64b608"
    * def task_id_not_ready = 'd2f49459-1624-4710-b80c-15e33d64b608_NOT_READY'

    Given url url_validazione
    And request read("richiesta-applicativa.xml")
    When method post
    Then status 200
    And match response == read("richiesta-applicativa-response.xml")
    And match header GovWay-Conversation-ID == task_id

    * call check_traccia_richiesta ({tid: responseHeaders['GovWay-Transaction-ID'][0]})
    * call check_traccia_richiesta ({tid: responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0]})

    * call check_id_collaborazione ({tid: responseHeaders['GovWay-Transaction-ID'][0], id_collaborazione: task_id })
    * call check_id_collaborazione ({tid: responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0], id_collaborazione: task_id })

    Given url url_validazione
    And request read("richiesta-stato-not-ready.xml")
    When method post
    Then status 200
    And match response == read("richiesta-stato-not-ready-response.xml")
    And match header GovWay-Conversation-ID == task_id_not_ready

    * call check_traccia_richiesta_stato ({tid: responseHeaders['GovWay-Transaction-ID'][0], cid: task_id_not_ready })
    * call check_traccia_richiesta_stato ({tid: responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0], cid: task_id_not_ready })

    * call check_id_collaborazione ({tid: responseHeaders['GovWay-Transaction-ID'][0], id_collaborazione: task_id_not_ready })
    * call check_id_collaborazione ({tid: responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0], id_collaborazione: task_id_not_ready })


    Given url url_validazione
    And request read("richiesta-stato-ready.xml")
    When method post
    Then status 200
    And match response == read("richiesta-stato-ready-response.xml")
    And match header GovWay-Conversation-ID == task_id

    * call check_traccia_richiesta_stato ({tid: responseHeaders['GovWay-Transaction-ID'][0], cid: task_id })
    * call check_traccia_richiesta_stato ({tid: responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0], cid: task_id })

    * call check_id_collaborazione ({tid: responseHeaders['GovWay-Transaction-ID'][0], id_collaborazione: task_id })
    * call check_id_collaborazione ({tid: responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0], id_collaborazione: task_id })

    Given url url_validazione
    And request read("recupero-risposta.xml")
    When method post
    Then status 200
    And match response == read("recupero-risposta-response.xml")
    And match header GovWay-Conversation-ID == task_id

    * call check_traccia_risposta ({tid: responseHeaders['GovWay-Transaction-ID'][0], cid: task_id })
    * call check_traccia_risposta ({tid: responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0], cid: task_id })

    * call check_id_collaborazione ({tid: responseHeaders['GovWay-Transaction-ID'][0], id_collaborazione: task_id })
    * call check_id_collaborazione ({tid: responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0], id_collaborazione: task_id })


@generazione-header-conversation-id
Scenario: Verifica che la erogazione generi lo header GovWay-Conversation-ID

    Given url url_validazione
    And request read("richiesta-applicativa.xml")
    And header GovWay-TestSuite-Test-Id = 'generazione-header-conversation-id-richiesta'
    When method post
    Then status 200
    And match response == read("richiesta-applicativa-response.xml")
    And match header GovWay-Conversation-ID == 'd2f49459-1624-4710-b80c-15e33d64b608'

    Given url url_validazione
    And request read("richiesta-stato-not-ready.xml")
    And header GovWay-TestSuite-Test-Id = 'generazione-header-conversation-id-stato'
    When method post
    Then status 200
    And match response == read("richiesta-stato-not-ready-response.xml")
    And match header GovWay-Conversation-ID == 'd2f49459-1624-4710-b80c-15e33d64b608_NOT_READY'

    Given url url_validazione
    And request read("richiesta-stato-ready.xml")
    And header GovWay-TestSuite-Test-Id = 'generazione-header-conversation-id-stato-ready'
    When method post
    Then status 200
    And match response == read("richiesta-stato-ready-response.xml")
    And match header GovWay-Conversation-ID == 'd2f49459-1624-4710-b80c-15e33d64b608'

    Given url url_validazione
    And request read("recupero-risposta.xml")
    And header GovWay-TestSuite-Test-Id = 'generazione-header-conversation-id-risposta'
    When method post
    Then status 200
    And match response == read("recupero-risposta-response.xml")
    And match header GovWay-Conversation-ID == 'd2f49459-1624-4710-b80c-15e33d64b608'


# In questo caso l'erogazione aggiunge automaticamente lo header
# quindi ha senso testare solo la fruizione.
@no-correlation-in-request-validazione
Scenario: Richiesta applicativa senza X-Correlation-ID nella risposta con validazione sintattica body

    * def problem = read('error-bodies/no-correlation-id.xml')

    Given url url_validazione
    And request read("richiesta-applicativa.xml")
    And header GovWay-TestSuite-Test-Id = 'no-correlation-in-request-fruizione-validazione'
    When method post
    Then status 500
    And match response == problem



@generazione-header-correlazione
Scenario: Generazione dello header quando questo manca nella risposta della richiesta applicativa

    Given url url_validazione
    And request read("richiesta-applicativa.xml")
    And header GovWay-TestSuite-Test-Id = 'generazione-header-correlazione'
    When method post
    Then status 200
    And match /Envelope/Header/X-Correlation-ID != null


@no-correlation-in-request
Scenario: Richiesta applicativa senza X-Correlation-ID nella risposta

    * def problem = read('error-bodies/no-correlation-id.xml')

    Given url url_no_validazione
    And request read("richiesta-applicativa.xml")
    And header GovWay-TestSuite-Test-Id = 'no-correlation-in-request-fruizione'
    When method post
    Then status 500
    And match response == problem


@no-correlation-in-soap-header-fruizione
Scenario: Testa che la fruizione si arrabbi se non è presente lo header soap X-Corrleation-ID

    Given url url_no_validazione
    And request read("richiesta-stato-no-correlation.xml")
    When method post
    Then status 500
    And match response == read('error-bodies/no-correlation-id-in-request-status.xml')

    Given url url_no_validazione
    And request read("recupero-risposta-no-correlation.xml")
    When method post
    Then status 500
    And match response == read('error-bodies/no-correlation-id-in-request-status.xml')


@no-correlation-in-soap-header-erogazione
Scenario: Testa che la fruizione si arrabbi se non è presente lo header soap X-Corrleation-ID

    * def url_erogazione = govway_base_path + "/soap/in/DemoSoggettoErogatore/NonBlockingSoapPullNoValidazione/v1"

    Given url url_erogazione
    And request read("richiesta-stato-no-correlation.xml")
    And header GovWay-TestSuite-Test-Id = 'no-correlation-in-soap-header-erogazione'
    When method post
    Then status 500
    And match response == read('error-bodies/no-correlation-id-in-request-status-erogazione.xml')

    Given url url_erogazione
    And request read("recupero-risposta-no-correlation.xml")
    And header GovWay-TestSuite-Test-Id = 'no-correlation-in-soap-header-erogazione'
    When method post
    Then status 500
    And match response == read('error-bodies/no-correlation-id-in-request-status-erogazione.xml')


# Testare la presenza di GovWay-Conversation-ID (solo nella fruizione?)
@iniezione-header-soap
Scenario: Test Iniezione header soap a partire dagli header http di integrazione

    # Questa richiede che la api non abbia flaggato nessuna impostazione
    Given url url_validazione
    And request read("richiesta-stato-no-correlation.xml")
    And header GovWay-TestSuite-Test-Id = 'iniezione-header-soap'
    And header X-Correlation-ID = "d2f49459-1624-4710-b80c-15e33d64b608"
    When method post
    Then status 200
    And match response == read('richiesta-stato-ready-response.xml')


@iniezione-header-soap-id-collaborazione
Scenario: Test iniezione header soap per mezzo del parametro query o header http GovWay-Conversation-Id

    * def url_helper_id_collaborazione = govway_base_path + "/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/NonBlockingSoapPullHelperCollaborazioneNoValidazioneProxy/v1"

    Given url url_helper_id_collaborazione
    And request read("richiesta-stato-no-correlation.xml")
    And header GovWay-TestSuite-Test-Id = 'iniezione-header-soap'
    And header GovWay-Conversation-ID = "d2f49459-1624-4710-b80c-15e33d64b608"
    When method post
    Then status 200

    Given url url_helper_id_collaborazione
    And request read("richiesta-stato-no-correlation.xml")
    And header GovWay-TestSuite-Test-Id = 'iniezione-header-soap'
    And param govway_conversation_id = "d2f49459-1624-4710-b80c-15e33d64b608"
    When method post
    Then status 200


@iniezione-header-soap-riferimento-id-richiesta
Scenario: Test iniezione header soap per mezzo del parametro query o header http GovWay-Relates-To

    * def url_helper_riferimento_id_richiesta = govway_base_path + "/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/NonBlockingSoapPullHelperRiferimentoNoValidazioneProxy/v1"

    Given url url_helper_riferimento_id_richiesta
    And request read("richiesta-stato-no-correlation.xml")
    And header GovWay-TestSuite-Test-Id = 'iniezione-header-soap'
    And header GovWay-Relates-To = "d2f49459-1624-4710-b80c-15e33d64b608"
    When method post
    Then status 200

    Given url url_helper_riferimento_id_richiesta
    And request read("richiesta-stato-no-correlation.xml")
    And header GovWay-TestSuite-Test-Id = 'iniezione-header-soap'
    And param govway_relates_to = "d2f49459-1624-4710-b80c-15e33d64b608"
    When method post
    Then status 200

