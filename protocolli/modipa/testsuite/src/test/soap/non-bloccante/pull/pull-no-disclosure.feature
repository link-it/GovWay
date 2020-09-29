Feature: Test Profilo Non Bloccante Pull senza disclosure di informazioni

Background:

* def url_validazione = govway_base_path + "/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/NonBlockingSoapPullProxy/v1"
* def url_no_validazione = govway_base_path + "/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/NonBlockingSoapPullNoValidazioneProxy/v1"

* def result = callonce read('classpath:utils/jmx-disable-error-disclosure.feature')

* configure headers = { 'Content-Type': 'application/soap+xml', 'action': url_validazione }
* def invalid_response = read("invalid-response.xml")

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

