.. _errori_503:

Errori 503 (Service Unavailable)
--------------------------------

In questa sezione vengono riportati tutti i possibili codici di errore generati da GovWay relativi ad errori emersi durante la gestione della richiesta. Gli errori sono classificabili in:

- indisponibilità temporanea del backend che implementa l'API; l'errore viene identificato dal codice di errore 'APIUnavailable';
- accesso all'API sospeso su GovWay; l'errore viene identificato dal codice di errore 'APISuspended';
- il gateway non è al momento in grado di gestire la richiesta; rientrano in questa casistica i codici di errore: AttachmentsRequestFailed, MessageSecurityRequestFailed, InteroperabilitRequestManagementFailed, TransformationRuleRequestFailed;
- Il gateway non è al momento correttamente operativo; rientrano in questa casistica i codici di errore: GatewayInactive, GatewayUnavailable, GatewayError.

Nella configurazione di default di GovWay, gli errori che indicano una indisponibilità temporanea del gateway sono tutti restituiti al client con il solo codice di errore :ref:`errori_503_APIUnavailable`. La scelta è finalizzata ad evitare disclosure di informazioni relative al domino interno.

È possibile abilitare temporaneamente la generazione dei codici specifici accendendo alla voce 'Strumenti - Runtime' della console di gestione e abilitando 'Gestione Richiesta' o 'Errori Interni' nella sezione 'Errori generati dal Gateway' (:numref:`error503specifici`).

   .. figure:: ../../_figure_console/errori503.png
    :scale: 50%
    :align: center
    :name: error503specifici

    Attivazione temporanea degli errori specifici 503 (Service Unavailable)

L'abilitazione permanente può essere invece effettuata disabilitando le seguenti proprietà sul file di proprietà esterno /etc/govway/errori_local.properties:

	::

		# Gateway non in grado di gestire la richiesta: AttachmentsRequestFailed, MessageSecurityRequestFailed, InteroperabilitRequestManagementFailed, TransformationRuleRequestFailed
		WRAP_503_INTERNAL_REQUEST_ERROR.enabled=false
		# Gateway momentaneamente indisponibile: GatewayInactive, GatewayUnavailable, GatewayError 
		WRAP_503_INTERNAL_REQUEST_ERROR_GOVWAY.enabled=false

.. toctree::
        :maxdepth: 2
        
        APIUnavailable
	APISuspended
	AttachmentsRequestFailed
	MessageSecurityRequestFailed
	InteroperabilityRequestManagementFailed
	TransformationRuleRequestFailed
	GatewayInactive
	GatewayUnavailable
	GatewayError

