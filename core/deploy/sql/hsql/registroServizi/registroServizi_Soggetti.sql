-- **** Soggetti ****

CREATE SEQUENCE seq_soggetti AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE soggetti
(
	nome_soggetto VARCHAR(255) NOT NULL,
	tipo_soggetto VARCHAR(255) NOT NULL,
	descrizione VARCHAR(255),
	identificativo_porta VARCHAR(255),
	-- 1/0 (true/false) Indicazione se il soggetto svolge è quello di default per il protocollo
	is_default INT,
	-- 1/0 (true/false) svolge attivita di router
	is_router INT,
	id_connettore BIGINT NOT NULL,
	superuser VARCHAR(255),
	server VARCHAR(255),
	-- 1/0 (true/false) indica se il soggetto e' privato/pubblico
	privato INT,
	ora_registrazione TIMESTAMP,
	profilo VARCHAR(255),
	codice_ipa VARCHAR(255) NOT NULL,
	tipoauth VARCHAR(255),
	utente VARCHAR(2800),
	password VARCHAR(255),
	subject VARCHAR(2800),
	cn_subject VARCHAR(255),
	issuer VARCHAR(2800),
	cn_issuer VARCHAR(255),
	certificate VARBINARY(16777215),
	cert_strict_verification INT,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_soggetti_1 UNIQUE (nome_soggetto,tipo_soggetto),
	CONSTRAINT unique_soggetti_2 UNIQUE (codice_ipa),
	-- fk/pk keys constraints
	CONSTRAINT fk_soggetti_1 FOREIGN KEY (id_connettore) REFERENCES connettori(id),
	CONSTRAINT pk_soggetti PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_soggetti_1 ON soggetti (nome_soggetto,tipo_soggetto);
CREATE UNIQUE INDEX index_soggetti_2 ON soggetti (codice_ipa);

ALTER TABLE soggetti ALTER COLUMN is_default SET DEFAULT 0;
ALTER TABLE soggetti ALTER COLUMN is_router SET DEFAULT 0;
ALTER TABLE soggetti ALTER COLUMN privato SET DEFAULT 0;
ALTER TABLE soggetti ALTER COLUMN ora_registrazione SET DEFAULT CURRENT_TIMESTAMP;

CREATE TABLE soggetti_init_seq (id BIGINT);
INSERT INTO soggetti_init_seq VALUES (NEXT VALUE FOR seq_soggetti);



CREATE SEQUENCE seq_soggetti_ruoli AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE soggetti_ruoli
(
	id_soggetto BIGINT NOT NULL,
	id_ruolo BIGINT NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_soggetti_ruoli_1 UNIQUE (id_soggetto,id_ruolo),
	-- fk/pk keys constraints
	CONSTRAINT fk_soggetti_ruoli_1 FOREIGN KEY (id_soggetto) REFERENCES soggetti(id),
	CONSTRAINT fk_soggetti_ruoli_2 FOREIGN KEY (id_ruolo) REFERENCES ruoli(id),
	CONSTRAINT pk_soggetti_ruoli PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_soggetti_ruoli_1 ON soggetti_ruoli (id_soggetto,id_ruolo);
CREATE INDEX INDEX_SOGGETTI_RUOLI ON soggetti_ruoli (id_soggetto);
CREATE TABLE soggetti_ruoli_init_seq (id BIGINT);
INSERT INTO soggetti_ruoli_init_seq VALUES (NEXT VALUE FOR seq_soggetti_ruoli);



CREATE SEQUENCE seq_soggetti_credenziali AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE soggetti_credenziali
(
	id_soggetto BIGINT NOT NULL,
	subject VARCHAR(2800),
	cn_subject VARCHAR(255),
	issuer VARCHAR(2800),
	cn_issuer VARCHAR(255),
	certificate VARBINARY(16777215),
	cert_strict_verification INT,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_soggetti_credenziali_1 FOREIGN KEY (id_soggetto) REFERENCES soggetti(id),
	CONSTRAINT pk_soggetti_credenziali PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_SOGGETTI_CRED ON soggetti_credenziali (id_soggetto);
CREATE TABLE soggetti_credenziali_init_seq (id BIGINT);
INSERT INTO soggetti_credenziali_init_seq VALUES (NEXT VALUE FOR seq_soggetti_credenziali);



CREATE SEQUENCE seq_soggetti_properties AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE soggetti_properties
(
	id_soggetto BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(4000) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT uniq_soggetti_props_1 UNIQUE (id_soggetto,nome),
	-- fk/pk keys constraints
	CONSTRAINT fk_soggetti_properties_1 FOREIGN KEY (id_soggetto) REFERENCES soggetti(id),
	CONSTRAINT pk_soggetti_properties PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_SOGGETTI_PROP ON soggetti_properties (id_soggetto);
CREATE TABLE soggetti_properties_init_seq (id BIGINT);
INSERT INTO soggetti_properties_init_seq VALUES (NEXT VALUE FOR seq_soggetti_properties);


