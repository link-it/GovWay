Feature: Server proxy per il testing sicurezza messaggio

Background: 

    * def isTest = function(id) { return karate.get("requestHeaders['GovWay-TestSuite-Test-ID'][0]") == id } 
    * def karateCache = Java.type('org.openspcoop2.core.protocolli.modipa.testsuite.KarateCache')
    * def check_signature = read('check-signature.feature')
    * def check_client_token = read('check-client-token.feature')
    * def check_server_token = read('check-server-token.feature')


Scenario: isTest('connettivita-base')
    # Salvo la richiesta e la risposta per far controllare la traccia del token
    # alla feature chiamante
    * xmlstring client_request = bodyPath('/')
    * eval karateCache.add("Client-Request", client_request)

    * call check_client_token ({ address: "DemoSoggettoFruitore/ApplicativoBlockingIDA01", to: "testsuite" })

    # Siccome abbiamo un Riferimento X509 DirectReference, controllo che KeyInfo riferisca il BinarySecurityToken
    * def keyRef = bodyPath('/Envelope/Header/Security/Signature/KeyInfo/SecurityTokenReference/Reference/@URI')
    * def key = bodyPath('/Envelope/Header/Security/BinarySecurityToken/@Id')
    * match keyRef == '#' + key

    * karate.proceed (govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01/v1')

    * call check_server_token ({ from: "SoapBlockingIDAS01/v1", to: "DemoSoggettoFruitore/ApplicativoBlockingIDA01" })

    * def keyRef = /Envelope/Header/Security/Signature/KeyInfo/SecurityTokenReference/Reference/@URI
    * def key = /Envelope/Header/Security/BinarySecurityToken/@Id
    * match keyRef == '#' + key

    * xmlstring server_response = response
    * eval karateCache.add("Server-Response", server_response)


Scenario: isTest('connettivita-base-default-trustore')
    
    * xmlstring client_request = bodyPath('/')
    * eval karateCache.add("Client-Request", client_request)

    * call check_client_token ({ address: "DemoSoggettoFruitore/ApplicativoBlockingIDA01", to: "testsuite" })

    * karate.proceed (govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01DefaultTrustore/v1')

    * call check_server_token ({ from: "SoapBlockingIDAS01DefaultTrustore/v1", to: "DemoSoggettoFruitore/ApplicativoBlockingIDA01" })

    * xmlstring server_response = response
    * eval karateCache.add("Server-Response", server_response)
    

Scenario: isTest('connettivita-base-no-sbustamento')

    * xmlstring client_request = bodyPath('/')
    * eval karateCache.add("Client-Request", client_request)

    * karate.proceed (govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01DefaultTrustoreNoSbustamento/v1')

    * xmlstring server_response = response
    * eval karateCache.add("Server-Response", server_response)


Scenario: isTest('connettivita-base-truststore-ca')
    
    * xmlstring client_request = bodyPath('/')
    * eval karateCache.add("Client-Request", client_request)

    * call check_client_token ({ address: "DemoSoggettoFruitore/ApplicativoBlockingIDA01", to: "testsuite" })

    * karate.proceed (govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01TrustStoreCA/v1')

    * call check_server_token ({ from: "SoapBlockingIDAS01TrustStoreCA/v1", to: "DemoSoggettoFruitore/ApplicativoBlockingIDA01" })

    * xmlstring server_response = response
    * eval karateCache.add("Server-Response", server_response)


Scenario: isTest('response-without-payload')
    
    * xmlstring client_request = bodyPath('/')
    * eval karateCache.add("Client-Request", client_request)

    * call check_client_token ({ address: "DemoSoggettoFruitore/ApplicativoBlockingIDA01", to: "testsuite" })

    * karate.proceed (govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01MultipleOP/v1')

    # La signature non viene fatta su di una risposta vuota quindi non la controllo
    # Controllo qui la traccia della erogazione perchè non posso far viaggiare header
    # opzionali indietro visto che l'azione è one-way
    
    * def check_traccia = read("classpath:test/soap/sicurezza-messaggio/check-tracce/check-traccia.feature")
    * xml client_request = client_request

    * def tid = responseHeaders['GovWay-Transaction-ID'][0]
    * call check_traccia ({tid: tid, tipo: 'Richiesta', body: client_request, x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT' })



Scenario: isTest('disabled-security-on-action')
    * def c = request

    * match c/Envelope/Header/Security/BinarySecurityToken == "#notpresent"
    
    * karate.proceed (govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01MultipleOP/v1')

    * match /Envelope/Header/Security/BinarySecurityToken == "#notpresent"

##
# Controllo che la sicurezza sia abilitata puntualmente su una operazione,
# mentre di default è disabilitata
#
#

Scenario: isTest('enabled-security-on-action') && bodyPath('/Envelope/Body/MRequestOp') != ''
    
    * xmlstring client_request = bodyPath('/')
    * eval karateCache.add("Client-Request", client_request)

    * call check_client_token ({ address: "DemoSoggettoFruitore/ApplicativoBlockingIDA01", to: "testsuite" })
    
    * karate.proceed (govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01MultipleOPNoDefaultSecurity/v1')

    * call check_server_token ({ from: "SoapBlockingIDAS01MultipleOPNoDefaultSecurity/v1", to: "DemoSoggettoFruitore/ApplicativoBlockingIDA01" })

    * xmlstring server_response = response
    * eval karateCache.add("Server-Response", server_response)


Scenario: isTest('enabled-security-on-action') && bodyPath('/Envelope/Body/MRequestOp1') != ''

    * def c = request

    * match c/Envelope/Header/Security/BinarySecurityToken == "#notpresent"
    
    * karate.proceed (govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01MultipleOPNoDefaultSecurity/v1')

    * match /Envelope/Header/Security/BinarySecurityToken == "#notpresent"


Scenario: isTest('riferimento-x509-SKIKey-IssuerSerial')
    
    * xmlstring client_request = bodyPath('/')
    * eval karateCache.add("Client-Request", client_request)

    * call check_client_token ({ address: "DemoSoggettoFruitore/ApplicativoBlockingIDA01", to: "testsuite" })
    
    # Testo la presenza del Subject Key Identifier nello header
    * match bodyPath('/Envelope/Header/Security/Signature/KeyInfo/SecurityTokenReference/KeyIdentifier') == "V8ojtQaElmusOPopR34itbvzPW8="
    
    * karate.proceed (govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01IssuerSerial/v1')

    * call check_server_token ({ from: "SoapBlockingIDAS01IssuerSerial/v1", to: "DemoSoggettoFruitore/ApplicativoBlockingIDA01" })
    # Testo la presenza di IssuerSerial nello header
    * match /Envelope/Header/Security/Signature/KeyInfo/SecurityTokenReference/X509Data/X509IssuerSerial/X509IssuerName == "CN=ExampleCA,O=Example,L=Pisa,ST=Italy,C=IT"
    * match /Envelope/Header/Security/Signature/KeyInfo/SecurityTokenReference/X509Data/X509IssuerSerial/X509SerialNumber == "337913909459742394"

    * xmlstring server_response = response
    * eval karateCache.add("Server-Response", server_response)



Scenario: isTest('riferimento-x509-ThumbprintKey-SKIKey')
    
    * xmlstring client_request = bodyPath('/')
    * eval karateCache.add("Client-Request", client_request)

    * call check_client_token ({ address: "DemoSoggettoFruitore/ApplicativoBlockingIDA01", to: "testsuite" })

    * match bodyPath("/Envelope/Header/Security/Signature/KeyInfo/SecurityTokenReference/KeyIdentifier") == "#present"
    
    * karate.proceed (govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01SKIKey/v1')

    * call check_server_token ({ from: "SoapBlockingIDAS01SKIKey/v1", to: "DemoSoggettoFruitore/ApplicativoBlockingIDA01" })
    # Testo la presenza del Subject Key Identifier nello header
    * match /Envelope/Header/Security/Signature/KeyInfo/SecurityTokenReference/KeyIdentifier == "#present"

    * xmlstring server_response = response
    * eval karateCache.add("Server-Response", server_response)


Scenario: isTest('riferimento-x509-x509Key-ThumbprintKey')
    
    * xmlstring client_request = bodyPath('/')
    * eval karateCache.add("Client-Request", client_request)

    * call check_client_token ({ address: "DemoSoggettoFruitore/ApplicativoBlockingIDA01", to: "testsuite" })
    
    * match bodyPath("/Envelope/Header/Security/Signature/KeyInfo/SecurityTokenReference/KeyIdentifier") == "#present"
    
    * karate.proceed (govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01ThumbprintKey/v1')

    * call check_server_token ({ from: "SoapBlockingIDAS01ThumbprintKey/v1", to: "DemoSoggettoFruitore/ApplicativoBlockingIDA01" })
    
    * match /Envelope/Header/Security/Signature/KeyInfo/SecurityTokenReference/KeyIdentifier == "#present"

    * xmlstring server_response = response
    * eval karateCache.add("Server-Response", server_response)


Scenario: isTest('riferimento-x509-IssuerSerial-x509Key')
    
    * xmlstring client_request = bodyPath('/')
    * eval karateCache.add("Client-Request", client_request)

    * call check_client_token ({ address: "DemoSoggettoFruitore/ApplicativoBlockingIDA01", to: "testsuite" })
    
    * match bodyPath('/Envelope/Header/Security/Signature/KeyInfo/SecurityTokenReference/X509Data/X509IssuerSerial/X509IssuerName') == "#present"
    * match bodyPath('/Envelope/Header/Security/Signature/KeyInfo/SecurityTokenReference/X509Data/X509IssuerSerial/X509SerialNumber') == "#present"
    
    * karate.proceed (govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01X509KeyId/v1')

    * call check_server_token ({ from: "SoapBlockingIDAS01X509KeyId/v1", to: "DemoSoggettoFruitore/ApplicativoBlockingIDA01" })
    
    # Testo la presenza della thumbprint sha-1 del certificato server
    * match /Envelope/Header/Security/Signature/KeyInfo/SecurityTokenReference/KeyIdentifier == "#present"

    * xmlstring server_response = response
    * eval karateCache.add("Server-Response", server_response)

Scenario: isTest('no-token-to-fruizione')

    * def responseStatus = 200
    * def response = read('classpath:test/soap/sicurezza-messaggio/response.xml')
    * def responseHeaders = { 'Content-type': "application/soap+xml" }


Scenario: isTest('manomissione-token-richiesta')

    * def c = request
    * set c /Envelope/Header/To = "tampered_content"

    * karate.proceed (govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01/v1')
    * match responseStatus == 500
    * match response == read('classpath:test/soap/sicurezza-messaggio/error-bodies/manomissione-token-richiesta.xml')


Scenario: isTest('manomissione-token-richiesta-no-disclosure')

    * def c = request
    * set c /Envelope/Header/To = "tampered_content"

    * karate.proceed (govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01/v1')
    * match responseStatus == 500
    * match response == read('classpath:test/soap/sicurezza-messaggio/error-bodies/interoperability-invalid-request.xml')

Scenario: isTest('manomissione-token-risposta')

    * karate.proceed (govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01/v1')
    * match responseStatus == 200
    
    * def c = response
    * set c /Envelope/Header/To = "tampered_content"

Scenario: isTest('low-ttl-fruizione')

    * java.lang.Thread.sleep(2000)

    * karate.proceed (govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01/v1')
    * match responseStatus == 500
    * match response == read('classpath:test/soap/sicurezza-messaggio/error-bodies/ttl-scaduto-in-request.xml')


Scenario: isTest('low-ttl-fruizione-no-disclosure')

    * java.lang.Thread.sleep(2000)

    * karate.proceed (govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01/v1')
    * match responseStatus == 500
    * match response == read('classpath:test/soap/sicurezza-messaggio/error-bodies/interoperability-invalid-request.xml')
    

Scenario: isTest('low-ttl-erogazione')

    * karate.proceed (govway_base_path + '/soap/in/DemoSoggettoErogatore/SOAPBlockingIDAS01LowTTL/v1')
    * match responseStatus == 200

    * java.lang.Thread.sleep(2000)
    
    



# catch all
#
#

Scenario:
    karate.fail("Nessuno scenario matchato")
