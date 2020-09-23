
Feature: Test Della fruizione con mock proxy per il profilo di interazione non bloccante rest di tipo PULL


# Qui partono i test che devono fallire lato mock, il quale prima fa il forward della richiesta
# All'erogazione, che deve essere giusta, successivamente fa le modifiche e le restituisce alla fruizione
# La fruizione si arrabbierà e otterremo quindi lato client gli stessi errori che avrebbe dovuto generare 
# l'erogazione


Background:
    * def body_req = read('classpath:bodies/nonblocking-rest-request.json')
    
    * configure followRedirects = false

    * def url_invocazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoNonBlockingRestPullProxy/v1"
    * url url_invocazione

    * def invalid_implementation_response =
    """
    {
        title: "InvalidResponse",
        status :502,
        detail: "Invalid response received from the API Implementation"
    }
    """


Scenario: Test Fruizione con header location rimosso dal proxy

    * def task_id = "Test-Location-Removed-From-Ack"

    Given path 'tasks', 'queue'
    And request body_req
    And params ({ returnCode: 202, returnHttpHeader:'Location: /tasks/queue/' + task_id})
    When method post
    Then status 502
    And match response contains invalid_implementation_response


Scenario: Richiesta processamento con stato diverso da 202

    * def task_id = "Test-Status-Not-202"
    
    Given path 'tasks', 'queue'
    And request body_req
    And params ({ returnCode: 202, returnHttpHeader:'Location: /tasks/queue/' + task_id })
    When method post
    Then status 502
    And match response contains invalid_implementation_response


Scenario: Header Location che non corrisponde ad una URI

    * def task_id = "Test-Location-Not-An-URI"
    * def completed_params = 
    """
    ({ 
        returnCode: 303,
        returnHttpHeader: 'Location: http://127.0.0.1:8080/TestService/echo/tasks/result/' + task_id,
        destFile: '/etc/govway/test/protocolli/modipa/rest/non-bloccante/completed.json',
        destFileContentType: 'application/json' 
        })
    """
    Given path 'tasks', 'queue', task_id
    And params completed_params
    When method get
    Then status 502
    And match response contains invalid_implementation_response


Scenario: Richiesta stato operazione con stato http diverso da 200 e 303

    * def task_id = "Test-Invalid-Status-Request"

    Given path 'tasks', 'queue', task_id
    And params ({ returnCode: 200, destFile: '/etc/govway/test/protocolli/modipa/rest/non-bloccante/pending.json', destFileContentType: 'application/json' })
    When method get
    Then status 502
    And match response contains invalid_implementation_response


@no-location-from-status
Scenario: Richiesta stato operazione completata senza header location

    * def task_id = "Test-Location-Removed-From-Status"
    * def completed_params = 
    """
    ({ 
        returnCode: 303,
        returnHttpHeader: 'Location: http://127.0.0.1:8080/TestService/echo/tasks/result/' + task_id,
        destFile: '/etc/govway/test/protocolli/modipa/rest/non-bloccante/completed.json',
        destFileContentType: 'application/json' 
        })
    """
    Given path 'tasks', 'queue', task_id
    And params completed_params
    When method get
    Then status 502
    And match response contains invalid_implementation_response


@task-response-not-200
Scenario: Ottenimento risorsa processata con stato diverso da 200 OK    

    * def task_id = "Test-Response-Not-200"

    Given path 'tasks', 'result', task_id
    And params ({ returnCode: 200 })
    When method get
    Then status 502
    And match response contains invalid_implementation_response

