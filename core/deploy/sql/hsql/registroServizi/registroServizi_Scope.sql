-- **** Scope ****

CREATE SEQUENCE seq_scope AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE scope
(
	nome VARCHAR(255) NOT NULL,
	descrizione VARCHAR(255),
	tipologia VARCHAR(255),
	nome_esterno VARCHAR(255),
	contesto_utilizzo VARCHAR(255) NOT NULL,
	superuser VARCHAR(255),
	ora_registrazione TIMESTAMP,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- check constraints
	CONSTRAINT chk_scope_1 CHECK (contesto_utilizzo IN ('portaDelegata','portaApplicativa','qualsiasi')),
	-- unique constraints
	CONSTRAINT unique_scope_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_scope PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_scope_1 ON scope (nome);

ALTER TABLE scope ALTER COLUMN contesto_utilizzo SET DEFAULT 'qualsiasi';
ALTER TABLE scope ALTER COLUMN ora_registrazione SET DEFAULT CURRENT_TIMESTAMP;

CREATE TABLE scope_init_seq (id BIGINT);
INSERT INTO scope_init_seq VALUES (NEXT VALUE FOR seq_scope);

