Feature: Feature test connettività base https

Background:

* url govway_base_path + '/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoBlockingRest/v1'

Scenario: Test di Echo REST

    * def body = read("classpath:bodies/modipa-blocking-sample-request.json")
    * def resp = read("classpath:test/risposte-default/rest/bloccante/response.json")

    Given path 'resources', 1, 'M'
    And request body
    When method post
    Then status 200
    And match response == resp

Scenario: Test di Echo REST e verifica traccia sul DB

    * def body = read("classpath:bodies/modipa-blocking-sample-request.json")
    * def resp = read("classpath:test/risposte-default/rest/bloccante/response.json")

    Given path 'resources', 1, 'M'
    And request body
    When method post
    Then status 200
    And match response == resp
    
    * def DbUtils = Java.type('org.openspcoop2.core.protocolli.modipa.testsuite.DbUtils')
    * def db = new DbUtils(govwayDbConfig)

    * def id_transazione = responseHeaders['GovWay-Transaction-ID'][0]
    * def dbquery = "select * from tracce_ext_protocol_info where idtraccia=(select id from tracce where id_transazione='"+id_transazione+"' and tipo_messaggio='Richiesta')"
    * print "La query è: ", dbquery
    * def result = db.readRows(dbquery);
    * match result[0].name == "ProfiloInterazione"
