Feature: Server proxy contattato dalla fruizione

Background:
* def url_invocazione_erogazione = govway_base_path + "/rest/in/DemoSoggettoErogatore/NonBlockingSoapPull/v1"
* def url_no_validazione = govway_base_path + "/rest/in/DemoSoggettoErogatore/NonBlockingSoapPullNoValidazione/v1"
* def url_helper_headers = govway_base_path + "/rest/in/DemoSoggettoErogatore/NonBlockingSoapPullHelperHeadersNoValidazione/v1"

# Catch all

Scenario:
karate.proceed(url_invocazione_erogazione)