ALTER TABLE porte_delegate ADD COLUMN canale VARCHAR(20);
ALTER TABLE porte_applicative ADD COLUMN canale VARCHAR(20);
ALTER TABLE configurazione ADD COLUMN canali_stato VARCHAR(255);

CREATE TABLE canali_configurazione
(
	nome VARCHAR(20) NOT NULL,
	descrizione VARCHAR(255),
	canale_default INT NOT NULL,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	-- unique constraints
	CONSTRAINT unique_canali_configurazione_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_canali_configurazione PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs ROW_FORMAT DYNAMIC;




CREATE TABLE canali_nodi
(
	nome VARCHAR(255) NOT NULL,
	descrizione VARCHAR(255),
	canali VARCHAR(4000) NOT NULL,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	-- unique constraints
	CONSTRAINT unique_canali_nodi_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_canali_nodi PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs ROW_FORMAT DYNAMIC;

