Feature: Test per il profilo di interazione non bloccante rest di tipo PULL

Background:

# La url verso l'erogazione che effettua la validazione.
* def url_invocazione_validazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoNonBlockingRestPull/v1"

# La url verso l'erogazione che non effettua la validazione, serve per controllare gli errori ModiPA
* def url_invocazione = govway_base_path + "/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoNonBlockingRestPullNoValidazione/v1"

* def url_erogazione_validazione = govway_base_path + "/rest/in/DemoSoggettoErogatore/ApiDemoNonBlockingRestPull/v1"
* def url_erogazione_no_validazione = govway_base_path + "/rest/in/DemoSoggettoErogatore/ApiDemoNonBlockingRestPullNoValidazione/v1"

* def body_req = read('classpath:bodies/nonblocking-rest-request.json')
* def task_uid = "32bb4c13-e898-4c12-94db-4ddb18de7919"
* configure followRedirects = false

* def result = callonce read('classpath:utils/jmx-enable-error-disclosure.feature')
* configure afterFeature = function(){ karate.call('classpath:utils/jmx-disable-error-disclosure.feature'); }

@test-ok
Scenario: Giro OK

    * def result_response = read("classpath:test/risposte-default/rest/bloccante/response.json")

    Given url url_invocazione_validazione
    And path 'tasks', 'queue'
    And request body_req
    And params ({ returnCode: 202, returnHttpHeader:'Location: /tasks/queue/' + task_uid})
    When method post
    Then status 202
    And match header GovWay-Conversation-ID == task_uid

    Given url url_invocazione_validazione
    And path 'tasks', 'queue', 'not_ready_uid'
    And params ({ returnCode: 200, destFile: '/etc/govway/test/protocolli/modipa/rest/non-bloccante/pending.json', destFileContentType: 'application/json' })
    When method get
    Then status 200
    And match header GovWay-Conversation-ID == 'not_ready_uid'


    * def completed_params = 
    """
    ({ 
        returnCode: 303,
        returnHttpHeader: 'Location: http://127.0.0.1:8080/TestService/echo/tasks/result/' + task_uid,
        destFile: '/etc/govway/test/protocolli/modipa/rest/non-bloccante/completed.json',
        destFileContentType: 'application/json' 
        })
    """
    Given url url_invocazione_validazione
    And path 'tasks', 'queue', task_uid
    And params completed_params
    When method get
    Then status 303
    And match header Location == url_erogazione_validazione + "/tasks/result/" + task_uid
    And match header GovWay-Conversation-ID == task_uid

    
    Given url url_invocazione_validazione
    And path 'tasks', 'result', task_uid
    And params ({ returnCode: 200 })
    When method get
    Then status 200
    And match response == result_response
    And match header GovWay-Conversation-ID == task_uid


@location-not-an-uri
Scenario: Header Location che non corrisponde ad una URI

    * def pending_response = read('classpath:test/risposte-default/rest/non-bloccante/pending.json')
    # Il problem è fatto in questa maniera perchè l'erogazione restituisce un errore di validazione con un 
    # fault, mentre nell'openapi su questo endpoint non è specificato questo tipo di rispota.
    # Sicchè la fruizione si arrabbia dicento che il content-type application/json non è valido
    * def problem =
    """
    {
        type: "https://govway.org/handling-errors/502/InvalidResponseContent.html",
        title: "InvalidResponseContent",
        status: 502,
        detail: "Response content not conform to API specification: Validation error(s) :\nContent type 'application/problem+json' is not allowed for body content. (code: 203)\nFrom: \n",
        govway_id: "#string"
    }
    """

    Given url url_invocazione_validazione
    And path 'tasks', 'queue'
    And request body_req
    And params ({ returnCode: 202, returnHttpHeader:'Location: /tasks/queue/' + task_uid})
    When method post
    Then status 202

    * def completed_params = 
    """
    ({ 
        returnCode: 303,
        returnHttpHeader: 'Location: /non/an/URI',
        destFile: '/etc/govway/test/protocolli/modipa/rest/non-bloccante/completed.json',
        destFileContentType: 'application/json' 
    })
    """

    Given url url_invocazione_validazione
    And path 'tasks', 'queue', task_uid
    And params completed_params
    When method get
    Then status 502
    And match response == problem


@request-task-no-location
Scenario: Richiesta processamento con stato 202 e senza Header Location

    * def problem = read('classpath:test/rest/non-bloccante/error-bodies/request-task-no-location-erogazione.json')    

    Given url url_invocazione
    And path 'tasks', 'queue'
    And request body_req
    And params ({ returnCode: 202 })
    When method post
    Then status 502
    And match response == problem


@request-task-not-202
Scenario: Richiesta processamento con stato diverso da 202

    * def problem = read('classpath:test/rest/non-bloccante/error-bodies/request-task-not-202-erogazione.json')    
    
    Given url url_invocazione
    And path 'tasks', 'queue'
    And request body_req
    And params ({ returnCode: 201, returnHttpHeader:'Location: /tasks/queue/' + task_uid})
    When method post
    Then status 502
    And match response == problem


@invalid-status-from-request
Scenario: Richiesta stato operazione con stato http diverso da 200 e 303

    * def problem = read('classpath:test/rest/non-bloccante/error-bodies/invalid-status-from-request-erogazione.json')

    Given url url_invocazione
    And path 'tasks', 'queue'
    And request body_req
    And params ({ returnCode: 202, returnHttpHeader:'Location: /tasks/queue/' + task_uid})
    When method post
    Then status 202

    Given url url_invocazione
    And path 'tasks', 'queue', 'not_ready_uid'
    And params ({ returnCode: 201, destFile: '/etc/govway/test/protocolli/modipa/rest/non-bloccante/pending.json', destFileContentType: 'application/json' })
    When method get
    Then status 502
    And match response == problem


@no-location-from-status
Scenario: Richiesta stato operazione completata senza header location

    * def problem = read('classpath:test/rest/non-bloccante/error-bodies/no-location-from-status-erogazione.json')

    Given url url_invocazione
    And path 'tasks', 'queue', task_uid
    And params ({ returnCode: 303, destFile: '/etc/govway/test/protocolli/modipa/rest/non-bloccante/completed.json', destFileContentType: 'application/json' })
    When method get
    Then status 502
    And match response == problem


@task-response-not-200
Scenario: Ottenimento risorsa processata con stato diverso da 200 OK    

    * def problem = read('classpath:test/rest/non-bloccante/error-bodies/task-response-not-200-erogazione.json')

    Given url url_invocazione
    And path 'tasks', 'queue'
    And request body_req
    And params ({ returnCode: 202, returnHttpHeader:'Location: /tasks/queue/' + task_uid})
    When method post
    Then status 202

    * def completed_params = 
    """
    ({ 
        returnCode: 303,
        returnHttpHeader: 'Location: http://127.0.0.1:8080/TestService/echo/tasks/result/' + task_uid,
        destFile: '/etc/govway/test/protocolli/modipa/rest/non-bloccante/completed.json',
        destFileContentType: 'application/json' 
        })
    """
    Given url url_invocazione
    And path 'tasks', 'queue', task_uid
    And params completed_params
    When method get
    Then status 303
    And match header Location == url_erogazione_no_validazione + "/tasks/result/" + task_uid

    Given url url_invocazione
    And path 'tasks', 'result', task_uid
    And params ({ returnCode: 202 })
    When method get
    Then status 502
    And match response == problem

