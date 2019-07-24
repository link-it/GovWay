-- **** Porte Applicative ****

CREATE SEQUENCE seq_porte_applicative AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

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
	versione_servizio INT NOT NULL,
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
	-- wsdl/interface/xsd
	validazione_contenuti_tipo VARCHAR(255),
	-- abilitato/disabilitato
	validazione_contenuti_mtom VARCHAR(255),
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
	autorizzazione_xacml LONGVARCHAR,
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
	cors_allow_credentials VARCHAR(255),
	cors_allow_max_age INT,
	cors_allow_max_age_seconds INT,
	cors_allow_origins VARCHAR(65535),
	cors_allow_headers VARCHAR(65535),
	cors_allow_methods VARCHAR(65535),
	cors_allow_expose_headers VARCHAR(65535),
	-- Response caching
	response_cache_stato VARCHAR(255),
	response_cache_seconds INT,
	response_cache_max_msg_size BIGINT,
	response_cache_hash_url VARCHAR(255),
	response_cache_hash_query VARCHAR(255),
	response_cache_hash_query_list VARCHAR(65535),
	response_cache_hash_headers VARCHAR(255),
	response_cache_hash_hdr_list VARCHAR(65535),
	response_cache_hash_payload VARCHAR(255),
	response_cache_control_nocache INT,
	response_cache_control_maxage INT,
	response_cache_control_nostore INT,
	-- Stato della porta: abilitato/disabilitato
	stato VARCHAR(255),
	-- proprietario porta applicativa
	id_soggetto BIGINT NOT NULL,
	ora_registrazione TIMESTAMP,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_porte_applicative_1 UNIQUE (nome_porta),
	-- fk/pk keys constraints
	CONSTRAINT fk_porte_applicative_1 FOREIGN KEY (id_soggetto) REFERENCES soggetti(id),
	CONSTRAINT pk_porte_applicative PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_porte_applicative_1 ON porte_applicative (nome_porta);

ALTER TABLE porte_applicative ALTER COLUMN versione_servizio SET DEFAULT 1;
ALTER TABLE porte_applicative ALTER COLUMN ora_registrazione SET DEFAULT CURRENT_TIMESTAMP;

CREATE TABLE porte_applicative_init_seq (id BIGINT);
INSERT INTO porte_applicative_init_seq VALUES (NEXT VALUE FOR seq_porte_applicative);



CREATE SEQUENCE seq_porte_applicative_sa AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE porte_applicative_sa
(
	id_porta BIGINT NOT NULL,
	id_servizio_applicativo BIGINT NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_porte_applicative_sa_1 UNIQUE (id_porta,id_servizio_applicativo),
	-- fk/pk keys constraints
	CONSTRAINT fk_porte_applicative_sa_1 FOREIGN KEY (id_servizio_applicativo) REFERENCES servizi_applicativi(id),
	CONSTRAINT fk_porte_applicative_sa_2 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_porte_applicative_sa PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_SA ON porte_applicative_sa (id_porta);
CREATE TABLE porte_applicative_sa_init_seq (id BIGINT);
INSERT INTO porte_applicative_sa_init_seq VALUES (NEXT VALUE FOR seq_porte_applicative_sa);



CREATE SEQUENCE seq_pa_auth_properties AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_auth_properties
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT uniq_pa_auth_props_1 UNIQUE (id_porta,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_auth_properties_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_auth_properties PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_AUTH_PROP ON pa_auth_properties (id_porta);
CREATE TABLE pa_auth_properties_init_seq (id BIGINT);
INSERT INTO pa_auth_properties_init_seq VALUES (NEXT VALUE FOR seq_pa_auth_properties);



CREATE SEQUENCE seq_pa_authz_properties AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_authz_properties
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT uniq_pa_authz_props_1 UNIQUE (id_porta,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_authz_properties_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_authz_properties PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_AUTHZ_PROP ON pa_authz_properties (id_porta);
CREATE TABLE pa_authz_properties_init_seq (id BIGINT);
INSERT INTO pa_authz_properties_init_seq VALUES (NEXT VALUE FOR seq_pa_authz_properties);



CREATE SEQUENCE seq_pa_authzc_properties AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_authzc_properties
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT uniq_pa_authzc_props_1 UNIQUE (id_porta,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_authzc_properties_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_authzc_properties PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_AUTHZC_PROP ON pa_authzc_properties (id_porta);
CREATE TABLE pa_authzc_properties_init_seq (id BIGINT);
INSERT INTO pa_authzc_properties_init_seq VALUES (NEXT VALUE FOR seq_pa_authzc_properties);



CREATE SEQUENCE seq_pa_properties AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_properties
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT uniq_pa_properties_1 UNIQUE (id_porta,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_properties_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_properties PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_PROP ON pa_properties (id_porta);
CREATE TABLE pa_properties_init_seq (id BIGINT);
INSERT INTO pa_properties_init_seq VALUES (NEXT VALUE FOR seq_pa_properties);



CREATE SEQUENCE seq_pa_mtom_request AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_mtom_request
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	pattern VARCHAR(65535) NOT NULL,
	content_type VARCHAR(255),
	required INT NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_mtom_request_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_mtom_request PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_MTOMTREQ ON pa_mtom_request (id_porta);
CREATE TABLE pa_mtom_request_init_seq (id BIGINT);
INSERT INTO pa_mtom_request_init_seq VALUES (NEXT VALUE FOR seq_pa_mtom_request);



CREATE SEQUENCE seq_pa_mtom_response AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_mtom_response
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	pattern VARCHAR(65535) NOT NULL,
	content_type VARCHAR(255),
	required INT NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_mtom_response_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_mtom_response PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_MTOMTRES ON pa_mtom_response (id_porta);
CREATE TABLE pa_mtom_response_init_seq (id BIGINT);
INSERT INTO pa_mtom_response_init_seq VALUES (NEXT VALUE FOR seq_pa_mtom_response);



CREATE SEQUENCE seq_pa_security_request AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_security_request
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(65535) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_security_request_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_security_request PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_WSSREQ ON pa_security_request (id_porta);
CREATE TABLE pa_security_request_init_seq (id BIGINT);
INSERT INTO pa_security_request_init_seq VALUES (NEXT VALUE FOR seq_pa_security_request);



CREATE SEQUENCE seq_pa_security_response AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_security_response
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(65535) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_security_response_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_security_response PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_WSSRES ON pa_security_response (id_porta);
CREATE TABLE pa_security_response_init_seq (id BIGINT);
INSERT INTO pa_security_response_init_seq VALUES (NEXT VALUE FOR seq_pa_security_response);



CREATE SEQUENCE seq_pa_correlazione AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_correlazione
(
	id_porta BIGINT NOT NULL,
	nome_elemento VARCHAR(255),
	-- modalita di scelta user input, content-based, url-based, disabilitato
	mode_correlazione VARCHAR(255),
	-- pattern utilizzato solo per content/url based
	pattern VARCHAR(65535),
	-- blocca/accetta
	identificazione_fallita VARCHAR(255),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_correlazione_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_correlazione PRIMARY KEY (id)
);

CREATE TABLE pa_correlazione_init_seq (id BIGINT);
INSERT INTO pa_correlazione_init_seq VALUES (NEXT VALUE FOR seq_pa_correlazione);



CREATE SEQUENCE seq_pa_correlazione_risposta AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_correlazione_risposta
(
	id_porta BIGINT NOT NULL,
	nome_elemento VARCHAR(255),
	-- modalita di scelta user input, content-based, url-based, disabilitato
	mode_correlazione VARCHAR(255),
	-- pattern utilizzato solo per content/url based
	pattern VARCHAR(65535),
	-- blocca/accetta
	identificazione_fallita VARCHAR(255),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_correlazione_risposta_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_correlazione_risposta PRIMARY KEY (id)
);

CREATE TABLE pa_correlazione_risposta_init_seq (id BIGINT);
INSERT INTO pa_correlazione_risposta_init_seq VALUES (NEXT VALUE FOR seq_pa_correlazione_risposta);



CREATE SEQUENCE seq_pa_ruoli AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_ruoli
(
	id_porta BIGINT NOT NULL,
	ruolo VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_pa_ruoli_1 UNIQUE (id_porta,ruolo),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_ruoli_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_ruoli PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_ruoli_1 ON pa_ruoli (id_porta,ruolo);
CREATE TABLE pa_ruoli_init_seq (id BIGINT);
INSERT INTO pa_ruoli_init_seq VALUES (NEXT VALUE FOR seq_pa_ruoli);



CREATE SEQUENCE seq_pa_scope AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_scope
(
	id_porta BIGINT NOT NULL,
	scope VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_pa_scope_1 UNIQUE (id_porta,scope),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_scope_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_scope PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_scope_1 ON pa_scope (id_porta,scope);
CREATE TABLE pa_scope_init_seq (id BIGINT);
INSERT INTO pa_scope_init_seq VALUES (NEXT VALUE FOR seq_pa_scope);



CREATE SEQUENCE seq_pa_soggetti AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_soggetti
(
	id_porta BIGINT NOT NULL,
	tipo_soggetto VARCHAR(255) NOT NULL,
	nome_soggetto VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_pa_soggetti_1 UNIQUE (id_porta,tipo_soggetto,nome_soggetto),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_soggetti_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_soggetti PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_soggetti_1 ON pa_soggetti (id_porta,tipo_soggetto,nome_soggetto);
CREATE TABLE pa_soggetti_init_seq (id BIGINT);
INSERT INTO pa_soggetti_init_seq VALUES (NEXT VALUE FOR seq_pa_soggetti);



CREATE SEQUENCE seq_porte_applicative_sa_auth AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE porte_applicative_sa_auth
(
	id_porta BIGINT NOT NULL,
	id_servizio_applicativo BIGINT NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT uniq_pa_sa_auth_1 UNIQUE (id_porta,id_servizio_applicativo),
	-- fk/pk keys constraints
	CONSTRAINT fk_porte_applicative_sa_auth_1 FOREIGN KEY (id_servizio_applicativo) REFERENCES servizi_applicativi(id),
	CONSTRAINT fk_porte_applicative_sa_auth_2 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_porte_applicative_sa_auth PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_SA_AUTH ON porte_applicative_sa_auth (id_porta);
CREATE TABLE porte_applicative_sa_auth_init_seq (id BIGINT);
INSERT INTO porte_applicative_sa_auth_init_seq VALUES (NEXT VALUE FOR seq_porte_applicative_sa_auth);



CREATE SEQUENCE seq_pa_azioni AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_azioni
(
	id_porta BIGINT NOT NULL,
	azione VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_pa_azioni_1 UNIQUE (id_porta,azione),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_azioni_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_azioni PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_azioni_1 ON pa_azioni (id_porta,azione);
CREATE TABLE pa_azioni_init_seq (id BIGINT);
INSERT INTO pa_azioni_init_seq VALUES (NEXT VALUE FOR seq_pa_azioni);



CREATE SEQUENCE seq_pa_cache_regole AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_cache_regole
(
	id_porta BIGINT NOT NULL,
	status_min INT,
	status_max INT,
	fault INT,
	cache_seconds INT,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_cache_regole_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_cache_regole PRIMARY KEY (id)
);


ALTER TABLE pa_cache_regole ALTER COLUMN fault SET DEFAULT 0;

CREATE TABLE pa_cache_regole_init_seq (id BIGINT);
INSERT INTO pa_cache_regole_init_seq VALUES (NEXT VALUE FOR seq_pa_cache_regole);



CREATE SEQUENCE seq_pa_transform AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_transform
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	posizione INT NOT NULL,
	stato VARCHAR(255),
	applicabilita_azioni VARCHAR(65535),
	applicabilita_ct VARCHAR(65535),
	applicabilita_pattern VARCHAR(65535),
	req_conversione_enabled INT NOT NULL,
	req_conversione_tipo VARCHAR(255),
	-- In hsql 2.x usare il tipo BLOB al posto di VARBINARY
	req_conversione_template VARBINARY(1073741823),
	req_content_type VARCHAR(255),
	rest_transformation INT NOT NULL,
	rest_method VARCHAR(255),
	rest_path VARCHAR(255),
	soap_transformation INT NOT NULL,
	soap_version VARCHAR(255),
	soap_action VARCHAR(255),
	soap_envelope INT NOT NULL,
	soap_envelope_as_attach INT NOT NULL,
	soap_envelope_tipo VARCHAR(255),
	-- In hsql 2.x usare il tipo BLOB al posto di VARBINARY
	soap_envelope_template VARBINARY(1073741823),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
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

ALTER TABLE pa_transform ALTER COLUMN req_conversione_enabled SET DEFAULT 0;
ALTER TABLE pa_transform ALTER COLUMN rest_transformation SET DEFAULT 0;
ALTER TABLE pa_transform ALTER COLUMN soap_transformation SET DEFAULT 0;
ALTER TABLE pa_transform ALTER COLUMN soap_envelope SET DEFAULT 0;
ALTER TABLE pa_transform ALTER COLUMN soap_envelope_as_attach SET DEFAULT 0;

CREATE TABLE pa_transform_init_seq (id BIGINT);
INSERT INTO pa_transform_init_seq VALUES (NEXT VALUE FOR seq_pa_transform);



CREATE SEQUENCE seq_pa_transform_soggetti AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_transform_soggetti
(
	id_trasformazione BIGINT NOT NULL,
	tipo_soggetto VARCHAR(255) NOT NULL,
	nome_soggetto VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_pa_transform_soggetti_1 UNIQUE (id_trasformazione,tipo_soggetto,nome_soggetto),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_soggetti_1 FOREIGN KEY (id_trasformazione) REFERENCES pa_transform(id),
	CONSTRAINT pk_pa_transform_soggetti PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_transform_soggetti_1 ON pa_transform_soggetti (id_trasformazione,tipo_soggetto,nome_soggetto);
CREATE TABLE pa_transform_soggetti_init_seq (id BIGINT);
INSERT INTO pa_transform_soggetti_init_seq VALUES (NEXT VALUE FOR seq_pa_transform_soggetti);



CREATE SEQUENCE seq_pa_transform_sa AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_transform_sa
(
	id_trasformazione BIGINT NOT NULL,
	id_servizio_applicativo BIGINT NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_pa_transform_sa_1 UNIQUE (id_trasformazione,id_servizio_applicativo),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_sa_1 FOREIGN KEY (id_servizio_applicativo) REFERENCES servizi_applicativi(id),
	CONSTRAINT fk_pa_transform_sa_2 FOREIGN KEY (id_trasformazione) REFERENCES pa_transform(id),
	CONSTRAINT pk_pa_transform_sa PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_transform_sa_1 ON pa_transform_sa (id_trasformazione,id_servizio_applicativo);
CREATE TABLE pa_transform_sa_init_seq (id BIGINT);
INSERT INTO pa_transform_sa_init_seq VALUES (NEXT VALUE FOR seq_pa_transform_sa);



CREATE SEQUENCE seq_pa_transform_hdr AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_transform_hdr
(
	id_trasformazione BIGINT NOT NULL,
	tipo VARCHAR(255) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(65535),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_hdr_1 FOREIGN KEY (id_trasformazione) REFERENCES pa_transform(id),
	CONSTRAINT pk_pa_transform_hdr PRIMARY KEY (id)
);

-- index
CREATE INDEX idx_pa_trasf_hdr_1 ON pa_transform_hdr (id_trasformazione);
CREATE TABLE pa_transform_hdr_init_seq (id BIGINT);
INSERT INTO pa_transform_hdr_init_seq VALUES (NEXT VALUE FOR seq_pa_transform_hdr);



CREATE SEQUENCE seq_pa_transform_url AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_transform_url
(
	id_trasformazione BIGINT NOT NULL,
	tipo VARCHAR(255) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(65535),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_url_1 FOREIGN KEY (id_trasformazione) REFERENCES pa_transform(id),
	CONSTRAINT pk_pa_transform_url PRIMARY KEY (id)
);

-- index
CREATE INDEX idx_pa_trasf_url_1 ON pa_transform_url (id_trasformazione);
CREATE TABLE pa_transform_url_init_seq (id BIGINT);
INSERT INTO pa_transform_url_init_seq VALUES (NEXT VALUE FOR seq_pa_transform_url);



CREATE SEQUENCE seq_pa_transform_risp AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_transform_risp
(
	id_trasformazione BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	posizione INT NOT NULL,
	applicabilita_status_min INT,
	applicabilita_status_max INT,
	applicabilita_ct VARCHAR(65535),
	applicabilita_pattern VARCHAR(65535),
	conversione_enabled INT NOT NULL,
	conversione_tipo VARCHAR(255),
	-- In hsql 2.x usare il tipo BLOB al posto di VARBINARY
	conversione_template VARBINARY(1073741823),
	content_type VARCHAR(255),
	return_code INT,
	soap_envelope INT NOT NULL,
	soap_envelope_as_attach INT NOT NULL,
	soap_envelope_tipo VARCHAR(255),
	-- In hsql 2.x usare il tipo BLOB al posto di VARBINARY
	soap_envelope_template VARBINARY(1073741823),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
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

ALTER TABLE pa_transform_risp ALTER COLUMN conversione_enabled SET DEFAULT 0;
ALTER TABLE pa_transform_risp ALTER COLUMN soap_envelope SET DEFAULT 0;
ALTER TABLE pa_transform_risp ALTER COLUMN soap_envelope_as_attach SET DEFAULT 0;

CREATE TABLE pa_transform_risp_init_seq (id BIGINT);
INSERT INTO pa_transform_risp_init_seq VALUES (NEXT VALUE FOR seq_pa_transform_risp);



CREATE SEQUENCE seq_pa_transform_risp_hdr AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_transform_risp_hdr
(
	id_transform_risp BIGINT NOT NULL,
	tipo VARCHAR(255) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(65535),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_risp_hdr_1 FOREIGN KEY (id_transform_risp) REFERENCES pa_transform_risp(id),
	CONSTRAINT pk_pa_transform_risp_hdr PRIMARY KEY (id)
);

-- index
CREATE INDEX idx_pa_trasf_hdr_resp_1 ON pa_transform_risp_hdr (id_transform_risp);
CREATE TABLE pa_transform_risp_hdr_init_seq (id BIGINT);
INSERT INTO pa_transform_risp_hdr_init_seq VALUES (NEXT VALUE FOR seq_pa_transform_risp_hdr);


