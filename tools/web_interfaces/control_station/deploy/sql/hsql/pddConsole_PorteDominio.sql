-- **** Porte di Dominio ****

CREATE SEQUENCE seq_pdd AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pdd
(
	nome VARCHAR(255) NOT NULL,
	descrizione VARCHAR(255),
	-- ip pubblico
	ip VARCHAR(255),
	-- porta pubblico
	porta INT,
	-- protocollo pubblico
	protocollo VARCHAR(255),
	-- ip gestione
	ip_gestione VARCHAR(255),
	-- porta gestione
	porta_gestione INT,
	-- protocollo gestione
	protocollo_gestione VARCHAR(255),
	-- Tipo della Porta
	tipo VARCHAR(255),
	implementazione VARCHAR(255),
	subject VARCHAR(255),
	password VARCHAR(255),
	-- client auth: disabilitato/abilitato
	client_auth VARCHAR(255),
	ora_registrazione TIMESTAMP,
	superuser VARCHAR(255),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- check constraints
	CONSTRAINT chk_pdd_1 CHECK (tipo IN ('operativo','nonoperativo','esterno')),
	-- unique constraints
	CONSTRAINT unique_pdd_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_pdd PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pdd_1 ON pdd (nome);

ALTER TABLE pdd ALTER COLUMN implementazione SET DEFAULT 'standard';
ALTER TABLE pdd ALTER COLUMN ora_registrazione SET DEFAULT CURRENT_TIMESTAMP;

CREATE TABLE pdd_init_seq (id BIGINT);
INSERT INTO pdd_init_seq VALUES (NEXT VALUE FOR seq_pdd);


