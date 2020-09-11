Feature: payment service mock

Background:
* def pid = 0


Scenario: pathMatches('/resources/{id}/M') && methodIs('post')
    * karate.proceed(url_invocazione_erogazione)
    #* set response.cacca = 4
    #* def response = { aga: "mennone"}