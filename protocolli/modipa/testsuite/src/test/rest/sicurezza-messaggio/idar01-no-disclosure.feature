Feature: Testing Sicurezza Messaggio ModiPA IDAR01 Senza Disclosure di informazioni sugli errori

Background:
    * def basic = read('classpath:utils/basic-auth.js')
    
    * def result = callonce read('classpath:utils/jmx-disable-error-disclosure.feature')

@no-token-to-erogazione
Scenario: All'erogazione non arriva nessun token e questa deve arrabbiarsi

Given url govway_base_path + '/rest/in/DemoSoggettoErogatore/RestBlockingIDAR01/v1'
And path 'resources', 1, 'M'
And request read('request.json')
When method post
Then status 400
And match response == read('error-bodies/interoperability-invalid-request.json')


@no-token-fruizione
Scenario: Nella risposta alla fruizione non arriva nessun token e questa deve arrabbiarsi

Given url govway_base_path + '/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestBlockingIDAR01/v1'
And path 'resources', 1, 'M'
And request read('request.json')
And header GovWay-TestSuite-Test-ID = 'no-token-fruizione'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method post
Then status 502
And match response == read('error-bodies/invalid-response.json')


@manomissione-token-richiesta
Scenario: Il payload del token di richiesta viene manomesso in modo da non far corrispondere più la firma e far arrabbiare l'erogazione

Given url govway_base_path + '/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestBlockingIDAR01NoValidazione/v1'
And path 'resources', 1, 'M'
And request read('request.json')
And header GovWay-TestSuite-Test-ID = 'manomissione-token-richiesta-no-disclosure'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method post
Then status 400
And match response == read('error-bodies/interoperability-invalid-request.json')


@manomissione-token-risposta
Scenario: Il payload del token di risposta viene manomesso in modo da non far corrispondere più la firma e far arrabbiare la fruizione

Given url govway_base_path + '/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestBlockingIDAR01NoValidazione/v1'
And path 'resources', 1, 'M'
And request read('request.json')
And header GovWay-TestSuite-Test-ID = 'manomissione-token-risposta'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method post
Then status 502
And match response == read('error-bodies/invalid-response.json')


@low-ttl-fruizione
Scenario: Il TTL del token della fruizione (richiesta) viene superato e l'erogazione si arrabbia

Given url govway_base_path + '/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestBlockingIDAR01LowTTL/v1'
And path 'resources', 1, 'M'
And request read('request.json')
And header GovWay-TestSuite-Test-ID = 'low-ttl-fruizione-no-disclosure'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method post
Then status 502


@low-ttl-erogazione
Scenario: Il ttl del token dell'erogazione (risposta) viene superato e la fruizione si arrabbia

Given url govway_base_path + '/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/RestBlockingIDAR01/v1'
And path 'resources', 1, 'M'
And request read('request.json')
And header GovWay-TestSuite-Test-ID = 'low-ttl-erogazione'
And header Authorization = call basic ({ username: 'ApplicativoBlockingIDA01', password: 'ApplicativoBlockingIDA01' })
When method post
Then status 502
And match response == read('error-bodies/invalid-response.json')

