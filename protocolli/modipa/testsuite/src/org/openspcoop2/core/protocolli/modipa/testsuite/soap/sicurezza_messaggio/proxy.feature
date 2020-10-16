Feature: Server proxy per il testing sicurezza messaggio

Background: 

    * def isTest = function(id) { return karate.get("requestHeaders['GovWay-TestSuite-Test-ID'][0]") == id } 
    * def karateCache = Java.type('org.openspcoop2.core.protocolli.modipa.testsuite.KarateCache')
    * def check_signature = read('check-signature.feature')


Scenario: isTest('connettivita-base')
    * def url_invocazione_erogazione = govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01/v1'
    
    # Salvo la richiesta e la risposta per far controllare il token
    # alla feature chiamante

    * xmlstring client_request = bodyPath('/')
    * eval karateCache.add("Client-Request", client_request)

    * def client_token = bodyPath('/Envelope/Header')

    * def match_to = 
    """
    <soap:Header>
        <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"
            xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" soap:mustUnderstand="true">
            <wsse:BinarySecurityToken>#present</wsse:BinarySecurityToken>
            <ds:Signature xmlns:ds="http://www.w3.org/2000/09/xmldsig#">#present</ds:Signature>
            <wsu:Timestamp wsu:Id="#string">
                <wsu:Created>#string</wsu:Created>
                <wsu:Expires>#string</wsu:Expires>
            </wsu:Timestamp>
        </wsse:Security>
        <wsa:To xmlns:wsa="http://www.w3.org/2005/08/addressing"
            xmlns:env="http://www.w3.org/2003/05/soap-envelope"
            xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" env:mustUnderstand="false" wsu:Id="#string">testsuite</wsa:To>
        <wsa:From xmlns:wsa="http://www.w3.org/2005/08/addressing"
            xmlns:env="http://www.w3.org/2003/05/soap-envelope"
            xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" env:mustUnderstand="false" wsu:Id="#string">
            <wsa:Address>DemoSoggettoFruitore/ApplicativoBlockingIDA01</wsa:Address>
        </wsa:From>
        <wsa:MessageID xmlns:wsa="http://www.w3.org/2005/08/addressing"
            xmlns:env="http://www.w3.org/2003/05/soap-envelope"
            xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" env:mustUnderstand="false" wsu:Id="#string">#uuid</wsa:MessageID>
        <wsa:ReplyTo xmlns:wsa="http://www.w3.org/2005/08/addressing"
            xmlns:env="http://www.w3.org/2003/05/soap-envelope"
            xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" env:mustUnderstand="false" wsu:Id="#string">
            <wsa:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa:Address>
        </wsa:ReplyTo>
    </soap:Header>
    """
    * match client_token == match_to

    * def body = bodyPath('/')
    * call check_signature [ {element: 'To'}, {element: 'From'}, {element: 'MessageID'}, {element: 'ReplyTo'} ]

    * karate.proceed (url_invocazione_erogazione)

    # Qui faccio match manuali, non so perchè quando matcho la risposta si lamenta dell'assenza
    # del prefisso soap

    * match /Envelope/Header/Security/Signature == "#present"
    * match /Envelope/Header/Security/Timestamp/Created == "#string"
    * match /Envelope/Header/Security/Timestamp/Expires == "#string"
    * match /Envelope/Header/To == "DemoSoggettoFruitore/ApplicativoBlockingIDA01"
    * match /Envelope/Header/From/Address == "SoapBlockingIDAS01/v1"
    * match /Envelope/Header/MessageID == "#uuid"

    * def body = response
    * call check_signature [ {element: 'To'}, {element: 'From'}, {element: 'MessageID'}, {element: 'ReplyTo'}, {element: 'RelatesTo'} ]

    * xmlstring server_response = response
    * eval karateCache.add("Server-Response", server_response)


Scenario: isTest('connettivita-base-default-trustore')
    * def url_invocazione_erogazione = govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01DefaultTrustore/v1'
    
    * xmlstring client_request = bodyPath('/')
    * eval karateCache.add("Client-Request", client_request)

    * match bodyPath('/Envelope/Header/Security/Signature') == "#present"
    * match bodyPath('/Envelope/Header/Security/Timestamp/Created') == "#string"
    * match bodyPath('/Envelope/Header/Security/Timestamp/Expires') == "#string"
    * match bodyPath('/Envelope/Header/To') == "testsuite"
    * match bodyPath('/Envelope/Header/From/Address') == "DemoSoggettoFruitore/ApplicativoBlockingIDA01"
    * match bodyPath('/Envelope/Header/MessageID') == "#uuid"

    * def body = bodyPath('/')
    * call check_signature [ {element: 'To'}, {element: 'From'}, {element: 'MessageID'}, {element: 'ReplyTo'} ]

    * karate.proceed (url_invocazione_erogazione)

    * match /Envelope/Header/Security/Signature == "#present"
    * match /Envelope/Header/Security/Timestamp/Created == "#string"
    * match /Envelope/Header/Security/Timestamp/Expires == "#string"
    * match /Envelope/Header/To == "DemoSoggettoFruitore/ApplicativoBlockingIDA01"
    * match /Envelope/Header/From/Address == "SoapBlockingIDAS01DefaultTrustore/v1"
    * match /Envelope/Header/MessageID == "#uuid"

    * def body = response
    * call check_signature [ {element: 'To'}, {element: 'From'}, {element: 'MessageID'}, {element: 'ReplyTo'}, {element: 'RelatesTo'} ]

    * xmlstring server_response = response
    * eval karateCache.add("Server-Response", server_response)


Scenario: isTest('connettivita-base-no-sbustamento')

    * xmlstring client_request = bodyPath('/')
    * eval karateCache.add("Client-Request", client_request)

    * def url_invocazione_erogazione = govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01DefaultTrustoreNoSbustamento/v1'
    * karate.proceed (url_invocazione_erogazione)

    * xmlstring server_response = response
    * eval karateCache.add("Server-Response", server_response)


Scenario: isTest('connettivita-base-truststore-ca')
    * def url_invocazione_erogazione = govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01TrustStoreCA/v1'
    
    * xmlstring client_request = bodyPath('/')
    * eval karateCache.add("Client-Request", client_request)

    * match bodyPath('/Envelope/Header/Security/Signature') == "#present"
    * match bodyPath('/Envelope/Header/Security/Timestamp/Created') == "#string"
    * match bodyPath('/Envelope/Header/Security/Timestamp/Expires') == "#string"
    * match bodyPath('/Envelope/Header/To') == "testsuite"
    * match bodyPath('/Envelope/Header/From/Address') == "DemoSoggettoFruitore/ApplicativoBlockingIDA01"
    * match bodyPath('/Envelope/Header/MessageID') == "#uuid"

    * def body = bodyPath('/')
    * call check_signature [ {element: 'To'}, {element: 'From'}, {element: 'MessageID'}, {element: 'ReplyTo'} ]

    * karate.proceed (url_invocazione_erogazione)

    * match /Envelope/Header/Security/Signature == "#present"
    * match /Envelope/Header/Security/Timestamp/Created == "#string"
    * match /Envelope/Header/Security/Timestamp/Expires == "#string"
    * match /Envelope/Header/To == "DemoSoggettoFruitore/ApplicativoBlockingIDA01"
    * match /Envelope/Header/From/Address == "SoapBlockingIDAS01TrustStoreCA/v1"
    * match /Envelope/Header/MessageID == "#uuid"

    * def body = response
    * call check_signature [ {element: 'To'}, {element: 'From'}, {element: 'MessageID'}, {element: 'ReplyTo'}, {element: 'RelatesTo'} ]

    * xmlstring server_response = response
    * eval karateCache.add("Server-Response", server_response)


Scenario: isTest('response-without-payload')
    * def url_invocazione_erogazione = govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01MultipleOP/v1'
    
    * xmlstring client_request = bodyPath('/')
    * eval karateCache.add("Client-Request", client_request)

    * match bodyPath('/Envelope/Header/Security/Signature') == "#present"
    * match bodyPath('/Envelope/Header/Security/Timestamp/Created') == "#string"
    * match bodyPath('/Envelope/Header/Security/Timestamp/Expires') == "#string"
    * match bodyPath('/Envelope/Header/To') == "testsuite"
    * match bodyPath('/Envelope/Header/From/Address') == "DemoSoggettoFruitore/ApplicativoBlockingIDA01"
    * match bodyPath('/Envelope/Header/MessageID') == "#uuid"

    * def body = bodyPath('/')
    * call check_signature [ {element: 'To'}, {element: 'From'}, {element: 'MessageID'}, {element: 'ReplyTo'} ]

    * karate.proceed (url_invocazione_erogazione)

    # Controllo qui la traccia della erogazione perchè non posso far viaggiare header
    # opzionali indietro visto che l'azione è one-way
    
    * def check_traccia = read("classpath:test/soap/sicurezza-messaggio/check-tracce/check-traccia.feature")
    * xml client_request = client_request
    * def client_token_to_match = 
    """
    ({
        x509sub: 'CN=ExampleClient1, O=Example, L=Pisa, ST=Italy, C=IT',
        wsa_to: karate.xmlPath(client_request, '/Envelope/Header/To'),
        wsa_from: karate.xmlPath(client_request, '/Envelope/Header/From/Address'),
        message_id: karate.xmlPath(client_request, '/Envelope/Header/MessageID')
    })
    """
    * def tid = responseHeaders['GovWay-Transaction-ID'][0]
    * call check_traccia ({tid: tid, tipo: 'Richiesta', token: client_token_to_match })

    # La signature non viene fatta su di una risposta vuota


Scenario: isTest('disabled-security-on-action')
    * def c = request

    * match c/Envelope/Header/Security/BinarySecurityToken == "#notpresent"
    
    * def url_invocazione_erogazione = govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01MultipleOP/v1'
    * karate.proceed (url_invocazione_erogazione)

    * match /Envelope/Header/Security/BinarySecurityToken == "#notpresent"

##
# Controllo che la sicurezza sia abilitata puntualmente su una operazione,
# mentre di default è disabilitata
#
#

Scenario: isTest('enabled-security-on-action') && bodyPath('/Envelope/Body/MRequestOp') != ''
    
    * xmlstring client_request = bodyPath('/')
    * eval karateCache.add("Client-Request", client_request)

    * match bodyPath('/Envelope/Header/Security/Signature') == "#present"
    * match bodyPath('/Envelope/Header/Security/Timestamp/Created') == "#string"
    * match bodyPath('/Envelope/Header/Security/Timestamp/Expires') == "#string"
    * match bodyPath('/Envelope/Header/To') == "testsuite"
    * match bodyPath('/Envelope/Header/From/Address') == "DemoSoggettoFruitore/ApplicativoBlockingIDA01"
    * match bodyPath('/Envelope/Header/MessageID') == "#uuid"

    * def body = bodyPath('/')
    * call check_signature [ {element: 'To'}, {element: 'From'}, {element: 'MessageID'}, {element: 'ReplyTo'} ]
    
    * def url_invocazione_erogazione = govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01MultipleOPNoDefaultSecurity/v1'
    * karate.proceed (url_invocazione_erogazione)

    * match /Envelope/Header/Security/Signature == "#present"
    * match /Envelope/Header/Security/Timestamp/Created == "#string"
    * match /Envelope/Header/Security/Timestamp/Expires == "#string"
    * match /Envelope/Header/To == "DemoSoggettoFruitore/ApplicativoBlockingIDA01"
    * match /Envelope/Header/From/Address == "SoapBlockingIDAS01MultipleOPNoDefaultSecurity/v1"
    * match /Envelope/Header/MessageID == "#uuid"

    * def body = response
    * call check_signature [ {element: 'To'}, {element: 'From'}, {element: 'MessageID'}, {element: 'ReplyTo'}, {element: 'RelatesTo'} ]

    * xmlstring server_response = response
    * eval karateCache.add("Server-Response", server_response)


Scenario: isTest('enabled-security-on-action') && bodyPath('/Envelope/Body/MRequestOp1') != ''

    * def c = request

    * match c/Envelope/Header/Security/BinarySecurityToken == "#notpresent"
    
    * def url_invocazione_erogazione = govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01MultipleOPNoDefaultSecurity/v1'
    * karate.proceed (url_invocazione_erogazione)

    # Qui siamo in oneway, quindi al ritorno il token non c'è proprio
    # perchè la richiesta non viene gestita.

    * match /Envelope/Header/Security/BinarySecurityToken == "#notpresent"


# catch all
#
#

Scenario:
    karate.fail("Nessuno scenario matchato")
