Feature: Server proxy per il testing sicurezza messaggio

Background: 

    # TODO: usa Gov...Test-ID anzichè Gov...Test-Id anche negli altri test
    * def isTest = function(id) { return headerContains('GovWay-TestSuite-Test-ID', id) } 
    * def karateCache = Java.type('org.openspcoop2.core.protocolli.modipa.testsuite.KarateCache')


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

    * karate.proceed (url_invocazione_erogazione)

    # Qui faccio match manuali, non so perchè quando matcho la risposta si lamenta dell'assenza
    # del prefisso soap

    * match /Envelope/Header/Security/Signature == "#present"
    * match /Envelope/Header/Security/Timestamp/Created == "#string"
    * match /Envelope/Header/Security/Timestamp/Expires == "#string"
    * match /Envelope/Header/To == "DemoSoggettoFruitore/ApplicativoBlockingIDA01"
    * match /Envelope/Header/From/Address == "SoapBlockingIDAS01/v1"
    * match /Envelope/Header/MessageID == "#uuid"

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

    * karate.proceed (url_invocazione_erogazione)

    * match /Envelope/Header/Security/Signature == "#present"
    * match /Envelope/Header/Security/Timestamp/Created == "#string"
    * match /Envelope/Header/Security/Timestamp/Expires == "#string"
    * match /Envelope/Header/To == "DemoSoggettoFruitore/ApplicativoBlockingIDA01"
    * match /Envelope/Header/From/Address == "SoapBlockingIDAS01DefaultTrustore/v1"
    * match /Envelope/Header/MessageID == "#uuid"

    * xmlstring server_response = response
    * eval karateCache.add("Server-Response", server_response)


Scenario: isTest('connettivita-base-no-sbustamento')
    * def url_invocazione_erogazione = govway_base_path + '/soap/in/DemoSoggettoErogatore/SoapBlockingIDAS01DefaultTrustoreNoSbustamento/v1'
    * karate.proceed (url_invocazione_erogazione)
    
# catch all
#
#

Scenario:
    karate.fail("Nessuno scenario matchato")
