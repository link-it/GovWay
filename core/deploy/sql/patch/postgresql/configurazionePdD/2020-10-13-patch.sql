ALTER TABLE porte_delegate ADD COLUMN canale VARCHAR(20);
ALTER TABLE porte_applicative ADD COLUMN canale VARCHAR(20);
ALTER TABLE configurazione ADD COLUMN canali_stato VARCHAR(255);


CREATE SEQUENCE seq_canali_configurazione start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE canali_configurazione
(
	nome VARCHAR(20) NOT NULL,
	descrizione VARCHAR(255),
	canale_default INT NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_canali_configurazione') NOT NULL,
	-- unique constraints
	CONSTRAINT unique_canali_configurazione_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_canali_configurazione PRIMARY KEY (id)
);




CREATE SEQUENCE seq_canali_nodi start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE canali_nodi
(
	nome VARCHAR(255) NOT NULL,
	descrizione VARCHAR(255),
	canali VARCHAR(4000) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_canali_nodi') NOT NULL,
	-- unique constraints
	CONSTRAINT unique_canali_nodi_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_canali_nodi PRIMARY KEY (id)
);



