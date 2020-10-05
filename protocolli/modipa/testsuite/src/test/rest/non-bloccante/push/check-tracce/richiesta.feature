Feature: Controllo traccia richiesta IDAC01 su fruizione ed erogazione per profilo Non Bloccante Rest

Scenario: Controllo traccia richiesta IDAC01 su fruizione ed erogazione per profilo Non Bloccante Rest


* def get_traccia = read('classpath:utils/get_traccia.js')
* def traccia_to_match = 
"""
[
    { name: 'ProfiloInterazione', value: 'nonBloccante' },
    { name: 'ProfiloSicurezzaCanale', value: 'IDAC01' },
    { name: 'ProfiloInterazioneAsincrona-Tipo', value: 'PUSH' },
    { name: 'ProfiloInterazioneAsincrona-Ruolo', value: 'Richiesta' },
    { name: 'ProfiloInterazioneAsincrona-ReplyTo', value: 'http://localhost:8080/govway/rest/in/DemoSoggettoFruitore/RestNonBlockingPushClient/v1' }
]
"""

 * def result = get_traccia(tid) 
 * match result contains deep traccia_to_match

#  * def result = get_traccia(responseHeaders['GovWay-TestSuite-GovWay-Transaction-ID'][0]) 
#  * match result contains deep traccia_to_match