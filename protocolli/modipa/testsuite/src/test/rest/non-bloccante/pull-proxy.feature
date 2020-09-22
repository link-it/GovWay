
Feature: Test Della fruizione con mock proxy per il profilo di interazione non bloccante rest di tipo PULL

Background:
    * def body_req = read('classpath:bodies/nonblocking-rest-request.json')
    * def task_uid = "32bb4c13-e898-4c12-94db-4ddb18de7919"
    * configure followRedirects = false

    * def url_invocazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoNonBlockingRestPullProxy/v1"
    * url url_invocazione


Scenario: Test Fruizione con header location rimosso dal proxy

    * def task_uid_location_removed = "32bb4c13-e898-4c12-94db-4ddb18de7919_Location_Removed"

    Given path 'tasks', 'queue'
    And request body_req
    And params ({ returnCode: 202, returnHttpHeader:'Location: /tasks/queue/' + task_uid_location_removed})
    When method post
    Then status 502

