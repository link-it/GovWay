Feature: Server mock per il testing della sicurezza messaggio

Background:
    * def isTest = function(id) { return karate.get("requestHeaders['GovWay-TestSuite-Test-ID'][0]") == id } 
    * def check_signature = read('check-signature.feature')

    * def confHeaders = 
    """
    function() { 
        return {
            'Content-type': "application/soap+xml",
            'GovWay-TestSuite-GovWay-Transaction-ID': karate.get("requestHeaders['GovWay-Transaction-ID'][0]")
        }
    }
    """

    * configure responseHeaders = confHeaders


Scenario: isTest('connettivita-base') || isTest('connettivita-base-default-trustore') || isTest('connettivita-base-truststore-ca') || isTest('riferimento-x509-SKIKey-IssuerSerial') || isTest('riferimento-x509-ThumbprintKey-SKIKey') || isTest('riferimento-x509-x509Key-ThumbprintKey') ||  isTest('riferimento-x509-IssuerSerial-x509Key') || isTest('manomissione-token-risposta') || isTest('low-ttl-erogazione')
    
    * match bodyPath('/Envelope/Header') == ''
    * def responseStatus = 200
    * def response = read('classpath:test/soap/sicurezza-messaggio/response.xml')


Scenario: isTest('connettivita-base-no-sbustamento')
    
    * match bodyPath('/Envelope/Header/Security/Signature') == "#present"
    * match bodyPath('/Envelope/Header/Security/Timestamp/Created') == "#string"
    * match bodyPath('/Envelope/Header/Security/Timestamp/Expires') == "#string"
    * match bodyPath('/Envelope/Header/To') == "testsuite"
    * match bodyPath('/Envelope/Header/From/Address') == "DemoSoggettoFruitore/ApplicativoBlockingIDA01NoSbustamento"
    * match bodyPath('/Envelope/Header/MessageID') == "#uuid"

    * def body = bodyPath('/')
    * call check_signature [ {element: 'To'}, {element: 'From'}, {element: 'MessageID'}, {element: 'ReplyTo'} ]

    * def responseStatus = 200
    * def response = read('classpath:test/soap/sicurezza-messaggio/response.xml')


Scenario: isTest('response-without-payload')
    
    * match bodyPath('/Envelope/Header') == ''
    * def responseStatus = 200
    * def response = ''


Scenario: isTest('disabled-security-on-action')
    * def responseStatus = 200
    * def response = read('classpath:test/soap/sicurezza-messaggio/response-op.xml')



Scenario: isTest('enabled-security-on-action') && bodyPath('/Envelope/Body/MRequestOp') != ''
    * def responseStatus = 200
    * def response = read('classpath:test/soap/sicurezza-messaggio/response-op.xml')


Scenario: isTest('enabled-security-on-action') && bodyPath('/Envelope/Body/MRequestOp1') != ''
    * def responseStatus = 200
    * def response = read('classpath:test/soap/sicurezza-messaggio/response-op.xml')

# catch all
#
#

Scenario:
    karate.fail("Nessuno scenario matchato")