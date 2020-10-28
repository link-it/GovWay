Feature: Testing Sicurezza Messaggio ModiPA IDAS03

Background:
    * def basic = read('classpath:utils/basic-auth.js')

    * def result = callonce read('classpath:utils/jmx-enable-error-disclosure.feature')
    * configure afterFeature = function(){ karate.call('classpath:utils/jmx-disable-error-disclosure.feature'); }
    
    * def check_traccia = read('check-tracce/check-traccia.feature')
    * def check_signature = read('classpath:org/openspcoop2/core/protocolli/modipa/testsuite/soap/sicurezza_messaggio/check-signature.feature')

    * def x509sub_client1 = 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT'
    * def x509sub_server = 'CN=ExampleServer, O=Example, L=Pisa, ST=Italy, C=IT'


@connettivita-base
Scenario: Test giro ok, controllo del X-RequestDigest

* def soap_url = govway_base_path + '/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/SoapBlockingIDAS03/v1'

Given url soap_url
And request read("request.xml")
And header Content-Type = 'application/soap+xml'
And header action = soap_url
And header GovWay-TestSuite-Test-ID = 'connettivita-base-idas03'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method post
Then status 200
And match response == read("response.xml")

* def karateCache = Java.type('org.openspcoop2.core.protocolli.modipa.testsuite.KarateCache')
* xml client_request = karateCache.get("Client-Request")
* xml server_response = karateCache.get("Server-Response")

* def body_reference = get client_request/Envelope/Body/@Id
* def body_signature = karate.xmlPath(client_request, "/Envelope/Header/Security/Signature/SignedInfo/Reference[@URI='#"+body_reference+"']/DigestValue")

* karate.log ("Signature ASDRUBALE: ", body_signature)
 
 #client_request/Envelope/Header/Security/Signature/SignedInfo/Reference

# TODO: Estrarre il digest vero e proprio
* def checks_richiesta = 
"""
([
    { name: 'ProfiloSicurezzaMessaggio-Digest', value: 'SHA256='+body_signature}
])
"""

* def checks_risposta = 
"""
([
    { name: 'ProfiloSicurezzaMessaggio-Digest', value: '#string'},
    { name: 'ProfiloSicurezzaMessaggio-RelatesTo', value: '#string'}
])
"""


* def tid = responseHeaders['GovWay-Transaction-ID'][0]
* call check_traccia ({tid: tid, tipo: 'Richiesta', body: client_request, x509sub: x509sub_client1, profilo_sicurezza: "IDAS0301", other_checks: checks_richiesta })
* call check_traccia ({tid: tid, tipo: 'Risposta', body: server_response, x509sub: x509sub_server, profilo_sicurezza: "IDAS0301", other_checks: checks_risposta })

* def tid = responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0]
* call check_traccia ({tid: tid, tipo: 'Richiesta', body: client_request, x509sub: x509sub_client1, profilo_sicurezza: "IDAS0301", other_checks: checks_richiesta })
* call check_traccia ({tid: tid, tipo: 'Risposta', body: server_response, x509sub: x509sub_server, profilo_sicurezza: "IDAS0301", other_checks: checks_risposta })

# @manomissione-token-richiesta


# @manomissione-token-risposta


# @manomissione-payload-richiesta

# @manomissione-payload-risposta
