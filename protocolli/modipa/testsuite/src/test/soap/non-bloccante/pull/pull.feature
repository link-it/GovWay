Feature: Test Profilo Non Bloccante Pull

Background:

* def url_validazione = govway_base_path + "/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/NonBlockingSoapPullProxy/v1"
* def url_no_validazione = govway_base_path + "/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/NonBlockingSoapPullNoValidazioneProxy/v1"

* configure headers = { 'Content-Type': 'application/soap+xml', 'action': url_validazione }
* def invalid_response = read("invalid-response.xml")

* def result = callonce read('classpath:utils/jmx-enable-error-disclosure.feature')
* configure afterFeature = function(){ karate.call('classpath:utils/jmx-disable-error-disclosure.feature'); }


# TODO: Riscrivilo in modo che usi una configurazione https
@test-ok
Scenario: Giro OK

    Given url url_validazione
    And request read("richiesta-applicativa.xml")
    When method post
    Then status 200
    And match response == read("richiesta-applicativa-response.xml")
    And match header GovWay-Conversation-ID == 'd2f49459-1624-4710-b80c-15e33d64b608'

    Given url url_validazione
    And request read("richiesta-stato-not-ready.xml")
    When method post
    Then status 200
    And match response == read("richiesta-stato-not-ready-response.xml")
    And match header GovWay-Conversation-ID == 'd2f49459-1624-4710-b80c-15e33d64b608_NOT_READY'

    Given url url_validazione
    And request read("richiesta-stato-ready.xml")
    When method post
    Then status 200
    And match response == read("richiesta-stato-ready-response.xml")
    And match header GovWay-Conversation-ID == 'd2f49459-1624-4710-b80c-15e33d64b608'

    Given url url_validazione
    And request read("recupero-risposta.xml")
    When method post
    Then status 200
    And match response == read("recupero-risposta-response.xml")
    And match header GovWay-Conversation-ID == 'd2f49459-1624-4710-b80c-15e33d64b608'


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


@no-correlation-in-soap-header
Scenario: Testa che l'erogazione si arrabbi se non è presente lo header soap X-Corrleation-ID

    Given url url_no_validazione
    And request read("richiesta-stato-no-correlation.xml")
    And header GovWay-TestSuite-Test-Id = 'no-correlation-in-soap-header'
    When method post
    Then status 500

    Given url url_no_validazione
    And request read("recupero-risposta-no-correlation.xml")
    And header GovWay-TestSuite-Test-Id = 'no-correlation-in-soap-header'
    When method post
    Then status 500


# Controllare che l'erogazione si arrabbi quando non ci metto lo header x-correlation-id nelle richieste stato e richiesta risorsa


# Testare la presenza di GovWay-Conversation-ID (solo nella fruizione?)
Scenario: Test GovWayConversation-ID e GovWayRelates to

