Feature: Feature test connettività base

Background:

* def createOrUpdateConfig = read('classpath:utils/config-loader/createOrUpdate.js') 
* def deleteConfig = read('classpath:utils/config-loader/delete.js') 

* callonce createOrUpdateConfig modipa_demo_test_zip
* configure afterFeature = function() { deleteConfig(modipa_demo_test_zip) }

* url govway_base_path + '/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoBlockingRest/v1'

Scenario: Test Demo di Echo

    Given path 'resources', 1, 'M'
    And request { amount: 5.67, description: 'test one' }
    When method post
    Then status 200
    And match response == { amount: 5.67, description: 'test one' }