-- **** Connettori Gestione Errore ****

CREATE SEQUENCE seq_gestione_errore AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE gestione_errore
(
	-- accetta/rispedisci
	comportamento_default VARCHAR(255),
	cadenza_rispedizione VARCHAR(255),
	nome VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_gestione_errore_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_gestione_errore PRIMARY KEY (id)
);

CREATE TABLE gestione_errore_init_seq (id BIGINT);
INSERT INTO gestione_errore_init_seq VALUES (NEXT VALUE FOR seq_gestione_errore);



CREATE SEQUENCE seq_gestione_errore_trasporto AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE gestione_errore_trasporto
(
	id_gestione_errore BIGINT NOT NULL,
	valore_massimo INT,
	valore_minimo INT,
	-- accetta/rispedisci
	comportamento VARCHAR(255),
	cadenza_rispedizione VARCHAR(255),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_gestione_errore_trasporto_1 FOREIGN KEY (id_gestione_errore) REFERENCES gestione_errore(id),
	CONSTRAINT pk_gestione_errore_trasporto PRIMARY KEY (id)
);

-- index
CREATE INDEX idx_gest_err_trasporto_1 ON gestione_errore_trasporto (id_gestione_errore);
CREATE TABLE gestione_errore_trasporto_init_seq (id BIGINT);
INSERT INTO gestione_errore_trasporto_init_seq VALUES (NEXT VALUE FOR seq_gestione_errore_trasporto);



CREATE SEQUENCE seq_gestione_errore_soap AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE gestione_errore_soap
(
	id_gestione_errore BIGINT NOT NULL,
	fault_actor VARCHAR(255),
	fault_code VARCHAR(255),
	fault_string VARCHAR(255),
	-- accetta/rispedisci
	comportamento VARCHAR(255),
	cadenza_rispedizione VARCHAR(255),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_gestione_errore_soap_1 FOREIGN KEY (id_gestione_errore) REFERENCES gestione_errore(id),
	CONSTRAINT pk_gestione_errore_soap PRIMARY KEY (id)
);

-- index
CREATE INDEX idx_gest_err_soap_1 ON gestione_errore_soap (id_gestione_errore);
CREATE TABLE gestione_errore_soap_init_seq (id BIGINT);
INSERT INTO gestione_errore_soap_init_seq VALUES (NEXT VALUE FOR seq_gestione_errore_soap);


