Feature: payment service mock

Background:
* def pid = 0
# * def payments = {}

# Scenario: pathMatches('/payments') && methodIs('post')
#     * def payment = request
#     * def id = ~~(id + 1)
#     * payment.id = id
#     * payments[id + ''] = payment
#     * def response = payment 

# Scenario: pathMatches('/payments')
#     * def response = $payments.*

# Scenario: pathMatches('/payments/{id}') && methodIs('put')
#     * payments[pathParams.id] = request
#     * def response = request

# Scenario: pathMatches('/payments/{id}') && methodIs('delete')
#     * karate.remove('payments', '$.' + pathParams.id)
#     * def response = ''

# Scenario: pathMatches('/payments/{id}')
#     * def response = payments[pathParams.id]

Scenario: pathMatches('/resources/{id}/M') && methodIs('post')
    * karate.proceed(url_invocazione_erogazione)
    #* set response.cacca = 4
    #* def response = { aga: "mennone"}