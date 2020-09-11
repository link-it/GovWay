Feature: payment service contract test

Background:

* def createOrUpdateConfig = read('classpath:utils/config-loader/createOrUpdate.js') 
* def deleteConfig = read('classpath:utils/config-loader/delete.js') 

* callonce createOrUpdateConfig modipa_demo_test_http_zip
* configure afterFeature = function() { deleteConfig(modipa_demo_test_http_zip) }

* url url_invocazione_fruizione

Scenario: Test Demo con mock proxy

    Given path 'resources', 1, 'M'
    And request { amount: 5.67, description: 'test one' }
    When method post
    Then status 200
    And match response == { amount: 5.67, description: 'test one' }