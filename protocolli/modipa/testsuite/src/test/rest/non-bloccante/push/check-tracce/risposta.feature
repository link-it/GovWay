Feature: Controllo traccia risposta IDAC01 su fruizione ed erogazione per profilo Non Bloccante Rest

Scenario: Controllo traccia risposta IDAC01 su fruizione ed erogazione per profilo Non Bloccante Rest


# TODO: Ci manca ProfiloInterazioneAsincrona-ApiCorrelata
* def get_traccia = read('classpath:utils/get_traccia.js')
* def traccia_to_match = 
"""
([
    { name: 'ProfiloInterazione', value: 'nonBloccante' },
    { name: 'ProfiloSicurezzaCanale', value: 'IDAC01' },
    { name: 'ProfiloInterazioneAsincrona-Tipo', value: 'PUSH' },
    { name: 'ProfiloInterazioneAsincrona-Ruolo', value: 'Risposta' },
    { name: 'ProfiloInterazioneAsincrona-RisorsaCorrelata', value: 'POST /resources/{id_resource}/M' },
    { name: 'ProfiloInterazioneAsincrona-CorrelationID', value: cid },
    { name: 'ProfiloInterazioneAsincrona-ApiCorrelata', value: api_correlata }
])
"""


* def result = get_traccia(tid, 'Richiesta')
* match result contains deep traccia_to_match

# Nella risposta della risposta non c'è il correlation ID

* def traccia_to_match = 
"""
([
    { name: 'ProfiloInterazione', value: 'nonBloccante' },
    { name: 'ProfiloSicurezzaCanale', value: 'IDAC01' },
    { name: 'ProfiloInterazioneAsincrona-Tipo', value: 'PUSH' },
    { name: 'ProfiloInterazioneAsincrona-Ruolo', value: 'Risposta' },
    { name: 'ProfiloInterazioneAsincrona-RisorsaCorrelata', value: 'POST /resources/{id_resource}/M' }
])
"""

* def result = get_traccia(tid, 'Risposta')
* match result contains deep traccia_to_match