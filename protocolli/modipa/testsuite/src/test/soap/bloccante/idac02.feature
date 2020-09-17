Feature: Testing feature idac02 SOAP

Scenario: IDAC02 Autenticazione Client

* def body = read("classpath:bodies/modipa-blocking-sample-request-soap.xml")
* def resp = read("classpath:test/risposte-default/soap/bloccante/response.xml")
* def soap_url = govway_base_path + '/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoBlockingSoapIDAC02/v1'

Given url soap_url
And request body
And header Content-Type = 'application/soap+xml'
And header action = soap_url
When method post
Then status 200
And match response == resp


Scenario: IDAC02 Autenticazione Client Assente

* def body = read("classpath:bodies/modipa-blocking-sample-request-soap.xml")
* def soap_url = govway_base_path + '/soap/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoBlockingSoapIDAC02BadAuth/v1'

* def code_error = 
 """
  <env:Code>
    <env:Value>env:Sender</env:Value>
    <env:Subcode>
        <env:Value xmlns:integration="http://govway.org/integration/fault">integration:AuthenticationRequired</env:Value>
    </env:Subcode>
  </env:Code>
"""

* def detail_error = 
"""
<env:Detail>
    <problem xmlns="urn:ietf:rfc:7807">
        <type>https://govway.org/handling-errors/401/AuthenticationRequired.html</type>
        <title>AuthenticationRequired</title>
        <status>401</status>
        <detail>Authentication required</detail>
        <govway_id>#string</govway_id>
    </problem>
</env:Detail>
"""

Given url soap_url
And request body
And header Content-Type = 'application/soap+xml'
And header action = soap_url
When method post
Then status 500
And match /Envelope/Body/Fault/Reason/Text == "Authentication required"
And match /Envelope/Body/Fault/Code == code_error
And match /Envelope/Body/Fault/Detail == detail_error