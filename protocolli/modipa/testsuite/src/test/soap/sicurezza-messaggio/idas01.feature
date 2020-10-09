Feature: Testing Sicurezza Messaggio ModiPA IDAR01

Background:
    * def basic = read('classpath:utils/basic-auth.js')

    * def result = callonce read('classpath:utils/jmx-enable-error-disclosure.feature')
    * configure afterFeature = function(){ karate.call('classpath:utils/jmx-disable-error-disclosure.feature'); }

Scenario: Test connettività base

* def body = read("request.xml")
* def resp = read("response.xml")
* def soap_url = govway_base_path + '/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/SoapBlockingIDAS01/v1'

# TODO: Controlla che la validazione lato erogazione funzioni, perchè prima
# ho messo il tag Header nel tag Body e da wireshark vedevo che l'erogazione accettava la risposta del backend
Given url soap_url
And request body
And header Content-Type = 'application/soap+xml'
And header action = soap_url
And header GovWay-TestSuite-Test-ID = 'connettivita-base'
And header Authorization = call basic ({ username: 'RestBlockingIDAR01', password: 'RestBlockingIDAR01' })
When method post
Then status 200
And match response == resp
