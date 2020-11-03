-- **** Porte Applicative ****

CREATE TABLE porte_applicative
(
	nome_porta VARCHAR(2000) NOT NULL,
	descrizione VARCHAR(255),
	-- Soggetto Virtuale
	id_soggetto_virtuale BIGINT,
	tipo_soggetto_virtuale VARCHAR(255),
	nome_soggetto_virtuale VARCHAR(255),
	-- Servizio
	id_servizio BIGINT,
	tipo_servizio VARCHAR(255) NOT NULL,
	servizio VARCHAR(255) NOT NULL,
	versione_servizio INT NOT NULL DEFAULT 1,
	id_accordo BIGINT,
	id_port_type BIGINT,
	-- azione
	azione VARCHAR(255),
	mode_azione VARCHAR(255),
	pattern_azione VARCHAR(255),
	nome_porta_delegante_azione VARCHAR(255),
	-- abilitato/disabilitato
	force_interface_based_azione VARCHAR(255),
	-- disable/packaging/unpackaging/verify
	mtom_request_mode VARCHAR(255),
	-- disable/packaging/unpackaging/verify
	mtom_response_mode VARCHAR(255),
	-- abilitato/disabilitato (se abilitato le WSSproperties sono presenti nelle tabelle ...._security_request/response)
	security VARCHAR(255),
	-- abilitato/disabilitato
	security_mtom_req VARCHAR(255),
	-- abilitato/disabilitato
	security_mtom_res VARCHAR(255),
	security_request_mode VARCHAR(255),
	security_response_mode VARCHAR(255),
	-- abilitato/disabilitato
	ricevuta_asincrona_sim VARCHAR(255),
	-- abilitato/disabilitato
	ricevuta_asincrona_asim VARCHAR(255),
	-- abilitato/disabilitato/warningOnly
	validazione_contenuti_stato VARCHAR(255),
	-- openspcoop/interface/xsd/json/pattern
	validazione_contenuti_tipo VARCHAR(255),
	-- abilitato/disabilitato
	validazione_contenuti_mtom VARCHAR(255),
	-- abilitato/disabilitato
	validazione_contenuti_soapa VARCHAR(255),
	-- Nome interfaccia json
	validazione_contenuti_json VARCHAR(255),
	-- abilitato/disabilitato
	validazione_contenuti_pat_and VARCHAR(255),
	-- abilitato/disabilitato
	validazione_contenuti_pat_not VARCHAR(255),
	-- lista di tipi separati dalla virgola
	integrazione VARCHAR(255),
	-- scadenza correlazione applicativa
	scadenza_correlazione_appl VARCHAR(255),
	-- abilitato/disabilitato
	allega_body VARCHAR(255),
	-- abilitato/disabilitato
	scarta_body VARCHAR(255),
	-- abilitato/disabilitato
	gestione_manifest VARCHAR(255),
	-- abilitato/disabilitato
	stateless VARCHAR(255),
	behaviour VARCHAR(255),
	-- Controllo Accessi
	autenticazione VARCHAR(255),
	-- abilitato/disabilitato
	autenticazione_opzionale VARCHAR(255),
	-- Gestione Token
	token_policy VARCHAR(255),
	token_opzionale VARCHAR(255),
	token_validazione VARCHAR(255),
	token_introspection VARCHAR(255),
	token_user_info VARCHAR(255),
	token_forward VARCHAR(255),
	token_options VARCHAR(4000),
	token_authn_issuer VARCHAR(255),
	token_authn_client_id VARCHAR(255),
	token_authn_subject VARCHAR(255),
	token_authn_username VARCHAR(255),
	token_authn_email VARCHAR(255),
	-- Autorizzazione
	autorizzazione VARCHAR(255),
	autorizzazione_xacml VARCHAR(max),
	autorizzazione_contenuto VARCHAR(255),
	-- all/any
	ruoli_match VARCHAR(255),
	scope_stato VARCHAR(255),
	-- all/any
	scope_match VARCHAR(255),
	-- abilitato/disabilitato
	ricerca_porta_azione_delegata VARCHAR(255),
	-- Livello Log Messaggi Diagnostici
	msg_diag_severita VARCHAR(255),
	tracciamento_esiti VARCHAR(255),
	-- Gestione CORS
	cors_stato VARCHAR(255),
	cors_tipo VARCHAR(255),
	cors_all_allow_origins VARCHAR(255),
	cors_all_allow_methods VARCHAR(255),
	cors_all_allow_headers VARCHAR(255),
	cors_allow_credentials VARCHAR(255),
	cors_allow_max_age INT,
	cors_allow_max_age_seconds INT,
	cors_allow_origins VARCHAR(max),
	cors_allow_headers VARCHAR(max),
	cors_allow_methods VARCHAR(max),
	cors_allow_expose_headers VARCHAR(max),
	-- Response caching
	response_cache_stato VARCHAR(255),
	response_cache_seconds INT,
	response_cache_max_msg_size BIGINT,
	response_cache_hash_url VARCHAR(255),
	response_cache_hash_query VARCHAR(255),
	response_cache_hash_query_list VARCHAR(max),
	response_cache_hash_headers VARCHAR(255),
	response_cache_hash_hdr_list VARCHAR(max),
	response_cache_hash_payload VARCHAR(255),
	response_cache_control_nocache INT,
	response_cache_control_maxage INT,
	response_cache_control_nostore INT,
	id_sa_default BIGINT,
	-- Stato della porta: abilitato/disabilitato
	stato VARCHAR(255),
	-- proprietario porta applicativa
	id_soggetto BIGINT NOT NULL,
	ora_registrazione DATETIME2 DEFAULT CURRENT_TIMESTAMP,
	options VARCHAR(4000),
	canale VARCHAR(255),
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT unique_porte_applicative_1 UNIQUE (nome_porta),
	-- fk/pk keys constraints
	CONSTRAINT fk_porte_applicative_1 FOREIGN KEY (id_soggetto) REFERENCES soggetti(id),
	CONSTRAINT pk_porte_applicative PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_porte_applicative_1 ON porte_applicative (nome_porta);
CREATE INDEX index_porte_applicative_2 ON porte_applicative (id_soggetto);
CREATE INDEX index_porte_applicative_3 ON porte_applicative (canale);



CREATE TABLE porte_applicative_sa
(
	id_porta BIGINT NOT NULL,
	id_servizio_applicativo BIGINT NOT NULL,
	-- Dati Connettore
	connettore_nome VARCHAR(255),
	connettore_notifica INT,
	connettore_descrizione VARCHAR(4000),
	connettore_stato VARCHAR(255),
	connettore_filtri VARCHAR(max),
	connettore_coda VARCHAR(10) DEFAULT 'DEFAULT',
	connettore_priorita VARCHAR(10) DEFAULT 'DEFAULT',
	connettore_max_priorita INT DEFAULT 0,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT unique_porte_applicative_sa_1 UNIQUE (id_porta,id_servizio_applicativo),
	-- fk/pk keys constraints
	CONSTRAINT fk_porte_applicative_sa_1 FOREIGN KEY (id_servizio_applicativo) REFERENCES servizi_applicativi(id),
	CONSTRAINT fk_porte_applicative_sa_2 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_porte_applicative_sa PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_SA ON porte_applicative_sa (id_porta);
CREATE INDEX INDEX_PA_SA_CODA ON porte_applicative_sa (connettore_coda,connettore_priorita,id_servizio_applicativo);
CREATE INDEX INDEX_PA_SA_CODA_MAX ON porte_applicative_sa (connettore_coda,connettore_max_priorita,id_servizio_applicativo);



CREATE TABLE pa_sa_properties
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT uniq_pa_sa_props_1 UNIQUE (id_porta,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_sa_properties_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative_sa(id),
	CONSTRAINT pk_pa_sa_properties PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_SA_PROP ON pa_sa_properties (id_porta);



CREATE TABLE pa_behaviour_props
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(4000) NOT NULL,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT uniq_pa_behaviour_props_1 UNIQUE (id_porta,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_behaviour_props_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_behaviour_props PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_BEHAVIOUR_PROP ON pa_behaviour_props (id_porta);



CREATE TABLE pa_auth_properties
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT uniq_pa_auth_props_1 UNIQUE (id_porta,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_auth_properties_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_auth_properties PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_AUTH_PROP ON pa_auth_properties (id_porta);



CREATE TABLE pa_authz_properties
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT uniq_pa_authz_props_1 UNIQUE (id_porta,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_authz_properties_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_authz_properties PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_AUTHZ_PROP ON pa_authz_properties (id_porta);



CREATE TABLE pa_authzc_properties
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT uniq_pa_authzc_props_1 UNIQUE (id_porta,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_authzc_properties_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_authzc_properties PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_AUTHZC_PROP ON pa_authzc_properties (id_porta);



CREATE TABLE pa_properties
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT uniq_pa_properties_1 UNIQUE (id_porta,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_properties_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_properties PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_PROP ON pa_properties (id_porta);



CREATE TABLE pa_mtom_request
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	pattern VARCHAR(max) NOT NULL,
	content_type VARCHAR(255),
	required INT NOT NULL,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_mtom_request_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_mtom_request PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_MTOMTREQ ON pa_mtom_request (id_porta);



CREATE TABLE pa_mtom_response
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	pattern VARCHAR(max) NOT NULL,
	content_type VARCHAR(255),
	required INT NOT NULL,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_mtom_response_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_mtom_response PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_MTOMTRES ON pa_mtom_response (id_porta);



CREATE TABLE pa_security_request
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(max) NOT NULL,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_security_request_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_security_request PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_WSSREQ ON pa_security_request (id_porta);



CREATE TABLE pa_security_response
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(max) NOT NULL,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_security_response_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_security_response PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_WSSRES ON pa_security_response (id_porta);



CREATE TABLE pa_correlazione
(
	id_porta BIGINT NOT NULL,
	nome_elemento VARCHAR(255),
	-- modalita di scelta user input, content-based, url-based, disabilitato
	mode_correlazione VARCHAR(255),
	-- pattern utilizzato solo per content/url based
	pattern VARCHAR(max),
	-- blocca/accetta
	identificazione_fallita VARCHAR(255),
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_correlazione_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_correlazione PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_CORR_REQ ON pa_correlazione (id_porta);



CREATE TABLE pa_correlazione_risposta
(
	id_porta BIGINT NOT NULL,
	nome_elemento VARCHAR(255),
	-- modalita di scelta user input, content-based, url-based, disabilitato
	mode_correlazione VARCHAR(255),
	-- pattern utilizzato solo per content/url based
	pattern VARCHAR(max),
	-- blocca/accetta
	identificazione_fallita VARCHAR(255),
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_correlazione_risposta_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_correlazione_risposta PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_CORR_RES ON pa_correlazione_risposta (id_porta);



CREATE TABLE pa_ruoli
(
	id_porta BIGINT NOT NULL,
	ruolo VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT unique_pa_ruoli_1 UNIQUE (id_porta,ruolo),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_ruoli_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_ruoli PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_ruoli_1 ON pa_ruoli (id_porta,ruolo);



CREATE TABLE pa_scope
(
	id_porta BIGINT NOT NULL,
	scope VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT unique_pa_scope_1 UNIQUE (id_porta,scope),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_scope_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_scope PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_scope_1 ON pa_scope (id_porta,scope);



CREATE TABLE pa_soggetti
(
	id_porta BIGINT NOT NULL,
	tipo_soggetto VARCHAR(255) NOT NULL,
	nome_soggetto VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT unique_pa_soggetti_1 UNIQUE (id_porta,tipo_soggetto,nome_soggetto),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_soggetti_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_soggetti PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_soggetti_1 ON pa_soggetti (id_porta,tipo_soggetto,nome_soggetto);



CREATE TABLE porte_applicative_sa_auth
(
	id_porta BIGINT NOT NULL,
	id_servizio_applicativo BIGINT NOT NULL,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT uniq_pa_sa_auth_1 UNIQUE (id_porta,id_servizio_applicativo),
	-- fk/pk keys constraints
	CONSTRAINT fk_porte_applicative_sa_auth_1 FOREIGN KEY (id_servizio_applicativo) REFERENCES servizi_applicativi(id),
	CONSTRAINT fk_porte_applicative_sa_auth_2 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_porte_applicative_sa_auth PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_SA_AUTH ON porte_applicative_sa_auth (id_porta);



CREATE TABLE pa_azioni
(
	id_porta BIGINT NOT NULL,
	azione VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT unique_pa_azioni_1 UNIQUE (id_porta,azione),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_azioni_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_azioni PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_azioni_1 ON pa_azioni (id_porta,azione);



CREATE TABLE pa_cache_regole
(
	id_porta BIGINT NOT NULL,
	status_min INT,
	status_max INT,
	fault INT DEFAULT 0,
	cache_seconds INT,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_cache_regole_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_cache_regole PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_CACHE ON pa_cache_regole (id_porta);



CREATE TABLE pa_transform
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	posizione INT NOT NULL,
	stato VARCHAR(255),
	applicabilita_azioni VARCHAR(max),
	applicabilita_ct VARCHAR(max),
	applicabilita_pattern VARCHAR(max),
	req_conversione_enabled INT NOT NULL DEFAULT 0,
	req_conversione_tipo VARCHAR(255),
	req_conversione_template VARBINARY(MAX),
	req_content_type VARCHAR(255),
	rest_transformation INT NOT NULL DEFAULT 0,
	rest_method VARCHAR(255),
	rest_path VARCHAR(4000),
	soap_transformation INT NOT NULL DEFAULT 0,
	soap_version VARCHAR(255),
	soap_action VARCHAR(255),
	soap_envelope INT NOT NULL DEFAULT 0,
	soap_envelope_as_attach INT NOT NULL DEFAULT 0,
	soap_envelope_tipo VARCHAR(255),
	soap_envelope_template VARBINARY(MAX),
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT unique_pa_transform_1 UNIQUE (id_porta,nome),
	CONSTRAINT unique_pa_transform_2 UNIQUE (id_porta,posizione),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_transform PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_transform_1 ON pa_transform (id_porta,nome);
CREATE UNIQUE INDEX index_pa_transform_2 ON pa_transform (id_porta,posizione);



CREATE TABLE pa_transform_soggetti
(
	id_trasformazione BIGINT NOT NULL,
	tipo_soggetto VARCHAR(255) NOT NULL,
	nome_soggetto VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT unique_pa_transform_soggetti_1 UNIQUE (id_trasformazione,tipo_soggetto,nome_soggetto),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_soggetti_1 FOREIGN KEY (id_trasformazione) REFERENCES pa_transform(id),
	CONSTRAINT pk_pa_transform_soggetti PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_transform_soggetti_1 ON pa_transform_soggetti (id_trasformazione,tipo_soggetto,nome_soggetto);



CREATE TABLE pa_transform_sa
(
	id_trasformazione BIGINT NOT NULL,
	id_servizio_applicativo BIGINT NOT NULL,
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT unique_pa_transform_sa_1 UNIQUE (id_trasformazione,id_servizio_applicativo),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_sa_1 FOREIGN KEY (id_servizio_applicativo) REFERENCES servizi_applicativi(id),
	CONSTRAINT fk_pa_transform_sa_2 FOREIGN KEY (id_trasformazione) REFERENCES pa_transform(id),
	CONSTRAINT pk_pa_transform_sa PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_transform_sa_1 ON pa_transform_sa (id_trasformazione,id_servizio_applicativo);



CREATE TABLE pa_transform_hdr
(
	id_trasformazione BIGINT NOT NULL,
	tipo VARCHAR(255) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(max),
	identificazione_fallita VARCHAR(255),
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_hdr_1 FOREIGN KEY (id_trasformazione) REFERENCES pa_transform(id),
	CONSTRAINT pk_pa_transform_hdr PRIMARY KEY (id)
);

-- index
CREATE INDEX idx_pa_trasf_hdr_1 ON pa_transform_hdr (id_trasformazione);



CREATE TABLE pa_transform_url
(
	id_trasformazione BIGINT NOT NULL,
	tipo VARCHAR(255) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(max),
	identificazione_fallita VARCHAR(255),
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_url_1 FOREIGN KEY (id_trasformazione) REFERENCES pa_transform(id),
	CONSTRAINT pk_pa_transform_url PRIMARY KEY (id)
);

-- index
CREATE INDEX idx_pa_trasf_url_1 ON pa_transform_url (id_trasformazione);



CREATE TABLE pa_transform_risp
(
	id_trasformazione BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	posizione INT NOT NULL,
	applicabilita_status_min INT,
	applicabilita_status_max INT,
	applicabilita_ct VARCHAR(max),
	applicabilita_pattern VARCHAR(max),
	conversione_enabled INT NOT NULL DEFAULT 0,
	conversione_tipo VARCHAR(255),
	conversione_template VARBINARY(MAX),
	content_type VARCHAR(255),
	return_code INT,
	soap_envelope INT NOT NULL DEFAULT 0,
	soap_envelope_as_attach INT NOT NULL DEFAULT 0,
	soap_envelope_tipo VARCHAR(255),
	soap_envelope_template VARBINARY(MAX),
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT uniq_pa_trasf_resp_1 UNIQUE (id_trasformazione,nome),
	CONSTRAINT uniq_pa_trasf_resp_2 UNIQUE (id_trasformazione,posizione),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_risp_1 FOREIGN KEY (id_trasformazione) REFERENCES pa_transform(id),
	CONSTRAINT pk_pa_transform_risp PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX idx_pa_trasf_resp_1 ON pa_transform_risp (id_trasformazione,nome);
CREATE UNIQUE INDEX idx_pa_trasf_resp_2 ON pa_transform_risp (id_trasformazione,posizione);



CREATE TABLE pa_transform_risp_hdr
(
	id_transform_risp BIGINT NOT NULL,
	tipo VARCHAR(255) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(max),
	identificazione_fallita VARCHAR(255),
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_risp_hdr_1 FOREIGN KEY (id_transform_risp) REFERENCES pa_transform_risp(id),
	CONSTRAINT pk_pa_transform_risp_hdr PRIMARY KEY (id)
);

-- index
CREATE INDEX idx_pa_trasf_hdr_resp_1 ON pa_transform_risp_hdr (id_transform_risp);



CREATE TABLE pa_val_pattern
(
	id_configurazione BIGINT NOT NULL,
	tipo_validazione VARCHAR(255) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	regola VARCHAR(max) NOT NULL,
	-- abilitato/disabilitato
	pattern_and VARCHAR(255),
	-- abilitato/disabilitato
	pattern_not VARCHAR(255),
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT unique_pa_val_pattern_1 UNIQUE (tipo_validazione,id_configurazione,nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_pa_val_pattern PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_val_pattern_1 ON pa_val_pattern (tipo_validazione,id_configurazione,nome);



CREATE TABLE pa_val_richiesta
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	posizione INT NOT NULL,
	-- abilitato/disabilitato/warningOnly
	stato VARCHAR(255) NOT NULL,
	-- openspcoop/interface/xsd/json/pattern
	tipo VARCHAR(255),
	-- abilitato/disabilitato
	mtom VARCHAR(255),
	-- abilitato/disabilitato
	soapaction VARCHAR(255),
	-- Nome interfaccia json
	json VARCHAR(255),
	-- abilitato/disabilitato
	pattern_and VARCHAR(255),
	-- abilitato/disabilitato
	pattern_not VARCHAR(255),
	applicabilita_azioni VARCHAR(max),
	applicabilita_ct VARCHAR(max),
	applicabilita_pattern VARCHAR(max),
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT unique_pa_val_richiesta_1 UNIQUE (id_porta,nome),
	CONSTRAINT unique_pa_val_richiesta_2 UNIQUE (id_porta,posizione),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_val_richiesta_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_val_richiesta PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_val_richiesta_1 ON pa_val_richiesta (id_porta,nome);
CREATE UNIQUE INDEX index_pa_val_richiesta_2 ON pa_val_richiesta (id_porta,posizione);



CREATE TABLE pa_val_risposta
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	posizione INT NOT NULL,
	-- abilitato/disabilitato/warningOnly
	stato VARCHAR(255) NOT NULL,
	-- openspcoop/interface/xsd/json/pattern
	tipo VARCHAR(255),
	-- abilitato/disabilitato
	mtom VARCHAR(255),
	-- Nome interfaccia json
	json VARCHAR(255),
	-- abilitato/disabilitato
	pattern_and VARCHAR(255),
	-- abilitato/disabilitato
	pattern_not VARCHAR(255),
	applicabilita_azioni VARCHAR(max),
	applicabilita_ct VARCHAR(max),
	applicabilita_pattern VARCHAR(max),
	applicabilita_status_min INT,
	applicabilita_status_max INT,
	applicabilita_problem_detail VARCHAR(255),
	applicabilita_empty_response VARCHAR(255),
	-- fk/pk columns
	id BIGINT IDENTITY,
	-- unique constraints
	CONSTRAINT unique_pa_val_risposta_1 UNIQUE (id_porta,nome),
	CONSTRAINT unique_pa_val_risposta_2 UNIQUE (id_porta,posizione),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_val_risposta_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_val_risposta PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_val_risposta_1 ON pa_val_risposta (id_porta,nome);
CREATE UNIQUE INDEX index_pa_val_risposta_2 ON pa_val_risposta (id_porta,posizione);


