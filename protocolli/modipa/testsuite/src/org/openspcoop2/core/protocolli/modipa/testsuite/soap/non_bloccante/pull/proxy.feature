Feature: Server proxy contattato dalla fruizione

Background:
* def url_validazione = govway_base_path + "/soap/in/DemoSoggettoErogatore/NonBlockingSoapPull/v1"
* def url_no_validazione = govway_base_path + "/soap/in/DemoSoggettoErogatore/NonBlockingSoapPullNoValidazione/v1"
* def url_helper_headers = govway_base_path + "/soap/in/DemoSoggettoErogatore/NonBlockingSoapPullHelperHeadersNoValidazione/v1"

* def invalid_response = read("classpath:src/test/soap/non-bloccante/pull/invalid-response.xml")

Scenario: methodIs('post') && headerContains('GovWay-TestSuite-Test-Id', 'no-correlation-in-request-fruizione-validazione')

* def response = read('classpath:src/test/soap/non-bloccante/pull/richiesta-applicativa-no-correlation-response.xml')
* def responseStatus = 200


Scenario: methodIs('post') && headerContains('GovWay-TestSuite-Test-Id', 'no-correlation-in-request-erogazione-validazione')

* karate.proceed(url_validazione)

* match responseStatus == 500
* match response == invalid_response


Scenario: methodIs('post') && headerContains('GovWay-TestSuite-Test-Id', 'no-correlation-in-request-fruizione')

* def response = read('classpath:src/test/soap/non-bloccante/pull/richiesta-applicativa-no-correlation-response.xml')
* def responseStatus = 200


# Scenario: methodIs('post') && headerContains('GovWay-TestSuite-Test-Id', 'no-correlation-in-request-fruizione-disclosure')

# * def response = read('classpath:src/test/soap/non-bloccante/pull/richiesta-applicativa-no-correlation-response.xml')
# * def responseStatus = 200


Scenario: methodIs('post') && headerContains('GovWay-TestSuite-Test-Id', 'no-correlation-in-request-erogazione')

* karate.proceed(url_no_validazione)

* match responseStatus == 500
* match response == invalid_response


# Catch all

Scenario: methodIs('post')
* karate.proceed(url_validazione)



