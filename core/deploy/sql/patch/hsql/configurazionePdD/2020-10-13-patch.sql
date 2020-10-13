ALTER TABLE porte_delegate ADD COLUMN canale VARCHAR(20);
ALTER TABLE porte_applicative ADD COLUMN canale VARCHAR(20);
ALTER TABLE configurazione ADD COLUMN canali_stato VARCHAR(255);


CREATE SEQUENCE seq_canali_configurazione AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE canali_configurazione
(
	nome VARCHAR(20) NOT NULL,
	descrizione VARCHAR(255),
	canale_default INT NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_canali_configurazione_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_canali_configurazione PRIMARY KEY (id)
);

CREATE TABLE canali_configurazione_init_seq (id BIGINT);
INSERT INTO canali_configurazione_init_seq VALUES (NEXT VALUE FOR seq_canali_configurazione);



CREATE SEQUENCE seq_canali_nodi AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE canali_nodi
(
	nome VARCHAR(255) NOT NULL,
	descrizione VARCHAR(255),
	canali VARCHAR(4000) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_canali_nodi_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_canali_nodi PRIMARY KEY (id)
);

CREATE TABLE canali_nodi_init_seq (id BIGINT);
INSERT INTO canali_nodi_init_seq VALUES (NEXT VALUE FOR seq_canali_nodi);
