Feature: Controllo traccia IDAS01

Scenario: Controllo traccia IDAS01


* def get_traccia = read('classpath:utils/get_traccia.js')
* def traccia_to_match = 
"""
([
    { name: 'ProfiloInterazione', value: 'bloccante' },
    { name: 'ProfiloSicurezzaCanale', value: 'IDAC01' },
    { name: 'ProfiloSicurezzaMessaggio', value: 'IDAS01' },
    { name: 'ProfiloSicurezzaMessaggio-X509-Subject', value: token.x509sub },
    { name: 'ProfiloSicurezzaMessaggio-X509-Issuer', value: 'CN=ExampleCA, O=Example, L=Pisa, ST=Italy, C=IT' },
    { name: 'ProfiloSicurezzaMessaggio-IssuedAt', value: '#string' },
    { name: 'ProfiloSicurezzaMessaggio-Expiration', value: '#string' },
    { name: 'ProfiloSicurezzaMessaggio-WSA-To', value: token.wsa_to },
    { name: 'ProfiloSicurezzaMessaggio-WSA-From', value: token.wsa_from },
    { name: 'ProfiloSicurezzaMessaggio-MessageId', value: token.message_id },
])
"""

 * def result = get_traccia(tid,tipo) 
 * match result contains deep traccia_to_match