Feature: Test Profilo Non Bloccante Pull

Background:


@test-ok
Scenario: Giro OK

Given url url_invocazione_validazione
And request body
And header Content-Type = 'application/soap+xml'
And header action = url_invocazione_validazione
When method post
Then status 200