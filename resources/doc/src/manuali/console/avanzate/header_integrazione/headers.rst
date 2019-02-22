.. _headerIntegrazione_other:


Altri header di Integrazione
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Per attivare differenti header di integrazione è richiesto l'accesso
alla govwayConsole in modalità *avanzata* (Sezione :ref:`modalitaAvanzata`).

.. note::
    Gli header di trasporto relativi alle funzionalità di Rate-Limiting
    e Service-Unavailable, descritti nella sezione :ref:`headerRisposta`, vengono generati
    solamente nella modalità *Header HTTP*.

A partire dall'erogazione o fruizione già creata in precedenza accedendo
tramite il link Gestione Configurazione presente nel dettaglio
dell'erogazione/fruizione è possibile accedere a configurazioni
specifiche come descritto nella sezione :ref:`configSpecifica`. Accedendo in modalità avanzata compare
una sezione precedentemente non documentata denominata *Opzioni
Avanzate*. All'interno di tale sezione è possibile agire sulla
configurazione della voce *Metadati* nella sezione *Integrazione*. In
tale campo, per default non impostato, è possibile attivare gli header
di integrazione desiderati utilizzando le seguenti keyword separate da
virgola:

.. note::
    Per ogni tipo di header di integrazione descritto di seguito, esiste
    una modalità normale ed una estesa (suffisso 'Ext'). Le due modalità
    si differenziano poichè nella modalità non estesa non vengono
    generati gli header nella richiesta inoltrata al server in una
    fruizione e quelli generati nella risposta ritornata al client in
    una erogazione.

-  *trasporto* o *trasportoExt*: header di trasporto descritti nelle
   precedenti sezioni.

-  *urlBased* o *urlBasedExt*: le informazioni precedentemente descritte
   vengono aggiunte alla url tramite i parametri descritti nella :numref:`headerGw2SrvUrlTab`.

.. table:: Informazioni generate dal gateway nella url della richiesta inoltrata al server
   :widths: auto
   :name: headerGw2SrvUrlTab

   ==================================  =========================================================
   Nome Query URL Parameter            Descrizione
   ==================================  =========================================================
   govway_message_id                  	Identificativo del messaggio assegnato da GovWay
   govway_relates_to                  	Identificativo del messaggio riferito
   govway_conversation_id             	Identificativo della conversazione
   govway_transaction_id              	Identificativo della transazione assegnato da GovWay
   govway_sender_type                 	Codice che identifica il tipo del mittente
   govway_sender                       	Identificativo del mittente
   govway_provider_type               	Codice che identifica il tipo del destinatario
   govway_provider                     	Identificativo del destinatario
   govway_service_type                	Codice che identifica il tipo del servizio
   govway_service                      	Identificativo del servizio
   govway_service_version             	Progressivo di versione del servizio
   govway_action                       	Identificativo dell'azione
   govway_application_message_id     	Identificativo del messaggio assegnato dall'applicativo
   govway_application                  	Identificativo dell'applicativo
   ==================================  =========================================================

.. _notaUrlBased:

.. note::
   Esiste una terza versione *urlBasedOnlyRead* che permette di
   attivare la lettura delle informazioni impostate dall'applicativo
   client ma non genera header verso l'applicativo server.

-  *soap* o *soapExt*: le informazioni precedentemente descritte vengono
   incluse come attributi in uno specifico header SOAP proprietario di
   GovWay che possiede il nome *integration* associato al namespace
   *http://govway.org/integration*. Di seguito un esempio di tale
   header:

   ::

       <gw:integration 
               ...
               transactionId="a2c6fd66-ec0b-407c-8a21-25b4920e7c73"
               SOAP_ENV:actor="http://govway.org/integration" 
               SOAP_ENV:mustUnderstand="0" 
               xmlns:SOAP_ENV="http://schemas.xmlsoap.org/soap/envelope/"
               xmlns:gw="http://govway.org/integration"/>

   Nella tabella :numref:`headerGwSoapTab` vengono descritti i nome degli attributi.

.. table:: Informazioni generate dal gateway nell'header soap proprietario di GovWay
   :widths: auto
   :name: headerGwSoapTab

   ========================     ===============
   Nome Attributo               Descrizione
   ========================     ===============
   messageId                    Identificativo del messaggio assegnato da GovWay
   relatesTo                    Identificativo del messaggio riferito
   conversationId               Identificativo della conversazione
   transactionId                Identificativo della transazione assegnato da GovWay
   senderType                   Codice che identifica il tipo del mittente
   sender                       Identificativo del mittente
   providerType                 Codice che identifica il tipo del destinatario
   provider                     Identificativo del destinatario
   serviceType                  Codice che identifica il tipo del servizio
   service                      Identificativo del servizio
   serviceVersion               Progressivo di versione del servizio
   action                       Identificativo dell'azione
   applicationMessageId         Identificativo del messaggio assegnato dall'applicativo
   application                  Identificativo dell'applicativo
   ========================     ===============

.. _notaHeaderGwSoapTab:

.. note::
   Utilizzabile solamente con API di tipologia SOAP

-  *wsa* o *wsaExt*: all'interno del messaggio Soap vengono generati gli
   header *To*, *From*, *Action*, *MessageID* e *RelatesTo* associati al
   namespace *http://www.w3.org/2005/08/addressing*. I valori utilizzati
   per i vari header sono i seguenti:

   -  *To*,
      http://<providerType>\_<provider>.govway.org/services/<serviceType>\_<service>/<serviceVersion>

   -  *From*, http://[<application>.]<senderType>\_<sender>.govway.org

   -  *Action*,
      http://<providerType>\_<provider>.govway.org/services/<serviceType>\_<service>/<serviceVersion>/<action>

   -  *MessageID*, uuid:<messageId> in caso di Messaggio di Protocollo
      (restituzione di una risposto lato PD o in caso di consegna
      tramite PA), uuid:<applicationMessageId> in caso di Messaggio di
      Integrazione (invocazione lato PD o lettura risposta lato PA, es.
      per correlazione applicativa)

   -  *RelatesTo*, uuid:<relatesTo>

.. note::
   Utilizzabile solamente con API di tipologia SOAP