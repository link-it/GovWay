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


    Given url url_validazione
    And request read("richiesta-stato-not-ready.xml")
    When method post
    Then status 200
    And match response == read("richiesta-stato-not-ready-response.xml")

    Given url url_validazione
    And request read("richiesta-stato-ready.xml")
    When method post
    Then status 200
    And match response == read("richiesta-stato-ready-response.xml")

    Given url url_validazione
    And request read("recupero-risposta.xml")
    When method post
    Then status 200
    And match response == read("recupero-risposta-response.xml")


# Qui facciamo arrabbiare una volta l'erogazione e una volta la fruizione
# Questo è l'unico caso della testsuite in cui generiamo un'errore di validazione
# incrociato con un errore modIPA, per testare che l'integrazione fra i due funzioni.
@no-correlation-in-request-validazione
Scenario: Richiesta applicativa senza X-Correlation-ID nella risposta con validazione sintattica body

    Given url url_validazione
    And request read("richiesta-applicativa.xml")
    And header GovWay-TestSuite-Test-Id = 'no-correlation-in-request-erogazione-validazione'
    When method post
    Then status 500
    And match response == invalid_response


    Given url url_validazione
    And request read("richiesta-applicativa.xml")
    And header GovWay-TestSuite-Test-Id = 'no-correlation-in-request-fruizione-validazione'
    When method post
    Then status 500
    And match response == invalid_response


@no-correlation-in-request
Scenario: Richiesta applicativa senza X-Correlation-ID nella risposta

    #* def invalid_response == read('./error-messages/no-correlation-in-request.xml')

    Given url url_no_validazione
    And request read("richiesta-applicativa.xml")
    And header GovWay-TestSuite-Test-Id = 'no-correlation-in-request-erogazione'
    When method post
    Then status 500
    And match response == invalid_response

    Given url url_no_validazione
    And request read("richiesta-applicativa.xml")
    And header GovWay-TestSuite-Test-Id = 'no-correlation-in-request-fruizione'
    When method post
    Then status 500
    And match response == invalid_response


# Testo che lo X-Correlation-Id venga generato dall'erogazione
# con un giro ok che non lo prevede nelle risposte del mock
Scenario: Test generazione automatica header X-Correlation-Id


# Testare la presenza di GovWay-Conversation-ID e GovWay-Relates-To
Scenario: Test GovWayConversation-ID e GovWayRelates to

